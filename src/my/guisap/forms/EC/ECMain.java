package my.guisap.forms.EC;

import baseclass.BInternalFrame;
import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import my.guisap.FormRegister;
import my.guisap.GuiStaticVariables;
import my.guisap.forms.BottomDetails.AddSoleForm;
import my.guisap.forms.SingleImageForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.CacheImage;
import my.guisap.utils.ComponentsUtils;

/**
 *
 * @author KiselevDA
 */
public class ECMain extends BInternalFrame {

    public ECMain() {
        setTitle("Эксперементальный цех");
        initComponents();
        FillTable();
    }

    public void FillTable() {
        DefaultTableModel model = new DefaultTableModel();
        sql.tableFill(SqlOperations.EC_MAIN, model);
        jTable2.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        });
        jScrollPane2.setViewportView(jTable2);
        jTable2.setAutoCreateRowSorter(true);

        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);

        jTable2.setCellSelectionEnabled(true);

        jTable2.setOpaque(false);

        jTable2.setUpdateSelectionOnSort(false);

        jButton2.setFont(GuiStaticVariables.globalFont);
        jButton2.setText("Добавить заявку");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(903, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1014, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addContainerGap(551, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(45, 45, 45)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getClickCount() == 2) {
            switch (jTable2.getSelectedColumn()) {
                case 0:
                    fr.openForm(new SingleImageForm(CacheImage.cachePath.get(CacheImage.TYPE_SOLE), jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString()), FormRegister.SOME_KEY_FORM);
                    break;
                default:
                    fr.openForm(new ECTechnolog("Обработка заявки", jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString(), true), FormRegister.SOME_KEY_FORM);
                    break;
            }
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        fr.openForm(new ECConstructor("Создание заявки", true), FormRegister.SOME_KEY_FORM);
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
