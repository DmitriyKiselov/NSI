/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.utils;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import my.guisap.GuiStaticVariables;
import my.guisap.MainClass;
import my.guisap.forms.MKZ.MkzMainForm;

/**
 *
 * @author KiselevDA
 */
public class ImageUtils {

    public static Map<String, ImageIcon> cacheImageMap = new HashMap<String, ImageIcon>();

    public ImageUtils() {

    }

    public static void CreateAndSaveIcon(File in, File out, String model) {
        BufferedImage image;
        try {
            image = ImageIO.read(in);

            //высота, ширина для иконки
            int width = image.getWidth();
            int height = image.getHeight();

            long lengthFile = in.length() / 1024;

            //если размер большой, то уменьшаем ее
            if (lengthFile > 100 && lengthFile < 300) {
                width = width / 2;
                height = height / 2;
            } else if (lengthFile > 300) {
                width = width / 7;
                height = height / 7;
            }

            //перерисовка изображения под нужный размер
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D bGr = newImage.createGraphics();
            bGr.drawImage(image, 0, 0, width, height, null);
            bGr.dispose();
            ImageIO.write(newImage, "jpg", out);
            cacheImageMap.put(model, new ImageIcon(newImage));
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void openSaveImageDialog(String path, String iconPath, String model) {
        FileDialog fd = new FileDialog(new Frame(), "Выберите файл", FileDialog.LOAD);
        fd.setFile("*.jpg;*.gif;*png");
        fd.setVisible(true);
        if (fd.getFiles()[0].toPath() != null) {
            saveImage(fd.getFiles()[0], path, iconPath, model);
        }
    }

    public static void saveImage(File fileImage, String path, String iconPath, String model) {
        try {
            //основной файл (путь к нему)
            File newFile = new File(path + GuiStaticVariables.SEPARATOR + model + ".jpg");
            //файл иконки (путь к нему)
            File newFileIcon = new File(iconPath + GuiStaticVariables.SEPARATOR + model + ".jpg");
            ImageUtils.CreateAndSaveIcon(fileImage, newFileIcon, model);

            newFile.delete();
            Files.copy(fileImage.toPath(), newFile.toPath());

            JOptionPane.showMessageDialog(null, "Изображение успешно загружено", "Информация", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Не удалось сохранить изображение", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static ImageIcon createIconForButton(String path) {
        URL imgURL = MainClass.class.getResource(path);
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(imgURL);
            icon = new ImageIcon(icon.getImage().getScaledInstance((int) (20 * GuiStaticVariables.DefScaleWidth), (int) (20 * GuiStaticVariables.DefScaleHeight), java.awt.Image.SCALE_SMOOTH));
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Не удалось загрузить изображение", "Предупреждение", JOptionPane.WARNING_MESSAGE);;
        }
        return icon;
    }

    public static ImageIcon createIconForButton(String path, int width, int height) {
        URL imgURL = MainClass.class.getResource(path);
        ImageIcon icon = new ImageIcon(imgURL);
        icon = new ImageIcon(icon.getImage().getScaledInstance((int) (width * GuiStaticVariables.DefScaleWidth), (int) (height * GuiStaticVariables.DefScaleHeight), java.awt.Image.SCALE_SMOOTH));
        return icon;
    }

    public static void clearCache() {
        cacheImageMap.clear();
    }
}
