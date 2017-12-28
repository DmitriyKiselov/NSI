/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms;

import baseclass.BInternalFrame;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import my.guisap.GuiStaticVariables;
import my.guisap.utils.ComponentsUtils;

/**
 *
 * @author KiselevDA
 */
public class SimpleTableForm extends BInternalFrame {

    private String query;
    private String title;

    public SimpleTableForm(String title, String query) {
        setTitle(title);
        this.title = title;
        this.query = query;
        initComponents();
        setCenter();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1 = ComponentsUtils.createTable(this.query);

        if (jTable1.getModel().getRowCount()==0){
            JOptionPane.showMessageDialog(null, title + " отсутствуют", "Ошибка", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int width  = (int)(jTable1.getModel().getColumnCount()*100*GuiStaticVariables.scaleWidth);
        int height = (int)(jTable1.getModel().getRowCount()*100*GuiStaticVariables.scaleHeight);

        if (width>GuiStaticVariables.screenWidth){
            width=GuiStaticVariables.screenWidth;
        }

        if (height>GuiStaticVariables.screenHeight){
            height=GuiStaticVariables.screenHeight;
        }

        this.setPreferredSize(new Dimension(width,height));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
