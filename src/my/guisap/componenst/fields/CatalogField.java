package my.guisap.componenst.fields;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import my.guisap.forms.CatalogForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.ImageUtils;

/**
 *
 * @author Dima
 */
public class CatalogField extends EntityForField {

    private String value;
    private String code;

    private final JLabel nameField;
    private final JTextField textField;
    private final JButton buttonCatalog;

    public CatalogField(String nameForm, String nameField, String discriptionField, String nameToSave, String nameCatalog, String info, boolean editable) {

        super(nameForm, nameField, discriptionField, nameToSave, nameCatalog, info, editable);

        this.nameField = ComponentsUtils.createLabel(nameField);
        this.textField = ComponentsUtils.createField("", 200, 23, false);

        buttonCatalog = ComponentsUtils.createBtn("", 27, 23, editable);
        buttonCatalog.setIcon(ImageUtils.createIconForButton("/toolbarButtonGraphics/general/History16.gif"));
        buttonCatalog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callCatalog();
            }
        });

        super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        super.add(this.nameField);
        super.add(Box.createHorizontalGlue());
        super.add(textField);
        super.add(buttonCatalog);
    }

    public void setText(String code, String value) {
        this.value = value;
        this.code = code;
        textField.setText(value);
    }

    public String getText() {
        return value;
    }

    public JTextField getTextField() {
        return textField;
    }

    public String getCode() {
        if (code.equals("")) {
            SqlOperations sql = new SqlOperations();
            return (String) sql.getObj("SELECT CODE FROM " + getNameCatalog() + " WHERE NAME='" + textField.getText() + "'");
        } else {
            return code;
        }
    }

    private void callCatalog() {
        CatalogForm catalogForm = new CatalogForm(true, this);
    }

}
