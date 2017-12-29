/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import my.guisap.componenst.AttributePanel;
import my.guisap.attributeRow;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author KiselevDA
 */
public class TextUtils {

    private static SqlOperations sql = new SqlOperations();

    /**
     * Проверка на наличии значения в базе
     *
     * @param nameTable название таблицы
     * @param value значение
     * @return true - есть false - нет
     */
    public static boolean checkRow(String nameTable, String value) {
        boolean result = false;
        if (!value.equals("") && !value.equals(" ")) {
            if (!sql.checkInfo("select * from GUI_SAP.SAPX_" + nameTable + " where NAME='" + value + "'")) {
                switch (nameTable) {
                    case "ZTCODE_LAST": {

                        if (!sql.checkInfo("select * from LAST_HEAD where INDEX_LAST='" + value + "'")) {
                            result = true;
                        }
                    }
                    break;
                    case "ZTFASON_LAST": {
                        if (!sql.checkInfo("select * from LAST_HEAD where FASON_LAST='" + value + "'")) {
                            result = true;
                        }
                    }
                    break;
                    case "ZTFASON_SOLE": {
                        if (!sql.checkInfo("select * from LB_SOLE where ART='" + value + "'")) {
                            result = true;
                        }
                    }
                    break;
                    case "ZTFASON_HEEL": {
                        if (!sql.checkInfo("select * from LB_HEEL where NAME='" + value + "'")) {
                            result = true;
                        }
                    }
                    break;
                    default:
                        result = true;
                        break;
                }
            }
        }
        return result;
    }

    public static boolean checkAttributePanel(AttributePanel attPanel, boolean checkForEmpty) {
        boolean result = false;
        for (attributeRow atR : attPanel.rowList) {
            atR.txt.setBorder(CreateFormUtils.defaultTextFieldBorder);
            if (checkRow(atR.atprt, atR.txt.getText())) {
                atR.txt.setBorder(BorderFactory.createLineBorder(Color.RED));
                result = true;
            }
            if (checkForEmpty && (atR.txt.getText().equals("") || atR.txt.getText().equals(" "))) {
                atR.txt.setBorder(BorderFactory.createLineBorder(Color.RED));
                result = true;
            }
        }
        return result;
    }

    public static boolean checkAttributePanel(AttributePanel attPanel, List<String> listSkipFields) {
        boolean result = false;
        for (attributeRow atR : attPanel.rowList) {
            atR.txt.setBorder(CreateFormUtils.defaultTextFieldBorder);
            if (checkRow(atR.atprt, atR.txt.getText())) {
                atR.txt.setBorder(BorderFactory.createLineBorder(Color.RED));
                result = true;
            }
            if (!isRowExeption(listSkipFields, atR) && (atR.txt.getText().equals("") || atR.txt.getText().equals(" "))) {
                atR.txt.setBorder(BorderFactory.createLineBorder(Color.RED));
                result = true;
            }
        }
        return result;
    }

    /**
     * Проверка поля на пустоту
     *
     * @param field поле для проверки
     * @return true - поле пустое, false - значение присутствует
     */
    public static boolean checkTextField(JTextField field) {
        field.setBorder(CreateFormUtils.defaultTextFieldBorder);
        if (field.getText().equals("") || field.getText().equals(" ")) {
            field.setBorder(BorderFactory.createLineBorder(Color.RED));
            return true;
        }
        return false;
    }

    /**
     * Проверка поля на пустоту
     *
     * @param field поле для проверки
     * @param nameField имя поля
     * @param listSkipFields пропускаемые поля
     * @return true - поле пустое, false - значение присутствует
     */
    public static boolean checkTextField(JTextField field, JLabel nameField, ArrayList<String> listSkipFields) {
        field.setBorder(CreateFormUtils.defaultTextFieldBorder);
        if ((field.getText().equals("") || field.getText().equals(" ")) && listSkipFields.indexOf(nameField.getText()) == -1) {
            field.setBorder(BorderFactory.createLineBorder(Color.RED));
            return true;
        }
        return false;
    }

    /**
     * Проверка поля на пустоту, с использованием счетчика
     *
     * @param field поле для проверки
     * @param counter счетчик, увеличивается при пустом значении поля
     * @return
     */
    public static int checkTextField(JTextField field, int counter) {
        field.setBorder(CreateFormUtils.defaultTextFieldBorder);
        if (field.getText().equals("") || field.getText().equals(" ")) {
            field.setBorder(BorderFactory.createLineBorder(Color.RED));
            counter++;
        }
        return counter;
    }

    public static boolean isRowExeption(List<String> listForCheck, attributeRow row) {
        for (String check : listForCheck) {
            if (row.lbl.getText().equals(check)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверка на ввод цифр true - является числом false - не является
     *
     * @param string строка
     * @param checkList список исключений
     * @return
     */
    public static boolean checkStringToNumber(String string, ArrayList checkList) {
        try {
            int tmp = Integer.parseInt(string);
            //проверка на определенные символы
            if (checkList != null) {
                for (Object obj : checkList) {
                    if (obj.equals(tmp)) {
                        return false;
                    }
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Проверка строки, является ли она числом, и входит ли она в заданный
     * промежуток
     *
     * @param string строка для проверки
     * @param beginNumber ограничение , начало
     * @param endNumber ограничение , конец
     * @return
     */
    public static boolean checkStringToNumber(String string, int beginNumber, int endNumber) {
        try {
            int tmp = Integer.parseInt(string);
            if (tmp >= beginNumber && tmp <= endNumber) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    /**
     * Проверка строки, является ли она числом, и входит ли она в заданный
     * промежуток
     *
     * @param string строка для проверки
     * @param beginNumber ограничение , начало
     * @param endNumber ограничение , конец
     * @param multiple проверка на кратность
     * @return
     */
    public static boolean checkStringToNumber(String string, int beginNumber, int endNumber, int multiple) {
        try {
            int tmp = Integer.parseInt(string);
            if (tmp >= beginNumber && tmp <= endNumber && tmp % multiple == 0) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

}
