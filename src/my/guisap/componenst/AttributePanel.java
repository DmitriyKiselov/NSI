/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.componenst;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import my.guisap.attributeRow;

/**
 *
 * @author DaN
 */
public class AttributePanel extends javax.swing.JPanel {

    public ArrayList<attributeRow> rowList = new ArrayList<>();
    attributeRow atRow;

    public AttributePanel(DefaultTableModel dataModel) {
        AttributePanel_1(dataModel, false, 1);
    }

    public AttributePanel(DefaultTableModel dataModel, boolean SetEn) {
        AttributePanel_1(dataModel, SetEn, 1);
    }

    public AttributePanel(DefaultTableModel dataModel, boolean SetEn, int FirstEelem) {
        AttributePanel_1(dataModel, SetEn, FirstEelem);
    }

    public void AttributePanel_1(DefaultTableModel dataModel, boolean SetEn, int FirstEelem) {
        setLayout(new GridLayout(dataModel.getRowCount(), 1));
        for (int Count = FirstEelem; Count < dataModel.getRowCount(); Count++) {
            atRow = new attributeRow(
                    dataModel.getValueAt(Count, 0).toString(),
                    dataModel.getValueAt(Count, 1).toString(),
                    dataModel.getValueAt(Count, 2).toString(),
                    dataModel.getValueAt(Count, 3).toString(),
                    null, SetEn);
            rowList.add(atRow);
            add(atRow);
        }
    }

    public void setTextAll(DefaultTableModel dataHeaderModel) {
        for (int i = 0; i < dataHeaderModel.getRowCount(); i++) {
            for (attributeRow atR : rowList) {
                if (atR.atnam.equals(dataHeaderModel.getValueAt(i, 0))) {
                    atR.setText(dataHeaderModel.getValueAt(i, 1).toString(), dataHeaderModel.getValueAt(i, 2).toString());
                }
            }
        }
        resetChange();
    }

    public void resetChange() {
        for (attributeRow atR : rowList) {
            atR.editFlag = false;
        }
    }
}
