/*
 * Класс окна добавления колодок
 */
package my.guisap.forms.Last;

import my.guisap.utils.LogClass;
import my.guisap.componenst.EmptyForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import my.guisap.FormRegister;
import my.guisap.componenst.NewDataPanel;
import my.guisap.forms.SimpleTableForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.LifeLineUtils;
import my.guisap.utils.TextUtils;

/**
 *
 * @author KiselevDA
 */
public class LastEdit extends EmptyForm {

    NewDataPanel mainData = new NewDataPanel("LAST_HEAD", "ID", "LastEdit", 1);

//    DataPanel mainData = new DataPanel("LAST_HEAD", "ID", 1);
//    DataPanel infoData = new DataPanel("LAST_INFO", "INDEX_LAST", 1);
//    DataPanel extraInfoData = new DataPanel("LAST_INFO_EXTRA", "INDEX_LAST", 1);
    String classFlag;
    LastMain ParrentForm;
    String indexLast;
    String model;
    //генерация нового индекса (если не передан через конструктор)
    String idLast = "SEQ_LAST_HEAD.NEXTVAL";

    StringBuilder formIndexLast = new StringBuilder("00000/18-0");
    boolean isEditing = false;

    public LastEdit(String caption, String classFlag, LastMain ParrentForm) {
        super(caption, classFlag, true, false);
        this.ParrentForm = ParrentForm;
        this.classFlag = classFlag;
        log = LogClass.getInstance();
        createFormFields();
        formIndex();
    }

    /**
     *
     * есть 2 варианта открытия этого окна, для редактирования, и создания на
     * основе
     *
     * @param caption
     * @param classFlag
     * @param ParrentForm
     * @param indexLast индекс колодки
     * @param isEditing отвечает за выбор режима, true - редактирование, false
     * -создания на основе
     */
    public LastEdit(String caption, String classFlag, LastMain ParrentForm, String indexLast, boolean isEditing) {
        super(caption, classFlag, true, false);
        this.ParrentForm = ParrentForm;
        this.classFlag = classFlag;
        log = LogClass.getInstance();
        createFormFields();
        DefaultTableModel idModel = new DefaultTableModel();
        sql.tableFill("SELECT ID FROM LAST_HEAD WHERE INDEX_LAST='" + indexLast + "'", idModel);
        if (idModel.getRowCount() == 1) {
            // ' используются для SQL запросов
            this.idLast = "'" + idModel.getValueAt(0, 0).toString() + "'";
        }
        this.isEditing = isEditing;
        this.indexLast = indexLast;
        fillFields();
    }

    //открытие окна при обработке заявки
    public LastEdit(String caption, String classFlag, LastMain ParrentForm, String indexLast, final String model) {
        super(caption, classFlag, true, false);
        this.ParrentForm = ParrentForm;
        this.classFlag = classFlag;
        this.model = model;
        fr = FormRegister.getInstance();
        log = LogClass.getInstance();
        JButton openModel = ComponentsUtils.createBtn("Открыть модель", 150, 23, true);
        openModel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fr.openForm(new SimpleTableForm("Модель:" + model, SqlOperations.MKZ_LIST + " where MODEL='" + model + "'"), FormRegister.ONE_KEY_FORM);
            }
        });
        pnlSouth.add(new JLabel("    "));
        pnlSouth.add(openModel);
        createFormFields();
        formIndex();
        fillFields(model);
    }

    private void createFormFields() {
        mainData.getTextField(1).setEnabled(true);
        mainData.setCheckFields(true);
        pnlAttElem.add(mainData);
        pack();
        setCenter();
    }

    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        saveToDB("FL");
    }

    private void fillFields() {
        mainData.fillFields(SqlOperations.LAST_LIST_TO_EDIT + " where INDEX_LAST='" + indexLast + "'", 0);
        if (isEditing) {
            mainData.blockFields(new int[]{0, 1, 2, 3, 4, 5, 6});
        } else {
            formIndex();
        }
    }

    private void formIndex() {

        mainData.setText(0, formIndexLast.toString());

        //род обуви
        mainData.getTextField(3).addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                switch (mainData.getText(3)) {
                    case "Мужской":
                        formIndexLast.setCharAt(0, '9');
                        break;
                    case "Женский":
                        formIndexLast.setCharAt(0, '8');
                        break;
                    default:
                        formIndexLast.setCharAt(0, '0');
                        break;
                }
                updateIndex();
            }
        });

        //величина ппч
        mainData.getTextField(4).addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                if (TextUtils.checkStringToNumber(mainData.getText(4), 10, 95, 5)) {
                    formIndexLast.setCharAt(1, mainData.getText(4).charAt(0));
                } else {
                    formIndexLast.setCharAt(1, '0');
                }
                updateIndex();
            }
        });

        //год разработки
        mainData.getSpinnerField(5).addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (TextUtils.checkStringToNumber(mainData.getText(5), 1900, 2100)) {
                    formIndexLast.setCharAt(6, mainData.getText(5).charAt(2));
                    formIndexLast.setCharAt(7, mainData.getText(5).charAt(3));
                } else {
                    formIndexLast.setCharAt(6, '0');
                    formIndexLast.setCharAt(7, '0');
                }
                updateIndex();
            }

        });

        //тип колодки
        mainData.getTextField(6).addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                formIndexLast.setCharAt(9, mainData.getCode(6).charAt(0));
                updateIndex();
            }
        });

    }

    private void updateIndex() {
        String id = (String) sql.getObj("select TO_CHAR(NVL(max(TO_NUMBER(ID)),0)+1, '000') from LAST_HEAD");
        formIndexLast.setCharAt(2, id.charAt(1));
        formIndexLast.setCharAt(3, id.charAt(2));
        formIndexLast.setCharAt(4, id.charAt(3));
        mainData.getTextField(0).setText(formIndexLast.toString());
    }

    private void fillFields(String model) {
//        DefaultTableModel tmpModel = new DefaultTableModel();
//        sql.tableFill("select TYPE_LAST,FASON_LAST,CONSTRUCTION_SOLE_NODE,FASON_SOLE,FASON_HEEL from LAST_REQUEST WHERE MODEL='" + model + "'", tmpModel);
//        mainData.getTextField(4).setText((String) tmpModel.getValueAt(0, 0));
//        mainData.getTextField(6).setText((String) tmpModel.getValueAt(0, 1));
//        infoData.getTextField(0).setText((String) tmpModel.getValueAt(0, 2));
//        infoData.getTextField(1).setText((String) tmpModel.getValueAt(0, 3));
//        infoData.getTextField(3).setText((String) tmpModel.getValueAt(0, 4));
    }

    public void saveToDB(String status) {

        String[] mainQuery = mainData.getValueForInsert(new String[]{"ID", "DATE_CREATE"}, true);

        String query;

        if (isEditing) {
            query = "update GUI_SAP.LAST_HEAD set " + mainData.getQuetyForUpdate(new String[]{"ID", "DATE_CREATE"}) + " where ID=" + idLast + "";
        } else {
            if (testIndexInDB(mainData.getText(0))) {
                query = "insert into GUI_SAP.LAST_HEAD (" + mainQuery[0] + ") values"
                        + " (" + mainQuery[1] + ")";
            } else {
                JOptionPane.showMessageDialog(this, "Колодка с указанным индексом уже есть в базе", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                return;
            }

        }

        sql.SendQuery(query);
        LifeLineUtils.InsertLifeLine(LifeLineUtils.LAST_LIFE_LINE, mainData.getText(0), status);

        if (model != null) {
            sql.SendQuery("update GUI_SAP.MKZ_LIST_HEAD set CODE_LAST='" + mainData.getText(0) + "',  FASON_LAST='" + mainData.getText(6) + "',  status = 'LC' where MODEL = '" + model + "'");
            sql.SendQuery("update GUI_SAP.LAST_REQUEST set status = 'CL' where MODEL = '" + model + "'");
            LifeLineUtils.UpdateLifeLine(LifeLineUtils.MKZ_LIFE_LINE, model, "LC");
        }

        //обновление главной страницы
        ParrentForm.FillMainTable();
        ParrentForm.FillRequestTable();
        closeWindow();
    }

    //функция для проверки на дубликат идентификатора колодки
    public boolean testIndexInDB(String index) {
        DefaultTableModel tempModel = new DefaultTableModel();
        sql.tableFill("select * from LAST_HEAD where INDEX_LAST='" + index + "'", tempModel);
        return tempModel.getRowCount() == 0;
    }

}
