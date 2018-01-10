/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap;

import my.guisap.componenst.AttributePanel;
import my.guisap.utils.LogClass;
import my.guisap.componenst.EmptyForm;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import my.guisap.sql.ConnectionClass;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author dima
 */
public class attributeRowHeader extends attributeRow {

    public ConnectionClass connect;
    PreparedStatement ps;
    boolean existFlag;
    EmptyForm form;
    SqlOperations sql = new SqlOperations();
    DefaultTableModel historyModel = new DefaultTableModel(0, 0);
    DefaultTableModel attrModel = new DefaultTableModel(0, 0);
    DefaultTableModel dataHeaderModel = new DefaultTableModel(new Object[][]{}, new String[]{});
    public int idKey = 0;
    AttributePanel attPanel;
    String classFlagRH;
    LogClass log;

    public attributeRowHeader(String atinn, String atnam, String atbez, String atprt, AttributePanel attPanel, EmptyForm form, String classFlag) {
        super(atinn, atnam, atbez, atprt, classFlag);
        this.classFlagRH = classFlag;
        this.attPanel = attPanel;
        this.form = form;
        btn.setEnabled(true);
        log = LogClass.getInstance();
    }

    @Override
    public void setText(String code, String name) {
        super.setText(code, name);
        dataHeaderModel = new DefaultTableModel(0, 0);
        existFlag = isExistRecOnSQL();
        setAllAttributes(existFlag);
        setMainHeader(existFlag);
    }

    private boolean isExistRecOnSQL() {
        if (atnam.equals(GuiStaticVariables.FASON_ATNAM)) {
            sql.tableFill(SqlOperations.FASON_HEADER + " where a.CODE = '" + code + "'", dataHeaderModel);
        }
        if (atnam.equals(GuiStaticVariables.MODEL_ATNAM)) {
            sql.tableFill(SqlOperations.MODEL_HEADER + " where a.CODE = '" + code + "'", dataHeaderModel);
        }
        if (atnam.equals(GuiStaticVariables.ARTICLE_ATNAM)) {
            sql.tableFill(SqlOperations.ARTICLE_HEADER + " where a.CODE = '" + code + "'", dataHeaderModel);
        }

        if (dataHeaderModel.getRowCount() == 0) {
            idKey = 0;

            EmptyForm ef = (EmptyForm) this.form;
            ef.saveLblUpdating();

            historyModel = new DefaultTableModel(0, 0);

            if (atnam.equals(GuiStaticVariables.FASON_ATNAM)) {
                historyCheck("select * FROM [SAP].[dbo].[OZM_HISTORY_FERT] where CODE_LAST_001 = ? order by MAKTX", txt.getText());

                if (historyModel.getRowCount() != 0 && !txt.getText().equals("")) {
                    int element = sql.dataUpdate(SqlOperations.BLOCK_FASON_SAVE + "('" + txt.getText() + "', '" + txt.getText() + "','" + classFlagRH + "') " + SqlOperations.BLOCK_SAVE_SCOPE);
                    idKey = historySaving(element, "FASON", SqlOperations.BLOCK_FASON_PR_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR);

                    dataHeaderModel = new DefaultTableModel(0, 0);
                    sql.tableFill(SqlOperations.FASON_HEADER + " where a.CODE = '" + txt.getText() + "'", dataHeaderModel);
                    log.logWriting("импортировал признаки по фасону " + txt.getText() + " из таблиц HISTORY базы SAP");
                    return true;
                }
            }

            if (atnam.equals(GuiStaticVariables.MODEL_ATNAM)) {
                historyCheck("select * FROM [SAP].[dbo].[OZM_HISTORY_FERT] where MODEL_001 = ? order by MAKTX", txt.getText());

                if (historyModel.getRowCount() != 0 && !txt.getText().equals("")) {
                    String fason = historyStrFill("select distinct CODE_LAST_001 FROM [SAP].[dbo].[OZM_HISTORY_FERT] where MODEL_001 = ?", txt.getText());
                    int fasonIDKey = 0, element;
                    if (sql.strFill("select IDKEY from BLOCK_FASON where NAME = '" + fason + "'").equals("")) {
                        element = sql.dataUpdate(SqlOperations.BLOCK_FASON_SAVE + "('" + fason + "', '" + fason + "','" + classFlagRH + "') " + SqlOperations.BLOCK_SAVE_SCOPE);
                        fasonIDKey = historySaving(element, "FASON", SqlOperations.BLOCK_FASON_PR_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR);
                        log.logWriting("импортировал признаки по фасону " + txt.getText() + " из таблиц HISTORY базы SAP");
                    } else {
                        fasonIDKey = Integer.parseInt(sql.strFill("select IDKEY from BLOCK_FASON where NAME = '" + fason + "'"));
                    }

                    if (!classFlagRH.equals(GuiStaticVariables.SALE_SHOES)) {
                        element = sql.dataUpdate(SqlOperations.BLOCK_MODEL_SAVE + "('" + txt.getText() + "', '" + txt.getText() + "'," + fasonIDKey + ",'" + classFlagRH + "') " + SqlOperations.BLOCK_SAVE_SCOPE);
                    } else {
                        element = sql.dataUpdate(SqlOperations.BLOCK_MODEL_SAVE + "('" + txt.getText() + "', '" + txt.getText() + "'," + "null" + ",'" + classFlag + "') " + SqlOperations.BLOCK_SAVE_SCOPE);
                    }
                    idKey = historySaving(element, "MOD", SqlOperations.BLOCK_MODEL_PR_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR);
                    historyCheck("select * FROM [SAP].[dbo].[OZM_HISTORY_5F] where MODEL_001 = ?", txt.getText());
                    if (historyModel.getRowCount() != 0) {
                        historySaving(idKey, "MOD_TEXN", SqlOperations.MODEL_TECHN_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR_TECHN);
                        log.logWriting("импортировал технологические признаки по модели " + txt.getText() + " из таблиц HISTORY базы SAP");
                    }

                    dataHeaderModel = new DefaultTableModel(0, 0);
                    sql.tableFill(SqlOperations.MODEL_HEADER + " where a.CODE = '" + txt.getText() + "'", dataHeaderModel);

//                mf.sizeLblUpdate();
                    log.logWriting("импортировал признаки по модели " + txt.getText() + " из таблиц HISTORY базы SAP");

                    return true;
                }

//                mf.sizeLblUpdate();
            }

            if (atnam.equals(GuiStaticVariables.ARTICLE_ATNAM)) {
                historyCheck("select * FROM [SAP].[dbo].[OZM_HISTORY_FERT] where ART_001 = ? order by MAKTX", txt.getText());

                if (historyModel.getRowCount() != 0 && !txt.getText().equals("")) {
                    String fason = historyStrFill("select distinct CODE_LAST_001 FROM [SAP].[dbo].[OZM_HISTORY_FERT] where ART_001 = ?", txt.getText());
                    String model = historyStrFill("select distinct MODEL_001 FROM [SAP].[dbo].[OZM_HISTORY_FERT] where ART_001 = ?", txt.getText());

                    int fasonIDKey, modelIDKey, element;
                    if (sql.strFill("select IDKEY from BLOCK_FASON where NAME = '" + fason + "'").equals("")) {
                        element = sql.dataUpdate(SqlOperations.BLOCK_FASON_SAVE + "('" + fason + "', '" + fason + "','" + classFlagRH + "') " + SqlOperations.BLOCK_SAVE_SCOPE);
                        fasonIDKey = historySaving(element, "FASON", SqlOperations.BLOCK_FASON_PR_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR);
                        log.logWriting("импортировал признаки по фасону " + txt.getText() + " из таблиц HISTORY базы SAP");
                    } else {
                        fasonIDKey = Integer.parseInt(sql.strFill("select IDKEY from BLOCK_FASON where NAME = '" + fason + "'"));
                    }

                    if (sql.strFill("select IDKEY from BLOCK_MODEL where NAME = '" + model + "'").equals("")) {
                        element = sql.dataUpdate(SqlOperations.BLOCK_MODEL_SAVE + "('" + model + "', '" + model + "','" + fasonIDKey + "', '" + classFlagRH + "') " + SqlOperations.BLOCK_SAVE_SCOPE);
                        modelIDKey = historySaving(element, "MOD", SqlOperations.BLOCK_MODEL_PR_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR);
                        log.logWriting("импортировал признаки по модели " + txt.getText() + " из таблиц HISTORY базы SAP");
                    } else {
                        modelIDKey = Integer.parseInt(sql.strFill("select IDKEY from BLOCK_MODEL where NAME = '" + model + "'"));
                    }

                    element = sql.dataUpdate(SqlOperations.BLOCK_ARTICLE_SAVE + "('" + txt.getText() + "', '" + txt.getText() + "'," + modelIDKey + ",'" + classFlagRH + "') " + SqlOperations.BLOCK_SAVE_SCOPE);

                    idKey = historySaving(element, "ART", SqlOperations.BLOCK_ARTICLE_PR_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR);

                    historyCheck("select * FROM [SAP].[dbo].[OZM_HISTORY_5F] where MODEL_001 = ?", sql.strFill("select distinct a.NAME from BLOCK_MODEL a inner join BLOCK_ARTICLE b on a.IDKEY = b.RELIDKEY where b.NAME = '" + txt.getText() + "'"));
                    if (historyModel.getRowCount() != 0) {
                        historySaving(modelIDKey, "MOD_TEXN", SqlOperations.MODEL_TECHN_SAVE, SqlOperations.SELECT_HISTORY_BLOCK_PR_TECHN);
                        log.logWriting("импортировал технологические признаки по модели " + txt.getText() + " из таблиц HISTORY базы SAP");
                    }

                    dataHeaderModel = new DefaultTableModel(0, 0);
                    sql.tableFill(SqlOperations.ARTICLE_HEADER + " where a.CODE = '" + txt.getText() + "'", dataHeaderModel);
                    log.logWriting("импортировал признаки по артикулу " + txt.getText() + " из таблиц HISTORY базы SAP");

                    return true;
                }
            }

            return false;
        } else {
            if (dataHeaderModel.getValueAt(0, 4).toString().equals(classFlagRH)) {
                idKey = Integer.parseInt(dataHeaderModel.getValueAt(0, 3).toString());

                EmptyForm ef = (EmptyForm) this.form;
                ef.saveLblUpdating();

                if (atnam.equals(GuiStaticVariables.MODEL_ATNAM)) {
                }

                return true;
            } else {
                idKey = 0;
                setText("", "");
                JOptionPane.showMessageDialog(this, "Выбрана существующая запись другого класса!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        // вызывать SQL запрос (для Фасона, или Модели, или Артикул - header) и возвращает есть ли уже запись на сервере для данного header
    }

    private void setMainHeader(boolean exist) {
        String str = "";
        if (atnam.equals(GuiStaticVariables.FASON_ATNAM)) {
            return;
        }
        if (atnam.equals(GuiStaticVariables.TECHN_ATNAM)) {
            return;
        }
        if (atnam.equals(GuiStaticVariables.MODEL_ATNAM) && classFlagRH.equals(GuiStaticVariables.SALE_SHOES)) {
            return;
        }
        if (atnam.equals(GuiStaticVariables.MODEL_ATNAM)) {
            str = sql.strFill(SqlOperations.MODEL_FASON_NAME + idKey + ")");
            try {
                form.atRowMainHed.idKey = Integer.parseInt(sql.strFill(SqlOperations.MODEL_FASON_KEY + idKey + ")"));
            } catch (NumberFormatException e) {
            }
        }
        if (atnam.equals(GuiStaticVariables.ARTICLE_ATNAM)) {
            str = sql.strFill(SqlOperations.ARTICLE_MODEL_NAME + idKey + ")");
            try {
                form.atRowMainHed.idKey = Integer.parseInt(sql.strFill(SqlOperations.ARTICLE_MODEL_KEY + idKey + ")"));
            } catch (NumberFormatException e) {
            }
        }
        if (exist) {
            form.atRowMainHed.clear();

            form.atRowMainHed.setText(str, str);
        } else {
            // form.atRowMainHed.clear();
        }
    }

    private void setAllAttributes(boolean exist) {
        for (attributeRow aR : attPanel.rowList) {
            if (exist) {
                aR.clear();
                attPanel.setTextAll(dataHeaderModel);
                if (atnam.equals(GuiStaticVariables.MODEL_ATNAM)) {
                    String str = sql.strFill(SqlOperations.MODEL_EDITION_YEAR_SELECTING + idKey);
                    form.dataTxt.setText(str);
                }
            } else {
                aR.clear();
            }
            aR.btn.setEnabled(true);
        }
    }

    private void historyCheck(String query, String param) {
        try {
            ps = sql.preparingTableFill(query);
            if (ps != null) {
                ps.setString(1, param);
                historyModel = new DefaultTableModel(0, 0);
                sql.psTableFill(historyModel, ps);
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private String historyStrFill(String query, String param) {
        String str = "";
        try {
            ps = sql.preparingTableFill(query);
            if (ps != null) {
                ps.setString(1, param);
                str = sql.psStrFill(ps);
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return str;
    }

    private int historySaving(int element, String attParam_1, String saveQuery, String attrQuery) {
        attrModel = new DefaultTableModel(0, 0);
        try {
            ps = sql.preparingTableFill(attrQuery);
            if (ps != null) {
                ps.setString(1, attParam_1);
                ps.setString(2, classFlagRH);
                sql.psTableFill(attrModel, ps);
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        boolean yearFlag = true;
        boolean sizeFlag = true;

        for (int i = 0; i < attrModel.getRowCount(); i++) {
            for (int j = historyModel.getColumnCount() - 1; j >= 0; j--) {
                if (historyModel.getColumnName(j).equals("INNOV_YEAR") && yearFlag && saveQuery.equals(SqlOperations.BLOCK_MODEL_PR_SAVE)) {
                    if (historyModel.getValueAt(0, j) != null) {
                        sql.dataUpdate(saveQuery + "('" + element + "','" + "EDITION_YEAR" + "','" + "Год выпуска" + "','" + historyModel.getValueAt(0, j).toString().substring(historyModel.getValueAt(0, j).toString().length() - 4, historyModel.getValueAt(0, j).toString().length()) + "')");
                    } else {
                        if (txt.getText().substring(0, 1).equals("1") || txt.getText().substring(0, 1).equals("2") || txt.getText().substring(0, 1).equals("3") || txt.getText().substring(0, 1).equals("4") || txt.getText().substring(0, 1).equals("0")) {
                            sql.dataUpdate(saveQuery + "('" + element + "','" + "EDITION_YEAR" + "','" + "Год выпуска" + "','" + "201" + txt.getText().substring(0, 1) + "')");
                        } else {
                            sql.dataUpdate(saveQuery + "('" + element + "','" + "EDITION_YEAR" + "','" + "Год выпуска" + "','" + "200" + txt.getText().substring(0, 1) + "')");
                        }
                    }
                    yearFlag = false;
                }
                if (historyModel.getColumnName(j).equals("MAKTX") && sizeFlag && saveQuery.equals(SqlOperations.BLOCK_MODEL_PR_SAVE)) {
                    historyModel.setValueAt(historyModel.getValueAt(0, j).toString().replaceAll(" ", ""), 0, j);
                    historyModel.setValueAt(historyModel.getValueAt(historyModel.getRowCount() - 1, j).toString().replaceAll(" ", ""), historyModel.getRowCount() - 1, j);
                    if (historyModel.getValueAt(0, j).toString().substring(historyModel.getValueAt(0, j).toString().length() - 2, historyModel.getValueAt(0, j).toString().length()).contains(".")) {
                        for (int m = (int) Double.parseDouble(historyModel.getValueAt(0, j).toString().substring(historyModel.getValueAt(0, j).toString().length() - 4, historyModel.getValueAt(0, j).toString().length())); m < (int) Double.parseDouble(historyModel.getValueAt(historyModel.getRowCount() - 1, j).toString().substring(historyModel.getValueAt(historyModel.getRowCount() - 1, j).toString().length() - 4, historyModel.getValueAt(historyModel.getRowCount() - 1, j).toString().length())) + 1; m++) {
                            sql.dataUpdate(SqlOperations.SIZE_MODEL_SAVE + "('" + element + "','" + m + ".0','" + m + ".0')");
                        }
                    } else {
                        for (int m = (int) Double.parseDouble(historyModel.getValueAt(0, j).toString().substring(historyModel.getValueAt(0, j).toString().length() - 2, historyModel.getValueAt(0, j).toString().length())); m < (int) Double.parseDouble(historyModel.getValueAt(historyModel.getRowCount() - 1, j).toString().substring(historyModel.getValueAt(historyModel.getRowCount() - 1, j).toString().length() - 2, historyModel.getValueAt(historyModel.getRowCount() - 1, j).toString().length())) + 1; m++) {
                            sql.dataUpdate(SqlOperations.SIZE_MODEL_SAVE + "('" + element + "','" + m + ".0','" + m + ".0')");
                        }
                    }
                    sizeFlag = false;
                }
                if (historyModel.getColumnName(j).length() >= 7) {
                    if (attrModel.getValueAt(i, 1).toString().equals(historyModel.getColumnName(j).substring(0, historyModel.getColumnName(j).length() - 4))) {
                        if (historyModel.getValueAt(0, j) != null) {
                            sql.dataUpdate(saveQuery + "('" + element + "','" + attrModel.getValueAt(i, 1) + "','" + attrModel.getValueAt(i, 0) + "','" + historyModel.getValueAt(0, j) + "')");
                        } else {
                            sql.dataUpdate(saveQuery + "('" + element + "','" + attrModel.getValueAt(i, 1) + "','" + attrModel.getValueAt(i, 0) + "','" + "" + "')");
                        }
                    }
                }
            }
        }
        return element;
    }
}
