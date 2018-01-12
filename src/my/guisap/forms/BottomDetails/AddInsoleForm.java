/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms.BottomDetails;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import my.guisap.componenst.EmptyForm;
import my.guisap.componenst.SaveForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.CreateFormUtils;

/**
 *
 * @author KiselevDA
 */
public class AddInsoleForm extends SaveForm {

//    DataPanel data = new DataPanel("LB_BASIC_INSOLE", "NAME", 1);

    JTextField field;

    BottomDetails parentForm;

    public AddInsoleForm(String caption, boolean needToSave,  BottomDetails parentForm) {
        super(caption,  needToSave);
        this.parentForm = parentForm;
        createFormFields();
    }

    public AddInsoleForm(String caption,  boolean needToSave,  JTextField field) {
        super(caption,  needToSave);
        this.field = field;
        createFormFields();
    }

    private void createFormFields() {
        //названия полей без подгрузки из SAP
        String firstMasElem[][] = {
            {"Название фасона", "true"}};

        String secondMasElem[][] = {
            {"Ростовка", "true"}};

//        data.addFields(firstMasElem, 0, 1, CreateFormUtils.DEFAULT_INSETS);
//        data.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'LB_BASIC_INSOLE' and MAIN_CLASS = 'LB_BASIC_INSOLE' and PRIORITY<'3'" + SqlOperations.GROUP_BY, true, 0);
//        data.addFields(secondMasElem, 0, 1, CreateFormUtils.DEFAULT_INSETS);
//        data.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'LB_BASIC_INSOLE' and MAIN_CLASS = 'LB_BASIC_INSOLE' and PRIORITY>='3'" + SqlOperations.GROUP_BY, true, 0);
//        data.setBorder(CreateFormUtils.defaultBorder);
//        data.setCheckFields(true, null);
//
//        contentPanel.add(data);

        pack();
        setCenter();
    }

    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
//        if (!data.checkID(0)) {
//            saveToDB();
//        } else {
//            JOptionPane.showMessageDialog(this, "Стелька с таким именем уже есть в базе", "Предупреждение", JOptionPane.WARNING_MESSAGE);
//        }
    }

    private void saveToDB() {
//        String[] extraFields = new String[1];
//
//        if (my.guisap.utils.SecurityManager.idGroup == 1) {
//            extraFields[0] = "NS";
//        } else {
//            extraFields[0] = "R";
//        }
//
//        if (data.saveToDB(data.generateID, extraFields)) {
//            if (parentForm != null) {
//                parentForm.fillTableInSole();
//            } else if (field != null) {
//                field.setText(data.getTextField(0).getText());
//            }
//            this.closeWindow();
//        } else {
//            JOptionPane.showMessageDialog(this, "Заполните поля отмеченные красным", "Предупреждение", JOptionPane.WARNING_MESSAGE);
//        }
    }

    @Override
    public void fillFields() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
