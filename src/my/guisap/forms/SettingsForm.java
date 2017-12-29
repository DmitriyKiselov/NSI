/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms;

import baseclass.BInternalFrame;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import my.guisap.Configs;
import my.guisap.GuiStaticVariables;

/**
 *
 * @author DonchenkoUA
 */
public class SettingsForm extends BInternalFrame {

    /**
     * Creates new form SettingsForm
     */
    final String fileNameSettings = GuiStaticVariables.CONFIGS_DIRECTORY + "settings.config";
    String path = GuiStaticVariables.picturesPATH;
    String temp = GuiStaticVariables.picturesPATH;
    int defFontSize = 12;

    private String[] fontName = {"Arial",
        "Arial Black",
        "Comic Sans MS",
        "Courier New",
        "Georgia",
        "Impact",
        "Times New Roman",
        "Trebuchet MS",
        "Verdana"};

    public SettingsForm() {
        setClosable(true);
        initComponents();
        picturePathField.setText(GuiStaticVariables.picturesPATH);
    }

    private String runFileChooser() {
        String file = GuiStaticVariables.picturesPATH.substring(0, GuiStaticVariables.picturesPATH.length() - 1);
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);// выбрать тип диалога Open или Save
        chooser.setDialogTitle("Выбор директории картинок");
        chooser.setApproveButtonText("Выбрать");//выбрать название для кнопки согласия
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile().toString();
        }
        return file;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        picturePathField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox1.setSelected(GuiStaticVariables.SETTIN_LOCATION_ON_MOUSE);
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Выбор пути картинок модели"));
        jPanel1.setFont(GuiStaticVariables.globalFont);

        picturePathField.setFont(GuiStaticVariables.globalFont);
        picturePathField.setEnabled(false);

        jLabel1.setFont(GuiStaticVariables.globalFont);
        jLabel1.setText("Текущий путь:");

        jButton3.setFont(GuiStaticVariables.globalFont);
        jButton3.setText("Выбрать");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(GuiStaticVariables.globalFont);
        jButton4.setText("По умолчанию");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(picturePathField)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(picturePathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4))
        );

        jButton2.setFont(GuiStaticVariables.globalFont);
        jButton2.setText("Отмена");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setFont(GuiStaticVariables.globalFont);
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setFont(GuiStaticVariables.globalFont);
        jCheckBox1.setText("Открывать справочники около курсора");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        DefaultComboBoxModel comboModel = new DefaultComboBoxModel();

        for (int i=0; i<fontName.length; i++){
            comboModel.addElement(fontName[i]);
        }

        jComboBox1.setModel(comboModel);
        jComboBox1.setFont(GuiStaticVariables.globalFont);
        jComboBox1.setSelectedItem(GuiStaticVariables.globalFont.getName());
        jComboBox1.setPreferredSize(new java.awt.Dimension(230, 20));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(GuiStaticVariables.globalFont);
        jLabel2.setText("Шрифт:");

        DefaultComboBoxModel comboMode2 = new DefaultComboBoxModel();

        for (int i=5; i<20; i++){
            comboMode2.addElement(i);
        }

        jComboBox2.setModel(comboMode2);
        jComboBox2.setFont(GuiStaticVariables.globalFont);
        jComboBox2.setSelectedItem(GuiStaticVariables.globalFont.getSize());
        jComboBox2.setPreferredSize(new java.awt.Dimension(50, 20));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jRadioButton1.setText("Маленький");
        jRadioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiStaticVariables.scaleWidth=GuiStaticVariables.DefScaleWidth-0.3+GuiStaticVariables.fontScale;
                GuiStaticVariables.scaleHeight=GuiStaticVariables.DefScaleHeight-0.3+GuiStaticVariables.fontScale;
            }
        });

        jRadioButton2.setText("Стандартный");
        jRadioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiStaticVariables.scaleWidth=GuiStaticVariables.DefScaleWidth+GuiStaticVariables.fontScale;
                GuiStaticVariables.scaleHeight=GuiStaticVariables.DefScaleHeight+GuiStaticVariables.fontScale;
            }
        });

        jRadioButton3.setText("Большой");
        jRadioButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiStaticVariables.scaleWidth=GuiStaticVariables.DefScaleWidth+0.3+GuiStaticVariables.fontScale;
                GuiStaticVariables.scaleHeight=GuiStaticVariables.DefScaleHeight+0.3+GuiStaticVariables.fontScale;
            }
        });

        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        buttonGroup1.add(jRadioButton3);

        System.out.println(GuiStaticVariables.DefScaleWidth+GuiStaticVariables.fontScale);
        System.out.println(GuiStaticVariables.fontScale);

        if (GuiStaticVariables.scaleWidth==GuiStaticVariables.DefScaleWidth+GuiStaticVariables.fontScale){
            jRadioButton2.setSelected(true);
        } else if (GuiStaticVariables.scaleWidth<GuiStaticVariables.DefScaleWidth+GuiStaticVariables.fontScale){
            jRadioButton1.setSelected(true);
        } else {
            jRadioButton3.setSelected(true);
        }

        jLabel3.setFont(GuiStaticVariables.globalFont);
        jLabel3.setText("Размер интерфейса:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jRadioButton1))
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jRadioButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButton3)
                                        .addGap(0, 4, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        GuiStaticVariables.picturesPATH = temp;
        try {
            this.setClosed(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(SettingsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void saveConfig() {
        Configs conf = new Configs(fileNameSettings);
        conf.setProperty("IMAGEPATH", GuiStaticVariables.picturesPATH);
        conf.setProperty("FONT", GuiStaticVariables.globalFont.getFontName());
        conf.setProperty("FONT_SIZE", String.valueOf(GuiStaticVariables.globalFont.getSize()));
        conf.setProperty("SCALE_W", String.valueOf(GuiStaticVariables.scaleWidth));
        conf.setProperty("SCALE_H", String.valueOf(GuiStaticVariables.scaleHeight));
        conf.setProperty("SCALE_FONT", String.valueOf(GuiStaticVariables.fontScale));
        conf.saveProperties("Configuration");
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        path = runFileChooser();
        picturePathField.setText(path);
        GuiStaticVariables.picturesPATH = path + GuiStaticVariables.SEPARATOR;
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        saveConfig();
        try {
            this.setClosed(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(SettingsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        temp = GuiStaticVariables.picturesPATH;
        GuiStaticVariables.picturesPATH = GuiStaticVariables.DEF_PICTURES_PATH;
        picturePathField.setText(GuiStaticVariables.picturesPATH);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        GuiStaticVariables.SETTIN_LOCATION_ON_MOUSE = jCheckBox1.isSelected();
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        GuiStaticVariables.globalFont = new Font(jComboBox1.getSelectedItem().toString(), 0, (int) jComboBox2.getSelectedItem());
        GuiStaticVariables.fontScale = (double) ((int) jComboBox2.getSelectedItem() - defFontSize) / (double) 12;
        if (jRadioButton1.isSelected()) {
            GuiStaticVariables.scaleWidth = GuiStaticVariables.DefScaleWidth + GuiStaticVariables.fontScale - 0.3;
            GuiStaticVariables.scaleHeight = GuiStaticVariables.DefScaleHeight + GuiStaticVariables.fontScale - 0.3;
        } else if (jRadioButton2.isSelected()) {
            GuiStaticVariables.scaleWidth = GuiStaticVariables.DefScaleWidth + GuiStaticVariables.fontScale;
            GuiStaticVariables.scaleHeight = GuiStaticVariables.DefScaleHeight + GuiStaticVariables.fontScale;
        } else if (jRadioButton3.isSelected()) {
            GuiStaticVariables.scaleWidth = GuiStaticVariables.DefScaleWidth + GuiStaticVariables.fontScale + 0.3;
            GuiStaticVariables.scaleHeight = GuiStaticVariables.DefScaleHeight + GuiStaticVariables.fontScale + 0.3;
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        GuiStaticVariables.globalFont = new Font(jComboBox1.getSelectedItem().toString(), 0, (int) jComboBox2.getSelectedItem());
    }//GEN-LAST:event_jComboBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JTextField picturePathField;
    // End of variables declaration//GEN-END:variables
}
