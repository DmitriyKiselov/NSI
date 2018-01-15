package my.guisap.forms.BottomDetails;

import baseclass.BInternalFrame;
import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import my.guisap.FormRegister;
import my.guisap.GuiStaticVariables;
import my.guisap.componenst.MyImageCellRenderer;
import my.guisap.forms.ImageForm;
import my.guisap.forms.LifeLineForm;
import my.guisap.forms.SingleImageForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.CacheImage;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.ImageUtils;
import my.guisap.utils.LifeLineUtils;

/**
 *
 * @author KiselevDA
 */
public class BottomDetails extends BInternalFrame {

    TableRowSorter<DefaultTableModel> sorter;

    int pictureColumn = 0;
    int indexColumn = 2;

    MouseAdapter test;

    public BottomDetails() {
        needSaveSize = true;
        setTitle("Делати низа");
        fr = FormRegister.getInstance();
        initComponents();
        setIcons();
        fillTable();

    }

    public void fillTable() {
        DefaultTableModel tmpModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return ImageIcon.class;
                }
                return super.getColumnClass(column);
            }
        };
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                sql.tableFill(SqlOperations.SOLE_FULL_TABLE, tmpModel);
                break;
            case 1:
                sql.tableFill(SqlOperations.HEEL_FULL_TABLE, tmpModel);
                break;
            case 2:
                sql.tableFill(SqlOperations.INSOLE_FULL, tmpModel);
                break;
            default:
                sql.tableFill(SqlOperations.SOLE_FULL_TABLE, tmpModel);
                break;
        }

        jTable1.setModel(tmpModel);
        sorter = new TableRowSorter<>(tmpModel);

        Thread t = new Thread() {
            @Override
            public void run() {
                switch (jTabbedPane1.getSelectedIndex()) {
                    case 0:
                        CacheImage.loadImageToTable(CacheImage.TYPE_SOLE, CacheImage.cacheSole, jTable1, 1, pictureColumn);
                        break;
                    case 1:
                        CacheImage.loadImageToTable(CacheImage.TYPE_HEEL, CacheImage.cacheHeel, jTable1, 1, pictureColumn);
                        break;
                }

            }
        };
        t.start();

        jTable1.setDefaultRenderer(jTable1.getColumnClass(0),
                new MyImageCellRenderer());
        jTable1.setRowHeight(60);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
    }

    private void setIcons() {
        jButton17.setIcon(ImageUtils.createIconForButton("images/update.png"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton17 = ComponentsUtils.createBtn("",25,25,true);
        jButton2 = ComponentsUtils.createBtn("Удалить",140,25,true);
        jButton3 = ComponentsUtils.createBtn("Добавить на основе",240,25,true);
        jButton1 = ComponentsUtils.createBtn("Добавить",140,25,true);
        jButton5 = ComponentsUtils.createBtn("Открыть/Редактировать",240,25,true)
        ;
        jButton6 = ComponentsUtils.createBtn("Анализ фасона",200,25,true);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1360, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Подошва", jPanel1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1360, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Каблук", jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1360, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Основная стелька", jPanel6);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1360, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Каблучный узел", jPanel7);

        jLabel1.setText("Поиск:");

        jTextField1.setFont(GuiStaticVariables.globalFont);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                jTable1.setRowSorter(sorter);
                try {
                    sorter.setRowFilter(RowFilter.regexFilter("(?iu)" + jTextField1.getText()));
                } catch (NullPointerException ex) {
                }
                try {
                    jTable1.changeSelection(0, 1, true, true);
                    jTable1.setRowSelectionInterval(0, 0);
                } catch (IllegalArgumentException ex) {
                }
            }
        });

        jButton17.setOpaque(false);
        jButton17.setPreferredSize(new java.awt.Dimension(33, 25));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton2.setText("Удалить");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Добавить на основе");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("Добавить");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setText("Открыть/Редактировать");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Анализ фасона");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
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
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setCellSelectionEnabled(true);
        jTable1.setOpaque(false);
        jTable1.setUpdateSelectionOnSort(false);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jLabel2.setText("Связанные колодки:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton2)
                        .addGap(28, 28, 28)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jButton6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton1)
                            .addComponent(jButton5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(238, Short.MAX_VALUE))))
        );

        jTabbedPane1.addChangeListener((ce) -> {
            fillTable();
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        fillTable();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                sql.SendQuery("DELETE FROM LB_SOLE WHERE ART='" + jTable1.getValueAt(jTable1.getSelectedRow(), indexColumn) + "'");
                break;
            case 1:
                sql.SendQuery("DELETE FROM LB_HEEL WHERE ART='" + jTable1.getValueAt(jTable1.getSelectedRow(), indexColumn) + "'");
                break;
            default:
                break;
        }
        fillTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                fr.openForm(new AddSoleForm("Подошва", true, this), FormRegister.SOME_KEY_FORM);
                break;
            case 1:
                fr.openForm(new AddHeelForm("Каблук", true, this), FormRegister.SOME_KEY_FORM);
                break;
            case 2:
                fr.openForm(new AddInsoleForm("Основная стелька", true, this), FormRegister.SOME_KEY_FORM);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                fr.openForm(new AddSoleForm("Подошва", true, this, jTable1.getValueAt(jTable1.getSelectedRow(), indexColumn).toString(), true), FormRegister.SOME_KEY_FORM);
                break;
            case 1:
                fr.openForm(new AddHeelForm("Каблук", false, this, jTable1.getValueAt(jTable1.getSelectedRow(), indexColumn).toString(), true), FormRegister.SOME_KEY_FORM);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                fr.openForm(new AddSoleForm("Подошва", true, this, jTable1.getValueAt(jTable1.getSelectedRow(), indexColumn).toString(), false), FormRegister.SOME_KEY_FORM);
                break;
            case 1:
                fr.openForm(new AddHeelForm("Каблук", false, this, jTable1.getValueAt(jTable1.getSelectedRow(), indexColumn).toString(), false), FormRegister.SOME_KEY_FORM);
                break;
            default:
                break;

        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                fr.openForm(new AnalysisBTForm("Выборка моделей на фасоне: " + jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString(), "Sole", true, true, jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString(), "LB_SOLE"), FormRegister.SOME_KEY_FORM);
                break;
            case 1:
                fr.openForm(new AnalysisBTForm("Выборка моделей на фасоне: " + jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString(), "Heel", true, true, jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString(), "LB_HEEL"), FormRegister.SOME_KEY_FORM);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 1) {
            DefaultTableModel tmpModel = new DefaultTableModel();
            switch (jTabbedPane1.getSelectedIndex()) {
                case 0:
                    sql.tableFill("select  b.index_last \"Индекс\", a.LAST \"Фасон\" from FASON_LINK a left join LAST_HEAD b on a.LAST=b.fason_last where a.sole='" + jTable1.getValueAt(jTable1.getSelectedRow(), 2) + "'", tmpModel);
                    break;
                case 1:
                    sql.tableFill("select  b.index_last \"Индекс\", a.LAST \"Фасон\" from FASON_LINK a left join LAST_HEAD b on a.LAST=b.fason_last where a.heel='" + jTable1.getValueAt(jTable1.getSelectedRow(), 2) + "'", tmpModel);
                    break;
                default:
//                    sql.tableFill(SqlOperations.SOLE_FULL_TABLE, tmpModel);
                    break;
            }
            jTable2.setModel(tmpModel);
        } else if (evt.getClickCount() == 2) {
            switch (jTable1.getSelectedColumn()) {
                case 0:
                    fr.openForm(new SingleImageForm(CacheImage.cachePath.get(CacheImage.TYPE_SOLE), jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString()), FormRegister.SOME_KEY_FORM);
                    break;
                default:
                    jButton5ActionPerformed(null);
                    break;
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
