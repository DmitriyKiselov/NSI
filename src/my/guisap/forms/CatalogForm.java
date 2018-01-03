package my.guisap.forms;

import my.guisap.*;
import com.sun.glass.events.KeyEvent;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import my.guisap.componenst.JOverrideTable;
import my.guisap.componenst.fields.CatalogField;
import my.guisap.forms.BottomDetails.AddHeelForm;
import my.guisap.forms.BottomDetails.AddInsoleForm;
import my.guisap.forms.BottomDetails.AddSoleForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.LogClass;
import my.guisap.utils.SecurityManager;

/**
 *
 * @author DaN
 */
public class CatalogForm extends javax.swing.JDialog {

    FormRegister fr = FormRegister.getInstance();
    LogClass log = LogClass.getInstance();
    public static boolean selectValue = false;
    SqlOperations sql = new SqlOperations();
    DefaultTableModel guideModelLocal = new DefaultTableModel(new Object[][]{}, new String[]{});
    DefaultTableModel guideModelSap = new DefaultTableModel(new Object[][]{}, new String[]{});
    CatalogField field;
    String atbez;
    int typeSave = 1;
    TableRowSorter<DefaultTableModel> sorterLocal;
    TableRowSorter<DefaultTableModel> sorterSap;

    public CatalogForm(boolean modal, CatalogField field) {
        super(MainClass.mainWindow, modal);
        super.setTitle(field.getNameField());

        this.field = field;
        setAlwaysOnTop(false);
        initComponents();

        switch (field.getDiscriptionField()) {
            case "SHIN_VIEW":
                setSearchKeyListner(false);
                break;
            case "FASON_LAST":
                jButton3.setVisible(false);
                setSearchKeyListner(true);
                break;
            case "CODE_FASON_LAST":
                jButton3.setVisible(false);
                setSearchKeyListner(true);
                break;
            case "FASON_SOLE":
                jButton3.setText("Новый фасон");
                typeSave = 2;
                setSearchKeyListner(true);
                break;
            case "FASON_HEEL":
                jButton3.setText("Новый фасон");
                typeSave = 2;
                setSearchKeyListner(true);
                break;
            case "FASON_INSOLE":
                jButton3.setText("Новый фасон");
                typeSave = 2;
                setSearchKeyListner(true);
                break;
            case "EXTRACT":
                jButton3.setText("Удалить экстракт");
                jButton2.setText("Открыть");
                typeSave = 3;
                setSearchKeyListner(true);
                break;
            default:
                setSearchKeyListner(false);
                break;
        }

        signsFormation(field.getNameCatalog(), field.getDiscriptionField());

        jTextField2.requestFocus();
        setVisible(true);
        JOverrideTable.packColumns(jTable1);
        JOverrideTable.packColumns(jTable2);

    }

    private void signsFormation(String atprt, String atnam) {

        jTable1.setModel(guideModelLocal);
        jTable1.getTableHeader().setReorderingAllowed(false);

        jTable2.setModel(guideModelSap);
        jTable2.getTableHeader().setReorderingAllowed(false);

        int rowCount = guideModelLocal.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            guideModelLocal.removeRow(0);
        }

        rowCount = guideModelLocal.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            guideModelSap.removeRow(0);
        }

        switch (atnam) {
            case "SHIN_VIEW":
                switch (field.getInfoForLimitation()) {
                    case "Мужской":
                        sql.tableFill("select * from " + atprt + " a  WHERE CODE like '%мужские%' order BY a.CODE", guideModelLocal);
                        break;
                    case "Женский":
                        sql.tableFill("select * from " + atprt + " a WHERE CODE like '%женские%'  order BY a.CODE", guideModelLocal);
                        break;
                    default:
                        sql.tableFill("select * from " + atprt + " a order BY a.CODE", guideModelLocal);
                }
                break;

            case "FASON_LAST":
                sql.tableFill("select * from (" + SqlOperations.SELECT_FOR_CATALOG_FORM + atprt + " a order BY a.CODE) " + SqlOperations.UNION_REQUEST_SAPX + "ATBEZ='" + atbez + "'", guideModelSap);
                if (field.getInfoForLimitation().equals("") || field.getInfoForLimitation().equals(" ")) {
                    sql.tableFill("select INDEX_LAST,FASON_LAST from LAST_HEAD order BY  lpad(ID, 50)", guideModelLocal);
                } else {
                    sql.tableFill("select a.INDEX_LAST,a.FASON_LAST from LAST_HEAD a left join LAST_INFO b on a.Index_Last = b.index_last left join LAST_INFO_EXTRA c on a.Index_Last = c.index_last WHERE " + field.getInfoForLimitation(), guideModelLocal);
                }
                break;

            case "CODE_FASON_LAST":
                sql.tableFill("select * from (" + SqlOperations.SELECT_FOR_CATALOG_FORM + atprt + " a order BY a.CODE) " + SqlOperations.UNION_REQUEST_SAPX + "ATBEZ='" + atbez + "'", guideModelSap);
                if (field.getInfoForLimitation().equals("") || field.getInfoForLimitation().equals(" ")) {
                    sql.tableFill("select FASON_LAST,INDEX_LAST from LAST_HEAD order BY  lpad(ID, 50)", guideModelLocal);
                } else {
                    sql.tableFill("select a.FASON_LAST,a.INDEX_LAST from LAST_HEAD a left join LAST_INFO b on a.Index_Last = b.index_last left join LAST_INFO_EXTRA c on a.Index_Last = c.index_last WHERE " + field.getInfoForLimitation(), guideModelLocal);
                }
                break;

            case "FASON_SOLE":
                sql.tableFill("select * from (" + SqlOperations.SELECT_FOR_CATALOG_FORM + atprt + " a order BY a.CODE) " + SqlOperations.UNION_REQUEST_SAPX + "ATBEZ='" + atbez + "'", guideModelSap);
                if (field.getInfoForLimitation().equals("") || field.getInfoForLimitation().equals(" ")) {
                    sql.tableFill("select ID,ART from LB_SOLE order BY  lpad(ID, 50)", guideModelLocal);
                } else {
                    jButton3.setVisible(false);
                    sql.fillModel("select a.INDEX_LAST,FASON_SOLE from LAST_HEAD a left join LAST_INFO b on a.Index_Last = b.index_last left join LAST_INFO_EXTRA c on a.Index_Last = c.index_last WHERE " + field.getInfoForLimitation() + " and FASON_SOLE is not null", guideModelLocal, 0);
                    sql.fillModel("select a.INDEX_LAST,FASON_SOLE_2 from LAST_HEAD a left join LAST_INFO b on a.Index_Last = b.index_last inner join LAST_INFO_EXTRA c on a.Index_Last = c.index_last WHERE " + field.getInfoForLimitation() + " and FASON_SOLE_2 is not null", guideModelLocal, 0);
                    sql.fillModel("select a.INDEX_LAST,FASON_SOLE_3 from LAST_HEAD a left join LAST_INFO b on a.Index_Last = b.index_last inner join LAST_INFO_EXTRA c on a.Index_Last = c.index_last WHERE " + field.getInfoForLimitation() + " and FASON_SOLE_3 is not null", guideModelLocal, 0);
                }
                break;

            case "FASON_HEEL":
                sql.tableFill("select * from (" + SqlOperations.SELECT_FOR_CATALOG_FORM + atprt + " a order BY a.CODE) " + SqlOperations.UNION_REQUEST_SAPX + "ATBEZ='" + atbez + "'", guideModelSap);
                if (field.getInfoForLimitation().equals("") || field.getInfoForLimitation().equals(" ")) {
                    sql.tableFill("select ID,NAME from LB_HEEL order BY  lpad(ID, 50)", guideModelLocal);
                } else {
                    jButton3.setVisible(false);
                    sql.fillModel("select a.INDEX_LAST,FASON_HEEL from LAST_HEAD a left join LAST_INFO b on a.Index_Last = b.index_last left join LAST_INFO_EXTRA c on a.Index_Last = c.index_last WHERE " + field.getInfoForLimitation() + " and FASON_HEEL is not null", guideModelLocal, 0);
                    sql.fillModel("select a.INDEX_LAST,FASON_HEEL_2 from LAST_HEAD a left join LAST_INFO b on a.Index_Last = b.index_last inner join LAST_INFO_EXTRA c on a.Index_Last = c.index_last WHERE " + field.getInfoForLimitation() + " and FASON_HEEL_2 is not null", guideModelLocal, 0);
                    sql.fillModel("select a.INDEX_LAST,FASON_HEEL_3 from LAST_HEAD a left join LAST_INFO b on a.Index_Last = b.index_last inner join LAST_INFO_EXTRA c on a.Index_Last = c.index_last WHERE " + field.getInfoForLimitation() + " and FASON_HEEL_3 is not null", guideModelLocal, 0);
                }
                break;

            case "FASON_BASIC_INSOLE":
                sql.tableFill("select ID,NAME from LB_BASIC_INSOLE order BY  lpad(ID, 50))", guideModelLocal);
                sql.tableFill(" select * from (" + SqlOperations.SELECT_FOR_CATALOG_FORM + atprt + " a order BY a.CODE) " + SqlOperations.UNION_REQUEST_SAPX + "ATBEZ='" + atbez + "'", guideModelSap);
                break;

            case "EXTRACT":
                sql.tableFill("select DISTINCT ID,NAME from EXTRACT order by NAME ", guideModelLocal);
                jTabbedPane1.remove(1);
                break;

            default:
                if (atprt.equals("Z_PROVIDER")) {
                    String[] tmp = atnam.split("_");
                    if (tmp.length > 1) {
                        sql.tableFill("select * from (" + SqlOperations.SELECT_FOR_CATALOG_FORM + atprt + "_L a WHERE TYPE like '%" + tmp[1] + "%' order BY a.CODE) " + SqlOperations.UNION_REQUEST_SAPX + "ATBEZ='" + atbez + "'", guideModelLocal);
                    }
                    sql.tableFill("select * from (" + SqlOperations.SELECT_FOR_CATALOG_FORM + atprt + " a order BY lpad(a.CODE, 50)) " + SqlOperations.UNION_REQUEST_SAPX + "ATBEZ='" + atbez + "'", guideModelSap);
                }

                if (atprt.charAt(atprt.length() - 1) == 'L' && atprt.charAt(atprt.length() - 2) == '_') {
                    jButton3.setText("Добавить в локальный справочник");
                    typeSave = 4;
                    sql.tableFill("select * from (" + SqlOperations.SELECT_FOR_CATALOG_FORM + atprt + " a order BY lpad(a.CODE, 50)) " + SqlOperations.UNION_REQUEST_SAPX + "ATBEZ='" + atbez + "'", guideModelLocal);
                } else {
                    jTabbedPane1.setSelectedIndex(1);
                    sql.tableFill("select * from (" + SqlOperations.SELECT_FOR_CATALOG_FORM + atprt + " a order BY lpad(a.CODE, 50)) " + SqlOperations.UNION_REQUEST_SAPX + "ATBEZ='" + atbez + "'", guideModelSap);
                }

                break;

        }

        guideModelLocal.addRow(new Object[]{"", "", 0});
        guideModelLocal.moveRow(guideModelLocal.getRowCount() - 1, guideModelLocal.getRowCount() - 1, 0);
        guideModelSap.addRow(new Object[]{"", "", 0});
        guideModelSap.moveRow(guideModelSap.getRowCount() - 1, guideModelSap.getRowCount() - 1, 0);

        jTextField2.setCaretPosition(0);

        if (guideModelLocal.getRowCount() > 1) {
            jTable1.getColumnModel().getColumn(0).setHeaderValue("Код");
            jTable1.getColumnModel().getColumn(1).setHeaderValue("Наименование");
        }
        if (guideModelSap.getRowCount() > 1) {
            jTable2.getColumnModel().getColumn(0).setHeaderValue("Код");
            jTable2.getColumnModel().getColumn(1).setHeaderValue("Наименование");
        }

        selectValue = false;

        if (GuiStaticVariables.SETTIN_LOCATION_ON_MOUSE) {
            setLocation();
        }
    }

    private void setSearchKeyListner(final boolean notBlockButtn) {
        jButton3.setEnabled(notBlockButtn);
        jTextField2.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {

                sorterLocal = new TableRowSorter<>(guideModelLocal);
                sorterSap = new TableRowSorter<>(guideModelSap);
                jTable1.setRowSorter(sorterLocal);
                jTable2.setRowSorter(sorterSap);
                try {
                    sorterLocal.setRowFilter(RowFilter.regexFilter("(?iu)" + jTextField2.getText()));
                    sorterSap.setRowFilter(RowFilter.regexFilter("(?iu)" + jTextField2.getText()));
                } catch (NullPointerException ex) {
                }
                try {
                    jTable1.changeSelection(0, 1, true, true);
                    jTable1.setRowSelectionInterval(0, 0);
                    jTable2.changeSelection(0, 1, true, true);
                    jTable2.setRowSelectionInterval(0, 0);
                } catch (IllegalArgumentException ex) {
                }

                if (!notBlockButtn) {
                    if (jTable1.getRowCount() == 0) {
                        jButton3.setEnabled(true);
                    } else if (!jTable1.getValueAt(0, 1).toString().equals(jTextField2.getText())) {
                        jButton3.setEnabled(true);
                    } else {
                        jButton3.setEnabled(false);
                    }

                    if (jTable2.getRowCount() == 0) {
                        jButton3.setEnabled(true);
                    } else if (!jTable2.getValueAt(0, 1).toString().equals(jTextField2.getText())) {
                        jButton3.setEnabled(true);
                    } else {
                        jButton3.setEnabled(false);
                    }
                }
            }
        });
    }

    private void update() {
        signsFormation(field.getNameCatalog(), field.getDiscriptionField());
        jTextField2.requestFocusInWindow();
        JOverrideTable.packColumns(jTable1);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }};

            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

            jButton1.setFont(GuiStaticVariables.globalFont);
            jButton1.setText("Отмена");
            jButton1.setPreferredSize(new Dimension((int) (73 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });

            jButton2.setFont(GuiStaticVariables.globalFont);
            jButton2.setText("Добавить");
            jButton2.setPreferredSize(new Dimension((int) (83 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
            jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });

            jButton3.setFont(GuiStaticVariables.globalFont);
            jButton3.setText("Заявка на пополнение");
            jButton3.setPreferredSize(new Dimension((int) (250 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
            jButton3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton3ActionPerformed(evt);
                }
            });

            jTextField2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

            jLabel1.setFont(GuiStaticVariables.globalFont);
            jLabel1.setText("Фильтр:");

            jTable1.setFont(GuiStaticVariables.globalFont);
            jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
            jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable1MouseClicked(evt);
                }
            });
            jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable1KeyPressed(evt);
                }
            });
            jScrollPane2.setViewportView(jTable1);

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
            );

            jTabbedPane1.addTab("Local", jPanel1);

            jTable2.setFont(GuiStaticVariables.globalFont);
            jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
            jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable2MouseClicked(evt);
                }
            });
            jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    jTable2KeyPressed(evt);
                }
            });
            jScrollPane1.setViewportView(jTable2);

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
            );

            jTabbedPane1.addTab("SAP", jPanel2);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTabbedPane1)
                        .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void textFill(JTable table, int selRow) {
        try {
            if (atbez != null) {
                if (!sql.strFill("select distinct VALUE from BLACK_LIST where VALUE = '" + table.getValueAt(selRow, 1) + "' and ATBEZ = '" + atbez + "'").equals("")) {
                    JOptionPane.showMessageDialog(this, "Значение " + table.getValueAt(selRow, 1) + " находится в черном списке!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            field.setText(table.getValueAt(selRow, 0).toString(), table.getValueAt(selRow, 1).toString());

            this.setVisible(false);
            this.dispose();
        } catch (ArrayIndexOutOfBoundsException e) {
            this.setVisible(false);
            this.dispose();
            selectValue = false;
        }
    }

    private void addNewAttr(int typeSave) {
        switch (typeSave) {
            case 1: {
                if (jTextField2.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "Пожалуйста,перед сохранением," + "\n" + "введите значение!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                saveRequestValue();
                JOptionPane.showMessageDialog(this, "Заявка на пополнение справочника сохранена!", "Уведомление", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
            case 2: {
                switch (field.getDiscriptionField()) {
                    case "FASON_SOLE":
                        fr.openForm(new AddSoleForm("Подошва", "Sole", true, true, field.getTextField()), FormRegister.SOME_KEY_FORM);
                        break;
                    case "FASON_HEEL":
                        fr.openForm(new AddHeelForm("Каблук", "Heel", true, true, field.getTextField()), FormRegister.SOME_KEY_FORM);
                        break;
                    case "FASON_INSOLE":
                        fr.openForm(new AddInsoleForm("Основная стелька", "Base_insole", true, true, field.getTextField()), FormRegister.SOME_KEY_FORM);
                        break;
                }
                this.setVisible(false);
                this.dispose();
                break;
            }
            case 3: {
                if (jTable1.getSelectedRow() > -1) {
                    sql.SendQuery("delete from extract where NAME='" + jTable1.getValueAt(jTable1.getSelectedRow(), 1) + "'");
                    DefaultTableModel tmpModel = (DefaultTableModel) jTable1.getModel();
                    tmpModel.removeRow(jTable1.getSelectedRow());
                }
                break;
            }
            case 4: {
                seveToLocalTable();
                update();
                break;
            }
        }
    }

    private void seveToLocalTable() {
        BigDecimal nextID = (BigDecimal) sql.getObj("select NVL(max(TO_NUMBER(CODE)),0)+1 from " + field.getNameCatalog());
        if (nextID != null && !jTextField2.getText().equals("")) {
            sql.SendQuery("insert into " + field.getNameCatalog() + " values('" + nextID + "','" + jTextField2.getText() + "')");
            log.logWriting("Добавлены данные " + jTextField2.getText() + " в справочник " + field.getNameCatalog());
        }
    }

    private void saveRequestValue() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            PreparedStatement ps;
            ps = sql.preparingTableFill(SqlOperations.REQUEST_SAPX_SAVING);
            if (ps != null) {

                ps.setString(1, atbez);
                ps.setString(2, jTextField2.getText());
                ps.setString(3, SecurityManager.userName);
                ps.setString(4, addr.getHostName());
                ps.setString(5, "0");
                ps.setString(6, jTextField2.getText());

                ps.setString(7, atbez);
                ps.setString(8, jTextField2.getText());
                ps.setString(9, SecurityManager.userName);
                ps.setString(10, addr.getHostName());
                ps.setString(11, "0");
                ps.setString(12, jTextField2.getText());

                ps.execute();
                ps.close();

                update();
            }
        } catch (SQLException | UnknownHostException ex) {
            Logger.getLogger(SecurityManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setLocation() {
        Point location = MouseInfo.getPointerInfo().getLocation();

        if ((location.x + getWidth()) > GuiStaticVariables.screenWidth) {
            location.x = location.x - getWidth();
        }

        if ((location.y + getHeight()) > GuiStaticVariables.screenHeight) {
            location.y = location.y - getHeight();
        } else if ((location.y - getHeight()) > 0) {
            location.y = location.y - (getHeight() / 2);
        }

        setLocation(location);
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jTabbedPane1.getSelectedIndex() == 0) {
            textFill(jTable1, jTable1.getSelectedRow());
        } else {
            textFill(jTable2, jTable2.getSelectedRow());
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        selectValue = false;
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            jButton2ActionPerformed(null);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        addNewAttr(typeSave);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jButton2ActionPerformed(null);
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getClickCount() == 2) {
            jButton2ActionPerformed(null);
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jButton2ActionPerformed(null);
        }
    }//GEN-LAST:event_jTable2KeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
