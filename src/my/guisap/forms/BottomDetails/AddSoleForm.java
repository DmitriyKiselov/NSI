package my.guisap.forms.BottomDetails;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import my.guisap.attributeRow;
import my.guisap.componenst.fields.CatalogField;
import my.guisap.componenst.DataPanel;
import my.guisap.componenst.EmptyForm;
import my.guisap.componenst.fields.FreeWriteField;
import my.guisap.componenst.fields.SpinnerField;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.CreateFormUtils;

/**
 *
 * @author KiselevDA
 */
public class AddSoleForm extends EmptyForm {

    DataPanel data = new DataPanel("LB_SOLE", "ID", 1);

    JTextField field;
    BottomDetails parentForm;

    String art = "";

    public AddSoleForm(String caption, String classFlag, boolean needToSave, boolean needSaveSize, BottomDetails parentForm) {
        super(caption, classFlag, needToSave, needSaveSize);
        this.parentForm = parentForm;
        createFormFields();
    }

    public AddSoleForm(String caption, String classFlag, boolean needToSave, boolean needSaveSize, BottomDetails parentForm, String art) {
        super(caption, classFlag, needToSave, needSaveSize);
        this.parentForm = parentForm;
        createFormFields();
        this.art = art;
        fillFields();
    }

    public AddSoleForm(String caption, String classFlag, boolean needToSave, boolean needSaveSize, JTextField field) {
        super(caption, classFlag, needToSave, needSaveSize);
        this.field = field;
        createFormFields();
    }

    private void createFormFields() {

        //названия полей без подгрузки из SAP
        String firstMasElem[][] = {
            {"Название фасона", "true"},
            {"Название артикула", "false"}};

        String secondMasElem[][] = {
            {"Общее описание принципов отделки", "true"},
            {"Ростовка", "true"}
        };

        String thirdMasElem[][] = {
            {"Толщина подошвы в пучках", "true"},
            {"Ширина подошвы в пятке", "true"},
            {"Длина следа", "true"}};

        data.addFields(firstMasElem, 0, 1, CreateFormUtils.DEFAULT_INSETS);
        data.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'LB_SOLE' and MAIN_CLASS = 'LB_SOLE' and PRIORITY<'7'" + SqlOperations.GROUP_BY, true, 0);
        data.addFields(secondMasElem, 0, 1, CreateFormUtils.DEFAULT_INSETS);
        data.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'LB_SOLE' and MAIN_CLASS = 'LB_SOLE' and PRIORITY>='7' and PRIORITY<'15'" + SqlOperations.GROUP_BY, true, 0);
        data.addFields(thirdMasElem, 0, 1, CreateFormUtils.DEFAULT_INSETS);
        data.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'LB_SOLE' and MAIN_CLASS = 'LB_SOLE' and PRIORITY>='15'" + SqlOperations.GROUP_BY, true, 0);
        data.setBorder(CreateFormUtils.defaultBorder);
//        data.setCheckFields(true, null);

        pnlAttElem.add(data);
        pnlAttElem.add(new attributeRow("Тест", "Тест", "Тест", "Тест", "Тест"));
        pnlAttElem.add(new CatalogField("Тест", "Тест", "Test", "Test", true));
        pnlAttElem.add(new FreeWriteField("Тест", "Тест", "Test", true));
        pnlAttElem.add(new SpinnerField("Тест", "Тест", "Test", "year", true));
        processing();

        pack();
        setCenter();
    }

    private void fillFields() {
        data.fillFields("SELECT * FROM LB_SOLE " + " where ART='" + art + "'", 1);
    }

    private void processing() {
        data.getTextField(0).addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                data.getTextField(1).setText(data.getTextField(0).getText() + "/");
            }
        });

        data.getTextField(0).addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                DefaultTableModel tmp = new DefaultTableModel();
                sql.tableFill("select count(name)+1 from LB_SOLE where name ='" + data.getTextField(0).getText() + "'", tmp);
                data.getTextField(1).setText(data.getTextField(0).getText() + "/" + tmp.getValueAt(0, 0));
            }
        });
    }

    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        saveToDB();
    }

    private void saveToDB() {
        String[] extraFields = new String[1];

        if (my.guisap.utils.SecurityManager.idGroup == 1) {
            extraFields[0] = "NS";
        } else {
            extraFields[0] = "R";
        }

        if (data.saveToDB(data.generateID, extraFields)) {
            if (parentForm != null) {
                parentForm.fillTableSole();
            } else if (field != null) {
                field.setText(data.getTextField(0).getText());
            }
            this.closeWindow();
        } else {
            JOptionPane.showMessageDialog(this, "Заполните поля отмеченные красным", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }

}
