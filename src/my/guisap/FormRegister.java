package my.guisap;

import my.guisap.componenst.EmptyForm;
import baseclass.BInternalFrame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import my.guisap.componenst.SaveForm;

/**
 *
 * @author DaN
 */
public class FormRegister {

    public static final int ONE_KEY_FORM = 1;
    public static final int SOME_KEY_FORM = 2;
    public static final int SELECT_KEY_FORM = 0;
    public static final int MAIN_FORM = 3;
    private static FormRegister formRegister;
    BInternalFrame form = null;
    public ArrayList<BInternalFrame> formList = new ArrayList<>();
    public ArrayList<JMenuItem> itemList = new ArrayList<>();
    final String fileName = GuiStaticVariables.CONFIGS_DIRECTORY + "form.config";

    private FormRegister() {
    }

    public static FormRegister getInstance() {
        if (formRegister == null) {
            formRegister = new FormRegister();
        }
        return formRegister;
    }

    public BInternalFrame openForm(BInternalFrame frameIn, int key) {
        boolean flagSelForm = false;

        form = frameIn;
        switch (key) {
            case SOME_KEY_FORM:
                setForm(true, true, false);
                break;
            case ONE_KEY_FORM:
                for (BInternalFrame fR : formList) {
                    if (fR.getClass().getName().equals(form.getClass().getName())) {
                        System.out.println("Закрытие " + form.getClass().getName());
                        fR.setVisible(false);
                        fR.dispose();
                        formList.remove(formList.indexOf(fR));
                        break;
                    }
                    //if(formList.isEmpty()){break;}
                }
                setForm(true, true, false);
                break;

            case SELECT_KEY_FORM:
                for (BInternalFrame fR : formList) {
                    if (fR.getClass().getName().equals(form.getClass().getName())) {
                        try {
                            fR.setSelected(true);
                            flagSelForm = true;
                            JOptionPane.showMessageDialog(MainClass.mainWindow, "Данный тип форм уже открыт! Для открытия новой," + "\n" + "пожалуйста, закройте текущую.", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(FormRegister.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if (flagSelForm == false) {
                    setForm(true, true, false);
                } else {
                    form = null;
                }
                break;

            case MAIN_FORM:
                for (BInternalFrame fR : formList) {
                    if (fR.getClass().getName().equals(form.getClass().getName())) {
                        try {
                            fR.setSelected(true);
                            flagSelForm = true;
                            JOptionPane.showMessageDialog(MainClass.mainWindow, "Данный тип форм уже открыт! Для открытия новой," + "\n" + "пожалуйста, закройте текущую.", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(FormRegister.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if (flagSelForm == false) {
                    setForm(false, false, true);
                } else {
                    form = null;
                }
                break;
        }
        return form;
    }

    private void setForm(boolean resizeble, boolean maximizable, boolean maximum) {
        form.setVisible(true);
        form.setClosable(true);
        form.setResizable(resizeble);
        form.setMaximizable(maximizable);
        MainWindow.desktop.add(form);
        form.addInternalFrameListener(new CloseListener());
        formList.add(form);
        itemList.add(new JMenuItem(form.getTitle()));
        updateJMenu();
        try {
            form.setSelected(true);
            form.setMaximum(maximum);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FormRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadFormLocation(form);
    }

    // сохранение местоположения и размера формы
    private void saveFormLocation(BInternalFrame fR) {
        if (!fR.needSaveSize) {
            return;
        }
        Configs proper = new Configs(fileName);

        proper.setProperty(fR.getClass().getName() + "$" + "WIDTH", Integer.toString(fR.getWidth()));
        proper.setProperty(fR.getClass().getName() + "$" + "HEIGHT", Integer.toString(fR.getHeight()));
        proper.setProperty(fR.getClass().getName() + "$" + "LEFT", Integer.toString(fR.getX()));
        proper.setProperty(fR.getClass().getName() + "$" + "TOP", Integer.toString(fR.getY()));
        proper.saveProperties("Form Configuration");
    }

    // загрузка местоположения и размера формы
    private void loadFormLocation(BInternalFrame fR) {
        if (!fR.needSaveSize) {
            return;
        }
//        Properties proper = new Properties();
//        File fConfig = new File(fileName);
        Configs proper = new Configs(fileName);
        try {
            fR.setPreferredSize(new Dimension(new Integer(proper.getProperty(fR.getClass().getName() + "$" + "WIDTH")), new Integer(proper.getProperty(fR.getClass().getName() + "$" + "HEIGHT"))));
            fR.setLocation(new Integer(proper.getProperty(fR.getClass().getName() + "$" + "LEFT")), new Integer(proper.getProperty(fR.getClass().getName() + "$" + "TOP")));
        } catch (Exception e) {
            System.out.println("Не найдены свойства формы");
        }
    }

    private class CloseListener extends InternalFrameAdapter {

        @Override
        public void internalFrameClosing(InternalFrameEvent e) {
            boolean flag = true;
            saveFormLocation((BInternalFrame) e.getInternalFrame());
            for (JInternalFrame fR : formList) {
                if (e.getInternalFrame() == fR) {
                    try {
                        Method frameClose = fR.getClass().getMethod("formInternalFrameClosed", e.getClass());
                        if (fR instanceof EmptyForm) {
                            EmptyForm ef = (EmptyForm) fR;
                            flag = ef.formInternalFrameClosed(e);
                        } else {
                            SaveForm ef = (SaveForm) fR;
                            flag = ef.formInternalFrameClosed(e);
                        }

                    } catch (NoSuchMethodException ex) {
                        //Logger.getLogger(FormRegister.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (flag) {
                        formList.remove(formList.indexOf(fR));
                        updateJMenu();
                        fR.setVisible(false);
                        fR.dispose();
                        return;
                    }
                }
            }
        }
    }

    private void updateJMenu() {
        MainWindow.linkMainWindow.jMenu4.removeAll();
        for (final BInternalFrame frame : formList) {
            JMenuItem item = new JMenuItem(frame.getTitle());
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    frame.toFront();
                }
            });
            MainWindow.linkMainWindow.jMenu4.add(item);
        }
    }

    public ArrayList<BInternalFrame> getListFrame() {
        return formList;
    }
}
