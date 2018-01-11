/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms.EC;

import my.guisap.componenst.NewDataPanel;
import my.guisap.componenst.SaveForm;

/**
 *
 * @author KiselevDA
 */
public class ECConstructor extends SaveForm {

    NewDataPanel data = new NewDataPanel("EC", "ID", "ECConstructor", 1);

    boolean newOn;

    public ECConstructor(String title, boolean nedToSave) {
        super(title, nedToSave);
        createFormFields();
    }

    public ECConstructor(String title, boolean nedToSave, boolean newOn) {
        super(title, nedToSave);
        this.newOn = newOn;
        createFormFields();
    }

    private void createFormFields() {
        data.setCheckFields(true);
        contentPanel.add(data);

        pack();
        setCenter();
    }

    @Override
    public void fillFields() {
        if (!newOn) {
            data.blockFields(new int[]{0, 1});
        }
    }

    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        saveToDB();
    }

    private void saveToDB() {
        
    }

}
