package my.guisap.forms;

import baseclass.BInternalFrame;
import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import my.guisap.ColorRend;
import my.guisap.componenst.JOverrideTable;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author dima
 */
public class RequestReport extends BInternalFrame {

    ColorRend colRend = new ColorRend();
    DefaultTableModel reqRepModel = new DefaultTableModel(0, 0);
    DefaultTableModel checkReqRepModel = new DefaultTableModel(0, 0) {
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex) {
            return colIndex == 0 || colIndex == 7;
        }

        @Override
        public Class<?> getColumnClass(int column) {
            if (column == 0) {
                return Boolean.class;
            } else {
                return super.getColumnClass(column);
            }
        }
    };

    public RequestReport() {
        setClosable(true);
        setMaximizable(true);
        initComponents();

        modelFormation(" where a.SAVE_FLAG = 0 order by a.ATBEZ");

        jComboBox1.addItemListener(
                new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jComboBox1.getSelectedItem().equals("Все")) {
                    modelFormation(" order by a.ATBEZ");
                }
                if (jComboBox1.getSelectedItem().equals("Заведенные")) {
                    modelFormation(" where a.SAVE_FLAG = 1 order by a.ATBEZ");
                }
                if (jComboBox1.getSelectedItem().equals("Скорректированные")) {
                    modelFormation(" where a.SAVE_FLAG = 3 order by a.ATBEZ");
                }
                if (jComboBox1.getSelectedItem().equals("Отклоненные")) {
                    modelFormation(" where a.SAVE_FLAG = 2 order by a.ATBEZ");
                }
                if (jComboBox1.getSelectedItem().equals("Не рассмотренные")) {
                    modelFormation(" where a.SAVE_FLAG = 0 order by a.ATBEZ");
                }
            }
        });
    }

    private void modelFormation(String query) {
        reqRepModel = new DefaultTableModel(0, 0);
        checkReqRepModel.setRowCount(0);
        checkReqRepModel.setColumnCount(0);
        sql.tableFill(SqlOperations.REQUEST_SAPX_REPORT + query, reqRepModel);

        jTable1.setModel(checkReqRepModel);
        jTable1.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        for (int colInd = 0; colInd < reqRepModel.getColumnCount(); colInd++) {
            if (colInd != 0) {
                checkReqRepModel.addColumn(reqRepModel.getColumnName(colInd - 1));
            } else {
                checkReqRepModel.addColumn("");
            }
        }

        for (int rowInd = 0; rowInd < reqRepModel.getRowCount(); rowInd++) {
            checkReqRepModel.addRow(new Object[]{});
            checkReqRepModel.setValueAt(false, rowInd, 0);

            for (int colInd = 0; colInd < reqRepModel.getColumnCount() - 1; colInd++) {
                if (reqRepModel.getValueAt(rowInd, colInd) != null) {
                    checkReqRepModel.setValueAt(reqRepModel.getValueAt(rowInd, colInd).toString(), rowInd, colInd + 1);
                } else {
                    checkReqRepModel.setValueAt("", rowInd, colInd + 1);
                }
            }

            if (!reqRepModel.getValueAt(rowInd, 8).toString().equals("0")) {
                checkReqRepModel.setValueAt(true, rowInd, 0);
            }
        }

        jTable1.getColumnModel().getColumn(0).setMaxWidth(25);
        jTable1.getTableHeader().setReorderingAllowed(false);

        for (int columnNo = 1; columnNo < jTable1.getColumnCount(); columnNo++) {
            jTable1.getColumnModel().getColumn(columnNo).setCellRenderer(new Rend());
        }

        JOverrideTable.packColumns(jTable1);
    }

    private class Rend extends DefaultTableCellRenderer {

        int colorBgRed = -1, colorBgGreen, colorBgBlue;
        int colorBgSelRed = 0, colorBgSelGreen = 0, colorBgSelBlue = 0;
        int colorHlRed = -1, colorHlGreen, colorHlBlue;
        int colorHlSelRed = 0, colorHlSelGreen = 0, colorHlSelBlue = 0;
        static final int COLOR_DESP = 20;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!isSelected) {
                if ((Boolean) jTable1.getValueAt(row, 0) == true) {
                    cell.setBackground(colRend.changeColor(cell, 1, ColorRend.COLOR_DESP_20));
                } else {
                    cell.setBackground(colRend.restoreColor(cell, 1));
                }
            } else {
                if ((Boolean) jTable1.getValueAt(row, 0) == true) {
                    cell.setBackground(colRend.changeColor(cell, 2, ColorRend.COLOR_DESP_20));
                } else {
                    cell.setBackground(colRend.restoreColor(cell, 2));
                }
            }
            return cell;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = TableRowFilterSupport
        .forTable(new JTable())
        .searchable(true)
        .actions(true)
        .apply();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setCellSelectionEnabled(true);
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Сохранить заведенные");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Отклонить");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Не рассмотренные", "Все", "Заведенные", "Скорректированные", "Отклоненные" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 131, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int count = 0;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            if ((Boolean) jTable1.getValueAt(i, 0) == true && reqRepModel.getValueAt(i, 8).toString().equals("0")) {
                if (jTable1.getValueAt(i, 7).equals(jTable1.getValueAt(i, 6))) {
                    sql.dataUpdate("update REQUEST_SAPX set SAVE_FLAG = 1, EDIT_VALUE = '" + jTable1.getValueAt(i, 7).toString() + "' where to_char(DATES,'yyyy-mm-dd hh24:mi:ss') = '" + jTable1.getValueAt(i, 3).toString() + "'");
                    count++;
                } else {
                    sql.dataUpdate("update REQUEST_SAPX set SAVE_FLAG = 3, EDIT_VALUE = '" + jTable1.getValueAt(i, 7).toString() + "' where to_char(DATES,'yyyy-mm-dd hh24:mi:ss') = '" + jTable1.getValueAt(i, 3).toString() + "'");
                    count++;
                }
            }
        }

        if (count > 0) {
            modelFormation(" where a.SAVE_FLAG = 0 order by a.ATBEZ");
            JOptionPane.showMessageDialog(null, "Заявки подтверждены", "Уведомление", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Выберите записи для подтверждения", "Уведомление", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int count = 0;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            if ((Boolean) jTable1.getValueAt(i, 0) == true && reqRepModel.getValueAt(i, 8).toString().equals("0")) {
                sql.dataUpdate("update REQUEST_SAPX set SAVE_FLAG = 2, EDIT_VALUE = '" + jTable1.getValueAt(i, 7).toString() + "' where to_char(DATES,'yyyy-mm-dd hh24:mi:ss') = '" + jTable1.getValueAt(i, 3).toString() + "'");
                count++;
            }
        }

        if (count > 0) {
            modelFormation(" where a.SAVE_FLAG = 0 order by a.ATBEZ");
            JOptionPane.showMessageDialog(null, "Заявки отклонены", "Уведомление", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Выберите записи для подтверждения", "Уведомление", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
