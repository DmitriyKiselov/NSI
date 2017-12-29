package my.guisap.forms.MKZ;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import my.guisap.GuiStaticVariables;
import my.guisap.componenst.DataPanel;
import my.guisap.componenst.EmptyForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.CreateFormUtils;

/**
 *
 * @author KiselevDA
 */
public class CreateArticleForm extends EmptyForm {

    DataPanel data = new DataPanel("MKZ_MODEL_ART", "ART", 1);

    MkzEditModel parent;

    String modelId;
    Integer artId;
    StringBuffer newModel;
    JComboBox comboBoxArt;

    public CreateArticleForm(String caption, String classFlag, String model, String lastArticul, String modelId, MkzEditModel parent) {
        super(caption, classFlag, true, false);
        setTitle("Создание артикула");
        this.parent = parent;
        this.modelId = modelId;

        lblHeader.setText(model);

        newModel = new StringBuffer(model);
        newModel.delete(newModel.length() - 2, newModel.length());
        newModel.append(lastArticul);

        artId = Integer.parseInt(lastArticul);

        String[][] id = {{"Артикул", "false"}};

        data.addFields(id, 0, 1, CreateFormUtils.DEFAULT_INSETS);
        data.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'NEW_ART' and MAIN_CLASS = 'NEW_ART' " + SqlOperations.GROUP_BY, true, 0);
        data.getTextField(0).setText(lastArticul);
        data.setCheckFields(true);

        pnlAttElem.add(data);

        JPanel panelArt = new JPanel();
        JLabel labelArt = new JLabel("Создать артикул на основе:");
        comboBoxArt = new javax.swing.JComboBox<>();
        comboBoxArt.setFont(GuiStaticVariables.globalFont);
        comboBoxArt.setModel(new javax.swing.DefaultComboBoxModel<>(createArrayForComboBox()));

        panelArt.add(labelArt);
        panelArt.add(comboBoxArt);

        pnlAttElem.add(panelArt);
        pnlAttElem.add(Box.createVerticalStrut(GuiStaticVariables.ELEMENT_STRUT));

        pack();
        setCenter();
    }

    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        if (data.checkFields()) {
            String insertQuery = "insert into GUI_SAP.MKZ_MODEL_ART (ART,ID, COLOR,GROUPE_MAT_UPPER,MAT_UPPER,MODEL_ID,ART_ID,STATUS) VALUES('" + newModel.toString() + "','1','"
                    + data.getTextField(1).getText() + "','" + data.getTextField(2).getText() + "','" + data.getTextField(3).getText() + "','" + modelId + "','" + artId.toString() + "','" + "F')";
            sql.SendQuery(insertQuery);

            parent.Btn_Add_Art(newModel.toString(), true, comboBoxArt.getSelectedItem().toString());
            parent.fillArt(data, newModel.toString());

            log.logWriting("Заведен артикул: " + newModel.toString());
            this.isClosed = true;
            this.closeWindow();
        } else {
            JOptionPane.showMessageDialog(this, "Заполните все поля", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }

    public String[] createArrayForComboBox() {
        DefaultTableModel tmp = new DefaultTableModel();
        sql.tableFill("select ART from MKZ_MODEL_ART where MODEL_ID='" + modelId + "' ORDER BY ART", tmp);
        String[] result = new String[tmp.getRowCount() + 1];
        result[0] = "";
        for (int i = 0; i < tmp.getRowCount(); i++) {
            result[i + 1] = tmp.getValueAt(i, 0).toString();
        }
        return result;
    }
}
