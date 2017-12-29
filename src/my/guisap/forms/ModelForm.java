/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms;

import my.guisap.componenst.EmptyForm;
import java.awt.Dimension;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import my.guisap.componenst.AttributePanel;
import my.guisap.FormRegister;
import my.guisap.GuiStaticVariables;
import my.guisap.utils.LogClass;
import my.guisap.attributeRow;
import my.guisap.attributeRowHeader;
import my.guisap.attributeRowMainHeader;
import my.guisap.sql.SqlOperations;
import org.apache.poi.ss.formula.FormulaParseException;

/**
 * @author DaN
 */
public class ModelForm extends EmptyForm {

    LogClass log;
    JLabel lbl = new JLabel("Год выпуска");
    JLabel sizeLbl = new JLabel("");
    JTextField tx1 = new JTextField("");
    JTextField tx2 = new JTextField("");
//    JButton sizeButt = new JButton("Ростовочная шкала");
    JButton technButt = new JButton("Тех. признаки модели");
    JButton technButt2 = new JButton("Тех. признаки полуфабрикатов");
    String classFlag;
    FormRegister fR;

    public ModelForm(String caption, String classFlag) {

        super(caption, classFlag, false, false);
        this.classFlag = classFlag;
        log = LogClass.getInstance();
        modelElemFill();
        addElem();
    }

    private void modelElemFill() {
        sql.tableFill(SqlOperations.DATA_SELECTION + "'MOD' and MAIN_CLASS = '" + classFlag + "' " + SqlOperations.GROUP_BY, dataModel);
        headAttPanel = new AttributePanel(dataModel);

        if (!classFlag.equals(GuiStaticVariables.SALE_SHOES)) {
            sql.tableFill(SqlOperations.DATA_SELECTION + "'FASON' and MAIN_CLASS = '" + classFlag + "' " + SqlOperations.GROUP_BY, generalModel);
            atRowMainHed = new attributeRowMainHeader(generalModel.getValueAt(GuiStaticVariables.FIRST_ELEMENT, 0).toString(), generalModel.getValueAt(GuiStaticVariables.FIRST_ELEMENT, 1).toString(), generalModel.getValueAt(GuiStaticVariables.FIRST_ELEMENT, 2).toString(), generalModel.getValueAt(GuiStaticVariables.FIRST_ELEMENT, 3).toString(), classFlag);
            pnlAttElem.add(atRowMainHed);
        }
        atRowHed = new attributeRowHeader(dataModel.getValueAt(GuiStaticVariables.FIRST_ELEMENT, 0).toString(), dataModel.getValueAt(GuiStaticVariables.FIRST_ELEMENT, 1).toString(), dataModel.getValueAt(GuiStaticVariables.FIRST_ELEMENT, 2).toString(), dataModel.getValueAt(GuiStaticVariables.FIRST_ELEMENT, 3).toString(), headAttPanel, this, classFlag);

        pnlAttElem.add(atRowHed);

        pnlAttElem.add(headAttPanel);

        pnlAttElem.add(Box.createVerticalStrut(GuiStaticVariables.ELEMENT_STRUT));
    }

    private void addElem() {
        //-------------------Год выпуска------------------------------
        JPanel pnlDate = new JPanel();
        pnlDate.setLayout(new BoxLayout(pnlDate, BoxLayout.X_AXIS));
        pnlDate.add(lbl);
        pnlDate.add(Box.createHorizontalGlue());
        pnlDate.add(Box.createHorizontalStrut(GuiStaticVariables.TIGHTLE_STRUT));
        try {
            dataTxt = new JFormattedTextField(new MaskFormatter("****"));
        } catch (ParseException ex) {
            Logger.getLogger(ModelForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char a = e.getKeyChar();
                if (!Character.isDigit(a) && (a != '\b')) {
                    e.consume();
                }
            }
        });
        dataTxt.setPreferredSize(new Dimension((int) (227 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
        dataTxt.setMaximumSize(new Dimension((int) (227 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
        pnlDate.add(dataTxt);
        pnlAttElem.add(pnlDate);
        //----------------------------------------------------------------------------
        pnlAttElem.add(Box.createVerticalStrut(GuiStaticVariables.ELEMENT_STRUT));
        //----------Кнопка технологических признаков----------------------------------
        JPanel pnlTechn = new JPanel();
        pnlTechn.setLayout(new BoxLayout(pnlTechn, BoxLayout.X_AXIS));
        pnlTechn.add(Box.createHorizontalGlue());
        pnlTechn.add(technButt);
        pnlAttElem.add(pnlTechn);
        //----------Кнопка технологических признаков2----------------------------------
        JPanel pnlTechn2 = new JPanel();
        pnlTechn.setLayout(new BoxLayout(pnlTechn, BoxLayout.X_AXIS));
        pnlTechn.add(Box.createHorizontalGlue());
        pnlTechn.add(technButt2);
        pnlAttElem.add(pnlTechn2);
        //---------------------------------------------------------------------------
        pnlAttElem.add(Box.createVerticalStrut(GuiStaticVariables.ELEMENT_STRUT));

        //-----------------ростовка и картинка-----------------------------------
        pnlRostAndImage.setLayout(new BoxLayout(pnlRostAndImage, BoxLayout.X_AXIS));
//        pnlRost.add(sizeLbl);
        tx1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char a = e.getKeyChar();
                if (!Character.isDigit(a) && (a != '\b')) {
                    e.consume();
                }
            }
        });
        tx2.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char a = e.getKeyChar();
                if (!Character.isDigit(a) && (a != '\b')) {
                    e.consume();
                }
            }
        });
        tx1.setPreferredSize(new Dimension((int) (30 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
        tx1.setMaximumSize(new Dimension((int) (30 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
        tx2.setPreferredSize(new Dimension((int) (30 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
        tx2.setMaximumSize(new Dimension((int) (30 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));

        pnlRostAndImage.add(Box.createHorizontalGlue());

        pnlRostAndImage.add(new JLabel("Размерный ряд: "));
        pnlRostAndImage.add(tx1);
        pnlRostAndImage.add(new JLabel(" - "));
        pnlRostAndImage.add(tx2);
        pnlAttElem.add(pnlRostAndImage);
        //----------------------------------------------------------------------------
        //pnlAttElem.add(Box.createVerticalStrut(GuiStaticVariables.ELEMENT_STRUT));
        //---------------------------------------------------------------------
//        JPanel img = new JPanel();
//        img.setLayout(new BoxLayout(img, BoxLayout.X_AXIS));
//        img.add(setImage("logo2.jpg"));
//        img.add(Box.createHorizontalGlue());
//        pnlAttElem.add(img);
        //----------------------------------------------------------------------
        pnlAttElem.add(Box.createVerticalStrut(GuiStaticVariables.ELEMENT_STRUT));
        //---------------------------------------------------------------------
        pack();
        setCenter();

//        sizeButt.addActionListener(new java.awt.event.ActionListener() {
//
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                sizeButtPresed(evt);
//            }
//        });
        txtUpdate();
//        sizeLblUpdate();
    }

    public void txtUpdate() {
        tx1.setText("");
        tx2.setText("");
        System.out.println(atRowHed.idKey);

        System.out.println(atRowHed.txt.getText());
        if (atRowHed.idKey != 0) {
            sizeRowModel = new DefaultTableModel(0, 0);
            ps = sql.preparingTableFill(SqlOperations.MODEL_SIZE_ROW_SELECTION);
            if (ps != null) {
                try {
                    ps.setInt(1, atRowHed.idKey);
                    sql.psTableFill(sizeRowModel, ps);
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ModelForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                tx1.setText(Integer.toString((int) Double.parseDouble(sizeRowModel.getValueAt(0, 0).toString())));
                tx2.setText(Integer.toString((int) Double.parseDouble(sizeRowModel.getValueAt(sizeRowModel.getRowCount() - 1, 0).toString())));
            } catch (ArrayIndexOutOfBoundsException ex) {
            }
        }
    }

    public void sizeLblUpdate() {
        sizeLbl.setText("");
        if (atRowHed.idKey != 0) {
            sizeRowModel = new DefaultTableModel(0, 0);
            ps = sql.preparingTableFill(SqlOperations.MODEL_SIZE_ROW_SELECTION);
            if (ps != null) {
                try {
                    ps.setInt(1, atRowHed.idKey);
                    sql.psTableFill(sizeRowModel, ps);
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ModelForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            int count = 0;
            for (int rowInd = 1; rowInd < sizeRowModel.getRowCount(); rowInd++) {
                if (Double.parseDouble(sizeRowModel.getValueAt(rowInd - 1, 0).toString()) < 15) {
                    if (Double.parseDouble(sizeRowModel.getValueAt(rowInd - 1, 0).toString()) + 0.5
                            != Double.parseDouble(sizeRowModel.getValueAt(rowInd, 0).toString())) {
                        sizeLbl.setText(sizeLbl.getText() + sizeRowModel.getValueAt(rowInd - 1, 0).toString());
                        sizeLbl.setText(sizeLbl.getText() + "; ");
                        count = 0;
                    } else {
                        if (count == 0) {
                            sizeLbl.setText(sizeLbl.getText() + sizeRowModel.getValueAt(rowInd - 1, 0).toString());
                            sizeLbl.setText(sizeLbl.getText() + " - ");
                            count++;
                        }
                    }
                }
                if (Double.parseDouble(sizeRowModel.getValueAt(rowInd - 1, 0).toString()) >= 15) {
                    if (Double.parseDouble(sizeRowModel.getValueAt(rowInd - 1, 0).toString()) + 1
                            != Double.parseDouble(sizeRowModel.getValueAt(rowInd, 0).toString())) {
                        sizeLbl.setText(sizeLbl.getText() + sizeRowModel.getValueAt(rowInd - 1, 0).toString());
                        sizeLbl.setText(sizeLbl.getText() + "; ");
                        count = 0;
                    } else {
                        if (count == 0) {
                            sizeLbl.setText(sizeLbl.getText() + sizeRowModel.getValueAt(rowInd - 1, 0).toString());
                            sizeLbl.setText(sizeLbl.getText() + " - ");
                            count++;
                        }
                    }
                }
            }
            try {
                sizeLbl.setText(sizeLbl.getText() + sizeRowModel.getValueAt(sizeRowModel.getRowCount() - 1, 0).toString());
            } catch (ArrayIndexOutOfBoundsException ex) {
            }
        }
    }

    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        int element = 0;
        if (!classFlag.equals(GuiStaticVariables.SALE_SHOES)) {
            if (atRowHed.txt.getText().equals("") || atRowMainHed.txt.getText().equals("") || atRowHed.txt.getText().equals("N/A") || atRowMainHed.txt.getText().equals("N/A")) {
                JOptionPane.showMessageDialog(this, "Пожалуйста, убедитесь в том, что поля код фасона колодки" + "\n" + "и модель заполнены!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else {
            if (atRowHed.txt.getText().equals("") || atRowHed.txt.getText().equals("N/A")) {
                JOptionPane.showMessageDialog(this, "Пожалуйста, убедитесь в том, что поле модель заполнено!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        try {
            if (tx1.getText().equals("") || tx2.getText().equals("")
                    || Integer.parseInt(tx1.getText()) <= 0
                    || Integer.parseInt(tx1.getText()) > 70
                    || Integer.parseInt(tx2.getText()) <= 0
                    || Integer.parseInt(tx2.getText()) > 70) {
                JOptionPane.showMessageDialog(this, "Пожалуйста, убедитесь в том, что интервал размерного ряда заполнен верно!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (FormulaParseException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Пожалуйста, убедитесь в том, что интервал размерного ряда заполнен верно!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (dataTxt.getText().length() < 4 || dataTxt.getText().equals("    ")) {
            JOptionPane.showMessageDialog(this, "Пожалуйста, убедитесь в том, что поле год выпуска заполнен верно!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (atRowHed.idKey != 0) {
            sql.dataUpdate(SqlOperations.BLOCK_MODEL_PR_DELETE + atRowHed.idKey);
            sql.dataUpdate(SqlOperations.MODEL_SIZE_DELETE + atRowHed.idKey);
            if (!classFlag.equals(GuiStaticVariables.SALE_SHOES)) {
                sql.dataUpdate(SqlOperations.BLOCK_MODEL_UPDATE + "RELIDKEY = " + atRowMainHed.idKey + "where IDKEY = " + atRowHed.idKey);
            }
            for (attributeRow atR : headAttPanel.rowList) {
                if (!atR.getText().equals("")) {
                    sql.dataUpdate(SqlOperations.BLOCK_MODEL_PR_SAVE + "('" + atRowHed.idKey + "','" + atR.atnam + "','" + atR.atbez + "','" + atR.getText() + "')");
                } else {
                    sql.dataUpdate(SqlOperations.BLOCK_MODEL_PR_SAVE + "('" + atRowHed.idKey + "','" + atR.atnam + "','" + atR.atbez + "','" + "" + "')");
                }
            }
            sql.dataUpdate(SqlOperations.BLOCK_MODEL_PR_SAVE + "('" + atRowHed.idKey + "','EDITION_YEAR','Год выпуска','" + dataTxt.getText().toString() + "')");
            int txt1, txt2;
            if (Integer.parseInt(tx1.getText()) <= Integer.parseInt(tx2.getText())) {
                txt1 = Integer.parseInt(tx1.getText());
                txt2 = Integer.parseInt(tx2.getText());
            } else {
                txt1 = Integer.parseInt(tx2.getText());
                txt2 = Integer.parseInt(tx1.getText());
            }
            for (int i = txt1; i < txt2 + 1; i++) {
                sql.dataUpdate(SqlOperations.SIZE_MODEL_SAVE + "('" + atRowHed.idKey + "','" + i + ".0','" + i + ".0')");
            }
        } else {
            if (!classFlag.equals(GuiStaticVariables.SALE_SHOES)) {
                element = sql.dataUpdate(SqlOperations.BLOCK_MODEL_SAVE + "('" + atRowHed.code + "', '" + atRowHed.txt.getText().toString() + "'," + atRowMainHed.idKey + ",'" + classFlag + "') " + SqlOperations.BLOCK_SAVE_SCOPE);
            } else {
                element = sql.dataUpdate(SqlOperations.BLOCK_MODEL_SAVE + "('" + atRowHed.code + "', '" + atRowHed.txt.getText().toString() + "'," + "null" + ",'" + classFlag + "') " + SqlOperations.BLOCK_SAVE_SCOPE);
            }
            for (attributeRow atR : headAttPanel.rowList) {
                if (!atR.getText().equals("")) {
                    sql.dataUpdate(SqlOperations.BLOCK_MODEL_PR_SAVE + "('" + element + "','" + atR.atnam + "','" + atR.atbez + "','" + atR.getText() + "')");
                } else {
                    sql.dataUpdate(SqlOperations.BLOCK_MODEL_PR_SAVE + "('" + element + "','" + atR.atnam + "','" + atR.atbez + "','" + "" + "')");
                }
            }
            sql.dataUpdate(SqlOperations.BLOCK_MODEL_PR_SAVE + "('" + element + "','EDITION_YEAR','Год выпуска','" + dataTxt.getText().toString() + "')");
            int txt1, txt2;
            if (Integer.parseInt(tx1.getText()) <= Integer.parseInt(tx2.getText())) {
                txt1 = Integer.parseInt(tx1.getText());
                txt2 = Integer.parseInt(tx2.getText());
            } else {
                txt1 = Integer.parseInt(tx2.getText());
                txt2 = Integer.parseInt(tx1.getText());
            }
            for (int i = txt1; i < txt2 + 1; i++) {
                sql.dataUpdate(SqlOperations.SIZE_MODEL_SAVE + "('" + element + "','" + i + ".0','" + i + ".0')");
            }
            atRowHed.idKey = element;
        }
        saveLabel.setText("Модель '" + atRowHed.txt.getText() + "' сохранена.");
        log.logWriting("сохранил модель " + atRowHed.txt.getText());
        JOptionPane.showMessageDialog(this, "Запись успешно сохранена!", "Сохранение", JOptionPane.INFORMATION_MESSAGE);
        headAttPanel.resetChange();
    }
}
