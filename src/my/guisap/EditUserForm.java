package my.guisap;

import my.guisap.forms.UserEditDialog;
import my.guisap.componenst.JOverrideTable;
import baseclass.BInternalFrame;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dima
 */
public final class EditUserForm extends BInternalFrame {

    UserEditDialog userDialog;
    DefaultTableModel userInfoModel = new DefaultTableModel(0, 0);
    DefaultTableModel groupsModel = new DefaultTableModel(0, 0);
    DefaultTableModel userModel = new DefaultTableModel(0, 1) {
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex) {
            return false;
        }
    };

    public EditUserForm() {
        setClosable(true);
        initComponents();

        setTitle("Настройка пользователей");

        formUpdate();
    }

    public void formUpdate() {
        userInfoModel = new DefaultTableModel(0, 0);
        userModel.setRowCount(0);
        sql.tableFill("select a.Users,a.Pass, a.USER_ID, b.* from USERS a inner join USERS_GROUPS b on a.ID_Group = b.ID_GROUP", userInfoModel);

        for (int rowInd = 0; rowInd < userInfoModel.getRowCount(); rowInd++) {
            userModel.addRow(new Object[]{});
            userModel.setValueAt(userInfoModel.getValueAt(rowInd, 0).toString(), rowInd, 0);
        }

        jTable1.setModel(userModel);

        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.getColumnModel().getColumn(0).setHeaderValue("Пользователь");

        JOverrideTable.packColumns(jTable1);
    }

    private void userEdit(String caption) {
        if (userDialog != null) {
            userDialog.dispose();
        }

        ArrayList<String> groups = new ArrayList<>();
        groupsModel = new DefaultTableModel(0, 0);
        sql.tableFill("select distinct a.GROUP_NAME from USERS_GROUPS a", groupsModel);

        for (int rowInd = 0; rowInd < groupsModel.getRowCount(); rowInd++) {
            groups.add(groupsModel.getValueAt(rowInd, 0).toString());
        }

        if (caption.equals("Добавление")) {
            userDialog = new UserEditDialog(true, caption, groups, this);
        } else {
            userDialog = new UserEditDialog(true, caption, userInfoModel.getValueAt(jTable1.getSelectedRow(), 0).toString(), userInfoModel.getValueAt(jTable1.getSelectedRow(), 1).toString(), groups, userInfoModel.getValueAt(jTable1.getSelectedRow(), 2).toString(), this);
        }
        userDialog.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

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

        jButton1.setText("Добавить");
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 183, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        userEdit("Добавление");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            userEdit("Редактирование");
        }
    }//GEN-LAST:event_jTable1MouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
