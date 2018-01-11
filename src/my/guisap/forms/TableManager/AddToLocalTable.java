package my.guisap.forms.TableManager;

import javax.swing.JOptionPane;
import my.guisap.componenst.DataPanel;
import my.guisap.componenst.EmptyForm;
import my.guisap.utils.CreateFormUtils;

/**
 *
 * @author KiselevDA
 */
public class AddToLocalTable extends EmptyForm {

    DataPanel data;
    LocalTablesManager parentForm;

    public AddToLocalTable(String caption, String classFlag, boolean needToSave, boolean needSaveSize, String table, LocalTablesManager parentForm) {
        super(caption, classFlag, needToSave, needSaveSize);
        this.parentForm = parentForm;
        data = new DataPanel("SAPX_" + table, "CODE", 1);
        createFormFields();
    }

    public void createFormFields() {

        String[][] elements = {
            {"Код", "true"},
            {"Наименование", "true"}};

        data.addFields(elements, 0, 1, CreateFormUtils.DEFAULT_INSETS);
        data.setCheckFields(true, null);

        pnlAttElem.add(data);

        try {
            data.getTextField(0).setText(data.generateNewID());
        } catch (NullPointerException ex) {
            data.getTextField(0).setText("Невозможно сгенерировать ID");
        }

        pack();
        setCenter();
    }

    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        if (!data.checkID(0)) {
            if (data.saveToDB()) {
                log.logWriting("Добавлена запись " + data.getTextField(1).getText() + " в таблицу " + data.nameTable);
                parentForm.updateTable();
                closeWindow();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Данный ID уже присутствует в таблице", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }

}
