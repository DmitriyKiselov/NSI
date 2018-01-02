package my.guisap.componenst.fields;

import javax.swing.JPanel;

/**
 *
 * @author Dima
 */
public class EntityForField extends JPanel {

    private final String nameForm;
    private final String nameField;
    private final String discriptionField;
    private final String nameToSave;
    private final String nameCatalog;
    private final String info;
    private final boolean editable;

    private String infoForLimitation;

    public EntityForField(String nameForm, String nameField, String discriptionField, String nameToSave, String nameCatalog, String info, boolean editable) {
        this.nameForm = nameForm;
        this.nameField = nameField;
        this.discriptionField = discriptionField;
        this.nameToSave = nameToSave;
        this.nameCatalog = nameCatalog;
        this.info = info;
        this.editable = editable;
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

    public String getInfo() {
        return info;
    }

    public boolean isEditable() {
        return editable;
    }

    public String getInfoForLimitation() {
        return infoForLimitation;
    }

    public void setInfoForLimitation(String value) {
        this.infoForLimitation = value;
    }

}
