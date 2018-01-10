package my.guisap.forms.UpDetails;

import java.math.BigDecimal;
import javax.swing.JOptionPane;
import my.guisap.componenst.NewDataPanel;
import my.guisap.componenst.SaveForm;
import my.guisap.utils.CacheImage;

/**
 *
 * @author KiselevDA
 */
public class AddUpDetails extends SaveForm {

    NewDataPanel data = new NewDataPanel("LB_UP_DETAILS", "ID", "AddUpDetails", 1);
    StringBuilder generateIndex = new StringBuilder("0000A00/0");

    UpDetailsMain parentForm;
    String index = "";

    boolean newOn;

    public AddUpDetails(String caption, boolean needToSave, UpDetailsMain parentForm) {
        super(caption, needToSave);
        this.parentForm = parentForm;
        createFormFields(true);

    }

    public AddUpDetails(String caption, boolean needToSave, UpDetailsMain parentForm, String index, boolean newOn) {
        super(caption, needToSave);
        this.parentForm = parentForm;
        this.index = index;
        this.newOn = newOn;
        createFormFields(newOn);
        fillFields();
    }

    private void createFormFields(boolean addProcessing) {
        data.addLoadImageField(CacheImage.TYPE_UP, false, 0);
        data.setCheckFields(true);
        contentPanel.add(data);
        if (addProcessing) {
            processing();
        }
        pack();
        setCenter();
    }

    @Override
    public void fillFields() {
        data.fillFields("SELECT * FROM LB_UP_DETAILS " + " where INDEX_UP='" + index + "'", 1);
        if (!newOn) {
            data.blockFields(new int[]{0, 1, 2, 3, 4, 5, 6});
        }
    }

    private void processing() {
        data.getTextField(2).addCaretListener((ce) -> {
            String tmp = data.getCode(2);
            generateIndex.setCharAt(0, tmp.charAt(1));
            generateIndex();
        });
        data.getTextField(3).addCaretListener((ce) -> {
            String tmp = data.getCode(3);
            if (tmp.length() < 2) {
                tmp = "0" + tmp;
            }
            generateIndex.setCharAt(1, tmp.charAt(0));
            generateIndex.setCharAt(2, tmp.charAt(1));
            generateIndex();
        });
        data.getTextField(4).addCaretListener((ce) -> {
            String tmp = data.getCode(4);
            generateIndex.setCharAt(3, tmp.charAt(0));
            generateIndex();
        });
        data.getTextField(5).addCaretListener((ce) -> {
            String tmp = data.getCode(5);
            
            generateIndex();
        });
        data.getTextField(6).addCaretListener((ce) -> {
            String tmp = data.getCode(6);
            if (tmp.length() < 2) {
                tmp = "0" + tmp;
            }
            generateIndex.setCharAt(5, tmp.charAt(0));
            generateIndex.setCharAt(6, tmp.charAt(1));
            generateIndex();
        });
    }

    private void generateIndex() {
        BigDecimal x = (BigDecimal) sql.getObj("select count(ID) from LB_UP_DETAILS where INDEX_UP='" + generateIndex.toString() + "'");
        generateIndex.setCharAt(8, String.valueOf(x).charAt(0));
        data.setText(1, generateIndex.toString());
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
        if (!newOn) {
            String id = (String) sql.getObj("SELECT ID FROM LB_UP_DETAILS where" + " INDEX_UP='" + index + "'");
            if (data.updateDB(id, null)) {
                parentForm.fillTable();
                this.closeWindow();
            }
        } else if (data.saveToDB(data.generateID, extraFields)) {
            if (parentForm != null) {
                parentForm.fillTable();
            }
            this.closeWindow();
        } else {
            JOptionPane.showMessageDialog(this, "Заполните поля отмеченные красным", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }

}
