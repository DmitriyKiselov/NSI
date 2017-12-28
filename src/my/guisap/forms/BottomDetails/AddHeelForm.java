/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms.BottomDetails;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import my.guisap.componenst.DataPanel;
import my.guisap.componenst.EmptyForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.CreateFormUtils;
import my.guisap.utils.SecurityManager;

/**
 *
 * @author KiselevDA
 */
public class AddHeelForm extends EmptyForm {

    DataPanel data = new DataPanel("LB_HEEL", "NAME", 1);

    JTextField field;
    BottomDetails parentForm;

    public AddHeelForm(String caption, String classFlag, boolean needToSave, boolean needSaveSize, BottomDetails parentForm) {
        super(caption, classFlag, needToSave, needSaveSize);
        this.parentForm = parentForm;
        createFormFields();
    }

    public AddHeelForm(String caption, String classFlag, boolean needToSave, boolean needSaveSize, JTextField field) {
        super(caption, classFlag, needToSave, needSaveSize);
        this.field = field;
        createFormFields();
    }

    private void createFormFields() {
        String firstMasElem[][] = {
            {"Название фасона", "true"},
            {"Название артикула", "true"}};
        String secondMasElem[][] = {
            {"Высота каблука", "true"}};

        data.addFields(firstMasElem, 0, 1, CreateFormUtils.DEFAULT_INSETS);
        data.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'LB_HEEL' and MAIN_CLASS = 'LB_HEEL'" + SqlOperations.GROUP_BY, true, 0);
        data.addFields(secondMasElem, 0, 1, CreateFormUtils.DEFAULT_INSETS);
        data.setBorder(CreateFormUtils.defaultBorder);
        data.setCheckFields(true, null);

        pnlAttElem.add(data);

        processing();

        pack();
        setCenter();
    }

    private void processing() {
        data.getTextField(0).addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                data.getTextField(1).setText(data.getTextField(0).getText() + "/");
            }
        });
        data.getTextField(1).setEditable(false);
    }

    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        if (!data.checkID(0)) {
            saveToDB();
        } else {
            JOptionPane.showMessageDialog(this, "Каблук с таким именем уже есть в базе", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void saveToDB() {
        String[] extraFields = new String[1];

        if (SecurityManager.idGroup == 1) {
            extraFields[0] = "NS";
        } else {
            extraFields[0] = "R";
        }

        if (data.saveToDB(data.generateID, extraFields)) {
            if (parentForm != null) {
                parentForm.fillTableHeel();
            } else if (field != null) {
                field.setText(data.getTextField(0).getText());
            }
            this.closeWindow();
        } else {
            JOptionPane.showMessageDialog(this, "Заполните поля отмеченные красным", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }

}
