/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms.EC;

import java.util.ArrayList;
import my.guisap.componenst.DataPanel;
import my.guisap.componenst.EmptyForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.CreateFormUtils;

/**
 *
 * @author KiselevDA
 */
public class ECRequest extends EmptyForm {

    DataPanel data = new DataPanel("EC", "ID", 1);
    ECMain parentForm;

    public ECRequest(String caption, String classFlag, boolean needToSave, boolean needSaveSize, ECMain parentForm) {
        super(caption, classFlag, needToSave, needSaveSize);
        this.parentForm = parentForm;
        createFormFields();
    }

    private void createFormFields() {
        String[][] fields = {
            {"Количество", "true"},
            {"Размер обуви", "true"},
            {"Примечание", "true"}
        };

        data.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'EC' and MAIN_CLASS = 'EC' and PRIORITY<'4'" + SqlOperations.GROUP_BY, true, 0);
        data.addFields(fields, 0, 1, CreateFormUtils.DEFAULT_INSETS);
        data.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'EC' and MAIN_CLASS = 'EC' and PRIORITY>='4'" + SqlOperations.GROUP_BY, true, 0);

        ArrayList<String> notCheck = new ArrayList<>();
        notCheck.add("Артикул");
        notCheck.add("Примечание");

        data.setCheckFields(true, notCheck);

        pnlAttElem.add(data);

        pack();
        setCenter();
    }

    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        saveToDB();
    }

    public void saveToDB() {
        if (data.saveToDB(true, new String[]{"ID", "DATE_CREATE"}, null)) {
            parentForm.FillTable();
            closeWindow();
        }
    }

}
