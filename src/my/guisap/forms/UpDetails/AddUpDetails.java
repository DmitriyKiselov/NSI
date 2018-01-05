/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms.UpDetails;

import java.math.BigDecimal;
import javax.swing.JOptionPane;
import my.guisap.componenst.EmptyForm;
import my.guisap.componenst.NewDataPanel;

/**
 *
 * @author KiselevDA
 */
public class AddUpDetails extends EmptyForm {
    
    NewDataPanel data = new NewDataPanel("LB_UP_DETAILS", "ID", "AddUpDetails", 1);
    StringBuilder generateIndex = new StringBuilder("0000A00/0");
    
    UpDetailsMain parentForm;
    String index = "";
    
    public AddUpDetails(String caption, String classFlag, boolean needToSave, boolean needSaveSize, UpDetailsMain parentForm) {
        super(caption, classFlag, needToSave, needSaveSize);
        this.parentForm = parentForm;
        createFormFields();
        
    }
    
    public AddUpDetails(String caption, String classFlag, boolean needToSave, boolean needSaveSize, UpDetailsMain parentForm, String index) {
        super(caption, classFlag, needToSave, needSaveSize);
        this.parentForm = parentForm;
        this.index = index;
        createFormFields();
        fillFields();
    }
    
    private void createFormFields() {
        pnlAttElem.add(data);
        processing();
        pack();
        setCenter();
    }
    
    private void fillFields() {
        data.fillFields("SELECT * FROM LB_UP_DETAILS " + " where INDEX_UP='" + index + "'", 1);
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
        System.out.println(x);
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
        
        if (data.saveToDB(data.generateID, extraFields)) {
            if (parentForm != null) {
                parentForm.fillTable();
            }
            this.closeWindow();
        } else {
            JOptionPane.showMessageDialog(this, "Заполните поля отмеченные красным", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }
    
}
