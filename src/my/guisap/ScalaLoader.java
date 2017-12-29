/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap;

import my.guisap.utils.LogClass;
import my.guisap.componenst.JOverrideTable;
import baseclass.BInternalFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import my.guisap.sql.ConnectionClass;
import my.guisap.sql.SqlOperations;

public class ScalaLoader extends BInternalFrame {

    LogClass log;
    DefaultTableModel attrModel = new DefaultTableModel();
    ColorRend colRend = new ColorRend();
    String art = "";
    ArrayList<Integer> rowArr = new ArrayList<>();
    public static String attrName = "";
    public static String attrCode = "";
    SqlOperations sql = new SqlOperations();
    GuideForm gf;
    int BUTTON_COLUMN_NUM = 5;
    int SAP_NAME_COLUMN_NUM = 3;
    int EXIST_COLUMN_NUM = 4;
    DefaultTableModel sizeModel = new DefaultTableModel(0, 0);
    DefaultTableModel scalaModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex) {
            try {
                if (colIndex == BUTTON_COLUMN_NUM && rowArr.contains(rowIndex)) {
                    jTable1.getColumnModel().getColumn(BUTTON_COLUMN_NUM).setCellEditor(new ButtonEditor(jTable1));
                    return true;
                }
                return false;
            } catch (NullPointerException e) {
                System.err.println(e);
                return false;
            }
        }
    };

    public ScalaLoader() {
        log = LogClass.getInstance();
        setClosable(true);
        setMaximizable(true);
        initComponents();

        jTable1.setModel(scalaModel);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    tableUpdate();
                }
            }
        });
        JOverrideTable.packColumns(jTable1);
    }

    class ButtonRenderer implements TableCellRenderer {

        JButton button = new JButton(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Edit16.gif")));

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (column == BUTTON_COLUMN_NUM && rowArr.contains(row)) {
                button.setVisible(true);
                return button;
            } else {
                button.setVisible(false);
                return new JLabel("");
            }
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

        JTable table;
        JButton button = new JButton(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Edit16.gif")));
        int clickCountToStart = 1;

        public ButtonEditor(JTable table) {
            this.table = table;
            button.addActionListener(this);
        }
        //-------------событие нажатия кнопки цвета---------------------------

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.getEditingRow();
            gf = new GuideForm(true, "", sql.strFill("select distinct ATPRT from CLASS_PR where ATNAM = '" + scalaModel.getValueAt(row, 0).toString() + "'"), sql.strFill("select distinct ATINN from CLASS_PR where ATNAM = '" + scalaModel.getValueAt(row, 0).toString() + "'"), jComboBox1.getSelectedItem().toString(), scalaModel.getValueAt(row, 1).toString());
            if (GuideForm.selectValue == false) {
                stopCellEditing();
                return;
            }
            scalaModel.setValueAt(attrName, row, SAP_NAME_COLUMN_NUM);
            stopCellEditing();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }

        @Override
        public boolean isCellEditable(EventObject anEvent) {
            if (anEvent instanceof MouseEvent) {
                return ((MouseEvent) anEvent).getClickCount() >= clickCountToStart;
            }
            return true;
        }

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
            return true;
        }

        @Override
        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }

        @Override
        public void cancelCellEditing() {
            super.cancelCellEditing();
        }
    }

    private class Rend extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!isSelected) {
                if (table.getValueAt(row, column) == null) {
                    cell.setBackground(colRend.changeColor(cell, 1, Color.red));
                } else {
                    if (table.getValueAt(row, EXIST_COLUMN_NUM).equals("1")) {
                        cell.setBackground(colRend.changeColor(cell, 1, Color.green));
                    } else {
                        cell.setBackground(colRend.restoreColor(cell, 1));
                    }
                }
            } else {
                if (table.getValueAt(row, column) == null) {
                    cell.setBackground(colRend.changeColor(cell, 2, new Color(205, 0, 0)));
                } else {
                    if (table.getValueAt(row, EXIST_COLUMN_NUM).equals("1")) {
                        cell.setBackground(colRend.changeColor(cell, 2, new Color(0, 205, 0)));
                    } else {
                        cell.setBackground(colRend.restoreColor(cell, 2));
                    }
                }
            }
            return cell;
        }
    }

    private void tableUpdate() {
        try {
            scalaModel.setRowCount(0);
            scalaModel.setColumnCount(0);
            CallableStatement cs;
            cs = ConnectionClass.CONNECT_BUF.prepareCall(SqlOperations.GET_SCALA_DATA);
            cs.setString(1, jTextField1.getText());
            sql.csTableFill(scalaModel, cs);
            cs.close();
        } catch (SQLException ex) {
        }
        rowArr.clear();
        for (int ind = 0; ind < scalaModel.getRowCount(); ind++) {
            if (scalaModel.getValueAt(ind, SAP_NAME_COLUMN_NUM) == null) {
                rowArr.add(ind);
            }
        }
        scalaModel.addColumn("");
        jTable1.getColumnModel().getColumn(BUTTON_COLUMN_NUM).setMaxWidth(50);
        jTable1.getColumnModel().getColumn(BUTTON_COLUMN_NUM).setCellRenderer(new ButtonRenderer());
        jTable1.getColumnModel().getColumn(SAP_NAME_COLUMN_NUM).setCellRenderer(new Rend());
        art = jTextField1.getText();
    }

    private void attrSaving(int element, String attParam_1, String saveQuery, String attrQuery, boolean updateFlag) {
        attrModel = new DefaultTableModel(0, 0);
        try {
            PreparedStatement ps = sql.preparingTableFill(attrQuery);
            if (ps != null) {
                ps.setString(1, attParam_1);
                ps.setString(2, jComboBox1.getSelectedItem().toString());
                sql.psTableFill(attrModel, ps);
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        for (int attrInd = 0; attrInd < attrModel.getRowCount(); attrInd++) {
            for (int scalaInd = 0; scalaInd < scalaModel.getRowCount(); scalaInd++) {
                if (attrModel.getValueAt(attrInd, 1).toString().equals(jTable1.getValueAt(scalaInd, 0))) {
                    if (!updateFlag) {
                        sql.dataUpdate(saveQuery + "('" + element + "','" + attrModel.getValueAt(attrInd, 1) + "','" + attrModel.getValueAt(attrInd, 0) + "','" + jTable1.getValueAt(scalaInd, SAP_NAME_COLUMN_NUM) + "')");
                    } else {
                        if (saveQuery.equals(SqlOperations.BLOCK_FASON_PR_SAVE)) {
                            sql.dataUpdate("update BLOCK_FASON_PR set VALUE = '" + jTable1.getValueAt(scalaInd, SAP_NAME_COLUMN_NUM) + "' where RELIDKEY = " + element + " and ATNAM = '" + jTable1.getValueAt(scalaInd, 0) + "'");
                        }
                        if (saveQuery.equals(SqlOperations.BLOCK_MODEL_PR_SAVE)) {
                            sql.dataUpdate("update BLOCK_MODEL_PR set VALUE = '" + jTable1.getValueAt(scalaInd, SAP_NAME_COLUMN_NUM) + "' where RELIDKEY = " + element + " and ATNAM = '" + jTable1.getValueAt(scalaInd, 0) + "'");
                        }
                        if (saveQuery.equals(SqlOperations.BLOCK_ARTICLE_PR_SAVE)) {
                            sql.dataUpdate("update BLOCK_ARTICLE_PR set VALUE = '" + jTable1.getValueAt(scalaInd, SAP_NAME_COLUMN_NUM) + "' where RELIDKEY = " + element + " and ATNAM = '" + jTable1.getValueAt(scalaInd, 0) + "'");
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

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

        jButton1.setText("Загрузить");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SALE_BW", "SALE_HW1", "SALE_SHOES" }));

        jButton2.setText("Сохранить");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Артикул");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tableUpdate();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Object[] options = {"Да", "Нет"};
        int countFlag = 0;
        for (int ind = 0; ind < rowArr.size(); ind++) {
            if (jTable1.getValueAt(rowArr.get(ind), SAP_NAME_COLUMN_NUM) == null) {
                JOptionPane.showMessageDialog(null, "Ошибка импорта данных! Не заполнены все признаки!");
                return;
            }
        }
        if (scalaModel.getRowCount() != 0) {
            int artID = 0, modelID = 0, fasonID = 0;
            String article = "", model = "", fason = "";
            for (int rowInd = 0; rowInd < jTable1.getRowCount(); rowInd++) {
                if (jTable1.getValueAt(rowInd, 0).equals("CODE_LAST")) {
                    fason = jTable1.getValueAt(rowInd, SAP_NAME_COLUMN_NUM).toString();
                }
                if (jTable1.getValueAt(rowInd, 0).equals("MODEL")) {
                    model = jTable1.getValueAt(rowInd, SAP_NAME_COLUMN_NUM).toString();
                }
                if (jTable1.getValueAt(rowInd, 0).equals("ART")) {
                    article = jTable1.getValueAt(rowInd, SAP_NAME_COLUMN_NUM).toString();
                }
            }
            try {
                artID = Integer.parseInt(sql.strFill("select distinct IDKEY from BLOCK_ARTICLE where NAME = '" + article + "'"));
            } catch (NumberFormatException exc) {
            }
            try {
                modelID = Integer.parseInt(sql.strFill("select distinct a.IDKEY from BLOCK_MODEL a where a.NAME = '" + model + "'"));
            } catch (NumberFormatException exc) {
            }
            try {
                fasonID = Integer.parseInt(sql.strFill("select distinct a.IDKEY from BLOCK_FASON a where a.NAME = '" + fason + "'"));
            } catch (NumberFormatException exc) {
            }

            if (modelID == 0 && artID != 0) {
                JOptionPane.showMessageDialog(null, "Ошибка импорта данных! Артикул - " + article + " привязан к другой модели.");
                return;
            }

            if (fasonID == 0 && modelID != 0) {
                JOptionPane.showMessageDialog(null, "Ошибка импорта данных! Модель - " + model + " привязана к другому фасону.");
                return;
            }

            countFlag = 0;

            if (fasonID == 0) {
                countFlag++;
                fasonID = sql.dataUpdate(SqlOperations.BLOCK_FASON_SAVE + "('" + fason + "', '" + fason + "','" + jComboBox1.getSelectedItem().toString() + "') " + SqlOperations.BLOCK_SAVE_SCOPE);

                attrSaving(fasonID, "FASON", SqlOperations.BLOCK_FASON_PR_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR, false);
            } else {
//                int answer = JOptionPane.showOptionDialog(null, "Фасон - " + fason + " уже заведен в базе! Изменить его признаки?", "Предупреждение", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
//                if (answer == 0) {
//                    attrSaving(fasonID, "FASON", SqlOperations.BLOCK_FASON_PR_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR, true);
//                    log.logWriting("импортировал признаки фасона по артикулу " + art + ".");
//                }
            }
            if (modelID == 0) {
                countFlag++;
                modelID = sql.dataUpdate(SqlOperations.BLOCK_MODEL_SAVE + "('" + model + "', '" + model + "','" + fasonID + "','" + jComboBox1.getSelectedItem().toString() + "') " + SqlOperations.BLOCK_SAVE_SCOPE);

                attrSaving(modelID, "MOD", SqlOperations.BLOCK_MODEL_PR_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR, false);
                sql.tableFill("select RR from RKV_SCAL..rostov2 a inner join RKV_SCAL..rostov3 b on a.NOMER = b.NOMER where b.MOD = '" + model + "' group by RR order by RR", sizeModel);
                if (sizeModel.getRowCount() != 0) {
                    for (int rowInd = 0; rowInd < sizeModel.getRowCount(); rowInd++) {
                        sql.dataUpdate("insert into MODEL_SIZE values (" + modelID + ",'" + sizeModel.getValueAt(rowInd, 0) + "','" + sizeModel.getValueAt(rowInd, 0) + "')");
                    }
                }
            } else {
//                int answer = JOptionPane.showOptionDialog(null, "Модель - " + model + " уже заведена в базе! Изменить ее признаки?", "Предупреждение", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
//                if (answer == 0) {
//                    attrSaving(modelID, "MOD", SqlOperations.BLOCK_MODEL_PR_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR, true);
//                    log.logWriting("импортировал признаки модели по артикулу " + art + ".");
//                }
            }
            if (artID == 0) {
                countFlag++;
                artID = sql.dataUpdate(SqlOperations.BLOCK_ARTICLE_SAVE + "('" + article + "', '" + article + "','" + modelID + "','" + jComboBox1.getSelectedItem().toString() + "') " + SqlOperations.BLOCK_SAVE_SCOPE);

                attrSaving(artID, "ART", SqlOperations.BLOCK_ARTICLE_PR_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR, false);
            } else {
//                int answer = JOptionPane.showOptionDialog(null, "Артикул - " + article + " уже заведен в базе! Изменить его признаки?", "Предупреждение", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
//                if (answer == 0) {
//                    attrSaving(artID, "ART", SqlOperations.BLOCK_ARTICLE_PR_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR, true);
//                    log.logWriting("импортировал признаки артикула " + art + ".");
//                }
            }
        } else {
            return;
        }
        if (countFlag != 0) {
            log.logWriting("импортировал данные по артикулу " + art + ".");
            JOptionPane.showMessageDialog(null, "Импорт данных завершен!");
        } else {
            JOptionPane.showMessageDialog(null, "Артикул уже заведен в базе! Импорт невозможен!");
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
