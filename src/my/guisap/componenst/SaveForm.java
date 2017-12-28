package my.guisap.componenst;

import baseclass.BInternalFrame;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import my.guisap.GuiStaticVariables;
import my.guisap.utils.ComponentsUtils;

/**
 *
 * @author KiselevDA
 */
public class SaveForm extends BInternalFrame {

    public boolean needToSave;
    public JButton saveButt = ComponentsUtils.createBtn("Сохранить", 130, 23, true);

    public SaveForm(String title, boolean needToSave, boolean needSaveSize) {

        this.setTitle(title);
        this.needToSave = needToSave;
        this.needSaveSize = needSaveSize;

        setMaximumSize(new Dimension(GuiStaticVariables.screenWidth, GuiStaticVariables.screenHeight));
        setClosable(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setMaximizable(false);
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
