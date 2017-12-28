package my.guisap.forms.MKZ;

import my.guisap.componenst.AttributePanel;
import my.guisap.utils.LogClass;
import my.guisap.componenst.EmptyForm;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import my.guisap.FormRegister;
import my.guisap.GuiStaticVariables;
import my.guisap.attributeRow;
import my.guisap.componenst.DataPanel;
import my.guisap.forms.ImageForm;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.ComponentsUtils;
import my.guisap.utils.CreateFormUtils;
import my.guisap.utils.ImageUtils;
import my.guisap.utils.LifeLineUtils;
import my.guisap.utils.TextUtils;

public class MkzEditModel extends EmptyForm {

    ArrayList<AttributePanel> listForCheck = new ArrayList<>();
    List<DefaultTableModel> allRowsFromSap = new ArrayList<>();

    String classFlag;
    String Model;
    Insets defaultInsets = new Insets(0, 5, 0, 10);

    boolean Update_h = false;
    int CountInRow = 0;
    int CountAddArt = 0;
    String ID_RECORD = "0";
    String IndexLast = "0";

    JPanel attPanel_all = new JPanel(new GridBagLayout());
    JPanel ArtPannel = new JPanel(new GridBagLayout());
    JScrollPane sp_ArtPannel;
    int HeightSP = 0;

    String StatusModel = "";
    JButton finishStageBtn;
    MkzMainForm ParrentForm;

    JPanel MODEL_STEP1;
    JPanel MODEL_STEP2;
    JButton addArtBtn;
    JButton hide;
    Boolean isHide = false;

    MkzEditModel linkToThis = this;

    //строка содежращая 2 последних символа модели
    String twoLastCharModelName;
    String EditingModel = "";
    StringBuilder artLeather = new StringBuilder("0000000000000");

    public ArrayList<String[]> EditingArt = new ArrayList<>();

    public ArrayList<ArrayList> ListArtInfo = new ArrayList<>();
    public ArrayList<ArrayList> ListMatUpper = new ArrayList<>();
    public ArrayList<ArrayList> ListLining = new ArrayList<>();
    public ArrayList<ArrayList> ListSole = new ArrayList<>();
    public ArrayList<ArrayList> ListHEEL = new ArrayList<>();
    public ArrayList<ArrayList> ListINSOLE = new ArrayList<>();
    public ArrayList<ArrayList> ListINSOCK = new ArrayList<>();
    public ArrayList<ArrayList> ListBACKLOTH = new ArrayList<>();
    public ArrayList<ArrayList> ListLIGHTNING = new ArrayList<>();
    public ArrayList<ArrayList> ListFindings = new ArrayList<>();
    public ArrayList<ArrayList> ListShoelace = new ArrayList<>();
    public ArrayList<ArrayList> ListThreads = new ArrayList<>();

    Map<String, Integer> mapToFormArtLth = new HashMap<String, Integer>() {
        {
            put("Z_RANK_CRAST", 0);
            put("Z_BASIS_SKIN_L", 1);
            put("Z_PROCESSING_TYPE_L", 2);
            put("Z_FACE_TINT_L", 4);
            put("Z_COLOR_BASICS_L", 6);
            put("Z_PLATE_L", 7);
            put("Z_GRINDING_L", 8);
            put("Z_COMPLEXION_BY_RALA", 9);
        }
    };

    public MkzEditModel(String caption, String classFlag, MkzMainForm ParrentForm, String Model) {

        super(caption + " " + Model, classFlag, true, false);

        setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        if (GuiStaticVariables.screenWidth > 3000) {
            CountInRow = 4;
            setPreferredSize(new Dimension(GuiStaticVariables.screenWidth - 1000, GuiStaticVariables.screenHeight - 700));
        } else {
            CountInRow = (int) GuiStaticVariables.screenWidth / 390;
        }
        this.ParrentForm = ParrentForm;

        this.Model = Model;
        this.classFlag = classFlag;

        twoLastCharModelName = "" + Model.charAt(Model.length() - 2) + Model.charAt(Model.length() - 1);
        fr = FormRegister.getInstance();
        log = LogClass.getInstance();
        createAllRowsFromSap();
        modelElemFill();
    }

    private void modelElemFill() {

        MODEL_STEP1 = new JPanel();
        MODEL_STEP2 = new JPanel(new GridBagLayout());
        int NowCount = 0;

        MODEL_STEP1 = ReadModelElem();

        sql.tableFill(SqlOperations.DATA_SELECTION_2 + "'MODEL_STEP2' and b.MAIN_CLASS = 'MODEL_STEP2' " + SqlOperations.GROUP_BY, dataModel);

        headAttPanel = new AttributePanel(dataModel, true, 0);

        for (attributeRow atR : headAttPanel.rowList) {
            MODEL_STEP2.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
            NowCount++;
        }

        MODEL_STEP1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        MODEL_STEP2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        addArtBtn = ComponentsUtils.createBtn("Добавить артикул ", 130, 23, true);
        addArtBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.openForm(new CreateArticleForm("Создание артикула", GuiStaticVariables.SALE_BW, Model, getNextArticule(), ID_RECORD, linkToThis), FormRegister.ONE_KEY_FORM);
                setCenter();
            }
        });

        //проверка на Образец (формат образца ********99)
        if (twoLastCharModelName.equals("99")) {
            addArtBtn.setEnabled(false);
        }

        hide = new JButton("Скрыть первые этапы");
        hide.setFont(GuiStaticVariables.globalFont);
        hide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isHide) {
                    hide.setText("Скрыть первые этапы");
                    MODEL_STEP1.setVisible(true);
                    MODEL_STEP2.setVisible(true);
                    isHide = false;
                } else {
                    hide.setText("Раскрыть");
                    MODEL_STEP1.setVisible(false);
                    MODEL_STEP2.setVisible(false);
                    isHide = true;
                }
            }
        });

        attPanel_all.add(hide,
                new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        attPanel_all.add(MODEL_STEP1,
                new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        attPanel_all.add(MODEL_STEP2,
                new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));

        pnlAttElem.add(attPanel_all);

        pnlSouth.add(new JLabel("   "));
        finishStageBtn = ComponentsUtils.createBtn("Завершить этап. Отправить на рассмотрение", 300, 23, true);
        finishStageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NextStage();
            }
        });
        pnlSouth.add(finishStageBtn);
        pnlSouth.add(new JLabel("             "));
        pnlSouth.add(addArtBtn);
        pnlSouth.add(Box.createHorizontalBox());
        pnlSouth.add(new JLabel("   "));

        if (!StatusModel.equals("N") && !StatusModel.equals("R_U")) {
            addArtBtn.setEnabled(false);
            finishStageBtn.setEnabled(false);
            saveButt.setEnabled(false);
            SetEnabledStepTwo(false);
            needToSave = false;
        }

        pack();

        HeightSP = getHeight();
        LoadSave();
        setCenter();
    }

    public void NextStage() {
        if (!checkAllRow()) {
            JPanel Temp;

            Save_All_MKZ(true);

            sql.SendQuery("update GUI_SAP.MKZ_LIST_HEAD set status = 'W' where ID = '" + ID_RECORD + "'");
            LifeLineUtils.UpdateLifeLine(LifeLineUtils.MKZ_LIFE_LINE, Model, "W");
            sql.SendQuery("update GUI_SAP.MKZ_MODEL_ART set status = 'W' where MODEL_ID = '" + ID_RECORD + "'");
            sql.SendQuery("commit");

            for (int i = 0; i < ArtPannel.getComponentCount(); i++) {
                Temp = ((JPanel) (ArtPannel.getComponent(i)));
                SetEnabledArt(((JLabel) Temp.getComponent(0)).getText().replace("Артикул ", ""), false);
            }

            SetEnabledStepTwo(false);
            ParrentForm.modelFormation(null);
            addArtBtn.setEnabled(false);
            finishStageBtn.setEnabled(false);
            saveButt.setEnabled(false);
            needToSave = false;

            closeWindow();
        } else {
            JOptionPane.showMessageDialog(this, "Присутствуют данные отсутствующие в справочниках, дождитесь их добавления", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void Btn_Add_Art() {
        Btn_Add_Art(null, true, "");
    }

    public void Btn_Add_Art(String NumArt, boolean This_New_Elem, String copyArt) {
        ArtPannel.add(addArtForm(NumArt),
                new GridBagConstraints(0, CountAddArt, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        if (CountAddArt == 0) {
            sp_ArtPannel = new JScrollPane(ArtPannel);
            sp_ArtPannel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            Dimension NewDimension = new Dimension(getWidth(), (int) (GuiStaticVariables.screenHeight * 0.8) - HeightSP);

            sp_ArtPannel.setMinimumSize(NewDimension);
            sp_ArtPannel.setPreferredSize(NewDimension);
            setPreferredSize(new Dimension(getWidth(), (int) (GuiStaticVariables.screenHeight * 0.9)));

            attPanel_all.add(sp_ArtPannel,
                    new GridBagConstraints(0, 4, 1, 1, 1, 10, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
        }

        if (CountAddArt != 0 && This_New_Elem && !copyArt.equals("")) {
            String Copy_art = copyArt;
            String Copy_to_art = EditingArt.get(EditingArt.size() - 1)[0];
            ArrayList<JTextField> RowElem = null;
            ArrayList<JTextField> RowElemCopy = null;
            JPanel TempPanel = null;

            for (String[] StrMas : GuiStaticVariables.listAllBlocksSketch) {
                for (int k = 0; k < ReturnListElemPanel(Integer.valueOf(StrMas[1])).size(); k++) {
                    RowElem = ReturnListElemPanel(Integer.valueOf(StrMas[1])).get(k);
                    if ((RowElem.get(0)).getText().equals(Copy_art)) {
                        if (Integer.valueOf((RowElem.get(1)).getText()) > 1) {
                            for (int j = 0; j < ArtPannel.getComponentCount(); j++) {
                                TempPanel = (JPanel) ArtPannel.getComponent(j);
                                if (((JLabel) TempPanel.getComponent(0)).getText().replace("Артикул ", "").equals(Copy_to_art)) {
                                    Btn_Add_Element(TempPanel, Integer.valueOf(StrMas[1]), ReturnListElemPanel(Integer.valueOf(StrMas[1])), StrMas[2], "true");
                                    pack();
                                }
                            }
                        }

                        for (int r = 0; r < ReturnListElemPanel(Integer.valueOf(StrMas[1])).size(); r++) {
                            RowElemCopy = ReturnListElemPanel(Integer.valueOf(StrMas[1])).get(r);
                            if ((RowElemCopy.get(0)).getText().equals(Copy_to_art)
                                    && (RowElemCopy.get(1)).getText().equals((RowElem.get(1)).getText())) {
                                for (int f = 2; f < RowElemCopy.size(); f++) {
                                    RowElemCopy.get(f).setText(RowElem.get(f).getText());
                                }
                            }
                        }
                    }
                }
            }
            Save_All_MKZ(false);
        }

        CountAddArt++;

        pack();
    }

    public void LoadSave() {
        DefaultTableModel tempData = new DefaultTableModel();
        sql.tableFill("select * from GUI_SAP.MKZ_STEP2 where id = '" + ID_RECORD + "'", tempData);
        if (tempData.getRowCount() == 1) {
            Update_h = true;
            int count = 1;
            for (attributeRow atR : headAttPanel.rowList) {
                if (tempData.getValueAt(0, count) != null) {
                    atR.txt.setText(tempData.getValueAt(0, count).toString());
                } else {
                    atR.txt.setText(" ");
                }
                count++;
            }
        } else {
            tempData = new DefaultTableModel();
            sql.tableFill("select TYPE_LAST,VALUE_PPCH,COMPLETENESS_INDEX, COMPLETENESS_SOLE from GUI_SAP.LAST_HEAD a "
                    + "left join  LAST_INFO b on a.INDEX_LAST=b.INDEX_LAST "
                    + "WHERE a.INDEX_LAST='" + IndexLast + "'", tempData);
            if (tempData.getRowCount() == 1) {
                //тип колодки
                headAttPanel.rowList.get(0).txt.setText((String) tempData.getValueAt(0, 0));
                //высота ппч
                headAttPanel.rowList.get(7).txt.setText((String) tempData.getValueAt(0, 1));
                //полнота колодки
                headAttPanel.rowList.get(4).txt.setText((String) tempData.getValueAt(0, 2));
                //высота подошвы
                headAttPanel.rowList.get(11).txt.setText((String) tempData.getValueAt(0, 3));
            }
        }

        tempData = new DefaultTableModel();
        sql.tableFill("select lpad(ART_ID,2,'0'),ART,STATUS from GUI_SAP.MKZ_MODEL_ART where model_id = '" + ID_RECORD + "' order by ART_ID", tempData);

        if (tempData.getRowCount() > 0) {

            for (int i = 0; i < tempData.getRowCount(); i++) {
                if (tempData.getValueAt(i, 1) != null) {

                    Add_Editing_Art(tempData.getValueAt(i, 1).toString(), tempData.getValueAt(i, 2).toString());
                    Btn_Add_Art(tempData.getValueAt(i, 1).toString(), false, "");
                }
            }

            for (String[] StrMas : GuiStaticVariables.listAllBlocksSketch) {
                Load_Elem(StrMas[0], Integer.valueOf(StrMas[1]), ReturnListElemPanel(Integer.valueOf(StrMas[1])), StrMas[2], StrMas[3]);
            }

            for (String[] EditingArtStr : EditingArt) {
                if (!EditingArtStr[1].equals("F")) {
                    SetEnabledArt(EditingArtStr[0], false);
                }
            }

            pack();
        }
    }

    public void Load_Elem(String Table, int NumPanel, ArrayList<ArrayList> ListElem, String NameElem, String AdditionElem) {
        DefaultTableModel tempData = new DefaultTableModel();
        String quer = "select * from GUI_SAP." + Table + " \n"
                + "where art in (select ART from GUI_SAP.MKZ_MODEL_ART where model_id = '" + ID_RECORD + "') \n"
                + "order by ART,ID";
        sql.tableFill(quer, tempData);
        JPanel TempPanel;
        for (int i = 0; i < tempData.getRowCount(); i++) {
            if (tempData.getValueAt(i, 0) != null) {
                if (Integer.valueOf(tempData.getValueAt(i, 1).toString()) > 1) {
                    for (int j = 0; j < ArtPannel.getComponentCount(); j++) {
                        TempPanel = (JPanel) ArtPannel.getComponent(j);
                        if (((JLabel) TempPanel.getComponent(0)).getText().replace("Артикул ", "").equals(tempData.getValueAt(i, 0).toString())) {
                            Btn_Add_Element(TempPanel, NumPanel, ListElem, NameElem, AdditionElem);
                        }
                    }
                }

                for (ArrayList<JTextField> RowElem : ListElem) {
                    if (RowElem.get(0).getText().equals(tempData.getValueAt(i, 0).toString())
                            && RowElem.get(1).getText().equals(tempData.getValueAt(i, 1).toString())) {
                        for (int j = 0; j < RowElem.size(); j++) {
                            if (tempData.getValueAt(i, j) == null) {
                                RowElem.get(j).setText("");
                            } else {
                                RowElem.get(j).setText(tempData.getValueAt(i, j).toString());
                            }
                        }
                    }
                }
            }
        }
    }

    public JPanel ReadModelElem() {
        int NowCount;
        JPanel OutPanel = new JPanel(new GridBagLayout());
        JPanel Element;
        DefaultTableModel dtm = new DefaultTableModel();
        sql.tableFill("select ID,YEAR,GROUPMANWOMAN,METHOD_CONSTR,SHIN_VIEW,SEASON,STYLE_MODEL,SIMILAR_MODEL,MAT_LINING,FASON_SOLE, FASON_LAST, CODE_LAST,FASON_HEEL,DESIGNER,status "
                + " from GUI_SAP.MKZ_LIST_HEAD where model = '" + Model + "'", dtm);
        ID_RECORD = dtm.getValueAt(0, 0).toString();
        IndexLast = dtm.getValueAt(0, 11).toString();
        StatusModel = dtm.getValueAt(0, 14).toString();
        String NameColumn[] = {"ID",
            "Год",
            "Половозрастная группа",
            "Метод крепления подошвы",
            "Вид обуви + материал подкладки",
            "Сезон",
            "Стиль модели",
            "Модель аналог",
            "Материал подкладки",
            "Фасон подошвы",
            "Фасон колодки",
            "Код колодки",
            "Фасон каблука",
            "Модельер"};

        JPanel imgPanel = new JPanel(new GridBagLayout());
        JPanel designPanel = new JPanel(new GridBagLayout());

        JButton img = ComponentsUtils.createBtn("Фото модели", 200, 18, true);
        JButton design = ComponentsUtils.createBtn("Эскиз", 200, 18, true);
        JButton imgAdd = ComponentsUtils.createBtn("+", 20, 18, true);
        JButton designAdd = ComponentsUtils.createBtn("+", 20, 18, true);

        imgPanel.add(img,
                new GridBagConstraints(0, 0, 1, 1, 5, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
        imgPanel.add(imgAdd,
                new GridBagConstraints(6, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
        designPanel.add(design,
                new GridBagConstraints(0, 0, 1, 1, 4, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
        designPanel.add(designAdd,
                new GridBagConstraints(5, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));

        for (int i = 0; i < dtm.getRowCount(); i++) {
            for (int j = 1; j < NameColumn.length; j++) {
                NowCount = (i * dtm.getColumnCount() + (j - 1));
                Element = new JPanel();
                Element.setLayout(new BoxLayout(Element, BoxLayout.X_AXIS));
                JLabel label = new JLabel(NameColumn[j] + " : ");
                label.setFont(GuiStaticVariables.globalFont);
                Element.add(label);
                Element.add(Box.createHorizontalGlue());
                Element.add(Box.createHorizontalStrut(GuiStaticVariables.TIGHTLE_STRUT));

                JTextField Field;
                if (dtm.getValueAt(i, j) != null) {
                    Field = new JTextField(dtm.getValueAt(i, j).toString());
                } else {
                    Field = new JTextField("");
                }

                Field.setEditable(false);
                Field.setFont(GuiStaticVariables.globalFont);
                Field.setPreferredSize(new Dimension((int) (227 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));
                Field.setMaximumSize(new Dimension((int) (227 * GuiStaticVariables.scaleWidth), (int) (23 * GuiStaticVariables.scaleHeight)));

                Element.add(Field);
                OutPanel.add(Element,
                        new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
            }

        }

        img.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                File checkFile = new File(GuiStaticVariables.DEF_PICTURES_PATH + File.separator + Model + ".jpg");
                if (checkFile.canRead()) {
                    fr.openForm(new ImageForm(Model, 0), FormRegister.SOME_KEY_FORM);
                } else {
                    int n = JOptionPane.showConfirmDialog(
                            linkToThis,
                            "У данной модели отсутствует фото, добавить?",
                            "Внимание",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        ImageUtils.openSaveImageDialog(GuiStaticVariables.DEF_PICTURES_PATH, GuiStaticVariables.DEF_PICTURESICONS_PATH, Model);
                    }
                }
            }
        });

        design.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                File checkFile = new File(GuiStaticVariables.DEF_DESIGN_PATH + File.separator + Model + ".jpg");
                if (checkFile.canRead()) {
                    fr.openForm(new ImageForm(Model, 1), FormRegister.SOME_KEY_FORM);
                } else {
                    int n = JOptionPane.showConfirmDialog(
                            linkToThis,
                            "У данной модели отсутствует эскиз, добавить?",
                            "Внимание",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        ImageUtils.openSaveImageDialog(GuiStaticVariables.DEF_DESIGN_PATH, GuiStaticVariables.DEF_DESIGNICONS_PATH, Model);
                    }
                }
            }
        });

        imgAdd.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                File checkFile = new File(GuiStaticVariables.DEF_PICTURES_PATH + File.separator + Model + ".jpg");
                if (checkFile.canRead()) {
                    int n = JOptionPane.showConfirmDialog(
                            linkToThis,
                            "У данной модели уже есть изображение, заменить?",
                            "Внимание",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        ImageUtils.openSaveImageDialog(GuiStaticVariables.DEF_PICTURES_PATH, GuiStaticVariables.DEF_PICTURESICONS_PATH, Model);
                    }
                } else {
                    ImageUtils.openSaveImageDialog(GuiStaticVariables.DEF_PICTURES_PATH, GuiStaticVariables.DEF_PICTURESICONS_PATH, Model);
                }
            }
        });

        designAdd.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                File checkFile = new File(GuiStaticVariables.DEF_DESIGN_PATH + File.separator + Model + ".jpg");
                if (checkFile.canRead()) {
                    int n = JOptionPane.showConfirmDialog(
                            linkToThis,
                            "У данной модели уже есть изображение, заменить?",
                            "Внимание",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        ImageUtils.openSaveImageDialog(GuiStaticVariables.DEF_DESIGN_PATH, GuiStaticVariables.DEF_DESIGNICONS_PATH, Model);
                    }
                } else {
                    ImageUtils.openSaveImageDialog(GuiStaticVariables.DEF_DESIGN_PATH, GuiStaticVariables.DEF_DESIGNICONS_PATH, Model);
                }
            }
        }
        );

        OutPanel.add(imgPanel,
                new GridBagConstraints(0, (int) (NameColumn.length / CountInRow) + 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 0));
        OutPanel.add(designPanel,
                new GridBagConstraints(1, (int) (NameColumn.length / CountInRow) + 1, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 0));

        return OutPanel;
    }

    //Добавление панели Артикулов
    public JPanel addArtForm(String NumArt) {
        JPanel ArtForm = new JPanel(new GridBagLayout());
        JPanel ArtInfo = new JPanel(new GridBagLayout());
        JPanel Mat_Upper = new JPanel(new GridBagLayout());
        JPanel Lining = new JPanel(new GridBagLayout());
        JPanel Sole = new JPanel(new GridBagLayout());
        JPanel Heel = new JPanel(new GridBagLayout());
        JPanel Insole = new JPanel(new GridBagLayout());
        JPanel Insock = new JPanel(new GridBagLayout());
        JPanel Backcloth = new JPanel(new GridBagLayout());
        JPanel Lightning = new JPanel(new GridBagLayout());
        JPanel Findings = new JPanel(new GridBagLayout());
        JPanel Shoelace = new JPanel(new GridBagLayout());
        JPanel Threads = new JPanel(new GridBagLayout());

        ArtForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel labelArt = new JLabel("Артикул " + NumArt);
        labelArt.setFont(new Font("Arial", 1, (int) (18 * GuiStaticVariables.scaleHeight)));

        Add_Editing_Art(NumArt, "F");

        JButton AddMatUpeerElem = new JButton("Добавить материал верха");
        AddMatUpeerElem.setFont(GuiStaticVariables.globalFont);
        AddMatUpeerElem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Btn_Add_Element((JPanel) ((JButton) (e.getSource())).getParent(), 2, ListMatUpper, "Материал верха ", "true");
            }
        });

        JButton AddLiningElem = new JButton("Добавить Подкладку");
        AddLiningElem.setFont(GuiStaticVariables.globalFont);
        AddLiningElem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Btn_Add_Element((JPanel) ((JButton) (e.getSource())).getParent(), 4, ListLining, "Подкладка ", "true");
            }
        });

        JButton AddFindingsElem = new JButton("Добавить Фурнитуру");
        AddFindingsElem.setFont(GuiStaticVariables.globalFont);
        AddFindingsElem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Btn_Add_Element((JPanel) ((JButton) (e.getSource())).getParent(), 12, ListFindings, "Фурнитура ", "true");
            }
        });

        JButton AddThreadsElem = new JButton("Добавить Нитки");
        AddThreadsElem.setFont(GuiStaticVariables.globalFont);
        AddThreadsElem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Btn_Add_Element((JPanel) ((JButton) (e.getSource())).getParent(), 15, ListThreads, "Нитки ", "true");
            }
        });

        ArtForm.add(labelArt,
                new GridBagConstraints(0, 0, 1, 1, 0.2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        ArtForm.add(ArtInfo,
                new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 0, 0), 0, 0));
        ArtForm.add(Mat_Upper,
                new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 0), 0, 0));
        ArtForm.add(AddMatUpeerElem,
                new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
        ArtForm.add(Lining,
                new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 0, 0), 0, 0));
        ArtForm.add(AddLiningElem,
                new GridBagConstraints(0, 5, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
        ArtForm.add(Sole,
                new GridBagConstraints(0, 6, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 0, 0), 0, 0));
        ArtForm.add(Heel,
                new GridBagConstraints(0, 7, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 0, 0), 0, 0));
        ArtForm.add(Insole,
                new GridBagConstraints(0, 8, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 0, 0), 0, 0));
        ArtForm.add(Insock,
                new GridBagConstraints(0, 9, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 0, 0), 0, 0));
        ArtForm.add(Backcloth,
                new GridBagConstraints(0, 10, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 0, 0), 0, 0));
        ArtForm.add(Lightning,
                new GridBagConstraints(0, 11, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 0, 0), 0, 0));
        ArtForm.add(Findings,
                new GridBagConstraints(0, 12, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 0, 0), 0, 0));
        ArtForm.add(AddFindingsElem,
                new GridBagConstraints(0, 13, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
        ArtForm.add(Shoelace,
                new GridBagConstraints(0, 14, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 0, 0), 0, 0));
        ArtForm.add(Threads,
                new GridBagConstraints(0, 15, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 0, 0), 0, 0));
        ArtForm.add(AddThreadsElem,
                new GridBagConstraints(0, 16, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));

        for (String[] StrMas : GuiStaticVariables.listAllBlocksSketch) {
            Btn_Add_Element(ArtForm, Integer.valueOf(StrMas[1]), ReturnListElemPanel(Integer.valueOf(StrMas[1])), StrMas[2], StrMas[3]);
        }
        return ArtForm;
    }

    public void Add_Editing_Art(String Art, String Record) {
        int replaceNum = 0;
        for (String[] StrMas : EditingArt) {
            if (StrMas.equals(Art)) {
                EditingArt.remove(replaceNum);
                EditingArt.add(replaceNum, new String[]{Art, Record});
                return;
            }
            replaceNum++;
        }

        EditingArt.add(new String[]{Art, Record});
    }

    public JPanel ReturnAddingPanel(int num, String NameArt, int numElem) {
        switch (num) {
            case 1:
                return addArtInfo(NameArt, numElem);
            case 2:
                return addMatUpper(NameArt, numElem);
            case 4:
                return addLining(NameArt, numElem);
            case 6:
                return addSole(NameArt, numElem);
            case 7:
                return addHEEL(NameArt, numElem);
            case 8:
                return addInsole(NameArt, numElem);
            case 9:
                return addInsock(NameArt, numElem);
            case 10:
                return addBackcloth(NameArt, numElem);
            case 11:
                return addLightning(NameArt, numElem);
            case 12:
                return addFindings(NameArt, numElem);
            case 14:
                return addShoelace(NameArt, numElem);
            case 15:
                return addThreads(NameArt, numElem);
            default:
                return new JPanel();
        }
    }

    public ArrayList<ArrayList> ReturnListElemPanel(int num) {
        switch (num) {
            case 1:
                return ListArtInfo;
            case 2:
                return ListMatUpper;
            case 4:
                return ListLining;
            case 6:
                return ListSole;
            case 7:
                return ListHEEL;
            case 8:
                return ListINSOLE;
            case 9:
                return ListINSOCK;
            case 10:
                return ListBACKLOTH;
            case 11:
                return ListLIGHTNING;
            case 12:
                return ListFindings;
            case 14:
                return ListShoelace;
            case 15:
                return ListThreads;
            default:
                return new ArrayList<>();
        }
    }

    ;

   public void Btn_Add_Element(JPanel HeadPanel, int NumAddingPanel, ArrayList<ArrayList> ListElem, String NameElem, String AddNumNameElem) {
        JPanel PanelElem = (JPanel) HeadPanel.getComponent(NumAddingPanel);
        String Art_Name = ((JLabel) HeadPanel.getComponent(0)).getText().replace("Артикул ", "");
        int num_Elem = 1;

        for (ArrayList<JTextField> RowElem : ListElem) {
            if (RowElem.get(0).getText().equals(Art_Name)) {
                num_Elem = Integer.valueOf(RowElem.get(1).getText()) + 1;
            }
        }

        JPanel jpAddElement = new JPanel(new GridBagLayout());
        JLabel labelNameElem;
        if (AddNumNameElem.equals("true")) {
            labelNameElem = new JLabel(NameElem + String.valueOf(num_Elem));
        } else {
            labelNameElem = new JLabel(NameElem);
        }
        labelNameElem.setFont(new Font("Arial", 1, (int) (13 * GuiStaticVariables.scaleHeight)));

        jpAddElement.add(labelNameElem,
                new GridBagConstraints(0, 0, 1, 1, 0.2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        jpAddElement.add(ReturnAddingPanel(NumAddingPanel, Art_Name, num_Elem),
                new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        PanelElem.add(jpAddElement,
                new GridBagConstraints(0, num_Elem - 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        pack();
        setCenter();
    }

    public JPanel addArtInfo(String NameArt, int NumElem) {
        JPanel PanelElem = new JPanel(new GridBagLayout());
        int NowCount = 0;
        AttributePanel addPanel;
        ArrayList<JTextField> RowElem = new ArrayList<>();

        RowElem.add(new JTextField(NameArt));
        RowElem.add(new JTextField(String.valueOf(NumElem)));

        addPanel = new AttributePanel(allRowsFromSap.get(13), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);
        ListArtInfo.add(RowElem);

        return PanelElem;
    }

    //Добавление панели материала подкладки
    public JPanel addMatUpper(String NameArt, int NumMatUpper) {
        JPanel MatUpper = new JPanel(new GridBagLayout());
        int NowCount = 0;
        JPanel jp_temp;
        AttributePanel addPanel;
        ArrayList<JTextField> RowMatUpper = new ArrayList<>();

        RowMatUpper.add(new JTextField(NameArt));
        RowMatUpper.add(new JTextField(String.valueOf(NumMatUpper)));

        //задаем поля без подгрузки из SAP
        String LastMasElem[][] = {
            {"Артикул пленки 1", "true"},
            {"Артикул пленки 2", "true"},
            {"Перфорация лазер", "true"},
            {"Перфорация плитой", "true"}};

        addPanel = new AttributePanel(allRowsFromSap.get(0), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowMatUpper.add(atR.txt);
            MatUpper.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        //вывод полей из массива
        jp_temp = CreateFormUtils.CreateElem("Артикул кожи", null, "false");
        final JTextField artLthField = (JTextField) (jp_temp.getComponent(3));
        RowMatUpper.add((JTextField) (jp_temp.getComponent(3)));
        MatUpper.add(jp_temp,
                new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
        NowCount++;

        addPanel = new AttributePanel(allRowsFromSap.get(1), true, 0);
        for (final attributeRow atR : addPanel.rowList) {
            RowMatUpper.add(atR.txt);
            MatUpper.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
            //поле PRINT не участвует в формировании артикула кожи
            if (!atR.atnam.equals("PRINT")) {
                atR.txt.addCaretListener(new CaretListener() {
                    @Override
                    public void caretUpdate(CaretEvent e) {
                        if (!atR.txt.getText().isEmpty()) {
                            SetArticleLth(atR.atprt, atR.txt.getText(), artLthField);
                        }
                    }
                });
            }
        }

        listForCheck.add(addPanel);

        CreateFormUtils.CreatePanelOfTextFields(LastMasElem, RowMatUpper, MatUpper, NowCount, CountInRow, defaultInsets);
        createCostRow(MatUpper, RowMatUpper, NowCount, "(дм2)");

        ListMatUpper.add(RowMatUpper);
        return MatUpper;
    }

    //добавление подкладки
    public JPanel addLining(String NameArt, int NumElem) {
        JPanel PanelElem = new JPanel(new GridBagLayout());
        int NowCount = 0;
        AttributePanel addPanel;
        ArrayList<JTextField> RowElem = new ArrayList<>();

        RowElem.add(new JTextField(NameArt));
        RowElem.add(new JTextField(String.valueOf(NumElem)));

        String LastMasElem[][] = {
            {"Артикул поставщика", "true"}};

        addPanel = new AttributePanel(allRowsFromSap.get(2), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        CreateFormUtils.CreatePanelOfTextFields(LastMasElem, RowElem, PanelElem, NowCount, CountInRow, defaultInsets);
        createCostRow(PanelElem, RowElem, NowCount, "(дм2)");

        ListLining.add(RowElem);

        return PanelElem;
    }

    public JPanel addSole(String NameArt, int NumElem) {
        JPanel PanelElem = new JPanel(new GridBagLayout());
        int NowCount = 0;
        JPanel jp_temp;
        AttributePanel addPanel;
        ArrayList<JTextField> RowElem = new ArrayList<>();

        RowElem.add(new JTextField(NameArt));
        RowElem.add(new JTextField(String.valueOf(NumElem)));

        String FirstMasElem[][] = {
            {"Фасон подошвы", "false"},
            {"Артикул подошвы ", "true"}};

        //создание поля с данными о поставщике из справочника 
        addPanel = new AttributePanel(allRowsFromSap.get(3), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        addPanel = new AttributePanel(allRowsFromSap.get(4), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        for (int i = 0; i < FirstMasElem.length; i++) {
            jp_temp = CreateFormUtils.CreateElem(FirstMasElem[i][0], null, FirstMasElem[i][1]);
            RowElem.add((JTextField) (jp_temp.getComponent(3)));

            if (FirstMasElem[i][0].equals("Фасон подошвы")) {
                ((JTextField) (jp_temp.getComponent(3))).setText(headAttPanel.rowList.get(1).txt.getText());
            }

            PanelElem.add(jp_temp,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        createCostRow(PanelElem, RowElem, NowCount, "(пара)");

        ListSole.add(RowElem);

        return PanelElem;
    }

    public JPanel addHEEL(String NameArt, int NumElem) {
        JPanel PanelElem = new JPanel(new GridBagLayout());
        int NowCount = 0;
        JPanel jp_temp;
        AttributePanel addPanel;
        ArrayList<JTextField> RowElem = new ArrayList<>();

        RowElem.add(new JTextField(NameArt));
        RowElem.add(new JTextField(String.valueOf(NumElem)));

        String FirstMasElem[][] = {
            {"Фасон каблука", "false"},
            {"Артикул каблука ", "true"}};

        //создание поля с данными о поставщике из справочника 
        addPanel = new AttributePanel(allRowsFromSap.get(3), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        //создание поля с данными о поставщике из справочника 
        addPanel = new AttributePanel(allRowsFromSap.get(5), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        for (int i = 0; i < FirstMasElem.length; i++) {
            jp_temp = CreateFormUtils.CreateElem(FirstMasElem[i][0], null, FirstMasElem[i][1]);
            RowElem.add((JTextField) (jp_temp.getComponent(3)));

            if (FirstMasElem[i][0].equals("Фасон каблука")) {
                ((JTextField) (jp_temp.getComponent(3))).setText(headAttPanel.rowList.get(3).txt.getText());
            }

            PanelElem.add(jp_temp,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        createCostRow(PanelElem, RowElem, NowCount, "(пара)");

        ListHEEL.add(RowElem);

        return PanelElem;
    }

    public JPanel addInsole(String NameArt, int NumElem) {
        JPanel PanelElem = new JPanel(new GridBagLayout());
        int NowCount = 0;
        JPanel jp_temp;
        AttributePanel addPanel;
        ArrayList<JTextField> RowElem = new ArrayList<>();

        RowElem.add(new JTextField(NameArt));
        RowElem.add(new JTextField(String.valueOf(NumElem)));

        String FirstMasElem[][] = {
            {"Фасон основной стельки", "true"},
            {"Доп. Описание ", "true"}};

        //создание поля с данными о поставщике из справочника 
        addPanel = new AttributePanel(allRowsFromSap.get(3), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        addPanel = new AttributePanel(allRowsFromSap.get(6), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        CreateFormUtils.CreatePanelOfTextFields(FirstMasElem, RowElem, PanelElem, NowCount, CountInRow, defaultInsets);
        createCostRow(PanelElem, RowElem, NowCount, "(пара)");

        ListINSOLE.add(RowElem);

        return PanelElem;
    }

    public JPanel addInsock(String NameArt, int NumElem) {
        JPanel PanelElem = new JPanel(new GridBagLayout());
        int NowCount = 0;
        JPanel jp_temp;
        AttributePanel addPanel;
        ArrayList<JTextField> RowElem = new ArrayList<>();

        RowElem.add(new JTextField(NameArt));
        RowElem.add(new JTextField(String.valueOf(NumElem)));

        String FirstMasElem[][] = {
            {"Фасон вкладной стельки", "true"},
            {"Доп. Описание ", "true"}};

        //создание поля с данными о поставщике из справочника 
        addPanel = new AttributePanel(allRowsFromSap.get(3), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        addPanel = new AttributePanel(allRowsFromSap.get(7), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        CreateFormUtils.CreatePanelOfTextFields(FirstMasElem, RowElem, PanelElem, NowCount, CountInRow, defaultInsets);
        createCostRow(PanelElem, RowElem, NowCount, "(пара)");

        ListINSOCK.add(RowElem);

        return PanelElem;
    }

    public JPanel addBackcloth(String NameArt, int NumElem) {
        JPanel PanelElem = new JPanel(new GridBagLayout());
        int NowCount = 0;
        JPanel jp_temp;
        AttributePanel addPanel;
        ArrayList<JTextField> RowElem = new ArrayList<>();

        RowElem.add(new JTextField(NameArt));
        RowElem.add(new JTextField(String.valueOf(NumElem)));

        String FirstMasElem[][] = {
            {"Фасон задника", "true"},
            {"Доп. Описание ", "true"}};

        //создание поля с данными о поставщике из справочника 
        addPanel = new AttributePanel(allRowsFromSap.get(3), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        addPanel = new AttributePanel(allRowsFromSap.get(8), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        CreateFormUtils.CreatePanelOfTextFields(FirstMasElem, RowElem, PanelElem, NowCount, CountInRow, defaultInsets);
        createCostRow(PanelElem, RowElem, NowCount, "(пара)");

        ListBACKLOTH.add(RowElem);

        return PanelElem;
    }

    public JPanel addLightning(String NameArt, int NumElem) {
        JPanel PanelElem = new JPanel(new GridBagLayout());
        int NowCount = 0;
        JPanel jp_temp;
        AttributePanel addPanel;
        ArrayList<JTextField> RowElem = new ArrayList<>();

        RowElem.add(new JTextField(NameArt));
        RowElem.add(new JTextField(String.valueOf(NumElem)));

        String FirstMasElem[][] = {
            {"Фасон молнии", "true"},
            {"Артикул ", "true"},
            {"Длина ", "true"}};

        //создание поля с данными о поставщике из справочника 
        addPanel = new AttributePanel(allRowsFromSap.get(3), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        addPanel = new AttributePanel(allRowsFromSap.get(9), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        CreateFormUtils.CreatePanelOfTextFields(FirstMasElem, RowElem, PanelElem, NowCount, CountInRow, defaultInsets);
        createCostRow(PanelElem, RowElem, NowCount, "(пара)");

        ListLIGHTNING.add(RowElem);

        return PanelElem;
    }

    public JPanel addFindings(String NameArt, int NumElem) {
        JPanel PanelElem = new JPanel(new GridBagLayout());
        int NowCount = 0;
        JPanel jp_temp;
        AttributePanel addPanel;
        ArrayList<JTextField> RowElem = new ArrayList<>();

        RowElem.add(new JTextField(NameArt));
        RowElem.add(new JTextField(String.valueOf(NumElem)));

        String FirstMasElem[][] = {
            {"Артикул фурнитуры", "true"},
            {"Доп. Описание ", "true"}};

        //создание поля с данными о поставщике из справочника 
        addPanel = new AttributePanel(allRowsFromSap.get(3), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        addPanel = new AttributePanel(allRowsFromSap.get(10), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        CreateFormUtils.CreatePanelOfTextFields(FirstMasElem, RowElem, PanelElem, NowCount, CountInRow, defaultInsets);
        createCostRow(PanelElem, RowElem, NowCount, "(шт)");

        ListFindings.add(RowElem);
        return PanelElem;
    }

    public JPanel addShoelace(String NameArt, int NumElem) {
        JPanel PanelElem = new JPanel(new GridBagLayout());
        int NowCount = 0;
        JPanel jp_temp;
        AttributePanel addPanel;
        ArrayList<JTextField> RowElem = new ArrayList<>();

        RowElem.add(new JTextField(NameArt));
        RowElem.add(new JTextField(String.valueOf(NumElem)));

        String FirstMasElem[][] = {
            {"Артикул шнурка", "true"},
            {"Длина", "true"}};

        //создание поля с данными о поставщике из справочника 
        addPanel = new AttributePanel(allRowsFromSap.get(3), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        addPanel = new AttributePanel(allRowsFromSap.get(11), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        CreateFormUtils.CreatePanelOfTextFields(FirstMasElem, RowElem, PanelElem, NowCount, CountInRow, defaultInsets);
        createCostRow(PanelElem, RowElem, NowCount, "(пара)");

        ListShoelace.add(RowElem);

        return PanelElem;
    }

    public JPanel addThreads(String NameArt, int NumElem) {
        JPanel PanelElem = new JPanel(new GridBagLayout());
        int NowCount = 0;
        AttributePanel addPanel;
        ArrayList<JTextField> RowElem = new ArrayList<>();

        RowElem.add(new JTextField(NameArt));
        RowElem.add(new JTextField(String.valueOf(NumElem)));

        String LastMasElem[][] = {
            {"Артикул поставщика", "true"},
            {"Толщина", "true"}};

        //создание поля с данными о поставщике из справочника 
        addPanel = new AttributePanel(allRowsFromSap.get(3), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        addPanel = new AttributePanel(allRowsFromSap.get(12), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        CreateFormUtils.CreatePanelOfTextFields(LastMasElem, RowElem, PanelElem, NowCount, CountInRow, defaultInsets);
        createCostRow(PanelElem, RowElem, NowCount, "(м)");

        ListThreads.add(RowElem);

        return PanelElem;
    }

    public JPanel addPocket(String NameArt, int NumElem) {
        JPanel PanelElem = new JPanel(new GridBagLayout());
        int NowCount = 0;
        AttributePanel addPanel;
        ArrayList<JTextField> RowElem = new ArrayList<>();

        RowElem.add(new JTextField(NameArt));
        RowElem.add(new JTextField(String.valueOf(NumElem)));

        //создание поля с данными о поставщике из справочника 
        addPanel = new AttributePanel(allRowsFromSap.get(3), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        addPanel = new AttributePanel(allRowsFromSap.get(14), true, 0);

        for (attributeRow atR : addPanel.rowList) {
            RowElem.add(atR.txt);
            PanelElem.add(atR,
                    new GridBagConstraints(NowCount % CountInRow, (int) NowCount / CountInRow, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            NowCount++;
        }

        listForCheck.add(addPanel);

        createCostRow(PanelElem, RowElem, NowCount, "(шт)");

        ListThreads.add(RowElem);

        return PanelElem;
    }

    //Сохранeние данных
    @Override
    public void saveActionPerformed(java.awt.event.ActionEvent evt) {
        Save_All_MKZ(true);
    }

    public void Save_All_MKZ(boolean SendMessage) {
        int MassCount = headAttPanel.rowList.size() + 2;
        String query;

        if (Update_h) {
            query = "update GUI_SAP.MKZ_STEP2 set "
                    + "(ID, PADS_TYPE, TYPE_SOLE, TYPE_HEEL, TYPE_CONSTRUCTION, COMPLETENESS_PADS, COMPLETENESS_SHOES, KIND_ADJUSTMENT, HEIGHT_HEEL_PART, HEIGHT_HEEL_SOLE, SHAFT, SIZE_SHAFT, HEIGHT_SOLE, KIND_ADJUSTMENT_READY) = "
                    + "(select";
        } else {
            query = "insert into GUI_SAP.MKZ_STEP2 "
                    + "(ID, PADS_TYPE, TYPE_SOLE,  TYPE_HEEL, TYPE_CONSTRUCTION, COMPLETENESS_PADS, COMPLETENESS_SHOES, KIND_ADJUSTMENT, HEIGHT_HEEL_PART, HEIGHT_HEEL_SOLE, SHAFT, SIZE_SHAFT, HEIGHT_SOLE, KIND_ADJUSTMENT_READY) values"
                    + "( ";
        }

        String[] data = new String[MassCount];
        int count = 0;

        data[count] = ID_RECORD;
        count++;

        for (attributeRow atR : headAttPanel.rowList) {
            data[count] = atR.txt.getText();
            count++;
        }

        for (int i = 0; i < count; i++) {
            if (i != count - 1) {
                query += "'" + data[i] + "',";
            } else {
                query += "'" + data[i] + "'";
            }
        }

        if (Update_h) {
            query += " from dual)";
        } else {
            query += " )";
        }

        if (Update_h) {
            query += " where ID = '" + ID_RECORD + "'";
        }

        sql.SendQuery(query);

        Update_h = true;

        for (String[] StrMas : GuiStaticVariables.listAllBlocksSketch) {
            Save_MKZ_List_Elem(StrMas[0], ReturnListElemPanel(Integer.valueOf(StrMas[1])));
        }

        JOptionPane.showMessageDialog(this,
                "Модельные данные сохранены.", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        needToSave = false;

        if (SendMessage) {
            log.logWriting("Заполение модели " + Model + " окончено.");
            saveArt();
        }
    }

    public boolean Save_MKZ_List_Elem(String Table, ArrayList<ArrayList> ListElem) {
        int CountMas;
        String query;
        DefaultTableModel tempData = new DefaultTableModel();

        sql.tableFill("select column_names from \n"
                + "(select column_id,ltrim(sys_connect_by_path(Column_Name, ','),',') as Column_Names from\n"
                + "(select  t.column_id,t.COLUMN_NAME,lag(t.COLUMN_NAME) over (order by t.column_id ) as COLUMN_NAME_1 from \n"
                + "(select column_name,column_id from all_tab_columns\n"
                + "where table_name = upper('" + Table + "')\n"
                + "and owner = 'GUI_SAP' order by column_id) t\n"
                + ")\n"
                + "start with Column_Name_1 is null\n"
                + "connect by Column_Name_1 = prior COLUMN_NAME order by column_id DESC) where rownum = 1", tempData);

        if (tempData.getValueAt(0, 0) != null) {
            query = tempData.getValueAt(0, 0).toString();
        } else {
            return false;
        }

        String QuerIns = "insert into GUI_SAP." + Table + "(" + query + ") (select ?1? from dual)";
        String QuerUpd = "update GUI_SAP." + Table + " set (" + query + ") = (select ?1? from dual) where ART = '?2?' and ID = '?3?'";
        String tmpQuer = "";
        for (ArrayList<JTextField> RowElem : ListElem) {
            CountMas = RowElem.size();
            tmpQuer = "";
            for (int i = 0; i < CountMas; i++) {
                if (CountMas - 1 != i) {
                    tmpQuer += "'" + RowElem.get(i).getText() + "',";
                } else {
                    tmpQuer += "'" + RowElem.get(i).getText() + "'";
                }
            }

            tempData = new DefaultTableModel();
            sql.tableFill("select * from GUI_SAP." + Table + " where art = '" + RowElem.get(0).getText() + "' and id = '" + RowElem.get(1).getText() + "'", tempData);

            if (tempData.getRowCount() > 0) {
                query = QuerUpd.replace("?1?", tmpQuer).replace("?2?", RowElem.get(0).getText()).replace("?3?", RowElem.get(1).getText());
            } else {
                query = QuerIns.replace("?1?", tmpQuer);
            }

            sql.SendQuery(query);
        }

        return true;
    }

    private void saveArt() {
        DefaultTableModel tempData = new DefaultTableModel();
        sql.tableFill("select ART_ID,ART,STATUS from GUI_SAP.MKZ_MODEL_ART where model_id = '" + ID_RECORD + "' order by ART_ID", tempData);
        for (int i = 0; i < ListArtInfo.size(); i++) {
            sql.SendQuery("update GUI_SAP.MKZ_MODEL_ART set COLOR = '" + ((ArrayList<JTextField>) ListArtInfo.get(i)).get(2).getText() + "',"
                    + "GROUPE_MAT_UPPER = '" + ((ArrayList<JTextField>) ListArtInfo.get(i)).get(3).getText() + "',"
                    + "MAT_UPPER = '" + ((ArrayList<JTextField>) ListArtInfo.get(i)).get(4).getText() + "'"
                    + " where MODEL_ID = '" + ID_RECORD + "' and "
                    + " ART = '" + ((ArrayList<JTextField>) ListArtInfo.get(i)).get(0).getText() + "'");
        }
    }

    public void fillArt(DataPanel data, String art) {
        for (ArrayList<JTextField> listFields : ListArtInfo) {
            if (listFields.get(0).getText().equals(art)) {
                listFields.get(2).setText(data.getTextField(1).getText());
                listFields.get(3).setText(data.getTextField(2).getText());
                listFields.get(4).setText(data.getTextField(3).getText());
            }
        }
    }

//получение следующего номера артикула
    private String getNextArticule() {
        DefaultTableModel tempData = new DefaultTableModel();
        sql.tableFill("select lpad(ART_ID+1,2,'0'),ART,STATUS from GUI_SAP.MKZ_MODEL_ART where model_id = '" + ID_RECORD + "' order by ART_ID", tempData);
        //если у модели еще нет артикула
        if (tempData.getRowCount() == 0) {
            return "01";
        } else {
            return tempData.getValueAt(tempData.getRowCount() - 1, 0).toString();
        }
    }

    private void createAllRowsFromSap() {
        //Материалы верха индекс:0,1
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION_2 + "'UPPER_MATERIAL' and b.MAIN_CLASS = 'UPPER_MATERIAL' and b.PRIORITY<5" + SqlOperations.GROUP_BY));
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION_2 + "'UPPER_MATERIAL' and b.MAIN_CLASS = 'UPPER_MATERIAL' and b.PRIORITY>=5" + SqlOperations.GROUP_BY));
        //Подкладка индекс:2
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION_2 + "'LINING' and b.MAIN_CLASS = 'LINING'" + SqlOperations.GROUP_BY));
        //Поставщик индекс: 3
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION_2 + "'FOR_ALL' and b.MAIN_CLASS = 'FOR_ALL' " + SqlOperations.GROUP_BY));
        //Подошва индекс:4
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION_2 + "'SOLE' and b.MAIN_CLASS = 'SOLE' " + SqlOperations.GROUP_BY));
        //Каблук индекс:5
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION_2 + "'HEEL' and b.MAIN_CLASS = 'HEEL' " + SqlOperations.GROUP_BY));
        //Стелька индекс:6
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION_2 + "'INSOLE' and b.MAIN_CLASS = 'INSOLE' " + SqlOperations.GROUP_BY));
        //Стелька индекс:7
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION_2 + "'INSOCK' and b.MAIN_CLASS = 'INSOCK' " + SqlOperations.GROUP_BY));
        //Задники индекс:8
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION_2 + "'BACK' and b.MAIN_CLASS = 'BACK' " + SqlOperations.GROUP_BY));
        //Молникя индекс:9
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION_2 + "'ZIPPER' and b.MAIN_CLASS = 'ZIPPER' " + SqlOperations.GROUP_BY));
        //Фурнитура индекс:10
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION_2 + "'DECORATION' and b.MAIN_CLASS = 'DECORATION' " + SqlOperations.GROUP_BY));
        //Шнурки индекс:11
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION_2 + "'TIE' and b.MAIN_CLASS = 'TIE' " + SqlOperations.GROUP_BY));
        //Нитки индекс: 12
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION_2 + "'MKZ_THREADS' and b.MAIN_CLASS = 'MKZ_THREADS' " + SqlOperations.GROUP_BY));
        //Артикл индекс: 13
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION + "'NEW_ART' and MAIN_CLASS = 'NEW_ART'" + SqlOperations.GROUP_BY));
        //Карман: 14
        allRowsFromSap.add(createBlockRows(SqlOperations.DATA_SELECTION + "'POCKET' and MAIN_CLASS = 'POCKET'" + SqlOperations.GROUP_BY));
    }

    private DefaultTableModel createBlockRows(String query) {
        DefaultTableModel outBaseModel = new DefaultTableModel();
        sql.tableFill(query, outBaseModel);
        return outBaseModel;
    }

    private boolean checkAllRow() {
        if (TextUtils.checkAttributePanel(headAttPanel, true)) {
            return true;
        }
//        for (AttributePanel atP : listForCheck) {
//            if (TextUtils.checkAttributePanel(atP, false)) {
//                return true;
//            }
//        }
        return false;
    }

    private void SetEnabledStepTwo(boolean EnabledValue) {
        for (attributeRow atR : headAttPanel.rowList) {
            atR.btn.setEnabled(EnabledValue);
        }
    }

    private void SetEnabledArt(String NameArt, boolean EnabledValue) {
        JPanel Temp;
        for (String[] ListElemStr : GuiStaticVariables.listAllBlocksSketch) {
            for (ArrayList<JTextField> RowElem : ReturnListElemPanel(Integer.valueOf(ListElemStr[1]))) {
                if (RowElem.get(0).getText().equals(NameArt)) {
                    for (JTextField FieldElem : RowElem) {
                        FieldElem.setEnabled(EnabledValue);
                        if (FieldElem.getParent() instanceof JPanel) {
                            Temp = ((JPanel) FieldElem.getParent());
                            if (Temp.getComponentCount() > 4) {
                                if (Temp.getComponent(4) instanceof JButton) {
                                    ((JButton) ((JPanel) FieldElem.getParent()).getComponent(4)).setEnabled(EnabledValue);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < ArtPannel.getComponentCount(); i++) {
            Temp = ((JPanel) (ArtPannel.getComponent(i)));
            if (((JLabel) Temp.getComponent(0)).getText().replace("Артикул ", "").equals(NameArt)) {
                for (int j = 0; j < Temp.getComponentCount(); j++) {
                    Temp.getComponent(j).setEnabled(EnabledValue);
                }
            }
        }
    }

    private void SetArticleLth(String tableName, String valueToSearch, JTextField artLthField) {
        DefaultTableModel tmp = new DefaultTableModel();
        String query = "select CODE from SAPX_" + tableName + " where NAME='" + valueToSearch + "'";
        sql.tableFill(query, tmp);
        if (tmp.getRowCount() > 0) {
            if (tmp.getValueAt(0, 0).toString().length() >= 4) {
                if (tmp.getValueAt(0, 0).toString().length() == 4) {
                    artLeather.setLength(13);
                }
                artLeather.replace(mapToFormArtLth.get(tableName), mapToFormArtLth.get(tableName) + tmp.getValueAt(0, 0).toString().length(), tmp.getValueAt(0, 0).toString().substring(3));
            } else {
                artLeather.replace(mapToFormArtLth.get(tableName), mapToFormArtLth.get(tableName) + tmp.getValueAt(0, 0).toString().length(), tmp.getValueAt(0, 0).toString());
            }
            artLthField.setText(artLeather.toString());
        } else {
            artLthField.setText("");
        }
    }

    private void createCostRow(JPanel panel, ArrayList<JTextField> rowPanel, int curentCount, String units) {

        JPanel tmpJPanel;
        String costElem[][] = {
            {"Расход " + units, "true"},
            {"Цена ($ " + units + " )", "true"},
            {"Итого стоимость", "false"}};

        int tmpPos = (curentCount / CountInRow) + 1;

        JLabel head = new JLabel("Расчет стоимости:");
        head.setFont(new Font("Arial", 1, (int) (12 * GuiStaticVariables.scaleHeight)));

        panel.add(head,
                new GridBagConstraints(0, tmpPos++, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));

        //ввод полей из массива
        for (int i = 0; i < costElem.length; i++) {
            tmpJPanel = CreateFormUtils.CreateElem(costElem[i][0], null, costElem[i][1]);
            rowPanel.add((JTextField) (tmpJPanel.getComponent(3)));

            if (costElem[i][0].equals("Расход" + units) || costElem[i][0].equals("Цена ($ " + units + " )")) {
                ((JTextField) (tmpJPanel.getComponent(3))).addCaretListener(new CaretListener() {
                    @Override
                    public void caretUpdate(CaretEvent e) {
                        JTextField tField = ((JTextField) e.getSource());
                        int componentCount = tField.getParent().getParent().getComponentCount() - 3;
                        JTextField OutGo = (JTextField) ((JPanel) (tField.getParent().getParent().getComponent(componentCount++))).getComponent(3);
                        JTextField Price = (JTextField) ((JPanel) (tField.getParent().getParent().getComponent(componentCount++))).getComponent(3);
                        JTextField Result = (JTextField) ((JPanel) (tField.getParent().getParent().getComponent(componentCount))).getComponent(3);
                        double OutPutValue = 0;
                        if (OutGo.getText().length() > 0 && Price.getText().length() > 0) {
                            try {
                                OutPutValue = Double.valueOf(OutGo.getText().replace(",", ".")) * Double.valueOf(Price.getText().replace(",", "."));
                                Result.setText(String.valueOf(BigDecimal.valueOf(OutPutValue).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue()));
                            } catch (java.lang.NumberFormatException ex) {

                            }
                        }

                    }
                });
            }
            panel.add(tmpJPanel,
                    new GridBagConstraints(i, tmpPos, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
            curentCount++;
        }
    }

}
