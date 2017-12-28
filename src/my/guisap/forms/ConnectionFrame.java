/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms;

import my.guisap.utils.Crypt;
import my.guisap.utils.LogClass;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import my.guisap.*;
import my.guisap.sql.ConnectionClass;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.SecurityManager;

/**
 *
 * @author Dan
 */
public class ConnectionFrame extends javax.swing.JDialog {

    ConnectionClass connect;
    boolean flagHide = true;
    private char[] pass;
    DefaultTableModel usersModel = new DefaultTableModel();
    SqlOperations sql = new SqlOperations();
    MainWindow mw;
    SecurityManager sm;
    LogClass log;
    String loadUserFromConfig = "";
    String loadPasswordFromConfig = "";
    Boolean flagChekBox = false;
    final String fileName = GuiStaticVariables.CONFIGS_DIRECTORY + "userGuiSAP.config";
    public static ConnectionFrame linkConnectionFrame;

    public ConnectionFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        linkConnectionFrame = this;
        initComponents();
        setSize(320, 200);
        filterKeyListener();
        log = LogClass.getInstance();
        this.mw = (MainWindow) parent;
        loadUser();
        userSaveCheckBox.setSelected(flagChekBox);
        if (flagChekBox == true) {
            jTextField2.setText(loadUserFromConfig);
            jPasswordField1.setText(loadPasswordFromConfig);
        }
        setAlwaysOnTop(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jButton1 = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jCheckBox1 = new javax.swing.JCheckBox();
        userSaveCheckBox = new javax.swing.JCheckBox();

        jPopupMenu1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Подключение");
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Развернуть настройки сервера...");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(0);
        jSplitPane1.setDividerSize(0);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        jSplitPane1.setBottomComponent(jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jLabel2.setText("Имя пользователя:");

        jTextField2.setPreferredSize(new java.awt.Dimension(140, 20));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Пароль:");

        jPasswordField1.setMinimumSize(new java.awt.Dimension(140, 20));

        jButton3.setText("Подключиться");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Отмена");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextField1.setText("192.168.2.121");
        jTextField1.setToolTipText("");

        jLabel4.setText("IP-адресс:");

        jLabel5.setText("Порт:");

        jTextField3.setText("1521");

        jLabel6.setText("Имя базы:");

        jLabel7.setText("Имя пользователя:");

        jTextField4.setText("ORCL");

        jTextField5.setText("gui_sap");

        jLabel8.setText("Пароль:");

        jPasswordField2.setText("_Gui_sap");

        jCheckBox1.setText("Localhost");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        userSaveCheckBox.setSelected(true);
        userSaveCheckBox.setText("Запомнить меня");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                        .addComponent(jTextField4))
                                    .addComponent(jCheckBox1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSplitPane1)
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8))
                                        .addGap(15, 15, 15))
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField5)
                                    .addComponent(jPasswordField2)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jPasswordField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(userSaveCheckBox)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(93, 93, 93))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userSaveCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jCheckBox1))))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void filterKeyListener() {
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char a = e.getKeyChar();
                if (!Character.isDigit(a)
                        && (a != '.')
                        && (a != '\b')) {
                    e.consume();
                }
            }
        });

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char a = e.getKeyChar();
                if (!Character.isDigit(a)
                        && (a != '\b')) {
                    e.consume();
                }
            }
        });

        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkPassword();
                }
            }
        });
    }

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        if (flagHide == true) {
            jSplitPane1.setDividerLocation(115);
            setSize(320, 365);
            flagHide = false;
            jLabel1.setText("Свернуть настройки сервера...");
        } else {
            jSplitPane1.setDividerLocation(0);
            setSize(320, 200);
            jLabel1.setText("Развернуть настройки сервера...");
            flagHide = true;
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        checkPassword();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void saveUser(String login, String pass, Boolean flagChekBox) {

        String cryptPass = "";
        Configs proper = new Configs(fileName);

        try {
            cryptPass = Crypt.encrypt(pass);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(ConnectionFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ConnectionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        proper.setProperty("LOGIN", login);
        proper.setProperty("PASSWORD", cryptPass);
        proper.setProperty("FLAG", flagChekBox.toString());
        proper.setProperty("IP_BD", jTextField1.getText());
        proper.saveProperties("User Configuration");
    }

    private void loadUser() {
        Configs proper = new Configs(fileName);
        if (proper.fConfig.exists()) {
            try {
                loadUserFromConfig = proper.getProperty("LOGIN");
                String cryptStr = proper.getProperty("PASSWORD");
                loadPasswordFromConfig = Crypt.decrypt(cryptStr);
                flagChekBox = Boolean.parseBoolean(proper.getProperty("FLAG"));
                jTextField1.setText(proper.getProperty("IP_BD"));
            } catch (Exception e) {
                System.out.println("Error user load!!!");
            }
        } else {
            saveUser("123", "000", false);
        }

        if (jTextField1.getText().equals("")) {
            jTextField1.setText("192.168.2.121");
        }
    }

    private Boolean getChekboxValue() {
        return userSaveCheckBox.isSelected();
    }

    private void checkPassword() {
        ConnectionClass.ip = jTextField1.getText();
        ConnectionClass.port = jTextField3.getText();
        ConnectionClass.databaseName = jTextField4.getText();
        ConnectionClass.userName = jTextField5.getText();
        pass = jPasswordField2.getPassword();
        String buf = new String(pass);
        ConnectionClass.password = buf;
        connect = new ConnectionClass(ConnectionClass.databaseName);

        String password = new String(jPasswordField1.getPassword());
        String userName = jTextField2.getText();

        sql.tableFill("SELECT * FROM USERS where USERS = '" + userName + "' and PASS = '" + password + "'", usersModel);
        if (usersModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Неверное имя пользователя или пароль",
                    "Ошибка регистрации",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            my.guisap.utils.SecurityManager.idGroup = Integer.parseInt(usersModel.getValueAt(0, 2).toString());
            my.guisap.utils.SecurityManager.userName = usersModel.getValueAt(0, 0).toString();
            log.logWriting("Авторизовался в системе");
            sm = SecurityManager.getInstance();
            mw.updateMenu();
            saveUser(userName, password, getChekboxValue());
        }
        this.setVisible(false);
        this.dispose();
    }

    protected void resetFocus() {
        jTextField2.setText("");
        jPasswordField1.setText("");
        jTextField2.requestFocusInWindow();
    }

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
            ConnectionClass.flagLocalHost = true;
            jTextField1.setEditable(false);
            jTextField3.setEditable(false);
            jTextField5.setEditable(false);
            jPasswordField2.setEditable(false);
        } else {
            ConnectionClass.flagLocalHost = false;
            jTextField1.setEditable(true);
            jTextField3.setEditable(true);
            jTextField5.setEditable(true);
            jPasswordField2.setEditable(true);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JCheckBox userSaveCheckBox;
    // End of variables declaration//GEN-END:variables
}
