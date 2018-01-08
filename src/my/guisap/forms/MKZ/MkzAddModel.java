package my.guisap.forms.MKZ;

import my.guisap.componenst.AttributePanel;
import my.guisap.utils.LogClass;
import my.guisap.componenst.EmptyForm;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import my.guisap.FormRegister;
import my.guisap.GuiStaticVariables;
import my.guisap.attributeRow;
import my.guisap.forms.Last.RequestLastForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.CacheImage;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.CreateFormUtils;
import my.guisap.utils.ImageUtils;
import my.guisap.utils.LifeLineUtils;
import my.guisap.utils.TextUtils;

public class MkzAddModel extends EmptyForm {

    //лист для полей со справочниками для проверки
    ArrayList<AttributePanel> listForCheck = new ArrayList<>();
    ArrayList<String> listExeptionsForCheck = new ArrayList<>();
    String classFlag;
    //поле содержащее год
    JTextField year;
    //поле для ввода /
    JTextField number;
    //поле для отображения пути загружаемого изображения
    JTextField pathFile;
    AttributePanel infoAttPanel;

    MkzMainForm ParrentForm;
    MkzAddModel thisForm = this;

    //заголовок окна
    String Head_Name;
    //кнопка завершить этап
    JButton finishStage;

    //кнопка заявки на колодку
    JButton requestLast;
    JButton closeRequestLast;
    JLabel requestInfo;
    //кнопка загрузить изображение
    JButton loadImage;
    //ID модели, используется для редактирования на первом этапе
    int ID_MODEL = 0;
    //текущий статус, null если модель новая
    String curentStatus = "F";
    String dateStatus = "";
    //поле содержащее артикул модели
    String Model;
    String startModel;
    //группа для RadioButton
    ButtonGroup btnGroup = new ButtonGroup();

    //по умолчанию
    JRadioButton defRButton;
    //большеразмерный
    JRadioButton bigRButton;
    //образец
    JRadioButton exRButton;

    //поле хранящее выбор RadioButton
    String selectedRButton = " ";
    //массив из цифр для проверки на ввод
    ArrayList<Integer> checkList;
    //проверка при открытии окна, редактирование или новая модель
    Boolean editElem;
    //проверка для года
    Boolean checkYear = false;

    File imageModel;

    DefaultTableModel tmpDataModel = new DefaultTableModel();

    String[][] fieldsToRequestLast = {
        {"Художник", ""},
        {"Фасон подошвы", ""},
        {"Фасон каблука", ""},
        {"Материал подкладки", ""}};

    public MkzAddModel(String caption, String classFlag, MkzMainForm ParrentForm, String Model, Boolean editElem) {

        super(caption, classFlag, true, false);

        fr = FormRegister.getInstance();

        this.editElem = editElem;
        this.ParrentForm = ParrentForm;
        this.classFlag = classFlag;
        Head_Name = caption;

        if (Model.equals("")) {
            this.Model = "0000000000";
            this.checkYear = false;
        } else {
            this.Model = Model;
            this.startModel = Model;
            tmpDataModel = new DefaultTableModel();
            sql.tableFill("select ID,STATUS,DATE_UPDATE from GUI_SAP.MKZ_LIST_HEAD where model = '" + Model + "'", tmpDataModel);
            if (tmpDataModel.getRowCount() == 1) {
                ID_MODEL = Integer.valueOf(tmpDataModel.getValueAt(0, 0).toString());
                curentStatus = tmpDataModel.getValueAt(0, 1).toString();
                dateStatus = tmpDataModel.getValueAt(0, 2).toString();
            }
            this.checkYear = true;
        }

        log = LogClass.getInstance();

        listExeptionsForCheck.add("Художник таб.№ ");
        listExeptionsForCheck.add("Модель-аналог");
        listExeptionsForCheck.add("Фасон каблука");

        createFormFields();
    }

    private void createFormFields() {

        JPanel attPanelAll = new JPanel(new GridBagLayout());

        JPanel yearPanel = new JPanel();
        JPanel attPanelFirstBlock = new JPanel(new GridBagLayout());
        JPanel attPanelSecondBlock = new JPanel(new GridBagLayout());
        JPanel loadImageBlock = new JPanel(new GridBagLayout());
        JPanel createRequestBlock = new JPanel(new GridBagLayout());

        yearPanel.setLayout(new BoxLayout(yearPanel, BoxLayout.X_AXIS));
        JLabel labelYear = ComponentsUtils.createLabel("Год : ");
        yearPanel.add(labelYear);
        yearPanel.add(Box.createHorizontalGlue());
        yearPanel.add(Box.createHorizontalStrut(GuiStaticVariables.TIGHTLE_STRUT));

        year = ComponentsUtils.createField("", 227, 23, true);
        yearPanel.add(year);

        //создание группы RadioButton
        defRButton = new JRadioButton();
        createJRadioButton(defRButton, "По умолчанию(обычная)", " ", true);

        bigRButton = new JRadioButton();
        createJRadioButton(bigRButton, "Большеразмерная", "9", false);

        exRButton = new JRadioButton();
        createJRadioButton(exRButton, "Образец", "99", false);

        JLabel nameNumber = new JLabel("Значение после дроби");
        nameNumber.setFont(GuiStaticVariables.globalFont);

        number = ComponentsUtils.createField("", 35, 23, true);

        //путь изображения
        pathFile = ComponentsUtils.createField("", 200, 23, false);
        loadImage = ComponentsUtils.createBtn("", 27, 23, true);
        loadImage.setIcon(ImageUtils.createIconForButton("/toolbarButtonGraphics/general/History16.gif"));

        loadImageBlock.setLayout(new BoxLayout(loadImageBlock, BoxLayout.X_AXIS));
        JLabel labelLoad = ComponentsUtils.createLabel("Прикрепить изображение: ");
        loadImageBlock.add(labelLoad);
        loadImageBlock.add(Box.createHorizontalGlue());
        loadImageBlock.add(Box.createHorizontalStrut(GuiStaticVariables.TIGHTLE_STRUT));
        loadImageBlock.add(pathFile);
        loadImageBlock.add(loadImage);

        //создание полей со справочниками
        headAttPanel = CreateFormUtils.CreateAttributePanel("'MODEL_NUMBER' and priority < 4 and MAIN_CLASS = '" + classFlag + "' ", true, 0);
        infoAttPanel = CreateFormUtils.CreateAttributePanel("'MODEL_NUMBER' and priority >= 4 and MAIN_CLASS = '" + classFlag + "' ", true, 0);

        listForCheck.add(infoAttPanel);

        attPanelFirstBlock.add(yearPanel,
                new GridBagConstraints(0, 0, 4, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        attPanelFirstBlock.add(headAttPanel,
                new GridBagConstraints(0, 1, 4, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        attPanelFirstBlock.add(defRButton, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        attPanelFirstBlock.add(bigRButton, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        attPanelFirstBlock.add(exRButton, new GridBagConstraints(2, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        attPanelFirstBlock.add(nameNumber, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        attPanelFirstBlock.add(number, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        attPanelSecondBlock.add(infoAttPanel,
                new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        //блок заявки
        requestLast = ComponentsUtils.createBtn("Сформировать заявку на колодку", 220, 17, true);
        closeRequestLast = ComponentsUtils.createBtn("Отменить заявку", 220, 17, false);

        if (curentStatus.equals("L")) {
            requestInfo = ComponentsUtils.createLabel("");
            blockFirstStep(false);
            requestInfo.setText("Заявка отправлена : " + dateStatus);
        } else {
            requestInfo = ComponentsUtils.createLabel("Заявка отсутствует");
        }

        createRequestBlock.add(requestInfo, new GridBagConstraints(0, 0, 1, 1, 2, 2, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        createRequestBlock.add(closeRequestLast, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        createRequestBlock.add(requestLast, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        attPanelFirstBlock.setBorder(CreateFormUtils.defaultBorder);
        attPanelSecondBlock.setBorder(CreateFormUtils.defaultBorder);
        loadImageBlock.setBorder(CreateFormUtils.defaultBorder);
        createRequestBlock.setBorder(CreateFormUtils.defaultBorder);

        attPanelAll.add(attPanelFirstBlock,
                new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        attPanelAll.add(attPanelSecondBlock,
                new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(20, 0, 0, 0), 0, 0));
        attPanelAll.add(loadImageBlock,
                new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(20, 0, 0, 0), 0, 0));
        attPanelAll.add(createRequestBlock,
                new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(20, 0, 0, 0), 0, 0));

        pnlAttElem.add(attPanelAll);

        pnlAttElem.add(Box.createVerticalStrut(GuiStaticVariables.ELEMENT_STRUT));

        //создание кнопки завершить этап
        finishStage = ComponentsUtils.createBtn("Завершить этап", 140, 23, true);
        pnlSouth.add(new JLabel("    "));
        pnlSouth.add(finishStage);

        addListeners();

        if (ID_MODEL != 0) {
            LoadSaveModel();
        }

        pack();
        setCenter();
    }

    public void LoadSaveModel() {
        DefaultTableModel loadModel = new DefaultTableModel();
        sql.tableFill("select * from GUI_SAP.MKZ_LIST_HEAD where ID = '" + ID_MODEL + "'", loadModel);
        int NumCount = 2;

        if (loadModel.getValueAt(0, NumCount) != null) {
            year.setText(loadModel.getValueAt(0, NumCount).toString());
        }
        NumCount++;

        for (attributeRow ap : headAttPanel.rowList) {
            if (loadModel.getValueAt(0, NumCount) != null) {
                ap.txt.setText(loadModel.getValueAt(0, NumCount).toString());
            }
            NumCount++;
        }
        for (attributeRow ap : infoAttPanel.rowList) {
            if (loadModel.getValueAt(0, NumCount) != null) {
                ap.txt.setText(loadModel.getValueAt(0, NumCount).toString());
            }
            NumCount++;
        }

        if (editElem) {
            Model = loadModel.getValueAt(0, 1).toString();
            lblHeader.setText(Head_Name + " " + loadModel.getValueAt(0, 1).toString());
        } else {
            SetHeadLabelModel();
        }

    }

    public void SetHeadLabelModel() {
        Model = GetValueModel(editElem);
        lblHeader.setText(Head_Name + " " + Model);
    }

    public String GetValueModel(boolean editElem) {
        DefaultTableModel modelToCreateArticle = new DefaultTableModel();
        String query = "select GUI_SAP.FORMING_ART('" + year.getText() + "','" + headAttPanel.rowList.get(0).txt.getText() + " " + headAttPanel.rowList.get(1).txt.getText() + "','" + headAttPanel.rowList.get(2).txt.getText() + "') art from dual ";
        if (editElem) {
            query = "select GUI_SAP.FORMING_ART('" + year.getText() + "','" + headAttPanel.rowList.get(0).txt.getText() + " " + headAttPanel.rowList.get(1).txt.getText() + "','" + headAttPanel.rowList.get(2).txt.getText() + "','" + startModel + "') art from dual ";
        }
        sql.tableFill(query, modelToCreateArticle);
        if (modelToCreateArticle.getValueAt(0, 0) != null && modelToCreateArticle.getValueAt(0, 0).toString().length() == 10) {
            StringBuilder result = new StringBuilder(modelToCreateArticle.getValueAt(0, 0).toString());
            if (number.getText().equals("")) {
                result.setCharAt(7, '0');
            } else {
                result.setCharAt(7, number.getText().charAt(0));
            }
            if (bigRButton.isSelected()) {
                result.setCharAt(7, '9');
            }
            if (exRButton.isSelected()) {
                result.setCharAt(8, '9');
                result.setCharAt(9, '9');
            }
            return result.toString();
        } else {
            return " ";
        }
    }

    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkYear && !TextUtils.checkAttributePanel(headAttPanel, true)) {
            SaveParemetrList("F");
        } else {
            JOptionPane.showMessageDialog(this, "Для создания модели заполните основные данные", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void SaveParemetrList(String EndParam) {

        if (Model.equals(" ")) {
            JOptionPane.showMessageDialog(this, "Невозможно сформировать код модели", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (sql.checkInfo(SqlOperations.LAST_REQUEST + " where MODEL='" + Model + "' and STATUS='OP'")) {
            EndParam = "L";
        }

        SetHeadLabelModel();
        String InsQuery = "insert into GUI_SAP.MKZ_LIST_HEAD (ID, MODEL, YEAR, GROUPMANWOMAN, METHOD_CONSTR, SHIN_VIEW, SEASON, STYLE_MODEL, SIMILAR_MODEL, MAT_LINING, FASON_SOLE,  FASON_LAST, CODE_LAST, FASON_HEEL, DESIGNER, STATUS)"
                + "(select (select NVL(max(TO_NUMBER(ID)),0)+1 from GUI_SAP.MKZ_LIST_HEAD) ID,  ";
        String UpdQuery = "update GUI_SAP.MKZ_LIST_HEAD set (MODEL, YEAR, GROUPMANWOMAN, METHOD_CONSTR, SHIN_VIEW, SEASON, STYLE_MODEL, SIMILAR_MODEL, MAT_LINING, FASON_SOLE,FASON_LAST, CODE_LAST, FASON_HEEL,DESIGNER, STATUS) = "
                + "(select ";
        String AddQuery = "";
        String[] data = new String[headAttPanel.getComponentCount() + infoAttPanel.getComponentCount() + 3];
        int count = 0;

        data[count] = Model;
        count++;

        data[count] = year.getText();
        count++;

        for (attributeRow atR : headAttPanel.rowList) {
            atR.editFlag = false;
            data[count] = atR.txt.getText();
            count++;
        }

        for (attributeRow atR : infoAttPanel.rowList) {
            atR.editFlag = false;
            data[count] = atR.txt.getText();
            count++;
        }

        for (int i = 0; i < count; i++) {
            AddQuery += "'" + data[i] + "',";
        }

        if (!sql.checkInfo("select ID from GUI_SAP.MKZ_LIST_HEAD where ID = '" + ID_MODEL + "'") || !editElem) {
            BigDecimal nextID = (BigDecimal) sql.getObj("select NVL(max(TO_NUMBER(ID)),0)+1 from GUI_SAP.MKZ_LIST_HEAD");
            ID_MODEL = nextID.intValue();
            AddQuery = InsQuery + AddQuery + "'" + EndParam + "' from dual)";
        } else {
            AddQuery = UpdQuery + AddQuery + "'" + EndParam + "' from dual) where ID = '" + ID_MODEL + "'";
        }

        sql.SendQuery(AddQuery);
        LifeLineUtils.InsertLifeLine(LifeLineUtils.MKZ_LIFE_LINE, Model, EndParam);

        if (EndParam.equals("F")) {
            JOptionPane.showMessageDialog(this, "Модель " + data[0] + " сохранена.", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
            log.logWriting("Модель " + data[0] + " сохранена.");
        } else {
            JOptionPane.showMessageDialog(this, "Модель " + data[0] + " сохранена. Этап формирования модели окончен.", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
            log.logWriting("Модель " + data[0] + " сохранена. Этам формирования модели окончен. ");
        }

        log.logWriting(AddQuery);

        if (!pathFile.getText().equals("")) {
            ImageUtils.saveImage(imageModel, GuiStaticVariables.DEF_PICTURES_PATH, GuiStaticVariables.DEF_PICTURESICONS_PATH, Model, CacheImage.cacheModel);
        }

        this.closeWindow();
        ParrentForm.modelFormation(null);
    }

    private boolean checkAllRow() {
        if (TextUtils.checkAttributePanel(headAttPanel, false)) {
            return true;
        }
        for (AttributePanel atP : listForCheck) {
            if (TextUtils.checkAttributePanel(atP, false)) {
                return true;
            }
        }
        return false;
    }

    private void createJRadioButton(JRadioButton btnToCreate, String textBtn, final String textForSelect, boolean setSelected) {
        btnToCreate.setFont(GuiStaticVariables.globalFont);
        btnToCreate.setText(textBtn);
        btnToCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                selectedRButton = textForSelect;
                SetHeadLabelModel();
            }
        });
        if (setSelected) {
            btnToCreate.setSelected(true);
        }
        btnGroup.add(btnToCreate);
    }

    /**
     * блокировка(разблокировка) первого блока после формирования завявки на
     * колодку
     *
     * @param status
     */
    public void blockFirstStep(boolean status) {
        year.setEditable(status);

        for (attributeRow at : headAttPanel.rowList) {
            at.btn.setEnabled(status);
        }

        closeRequestLast.setEnabled(!status);

        infoAttPanel.rowList.get(5).btn.setEnabled(status);
        infoAttPanel.rowList.get(6).btn.setEnabled(status);

        defRButton.setEnabled(status);
        bigRButton.setEnabled(status);
        exRButton.setEnabled(status);

        number.setEnabled(status);

        if (status == false) {
            requestLast.setText("Открыть заявку");
            requestInfo.setText("Заявка отправлена");
        } else {
            requestLast.setText("Сформировать заявку на колодку");
            requestInfo.setText("Заявка отсутствует");
        }
    }

    /**
     * обновление статуса модели (если она создана ID_MODEL!=0)
     *
     * @param status значение статуса для заполнения
     */
    public void updateStatusTo(String status) {
        if (ID_MODEL != 0) {
            sql.SendQuery("UPDATE MKZ_LIST_HEAD set STATUS='" + status + "' WHERE MODEL='" + Model + "'");
        }
    }

    private void fillMassToRequest() {
        fieldsToRequestLast[0][1] = infoAttPanel.rowList.get(8).txt.getText();
        fieldsToRequestLast[1][1] = infoAttPanel.rowList.get(4).txt.getText();
        fieldsToRequestLast[2][1] = infoAttPanel.rowList.get(7).txt.getText();
    }

    private void addListeners() {

        //проверка поля Год
        year.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                String s = "" + e.getKeyChar();
                if (year.getText().length() > 3 || !TextUtils.checkStringToNumber(s, null)) {
                    e.consume();
                } else if (year.getText().length() == 3 && TextUtils.checkStringToNumber(year.getText() + s, 1900, 2100)) {
                    year.setText(year.getText() + s);
                    SetHeadLabelModel();
                    year.setText(year.getText().substring(0, year.getText().length() - 1));
                }
            }
        });

        year.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                checkYear = TextUtils.checkStringToNumber(year.getText(), 1900, 2100);
            }
        });

        //на поля первого блока  
        for (attributeRow tmpRow : headAttPanel.rowList) {
            tmpRow.txt.addCaretListener(new CaretListener() {
                @Override
                public void caretUpdate(CaretEvent e) {
                    SetHeadLabelModel();
                }
            });
        }

        //0 поле - пол , 2 поле вид обуви, после изменения в 0 поле, во 2ром заполняется информация, благодаря которой делается выборка (используется для отображения вида обуви по полу)
        headAttPanel.rowList.get(0).txt.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                headAttPanel.rowList.get(2).infoForCheck = headAttPanel.rowList.get(0).txt.getText();
            }
        });

        infoAttPanel.rowList.get(4).txt.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                infoAttPanel.rowList.get(5).infoForCheck = "FASON_SOLE='" + infoAttPanel.rowList.get(4).txt.getText()
                        + "' or FASON_SOLE_2='" + infoAttPanel.rowList.get(4).txt.getText()
                        + "' or FASON_SOLE_3='" + infoAttPanel.rowList.get(4).txt.getText() + "'";
                infoAttPanel.rowList.get(6).infoForCheck = "FASON_SOLE='" + infoAttPanel.rowList.get(4).txt.getText()
                        + "' or FASON_SOLE_2='" + infoAttPanel.rowList.get(4).txt.getText()
                        + "' or FASON_SOLE_3='" + infoAttPanel.rowList.get(4).txt.getText() + "'";
                if (infoAttPanel.rowList.get(4).txt.getText().equals("") || infoAttPanel.rowList.get(4).txt.getText().equals(" ")) {
                    infoAttPanel.rowList.get(5).infoForCheck = "";
                    infoAttPanel.rowList.get(6).infoForCheck = "";
                    infoAttPanel.rowList.get(7).infoForCheck = "";
                }
            }
        });

        infoAttPanel.rowList.get(5).txt.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                infoAttPanel.rowList.get(4).infoForCheck = "FASON_LAST='" + infoAttPanel.rowList.get(5).txt.getText() + "'";
                infoAttPanel.rowList.get(6).infoForCheck = "FASON_LAST='" + infoAttPanel.rowList.get(5).txt.getText() + "'";
                infoAttPanel.rowList.get(7).infoForCheck = "FASON_LAST='" + infoAttPanel.rowList.get(5).txt.getText() + "'";
                if (infoAttPanel.rowList.get(5).txt.getText().equals("") || infoAttPanel.rowList.get(5).txt.getText().equals(" ")) {
                    infoAttPanel.rowList.get(4).infoForCheck = "";
                    infoAttPanel.rowList.get(4).txt.setText("");
                    infoAttPanel.rowList.get(6).infoForCheck = "";
                    infoAttPanel.rowList.get(6).txt.setText("");
                    infoAttPanel.rowList.get(7).infoForCheck = "";
                    infoAttPanel.rowList.get(7).txt.setText("");
                }
            }
        });

        infoAttPanel.rowList.get(6).txt.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                infoAttPanel.rowList.get(4).infoForCheck = "a.INDEX_LAST='" + infoAttPanel.rowList.get(6).txt.getText() + "'";
                infoAttPanel.rowList.get(5).infoForCheck = "a.INDEX_LAST='" + infoAttPanel.rowList.get(6).txt.getText() + "'";
                infoAttPanel.rowList.get(7).infoForCheck = "a.INDEX_LAST='" + infoAttPanel.rowList.get(6).txt.getText() + "'";
                if (infoAttPanel.rowList.get(6).txt.getText().equals("") || infoAttPanel.rowList.get(6).txt.getText().equals(" ")) {
                    infoAttPanel.rowList.get(4).infoForCheck = "";
                    infoAttPanel.rowList.get(4).txt.setText("");
                    infoAttPanel.rowList.get(5).infoForCheck = "";
                    infoAttPanel.rowList.get(5).txt.setText("");
                    infoAttPanel.rowList.get(7).infoForCheck = "";
                    infoAttPanel.rowList.get(7).txt.setText("");
                }
            }
        });

        infoAttPanel.rowList.get(7).txt.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                infoAttPanel.rowList.get(5).infoForCheck = "FASON_HEEL='" + infoAttPanel.rowList.get(7).txt.getText()
                        + "' or FASON_HEEL_2='" + infoAttPanel.rowList.get(7).txt.getText()
                        + "' or FASON_HEEL_3='" + infoAttPanel.rowList.get(7).txt.getText() + "'";
                infoAttPanel.rowList.get(6).infoForCheck = "FASON_HEEL='" + infoAttPanel.rowList.get(7).txt.getText()
                        + "' or FASON_HEEL_2='" + infoAttPanel.rowList.get(7).txt.getText()
                        + "' or FASON_HEEL_3='" + infoAttPanel.rowList.get(7).txt.getText() + "'";
                if (infoAttPanel.rowList.get(7).txt.getText().equals("") || infoAttPanel.rowList.get(7).txt.getText().equals(" ")) {
                    infoAttPanel.rowList.get(4).infoForCheck = "";
                    infoAttPanel.rowList.get(5).infoForCheck = "";
                    infoAttPanel.rowList.get(6).infoForCheck = "";
                }
            }
        });

        //ограничение на ввод цифр в поле number
        checkList = new ArrayList<>();
        checkList.add(9);
        checkList.add(0);
        // значение после дроби
        number.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                String s = "" + e.getKeyChar();
                if (number.getText().length() > 0 || !TextUtils.checkStringToNumber(s, checkList)) {
                    e.consume();
                } else if (number.getText().length() == 0) {
                    number.setText(number.getText() + s);
                    SetHeadLabelModel();
                    number.setText(number.getText().substring(0, number.getText().length() - 1));
                }
            }
        });

        // кнопка загрузить изображение
        loadImage.addActionListener((ActionEvent ae) -> {
            FileDialog fd = new FileDialog(new Frame(), "Выберите файл", FileDialog.LOAD);
            fd.setFile("*.jpg;*.gif;*png");
            fd.setVisible(true);
            String filename = fd.getFile();
            if (filename == null) {
                pathFile.setText("");
            } else {
                imageModel = fd.getFiles()[0];
                pathFile.setText(imageModel.getPath());
            }
        });

        //кнопка создания заявки на колодку
        requestLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkYear && !TextUtils.checkAttributePanel(headAttPanel, true)) {
                    fillMassToRequest();
                    fr.openForm(new RequestLastForm("Заявка на создание колодки", GuiStaticVariables.LAST_REQUEST, Model, thisForm, fieldsToRequestLast), FormRegister.ONE_KEY_FORM);
                } else {
                    JOptionPane.showMessageDialog(null, "Для создания заявки заполните основные данные", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        //кнопка удаления заявки на колодку
        closeRequestLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sql.SendQuery("DELETE FROM LAST_REQUEST WHERE MODEL='" + Model + "'");
                updateStatusTo("F");
                blockFirstStep(true);
            }
        });

        //кнопка завершить этап
        finishStage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkYear && !TextUtils.checkAttributePanel(headAttPanel, true) && !TextUtils.checkAttributePanel(infoAttPanel, listExeptionsForCheck)) {
                    if (!checkAllRow()) {
                        SaveParemetrList("N");
                    } else {
                        JOptionPane.showMessageDialog(ParrentForm, "Присутствуют данные отсутствующие в справочниках, дождитесь их добавления", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(ParrentForm, "Пожалуйста, убедитесь в том, что поля модели заполнены!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

    }
}
