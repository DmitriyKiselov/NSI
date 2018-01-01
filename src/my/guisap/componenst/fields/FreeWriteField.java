package my.guisap.componenst.fields;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import my.guisap.utils.ComponentsUtils;

/**
 *
 * @author Dima
 */
public class FreeWriteField extends EntityForField {

    private final JLabel nameField;
    private final JTextField textField;

    public FreeWriteField(String name, String nameToSave, String info, boolean editable) {
        super(name, nameToSave, "", info, editable);

        nameField = ComponentsUtils.createLabel(name);
        textField = ComponentsUtils.createField("", 227, 23, editable);

        super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        super.add(nameField);
        super.add(Box.createHorizontalGlue());
        super.add(textField);

    }

    public String getText() {
        return textField.getText();
    }

    public JTextField getTextField() {
        return textField;
    }

}
