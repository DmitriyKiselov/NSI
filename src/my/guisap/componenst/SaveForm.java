package my.guisap.componenst;

import baseclass.BInternalFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.event.InternalFrameEvent;
import my.guisap.GuiStaticVariables;
import my.guisap.utils.ComponentsUtils;

/**
 *
 * @author KiselevDA
 */
public abstract class SaveForm extends BInternalFrame {
    
    public boolean needToSave;
    
    private JButton extraButt = ComponentsUtils.createBtn("", 130, 23, true);
    private JButton saveButt = ComponentsUtils.createBtn("Сохранить", 130, 23, true);
    
    public JScrollPane scrollPane;
    public JScrollPane scrollPane2;
    public JPanel mainPanel = new JPanel();
    public JPanel contentPanel = new JPanel();
    public JPanel savePanel = new JPanel();
    
    public SaveForm(String title, boolean needToSave) {
        
        this.setTitle(title);
        this.needToSave = needToSave;
        needSaveSize = false;
        
        setPreferredSize(new Dimension(GuiStaticVariables.screenWidth / 3, GuiStaticVariables.screenHeight - 200));
        
        setLayout(new BorderLayout());
        setClosable(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setMaximizable(false);
        extraButt.setVisible(false);
        SaveButtInitial();
        
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel pnlNorth = new JPanel();
        pnlNorth.add(ComponentsUtils.createLabel(title, new Font("Arial", 1, 18)));
        mainPanel.add(pnlNorth, BorderLayout.NORTH);
        
        contentPanel.add(Box.createVerticalStrut(GuiStaticVariables.ELEMENT_STRUT));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.X_AXIS));
        savePanel.add(extraButt);
        savePanel.add(Box.createHorizontalGlue());
        savePanel.add(saveButt);
        savePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
//        mainPanel.add(savePanel);

        mainPanel.add(savePanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
//        add(savePanel, BorderLayout.SOUTH);

        add(Box.createHorizontalStrut(GuiStaticVariables.INDENT_STRUT), BorderLayout.EAST);
        add(Box.createHorizontalStrut(GuiStaticVariables.INDENT_STRUT), BorderLayout.WEST);
        add(Box.createVerticalStrut(GuiStaticVariables.INDENT_STRUT), BorderLayout.SOUTH);
        add(Box.createVerticalStrut(GuiStaticVariables.INDENT_STRUT), BorderLayout.NORTH);
    }
    
    abstract public void fillFields();
    
    public void ExtraButtInitial(String text, ActionListener actList) {
        extraButt.setVisible(true);
        extraButt.setText(text);
        extraButt.addActionListener(actList);
    }
    
    public void SaveButtInitial() {
        saveButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
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
    
    public void extraButtActionPerformed(java.awt.event.ActionEvent evt) {
    }
    
    public void closeWindow() {
        setVisible(false);
        dispose();
    }
    
}
