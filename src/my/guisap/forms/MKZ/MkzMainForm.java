package my.guisap.forms.MKZ;

import my.guisap.utils.SecurityManager;
import baseclass.BInternalFrame;
import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import my.guisap.forms.Calculation.CalculationMain;
import my.guisap.FormRegister;
import my.guisap.GuiStaticVariables;
import my.guisap.componenst.MyImageCellRenderer;
import my.guisap.forms.ImageForm;
import my.guisap.forms.LifeLineForm;
import my.guisap.forms.RejectForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.ImageUtils;
import my.guisap.utils.LifeLineUtils;

public class MkzMainForm extends BInternalFrame {

    int NowSelected = 0;

    TableRowSorter<DefaultTableModel> sorter;

    JPopupMenu popupmenu = new JPopupMenu();

    DefaultTableModel OutTable;
    DefaultTableModel tTable;
    Map<String, ImageIcon> imageMap = new HashMap<>();
    Map<String, String> fileMap = new HashMap<>();

    final String[] AllQuery = new String[]{
        SqlOperations.MKZ_LIST + SqlOperations.MKZ_LIST_ALL,
        SqlOperations.MKZ_LIST + SqlOperations.MKZ_LIST_FIRST,
        SqlOperations.MKZ_LIST + SqlOperations.MKZ_LIST_SECOND,
        SqlOperations.MKZ_LIST + SqlOperations.MKZ_LIST_WORK,
        SqlOperations.MKZ_LIST + SqlOperations.MKZ_LIST_CLOSE,
        SqlOperations.MKZ_LIST + SqlOperations.MKZ_LIST_REJECT,
        SqlOperations.MKZ_LIST + SqlOperations.MKZ_LIST_LAST,
        SqlOperations.MKZ_LIST + SqlOperations.MKZ_LIST_ARH
    };

    int numberColumnStatus = 15;
    int numberColumnIndex = 2;

    public MkzMainForm() {
        setName("МКЗ");
        initComponents();
        setTitle("Редактирование моделей");
        setIcons();
        createPopMenu();
        modelFormation(null);

        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelFormation(null);
            }
        });

        jTable2.setDefaultRenderer(jTable2.getColumnClass(1),
                new MyImageCellRenderer());
        jTable2.setRowHeight(60);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(50);

    }

    public void modelFormation(String customSQL) {
        tTable = new DefaultTableModel();

        String query = AllQuery[jComboBox1.getSelectedIndex()];

        if (jCheckBox2.isSelected()) {
            query = "select a.* from (" + AllQuery[jComboBox1.getSelectedIndex()]
                    + " ) a INNER JOIN MKZ_LIFE_LINE b ON a.\"Модель\"=b.id where LIFE_LINE like 'F/________________/" + SecurityManager.userName + ";%' ORDER BY a.\"Дата изменения\" DESC";
        }

        if (customSQL != null) {
            query = customSQL;
        }

        sql.tableFill(query, tTable);

        int FirstElem = 2;

        Object[] columnNames = new Object[tTable.getColumnCount() + FirstElem];
        Object[][] data = new Object[tTable.getRowCount()][tTable.getColumnCount() + FirstElem];

        columnNames[0] = "";
        columnNames[1] = "Изображение";

        for (int i = 0; i < tTable.getColumnCount(); i++) {
            if (tTable.getColumnName(i) != null) {
                columnNames[FirstElem + i] = tTable.getColumnName(i);
            } else {
                columnNames[FirstElem + i] = "";
            }
        }

        for (int i = 0; i < tTable.getRowCount(); i++) {
            for (int j = 0; j < tTable.getColumnCount(); j++) {
                if (tTable.getValueAt(i, j) != null) {
                    data[i][FirstElem + j] = tTable.getValueAt(i, j).toString();
                } else {
                    data[i][FirstElem + j] = "";
                }
            }
            data[i][0] = new Boolean(false);
        }

        OutTable = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return Boolean.class;
                } else if (column == 1) {
                    return ImageIcon.class;
                }
                return super.getColumnClass(column);
            }
        };

        //подгружаем картинки в другом потоке
        Thread t = new Thread() {
            @Override
            public void run() {
                createMapFiles();
            }
        };
        t.start();

        jTable2.setModel(OutTable);

        jTable2.getColumnModel()
                .getColumn(0).setMaxWidth(40);
        jTable2.setAutoCreateRowSorter(
                true);
        jTable2.getTableHeader()
                .setReorderingAllowed(false);

        sorter = new TableRowSorter<>(OutTable);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton5 = new javax.swing.JButton();
        jButton2 = ComponentsUtils.createBtn("",160,25,true);
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = TableRowFilterSupport
        .forTable(new JTable() {
            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                if (arg1 == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        })
        .searchable(true)
        .actions(true)
        .apply();
        jComboBox1 = ComponentsUtils.createComboBox(200, 25, true);
        jButton1 = ComponentsUtils.createBtn("",160,25,true);
        jButton3 = ComponentsUtils.createBtn("",160,25,true);
        checkbox1 = new java.awt.Checkbox();
        jButton4 = ComponentsUtils.createBtn("Редактировать колодку",200,25,true);
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton6 = ComponentsUtils.createBtn("",25,25,true);
        jButton8 = ComponentsUtils.createBtn("",33,25,true);
        jButton9 = ComponentsUtils.createBtn("Редактировать колодку",150,25,true);
        jButton10 = ComponentsUtils.createBtn("Редактировать колодку",150,25,true);
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton11 = ComponentsUtils.createBtn("",160,25,true);
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        jButton5.setText("jButton5");

        jButton2.setFont(GuiStaticVariables.globalFont);
        jButton2.setText("Добавить новую модель");
        jButton2.setPreferredSize(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

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
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTable2.setCellSelectionEnabled(true);
        jTable2.setOpaque(false);
        jTable2.setRowHeight(50);
        jTable2.setUpdateSelectionOnSort(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable2MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jComboBox1.setFont(GuiStaticVariables.globalFont);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Все", "Первый этап", "Второй этап", "На рассмотрении", "Рассмотренные", "Отклоненные", "Ожидание колодки" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setFont(GuiStaticVariables.globalFont);
        jButton1.setText("Редактировать модель");
        jButton1.setPreferredSize(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setFont(GuiStaticVariables.globalFont);
        jButton3.setText("Выгрузить в Excel");
        jButton3.setPreferredSize(null);
        jButton3.setRequestFocusEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        checkbox1.setFont(GuiStaticVariables.globalFont);
        checkbox1.setLabel("Открыть папку");

        jButton4.setFont(GuiStaticVariables.globalFont);
        jButton4.setText("Добавить на основе существующей");
        jButton4.setPreferredSize(null);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setFont(GuiStaticVariables.globalFont);
        jLabel1.setText("Поиск:");

        jTextField1.setFont(GuiStaticVariables.globalFont);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                jTable2.setRowSorter(sorter);
                try {
                    sorter.setRowFilter(RowFilter.regexFilter("(?iu)" + jTextField1.getText()));
                } catch (NullPointerException ex) {
                }
                try {
                    jTable2.changeSelection(0, 1, true, true);
                    jTable2.setRowSelectionInterval(0, 0);
                } catch (IllegalArgumentException ex) {
                }
            }
        });

        jButton6.setMaximumSize(new java.awt.Dimension(33, 23));
        jButton6.setMinimumSize(new java.awt.Dimension(33, 23));
        jButton6.setPreferredSize(null);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setOpaque(false);
        jButton8.setPreferredSize(null);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(GuiStaticVariables.globalFont);
        jButton9.setText("Принять");
        jButton9.setMaximumSize(null);
        jButton9.setPreferredSize(null);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(GuiStaticVariables.globalFont);
        jButton10.setText("Отклонить");
        jButton10.setMaximumSize(new java.awt.Dimension(89, 25));
        jButton10.setMinimumSize(new java.awt.Dimension(89, 25));
        jButton10.setPreferredSize(null);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jCheckBox1.setFont(GuiStaticVariables.globalFont);
        jCheckBox1.setText("На доработку");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(GuiStaticVariables.globalFont);
        jLabel2.setText("Загружено 0 из ");

        jLabel3.setFont(GuiStaticVariables.globalFont);
        jLabel3.setText("0");

        jCheckBox2.setFont(GuiStaticVariables.globalFont);
        jCheckBox2.setText("Только мои записи");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jButton11.setFont(GuiStaticVariables.globalFont);
        jButton11.setText("Создать экстракт");
        jButton11.setPreferredSize(null);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jMenuBar1.setBorder(null);

        jMenu1.setText("Управление записями");

        jMenuItem1.setText("Добавить новую модель");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Добавить новую модель на основе существующей");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Редактировать выбранную модель");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Администрирование");

        jMenuItem4.setText("Принять");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem6.setText("Отклонить");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem5.setText("Отправить на доработку");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(checkbox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        fr.openForm(new MkzAddModel("Модель ", GuiStaticVariables.MODEL_NUMBER, this, "", false), FormRegister.ONE_KEY_FORM);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //проверка, выбрана ли модель
        try {
            DefaultTableModel dtm = new DefaultTableModel();
            String Model = jTable2.getValueAt(jTable2.getSelectedRow(), numberColumnIndex).toString();
            sql.tableFill("select case when status = 'F' or status = 'L' or status = 'LC' then 1 else 2 end stat from GUI_SAP.MKZ_LIST_HEAD where model = '" + Model + "'", dtm);
            if (dtm.getValueAt(0, 0) != null) {
                if (Integer.valueOf(dtm.getValueAt(0, 0).toString()) == 1) {
                    fr.openForm(new MkzAddModel("Модель ", GuiStaticVariables.MODEL_NUMBER, this, Model, true), FormRegister.ONE_KEY_FORM);
                } else {
                    fr.openForm(new MkzEditModel("Модель ", GuiStaticVariables.MODEL_NUMBER, this, Model), FormRegister.ONE_KEY_FORM);
                }
            } else {
                fr.openForm(new MkzAddModel("Модель ", GuiStaticVariables.MODEL_NUMBER, this, Model, true), FormRegister.ONE_KEY_FORM);
            }

        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Выберите модель для редактирования", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //проверка на выбранную ячейку
        if (jTable2.getSelectedColumn() != -1) {
            TableModel model = jTable2.getModel();

            //создание папки
            File folder = new File("result/");
            //расположение создаваемого файла
            String way = folder + "/" + model.getValueAt(jTable2.getSelectedRow(), numberColumnIndex).toString() + ".xls";

            //проверка созданна ли такая директория
            if (!folder.exists()) {
                folder.mkdir();
            }

            //создание Exel файла и сохранение его
            try (FileOutputStream fw = new FileOutputStream(way)) {
                MkzSaveToExcel save = new MkzSaveToExcel(fw, model.getValueAt(jTable2.getSelectedRow(), numberColumnIndex).toString());
                JOptionPane.showMessageDialog(this, "Файл успешно выгружен " + "(/" + way + ")", "Информация", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Нет доступа к файлу", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            }

            //проверка флага "Открыть папку", если true, то открыват папку с сохраненным файлом
            if (checkbox1.getState() == true) {
                try {
                    Runtime.getRuntime().exec("explorer " + folder);
                } catch (IOException ex) {
                    Logger.getLogger(MkzMainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "Не выбранна модель для выгрузки", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getClickCount() == 2) {
            switch (jTable2.getSelectedColumn()) {
                case 1:
                    fr.openForm(new ImageForm(jTable2.getValueAt(jTable2.getSelectedRow(), numberColumnIndex).toString(), 0), FormRegister.SOME_KEY_FORM);
                    break;
                case 15:
                    fr.openForm(new LifeLineForm(LifeLineUtils.MKZ_LIFE_LINE, jTable2.getValueAt(jTable2.getSelectedRow(), numberColumnIndex).toString()), FormRegister.SOME_KEY_FORM);
                    break;
                default:
                    jButton1ActionPerformed(null);
                    break;
            }
        }

    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            String Model = jTable2.getValueAt(jTable2.getSelectedRow(), numberColumnIndex).toString();
            fr.openForm(new MkzAddModel("Модель ", GuiStaticVariables.MODEL_NUMBER, this, Model, false), FormRegister.ONE_KEY_FORM);
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Выберите модель", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            fr.openForm(new ImageForm(jTable2.getValueAt(jTable2.getSelectedRow(), numberColumnIndex).toString(), 0), FormRegister.SOME_KEY_FORM);
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Не выбранна модель", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        modelFormation(null);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        DefaultTableModel tmp = (DefaultTableModel) jTable2.getModel();
        int checkEdit = 0;
        for (int i = 0; i < tmp.getRowCount(); i++) {
            if ((boolean) tmp.getValueAt(i, 0) == true) {
                if (tmp.getValueAt(i, numberColumnStatus).equals("На рассмотрении")) {
                    sql.SendQuery("update GUI_SAP.MKZ_LIST_HEAD set status = 'C' where MODEL = '" + tmp.getValueAt(i, numberColumnIndex) + "'");
                    LifeLineUtils.UpdateLifeLine(LifeLineUtils.MKZ_LIFE_LINE, tmp.getValueAt(i, numberColumnIndex).toString(), "C");
                    checkEdit++;
                }
            }
        }
        if (checkEdit > 0) {
            modelFormation(null);
            JOptionPane.showMessageDialog(this, checkEdit + " записей успешно подтверждены", "Успешно", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                if (jTable2.getValueAt(jTable2.getSelectedRow(), numberColumnStatus).equals("На рассмотрении")) {
                    String selectModel = jTable2.getValueAt(jTable2.getSelectedRow(), numberColumnIndex).toString();
                    int n = JOptionPane.showConfirmDialog(
                            rootPane,
                            "Подтвердить выбранную запись?",
                            "Внимание",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        sql.SendQuery("update GUI_SAP.MKZ_LIST_HEAD set status = 'C' where MODEL = '" + selectModel + "'");
                        LifeLineUtils.UpdateLifeLine(LifeLineUtils.MKZ_LIFE_LINE, selectModel, "C");
                        checkEdit++;
                        modelFormation(null);
                    } else {
                        return;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(this, "Выберите записи для подтверждения", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        if (checkEdit == 0) {
            JOptionPane.showMessageDialog(this, "Для подтверждения доступны записи со статусом 'На рассмотрении'", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        DefaultTableModel tmp = (DefaultTableModel) jTable2.getModel();
        final String status;
        if (jCheckBox1.isSelected()) {
            status = "R_U";
        } else {
            status = "R";
        }
        int checkEdit = 0;
        for (int i = 0; i < tmp.getRowCount(); i++) {
            if ((boolean) tmp.getValueAt(i, 0) == true) {
                if (!tmp.getValueAt(i, numberColumnStatus).equals("Рассмотрен") && !tmp.getValueAt(i, numberColumnStatus).equals("Отклонено")) {
                    sql.SendQuery("update GUI_SAP.MKZ_LIST_HEAD set status = '" + status + "' where MODEL = '" + tmp.getValueAt(i, numberColumnIndex) + "'");
                    LifeLineUtils.UpdateLifeLine(LifeLineUtils.MKZ_LIFE_LINE, tmp.getValueAt(i, numberColumnIndex).toString(), status);
                    sql.SendQuery("update GUI_SAP.MKZ_MODEL_ART set status = 'F' where MODEL_ID = (select ID from MKZ_LIST_HEAD where MODEL='" + tmp.getValueAt(i, numberColumnIndex) + "')");
                    sql.SendQuery("insert into REJECT_LIST (ID,TYPE_OBJECT,OBJECT,TEXT) values (SEQ_REJECT_LIST.NEXTVAL,'Модель','" + tmp.getValueAt(i, numberColumnIndex) + "','')");
                    checkEdit++;
                }
            }
        }
        if (checkEdit > 0) {
            modelFormation(null);
            JOptionPane.showMessageDialog(this, checkEdit + " записей успешно отклонены", "Успешно", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                Thread saveInfo = new Thread(new Runnable() {
                    @Override
                    public void run() //Этот метод будет выполняться в побочном потоке
                    {
                        sql.SendQuery("update GUI_SAP.MKZ_LIST_HEAD set status = '" + status + "' where MODEL = '" + jTable2.getValueAt(jTable2.getSelectedRow(), numberColumnIndex).toString() + "'");
                        sql.SendQuery("update GUI_SAP.MKZ_MODEL_ART set status = 'F' where MODEL_ID = (select ID from MKZ_LIST_HEAD where MODEL='" + jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString() + "')");
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                LifeLineUtils.UpdateLifeLine(LifeLineUtils.MKZ_LIFE_LINE, jTable2.getValueAt(jTable2.getSelectedRow(), numberColumnIndex).toString(), status);
                                modelFormation(null);
                            }
                        });
                    }
                });
                fr.openForm(new RejectForm(jTable2.getValueAt(jTable2.getSelectedRow(), numberColumnIndex).toString(), "Модель", saveInfo), FormRegister.SOME_KEY_FORM);
            } catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(this, "Выберите записи для отклонения", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    //нажатие правой кнопки мыши на таблице
    private void jTable2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MousePressed
        if (evt.getButton() == MouseEvent.BUTTON3) {
            int column = jTable2.columnAtPoint(evt.getPoint());
            int row = jTable2.rowAtPoint(evt.getPoint());
            jTable2.setColumnSelectionInterval(column, column);
            jTable2.setRowSelectionInterval(row, row);
            popupmenu.show(jTable2, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTable2MousePressed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        modelFormation(null);
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        DefaultTableModel tmp = (DefaultTableModel) jTable2.getModel();
        ArrayList<String> listModel = new ArrayList<>();
        for (int i = 0; i < tmp.getRowCount(); i++) {
            if ((boolean) tmp.getValueAt(i, 0) == true) {
                if (tmp.getValueAt(i, numberColumnStatus).equals("Рассмотрен")) {
                    listModel.add(tmp.getValueAt(i, numberColumnIndex).toString());
                }
            }
        }

        if (listModel.size() > 0) {
            fr.openForm(new CalculationMain(listModel), FormRegister.SOME_KEY_FORM);
        } else {
            JOptionPane.showMessageDialog(this, "Выберите записи для создания экстракта (использовать можно только записи со статусом 'Рассмотрен')", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        jButton9ActionPerformed(null);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        jButton10ActionPerformed(null);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        jButton10ActionPerformed(null);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        jButton2ActionPerformed(null);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        jButton4ActionPerformed(null);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        jButton1ActionPerformed(null);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    // An inner class to show when popup events occur
    private void setIcons() {
        jButton1.setIcon(ImageUtils.createIconForButton("images/edit.png"));
        jButton4.setIcon(ImageUtils.createIconForButton("images/copy.png"));
        jButton2.setIcon(ImageUtils.createIconForButton("images/plus.png"));
        jButton3.setIcon(ImageUtils.createIconForButton("images/doc.png"));
        jButton6.setIcon(ImageUtils.createIconForButton("images/img.png"));
        jButton8.setIcon(ImageUtils.createIconForButton("images/update.png"));
    }

    private void createPopMenu() {
        JMenuItem opModel = new JMenuItem("Открыть модель");
        opModel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1ActionPerformed(null);
            }
        });
        JMenuItem opImage = new JMenuItem("Открыть изображение");
        opImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.openForm(new ImageForm(jTable2.getValueAt(jTable2.getSelectedRow(), numberColumnIndex).toString(), 0), FormRegister.SOME_KEY_FORM);
            }
        });
        JMenuItem opLifeLine = new JMenuItem("Открыть жизненный цикл");
        opLifeLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.openForm(new LifeLineForm(LifeLineUtils.MKZ_LIFE_LINE, jTable2.getValueAt(jTable2.getSelectedRow(), numberColumnIndex).toString()), FormRegister.SOME_KEY_FORM);
            }
        });
        JMenuItem update = new JMenuItem("Обновить");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelFormation(null);
            }
        });

        popupmenu.add(opModel);
        popupmenu.add(opImage);
        popupmenu.add(opLifeLine);
        popupmenu.add(update);
        jTable2.add(popupmenu);
    }

    public void createMapFiles() {

        jLabel2.setVisible(true);
        jLabel3.setVisible(true);

        File F = new File(GuiStaticVariables.DEF_PICTURESICONS_PATH);

        FilenameFilter imageFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                return lowercaseName.endsWith(".jpg");
            }
        };

        String[] nameList = F.list(imageFilter);
        File[] fileList = F.listFiles(imageFilter);

        jLabel3.setText("из " + nameList.length + " фото");

        for (int i = 0; i < nameList.length; i++) {
            fileMap.put(nameList[i], fileList[i].toString());
        }

        if (ImageUtils.cacheImageMap.isEmpty()) {
            for (int i = 0; i < OutTable.getRowCount(); i++) {
                try {
                    ImageIcon image = new ImageIcon(fileMap.get(OutTable.getValueAt(i, numberColumnIndex).toString() + ".jpg"));
                    OutTable.setValueAt(image, i, 1);
                    ImageUtils.cacheImageMap.put(OutTable.getValueAt(i, numberColumnIndex).toString(), image);
                    jLabel2.setText("Загружено " + i);
                } catch (NullPointerException ex) {

                }
            }
        } else {
            for (int i = 0; i < OutTable.getRowCount(); i++) {
                try {
                    ImageIcon image = ImageUtils.cacheImageMap.get(OutTable.getValueAt(i, numberColumnIndex).toString());
                    if (image == null) {
                        image = new ImageIcon(GuiStaticVariables.DEF_PICTURESICONS_PATH + "" + GuiStaticVariables.SEPARATOR + "" + OutTable.getValueAt(i, numberColumnIndex).toString() + ".jpg");
                        ImageUtils.cacheImageMap.put(OutTable.getValueAt(i, numberColumnIndex).toString(), image);
                    }
                    OutTable.setValueAt(image, i, 1);
                    jLabel2.setText("Загружено " + i);
                } catch (NullPointerException ex) {

                }
            }
        }

        jLabel2.setVisible(
                false);
        jLabel3.setVisible(
                false);

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Checkbox checkbox1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

}
