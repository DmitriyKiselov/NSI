/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms.TableManager;

import baseclass.BInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import my.guisap.FormRegister;

/**
 *
 * @author KiselevDA
 */
public class LocalTablesManager extends BInternalFrame {

    DefaultTableModel listTables = new DefaultTableModel();

    public LocalTablesManager() {
        setTitle("Администрирование локальных справочников");
        sql.tableFill("select ATBEZ,ATPRT from CLASS_PR where ATPRT like '%\\_L' escape '\\' and ATPRT not like 'Z_TYPE_SHOE_L' group by ATBEZ,ATPRT", listTables);
        initComponents();
        setCenter();
    }

    public void updateTable() {
        DefaultTableModel tmp = new DefaultTableModel();
        sql.tableFill("select CODE \"Код\", NAME \"Наименование\" from SAPX_" + listTables.getValueAt(jComboBox1.getSelectedIndex(), 1).toString(), tmp);
        jTable1.setModel(tmp);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }};
            jButton1 = new javax.swing.JButton();
            jButton2 = new javax.swing.JButton();

            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

            if (listTables.getRowCount() > 0){
                for (int i=0;i<listTables.getRowCount();i++)   {
                    jComboBox1.addItem(listTables.getValueAt(i, 0).toString());
                }
            }
            jComboBox1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jComboBox1ActionPerformed(evt);
                }
            });

            jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Код", "Наименование"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }
            });
            jScrollPane1.setViewportView(jTable1);
            jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);

            jTable1.setCellSelectionEnabled(true);

            jTable1.setOpaque(false);

            jTable1.setUpdateSelectionOnSort(false);

            updateTable();

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

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jButton2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1)
                    .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                    .addGap(93, 93, 93)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(97, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2))
                    .addGap(10, 10, 10))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        DefaultTableModel tmp = new DefaultTableModel();
        sql.tableFill("select CODE \"Код\", NAME \"Наименование\" from SAPX_" + listTables.getValueAt(jComboBox1.getSelectedIndex(), 1).toString(), tmp);
        jTable1.setModel(tmp);
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            String code = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
            sql.SendQuery("delete from SAPX_" + listTables.getValueAt(jComboBox1.getSelectedIndex(), 1).toString() + " where code='" + code + "'");
            log.logWriting("Удалена запись " + jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString() + " из таблицы SAPX_" + listTables.getValueAt(jComboBox1.getSelectedIndex(), 1).toString());
            ((DefaultTableModel) jTable1.getModel()).removeRow(jTable1.getSelectedRow());
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Выберите модель для редактирования", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        fr.openForm(new AddToLocalTable("Добавить в таблицу '" + jComboBox1.getSelectedItem().toString() + "'", "add", true, true, listTables.getValueAt(jComboBox1.getSelectedIndex(), 1).toString(), this), FormRegister.SOME_KEY_FORM);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
