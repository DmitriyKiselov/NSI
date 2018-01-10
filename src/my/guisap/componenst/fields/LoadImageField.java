package my.guisap.componenst.fields;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import my.guisap.GuiStaticVariables;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.CacheImage;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.CreateFormUtils;
import my.guisap.utils.ImageUtils;

/**
 *
 * @author Dima
 */
public class LoadImageField extends JPanel {

    private JTextField pathField;
    private final JButton button;
    private File fileToSave;
    private Image image;
    private final String typeSave;

    private int numberFieldName;

    SqlOperations sql = new SqlOperations();

    public LoadImageField(String typeSave, boolean showImage) {

        this.typeSave = typeSave;

        pathField = ComponentsUtils.createField("", 200, 23, false);
        button = ComponentsUtils.createBtn("", 27, 23, ImageUtils.createIconForButton("/toolbarButtonGraphics/general/History16.gif"), true);

        button.addActionListener((ActionEvent ae) -> {
            FileDialog fd = new FileDialog(new Frame(), "Выберите файл", FileDialog.LOAD);
            fd.setFile("*.jpg;*.jpeg;*.gif;*png");
            fd.setVisible(true);
            String filename = fd.getFile();
            if (filename == null) {
                pathField.setText("");
            } else {
                fileToSave = fd.getFiles()[0];
                pathField.setText(fileToSave.getPath());
            }
        });

        super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        super.add(ComponentsUtils.createLabel("Прикрепить изображение: "));
        super.add(Box.createHorizontalGlue());
        super.add(Box.createHorizontalStrut(GuiStaticVariables.TIGHTLE_STRUT));
        super.add(pathField);
        super.add(button);
        super.setBorder(CreateFormUtils.defaultBorder);
    }
    
    
    public void saveImage(String nameToSave) {
        if (fileToSave != null) {
            String path = CacheImage.cachePath.get(typeSave);
            if (path != null) {
                ImageUtils.saveImage(fileToSave, path, path + "/icons", nameToSave, CacheImage.getMapByType(typeSave));
            }
        }
    }

}
