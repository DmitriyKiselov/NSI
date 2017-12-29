/*
 * Класс окна добавления колодок
 */
package my.guisap.forms.Last;

import my.guisap.utils.LogClass;
import java.awt.Color;
import java.awt.Insets;
import my.guisap.componenst.EmptyForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import my.guisap.FormRegister;
import my.guisap.componenst.DataPanel;
import my.guisap.forms.SimpleTableForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.CreateFormUtils;
import my.guisap.utils.LifeLineUtils;
import my.guisap.utils.TextUtils;

/**
 *
 * @author KiselevDA
 */
public class LastEdit extends EmptyForm {

    DataPanel mainData = new DataPanel("LAST_HEAD", "ID", 1);
    DataPanel infoData = new DataPanel("LAST_INFO", "INDEX_LAST", 1);
    DataPanel extraInfoData = new DataPanel("LAST_INFO_EXTRA", "INDEX_LAST", 1);

    String classFlag;
    LastMain ParrentForm;
    String indexLast;
    String model;
    //генерация нового индекса (если не передан через конструктор)
    String idLast = "SEQ_LAST_HEAD.NEXTVAL";

    StringBuilder formIndexLast = new StringBuilder("00000/00-0");
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

        String index[][] = {
            {"Индекс колодки: ", "false"}
        };

        String indexMassElem[][] = {
            {"Величина ППЧ", "true"},
            {"Год модельного ряда", "true"}
        };

        //названия полей без подгрузки из SAP
        String headMasElem[][] = {
            {"Полнота колодки мм", "true"},
            {"Длина следа", "true"}
        };

        String infoMasElem[][] = {
            {"Толщина подошвы в пучках", "true"},
            {"Геленок", "true"}
        };

        mainData.addFields(index, 0, 1, new Insets(10, 10, 10, 10));
        mainData.add(Box.createHorizontalGlue());
        mainData.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'LAST' and MAIN_CLASS = 'LAST' and PRIORITY<'2'" + SqlOperations.GROUP_BY, true, 0);
        mainData.addFields(indexMassElem, 0, 1, CreateFormUtils.DEFAULT_INSETS);
        mainData.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'LAST' and MAIN_CLASS = 'LAST' and PRIORITY>='2'" + SqlOperations.GROUP_BY, true, 0);
        mainData.addFields(headMasElem, 0, 1, CreateFormUtils.DEFAULT_INSETS);
        mainData.setBorder(CreateFormUtils.defaultBorder);

        infoData.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'LAST_INFO' and MAIN_CLASS = 'LAST_INFO' and PRIORITY<'5'" + SqlOperations.GROUP_BY, true, 0);
        infoData.addFields(infoMasElem, 0, 1, CreateFormUtils.DEFAULT_INSETS);
        infoData.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'LAST_INFO' and MAIN_CLASS = 'LAST_INFO' and PRIORITY>='5'" + SqlOperations.GROUP_BY, true, 0);
        infoData.setBorder(CreateFormUtils.defaultBorder);

        extraInfoData.addFieldsWithCatalog(SqlOperations.DATA_SELECTION + "'LAST_INFO_EXTRA' and MAIN_CLASS = 'LAST_INFO_EXTRA'" + SqlOperations.GROUP_BY, true, 0);
        extraInfoData.setBorder(CreateFormUtils.defaultBorder);

        //задается размер текстового поля "Код колодки"
        //получение полей с данными из SAP
        //создание кнопки завершить этап
        extraInfoData.setVisible(false);

        mainData.getTextField(6).setEnabled(true);

        pnlAttElem.add(mainData);
        pnlAttElem.add(infoData);
        pnlAttElem.add(extraInfoData);

        pack();
        setCenter();
    }

    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        if (!checkIndex()) {
            saveToDB("FL");
        } else {
            JOptionPane.showMessageDialog(this, "Заполните поле 'Код колодки'", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void fillFields() {
        mainData.fillFields(SqlOperations.LAST_LIST_TO_EDIT + " where INDEX_LAST='" + indexLast + "'", 0);
        infoData.fillFields(SqlOperations.LAST_LIST_INFO + " where INDEX_LAST='" + indexLast + "'", 1);
        extraInfoData.fillFields(SqlOperations.LAST_LIST_INFO_EXTRA + " where INDEX_LAST='" + indexLast + "'", 1);
        mainData.blockFields(new int[]{0, 1, 2, 3, 4, 6});
    }

    private void formIndex() {

        mainData.getTextField(0).setText(formIndexLast.toString());

        //род обуви
        mainData.getTextField(1).addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                switch (mainData.getTextField(1).getText()) {
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
        mainData.getTextField(2).addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                if (TextUtils.checkStringToNumber(mainData.getTextField(2).getText(), 10, 95, 5)) {
                    formIndexLast.setCharAt(1, mainData.getTextField(2).getText().charAt(0));
                } else {
                    formIndexLast.setCharAt(1, '0');
                }
                updateIndex();
            }
        });

        //год разработки
        mainData.getTextField(3).addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                if (TextUtils.checkStringToNumber(mainData.getTextField(3).getText(), 1900, 2100)) {
                    formIndexLast.setCharAt(6, mainData.getTextField(3).getText().charAt(2));
                    formIndexLast.setCharAt(7, mainData.getTextField(3).getText().charAt(3));
                } else {
                    formIndexLast.setCharAt(6, '0');
                    formIndexLast.setCharAt(7, '0');
                }
                updateIndex();
            }
        });

        //тип колодки
        mainData.getTextField(4).addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                formIndexLast.setCharAt(9, mainData.getAttRow(4).getCode().charAt(0));
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
        DefaultTableModel tmpModel = new DefaultTableModel();
        sql.tableFill("select TYPE_LAST,FASON_LAST,CONSTRUCTION_SOLE_NODE,FASON_SOLE,FASON_HEEL from LAST_REQUEST WHERE MODEL='" + model + "'", tmpModel);
        mainData.getTextField(4).setText((String) tmpModel.getValueAt(0, 0));
        mainData.getTextField(6).setText((String) tmpModel.getValueAt(0, 1));
        infoData.getTextField(0).setText((String) tmpModel.getValueAt(0, 2));
        infoData.getTextField(1).setText((String) tmpModel.getValueAt(0, 3));
        infoData.getTextField(3).setText((String) tmpModel.getValueAt(0, 4));
    }

    public void saveToDB(String status) {

        String[] mainQuery = mainData.getValueForInsert(new String[]{"ID", "DATE_CREATE"}, true);
        String[] infoQuery = infoData.getValueForInsert(new String[]{"INDEX_LAST"}, false);
        String[] extraInfoQuery = extraInfoData.getValueForInsert(new String[]{"INDEX_LAST"}, false);

        String query;
        String queryInfo;
        String queryInfoExtra = "";

        if (isEditing) {
            query = "update GUI_SAP.LAST_HEAD set " + mainData.getQuetyForUpdate(new String[]{"ID", "DATE_CREATE"}) + " where ID=" + idLast + "";
            queryInfo = "update GUI_SAP.LAST_INFO set " + infoData.getQuetyForUpdate(new String[]{"INDEX_LAST"}) + " where INDEX_LAST='" + mainData.getText(0) + "'";
            queryInfoExtra = "update GUI_SAP.LAST_INFO_EXTRA set " + extraInfoData.getQuetyForUpdate(new String[]{"INDEX_LAST"}) + " where INDEX_LAST='" + mainData.getText(0) + "'";
        } else {
            if (testIndexInDB(mainData.getText(0))) {
                query = "insert into GUI_SAP.LAST_HEAD (" + mainQuery[0] + ") values"
                        + " (" + mainQuery[1] + ")";
                queryInfo = "insert into GUI_SAP.LAST_INFO values ('" + mainData.getText(0) + "'," + infoQuery[1] + ")";
                queryInfoExtra = "insert into GUI_SAP.LAST_INFO_EXTRA values('" + mainData.getText(0) + "'," + extraInfoQuery[1] + ")";
            } else {
                JOptionPane.showMessageDialog(this, "Колодка с указанным индексом уже есть в базе", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                return;
            }

        }

        sql.SendQuery(query);
        sql.SendQuery(queryInfo);
        sql.SendQuery(queryInfoExtra);
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

    private boolean checkIndex() {
        int counter = 0;
        counter = TextUtils.checkTextField(mainData.getTextField(0), counter);
        counter = TextUtils.checkTextField(mainData.getTextField(1), counter);
        counter = TextUtils.checkTextField(mainData.getTextField(4), counter);
        counter = TextUtils.checkTextField(mainData.getTextField(6), counter);

        if (!TextUtils.checkStringToNumber(mainData.getTextField(2).getText(), 0, 95, 5)) {
            mainData.getTextField(2).setBorder(BorderFactory.createLineBorder(Color.RED));
            return true;
        } else {
            mainData.getTextField(2).setBorder(CreateFormUtils.defaultTextFieldBorder);
        }

        if (!TextUtils.checkStringToNumber(mainData.getTextField(3).getText(), 1900, 2100)) {
            mainData.getTextField(3).setBorder(BorderFactory.createLineBorder(Color.RED));
            return true;
        } else {
            mainData.getTextField(3).setBorder(CreateFormUtils.defaultTextFieldBorder);
        }

        return counter > 0;
    }

}
