package my.guisap.forms.BottomDetails;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import my.guisap.utils.CacheImage;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.CreateFormUtils;

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

    JTable jTable;

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

            switch (table) {
                case "LB_SOLE":
                    sql.tableFill("SELECT '' \"Фото модели\",MODEL \"Модель\" from MKZ_LIST_HEAD where FASON_SOLE='" + tmp.getValueAt(i, 0).toString() + "'", models);
                    break;
                case "LB_HEEL":
                    sql.tableFill("SELECT '' \"Фото модели\",MODEL \"Модель\" from MKZ_LIST_HEAD where FASON_HEEL='" + tmp.getValueAt(i, 0).toString() + "'", models);
                    break;
            }

            if (models.getRowCount() > 0) {

                jTable = new JTable() {
                    @Override
                    public boolean isCellEditable(int arg0, int arg1) {
                        return false;
                    }
                };

                jTable.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        jTableMouseClicked(evt, jTable);
                    }
                });

                jTable.setCellSelectionEnabled(true);
                jTable.setModel(models);
                jTable.setDefaultRenderer(jTable.getColumnClass(0),
                        new MyImageCellRenderer());
                jTable.setRowHeight(80);
                jTable.getColumnModel().getColumn(1).setPreferredWidth(40);

                Thread t = new Thread() {
                    @Override
                    public void run() {
                        CacheImage.loadImageToTable(CacheImage.TYPE_MKZ, CacheImage.cacheModel, jTable, 1, 0);
                    }
                };
                t.start();

                panel.add(jTable, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

            } else {
                panel.add(ComponentsUtils.createLabel("Модели на данном артикуле отсутствуют"), new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            }
            panel.setBorder(CreateFormUtils.defaultBorder);
            pnlAttElem.add(panel);

        }

        pack();
        setCenter();

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
