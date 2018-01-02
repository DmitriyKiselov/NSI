/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.componenst.fields;

import java.util.Calendar;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import my.guisap.utils.ComponentsUtils;

/**
 *
 * @author Dima
 */
public class SpinnerField extends EntityForField {

    private final JLabel nameField;
    private final JSpinner textField;
    private SpinnerModel value = null;

    public SpinnerField(String nameForm, String nameField, String discriptionField, String nameToSave, String nameCatalog, String info, boolean editable) {
        super(nameForm, nameField, nameToSave, discriptionField, "", info, editable);
        this.nameField = ComponentsUtils.createLabel(nameField);

        switch (info) {
            case "year": {
                Calendar calendar = Calendar.getInstance();
                value = new SpinnerNumberModel(calendar.get(Calendar.YEAR), 1900, 2100, 1);
            }
            break;
        }

        textField = ComponentsUtils.createSpinner("", 227, 23, value, editable);

        super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        super.add(this.nameField);
        super.add(Box.createHorizontalGlue());
        super.add(textField);

    }

    public String getText() {
        return textField.getValue().toString();
    }

    public JSpinner getTextField() {
        return textField;
    }
}
