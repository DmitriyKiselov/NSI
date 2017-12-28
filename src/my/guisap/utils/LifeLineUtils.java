/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author KiselevDA
 */
public class LifeLineUtils {

    public static String MKZ_LIFE_LINE = "MKZ_LIFE_LINE";
    public static String LAST_LIFE_LINE = "LAST_LIFE_LINE";
    public static SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    private static SqlOperations sql = new SqlOperations();

    public static DefaultTableModel CreateTable(String table, String id) {
        DefaultTableModel sourceModel = new DefaultTableModel();
        sql.tableFill("SELECT * FROM " + table + " WHERE ID='" + id + "'", sourceModel);
        DefaultTableModel resultModel = new DefaultTableModel();
        if (sourceModel.getRowCount() == 1) {
            List<String[]> tmpList = SplitLifeLine((String) sourceModel.getValueAt(0, 1));
            Object[] tmpArrayDate = new Object[tmpList.size()];
            Object[] tmpArrayUsers = new Object[tmpList.size()];
            for (int i = 0; i < tmpList.size(); i++) {
                resultModel.addColumn(transformStatus(tmpList.get(i)[0]));
                tmpArrayDate[i] = tmpList.get(i)[1];
                tmpArrayUsers[i] = tmpList.get(i)[2];
            }
            resultModel.addRow(tmpArrayDate);
            resultModel.addRow(tmpArrayUsers);
        }
        return resultModel;
    }

    public static void InsertLifeLine(String table, String id, String status) {
        if (!sql.checkInfo("SELECT * FROM " + table + " WHERE ID='" + id + "'")) {
            String lifeLine = status + "/" + formatForDateNow.format(new Date()) + "/" + my.guisap.utils.SecurityManager.userName + ";";
            sql.SendQuery("INSERT INTO " + table + " VALUES ('" + id + "','" + lifeLine + "')");
        } else {
            UpdateLifeLine(table, id, status);
        }
    }

    public static void UpdateLifeLine(String table, String id, String status) {
        DefaultTableModel resultModel = new DefaultTableModel();
        String query = "SELECT * FROM " + table + " WHERE ID='" + id + "'";
        sql.tableFill(query, resultModel);
        if (resultModel.getRowCount() > 0) {
            String lifeLine = (String) resultModel.getValueAt(0, 1) + status + "/" + formatForDateNow.format(new Date()) + "/" + my.guisap.utils.SecurityManager.userName + ";";
            sql.SendQuery("UPDATE " + table + " set LIFE_LINE='" + lifeLine + "' where id='" + id + "'");
        } else {
            JOptionPane.showMessageDialog(null, "Информация не заведена", "Внимание", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static List<String[]> SplitLifeLine(String line) {
        List<String[]> listLifeLine = new ArrayList();
        String[] firtsSplit = line.split(Pattern.quote(";"));
        for (String str : firtsSplit) {
            listLifeLine.add(str.split(Pattern.quote("/")));
        }
        return listLifeLine;
    }

    public static StringBuilder CreateLifeLine(List<String[]> listLifeLine) {
        StringBuilder result = new StringBuilder();
        for (String[] str : listLifeLine) {
            if (str.length == 3) {
                result.append(str[0]).append("/").append(str[1]).append("/").append(str[2]).append(";");
            }
        }
        return result;
    }

    public static String transformStatus(String status) {
        switch (status) {
            case "C":
                return "Рассмотрен";
            case "W":
                return "На рассмотрении";
            case "F":
                return "Первый этап";
            case "N":
                return "Второй этап";
            case "R":
                return "Отклонено";
            case "R_U":
                return "Отклонено (на доработку)";
            case "L":
                return "Сформирована заявка на колодку";
            case "LC":
                return "Заявка на колодку рассмотрена";
            case "FL":
                return "Этап формирования";
            default:
                return "Неизвестно";
        }
    }
}
