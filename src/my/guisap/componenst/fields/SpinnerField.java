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
import my.guisap.GuiStaticVariables;
import my.guisap.utils.ComponentsUtils;

/**
 *
 * @author Dima
 */
public class SpinnerField extends EntityForField {

    private final JLabel nameField;
    private final JSpinner textField;
    private SpinnerModel value = null;

    public SpinnerField(String nameForm, String nameField, String nameToSave, String discriptionField, String position, String block, String editable, String checked, String info) {
        super(nameForm, "", nameField, nameToSave, discriptionField, position, block, editable, checked, info);

        this.nameField = ComponentsUtils.createLabel(discriptionField);

        switch (info) {
            case "year": {
                Calendar calendar = Calendar.getInstance();
                value = new SpinnerNumberModel(calendar.get(Calendar.YEAR), 1900, 2100, 1);
            }
            break;
        }

        textField = ComponentsUtils.createSpinner("", 227, 23, value, isEditable());
        setField(textField);

        super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        super.add(this.nameField);
        super.add(Box.createHorizontalGlue());
        super.add(Box.createHorizontalStrut(GuiStaticVariables.TIGHTLE_STRUT));
        super.add(textField);

    }

    @Override
    public String getText() {
        return textField.getValue().toString();
    }

    public JSpinner getTextField() {
        return textField;
    }

    @Override
    public void setEnabledComponent(boolean isEnabled) {
        textField.setEnabled(false);
    }

    @Override
    public void setText(String text) {
        textField.setValue(Integer.valueOf(text));
    }
}
