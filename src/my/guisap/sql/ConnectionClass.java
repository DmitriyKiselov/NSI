package my.guisap.sql;

import my.guisap.forms.ConnectionFrame;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Hit
 */
public final class ConnectionClass {

    public static boolean flagLocalHost = false;
    public static String ip = "";
    public static String port = "";
    public static String databaseName = "";
    public static String userName = "";
    public static String password = "";
    public java.sql.Connection con = null;
    //private static final String CONNECT_URL = "jdbc:sqlserver://localhost;databaseName=SAP_NSI;integratedSecurity=true;";
    //private static final String CONNECT_URL2 = "jdbc:sqlserver://192.168.0.55:1433;databaseName=SAP_NSI;userName=" + "SAPuser" + ";password=" + "000" + ";";
    private static String CONNECT_URL = "";
    public static java.sql.Connection CONNECT_BUF = null;

    public ConnectionClass(String dataBase) {
        ConnectionClass.databaseName = dataBase;
        CONNECT_BUF = getConnection(dataBase);
    }

    public java.sql.Connection getConnection(String dataBase) {
        if (!flagLocalHost) {
            CONNECT_URL = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + dataBase;
        } else {
            CONNECT_URL = "jdbc:oracle:thin:@localhost:1521:" + dataBase;
        }
        System.out.println(CONNECT_URL);
        try {
            Locale.setDefault(Locale.ENGLISH);
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            con = DriverManager.getConnection(CONNECT_URL, userName, password);
        } catch (SQLException e) {
            Object[] options = {"Выход", "Подключиться еще раз"};
            int answer = JOptionPane.showOptionDialog(ConnectionFrame.linkConnectionFrame,
                    "Нет связи с сервером!",
                    "Ошибка",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (answer == 0) {
                System.exit(0);
            } else {
                getConnection(dataBase);
                return con;
            }
        }
        return con;
    }

    public void CloseConnOracle() {
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println("Ошибка при закрытии");
            Logger.getLogger(ConnectionClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeConnection(ResultSet rs, Statement st, PreparedStatement ps, java.sql.Connection con) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (st != null) {
            st.close();
        }
        if (con != null) {
            con.close();
        }
        if (ps != null) {
            ps.close();
        }
    }
}
