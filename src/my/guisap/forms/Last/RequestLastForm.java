package my.guisap.forms.Last;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import my.guisap.GuiStaticVariables;
import my.guisap.forms.MKZ.MkzAddModel;
import my.guisap.attributeRow;
import my.guisap.componenst.EmptyForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.CreateFormUtils;

/**
 *
 * @author KiselevDA
 */
public class RequestLastForm extends EmptyForm {
    
    String model;
    JPanel mainPanel;
    JTextField info;
    MkzAddModel parentForm;
    String[][] fields;
    
    public RequestLastForm(String caption, String classFlag, String model) {
        super(caption, classFlag, true, true);
        if (model != null) {
            this.model = model;
        }
        createFormFields();
    }
    
    public RequestLastForm(String caption, String classFlag, String model, MkzAddModel parentForm, String[][] fields) {
        super(caption, classFlag, true, true);
        if (model != null) {
            this.model = model;
        }
        this.parentForm = parentForm;
        this.fields = fields;
        createFormFields();
        fillFields();
    }
    
    private void createFormFields() {
        headAttPanel = CreateFormUtils.CreateAttributePanel("'LAST_REQUEST' and MAIN_CLASS = 'LAST_REQUEST' ", true, 0);
        pnlAttElem.add(headAttPanel);
        pnlAttElem.setBorder(CreateFormUtils.defaultBorder);
        pnlAttElem.add(Box.createVerticalStrut(GuiStaticVariables.ELEMENT_STRUT));
        JPanel infoPanel = new JPanel(new GridBagLayout());
        info = ComponentsUtils.createField("", 100, 23, true);
        infoPanel.add(ComponentsUtils.createLabel("Примечание:"),
                new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        infoPanel.add(info,
                new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        pnlAttElem.add(infoPanel);
        
        pack();
        setCenter();
    }
    
    private void fillFields() {
        DefaultTableModel tmpModel = new DefaultTableModel();
        sql.tableFill(SqlOperations.LAST_REQUEST + " where MODEL='" + model + "'", tmpModel);
        if (tmpModel.getRowCount() == 1) {
            int count = 2;
            for (int i = 0; i < headAttPanel.rowList.size(); i++) {
                if (tmpModel.getValueAt(0, count) != null) {
                    headAttPanel.rowList.get(i).txt.setText(tmpModel.getValueAt(0, count).toString());
                }
                count++;
            }
            if (tmpModel.getValueAt(0, count) != null) {
                info.setText(tmpModel.getValueAt(0, count).toString());
            }
        } else {
            // при создании новой заявки подгружаются поля из основной формы
            for (int i = 0; i < headAttPanel.rowList.size(); i++) {
                for (String[] field : fields) {
                    //field[][] содержит название поля и значение, тут идет проверка на совпадение названия поля
                    if (headAttPanel.rowList.get(i).atbez.equals(field[0])) {
                        if (!field[1].equals("")) {
                            headAttPanel.rowList.get(i).btn.setEnabled(false);
                            headAttPanel.rowList.get(i).txt.setText(field[1]);
                        }
                    }
                }
            }
        }
        headAttPanel.rowList.get(1).txt.setEnabled(true);
    }
    
    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        saveToDB();
    }
    
    private void saveToDB() {
        DefaultTableModel tmpModel = new DefaultTableModel();
        sql.tableFill(SqlOperations.LAST_REQUEST + " where MODEL='" + model + "'", tmpModel);
        ArrayList<String> data = createQuery(headAttPanel.rowList, info);
        String query;
        switch (tmpModel.getRowCount()) {
            case 0:
                query = "insert into LAST_REQUEST values (SEQ_LAST_REQUEST.Nextval, '" + model + "', sysdate,  "
                        + data.get(0) + " " + data.get(1) + " " + data.get(2) + "" + data.get(3) + " " + data.get(4) + ""
                        + data.get(5) + "" + data.get(6) + "" + data.get(7) + "" + data.get(8) + ", 'OP')";
                sql.SendQuery(query);
                parentForm.blockFirstStep(false);
                parentForm.updateStatusTo("L");
                break;
            case 1:
                query = "update LAST_REQUEST set DATE_CREATE=sysdate, DESIGNER=" + data.get(0) + " FASON_LAST=" + data.get(1) + ""
                        + " TYPE_LAST=" + data.get(2) + " CONSTRUCTION_SOLE_NODE=" + data.get(3) + " FASON_SOLE=" + data.get(4) + " FASON_HEEL=" + data.get(5) + ""
                        + " MAT_LINING=" + data.get(6) + " COMPLETENESS_FOOT=" + data.get(7) + " INFO=" + data.get(8) + " WHERE MODEL='" + model + "'";
                sql.SendQuery(query);
                parentForm.blockFirstStep(false);
                parentForm.updateStatusTo("L");
                break;
            default:
                JOptionPane.showMessageDialog(this, "Ошибка сохранения", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                break;
        }
        closeWindow();
    }

    //функция которая обьединяет значения всех полей для заполнения в одном массиве
    public ArrayList createQuery(ArrayList<attributeRow> rowList, JTextField textField) {
        ArrayList<String> result = new ArrayList<>();
        for (attributeRow art : rowList) {
            result.add("'" + art.txt.getText() + "',");
        }
        result.add("'" + textField.getText() + "'");
        return result;
    }
    
}
