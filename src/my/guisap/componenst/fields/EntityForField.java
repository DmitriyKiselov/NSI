package my.guisap.componenst.fields;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Dima
 */
public abstract class EntityForField extends JPanel {

    private final String nameForm;
    private final String nameField;
    private final String discriptionField;
    private final String nameToSave;
    private final String nameCatalog;
    private final int position;
    private final int block;
    private final String info;
    private final boolean editable;

    private JComponent field;

    private String infoForLimitation;

    public EntityForField(String nameForm, String nameCatalog, String nameField, String nameToSave, String discriptionField, String position, String block, String editable, String info) {
        this.nameForm = nameForm;
        this.nameCatalog = nameCatalog;
        this.nameField = nameField;
        this.nameToSave = nameToSave;
        this.discriptionField = discriptionField;
        this.position = Integer.valueOf(position);
        this.block = Integer.valueOf(block);
        this.editable = editable.equals("true");
        this.info = info;

    }

    public String getNameForm() {
        return nameForm;
    }

    public String getNameField() {
        return nameField;
    }

    public String getDiscriptionField() {
        return discriptionField;
    }

    public String getNameToSave() {
        return nameToSave;
    }

    public String getNameCatalog() {
        return nameCatalog;
    }

    public int getPosition() {
        return position;
    }

    public int getBlock() {
        return block;
    }

    public boolean isEditable() {
        return editable;
    }

    public String getInfo() {
        return info;
    }

    public String getInfoForLimitation() {
        return infoForLimitation;
    }

    public void setInfoForLimitation(String value) {
        this.infoForLimitation = value;
    }

    public JComponent getField() {
        return field;
    }

    public void setField(JComponent field) {
        this.field = field;
    }

    abstract public String getText();

    abstract public void setText(String text);

    abstract public void setEnabledComponent(boolean isEnabled);

}
