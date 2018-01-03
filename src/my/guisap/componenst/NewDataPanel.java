package my.guisap.componenst;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import my.guisap.componenst.fields.CatalogField;
import my.guisap.componenst.fields.EntityForField;
import my.guisap.componenst.fields.FreeWriteField;
import my.guisap.componenst.fields.SpinnerField;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.CreateFormUtils;
import my.guisap.utils.TextUtils;

/**
 *
 * @author Dima
 */
public class NewDataPanel extends JPanel {

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

    int columns;

    ArrayList<JPanel> blocks = new ArrayList<>();
    ArrayList<EntityForField> listFields = new ArrayList<>();

    boolean needToCheck = false;
    ArrayList<String> notCheckFields = new ArrayList<>();

    public NewDataPanel(String nameTable, String nameFieldID, String nameForm, int columns) {
        this.nameTable = nameTable;
        this.nameFieldID = nameFieldID;
        this.columns = columns;
        this.generateID = "(select NVL(max(TO_NUMBER(" + nameFieldID + ")),0)+1 from " + nameTable + "),";
        createForm(nameForm);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void createForm(String nameForm) {
        DefaultTableModel fields = new DefaultTableModel();
        sql.tableFill("select * from FORM_CREATOR where NAME_FORM='" + nameForm + "' order by lpad(POSITION, 50)", fields);
        for (int i = 0; i < fields.getRowCount(); i++) {
            switch (fields.getValueAt(i, 2).toString()) {
                case "CatalogField": {
                    listFields.add(new CatalogField((String) fields.getValueAt(i, 1),
                            (String) fields.getValueAt(i, 3),
                            (String) fields.getValueAt(i, 4),
                            (String) fields.getValueAt(i, 5),
                            (String) fields.getValueAt(i, 6),
                            (String) fields.getValueAt(i, 7),
                            (String) fields.getValueAt(i, 8),
                            (String) fields.getValueAt(i, 9),
                            (String) fields.getValueAt(i, 10)));
                }
                break;
                case "FreeWriteField": {
                    listFields.add(new FreeWriteField((String) fields.getValueAt(i, 1),
                            (String) fields.getValueAt(i, 4),
                            (String) fields.getValueAt(i, 5),
                            (String) fields.getValueAt(i, 6),
                            (String) fields.getValueAt(i, 7),
                            (String) fields.getValueAt(i, 8),
                            (String) fields.getValueAt(i, 9),
                            (String) fields.getValueAt(i, 10)));
                }
                break;
                case "SpinnerField": {
                    listFields.add(new SpinnerField((String) fields.getValueAt(i, 1),
                            (String) fields.getValueAt(i, 4),
                            (String) fields.getValueAt(i, 5),
                            (String) fields.getValueAt(i, 6),
                            (String) fields.getValueAt(i, 7),
                            (String) fields.getValueAt(i, 8),
                            (String) fields.getValueAt(i, 9),
                            (String) fields.getValueAt(i, 10)));
                }
                break;
            }
        }

        if (!listFields.isEmpty()) {
            for (int i = 0; i <= listFields.get(listFields.size() - 1).getBlock(); i++) {
                JPanel tmp = new JPanel();
                tmp.setLayout(new BoxLayout(tmp, BoxLayout.Y_AXIS));
                tmp.setBorder(CreateFormUtils.defaultBorder);
                blocks.add(tmp);
            }
            for (EntityForField field : listFields) {
                blocks.get(field.getBlock()).add(field);
            }
            for (JPanel block : blocks) {
                add(block);
            }
        }
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

    public EntityForField getRow(int id) {
        return listFields.get(id);
    }

    public JComponent getTextField(int id) {
        return listFields.get(id).getField();
    }

    public String getText(int id) {
        return listFields.get(id).getText();
    }

    public void setText(int id, String text) {
        listFields.get(id).setText(text);
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

    public void blockFields(int id) {
        blockFields(new int[]{id});
    }

    public void blockFields(int[] id) {
        for (int i : id) {
            listFields.get(i).setEnabledComponent(false);
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
            for (int i = 0; i < listFields.size(); i++) {
                listFields.get(i).setText((String) query.getValueAt(0, startValue++));
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

        for (int i = 0; i < listFields.size(); i++) {
            query.append("'").append(listFields.get(i).getText()).append("',");
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
            query.append(nameColumns.getValueAt(i, 0)).append("='").append(listFields.get(i).getText()).append("' ");
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
            for (int i = 0; i < listFields.size(); i++) {
                if (listFields.get(i) instanceof CatalogField) {
                    JTextField tmpField = (JTextField) listFields.get(i).getField();
                    tmpField.setBorder(CreateFormUtils.defaultTextFieldBorder);
                    if (TextUtils.checkRow(listFields.get(i).getNameCatalog(), tmpField.getText())) {
                        tmpField.setBorder(BorderFactory.createLineBorder(Color.RED));
                        result = false;
                    }
                    if (!TextUtils.isRowExeption(notCheckFields, listFields.get(i).getDiscriptionField()) && (tmpField.getText().equals("") || tmpField.getText().equals(" "))) {
                        tmpField.setBorder(BorderFactory.createLineBorder(Color.RED));
                        result = false;
                    }
                } else {
                    if (TextUtils.checkTextField((JTextField) listFields.get(i).getField(), listFields.get(i).getDiscriptionField(), notCheckFields)) {
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
        return sql.checkInfo("select * from " + nameTable + " where " + nameFieldID + "='" + listFields.get(numberFieldId).getText() + "'");
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