/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms;

import baseclass.BInternalFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import my.guisap.GuiStaticVariables;

/**
 *
 * @author KiselevDA
 */
public class SingleImageForm extends BInternalFrame {

    private Image imgPhoto;

    public SingleImageForm(String path, String name) {
        try {
            this.imgPhoto = ImageIO.read(new File(path + "\\" + name + ".jpg"));
            add(new SimpleComponent(imgPhoto));
            setPreferredSize(new Dimension(GuiStaticVariables.screenWidth / 4, GuiStaticVariables.screenHeight / 4));
            setBounds(GuiStaticVariables.screenWidth / 2 - getWidth() / 2, (GuiStaticVariables.screenHeight - 70) / 2 - getHeight() / 2, getWidth(), getHeight());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
            pack();
            setCenter();
        } catch (IOException ex) {
            Logger.getLogger(SingleImageForm.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Изображение отсутствует ", "Предупреждение", JOptionPane.WARNING_MESSAGE);

        }
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
}
