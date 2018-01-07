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
import javax.swing.table.DefaultTableModel;
import my.guisap.GuiStaticVariables;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.ImageUtils;

/**
 *
 * @author Dima
 */
public class LoadImageField extends JPanel {

    public static int TYPE_MKZ = 1;
    public static int TYPE_SOLE = 2;
    public static int TYPE_HEEL = 3;
    public static int TYPE_UP = 4;

    private JTextField pathField;
    private JButton button;
    private File fileToSave;
    private Image image;
    private int typeSave;

    SqlOperations sql = new SqlOperations();

    public LoadImageField(int typeSave, boolean showImage) {

        this.typeSave = typeSave;

        pathField = ComponentsUtils.createField("", 200, 23, false);
        button = ComponentsUtils.createBtn("", 27, 23, ImageUtils.createIconForButton("/toolbarButtonGraphics/general/History16.gif"), true);

        button.addActionListener((ActionEvent ae) -> {
            FileDialog fd = new FileDialog(new Frame(), "Выберите файл", FileDialog.LOAD);
            fd.setFile("*.jpg;*.gif;*png");
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
    }

    public void saveImage(String nameToSave) {
        if (fileToSave != null) {
            DefaultTableModel pathModel = new DefaultTableModel();
            sql.tableFill("SELECT PATH FROM PATH_TABLE WHERE ID=" + typeSave, pathModel);
            if (pathModel.getRowCount() == 2) {
                ImageUtils.saveImage(fileToSave, (String) pathModel.getValueAt(0, 0), (String) pathModel.getValueAt(0, 1), nameToSave);
            }
        }
    }

}
