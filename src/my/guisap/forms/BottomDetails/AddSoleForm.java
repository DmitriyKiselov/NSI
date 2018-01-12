package my.guisap.forms.BottomDetails;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import my.guisap.componenst.NewDataPanel;
import my.guisap.componenst.SaveForm;
import my.guisap.utils.CacheImage;
import my.guisap.utils.ComponentsUtils;

/**
 *
 * @author KiselevDA
 */
public class AddSoleForm extends SaveForm {

    JTextField field;
    BottomDetails parentForm;

    NewDataPanel data = new NewDataPanel("LB_SOLE", "ID", "AddSoleForm", 1);

    JButton addLast = ComponentsUtils.createBtn("Добавить связь с колодкой", 200, 25, true);
    ArrayList<NewDataPanel> listLast = new ArrayList<>();

    String art = "";

    boolean createNew;

    public AddSoleForm(String caption, boolean needToSave, BottomDetails parentForm) {
        super(caption, needToSave);
        createNew = true;
        this.parentForm = parentForm;
        createFormFields(true);
    }

    public AddSoleForm(String caption, boolean needToSave, BottomDetails parentForm, String art, boolean newOn) {
        super(caption, needToSave);
        this.parentForm = parentForm;
        this.createNew = newOn;
        createFormFields(newOn);
        this.art = art;
        fillFields();
    }

    public AddSoleForm(String caption, boolean needToSave, JTextField field) {
        super(caption, needToSave);
        this.field = field;
        createFormFields(true);
    }

    private void createFormFields(boolean addProcessing) {
        data.addLoadImageField(CacheImage.TYPE_SOLE, false, 0);
//        data.setCheckFields(true);
        contentPanel.add(data);
        contentPanel.add(addLast);

        addLast.addActionListener((ae) -> {
            NewDataPanel tmp = new NewDataPanel("FASON_LINK", "SOLE", "AddHeelFormLink", 1);
            listLast.add(tmp);
            
            contentPanel.add(tmp);
            pack();
        });

        if (addProcessing) {
            processing();
        }

        pack();
        setCenter();
    }

    @Override
    public void fillFields() {
        data.fillFields("SELECT * FROM LB_SOLE " + " where ART='" + art + "'", 1);
        if (!createNew) {
            data.blockFields(new int[]{0, 1});
        }
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
        String[][] extraFields = {
            {"STATUS", ""}};

        if (my.guisap.utils.SecurityManager.idGroup == 1) {
            extraFields[0][1] = "NS";
        } else {
            extraFields[0][1] = "R";
        }

        if (!createNew) {
            String id = (String) sql.getObj("SELECT ID FROM LB_SOLE " + " where ART='" + art + "'");
            if (data.updateDB(id, null)) {
                parentForm.fillTableHeel();
                this.closeWindow();
            }
        } else if (data.saveToDB(data.generateID, extraFields)) {
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
