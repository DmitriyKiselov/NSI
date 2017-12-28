package my.guisap.forms.BlackList;

import my.guisap.utils.LogClass;
import my.guisap.componenst.JOverrideTable;
import baseclass.BInternalFrame;
import java.awt.Component;
import java.awt.event.KeyEvent;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import my.guisap.ColorRend;

public final class BlackList extends BInternalFrame {

    BlackListDialog blackListDialog;
    ColorRend colRend = new ColorRend();
    TableRowSorter<DefaultTableModel> sorter;
    DefaultTableModel bListModel = new DefaultTableModel(0, 0);
    DefaultTableModel bListFullModel = new DefaultTableModel(0, 3) {
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex) {
            if (colIndex == 0) {
                return true;
            }
            return false;
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

    public BlackList() {
        setClosable(true);
        setMaximizable(true);
        initComponents();

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                } else {
                    sorter = new TableRowSorter<>(bListFullModel);
                    jTable1.setRowSorter(sorter);
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter("(?iu)" + jTextField1.getText()));
                    } catch (NullPointerException ex) {
                    }
                }
            }
        });

        tableUpdate();
    }

    public void tableUpdate() {
        bListModel = new DefaultTableModel(0, 0);
        bListFullModel.setRowCount(0);
        sql.tableFill("select ATBEZ Priznak, VALUE from BLACK_LIST", bListModel);

        jTable1.setModel(bListFullModel);
        jTable1.getTableHeader().setReorderingAllowed(false);

        for (int rowInd = 0; rowInd < bListModel.getRowCount(); rowInd++) {
            bListFullModel.addRow(new Object[]{});
            bListFullModel.setValueAt(false, rowInd, 0);
            for (int colInd = 0; colInd < bListModel.getColumnCount(); colInd++) {
                bListFullModel.setValueAt(bListModel.getValueAt(rowInd, colInd).toString(), rowInd, colInd + 1);
            }
        }

        for (int columnNo = 1; columnNo < jTable1.getColumnCount(); columnNo++) {
            jTable1.getColumnModel().getColumn(columnNo).setCellRenderer(new BlackList.Rend());
        }

        jTable1.getColumnModel().getColumn(0).setMaxWidth(25);
        jTable1.getColumnModel().getColumn(0).setHeaderValue("");
        jTable1.getColumnModel().getColumn(1).setHeaderValue("Признак");
        jTable1.getColumnModel().getColumn(2).setHeaderValue("Значение");
        JOverrideTable.packColumns(jTable1);
    }

    private void blackListEdit(String caption) {
        if (blackListDialog != null) {
            blackListDialog.dispose();
        }

        blackListDialog = new BlackListDialog(true, caption, this);

        blackListDialog.setVisible(true);
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
                if ((Boolean)jTable1.getValueAt(row, 0) == true) {
                    cell.setBackground(colRend.changeColor(cell, 1, colRend.COLOR_DESP_20));
                } else {
                    cell.setBackground(colRend.restoreColor(cell, 1));
                }
            } else {
                if ((Boolean)jTable1.getValueAt(row, 0) == true) {
                    cell.setBackground(colRend.changeColor(cell, 2, colRend.COLOR_DESP_20));
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
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

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
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Добавить");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Удалить");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Фильтр");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(0, 52, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        blackListEdit("Добавление в ЧС");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jTable1.getRowCount() != 0) {
            for (int rowInd = 0; rowInd < jTable1.getRowCount(); rowInd++) {
                if ((Boolean)jTable1.getValueAt(rowInd, 0) == true) {
                    sql.dataUpdate("delete from BLACK_LIST where ATBEZ = '" + jTable1.getValueAt(rowInd, 1).toString() + "' and VALUE = '" + jTable1.getValueAt(rowInd, 2).toString() + "'");
                    log.logWriting("удалил значение " + jTable1.getValueAt(rowInd, 2).toString() + " признака " + jTable1.getValueAt(rowInd, 1).toString() + " из черного списка");
                }
            }
            tableUpdate();
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
