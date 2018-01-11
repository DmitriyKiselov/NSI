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
public class ECTechnolog extends SaveForm {

    NewDataPanel data = new NewDataPanel("EC", "ID", "ECTechnolog", 1);

    String id;

    public ECTechnolog(String title, String id, boolean nedToSave) {
        super(title, nedToSave);
        this.id = id;
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

    }

    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        saveToDB();
    }

    private void saveToDB() {
    }

}
