/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms;

import baseclass.BInternalFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import my.guisap.GuiStaticVariables;
import my.guisap.MainClass;

/**
 *
 * @author KiselevDA
 */
public class ImageForm extends BInternalFrame {

    private Image imgPhoto;
    private Image imgEsk;
    private String model;
    private String typeImg;
    private String path;

    public ImageForm(String model, int typeImg) throws HeadlessException {
        this.model = model;
        switch (typeImg) {
            case 0:
                this.typeImg = "Фото модели";
                break;
            case 1:
                this.typeImg = "Эскиз";
                break;
        }

        if (loadImage(typeImg)) {
            initComponents();
            jTabbedPane1.setSelectedIndex(typeImg);
            setTitle(this.typeImg + " " + model);
            setPreferredSize(new Dimension(GuiStaticVariables.screenWidth / 4, GuiStaticVariables.screenHeight / 4));
            setBounds(GuiStaticVariables.screenWidth / 2 - getWidth() / 2, (GuiStaticVariables.screenHeight - 70) / 2 - getHeight() / 2, getWidth(), getHeight());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }

    public ImageForm(BufferedImage img) {
        this.imgPhoto = img;
        initComponents();
        setPreferredSize(new Dimension(GuiStaticVariables.screenWidth / 4, GuiStaticVariables.screenHeight / 4));
        setBounds(GuiStaticVariables.screenWidth / 2 - getWidth() / 2, (GuiStaticVariables.screenHeight - 70) / 2 - getHeight() / 2, getWidth(), getHeight());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private boolean loadImage(int typeImg) {
        switch (typeImg) {
            case 0:
                try {
                    imgPhoto = ImageIO.read(new File(GuiStaticVariables.DEF_PICTURES_PATH + GuiStaticVariables.SEPARATOR + model + ".jpg"));
                    try {
                        imgEsk = ImageIO.read(new File(GuiStaticVariables.DEF_DESIGN_PATH + GuiStaticVariables.SEPARATOR + model + ".jpg"));
                    } catch (IOException ex) {
                        URL imgURL = MainClass.class.getResource("images/notimage.png");
                        imgEsk = ImageIO.read(imgURL);
                    }
                    return true;
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "У данной модели отсутствует " + this.typeImg.toLowerCase(), "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            case 1:
                try {
                    imgEsk = ImageIO.read(new File(GuiStaticVariables.DEF_DESIGN_PATH + GuiStaticVariables.SEPARATOR + model + ".jpg"));
                    try {
                        imgPhoto = ImageIO.read(new File(GuiStaticVariables.DEF_PICTURES_PATH + GuiStaticVariables.SEPARATOR + model + ".jpg"));
                    } catch (IOException ex) {
                        URL imgURL = MainClass.class.getResource("images/notimage.png");
                        imgPhoto = ImageIO.read(imgURL);
                    }
                    return true;
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "У данной модели отсутствует " + this.typeImg.toLowerCase(), "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
        }
        return true;
    }

    private class SimpleComponent extends JPanel {

        Image img;
        int height;
        int width;

        public SimpleComponent(Image img) {
            setOpaque(true);
            this.img = img;
        }

        @Override
        protected void paintComponent(Graphics g) {
            height = getHeight();
            width = getWidth();
            if (img.getHeight(this) < getHeight()) {
                height = img.getHeight(this);
            }
            if (img.getWidth(this) < getWidth()) {
                width = img.getWidth(this);
            }
            g.drawImage(img, getX(), getY(), width, height, this);
        }

        public void setImage(Image img) {
            this.img = img;
            repaint();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new SimpleComponent(imgPhoto);
        jPanel2 = new SimpleComponent(imgEsk);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel1.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1074, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Фото", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1074, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Эскиз", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
