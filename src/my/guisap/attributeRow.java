package my.guisap;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author dima
 */
public class attributeRow extends JPanel {

    public GuideForm gf;
    public JButton btn;
    public JLabel lbl;
    public JTextField txt;
    public String tablenName;
    public String atinn;
    public String atnam;
    public String atbez;
    public String atprt;
    public String code = "";
    public String name = "";
    public String infoForCheck = "";
    public boolean editFlag = false;
    String classFlag;
    public SqlOperations sql = new SqlOperations();
    DefaultTableModel guideModel = new DefaultTableModel(0, 0);

    public attributeRow(String atinn, String atnam, String atbez, String atprt, String classFlag) {
        attributeRow_1(atinn, atnam, atbez, atprt, classFlag, false, null);
    }

    public attributeRow(String atinn, String atnam, String atbez, String atprt, String classFlag, boolean SetEnabled) {
        attributeRow_1(atinn, atnam, atbez, atprt, classFlag, SetEnabled, null);
    }

    public attributeRow(String atinn, String atnam, String atbez, String atprt, String classFlag, boolean SetEnabled, String infoForCheck) {
        attributeRow_1(atinn, atnam, atbez, atprt, classFlag, SetEnabled, infoForCheck);
    }

    public void attributeRow_1(String atinn, String atnam, String atbez, String atprt, String classFlag, boolean SetEnabled, String infoForCheck) {
        btn = new btnGuid();
        btn.setEnabled(SetEnabled);
        lbl = new JLabel(atbez);
        txt = new JTextField();
        txt.setPreferredSize(new Dimension((int) (200 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
        txt.setMaximumSize(new Dimension((int) (200 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
        txt.setEnabled(false);
        txt.setFont(GuiStaticVariables.globalFont);
        lbl.setFont(GuiStaticVariables.globalFont);
        this.atinn = atinn;
        this.atnam = atnam;
        this.atbez = atbez;
        this.atprt = atprt;
        this.classFlag = classFlag;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(lbl);
        add(Box.createHorizontalGlue());
        add(Box.createHorizontalStrut(GuiStaticVariables.TIGHTLE_STRUT));
        add(txt);
        add(btn);
        //clear();
    }

    public void setText(String code, String name) {
        if (!this.name.equals(name)) {
            editFlag = true;
        }
        this.name = name;
        this.code = code;
        txt.setText(name);
    }

    public String getText() {
        return name;
    }

    public String getCode() {
        if (code.equals("")) {
            return (String) sql.getObj("SELECT CODE FROM SAPX_" + atprt + " WHERE NAME='" + txt.getText() + "'");
        } else {
            return code;
        }
    }

    public void clear() {
        guideModel = new DefaultTableModel(0, 0);
        if (atprt.equals("CAWNT")) {
            sql.tableFill(SqlOperations.GUIDE_CAWNT + "where a.CODE = '" + atinn + "'", guideModel);
        } else {
            sql.tableFill(SqlOperations.GUIDE + atprt + " a", guideModel);
        }
        this.name = "";
        txt.setText(name);
        this.code = "";

        if (guideModel.getRowCount() == 1) {
            this.name = guideModel.getValueAt(0, 1).toString();
            txt.setText(name);
            this.code = guideModel.getValueAt(0, 1).toString();
        }
    }

    public void callGuideForm() {
        gf = new GuideForm(true, this, atbez, atprt, atinn, atnam, classFlag);
    }

    public class btnGuid extends JButton {

        public btnGuid() {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/History16.gif"));
            icon = new ImageIcon(icon.getImage().getScaledInstance((int) (16 * GuiStaticVariables.scaleWidth), (int) (16 * GuiStaticVariables.scaleHeight), java.awt.Image.SCALE_SMOOTH));
            setPreferredSize(new Dimension((int) (27 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
            super.setIcon(icon);
            addActionListener(new alOK());
        }
    }

    class alOK implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            callGuideForm();
        }
    }
}
