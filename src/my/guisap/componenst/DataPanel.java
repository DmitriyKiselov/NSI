package my.guisap.componenst;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import my.guisap.attributeRow;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.CreateFormUtils;
import static my.guisap.utils.CreateFormUtils.CreateElem;
import my.guisap.utils.TextUtils;

/**
 *
 * @author Dima
 */
public class DataPanel extends JPanel {

    final int nameFieldInJPanel = 0;
    final int textFieldInJPanel = 3;
    final int btnInJPanel = 4;

    SqlOperations sql = new SqlOperations();

    /**
     * название таблицы
     */
    public String nameTable = "";
    public String generateID = "";
    String nameFieldID = "";

    ArrayList<JTextField> fieldsData = new ArrayList<>();
    ArrayList<JPanel> rowData = new ArrayList<>();

    boolean needToCheck = false;
    ArrayList<String> notCheckFields = new ArrayList<>();

    public DataPanel(String nameTable, String nameFieldID, int columns) {
        this.nameTable = nameTable;
        this.nameFieldID = nameFieldID;
        this.generateID = "(select NVL(max(TO_NUMBER(" + nameFieldID + ")),0)+1 from " + nameTable + "),";
        setLayout(new GridLayout(0, columns));
    }

    /**
     * Установка значения needToCheck, вызов без listSkipFields
     *
     * @param needToCheck
     */
    public void setCheckFields(boolean needToCheck) {
        setCheckFields(needToCheck, null);
    }

    /**
     * Установка значения needToCheck, true поля перед сохранением проверяются
     * (наличие в справочнике, пустые значения), false без проверки
     *
     * @param needToCheck
     * @param listSkipFields список полей которые пропускаются
     */
    public void setCheckFields(boolean needToCheck, ArrayList<String> listSkipFields) {
        this.needToCheck = needToCheck;
        if (listSkipFields != null) {
            notCheckFields = listSkipFields;
        }
    }

    public JTextField getTextField(int id) {
        return fieldsData.get(id);
    }

    public String getText(int id) {
        return fieldsData.get(id).getText();
    }

    public String[] getValueForInsert(String[] exceptionFields, boolean addID) {
        DefaultTableModel fields = getFields(exceptionFields);
        String[] result = new String[2];

        StringBuilder query = new StringBuilder();
        if (addID) {
            query.append(nameFieldID).append(",");
        }

        // перечисление названий полей в формате поле1,поле2,поле3
        for (int i = 0; i < fields.getRowCount() - 1; i++) {
            query.append(fields.getValueAt(i, 0)).append(",");
        }
        query.append(fields.getValueAt(fields.getRowCount() - 1, 0));
        result[0] = query.toString();

        query = new StringBuilder();
        if (addID) {
            query.append(generateID);
        }

        for (int i = 0; i < fields.getRowCount() - 1; i++) {
            query.append("'").append(getText(i)).append("',");
        }

        query.append("'").append(getText(fields.getRowCount() - 1)).append("'");
        result[1] = query.toString();

        return result;
    }

    public String getQuetyForUpdate(String[] exceptionFields) {
        DefaultTableModel fields = getFields(exceptionFields);
        StringBuilder query = new StringBuilder();

        // перечисление названий полей в формате поле1,поле2,поле3
        for (int i = 0; i < fields.getRowCount() - 1; i++) {
            query.append(fields.getValueAt(i, 0)).append("='").append(getText(i)).append("',");
        }
        query.append(fields.getValueAt(fields.getRowCount() - 1, 0)).append("='").append(getText(fields.getRowCount() - 1)).append("'");

        return query.toString();
    }

    public attributeRow getAttRow(int id) {
        if (rowData.get(id) instanceof attributeRow) {
            return (attributeRow) rowData.get(id);
        } else {
            return null;
        }
    }

    /**
     * Добавление полей состоящих из jLabel(название поля) jTextField(текстовое
     * поле вывода и хранения информации) jButton (кнопка для открытия
     * справочника); Поля берутся из объединения таблиц CLASS_PR и BLOCK_PR
     *
     * @param query запрос для создания списка полей
     * @param isEnabled true доступны, false все поля заблокированы
     * @param FirstEelem номер первого элемента dataModel
     */
    public void addFieldsWithCatalog(String query, boolean isEnabled, int FirstEelem) {
        DefaultTableModel dataModel = new DefaultTableModel();
        sql.tableFill(query, dataModel);
        addFieldsWithCatalog(dataModel, isEnabled, FirstEelem);
    }

    /**
     * Добавление полей состоящих из jLabel(название поля) jTextField(текстовое
     * поле вывода и хранения информации) jButton (кнопка для открытия
     * справочника); Поля берутся из объединения таблиц CLASS_PR и BLOCK_PR
     *
     * @param dataModel данные для создания attributeRow
     * @param isEnabled true доступны, false все поля заблокированы
     * @param FirstEelem номер первого элемента dataModel
     */
    public void addFieldsWithCatalog(DefaultTableModel dataModel, boolean isEnabled, int FirstEelem) {
        if (dataModel.getRowCount() != 0) {
            attributeRow atRow;
            for (int Count = FirstEelem; Count < dataModel.getRowCount(); Count++) {
                atRow = new attributeRow(
                        dataModel.getValueAt(Count, 0).toString(),
                        dataModel.getValueAt(Count, 1).toString(),
                        dataModel.getValueAt(Count, 2).toString(),
                        dataModel.getValueAt(Count, 3).toString(),
                        null, isEnabled);
                fieldsData.add(atRow.txt);
                rowData.add(atRow);
                add(atRow);
            }
        }
    }

    public void addFields(String[][] massElem, int startElem, int countInRow, Insets insets) {
        JPanel tmpPanel;
        for (String[] elem : massElem) {
            tmpPanel = CreateElem(elem[0], null, elem[1]);
            add(tmpPanel,
                    new GridBagConstraints(startElem % countInRow, (int) startElem / countInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, insets, 0, 0));
            startElem++;

            fieldsData.add((JTextField) (tmpPanel.getComponent(textFieldInJPanel)));
            rowData.add(tmpPanel);
        }
    }

    public void blockFields(int id) {
        blockFields(new int[]{id});
    }

    public void blockFields(int[] id) {
        for (int i : id) {
            if (rowData.get(i) instanceof attributeRow) {
                rowData.get(i).getComponent(textFieldInJPanel).setEnabled(false);
                rowData.get(i).getComponent(btnInJPanel).setEnabled(false);
            } else {
                rowData.get(i).getComponent(textFieldInJPanel).setEnabled(false);
            }
        }
    }

    public boolean fillFields(String query, int startValue) {
        DefaultTableModel tmp = new DefaultTableModel();
        sql.tableFill(query, tmp);
        return fillFields(tmp, startValue);
    }

    public boolean fillFields(DefaultTableModel query, int startValue) {
        if (query.getRowCount() == 0) {
            return false;
        } else {
            for (int i = 0; i < fieldsData.size(); i++) {
                fieldsData.get(i).setText((String) query.getValueAt(0, startValue++));
            }
            return true;
        }
    }

    public boolean saveToDB() {
        return saveToDB("", null);
    }

    /**
     * Сохранение в базу данных, при учете что поля в таблице и на форме в
     * идентичном порядки
     *
     * @param id ид для сохранения, находится на 1 позиции
     * @param extraFields дополнительные поля для вставки (в конце запроса)
     * @return
     */
    public boolean saveToDB(String id, String[] extraFields) {

        if (checkFields() == false) {
            return false;
        }

        StringBuilder query = new StringBuilder("insert into " + nameTable + " values (");

        // добавление к запросу id
        if (!id.equals("")) {
            query.append(id);
        }

        for (int i = 0; i < fieldsData.size(); i++) {
            query.append("'").append(fieldsData.get(i).getText()).append("',");
        }

        if (extraFields != null) {
            for (String str : extraFields) {
                query.append("'").append(str).append("',");
            }
        }

        query.deleteCharAt(query.length() - 1);
        query.append(")");

        sql.SendQuery(query.toString());
        return true;
    }

    /**
     * Сохранение в базу данных
     *
     * @param addID true вставить сгенерированный ID (следующий), false при
     * варианте когда есть поле ID, которое вводит пользователь, либо оно
     * генерируется особым образом
     * @param exceptionFields исключаемые поля
     * @param extraFields значение дополнительных полей (например статус, поле
     * которое отсутствует для заполнения, но присутствует в базе)
     * @return
     */
    public boolean saveToDB(boolean addID, String[] exceptionFields, String[] extraFields) {

        if (checkFields() == false) {
            return false;
        }

        String[] value = getValueForInsert(exceptionFields, addID);

        StringBuilder query = new StringBuilder("insert into " + nameTable + "(");
        query.append(value[0]).append(") values (").append(value[1]);

        if (extraFields != null && extraFields.length > 0) {
            for (String str : extraFields) {
                query.append(",").append(str);
            }
        }

        query.append(")");

        sql.SendQuery(query.toString());
        return true;
    }

    public boolean updateDB(String id) {

        if (checkFields() == false) {
            return false;
        }

        DefaultTableModel nameColumns = new DefaultTableModel();
        StringBuilder query = new StringBuilder("update " + nameTable + " set");
        sql.tableFill("select column_name \n"
                + "  from user_tab_columns \n"
                + "  where table_name = '" + nameTable + "'", nameColumns);
        for (int i = 0; i < nameColumns.getRowCount(); i++) {
            query.append(nameColumns.getValueAt(i, 0)).append("='").append(fieldsData.get(i).getText()).append("' ");
        }
        query.append("where ").append(id);
        sql.SendQuery(query.toString());
        return true;
    }

    /**
     * Проверка полей на наличие в справочниках и заполнение
     *
     * @return true положительный результат, false отрицательный
     */
    public boolean checkFields() {
        boolean result = true;
        if (needToCheck) {
            attributeRow tmpRow;
            for (int i = 0; i < rowData.size(); i++) {
                if (rowData.get(i) instanceof attributeRow) {
                    tmpRow = (attributeRow) rowData.get(i);
                    tmpRow.txt.setBorder(CreateFormUtils.defaultTextFieldBorder);
                    if (TextUtils.checkRow(tmpRow.atprt, tmpRow.txt.getText())) {
                        tmpRow.txt.setBorder(BorderFactory.createLineBorder(Color.RED));
                        result = false;
                    }
                    if (!TextUtils.isRowExeption(notCheckFields, tmpRow) && (tmpRow.txt.getText().equals("") || tmpRow.txt.getText().equals(" "))) {
                        tmpRow.txt.setBorder(BorderFactory.createLineBorder(Color.RED));
                        result = false;
                    }
                } else {
                    if (TextUtils.checkTextField((JTextField) rowData.get(i).getComponent(textFieldInJPanel), (JLabel) rowData.get(i).getComponent(nameFieldInJPanel), notCheckFields)) {
                        result = false;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Запрос на наличие индекса в базе
     *
     * @param numberFieldId номер поля с индексом
     * @return true индекс присутствует в базе(отрицательный результат) false
     * отсутствует
     */
    public boolean checkID(int numberFieldId) {
        return sql.checkInfo("select * from " + nameTable + " where " + nameFieldID + "='" + fieldsData.get(numberFieldId).getText() + "'");
    }

    public String generateNewID() {
        return sql.getObj("select NVL(max(TO_NUMBER(" + nameFieldID + ")),0)+1 from " + nameTable).toString();
    }

    public DefaultTableModel getFields(String[] exceptionFields) {
        DefaultTableModel result = new DefaultTableModel();

        StringBuilder exception = new StringBuilder();
        for (String exceptionField : exceptionFields) {
            exception.append(" and column_name not like '").append(exceptionField).append("'");
        }

        sql.tableFill("SELECT column_name"
                + " FROM USER_TAB_COLUMNS"
                + " WHERE table_name = '" + nameTable + "' " + exception.toString(), result);

        return result;
    }
    
    
}
