package my.guisap.componenst.fields;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import my.guisap.GuiStaticVariables;
import my.guisap.utils.ComponentsUtils;

/**
 *
 * @author Dima
 */
public class FreeWriteField extends EntityForField {

    private final JLabel nameField;
    private final JTextField textField;

    public FreeWriteField(String nameForm, String nameField, String nameToSave, String discriptionField, String position, String block, String editable, String info) {
        super(nameForm, "", nameField, nameToSave, discriptionField, position, block, editable, info);

        this.nameField = ComponentsUtils.createLabel(discriptionField);
        this.textField = ComponentsUtils.createField("", 227, 23, isEditable());
        setField(textField);

        super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        super.add(this.nameField);
        super.add(Box.createHorizontalGlue());
        super.add(Box.createHorizontalStrut(GuiStaticVariables.TIGHTLE_STRUT));
        super.add(textField);

    }

    public String getText() {
        return textField.getText();
    }

    public JTextField getTextField() {
        return textField;
    }

    @Override
    public void setEnabledComponent(boolean isEnabled) {
        textField.setEnabled(false);
    }

    @Override
    public void setText(String text) {
        textField.setText(text);
    }

}
