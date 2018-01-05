package my.guisap.componenst.fields;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import my.guisap.GuiStaticVariables;
import my.guisap.forms.CatalogForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.ImageUtils;

/**
 *
 * @author Dima
 */
public class CatalogField extends EntityForField {

    private String code = "";

    private final JLabel nameField;
    private final JTextField textField;
    private final JButton buttonCatalog;

    public CatalogField(String nameForm, String nameCatalog, String nameField, String nameToSave, String discriptionField, String position, String block, String editable, String checked, String info) {

        super(nameForm, nameCatalog, nameField, nameToSave, discriptionField, position, block, editable, checked, info);

        this.nameField = ComponentsUtils.createLabel(discriptionField);
        this.textField = ComponentsUtils.createField("", 200, 23, false);
        setField(textField);

        buttonCatalog = ComponentsUtils.createBtn("", 27, 23, isEditable());
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
        super.add(Box.createHorizontalStrut(GuiStaticVariables.TIGHTLE_STRUT));
        super.add(textField);
        super.add(buttonCatalog);
    }

    public void setText(String code, String value) {
        this.code = code;
        textField.setText(value);
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

    @Override
    public void setEnabledComponent(boolean isEnabled) {
        textField.setEditable(false);
        buttonCatalog.setEnabled(false);
    }

    @Override
    public String getText() {
        return textField.getText();
    }

    @Override
    public void setText(String text) {
        textField.setText(text);
    }

}
