/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author Hit
 */
public class NameOZMClass {

    DefaultTableModel artAttrModel = new DefaultTableModel(0, 0);
    DefaultTableModel colorFGModel = new DefaultTableModel(0, 0);
    DefaultTableModel short5FModel = new DefaultTableModel(0, 0);
    SqlOperations sql = new SqlOperations();
    String art;
    PreparedStatement ps;
    ArrayList<String> resultArr = new ArrayList<>();

    public NameOZMClass(String art) {
        this.art = art;
        try {
            ps = sql.preparingTableFill(SqlOperations.SHORT_NAME_5F_GROUP);
            if (ps != null) {
                sql.psTableFill(short5FModel, ps);
                ps.close();
            }
            ps = sql.preparingTableFill(SqlOperations.SHORT_NAME_COLOR_FG);
            if (ps != null) {
                sql.psTableFill(colorFGModel, ps);
                ps.close();
            }
            ps = sql.preparingTableFill(SqlOperations.ARTICLE_ATNAM_VALUE_SELECTION_1 + art + SqlOperations.ARTICLE_ATNAM_VALUE_SELECTION_2 + art + SqlOperations.ARTICLE_ATNAM_VALUE_SELECTION_3 + art + SqlOperations.ARTICLE_ATNAM_VALUE_SELECTION_4 + art + SqlOperations.ARTICLE_ATNAM_VALUE_SELECTION_5 + art + "'");
            if (ps != null) {
                sql.psTableFill(artAttrModel, ps);
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public String nameOZMCreating(String codeOZM, String substr, String shortName) {
        String resultName = "";
        resultArr.clear();
        switch (codeOZM) {
            case "title"://заголовок
                resultName = art + " " + attrSelect("ASSORTMENT") + " " + attrSelect("GROUPMANWOMAN") + " " + attrSelect("MAT_LINING") + " " + attrSelect("MAT_SOLE");
                break;
            case "5F"://ФУз Обувь BW
                if (shortName.equals("")) {
                    resultName = digitCheck(art, short5FSelect(attrSelect("ASSORTMENT")), short5FSelect(attrSelect("GROUPMANWOMAN")), shortColorSelect(attrSelect("COLOR_FG")), short5FSelect(attrSelect("MAT_LINING")), short5FSelect(attrSelect("MAT_SOLE")), "ВспМат");
                } else {
                    resultName = digitCheck(art, short5FSelect(attrSelect("ASSORTMENT")), short5FSelect(attrSelect("GROUPMANWOMAN")), shortColorSelect(shortName), short5FSelect(attrSelect("MAT_LINING")), short5FSelect(attrSelect("MAT_SOLE")), "ВспМат");
                }
                break;
            case "5F-1"://ФУз Обувь BW 1
                if (shortName.equals("")) {
                    resultName = digitCheck(art, short5FSelect(attrSelect("ASSORTMENT")), short5FSelect(attrSelect("GROUPMANWOMAN")), shortColorSelect(attrSelect("COLOR_FG")), short5FSelect(attrSelect("MAT_LINING")), short5FSelect(attrSelect("MAT_SOLE")), "ВспМат1");
                } else {
                    resultName = digitCheck(art, short5FSelect(attrSelect("ASSORTMENT")), short5FSelect(attrSelect("GROUPMANWOMAN")), shortColorSelect(shortName), short5FSelect(attrSelect("MAT_LINING")), short5FSelect(attrSelect("MAT_SOLE")), "ВспМат1");
                }
                break;
            case "5F-2"://ФУз Обувь BW 2
                if (shortName.equals("")) {
                    resultName = digitCheck(art, short5FSelect(attrSelect("ASSORTMENT")), short5FSelect(attrSelect("GROUPMANWOMAN")), shortColorSelect(attrSelect("COLOR_FG")), short5FSelect(attrSelect("MAT_LINING")), short5FSelect(attrSelect("MAT_SOLE")), "ВспМат2");
                } else {
                    resultName = digitCheck(art, short5FSelect(attrSelect("ASSORTMENT")), short5FSelect(attrSelect("GROUPMANWOMAN")), shortColorSelect(shortName), short5FSelect(attrSelect("MAT_LINING")), short5FSelect(attrSelect("MAT_SOLE")), "ВспМат2");
                }
                break;
            case "5F-5"://ФУз Обувь BW 5
                if (shortName.equals("")) {
                    resultName = digitCheck(art, short5FSelect(attrSelect("ASSORTMENT")), short5FSelect(attrSelect("GROUPMANWOMAN")), shortColorSelect(attrSelect("COLOR_FG")), short5FSelect(attrSelect("MAT_LINING")), short5FSelect(attrSelect("MAT_SOLE")), "ВспМат5");
                } else {
                    resultName = digitCheck(art, short5FSelect(attrSelect("ASSORTMENT")), short5FSelect(attrSelect("GROUPMANWOMAN")), shortColorSelect(shortName), short5FSelect(attrSelect("MAT_LINING")), short5FSelect(attrSelect("MAT_SOLE")), "ВспМат5");
                }
                break;
            case "5U"://ФУз Упаковка
                resultName = digitCheck("Упаковка", attrSelect("MODEL"));
                break;
            case "51000"://ФУз Крой верха
                if (shortName.equals("")) {
                    resultName = digitCheck("Крой", attrSelect("ART"), shortColorSelect(attrSelect("COLOR_FG")), "ВспМат");
                } else {
                    resultName = digitCheck("Крой", attrSelect("ART"), shortColorSelect(shortName), "ВспМат");
                }
                break;
            case "51102"://ПФ Заготовки д/верха
                if (shortName.equals("")) {
                    resultName = digitCheck("Заготовка", attrSelect("ART"), shortColorSelect(attrSelect("COLOR_FG")), "ВспМат");
                } else {
                    resultName = digitCheck("Заготовка", attrSelect("ART"), shortColorSelect(shortName), "ВспМат");
                }
                break;
            case "51200"://ФУз Обувь Десма-2
                if (shortName.equals("")) {
                    resultName = digitCheck("ОбувьДесма", attrSelect("ART"), shortColorSelect(attrSelect("COLOR_FG")), "ВспМат");
                } else {
                    resultName = digitCheck("ОбувьДесма", attrSelect("ART"), shortColorSelect(shortName), "ВспМат");
                }
                break;
            case "51501"://ФУз Задники
                if (shortName.equals("")) {
                    resultName = digitCheck("Задник", attrSelect("FASON_LAST"), shortColorSelect(attrSelect("COLOR_FG")), "ВспМат");
                } else {
                    resultName = digitCheck("Задник", attrSelect("FASON_LAST"), shortColorSelect(shortName), "ВспМат");
                }
                break;
            case "51502": // ПФ Подносок
                resultName = digitCheck("Подносок", attrSelect("FASON_LAST"), "ВспМат");
                break;
            case "52000"://ФУз Подошвы
                if (substr.equals("") || substr.equals("ТЭП")) {
                    resultName = digitCheck("Подошва", attrSelect("FASON_SOLE"), shortName, "ВспМат");
                } else {
                    if (attrSelect("FASON_SOLE").equals("КОЖВОЛОН") || attrSelect("FASON_SOLE").equals("KOZHVOLON")) {
                        resultName = digitCheck("Подошва", attrSelect("FASON_SOLE"), substr.substring(substr.length() - 3, substr.length()), shortName, "ВспМат");
                    } else {
                        resultName = digitCheck("Подошва", attrSelect("FASON_SOLE"), substr, shortName, "ВспМат");
                    }
                }
                break;
            case "52202"://ФУз Каблук
                resultName = digitCheck("Каблук", attrSelect("FASON_HEEL"), shortName, "ВспМат");
                break;
            case "52203"://ФУз Набойка
                resultName = digitCheck("Набойка", attrSelect("FASON_HEEL"), shortName, "ВспМат");
                break;
            case "52401"://ФУз Стелька вкладная
                if (substr.equals("") || substr.equals("НатМех")) {
                    resultName = digitCheck("ВклСт", attrSelect("FASON_LAST"), short5FSelect(attrSelect("GROUPMANWOMAN")), substr, shortName, "ВспМат");
                } else {
                    resultName = digitCheck("ВклСт", attrSelect("FASON_LAST"), short5FSelect(attrSelect("GROUPMANWOMAN")), substr, shortName, "ВспМат");
                }
                break;
            case "52402"://ФУз Стелька основная
                if (substr.equals("Артикул") || substr.equals("")) {
                    resultName = digitCheck("ОснСт", attrSelect("FASON_LAST"), attrSelect("ART"), shortName, "ВспМат");
                } else {
                    resultName = digitCheck("ОснСт", attrSelect("FASON_LAST"), attrSelect("MODEL"), shortName, "ВспМат");
                }
                break;
            case "57305"://ФУз Резаки
                resultName = digitCheck("Комплект резаков верха", attrSelect("MODEL"), "ВспМат");
                break;
            case "61000"://ПФ Крой верха
                if (shortName.equals("")) {
                    resultName = digitCheck("Крой", attrSelect("ART"), shortColorSelect(attrSelect("COLOR_FG")));
                } else {
                    resultName = digitCheck("Крой", attrSelect("ART"), shortColorSelect(shortName));
                }
                break;
            case "61102"://ПФ Заготовки д/верха
                if (shortName.equals("")) {
                    resultName = digitCheck("Заготовка", attrSelect("ART"), shortColorSelect(attrSelect("COLOR_FG")));
                } else {
                    resultName = digitCheck("Заготовка", attrSelect("ART"), shortColorSelect(shortName));
                }
                break;
            case "61120"://ПФ Заготовки д/десмы
                if (shortName.equals("")) {
                    resultName = digitCheck("ЗаготовкаДесма", attrSelect("ART"), shortColorSelect(attrSelect("COLOR_FG")));
                } else {
                    resultName = digitCheck("ЗаготовкаДесма", attrSelect("ART"), shortColorSelect(shortName));
                }
                break;
            case "61200"://ПФ Обувь Десма-2
                if (shortName.equals("")) {
                    resultName = digitCheck("ОбувьДесма", attrSelect("ART"), shortColorSelect(attrSelect("COLOR_FG")));
                } else {
                    resultName = digitCheck("ОбувьДесма", attrSelect("ART"), shortColorSelect(shortName));
                }
                break;
            case "61501"://ПФ Задники
                if (shortName.equals("")) {
                    resultName = digitCheck("Задник", attrSelect("FASON_LAST"), shortColorSelect(attrSelect("COLOR_FG")));
                } else {
                    resultName = digitCheck("Задник", attrSelect("FASON_LAST"), shortColorSelect(shortName));
                }
                break;
            case "61502": // ПФ Подносок
                resultName = digitCheck("Подносок", attrSelect("FASON_LAST"));
                break;

            case "62000"://ПФ Подошвы
                if (substr.equals("") || substr.equals("ТЭП")) {
                    resultName = digitCheck("Подошва", attrSelect("FASON_SOLE"), shortName);
                } else {
                    if (attrSelect("FASON_SOLE").equals("КОЖВОЛОН") || attrSelect("FASON_SOLE").equals("KOZHVOLON")) {
                        resultName = digitCheck("Подошва", attrSelect("FASON_SOLE"), substr.substring(substr.length() - 3, substr.length()), shortName);
                    } else {
                        resultName = digitCheck("Подошва", attrSelect("FASON_SOLE"), substr, shortName);
                    }
                }
                break;
            case "62202"://ПФ Каблук
                resultName = digitCheck("Каблук", attrSelect("FASON_HEEL"), shortName);
                break;
            case "62203"://ПФ Набойка
                resultName = digitCheck("Набойка", attrSelect("FASON_HEEL"), shortName);
                break;
            case "62401"://ПФ Стелька вкладная
                if (substr.equals("") || substr.equals("НатМех")) {
                    resultName = digitCheck("ВклСт", attrSelect("FASON_LAST"), short5FSelect(attrSelect("GROUPMANWOMAN")), substr, shortName);
                } else {
                    resultName = digitCheck("ВклСт", attrSelect("FASON_LAST"), short5FSelect(attrSelect("GROUPMANWOMAN")), substr, shortName);
                }
                break;
            case "62402"://ПФ Стелька основная
                if (substr.equals("Артикул") || substr.equals("")) {
                    resultName = digitCheck("ОснСт", attrSelect("FASON_LAST"), attrSelect("ART"), shortName);
                } else {
                    resultName = digitCheck("ОснСт", attrSelect("FASON_LAST"), attrSelect("MODEL"), shortName);
                }
                break;
            case "64206"://ПФ Вкладыш в обувь
                resultName = digitCheck("ВкладышАБС", attrSelect("FASON_HEEL"));
                break;
            case "67305"://ПФ Резаки верха
                resultName = digitCheck("КомплектРезаковВерха", attrSelect("MODEL"));
                break;
            case "67306"://ПФ Резаки низа
                resultName = digitCheck("КомплектРезаковНиза", attrSelect("FASON_LAST"));
                break;
            case "RakursArticle"://Артикул RakursForm
                if (art.equals(substr)) {
                    resultName = digitCheck(art, short5FSelect(attrSelect("ASSORTMENT")), short5FSelect(attrSelect("GROUPMANWOMAN")), shortColorSelect(attrSelect("COLOR_FG")), short5FSelect(attrSelect("MAT_UPPER")), short5FSelect(attrSelect("MAT_SOLE")), short5FSelect(attrSelect("MAT_LINING")));
                } else {
                    if (!substr.substring(substr.length() - 1).equals("0")) {
                        resultName = digitCheck(art, short5FSelect(attrSelect("ASSORTMENT")), short5FSelect(attrSelect("GROUPMANWOMAN")), shortColorSelect(attrSelect("COLOR_FG")), short5FSelect(attrSelect("MAT_UPPER")), short5FSelect(attrSelect("MAT_SOLE")), short5FSelect(attrSelect("MAT_LINING")), substr);
                    } else {
                        resultName = digitCheck(art, short5FSelect(attrSelect("ASSORTMENT")), short5FSelect(attrSelect("GROUPMANWOMAN")), shortColorSelect(attrSelect("COLOR_FG")), short5FSelect(attrSelect("MAT_UPPER")), short5FSelect(attrSelect("MAT_SOLE")), short5FSelect(attrSelect("MAT_LINING")), substr.substring(0, substr.length() - 2));
                    }
                }
                break;
        }

        return resultName;
    }

    public String fullColorNameSelecting(String groupCode) {
        if (groupCode.equals("5F") || groupCode.equals("5F-1") || groupCode.equals("5F-2") || groupCode.equals("5F-5") || groupCode.equals("51000") || groupCode.equals("51102") || groupCode.equals("51200") || groupCode.equals("61000") || groupCode.equals("61102") || groupCode.equals("61120") || groupCode.equals("61200")) {
            if (!shortColorSelect(attrSelect("COLOR_FG")).equals("")) {
                return attrSelect("COLOR_FG");
            }
        }
        return "";
    }

    public String attrSelect(String atnam) {
        for (int i = 0; i < artAttrModel.getRowCount(); i++) {
            if (artAttrModel.getValueAt(i, 0).toString().equals(atnam)) {
                return artAttrModel.getValueAt(i, 1).toString();
            }
        }
        return "";
    }

    public String shortColorSelect(String color) {
        for (int i = 0; i < colorFGModel.getRowCount(); i++) {
            if (colorFGModel.getValueAt(i, 1).toString().equals(color)) {
                return colorFGModel.getValueAt(i, 2).toString();
            }
        }
        return "";
    }

    public String short5FSelect(String color) {
        for (int i = 0; i < short5FModel.getRowCount(); i++) {
            if (short5FModel.getValueAt(i, 0).toString().equals(color)) {
                return short5FModel.getValueAt(i, 1).toString();
            }
        }
        return "";
    }

    public String digitCheck(String... attributes) {
        String OZM = "";
        for (int ind = 1; ind < attributes.length; ind++) {
            OZM += attributes[ind - 1];
            if (!attributes[ind - 1].equals("") && !attributes[ind].equals("")) {
                if (Character.isDigit(attributes[ind - 1].charAt(attributes[ind - 1].length() - 1))
                        && Character.isDigit(attributes[ind].charAt(attributes[ind].length() - 1))) {
                    OZM += "_";
                }
            }
        }
        OZM += attributes[attributes.length - 1];
        return OZM;
    }
}
