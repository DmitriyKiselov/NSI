/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap;

import my.guisap.componenst.EmptyForm;
import baseclass.BInternalFrame;
import java.awt.Dimension;
import javax.swing.JButton;

/**
 *
 * @author DaN
 */
public class attributeRowMainHeader extends attributeRow {

    EmptyForm form;
    public int idKey = 0;
    String classFlagMH;
    FormRegister fR;
    JButton mainHeaderEditButt = new JButton(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Edit16.gif")));

    public attributeRowMainHeader(String atinn, String atnam, String atbez, String atprt, String classFlag) {
        super(atinn, atnam, atbez, atprt, classFlag);
        txt.setPreferredSize(new Dimension((int) (172 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
        txt.setMaximumSize(new Dimension((int) (172 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
        this.classFlagMH = classFlag;
        btn.setEnabled(true);
        mainHeaderEditButt.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainHeaderEditButtPressed(evt);
            }
        });
        add(mainHeaderEditButt);
    }

    @Override
    public void callGuideForm() {
        gf = new GuideForm(true, this, atbez, atprt, atinn, atnam, classFlagMH);
    }

    private void mainHeaderEditButtPressed(java.awt.event.ActionEvent evt) {
        fR = FormRegister.getInstance();
        if (atnam.equals(GuiStaticVariables.FASON_ATNAM)) {
            for (BInternalFrame fR2 : fR.formList) {
                if (fR2.getClass().getName().equals("my.guisap.ModelForm")) {
                    break;
                }
                //if(formList.isEmpty()){break;}
            }
        }
        form.atRowHed.setText(code, name);
    }
}
