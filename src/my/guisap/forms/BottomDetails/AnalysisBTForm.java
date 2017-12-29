/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.forms.BottomDetails;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import my.guisap.FormRegister;
import my.guisap.GuiStaticVariables;
import my.guisap.componenst.EmptyForm;
import my.guisap.componenst.MyImageCellRenderer;
import my.guisap.forms.MKZ.MkzEditModel;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.CreateFormUtils;
import my.guisap.utils.ImageUtils;

/**
 *
 * @author Dima
 */
public class AnalysisBTForm extends EmptyForm {

    String fason;
    String table;
    Map<String, String> fileMap = new HashMap<>();
    DefaultTableModel models;
    int numberColumnIndex = 1;

    public AnalysisBTForm(String caption, String classFlag, boolean needToSave, boolean needSaveSize, String fason, String table) {
        super(caption, classFlag, needToSave, needSaveSize);
        setTitle("Анализ фасона");
        this.fason = fason;
        this.table = table;
        createFormFields();
    }

    private void createFormFields() {

        DefaultTableModel tmp = new DefaultTableModel();
        sql.tableFill("SELECT ART from " + table + " where NAME='" + fason + "' order by art", tmp);

        for (int i = 0; i < tmp.getRowCount(); i++) {
            JPanel panel = new JPanel(new GridBagLayout());
            panel.add(ComponentsUtils.createLabel("Артикул: " + tmp.getValueAt(i, 0).toString()), new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            models = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int column) {
                    if (column == 0) {
                        return ImageIcon.class;
                    }
                    return super.getColumnClass(column);
                }
            };
            sql.tableFill("SELECT '' \"Фото модели\",MODEL \"Модель\" from MKZ_LIST_HEAD where FASON_SOLE='" + tmp.getValueAt(i, 0).toString() + "'", models);
            if (models.getRowCount() > 0) {

                JTable tmpTable = new JTable() {
                    @Override
                    public boolean isCellEditable(int arg0, int arg1) {
                        return false;
                    }
                };

                tmpTable.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        jTableMouseClicked(evt, tmpTable);
                    }
                });

                tmpTable.setCellSelectionEnabled(true);
                tmpTable.setModel(models);
                tmpTable.setDefaultRenderer(tmpTable.getColumnClass(0),
                        new MyImageCellRenderer());
                tmpTable.setRowHeight(80);
                tmpTable.getColumnModel().getColumn(1).setPreferredWidth(40);

                panel.add(tmpTable, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            } else {
                panel.add(ComponentsUtils.createLabel("Модели на данном артикуле отсутствуют"), new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            }
            panel.setBorder(CreateFormUtils.defaultBorder);
            pnlAttElem.add(panel);
            createMapFiles();
        }

        pack();
        setCenter();

    }

    public void createMapFiles() {

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

        for (int i = 0; i < nameList.length; i++) {
            fileMap.put(nameList[i], fileList[i].toString());
        }

        if (ImageUtils.cacheImageMap.isEmpty()) {
            for (int i = 0; i < models.getRowCount(); i++) {
                try {
                    ImageIcon image = new ImageIcon(fileMap.get(models.getValueAt(i, numberColumnIndex).toString() + ".jpg"));
                    models.setValueAt(image, i, 0);
                    ImageUtils.cacheImageMap.put(models.getValueAt(i, numberColumnIndex).toString(), image);
                } catch (NullPointerException ex) {

                }
            }
        } else {
            for (int i = 0; i < models.getRowCount(); i++) {
                try {
                    ImageIcon image = ImageUtils.cacheImageMap.get(models.getValueAt(i, numberColumnIndex).toString());
                    if (image == null) {
                        image = new ImageIcon(GuiStaticVariables.DEF_PICTURESICONS_PATH + "" + GuiStaticVariables.SEPARATOR + "" + models.getValueAt(i, numberColumnIndex).toString() + ".jpg");
                        ImageUtils.cacheImageMap.put(models.getValueAt(i, numberColumnIndex).toString(), image);
                    }
                    models.setValueAt(image, i, 0);
                } catch (NullPointerException ex) {

                }
            }
        }

    }

    private void jTableMouseClicked(java.awt.event.MouseEvent evt, JTable table) {
        if (evt.getClickCount() == 2) {
            switch (table.getSelectedColumn()) {
                case 1:
                    fr.openForm(new MkzEditModel("Модель ", GuiStaticVariables.MODEL_NUMBER, table.getValueAt(table.getSelectedRow(), numberColumnIndex).toString()), FormRegister.SOME_KEY_FORM);
                    break;
            }
        }

    }

}
