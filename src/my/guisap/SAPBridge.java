/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap;

import my.guisap.utils.LogClass;
import java.util.Properties;
import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import my.guisap.sql.ConnectionClass;

/**
 *
 * @author dima
 */
public class SAPBridge {

    ResultSet rs;
    PreparedStatement ps;
    private static SAPBridge SAPBridge;
    static String ahost = "192.168.2.227";
    static String sysnr = "00";
    static String client = "599";
    static String user = "TYSHEVICHDV";
    static String passwd = "131313";
    static String lang = "RU";
    static String poolCapacity = "3";
    static String peakLimit = "10";
    static final String DESTINATION_NAME1 = "ABAP_AS_WITHOUT_POOL";
    static final String DESTINATION_NAME2 = "ABAP_AS_WITH_POOL";
    Properties connectProperties = new Properties();
    String hostName;
    public static final String PREFIX_TAB = "SAPX_";
    public static final String DATABASE = ConnectionClass.databaseName;
    LogClass log;

    public SAPBridge() {
        setSAPConnectProperties();
        createDestinationDataFile(DESTINATION_NAME1, connectProperties);
        log = LogClass.getInstance();
    }

    public static SAPBridge getInstance() {
        if (SAPBridge == null) {
            SAPBridge = new SAPBridge();
        }
        return SAPBridge;
    }

    private void setSAPConnectProperties() {
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, ahost);
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, sysnr);
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, client);
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, user);
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, passwd);
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, lang);
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, poolCapacity);
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, peakLimit);
    }

    // ************************************
    // создает файл соединения
    // ************************************
    private static void createDestinationDataFile(String destinationName, Properties connectProperties) {
        File destCfg = new File(destinationName + ".jcoDestination");
        try {
            FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "SAP to SQL");
            fos.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to create the destination files",
                    "Сообщение", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Unable to create the destination files", e);

        }
    }

    public void executeSAPFM(String sapFunctionName) throws SQLException {

        JCoFunction function = null;
        try {
            JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME1);
            function = destination.getRepository().getFunction(sapFunctionName);
            if (function == null) {
                throw new RuntimeException(sapFunctionName + " not found in SAP.");
            }
            try {
                function.execute(destination);
            } catch (AbapException e) {
                System.out.println(e.toString());
            }
        } catch (JCoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Сообщение", JOptionPane.ERROR_MESSAGE);
        }
        for (int tableNo = 0; tableNo < function.getTableParameterList().getFieldCount(); tableNo++) {
            moveDataToSQLTable(function.getTableParameterList().getString(tableNo),
                    function.getTableParameterList().getTable(tableNo));
        }

        JOptionPane.showMessageDialog(null, "Выгрузка данных окончена",
                "Сообщение", JOptionPane.INFORMATION_MESSAGE);
    }

    private void moveDataToSQLTable(String tableName, JCoTable codes) throws SQLException {
        int codeColumn = 1;
        int nameColumn = 3;
        java.sql.Connection con = null;

        if (tableName.substring(0, 1).equals("T")) {
            codeColumn = 2;
            nameColumn = 3;
        } else if (tableName.substring(0, 2).equals("ZT")) {
            if (tableName.equals("ZTKOROB")) {
                codeColumn = 0;
                nameColumn = 1;
            } else if (tableName.equals("ZTSIZE")) {
                codeColumn = 1;
                nameColumn = 2;
            } else if (tableName.equals("ZTFSIZE")) {
                codeColumn = 2;
                nameColumn = 3;
            } else {
                codeColumn = 1;
                nameColumn = 3;
            }
        } else if (tableName.equals("CAWNT")) {
            codeColumn = 1;
            nameColumn = 5;
        } else if (tableName.equals("PA0003")) {
            codeColumn = 1;
            nameColumn = 9;
        } else if (tableName.substring(0, 2).equals("Z_")) {
            if (tableName.equals("Z_COMPLEXION_BY_RALA")) {
                codeColumn = 1;
                nameColumn = 2;
            } else {
                codeColumn = 0;
                nameColumn = 1;
            }

        }

        tableName = PREFIX_TAB + tableName;
        con = my.guisap.sql.ConnectionClass.CONNECT_BUF;
        System.out.println(tableName + " загружается.");
        try {
            ps = con.prepareStatement("select count(*) from User_Tables where table_name = upper('" + tableName + "')");
            rs = ps.executeQuery();
            rs.next();
            if (rs.getString(1).equals("0")) {
                ps = con.prepareStatement("CREATE TABLE " + tableName + "("
                        + " \"CODE\" VARCHAR2(200 CHAR) DEFAULT null,"
                        + " \"NAME\" VARCHAR2(200 CHAR) DEFAULT null) ");
            } else {
                ps = con.prepareStatement("delete  from " + tableName + " ");
            }
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Ошибка при загрузке " + tableName);
        } finally {
            rs.close();
            ps.close();
        }

        for (int rowNo = 0; rowNo < codes.getNumRows(); rowNo++) {
            codes.setRow(rowNo);
            try {
                ps = con.prepareStatement("insert into " + tableName + "(CODE, NAME) values (?,?)");
                ps.setString(1, codes.getString(codeColumn));
                ps.setString(2, codes.getString(nameColumn));
                ps.execute();
            } catch (SQLException e) {
                System.out.println("Ошибка при создании таблицы " + tableName);
            } finally {
                ps.close();
            }
        }
        log.logWriting("загрузил таблицу " + tableName + " из SAP.");
        System.out.println("Таблица:" + tableName + " загружена!");
//        connect.closeConnection(rs, null, ps, connect.getConnection());
//        if (con != null) {
//            con.close();
//        }
    }
}
