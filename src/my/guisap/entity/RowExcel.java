/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.entity;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KiselevDA
 */
public class RowExcel {

    private String nameRow;
    private String id;
    private List<Object> columnNames;
    private List<List<Object>> column;

    public RowExcel() {
        this.columnNames = new ArrayList<>();
        this.column = new ArrayList<>();
    }

    public RowExcel(DefaultTableModel input) {
        this.columnNames = new ArrayList<>();
        this.column = new ArrayList<>();
        DefaultTableModelToRow(input);
    }

    public RowExcel(DefaultTableModel input, String nameRow, String id) {
        this.nameRow = nameRow;
        this.id = id;
        this.columnNames = new ArrayList<>();
        this.column = new ArrayList<>();
        DefaultTableModelToRow(input);
    }

    public RowExcel(String nameRow, String id, List<Object> columnNames, List<List<Object>> column) {
        this.nameRow = nameRow;
        this.id = id;
        this.columnNames = columnNames;
        this.column = column;
    }

    public String getNameRow() {
        return nameRow;
    }

    public void setNameRow(String nameRow) {
        this.nameRow = nameRow;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<Object> columnNames) {
        this.columnNames = columnNames;
    }

    public List<List<Object>> getColumn() {
        return column;
    }

    public void setColumn(List<List<Object>> column) {
        this.column = column;
    }

    public void DefaultTableModelToRow(DefaultTableModel input) {
        for (int i = 0; i < input.getColumnCount(); i++) {
            columnNames.add(input.getColumnName(i));
        }

        for (int i = 0; i < input.getRowCount(); i++) {
            List<Object> tmp = new ArrayList<>();
            for (int j = 0; j < input.getColumnCount(); j++) {

                tmp.add(input.getValueAt(i, j));

            }
            column.add(tmp);
        }
    }

    public DefaultTableModel getDefaultTableModel() {
        DefaultTableModel result = new DefaultTableModel();
        int startPos = 0;
        if (columnNames.size() > 0) {
            result.setColumnIdentifiers(columnNames.toArray());
        } else {
            result.setColumnIdentifiers(column.get(0).toArray());
            startPos++;
        }
        for (int i = startPos; i < column.size(); i++) {
            result.addRow(column.get(i).toArray());
        }
        return result;
    }

    @Override
    public String toString() {
        return "Model = " + nameRow + "\nRowForExel{" + "columnNames=" + columnNames + "\n column=" + column + '}';
    }

}
