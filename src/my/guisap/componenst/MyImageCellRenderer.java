/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.componenst;

import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import my.guisap.entity.ImageViewer;

/**
 *
 * @author KiselevDA
 */
public class MyImageCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean isFocused, int row, int column) {
        if (value instanceof ImageIcon) {
            Image image = ((ImageIcon) value).getImage();
            ImageViewer imageViewer = new ImageViewer(image);
            return imageViewer;
        }
        return null;
    }
}
