/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms;

import my.guisap.componenst.EmptyForm;
import javax.swing.Box;
import javax.swing.JOptionPane;
import my.guisap.componenst.AttributePanel;
import my.guisap.GuiStaticVariables;
import my.guisap.utils.LogClass;
import my.guisap.attributeRow;
import my.guisap.attributeRowHeader;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author DaN
 */
public class FasonForm extends EmptyForm {

    String classFlag;
    LogClass log;
    ModelForm modelFr;

    public FasonForm(String caption, String classFlag, ModelForm modelFr) {
        super(caption, classFlag, false, false);
        this.modelFr = modelFr;

        log = LogClass.getInstance();
        sql.tableFill(SqlOperations.DATA_SELECTION + "'FASON' and MAIN_CLASS = '" + classFlag + "' " + SqlOperations.GROUP_BY, dataModel);

        this.classFlag = classFlag;
        headAttPanel = new AttributePanel(dataModel);
        atRowHed = new attributeRowHeader(
                dataModel.getValueAt(GuiStaticVariables.FIRST_ELEMENT, 0).toString(),
                dataModel.getValueAt(GuiStaticVariables.FIRST_ELEMENT, 1).toString(),
                dataModel.getValueAt(GuiStaticVariables.FIRST_ELEMENT, 2).toString(),
                dataModel.getValueAt(GuiStaticVariables.FIRST_ELEMENT, 3).toString(),
                headAttPanel, this, classFlag);
        pnlAttElem.add(atRowHed);
        pnlAttElem.add(headAttPanel);
        pnlAttElem.add(Box.createVerticalStrut(GuiStaticVariables.ELEMENT_STRUT));
        pack();
        setCenter();
    }

    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        int element = 0;
        if (atRowHed.txt.getText().equals("") || atRowHed.txt.getText().equals("N/A")) {
            JOptionPane.showMessageDialog(this, "Пожалуйста,перед сохранением," + "\n" + "выберите код фасона колодки!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (atRowHed.idKey != 0) {
            sql.dataUpdate(SqlOperations.BLOCK_FASON_PR_DELETE + atRowHed.idKey);
            for (attributeRow c : headAttPanel.rowList) {
                if (!c.getText().equals("")) {
                    sql.dataUpdate(SqlOperations.BLOCK_FASON_PR_SAVE + "('" + atRowHed.idKey + "','" + c.atnam + "','" + c.atbez + "','" + c.getText() + "')");
                } else {
                    sql.dataUpdate(SqlOperations.BLOCK_FASON_PR_SAVE + "('" + atRowHed.idKey + "','" + c.atnam + "','" + c.atbez + "','" + "" + "')");
                }
            }
        } else {
            element = sql.dataUpdate(SqlOperations.BLOCK_FASON_SAVE + "('" + atRowHed.code + "', '" + atRowHed.txt.getText().toString() + "','" + classFlag + "') " + SqlOperations.BLOCK_SAVE_SCOPE);
            for (attributeRow c : headAttPanel.rowList) {
                if (!c.getText().equals("")) {
                    sql.dataUpdate(SqlOperations.BLOCK_FASON_PR_SAVE + "('" + element + "','" + c.atnam + "','" + c.atbez + "','" + c.getText() + "')");
                } else {
                    sql.dataUpdate(SqlOperations.BLOCK_FASON_PR_SAVE + "('" + element + "','" + c.atnam + "','" + c.atbez + "','" + "" + "')");
                }
            }
            atRowHed.idKey = element;
        }
        saveLabel.setText("Фасон '" + atRowHed.txt.getText() + "' сохранен.");
        log.logWriting("сохранил фасон " + atRowHed.txt.getText());
        JOptionPane.showMessageDialog(this, "Фасон успешно сохранен!", "Сохранение", JOptionPane.INFORMATION_MESSAGE);
        headAttPanel.resetChange();
        if (modelFr != null) {
            modelFr.atRowMainHed.setText(atRowHed.txt.getText(), atRowHed.txt.getText());
        }
    }
}
