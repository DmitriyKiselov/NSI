package my.guisap.componenst;

import baseclass.BInternalFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import my.guisap.GuiStaticVariables;
import my.guisap.attributeRow;
import my.guisap.attributeRowHeader;
import my.guisap.attributeRowMainHeader;
import my.guisap.utils.ComponentsUtils;

/**
 *
 * @author dima
 */
public class EmptyForm extends BInternalFrame {

    public boolean needToSave;
    public AttributePanel headAttPanel;
    public attributeRowHeader atRowHed;
    public attributeRow atRow;
    public attributeRowMainHeader atRowMainHed;
    public DefaultTableModel generalModel = new DefaultTableModel(new Object[][]{}, new String[]{});
    public DefaultTableModel dataModel = new DefaultTableModel(new Object[][]{}, new String[]{});
    public DefaultTableModel sizeRowModel = new DefaultTableModel(new Object[][]{}, new String[]{});
    public PreparedStatement ps;
    public JButton saveButt = ComponentsUtils.createBtn("Сохранить", 130, 23, true);
    public JLabel saveLabel = new JLabel("");
    public JPanel pnlCenter = new JPanel();
    public JPanel pnlAttElem = new JPanel();
    public JPanel pnlSouth = new JPanel();
    public JPanel pnlRostAndImage = new JPanel();
    public JFormattedTextField dataTxt;
    public JLabel lblHeader;

    public EmptyForm(String caption, String classFlag, boolean needToSave, boolean needSaveSize) {

        this.needToSave = needToSave;
        this.needSaveSize = needSaveSize;

        setMaximumSize(new Dimension(GuiStaticVariables.screenWidth, GuiStaticVariables.screenHeight));
        setClosable(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setMaximizable(false);
        this.setTitle(caption);
//----------------------------------------------------------------------------------
        SaveButtInitial();
//------------------------------------------------------------------------------------
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        JPanel pnlNorth = new JPanel();
        lblHeader = new JLabel(caption);
        lblHeader.setFont(new Font("Arial", 1, 18));
        pnlNorth.add(lblHeader);
        pnlCenter.add(pnlNorth);
        pnlCenter.add(Box.createVerticalStrut(GuiStaticVariables.ELEMENT_STRUT));

        pnlAttElem.setLayout(new BoxLayout(pnlAttElem, BoxLayout.Y_AXIS));
        pnlCenter.add(pnlAttElem);

        pnlSouth.removeAll();
        pnlSouth.setLayout(new BoxLayout(pnlSouth, BoxLayout.X_AXIS));
        saveLabel.setFont(new Font("Arial", 1, (int) (16 * GuiStaticVariables.scaleHeight)));
//        pnlSouth.add(saveLabel);
        pnlSouth.add(Box.createHorizontalGlue());
        addSaveButtToPSouth();

        pnlCenter.add(pnlSouth);
        //  pnlCenter.add(Box.createVerticalStrut(GuiStaticVariables.ELEMENT_STRUT));
        getContentPane().add(pnlCenter, BorderLayout.CENTER);
        getContentPane().add(Box.createHorizontalStrut(GuiStaticVariables.INDENT_STRUT), BorderLayout.EAST);
        getContentPane().add(Box.createHorizontalStrut(GuiStaticVariables.INDENT_STRUT), BorderLayout.WEST);
        getContentPane().add(Box.createVerticalStrut(GuiStaticVariables.INDENT_STRUT), BorderLayout.SOUTH);
        getContentPane().add(Box.createVerticalStrut(GuiStaticVariables.INDENT_STRUT), BorderLayout.NORTH);
    }

    public final void addSaveButtToPSouth() {
        pnlSouth.add(saveButt);
    }

    public void SaveButtInitial() {
        saveButt.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
    }

    public void saveLblUpdating() {
        saveLabel.setText("");
    }

    public boolean formInternalFrameClosed(InternalFrameEvent evt) {
        boolean flag = true;
        int answer;
        Object[] options = {"Да", "Нет"};
        if (needToSave) {
            answer = JOptionPane.showOptionDialog(this,
                    "Вы действительно хотите выйти без сохранения?",
                    "Внимание",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (answer == 0) {
                evt.getInternalFrame().setVisible(false);
                evt.getInternalFrame().dispose();
            } else {
                flag = false;
            }
            return flag;
        }
        evt.getInternalFrame().setVisible(false);
        evt.getInternalFrame().dispose();
        return flag;
    }

    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
    }

    public void closeWindow() {
        setVisible(false);
        dispose();
    }

}
