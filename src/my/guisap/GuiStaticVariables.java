package my.guisap;

import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 *
 * @author DaN
 */
public class GuiStaticVariables {

    public static final int INDENT_STRUT = 12;
    public static final int ELEMENT_STRUT = 12;
    public static final int TIGHTLE_STRUT = 6;
    public static final String FASON_ATNAM = "CODE_LAST";
    public static final String MODEL_ATNAM = "MODEL";
    public static final String ARTICLE_ATNAM = "ART";
    public static final String TECHN_ATNAM = "CONSTR_BOTTOM";
    public static final String SALE_BW = "SALE_BW";
    public static final String NEW_MOD = "NEW_MOD";
    public static final String MODEL_NUMBER = "MODEL_NUMBER";
    public static final String SALE_HW1 = "SALE_HW1";
    public static final String SALE_SHOES = "SALE_SHOES";
    public static final String LAST = "LAST";
    public static final String LAST_REQUEST = "LAST_REQUEST";
    public static final int FIRST_ELEMENT = 0;
    public static final String SEPARATOR = System.getProperty("file.separator");
    public static final String HOME_DIRECTORY = System.getProperty("user.home");
    public static final String CONFIGS_DIRECTORY = HOME_DIRECTORY + SEPARATOR + "GuiPropertis" + SEPARATOR;
    public static final String DEF_PICTURES_PATH = SEPARATOR + SEPARATOR + "192.168.0.2" + SEPARATOR + "pub" + SEPARATOR + "ARTIK" + SEPARATOR + "NSI" + SEPARATOR + "MODEL";
    public static final String DEF_DESIGN_PATH = SEPARATOR + SEPARATOR + "192.168.0.2" + SEPARATOR + "pub" + SEPARATOR + "ARTIK" + SEPARATOR + "NSI" + SEPARATOR + "DESIGN";

    public static final String DEF_PICTURESICONS_PATH = SEPARATOR + SEPARATOR + "192.168.0.2" + SEPARATOR + "pub" + SEPARATOR + "ARTIK" + SEPARATOR + "NSI" + SEPARATOR + "MODEL" + SEPARATOR + "ICONS";
    public static final String DEF_DESIGNICONS_PATH = SEPARATOR + SEPARATOR + "192.168.0.2" + SEPARATOR + "pub" + SEPARATOR + "ARTIK" + SEPARATOR + "NSI" + SEPARATOR + "DESIGN" + SEPARATOR + "ICONS";

    public static String picturesPATH = "";
    public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final double DefScaleWidth = (double) ((double) screenWidth / (double) 1920);
    public static final double DefScaleHeight = (double) ((double) screenHeight / (double) 1080);
    public static double scaleWidth = DefScaleWidth;
    public static double scaleHeight = DefScaleHeight;
    public static double fontScale = 0;
    public static String lastPathToLoadImage = "";

    /**
     * Версия приложения
     */
    public static String VERSIONAPP = "2.0(050118-0)";
    public static String ACTUALVERSIONAPP = "";
    public static Font globalFont = new Font("Arial", 0, 12);

    static {
        if (screenHeight > 1200) {
            globalFont = new Font("Arial", 0, (int) (12 * GuiStaticVariables.scaleHeight));
        }
    }

    public static boolean SETTIN_LOCATION_ON_MOUSE = true;

    public static final ArrayList<String[]> listAllBlocksSketch = new ArrayList<>();

    static {

        listAllBlocksSketch.add(new String[]{"MKZ_MODEL_ART", "1", "Информация об артикуле ", "false", " "});
        listAllBlocksSketch.add(new String[]{"MKZ_MAT_UPPER", "2", "Материал верха ", "true", "MAT_UPPER \"Информация\", c.thickness \"Толщина\", ' ' \"Цвет\""});
        listAllBlocksSketch.add(new String[]{"MKZ_LINING", "4", "Подкладка ", "true", "LINING \"Информация\", c.thickness \"Толщина\", ' ' \"Цвет\""});
        listAllBlocksSketch.add(new String[]{"MKZ_SOLE", "6", "Подошва ", "false", "SOLES \"Информация\", ' ' \"Толщина\", c.COLOR \"Цвет\""});
        listAllBlocksSketch.add(new String[]{"MKZ_HEEL", "7", "Каблук ", "false", "HEEL \"Информация\", ' ' \"Толщина\", c.COLOR \"Цвет\""});
        listAllBlocksSketch.add(new String[]{"MKZ_INSOLE", "8", "Стелька основная", "false", "INSOLE \"Информация\", ' ' \"Толщина\", ' ' \"Цвет\""});
        listAllBlocksSketch.add(new String[]{"MKZ_INSOCK", "9", "Стелька вкладная", "false", "INSOCK \"Информация\", ' ' \"Толщина\", ' ' \"Цвет\""});
        listAllBlocksSketch.add(new String[]{"MKZ_BACKCLOTH", "10", "Задник ", "false", "BACKDROP \"Информация\", ' ' \"Толщина\", ' ' \"Цвет\""});
        listAllBlocksSketch.add(new String[]{"MKZ_LIGHTNING", "11", "Молния ", "false", "ZIPPER \"Информация\", ' ' \"Толщина\", c.COLOR \"Цвет\""});
        listAllBlocksSketch.add(new String[]{"MKZ_FINDINGS", "12", "Фурнитура ", "true", "DECORATION \"Информация\", ' ' \"Толщина\", c.COLOR \"Цвет\""});
        listAllBlocksSketch.add(new String[]{"MKZ_SHOELACE", "14", "Шнурки ", "false", "TIE \"Информация\", ' ' \"Толщина\", c.COLOR \"Цвет\""});
        listAllBlocksSketch.add(new String[]{"MKZ_THREADS", "15", "Нитки ", "true", "THREADS \"Информация\", c.thickness \"Толщина\", c.COLOR \"Цвет\""});

    }
}
