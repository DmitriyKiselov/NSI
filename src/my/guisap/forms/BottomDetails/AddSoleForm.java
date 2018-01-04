package my.guisap.forms.BottomDetails;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import my.guisap.componenst.EmptyForm;
import my.guisap.componenst.NewDataPanel;

/**
 *
 * @author KiselevDA
 */
public class AddSoleForm extends EmptyForm {

    NewDataPanel data = new NewDataPanel("LB_SOLE", "ID", "AddSoleForm", 1);
//    DataPanel data = new DataPanel("LB_SOLE", "ID", 1);

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
        pnlAttElem.add(data);
        pnlAttElem.setLayout(new BoxLayout(pnlAttElem, BoxLayout.X_AXIS));
        processing();
        pack();
        setCenter();
    }

    private void fillFields() {
        data.fillFields("SELECT * FROM LB_SOLE " + " where ART='" + art + "'", 1);
    }

    private void processing() {
        JTextField tmpField = (JTextField) data.getTextField(0);
        tmpField.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                data.setText(1, data.getText(0) + "/");
            }
        });
        tmpField.setEnabled(true);

        data.getTextField(0).addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                DefaultTableModel tmp = new DefaultTableModel();
                sql.tableFill("select count(name)+1 from LB_SOLE where name ='" + data.getText(0) + "'", tmp);
                data.setText(1, data.getText(0) + "/" + tmp.getValueAt(0, 0));
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
                field.setText(data.getText(0));
            }
            this.closeWindow();
        } else {
            JOptionPane.showMessageDialog(this, "Заполните поля отмеченные красным", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }

}
