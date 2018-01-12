package my.guisap;

import my.guisap.forms.EditClassForm;
import my.guisap.forms.EditUserForm;
import my.guisap.forms.RequestReport;
import my.guisap.forms.Calculation.CalculationMain;
import my.guisap.forms.Last.LastMain;
import my.guisap.forms.MKZ.MkzMainForm;
import my.guisap.forms.BottomDetails.BottomDetails;
import my.guisap.utils.SecurityManager;
import my.guisap.componenst.ProgressMessage;
import my.guisap.componenst.security.JSecurityMenu;
import my.guisap.componenst.security.JSecurityMenuItem;
import my.guisap.forms.SettingsForm;
import my.guisap.forms.LogsForm;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import my.guisap.forms.BlackList.BlackList;
import my.guisap.forms.UpdateForm;
import my.guisap.forms.ConnectionFrame;
import my.guisap.forms.EC.ECMain;
import my.guisap.forms.ImportFromExcel;
import my.guisap.forms.TableManager.LocalTablesManager;
import my.guisap.forms.UpDetails.UpDetailsMain;

/**
 *
 * @author DaN
 */
public class MainWindow extends javax.swing.JFrame {

    public static MainWindow linkMainWindow;
    public static JDesktopPane desktop;
    SAPBridge sb = new SAPBridge();
    FormRegister fr;
    my.guisap.forms.ConnectionFrame conn;
    BufferedImage img;

    //  my.guisap.sql.ConnectionClass connect;
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        //    MainClass.setupSubstance();
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        linkMainWindow = this;
//        try {
//            img = ImageIO.read(new File("logo2.jpg"));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        // A specialized layered pane to be used with JInternalFrames
        desktop = new JDesktopPane();
        setContentPane(desktop);
        fr = FormRegister.getInstance();
//        desktop.add(jLabel1, java.awt.BorderLayout.CENTER);
        conn = new ConnectionFrame(this, true);
        conn.setVisible(true);
        //    connect = new ConnectionClass();
        loadConfig();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fr.openForm(new UpdateForm(true), FormRegister.ONE_KEY_FORM);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu8 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new JSecurityMenu(new ArrayList<>(Arrays.asList(SecurityManager.ADMINS,SecurityManager.OPP,SecurityManager.TEHNOLOGES, SecurityManager.UNLOADING, SecurityManager.PEO)));
        jMenuItem36 = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new my.guisap.componenst.security.JSecurityMenu(new ArrayList<>(Arrays.asList(my.guisap.utils.SecurityManager.ADMINS, my.guisap.utils.SecurityManager.OPP, my.guisap.utils.SecurityManager.UNLOADING)));
        jMenuItem5 = new my.guisap.componenst.security.JSecurityMenuItem(new ArrayList<>(Arrays.asList(my.guisap.utils.SecurityManager.ADMINS, my.guisap.utils.SecurityManager.OPP)));
        jMenuItem22 = new my.guisap.componenst.security.JSecurityMenuItem(new ArrayList<>(Arrays.asList(my.guisap.utils.SecurityManager.ADMINS)));
        jMenuItem26 = new my.guisap.componenst.security.JSecurityMenuItem(new ArrayList<>(Arrays.asList(my.guisap.utils.SecurityManager.ADMINS, my.guisap.utils.SecurityManager.UNLOADING)));
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenu7 = new my.guisap.componenst.security.JSecurityMenu(new ArrayList<>(Arrays.asList(my.guisap.utils.SecurityManager.ADMINS, my.guisap.utils.SecurityManager.OPP, my.guisap.utils.SecurityManager.UNLOADING)));
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu9 = new JSecurityMenu(new ArrayList<>(Arrays.asList(my.guisap.utils.SecurityManager.ADMINS, my.guisap.utils.SecurityManager.UNLOADING)));
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();

        jMenu8.setText("jMenu8");

        jMenu5.setText("File");
        jMenuBar2.add(jMenu5);

        jMenu6.setText("Edit");
        jMenuBar2.add(jMenu6);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("НСИ Белвест");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jMenu1.setText("Файл");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Stop16.gif")));
        jMenuItem1.setText("Выход");
        jMenuItem1.setToolTipText("");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Редактирование");

        jMenuItem36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my/guisap/images/stek.png"))); // NOI18N
        jMenuItem36.setText("МКЗ");
        jMenuItem36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem36ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem36);

        jMenuItem35.setText("Расчет предварительных потребностей    ");
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem35);

        jMenuItem33.setText("Работа с колодками");
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem33);

        jMenuItem7.setText("Эксперементальный цех");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Сервис");

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Edit16.gif")));
        jMenuItem5.setText("Редактирование классов");
        jMenuItem5.setToolTipText("");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Properties16.gif")));
        jMenuItem22.setText("Редактирование пользователей");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem22);

        jMenuItem26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/History16.gif")));
        jMenuItem26.setText("Логи");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem26);

        jMenuItem25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Preferences16.gif")));
        jMenuItem25.setText("Настройки");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem25);

        jMenuItem34.setText("Обновление");
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem34);

        jMenuBar1.add(jMenu3);

        jMenu7.setText("Справочники");
        jMenu7.setToolTipText("");

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Edit16.gif")) );
        jMenuItem2.setText("Детали низа");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem2);

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Edit16.gif")) );
        jMenuItem9.setText("Конструкции верха");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem9);

        jMenuItem3.setText("Пополнение справочников");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem3);

        jMenuItem6.setText("Редактирование локальных справочников");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem6);

        jMenuItem4.setText("Список исключений");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem4);

        jMenuBar1.add(jMenu7);

        jMenu9.setText("Импорт/Экспорт");

        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my/guisap/images/sap.png"))); // NOI18N
        jMenuItem15.setText("Обновление справочников SAP");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem15);

        jMenuItem8.setText("Импорт из Excel");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem8);

        jMenuBar1.add(jMenu9);

        jMenu10.setEnabled(false);
        jMenu10.setMargin(new java.awt.Insets(0, 100, 0, 0));
        jMenuBar1.add(jMenu10);

        jMenu4.setText("Активные окна");
        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1098, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 577, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void updateMenu() { // перерабатывает меню в зависимости от групп пользователей
        Component[] comp = jMenuBar1.getComponents();
        ArrayList<Component> menuItemList = new ArrayList<>();
        ArrayList<Component> subItemsList = new ArrayList<>();
        JSecurityMenu securitM;
        JSecurityMenuItem securitMI;
        for (int indexAllComp = 0; indexAllComp < comp.length; indexAllComp++) {
            if (comp[indexAllComp] instanceof JSecurityMenu) {
                securitM = (JSecurityMenu) comp[indexAllComp];
                securitM.setVisible(SecurityManager.getInstance());
                Component[] menuComp = securitM.getMenuComponents();
                menuItemList.addAll(Arrays.asList(menuComp));
            } else if (comp[indexAllComp] instanceof JMenu) {
                JMenu temp = (JMenu) comp[indexAllComp];
                Component[] menuComp = temp.getMenuComponents();
                menuItemList.addAll(Arrays.asList(menuComp));
            }
        }
        for (Component itemMenu : menuItemList) {
            if (itemMenu instanceof JSecurityMenuItem) {
                securitMI = (JSecurityMenuItem) itemMenu;
                securitMI.setVisible(SecurityManager.getInstance());
            }
            if (itemMenu instanceof JMenu) {
                JMenu temp = (JMenu) itemMenu;
                Component[] subMenuComp = temp.getMenuComponents();
                subItemsList.addAll(Arrays.asList(subMenuComp));
            }
            if (itemMenu instanceof JSecurityMenu) {
                securitM = (JSecurityMenu) itemMenu;
                securitM.setVisible(SecurityManager.getInstance());
                Component[] menuComp = securitM.getMenuComponents();
                subItemsList.addAll(Arrays.asList(menuComp));
            }
        }
        for (Component subItemMenu : subItemsList) {
            if (subItemMenu instanceof JSecurityMenuItem) {
                securitMI = (JSecurityMenuItem) subItemMenu;
                securitMI.setVisible(SecurityManager.getInstance());
            }
        }
    }

    private void loadConfig() {
        Configs proper = new Configs(GuiStaticVariables.CONFIGS_DIRECTORY + "settings.config");
        if (proper.fConfig.exists()) {
            try {
                GuiStaticVariables.picturesPATH = proper.getProperty("IMAGEPATH");
                GuiStaticVariables.globalFont = new Font(proper.getProperty("FONT"), 0, Integer.valueOf(proper.getProperty("FONT_SIZE")));
                GuiStaticVariables.scaleWidth = Double.valueOf(proper.getProperty("SCALE_W"));
                GuiStaticVariables.scaleHeight = Double.valueOf(proper.getProperty("SCALE_H"));
                GuiStaticVariables.fontScale = Double.valueOf(proper.getProperty("SCALE_FONT"));
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Ошибка считывания настроек");
            }
        } else {
            GuiStaticVariables.picturesPATH = GuiStaticVariables.DEF_PICTURES_PATH;
            System.out.println("Нет файла конфигурации!");
        }
    }
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        fr.openForm(new EditClassForm(), FormRegister.SELECT_KEY_FORM);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        final ProgressMessage pm = new ProgressMessage("Пожалуйста, подождите! Идет загрузка...");

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    sb.executeSAPFM("ZGET_SAP_GUID_TABLE");
                } catch (SQLException ex) {
                }
                pm.setVisible(false);
                pm.dispose();
            }
        });
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
//        try {
//           // connect.closeConnection(null, null, null, ConnectionClass.CONNECT_BUF);
//        } catch (SQLException ex) {
//            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_formWindowClosed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        fr.openForm(new EditUserForm(), FormRegister.SELECT_KEY_FORM);
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        fr.openForm(new SettingsForm(), FormRegister.SELECT_KEY_FORM);
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        fr.openForm(new LogsForm(), FormRegister.SELECT_KEY_FORM);
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        fr.openForm(new LastMain(), FormRegister.MAIN_FORM);
    }//GEN-LAST:event_jMenuItem33ActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        fr.openForm(new UpdateForm(false), FormRegister.SELECT_KEY_FORM);
    }//GEN-LAST:event_jMenuItem34ActionPerformed

    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem35ActionPerformed
        fr.openForm(new CalculationMain(), FormRegister.SELECT_KEY_FORM);// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem35ActionPerformed

    private void jMenuItem36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem36ActionPerformed
        fr.openForm(new MkzMainForm(), FormRegister.MAIN_FORM);
    }//GEN-LAST:event_jMenuItem36ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        fr.openForm(new BottomDetails(), FormRegister.MAIN_FORM);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        fr.openForm(new RequestReport(), FormRegister.MAIN_FORM);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        fr.openForm(new BlackList(), FormRegister.SELECT_KEY_FORM);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        fr.openForm(new LocalTablesManager(), FormRegister.SELECT_KEY_FORM);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        fr.openForm(new ECMain(), FormRegister.MAIN_FORM);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        fr.openForm(new ImportFromExcel(), FormRegister.SELECT_KEY_FORM);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        fr.openForm(new UpDetailsMain(), FormRegister.MAIN_FORM);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    public javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    public javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    // End of variables declaration//GEN-END:variables
}
