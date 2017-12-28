/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms.BottomDetails;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import my.guisap.componenst.EmptyForm;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.CreateFormUtils;

/**
 *
 * @author Dima
 */
public class AnalysisBTForm extends EmptyForm {

    String fason;
    String table;

    public AnalysisBTForm(String caption, String classFlag, boolean needToSave, boolean needSaveSize, String fason, String table) {
        super(caption, classFlag, needToSave, needSaveSize);
        this.fason = fason;
        this.table = table;
        createFormFields();
    }

    private void createFormFields() {

        DefaultTableModel tmp = new DefaultTableModel();
        sql.tableFill("SELECT ART from " + table + " where NAME='" + fason + "' order by art", tmp);

        for (int i = 0; i < tmp.getRowCount(); i++) {
            pnlAttElem.add(ComponentsUtils.createLabel(tmp.getValueAt(i, 0).toString()));
            DefaultTableModel models = new DefaultTableModel();
            sql.tableFill("SELECT * from MKZ_LIST_HEAD where FASON_SOLE='" + tmp.getValueAt(i, 0).toString() + "'", models);
            if (models.getRowCount() > 0) {
                pnlAttElem.add(new JTable(models));
            } else {
                pnlAttElem.add(ComponentsUtils.createLabel("Модели на данном артикуле отсутствуют"));
            }

        }

        pack();
        setCenter();

    }

}
