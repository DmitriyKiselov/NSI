/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.componenst.tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author KiselevDA
 */
public class TableModelForCalculation extends AbstractTableModel {

    public String[] m_colNames = {"", "Модель", "Артикул", "Количество"};

    Class[] types = new Class[]{
        java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
    };
    boolean[] canEdit = new boolean[]{
        true, false, false, true
    };

    List<Object[]> data = new ArrayList<>();

    public TableModelForCalculation() {
        super();
    }

    public TableModelForCalculation(List<Object[]> macDataVector) {
        super();
        data = macDataVector;
    }

    @Override
    public int getColumnCount() {
        return m_colNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data.get(row)[col] = value;
    }

    @Override
    public String getColumnName(int col) {
        return m_colNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }

    public void addRow(String model, String art) {
        data.add(new Object[]{false, model, art, ""});
        fireTableDataChanged();
    }

    public void deleteRow(int indexRow, boolean update) {
        data.remove(indexRow);
        if (update) {
            fireTableDataChanged();
        }
    }

    public void clearAll() {
        data.clear();
        fireTableDataChanged();
    }
}
