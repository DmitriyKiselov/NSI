/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author dima
 */
public class LogClass {

    private static LogClass logClass;
    SqlOperations sql = new SqlOperations();
    String currentDate = "";
    String user = "";
    String host = "";

    private LogClass() {
        user = System.getProperty("user.name");
        host = getHost();
    }

    public static LogClass getInstance() {
        if (logClass == null) {
            logClass = new LogClass();
        }
        return logClass;
    }

    public void logWriting(String logData) {
        try {
            sql.SendQuery(SqlOperations.INSERT_LOGS + "Systimestamp,'" + host + "','" + my.guisap.utils.SecurityManager.userName + "','" + logData + "')");
        } catch (UnknownError e) {
            System.out.println("Ошибка записи лога : " + e);
        }
    }

    private String getHost() {
        String hostname = "";
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(LogClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hostname;
    }
    
    public String artReportDataFormation() {
        return new SimpleDateFormat("dd-MM-yyyy ,HH:mm:ss").format(new GregorianCalendar().getTime()) + "//" + host + "//" + my.guisap.utils.SecurityManager.userName;
    }

}
