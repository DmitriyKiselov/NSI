/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.utils;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.table.DefaultTableModel;
import my.guisap.GuiStaticVariables;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author KiselevDA
 */
public class ComponentsUtils {

    public static JButton createBtn(String name, int width, int height, boolean enabled) {
        JButton resultBnt = new JButton(name);
        createComponent(resultBnt, width, height, enabled);
        return resultBnt;
    }

    public static JButton createBtn(String name, int width, int height, ImageIcon icon, boolean enabled) {
        JButton resultBnt = new JButton(name);
        createComponent(resultBnt, width, height, enabled);
        resultBnt.setIcon(icon);
        return resultBnt;
    }

    public static JTextField createField(String text, int width, int height, boolean enabled) {
        JTextField resultField = new JTextField(text);
        createComponent(resultField, width, height, enabled);
        return resultField;
    }

    public static JSpinner createSpinner(String text, int width, int height, SpinnerModel model, boolean enabled) {
        JSpinner resultSpinner = new JSpinner();
        createComponent(resultSpinner, width, height, enabled);
        resultSpinner.setModel(model);
        resultSpinner.setEditor(new JSpinner.NumberEditor(resultSpinner, "#"));
        return resultSpinner;
    }

    public static JLabel createLabel(String text) {
        JLabel resultLabel = new JLabel(text);
        resultLabel.setFont(GuiStaticVariables.globalFont);
        return resultLabel;
    }

    public static JLabel createLabel(String text, Font font) {
        JLabel resultLabel = new JLabel(text);
        resultLabel.setFont(font);
        return resultLabel;
    }

    public static JComboBox<String> createComboBox(int width, int height, boolean enabled) {
        JComboBox resultComboBox = new JComboBox();
        createComponent(resultComboBox, width, height, enabled);
        return resultComboBox;
    }

    public static JTable createTable(String query) {
        JTable resultTable = new JTable();
        DefaultTableModel tmpModel = new DefaultTableModel();
        if (query != null) {
            SqlOperations sql = new SqlOperations();
            sql.tableFill(query, tmpModel);
            resultTable.setModel(tmpModel);
        }
        resultTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        resultTable.setCellSelectionEnabled(true);
        resultTable.setOpaque(false);
        resultTable.setUpdateSelectionOnSort(false);
        resultTable.setFont(GuiStaticVariables.globalFont);
        return resultTable;
    }

    public static JComponent createComponent(JComponent component, int width, int height, boolean enabled) {
        component.setPreferredSize(createDimension(width, height));
        component.setMaximumSize(createDimension(width, height));
        component.setFont(GuiStaticVariables.globalFont);
        component.setEnabled(enabled);
        return component;
    }

    public static Dimension createDimension(int width, int height) {
        return new Dimension((int) (width * GuiStaticVariables.scaleWidth), (int) (height * GuiStaticVariables.scaleHeight));
    }

}
