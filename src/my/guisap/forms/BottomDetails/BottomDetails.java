package my.guisap.forms.BottomDetails;

import baseclass.BInternalFrame;
import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import my.guisap.FormRegister;
import my.guisap.GuiStaticVariables;
import my.guisap.componenst.MyImageCellRenderer;
import my.guisap.forms.SingleImageForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.CacheImage;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.ImageUtils;

/**
 *
 * @author KiselevDA
 */
public class BottomDetails extends BInternalFrame {

    TableRowSorter<DefaultTableModel> sorterSole;
    TableRowSorter<DefaultTableModel> sorterHeel;
    TableRowSorter<DefaultTableModel> sorterInsole;

    int pictureColumn = 0;
    int indexColumn = 2;

    public BottomDetails() {
        needSaveSize = true;
        setTitle("Делати низа");
        fr = FormRegister.getInstance();
        initComponents();
        setIcons();
        fillTableFull();

    }

    private void fillTableFull() {
        fillTableSole();
        fillTableHeel();
        fillTableInSole();
    }

    public void fillTableSole() {
        DefaultTableModel tmpModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return ImageIcon.class;
                }
                return super.getColumnClass(column);
            }
        };
        sql.tableFill(SqlOperations.SOLE_FULL_TABLE, tmpModel);

        jTable1.setModel(tmpModel);
        sorterSole = new TableRowSorter<>(tmpModel);

        Thread t = new Thread() {
            @Override
            public void run() {
                CacheImage.loadImageToTable(CacheImage.TYPE_SOLE, CacheImage.cacheSole, jTable1, 1, pictureColumn);
            }
        };
        t.start();

        jTable1.setDefaultRenderer(jTable1.getColumnClass(0),
                new MyImageCellRenderer());
        jTable1.setRowHeight(60);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
    }

    public void fillTableHeel() {
        DefaultTableModel tmpModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return ImageIcon.class;
                }
                return super.getColumnClass(column);
            }
        };
        sql.tableFill(SqlOperations.HEEL_FULL_TABLE, tmpModel);
        jTable2.setModel(tmpModel);
        sorterHeel = new TableRowSorter<>(tmpModel);

        Thread t = new Thread() {
            @Override
            public void run() {
                CacheImage.loadImageToTable(CacheImage.TYPE_HEEL, CacheImage.cacheHeel, jTable2, 1, pictureColumn);
            }
        };
        t.start();

        jTable2.setDefaultRenderer(jTable2.getColumnClass(0),
                new MyImageCellRenderer());
        jTable2.setRowHeight(60);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(60);
    }

    public void fillTableInSole() {
        DefaultTableModel tmpModel = new DefaultTableModel();
        sql.tableFill(SqlOperations.INSOLE_FULL, tmpModel);
        jTable3.setModel(tmpModel);
        sorterInsole = new TableRowSorter<>(tmpModel);
    }

    private void setIcons() {
        jButton15.setIcon(ImageUtils.createIconForButton("images/update.png"));
        jButton16.setIcon(ImageUtils.createIconForButton("images/update.png"));
        jButton17.setIcon(ImageUtils.createIconForButton("images/update.png"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane(jTable1,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jTable1 = TableRowFilterSupport
        .forTable(new JTable(){
            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                return false;
            };
        })
        .searchable(true)
        .actions(true)
        .apply();
        jButton1 = ComponentsUtils.createBtn("Добавить",140,25,true);
        jButton2 = ComponentsUtils.createBtn("Удалить",140,25,true);
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton17 = ComponentsUtils.createBtn("",25,25,true);
        jButton3 = ComponentsUtils.createBtn("Добавить на основе",240,25,true);
        jButton4 = ComponentsUtils.createBtn("Анализ фасона",200,25,true);
        jPanel2 = new javax.swing.JPanel();
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
        jButton7 = ComponentsUtils.createBtn("Добавить",140,25,true);
        jButton9 = ComponentsUtils.createBtn("Удалить",140,25,true);
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton16 = ComponentsUtils.createBtn("",25,25,true);
        jButton5 = ComponentsUtils.createBtn("Анализ фасона",200,25,true);
        jButton6 = ComponentsUtils.createBtn("Добавить на основе",240,25,true);
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = TableRowFilterSupport
        .forTable(new JTable() {
            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                return false;
            }
        })
        .searchable(true)
        .actions(true)
        .apply();
        jButton10 = ComponentsUtils.createBtn("Удалить",140,25,true);
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton12 = ComponentsUtils.createBtn("Добавить",140,25,true);
        jButton15 = ComponentsUtils.createBtn("",25,25,true);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setCellSelectionEnabled(true);
        jTable1.setOpaque(false);
        jTable1.setUpdateSelectionOnSort(false);

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

        jTextField1.setFont(GuiStaticVariables.globalFont);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                jTable1.setRowSorter(sorterInsole);
                try {
                    sorterInsole.setRowFilter(RowFilter.regexFilter("(?iu)" + jTextField1.getText()));
                } catch (NullPointerException ex) {
                }
                try {
                    jTable1.changeSelection(0, 1, true, true);
                    jTable1.setRowSelectionInterval(0, 0);
                } catch (IllegalArgumentException ex) {
                }
            }
        });

        jLabel1.setText("Поиск:");

        jButton17.setOpaque(false);
        jButton17.setPreferredSize(new java.awt.Dimension(33, 25));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton3.setText("Добавить на основе");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Анализ фасона");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(45, 45, 45)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jButton3)
                        .addComponent(jButton4))
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Подошва", jPanel1);

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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        jTable2.setAutoCreateRowSorter(true);
        //jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTable2.setCellSelectionEnabled(true);
        jTable2.setOpaque(false);
        jTable2.setUpdateSelectionOnSort(false);

        jButton7.setText("Добавить");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton9.setText("Удалить");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jTextField3.setFont(GuiStaticVariables.globalFont);

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                jTable2.setRowSorter(sorterHeel);
                try {
                    sorterHeel.setRowFilter(RowFilter.regexFilter("(?iu)" + jTextField3.getText()));
                } catch (NullPointerException ex) {
                }
                try {
                    jTable2.changeSelection(0, 1, true, true);
                    jTable2.setRowSelectionInterval(0, 0);
                } catch (IllegalArgumentException ex) {
                }
            }
        });

        jLabel3.setText("Поиск:");

        jButton16.setOpaque(false);
        jButton16.setPreferredSize(new java.awt.Dimension(33, 25));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton5.setText("Анализ фасона");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Добавить на основе");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(10, 10, 10)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addGap(45, 45, 45)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6)
                            .addComponent(jButton5))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7)
                            .addComponent(jButton9)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Каблук", jPanel2);

        jTable3.setFont(GuiStaticVariables.globalFont);
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable3);
        jTable3.setAutoCreateRowSorter(true);
        jTable3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTable3.setCellSelectionEnabled(true);
        jTable3.setOpaque(false);
        jTable3.setUpdateSelectionOnSort(false);

        jButton10.setText("Удалить");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jTextField4.setFont(GuiStaticVariables.globalFont);

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                jTable3.setRowSorter(sorterInsole);
                try {
                    sorterInsole.setRowFilter(RowFilter.regexFilter("(?iu)" + jTextField4.getText()));
                } catch (NullPointerException ex) {
                }
                try {
                    jTable3.changeSelection(0, 1, true, true);
                    jTable3.setRowSelectionInterval(0, 0);
                } catch (IllegalArgumentException ex) {
                }
            }
        });

        jLabel4.setText("Поиск:");

        jButton12.setText("Добавить");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton15.setOpaque(false);
        jButton15.setPreferredSize(new java.awt.Dimension(33, 25));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(10, 10, 10)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton12)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton12)
                        .addComponent(jButton10)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Основная стелька", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        fillTableSole();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        fillTableHeel();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        fillTableInSole();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        sql.SendQuery("DELETE FROM LB_SOLE WHERE ART='" + jTable1.getValueAt(jTable1.getSelectedRow(), indexColumn) + "'");
        fillTableSole();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        sql.SendQuery("DELETE FROM LB_HEEL WHERE ID='" + jTable2.getValueAt(jTable2.getSelectedRow(), indexColumn) + "'");
        fillTableHeel();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        sql.SendQuery("DELETE FROM LB_BASIC_INSOLE WHERE ID='" + jTable3.getValueAt(jTable3.getSelectedRow(), indexColumn) + "'");
        fillTableInSole();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        fr.openForm(new AddSoleForm("Подошва", true, this), FormRegister.SOME_KEY_FORM);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        fr.openForm(new AddHeelForm("Каблук", true, this), FormRegister.SOME_KEY_FORM);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        fr.openForm(new AddInsoleForm("Основная стелька", true, this), FormRegister.SOME_KEY_FORM);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        fr.openForm(new AddSoleForm("Подошва", true, this, jTable1.getValueAt(jTable1.getSelectedRow(), indexColumn).toString(), true), FormRegister.SOME_KEY_FORM);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        fr.openForm(new AnalysisBTForm("Выборка моделей на фасоне: " + jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString(), "Sole", true, true, jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString(), "LB_SOLE"), FormRegister.SOME_KEY_FORM);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        fr.openForm(new AnalysisBTForm("Выборка моделей на фасоне: " + jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString(), "Heel", true, true, jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString(), "LB_HEEL"), FormRegister.SOME_KEY_FORM);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        fr.openForm(new AddHeelForm("Каблук", false, this, jTable2.getValueAt(jTable2.getSelectedRow(), indexColumn).toString(), true), FormRegister.SOME_KEY_FORM);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            switch (jTable1.getSelectedColumn()) {
                case 0:
                    fr.openForm(new SingleImageForm(CacheImage.cachePath.get(CacheImage.TYPE_SOLE), jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString()), FormRegister.SOME_KEY_FORM);
                    break;
                default:
                    fr.openForm(new AddSoleForm("Подошва", true, this, jTable1.getValueAt(jTable1.getSelectedRow(), indexColumn).toString(), false), FormRegister.SOME_KEY_FORM);
                    break;
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getClickCount() == 2) {
            switch (jTable2.getSelectedColumn()) {
                case 0:
                    fr.openForm(new SingleImageForm(CacheImage.cachePath.get(CacheImage.TYPE_HEEL), jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString()), FormRegister.SOME_KEY_FORM);
                    break;
                default:
                    fr.openForm(new AddHeelForm("Каблук", false, this, jTable2.getValueAt(jTable2.getSelectedRow(), indexColumn).toString(), false), FormRegister.SOME_KEY_FORM);
                    break;
            }
        }
    }//GEN-LAST:event_jTable2MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
