/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms;

import baseclass.BInternalFrame;
import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import my.guisap.GuiStaticVariables;
import my.guisap.componenst.JOverrideTable;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author DanchenkoUA
 */
public class LogsForm extends BInternalFrame {
    
    PreparedStatement ps;
    DefaultTableModel logModel = new DefaultTableModel(0, 0) {
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex) {
            return false;
        }
    };
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(logModel);

    public LogsForm() {
        initComponents();
        setResizable(true);
        setMaximizable(true);
        setClosable(true);
        jTable1.setModel(logModel);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jDateChooser1.getJCalendar().setTodayButtonVisible(true);
        setDataListner();
        // jTable1.setRowSorter(sorter);
    }

    private void setDataListner() {
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                // If the 'date' property was changed...
                if ("date".equals(evt.getPropertyName())) {
                    JDateChooser aDateChooser = (JDateChooser) evt.getSource();
                    boolean isDateSelectedByUser = false;
                    // Get the otherwise unaccessible JDateChooser's 'dateSelected' field.
                    try {
                        // Get the desired field using reflection
                        Field dateSelectedField = JDateChooser.class.getDeclaredField("dateSelected");
                        // This line makes the value accesible (can be read and/or modified)
                        dateSelectedField.setAccessible(true);
                        isDateSelectedByUser = dateSelectedField.getBoolean(aDateChooser);
                    } catch (Exception ignoreOrNot) {
                        System.out.println("Что то с датой!!!");
                    }

                    // Do some important stuff depending on wether value was changed by user
                    if (isDateSelectedByUser) {
                        tableDateFill();
                    }

                    // Reset the value to false
                    try {
                        Field dateSelectedField = JDateChooser.class.getDeclaredField("dateSelected");
                        dateSelectedField.setAccessible(true);
                    } catch (Exception ignoreOrNot) {
                        System.out.println("Что то с датой!!!");
                    }
                }
            }
        });

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() { // фильтр
            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                } else {
                    jTable1.setRowSorter(sorter);
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter("(?iu)" + jTextField1.getText()));
                    } catch (NullPointerException ex) {
                    }
                }
            }
        });

    }

    private void tableDateFill() {
        jTable1.setRowSorter(null);
        String date = new SimpleDateFormat("yyyyMMdd").format(jDateChooser1.getDate());
        logModel.setRowCount(0);
        if (date.equals("")) {
        } else {
            try {
                ps = sql.preparingTableFill(SqlOperations.GET_LOGS_BY_DATE);
                if (ps != null) {
                    ps.setString(1, date);
                    sql.psTableFill(logModel, ps);
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        JOverrideTable.packColumns(jTable1);
    }

    private void tableAllFill() {
        jTable1.setRowSorter(null);
        logModel.setRowCount(0);
        try {
            ps = sql.preparingTableFill(SqlOperations.GET_LOGS_ALL);
            if (ps != null) {
                sql.psTableFill(logModel, ps);
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        JOverrideTable.packColumns(jTable1);
    }

    private void saveToFile() {
        try {
            File selectedfile = null;
            boolean flag = false;

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);// выбрать тип диалога Open или Save
            //chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Сохранить логи в файл");
            chooser.setApproveButtonText("Сохранить");//выбрать название для кнопки согласия
            FileFilter filter1 = new FileNameExtensionFilter("Текстовый файл", "txt");
            FileFilter filter2 = new FileNameExtensionFilter("Документ Word", "doc");
            //chooser.setFileFilter(filter);
            chooser.addChoosableFileFilter(filter1);
            chooser.addChoosableFileFilter(filter2);
            chooser.setFileFilter(filter1);
            int result = chooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedfile = chooser.getSelectedFile();
                if (!selectedfile.exists()) {
                    flag = true;
                }
            } else {
                return;
            }
            int ColC = jTable1.getColumnCount(); //Определяем кол-во столбцов
            int ItemC = jTable1.getRowCount();  //и элементов (строк)

            StringBuilder sb;
            FileWriter fw = null;
            if (flag == true) {
                if (chooser.getFileFilter() == filter1) {
                    fw = new FileWriter(selectedfile + ".txt");
                }
                if (chooser.getFileFilter() == filter2) {
                    fw = new FileWriter(selectedfile + ".doc");
                }
            } else {
                fw = new FileWriter(selectedfile);
            }
            fw.write("*********TIME*********//***COMPUTER***//****USER****//********Action******\r\n");

            for (int i = 0; i < ItemC; i++) {
                sb = new StringBuilder();
                for (int j = 0; j < ColC; j++) {
                    sb.append(jTable1.getValueAt(i, j));
                    if (j < ColC - 1) {
                        sb.append("   //   ");
                    }
                    if (j == ColC - 1) {
                        sb.append("\r\n");
                    }
                }
                fw.write(sb.toString()); //записывем собранную строку в файл
                sb = null;
            }
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(LogsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        jLabel1.setFont(GuiStaticVariables.globalFont);
        jLabel1.setText("Выберите дату:");

        jTable1.setFont(GuiStaticVariables.globalFont);
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

        jButton2.setText("Выход");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Сохранить в файл");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(GuiStaticVariables.globalFont);
        jLabel2.setText("Фильтр:");

        jButton4.setFont(GuiStaticVariables.globalFont);
        jButton4.setText("Загрузить все");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 121, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            this.setClosed(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(SettingsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        saveToFile();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        tableAllFill();
    }//GEN-LAST:event_jButton4ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
