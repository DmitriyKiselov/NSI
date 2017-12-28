/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap;

import my.guisap.utils.LogClass;
import my.guisap.componenst.JOverrideTable;
import baseclass.BInternalFrame;
import java.util.Comparator;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author DaN
 */
public class EditClassForm extends BInternalFrame {

    DefaultTableModel testModel = new DefaultTableModel(0, 0);
    SqlOperations sql = new SqlOperations();
    JTextField tf = new JTextField();
    LogClass log;
    DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex) {
            try {
                if (colIndex == 1 || colIndex == 0) {
                    return false;
                }
                if ((Integer.parseInt(tableModel.getValueAt(rowIndex, 4).toString())) == 1) {
                    return false;
                } else {
                    return true;
                }
            } catch (NullPointerException e) {
                return true;
            }
        }
    };

    public EditClassForm() {

        log = LogClass.getInstance();
        this.testModel = tableModel;
        sql.tableFill(SqlOperations.CLASS_EDIT_SELECTION, testModel);
        setClosable(true);
        setMaximizable(false);
        initComponents();
        getSettingsModel();
    }

    private void getSettingsModel() {
        jTable1.setModel(testModel);
        jTable1.getTableHeader().setReorderingAllowed(false);
        for (int rowIndex = 0; rowIndex < testModel.getRowCount(); rowIndex++) {
            if (testModel.getValueAt(rowIndex, 4) == null) {
                testModel.setValueAt("0", rowIndex, 4);
            }
            if (testModel.getValueAt(rowIndex, 3) == null) {
                testModel.setValueAt("0", rowIndex, 3);
            }
        }
        jTable1.removeColumn(jTable1.getColumnModel().getColumn(5));
        jTable1.removeColumn(jTable1.getColumnModel().getColumn(4));
        jTable1.getColumnModel().getColumn(2).setCellEditor(new ComboBoxEditor(new String[]{"NEW_MOD","MOD", "MOD_TEXN", "ART", "FASON", "null"}));
        jTable1.getColumnModel().getColumn(3).setCellEditor(new textBoxEditor());

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(testModel) {
            @Override
            public Comparator<?> getComparator(int column) {
                if (column == 3) {
                    return new Comparator<String>() {
                        @Override
                        public int compare(String s1, String s2) {
                            return Integer.parseInt(s1) - Integer.parseInt(s2);
                        }
                    };
                }
                return super.getComparator(column);
            }
        };
        jTable1.setRowSorter(sorter);

        tf.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char a = e.getKeyChar();
                if (!Character.isDigit(a) && (a != '\b')) {
                    e.consume();
                }
            }
        });
        JOverrideTable.packColumns(jTable1);
    }

    public class ComboBoxEditor extends DefaultCellEditor {

        public ComboBoxEditor(String[] items) {
            super(new JComboBox<>(items));
        }
    }

    public class textBoxEditor extends DefaultCellEditor {

        public textBoxEditor() {
            super(tf);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setTitle("Редактирование класса");

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
        jTable1.setRowHeight(22);
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Сохранить");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        sql.dataUpdate(SqlOperations.BLOCK_PR_DELETE);
        for (int rowIndex = 0; rowIndex < testModel.getRowCount(); rowIndex++) {
            sql.dataUpdate(SqlOperations.BLOCK_PR_SAVE + "('" + testModel.getValueAt(rowIndex, 5) + "','" + testModel.getValueAt(rowIndex, 2) + "'," + testModel.getValueAt(rowIndex, 4) + "," + testModel.getValueAt(rowIndex, 3) + ",'" + testModel.getValueAt(rowIndex, 0) + "')");
        }
        log.logWriting("отредактировал настройки классов");
        JOptionPane.showMessageDialog(this, "Сохранение прошло успешно!", "Сохранение", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
