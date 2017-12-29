/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms.Calculation;

import my.guisap.forms.MKZ.MkzEditModel;
import my.guisap.forms.MKZ.MkzMainForm;
import baseclass.BInternalFrame;
import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;
import com.sun.glass.events.KeyEvent;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import my.guisap.FormRegister;
import my.guisap.GuiStaticVariables;
import my.guisap.GuideForm;
import my.guisap.attributeRow;
import my.guisap.entity.RowExcel;
import my.guisap.sql.SqlOperations;
import my.guisap.componenst.tableModel.TableModelForCalculation;
import my.guisap.utils.ExelUtils;
import my.guisap.utils.ImageUtils;
import my.guisap.utils.TextUtils;

/**
 *
 * @author KiselevDA
 */
public class CalculationMain extends BInternalFrame {

    SqlOperations sql = new SqlOperations();
    FormRegister fr;

    DefaultTableModel mainModelOracle;
    DefaultTableModel expandModelOracle;

    DefaultTableModel mainModelExel;
    DefaultTableModel expandModelExel;

    DefaultTableModel tableOneModel = new DefaultTableModel();

    ImageIcon upIcon;
    ImageIcon downIcon;

    public CalculationMain() {
        fr = FormRegister.getInstance();
        setTitle("Расчет предварительных потребностей");
        initComponents();
        setIcons();
        setCenter();
        jTabbedPane2.remove(3);
    }

    public CalculationMain(ArrayList<String> listModel) {
        fr = FormRegister.getInstance();
        setTitle("Расчет предварительных потребностей");
        initComponents();
        setIcons();
        setCenter();
        fillTable(listModel);
        jTabbedPane2.setSelectedIndex(3);
    }

    private void fillTable(ArrayList<String> listModel) {
        TableModelForCalculation dtm = (TableModelForCalculation) jTable7.getModel();
        DefaultTableModel tmpModel = new DefaultTableModel();
        for (String model : listModel) {
            sql.fillModel("SELECT model \"Модель\",art \"Артикул\" from MKZ_LIST_HEAD a inner join MKZ_MODEL_ART b on a.id=b.model_id where MODEL='" + model + "'", tmpModel, 0);
        }
        for (int i = 0; i < tmpModel.getRowCount(); i++) {
            dtm.addRow(tmpModel.getValueAt(i, 0).toString(), tmpModel.getValueAt(i, 1).toString());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = TableRowFilterSupport
        .forTable(new JTable() {
            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                return false;
            }
        })
        .searchable(true)
        .actions(true)
        .apply();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 =  TableRowFilterSupport
        .forTable(new JTable() {
            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                return false;
            }
        })
        .searchable(true)
        .actions(true)
        .apply();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = TableRowFilterSupport
        .forTable(new JTable() {
            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                return false;
            }
        })
        .searchable(true)
        .actions(true)
        .apply();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = TableRowFilterSupport
        .forTable(new JTable() {
            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                return false;
            }
        })
        .searchable(true)
        .actions(true)
        .apply();
        jTextField4 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new JTable();
        jButton15 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension((int) (1000 * GuiStaticVariables.scaleWidth), (int) (700 * GuiStaticVariables.scaleHeight)));

        jTable4.setFont(GuiStaticVariables.globalFont);
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);
        jTable4.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);

        jTable4.setCellSelectionEnabled(true);

        jTable4.setOpaque(false);

        jTable4.setUpdateSelectionOnSort(false);

        jTextField3.setEditable(false);
        jTextField3.setFont(GuiStaticVariables.globalFont);
        jTextField3.setPreferredSize(new Dimension((int) (135 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));

        jLabel3.setFont(GuiStaticVariables.globalFont);
        jLabel3.setText("Идентификатор загрузки:");

        jButton5.setFont(GuiStaticVariables.globalFont);
        jButton5.setText("Выгрузить в Excel");
        jButton5.setPreferredSize(new Dimension((int) (200 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)) );
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton1.setFont(GuiStaticVariables.globalFont);
        jButton1.setText("Открыть экстракт");
        jButton1.setPreferredSize(new Dimension((int) (135 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton8.setFont(GuiStaticVariables.globalFont);
        jButton8.setText("Свернуть");
        jButton8.setPreferredSize(new Dimension((int) (150 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)) );
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jComboBox1.setFont(GuiStaticVariables.globalFont);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Полный отчет", "Сокращенный отчет" }));
        jComboBox1.setPreferredSize(new Dimension((int) (200 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)) );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton8.setVisible(false);

        jTabbedPane2.addTab("Просмотр экстракта", jPanel2);

        jTable3.setFont(GuiStaticVariables.globalFont);
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);
        jTable3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);

        jTable3.setCellSelectionEnabled(true);

        jTable3.setOpaque(false);

        jTable3.setUpdateSelectionOnSort(false);

        jButton3.setFont(GuiStaticVariables.globalFont);
        jButton3.setText("Сохранить");
        jButton3.setPreferredSize(new Dimension((int) (130 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField2.setFont(GuiStaticVariables.globalFont);
        jTextField2.setPreferredSize(new Dimension((int) (135 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)) );

        jLabel2.setFont(GuiStaticVariables.globalFont);
        jLabel2.setText("Идентификатор загрузки:");

        jButton2.setFont(GuiStaticVariables.globalFont);
        jButton2.setText("Загрузить из файла");
        jButton2.setPreferredSize(new Dimension((int) (200 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton9.setFont(GuiStaticVariables.globalFont);
        jButton9.setText("Развернуть");
        jButton9.setPreferredSize(new Dimension((int) (150 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)) );
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton9.setVisible(false);

        jTabbedPane2.addTab("Файл", jPanel1);

        jTable5.setFont(GuiStaticVariables.globalFont);
        jTable5.setModel(new my.guisap.componenst.tableModel.TableModelForCalculation());
        jScrollPane5.setViewportView(jTable5);
        jTable5.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);

        jTable5.setCellSelectionEnabled(true);

        jTable5.setOpaque(false);

        jTable5.setUpdateSelectionOnSort(false);

        jTable5.getColumnModel().getColumn(0).setPreferredWidth(20);

        jTable6.setFont(GuiStaticVariables.globalFont);
        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mouseClicked6(evt);
            }
        });
        jScrollPane6.setViewportView(jTable6);
        jTable6.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);

        jTable6.setCellSelectionEnabled(true);

        jTable6.setOpaque(false);

        jTable6.setUpdateSelectionOnSort(false);

        DefaultTableModel dataModels = new DefaultTableModel();
        sql.tableFill(SqlOperations.MKZ_LIST_CLOSE_FOR_CALCUL, dataModels);
        jTable6.setModel(dataModels);
        jTable6.setAutoCreateRowSorter(true);

        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jTextField1.setFont(GuiStaticVariables.globalFont);
        jTextField1.setPreferredSize(new Dimension((int) (135 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));

        jLabel1.setFont(GuiStaticVariables.globalFont);
        jLabel1.setText("Идентификатор загрузки:");

        jButton7.setFont(GuiStaticVariables.globalFont);
        jButton7.setText("Сформировать");
        jButton7.setPreferredSize(new Dimension((int) (200 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)) );
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTable1.setFont(GuiStaticVariables.globalFont);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mouseClicked1(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);

        jTable1.setCellSelectionEnabled(true);

        jTable1.setOpaque(false);

        jTable1.setUpdateSelectionOnSort(false);

        jTable1.setModel(tableOneModel);

        jTextField4.setFont(GuiStaticVariables.globalFont);
        jTextField4.setPreferredSize(new Dimension((int) (100 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)) );
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyyMMdd");

        jTextField4.setText(formatForDateNow.format(dateNow));

        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel4.setText("Модели");

        jLabel5.setText("Артикула");

        jLabel6.setText("Формирование экстракта");

        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField5KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });

        jLabel7.setText("Задать количетво для всех:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4)
                            .addComponent(jButton6)
                            .addComponent(jButton14, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jButton10)
                        .addGap(18, 18, 18)
                        .addComponent(jButton11)
                        .addGap(18, 18, 18)
                        .addComponent(jButton12)
                        .addGap(18, 18, 18)
                        .addComponent(jButton13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(9, 9, 9)))))
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Формирование", jPanel3);

        jLabel8.setFont(GuiStaticVariables.globalFont);
        jLabel8.setText("Идентификатор загрузки:");

        jTextField6.setFont(GuiStaticVariables.globalFont);
        jTextField6.setPreferredSize(new Dimension((int) (100 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)) );
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        dateNow = new Date();
        formatForDateNow = new SimpleDateFormat("yyyyMMdd");

        jTextField6.setText(formatForDateNow.format(dateNow));

        jTextField7.setFont(GuiStaticVariables.globalFont);
        jTextField7.setPreferredSize(new Dimension((int) (135 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));

        jLabel9.setText("Задать количетво для всех:");

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField8KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField8KeyTyped(evt);
            }
        });

        jTable7.setFont(GuiStaticVariables.globalFont);
        jTable7.setModel(new my.guisap.componenst.tableModel.TableModelForCalculation());
        jScrollPane7.setViewportView(jTable7);
        jTable5.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);

        jTable5.setCellSelectionEnabled(true);

        jTable5.setOpaque(false);

        jTable5.setUpdateSelectionOnSort(false);

        jTable5.getColumnModel().getColumn(0).setPreferredWidth(20);

        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel10.setText("Формирование экстракта");

        jButton16.setFont(GuiStaticVariables.globalFont);
        jButton16.setText("Сформировать");
        jButton16.setPreferredSize(new Dimension((int) (200 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)) );
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton15)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton15)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Формирование из \"МКЗ\"", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jTextField8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8KeyTyped

    private void jTextField8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8KeyPressed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
        String s = "" + evt.getKeyChar();
        if (jTextField5.getText().length() > 4 || !TextUtils.checkStringToNumber(s, null)) {
            evt.consume();
        } else {
            jTextField5.setText(jTextField5.getText() + s);
            jTextField5.setText(jTextField5.getText().substring(0, jTextField5.getText().length() - 1));
        }
    }//GEN-LAST:event_jTextField5KeyTyped

    private void jTextField5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable5.getModel().getRowCount() > 0) {
                for (int i = 0; i < jTable5.getModel().getRowCount(); i++) {
                    jTable5.getModel().setValueAt(jTextField5.getText(), i, 3);
                }
            }
        }
    }//GEN-LAST:event_jTextField5KeyPressed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed

    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        TableModelForCalculation dtm = (TableModelForCalculation) jTable5.getModel();
        DefaultTableModel modelTableOne = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < modelTableOne.getRowCount(); i++) {
            dtm.addRow(modelTableOne.getValueAt(i, 0).toString(), modelTableOne.getValueAt(i, 1).toString());
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        tableOneModel = new DefaultTableModel();
        jTable1.setModel(tableOneModel);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        if (jTable1.getSelectedRow() > -1) {
            DefaultTableModel tmpModel = (DefaultTableModel) jTable1.getModel();
            tmpModel.removeRow(jTable1.getSelectedRow());
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        if (jTable6.getSelectedRow() > -1) {
            sql.fillModel("SELECT model \"Модель\",art \"Артикул\",color \"Цвет\" from MKZ_LIST_HEAD a inner join MKZ_MODEL_ART b on a.id=b.model_id where MODEL='" + jTable6.getValueAt(jTable6.getSelectedRow(), 0).toString() + "'", tableOneModel, 0);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        tableOneModel = new DefaultTableModel();
        for (int i = 0; i < jTable6.getModel().getRowCount(); i++) {
            sql.fillModel("SELECT model \"Модель\",art \"Артикул\",color \"Цвет\" from MKZ_LIST_HEAD a inner join MKZ_MODEL_ART b on a.id=b.model_id where MODEL='" + jTable6.getValueAt(i, 0).toString() + "'", tableOneModel, 0);
        }
        jTable1.setModel(tableOneModel);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed

    }//GEN-LAST:event_jTextField4ActionPerformed

    private void mouseClicked1(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseClicked1
        if (evt.getClickCount() == 2) {
            TableModelForCalculation dtm = (TableModelForCalculation) jTable5.getModel();
            dtm.addRow(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString(), jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
        }
    }//GEN-LAST:event_mouseClicked1

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        insertToDB(jTable5, jTextField4, jTextField1);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        TableModelForCalculation dtm = (TableModelForCalculation) jTable5.getModel();
        dtm.clearAll();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        TableModelForCalculation dtm = (TableModelForCalculation) jTable5.getModel();
        boolean isDeleted = false;
        for (int i = dtm.getRowCount() - 1; i >= 0; i--) {
            if ((boolean) dtm.getValueAt(i, 0) == true) {
                dtm.deleteRow(i, false);
                isDeleted = true;
            }
        }
        if (!isDeleted) {
            try {
                dtm.deleteRow(jTable5.getSelectedRow(), false);
            } catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(this, "Не выбрана ни одна запись", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            }
        }
        dtm.fireTableDataChanged();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void mouseClicked6(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseClicked6
        if (evt.getClickCount() == 2) {
            sql.fillModel("SELECT model \"Модель\",art \"Артикул\",color \"Цвет\" from MKZ_LIST_HEAD a inner join MKZ_MODEL_ART b on a.id=b.model_id where MODEL='" + jTable6.getValueAt(jTable6.getSelectedRow(), 0).toString() + "'", tableOneModel, 0);
        }
    }//GEN-LAST:event_mouseClicked6

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if (mainModelExel != null && expandModelExel != null) {
            if (jButton9.getText().equals("Свернуть")) {
                jTable3.setModel(mainModelExel);
                jButton9.setText("Развернуть");
                jButton9.setIcon(downIcon);
            } else {
                jTable3.setModel(expandModelExel);
                jButton9.setText("Свернуть");
                jButton9.setIcon(upIcon);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Загрузите экстракт", "Внимание", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        FileDialog fd = new FileDialog(new Frame(), "Выберите файл", FileDialog.LOAD);
        fd.setFile("*.xls;*.xlsx;");
        fd.setVisible(true);
        String filename = fd.getFile();
        if (filename != null) {
            try {
                RowExcel data = ExelUtils.ReadRowFromExcel(new File(fd.getDirectory() + fd.getFile()));
                mainModelExel = data.getDefaultTableModel();
                if (mainModelExel.getColumnCount() > 3) {
                    JOptionPane.showMessageDialog(this, "Не верный формат файла", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                } else {
                    int counter = 0;
                    for (int i = mainModelExel.getRowCount() - 1; i >= 0; i--) {
                        DefaultTableModel tmp = new DefaultTableModel();
                        sql.tableFill("select * from MKZ_MODEL_ART where STATUS='W' and ART='" + mainModelExel.getValueAt(i, mainModelExel.getColumnCount() - 2) + "'", tmp);
                        if (tmp.getRowCount() == 0) {
                            mainModelExel.removeRow(i);
                            counter++;
                        }
                    }

                    if (counter > 0) {
                        JOptionPane.showMessageDialog(this, "При загрузке " + counter + " записей не прошли проверку", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    }

                    expandModelExel = new DefaultTableModel();
                    for (int i = 0; i < mainModelExel.getRowCount(); i++) {
                        for (int j = 1; j < GuiStaticVariables.listAllBlocksSketch.size(); j++) {
                            sql.fillModel("select MODEL \"Модель\", b.ART \"Артикул\",'" + GuiStaticVariables.listAllBlocksSketch.get(j)[2] + "' \"Название группы\",c.PROVIDER \"Поставщик\" ,c." + GuiStaticVariables.listAllBlocksSketch.get(j)[4] + ",'" + mainModelExel.getValueAt(i, mainModelExel.getColumnCount() - 1) + "' \"ГП Количество\", c.CONSUMPTION  \"Норма на 1 пару\",c.PRICE \"Цена $/ед.мат.\",c.TOTAL_COST \"Сумма $/мат.\", (TOTAL_COST*" + mainModelExel.getValueAt(i, mainModelExel.getColumnCount() - 1) + ") \"Сумма $/парт.\" from MKZ_LIST_HEAD a inner join MKZ_MODEL_ART b on a.id=b.model_id inner join  " + GuiStaticVariables.listAllBlocksSketch.get(j)[0] + " c on c.ART=b.ART WHERE b.ART='" + mainModelExel.getValueAt(i, mainModelExel.getColumnCount() - 2) + "'", expandModelExel, 0);
                        }
                    }
                    jTable3.setModel(mainModelExel);
                    jButton9.setText("Развернуть");
                    jButton9.setIcon(downIcon);
                    jButton9.setVisible(true);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Не удалось загрузить файл", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (mainModelExel != null) {
            if (!jTextField2.getText().equals("")) {
                insertToDBFromFile(mainModelExel);
            } else {
                JOptionPane.showMessageDialog(this, "Заполните идентификатор", "Внимание", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Загрузите экстракт", "Внимание", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        if (evt.getClickCount() == 2) {
            fr.openForm(new MkzEditModel("Модель ", GuiStaticVariables.MODEL_NUMBER, null, jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString()), FormRegister.ONE_KEY_FORM);
        }
    }//GEN-LAST:event_jTable3MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (mainModelOracle != null && expandModelOracle != null) {
            if (jButton8.getText().equals("Свернуть")) {
                jTable4.setModel(mainModelOracle);
                jButton8.setText("Развернуть");
                jButton8.setIcon(downIcon);
            } else {
                jTable4.setModel(expandModelOracle);
                jButton8.setText("Свернуть");
                jButton8.setIcon(upIcon);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Откройте экстракт", "Внимание", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        attributeRow tmpRow = new attributeRow("Экстракт", "EXTRACT", "", "EXTRACT", "CALC");
        GuideForm gf = new GuideForm(true, tmpRow, "Экстракт", "EXTRACT", "", "EXTRACT", "CALC");
        if (!tmpRow.txt.getText().equals("")) {
            mainModelOracle = new DefaultTableModel();
            expandModelOracle = new DefaultTableModel();
            sql.tableFill("select MODEL  \"Модель\" ,ART \"Артикул\" ,COUNT \"Количество\" from EXTRACT a where NAME='" + tmpRow.txt.getText() + "'", mainModelOracle);
            for (int i = 0; i < mainModelOracle.getRowCount(); i++) {
                for (int j = 1; j < GuiStaticVariables.listAllBlocksSketch.size(); j++) {
                    sql.fillModel("select '" + mainModelOracle.getValueAt(i, 0) + "' \"Модель\",ART \"Артикул\",'" + GuiStaticVariables.listAllBlocksSketch.get(j)[2] + "' \"Название группы\",PROVIDER \"Поставщик\" ," + GuiStaticVariables.listAllBlocksSketch.get(j)[4] + ",'" + mainModelOracle.getValueAt(i, 2) + "' \"ГП Количество\", CONSUMPTION  \"Норма на 1 пару\",PRICE \"Цена $/ед.мат.\",TOTAL_COST \"Cумма $/мат.\",(CONSUMPTION*" + mainModelOracle.getValueAt(i, 2) + ") \"Потр. мат./партия\", (TOTAL_COST*" + mainModelOracle.getValueAt(i, 2) + ") \"Сумма $/парт.\" from " + GuiStaticVariables.listAllBlocksSketch.get(j)[0] + " c WHERE ART='" + mainModelOracle.getValueAt(i, 1) + "'", expandModelOracle, 0);
                }
            }
            jTextField3.setText(tmpRow.txt.getText());
            jTabbedPane2.setSelectedComponent(jPanel2);
            jTable4.setModel(expandModelOracle);
            jButton8.setText("Свернуть");
            jButton8.setIcon(upIcon);

            jButton8.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (!jTextField3.getText().equals("")) {

            RowExcel rowToExel;
            String way;
            File folder = new File("result/");

            switch (jComboBox1.getSelectedIndex()) {
                case 0:
                    rowToExel = new RowExcel(expandModelOracle);
                    way = folder + "/" + jTextField3.getText() + "_full.xls";
                    break;
                default:
                    rowToExel = new RowExcel(mainModelOracle);
                    way = folder + "/" + jTextField3.getText() + ".xls";
                    break;
            }

            if (!folder.exists()) {
                folder.mkdir();
            }

            try (FileOutputStream out = new FileOutputStream(way)) {
                ExelUtils.SaveToExcel(rowToExel, "Расчет предварительных потребностей", out);
            } catch (IOException ex) {
                Logger.getLogger(CalculationMain.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Ошибка при выгрузке данных", "Внимание", JOptionPane.WARNING_MESSAGE);
            }

            int n = JOptionPane.showConfirmDialog(
                    this,
                    "Данные успешно выгружены. Открыть директорию сохранения?",
                    "Внимание",
                    JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                try {
                    Runtime.getRuntime().exec("explorer " + folder);
                } catch (IOException ex) {
                    Logger.getLogger(MkzMainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Не выбран экстракт", "Внимание", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        if (evt.getClickCount() == 2) {
            fr.openForm(new MkzEditModel("Модель ", GuiStaticVariables.MODEL_NUMBER, null, jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString()), FormRegister.ONE_KEY_FORM);
        }
    }//GEN-LAST:event_jTable4MouseClicked

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        insertToDB(jTable7, jTextField6, jTextField7);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void setIcons() {

        jButton4.setIcon(ImageUtils.createIconForButton("images/minus.png"));
        jButton5.setIcon(ImageUtils.createIconForButton("images/doc.png"));
        jButton2.setIcon(ImageUtils.createIconForButton("images/doc.png"));
        jButton6.setIcon(ImageUtils.createIconForButton("images/delete.png"));
        jButton10.setIcon(ImageUtils.createIconForButton("images/doubleRight.png"));
        jButton11.setIcon(ImageUtils.createIconForButton("images/right.png"));
        jButton12.setIcon(ImageUtils.createIconForButton("images/left.png"));
        jButton13.setIcon(ImageUtils.createIconForButton("images/doubleLeft.png"));

        upIcon = ImageUtils.createIconForButton("images/up.png");
        jButton8.setIcon(upIcon);
        jButton14.setIcon(upIcon);

        downIcon = ImageUtils.createIconForButton("images/down.png");
        jButton9.setIcon(downIcon);

    }

    private void insertToDB(JTable jTable, JTextField year, JTextField field) {
        TableModelForCalculation dtm = (TableModelForCalculation) jTable.getModel();
        DefaultTableModel id = new DefaultTableModel();
        DefaultTableModel check = new DefaultTableModel();

        if (field.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Поле идентификатор загрузки не заполнено", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (int i = 0; i < dtm.getRowCount(); i++) {
            if (dtm.getValueAt(i, 3).equals("")) {
                JOptionPane.showMessageDialog(this, "Не все поля заполнены", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        sql.tableFill("select NVL(max(TO_NUMBER(ID)),0)+1 from GUI_SAP.EXTRACT where NAME not like '" + year.getText() + field.getText() + "'", id);
        sql.tableFill("select NAME from GUI_SAP.EXTRACT where NAME LIKE'" + year.getText() + field.getText() + "'", check);
        if (check.getRowCount() == 0) {
            for (int i = 0; i < dtm.getRowCount(); i++) {
                sql.SendQuery("INSERT INTO GUI_SAP.EXTRACT VALUES('" + id.getValueAt(0, 0) + "','" + year.getText() + field.getText() + "','" + dtm.getValueAt(i, 1) + "','" + dtm.getValueAt(i, 2) + "','" + dtm.getValueAt(i, 3) + "', sysdate)");
            }
            sql.SendQuery("commit");
        } else {
            JOptionPane.showMessageDialog(this, "Экстракт с таким именем существует", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Экстракт заведен", "Предупреждение", JOptionPane.WARNING_MESSAGE);
    }

    private void insertToDBFromFile(DefaultTableModel dtm) {
        DefaultTableModel id = new DefaultTableModel();
        DefaultTableModel check = new DefaultTableModel();
        sql.tableFill("select NVL(max(TO_NUMBER(ID)),0)+1 from GUI_SAP.EXTRACT where NAME not like '" + jTextField2.getText() + "'", id);
        sql.tableFill("select NAME from GUI_SAP.EXTRACT where NAME LIKE'" + jTextField2.getText() + "'", check);
        if (check.getRowCount() == 0) {
            for (int i = 0; i < dtm.getRowCount(); i++) {
                sql.SendQuery("INSERT INTO GUI_SAP.EXTRACT VALUES('" + id.getValueAt(0, 0) + "','" + jTextField2.getText() + "',(select MODEL from MKZ_LIST_HEAD a inner join MKZ_MODEL_ART b on b.MODEL_ID=a.ID WHERE ART='" + dtm.getValueAt(i, dtm.getColumnCount() - 2) + "'),'" + dtm.getValueAt(i, dtm.getColumnCount() - 2) + "','" + dtm.getValueAt(i, dtm.getColumnCount() - 1) + "', sysdate)");
            }
            sql.SendQuery("commit");
        } else {
            JOptionPane.showMessageDialog(this, "Экстракт с таким именем существует", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Экстракт заведен", "Предупреждение", JOptionPane.WARNING_MESSAGE);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
