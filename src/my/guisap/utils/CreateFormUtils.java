/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.utils;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;
import my.guisap.componenst.AttributePanel;
import my.guisap.GuiStaticVariables;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author KiselevDA
 */
public class CreateFormUtils {

    private static SqlOperations sql = new SqlOperations();

    public static final Insets DEFAULT_INSETS = new Insets(0, 0, 0, 0);

    public static final Border defaultTextFieldBorder = new JTextField().getBorder();
    public static final CompoundBorder defaultBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(BevelBorder.LOWERED),
            BorderFactory.createEmptyBorder(10, 10, 10, 10));

    /**
     * Метод для создания нескольких текстовых полей для заполнения данных
     *
     * @param massElem массив состоящий из названий полей
     * @param listTextFields лист из текстовых полей, куда дублируются созданные
     * текстовые поля, для более удобного доступа
     * @param resultPanel JPanel которая отвечает за отображение созданных полей
     * @param editable указывает на возможность редактирования полей
     */
    public static void CreatePanelOfTextFields(String[] massElem, List<JTextField> listTextFields, JPanel resultPanel, boolean editable, int startElem, int countInRow, Insets insets) {
        JPanel tmpPanel;
        if (insets == null) {
            insets = new Insets(0, 0, 0, 0);
        }
        for (String elem : massElem) {
            tmpPanel = CreateElem(elem, null, editable);
            listTextFields.add((JTextField) (tmpPanel.getComponent(3)));
            resultPanel.add(tmpPanel,
                    new GridBagConstraints(startElem % countInRow, (int) startElem / countInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, insets, 0, 0));
            startElem++;
        }
    }

    /**
     * Метод для создания нескольких текстовых полей для заполнения данных в
     * massElem указывается доступность каждого из полей в формате
     * {"Название","true/false"}
     *
     * @param massElem массив состоящий из названий полей
     * @param listTextFields лист из текстовых полей, куда дублируются созданные
     * текстовые поля, для более удобного доступа
     * @param resultPanel JPanel которая отвечает за отображение созданных полей
     */
    public static void CreatePanelOfTextFields(String[][] massElem, List<JTextField> listTextFields, JPanel resultPanel, int startElem, int countInRow, Insets insets) {
        JPanel tmpPanel;
        if (insets == null) {
            insets = new Insets(0, 0, 0, 0);
        }
        for (String[] elem : massElem) {
            tmpPanel = CreateElem(elem[0], null, elem[1]);
            listTextFields.add((JTextField) (tmpPanel.getComponent(3)));
            resultPanel.add(tmpPanel,
                    new GridBagConstraints(startElem % countInRow, (int) startElem / countInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, insets, 0, 0));
            startElem++;
        }
    }

    /**
     * Метод для создания нескольких полей с кнопкой справочника
     *
     * @param query указывается Class и MainClass для выборки
     * @param editable указывает на возможность редактирования полей
     * @param startIndex номер атрибута(из выборки) с которого начинать
     * @return
     */
    public static AttributePanel CreateAttributePanel(String query, boolean editable, int startIndex) {
        DefaultTableModel dataModel = new DefaultTableModel();
        sql.tableFill(SqlOperations.DATA_SELECTION + query + SqlOperations.GROUP_BY, dataModel);
        return new AttributePanel(dataModel, editable, startIndex);
    }

    /**
     * Метод для создания текстового поля формата JLabel JTextField
     *
     * @param ElName название элемента
     * @param value значение (если элемент создается в форме, где уже имеются
     * данные)
     * @param Enable указывает на возможность редактирования поля
     * @return
     */
    public static JPanel CreateElem(String ElName, String value, boolean Enable) {

        JPanel resultElem = new JPanel();
        resultElem.setLayout(new BoxLayout(resultElem, BoxLayout.X_AXIS));
        JLabel label = ComponentsUtils.createLabel(ElName);
        resultElem.add(label);
        resultElem.add(Box.createHorizontalGlue());
        resultElem.add(Box.createHorizontalStrut(GuiStaticVariables.TIGHTLE_STRUT));

        JTextField textFieldElem = ComponentsUtils.createField(value, 227, 23, Enable);
        resultElem.add(textFieldElem);

        return resultElem;
    }

    public static JPanel CreateElem(String ElName, String value, String Enable) {
        JPanel tmpElement = new JPanel();
        tmpElement.setLayout(new BoxLayout(tmpElement, BoxLayout.X_AXIS));
        JLabel label = ComponentsUtils.createLabel(ElName);
        label.setFont(GuiStaticVariables.globalFont);
        tmpElement.add(label);
        tmpElement.add(Box.createHorizontalGlue());
        tmpElement.add(Box.createHorizontalStrut(GuiStaticVariables.TIGHTLE_STRUT));

        boolean enable = false;
        if (Enable.equals("true")) {
            enable = true;
        }

        JTextField tmpTextField = ComponentsUtils.createField(value, 227, 23, enable);
        tmpElement.add(tmpTextField);

        return tmpElement;
    }

    public static JPanel CreateFieldDownloadImage() {
        JPanel loadImageBlock = new JPanel();
        JTextField pathField = ComponentsUtils.createField("", 200, 23, false);
        JButton button = ComponentsUtils.createBtn("", 27, 23, ImageUtils.createIconForButton("/toolbarButtonGraphics/general/History16.gif"), true);

        button.addActionListener((ActionEvent ae) -> {
            FileDialog fd = new FileDialog(new Frame(), "Выберите файл", FileDialog.LOAD);
            fd.setFile("*.jpg;*.gif;*png");
            fd.setVisible(true);
            String filename = fd.getFile();
            if (filename == null) {
                pathField.setText("");
            } else {
                File imageModel = fd.getFiles()[0];
                pathField.setText(imageModel.getPath());
            }
        });

        loadImageBlock.setLayout(new BoxLayout(loadImageBlock, BoxLayout.X_AXIS));
        loadImageBlock.add(ComponentsUtils.createLabel("Прикрепить изображение: "));
        loadImageBlock.add(Box.createHorizontalGlue());
        loadImageBlock.add(Box.createHorizontalStrut(GuiStaticVariables.TIGHTLE_STRUT));
        loadImageBlock.add(pathField);
        loadImageBlock.add(button);
        return loadImageBlock;
    }

}
