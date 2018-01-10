/*
 * Основной класс для работы с колодками
 */
package my.guisap.forms.Last;

import baseclass.BInternalFrame;
import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import my.guisap.ColorRend;
import my.guisap.FormRegister;
import my.guisap.GuiStaticVariables;
import my.guisap.forms.LifeLineForm;
import my.guisap.forms.RejectForm;
import my.guisap.forms.SimpleTableForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.ImageUtils;
import my.guisap.utils.LifeLineUtils;

/**
 *
 * @author KiselevDA
 */
public class LastMain extends BInternalFrame {

    int indexColumn = 2;

    ColorRend colRend = new ColorRend();
    JPopupMenu popupmenuTbl2 = new JPopupMenu();
    JPopupMenu popupmenuTbl1 = new JPopupMenu();

    public LastMain() {
        fr = FormRegister.getInstance();
        initComponents();
        createPopMenu();
        setIcons();
        FillMainTable();
        FillRequestTable();
    }

    //функция для заполнения таблицы
    public void FillMainTable() {
        DefaultTableModel lastTable = new DefaultTableModel();
        String query = SqlOperations.LAST_LIST_TABLE + SqlOperations.LAST_ORDER_BY;

        if (jCheckBox2.isSelected()) {
            query = "select a.* from (" + query
                    + " ) a INNER JOIN LAST_LIFE_LINE b ON a.\"Индекс колодки\"=b.id where LIFE_LINE like 'FL/________________/" + my.guisap.utils.SecurityManager.userName + ";%' ORDER BY a.\"Дата создания\" DESC";
        }

        sql.tableFill(query, lastTable);
        jTable2.setModel(lastTable);
    }

    public void FillRequestTable() {
        DefaultTableModel requestTable = new DefaultTableModel();
        String query = "";
        switch (jComboBox2.getSelectedIndex()) {
            case 0:
                query = SqlOperations.LAST_REQUEST + " WHERE STATUS ='OP'" + SqlOperations.LAST_ORDER_BY;
                break;
            case 1:
                query = SqlOperations.LAST_REQUEST + " WHERE STATUS ='CL'" + SqlOperations.LAST_ORDER_BY;
                break;
            case 2:
                query = SqlOperations.LAST_REQUEST + SqlOperations.LAST_ORDER_BY;
                break;
        }
        sql.tableFill(query, requestTable);
        jTable1.setModel(requestTable);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = ComponentsUtils.createBtn("Редактировать колодку",160,25,true);
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = TableRowFilterSupport
        .forTable(new JTable() {
            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                return false;
            }
        })
        .searchable(true)
        .actions(true)
        .apply();
        jButton2 = ComponentsUtils.createBtn("Редактировать колодку",120,25,true);
        jButton8 = ComponentsUtils.createBtn("",25,25,true);
        jButton5 = ComponentsUtils.createBtn("",190,25,true);
        jCheckBox2 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = TableRowFilterSupport
        .forTable(new JTable() {
            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                return false;
            }
        })
        .searchable(true)
        .actions(true)
        .apply();
        jButton9 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setTitle("Работа с колодками");
        setToolTipText("");
        setNormalBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setVisible(true);

        jButton1.setText("Редактировать колодку");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable2.setFont(GuiStaticVariables.globalFont);
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.setCellSelectionEnabled(true);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable2MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        jTable2.setAutoCreateRowSorter(true);

        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);

        jTable2.setCellSelectionEnabled(true);

        jTable2.setOpaque(false);

        jTable2.setUpdateSelectionOnSort(false);

        jButton2.setFont(GuiStaticVariables.globalFont);
        jButton2.setText("Добавить");
        jButton2.setPreferredSize(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton8.setOpaque(false);
        jButton8.setPreferredSize(new java.awt.Dimension(33, 25));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton5.setFont(GuiStaticVariables.globalFont);
        jButton5.setText("Добавить на основе существующей");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jCheckBox2.setFont(GuiStaticVariables.globalFont);
        jCheckBox2.setText("Только мои записи");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 986, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5)))
                    .addComponent(jCheckBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Колодки", jPanel1);

        jButton3.setFont(GuiStaticVariables.globalFont);
        jButton3.setText("Обработать заявку");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(GuiStaticVariables.globalFont);
        jButton4.setText("Отклонить");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.setAutoCreateRowSorter(true);

        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);

        jTable1.setCellSelectionEnabled(true);

        jTable1.setOpaque(false);

        jTable1.setUpdateSelectionOnSort(false);

        jButton9.setOpaque(false);
        jButton9.setPreferredSize(new java.awt.Dimension(33, 25));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jComboBox2.setFont(GuiStaticVariables.globalFont);
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Открытые", "Закрытые", "Все" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 986, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Заявки", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String indexLast = jTable2.getValueAt(jTable2.getSelectedRow(), indexColumn).toString();
            fr.openForm(new LastEdit("Колодка ", this, indexLast, true), FormRegister.ONE_KEY_FORM);
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Выберите колодку для редактирования", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        fr.openForm(new LastEdit("Колодка ", this), FormRegister.ONE_KEY_FORM);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getClickCount() == 2) {
            switch (jTable2.getSelectedColumn()) {
                case 11:
                    fr.openForm(new LifeLineForm(LifeLineUtils.LAST_LIFE_LINE, jTable2.getValueAt(jTable2.getSelectedRow(), indexColumn).toString()), FormRegister.SOME_KEY_FORM);
                    break;
                default:
                    jButton1ActionPerformed(null);
                    break;
            }
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            fr.openForm(new LastEdit("Обработка заявки: " + jTable1.getValueAt(jTable1.getSelectedRow(), 0), this, null, jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()), FormRegister.ONE_KEY_FORM);
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Выберите запись", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        FillMainTable();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        FillRequestTable();
    }//GEN-LAST:event_jButton9ActionPerformed

    //кнопка отклонить заявку
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            Thread saveInfo = new Thread(new Runnable() {
                @Override
                public void run() //Этот метод будет выполняться в побочном потоке
                {
                    if (sql.checkInfo("SELECT * FROM REJECT_LIST WHERE OBJECT='" + jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString() + "'")) {
                        sql.SendQuery("update GUI_SAP.MKZ_LIST_HEAD set status = 'R' where MODEL = '" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "'");
                        sql.SendQuery("update LAST_REQUEST set status = 'CL' where MODEL = '" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "'");
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                LifeLineUtils.UpdateLifeLine(LifeLineUtils.MKZ_LIFE_LINE, jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString(), "R");
                                FillRequestTable();
                            }
                        });
                    }
                }
            });
            fr.openForm(new RejectForm(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString(), "Заявка на колодку", saveInfo), FormRegister.SOME_KEY_FORM);
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Выберите запись", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            jButton3ActionPerformed(null);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            String indexLast = jTable2.getValueAt(jTable2.getSelectedRow(), indexColumn).toString();
            fr.openForm(new LastEdit("Колодка ", this, indexLast, false), FormRegister.ONE_KEY_FORM);
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Выберите колодку на основе которой создать новую", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTable2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MousePressed
        if (evt.getButton() == MouseEvent.BUTTON3) {
            int column = jTable2.columnAtPoint(evt.getPoint());
            int row = jTable2.rowAtPoint(evt.getPoint());
            jTable2.setColumnSelectionInterval(column, column);
            jTable2.setRowSelectionInterval(row, row);
            popupmenuTbl2.show(jTable2, evt.getX(), evt.getY());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2MousePressed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        if (evt.getButton() == MouseEvent.BUTTON3) {
            int column = jTable1.columnAtPoint(evt.getPoint());
            int row = jTable1.rowAtPoint(evt.getPoint());
            jTable1.setColumnSelectionInterval(column, column);
            jTable1.setRowSelectionInterval(row, row);
            popupmenuTbl1.show(jTable1, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTable1MousePressed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        FillRequestTable();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        FillMainTable();
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void setIcons() {
        jButton8.setIcon(ImageUtils.createIconForButton("images/update.png"));
        jButton9.setIcon(ImageUtils.createIconForButton("images/update.png"));
    }

    private void createPopMenu() {
        JMenuItem opModel = new JMenuItem("Открыть колодку");
        opModel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1ActionPerformed(null);
            }
        });

        JMenuItem opListModel = new JMenuItem("Открыть список моделей на этой колодке");
        opListModel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.openForm(new SimpleTableForm("Модели на основе колодки: " + jTable2.getValueAt(jTable2.getSelectedRow(), indexColumn).toString(),
                        SqlOperations.MKZ_LIST + " where CODE_LAST='" + jTable2.getValueAt(jTable2.getSelectedRow(), indexColumn).toString() + "'"), FormRegister.SOME_KEY_FORM);
            }
        });

        JMenuItem opLifeLineLast = new JMenuItem("Открыть жизненный цикл");
        opLifeLineLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.openForm(new LifeLineForm(LifeLineUtils.LAST_LIFE_LINE, jTable2.getValueAt(jTable2.getSelectedRow(), indexColumn).toString()), FormRegister.SOME_KEY_FORM);
            }
        });

        JMenuItem updateMain = new JMenuItem("Обновить");
        updateMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FillMainTable();
            }
        });
        popupmenuTbl2.add(opModel);
        popupmenuTbl2.add(opListModel);
        popupmenuTbl2.add(opLifeLineLast);
        popupmenuTbl2.add(updateMain);
        jTable2.add(popupmenuTbl2);

        JMenuItem reject = new JMenuItem("Отклонить заявку");
        reject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton4ActionPerformed(null);
            }
        });
        JMenuItem workOn = new JMenuItem("Обработать заявку");
        workOn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton3ActionPerformed(null);
            }
        });
        JMenuItem opLifeLineMKZ = new JMenuItem("Открыть жизненный цикл");
        opLifeLineMKZ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.openForm(new LifeLineForm(LifeLineUtils.MKZ_LIFE_LINE, jTable1.getValueAt(jTable1.getSelectedRow(), indexColumn).toString()), FormRegister.SOME_KEY_FORM);
            }
        });
        JMenuItem updateRequest = new JMenuItem("Обновить");
        updateRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FillRequestTable();
            }
        });

        popupmenuTbl1.add(reject);
        popupmenuTbl1.add(workOn);
        popupmenuTbl1.add(opLifeLineMKZ);
        popupmenuTbl1.add(updateRequest);
        jTable1.add(popupmenuTbl1);

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
