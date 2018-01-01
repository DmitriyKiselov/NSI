/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.componenst.fields;

import javax.swing.JPanel;

/**
 *
 * @author Dima
 */
public class EntityForField extends JPanel {

    private final String name;
    private final String nameToSave;
    private final String nameCatalog;
    private final String info;
    private final boolean editable;

    public EntityForField(String name, String nameToSave, String nameCatalog, String info, boolean editable) {
        this.name = name;
        this.nameToSave = nameToSave;
        this.nameCatalog = nameCatalog;
        this.info = info;
        this.editable = editable;
    }

    public String getNameField() {
        return name;
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
}
