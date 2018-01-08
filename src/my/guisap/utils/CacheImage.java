package my.guisap.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import my.guisap.sql.SqlOperations;

/**
 *
 * @author KiselevDA
 */
public class CacheImage {

    public final static Map<String, String> cachePath = new HashMap<String, String>();

    static {
        DefaultTableModel pathModel = new DefaultTableModel();
        SqlOperations.sql.tableFill("SELECT ID,PATH FROM PATH_TABLE", pathModel);
        for (int i = 0; i < pathModel.getRowCount(); i++) {
            cachePath.put((String) pathModel.getValueAt(i, 0), (String) pathModel.getValueAt(i, 1));
        }
    }

    public static String TYPE_MKZ = "1";
    public static String TYPE_MKZ_SKETCH = "2";
    public static String TYPE_SOLE = "3";
    public static String TYPE_HEEL = "4";
    public static String TYPE_UP = "5";

    public static Map<String, ImageIcon> cacheModel = new HashMap<String, ImageIcon>();
    public static Map<String, ImageIcon> cacheHeel = new HashMap<String, ImageIcon>();
    public static Map<String, ImageIcon> cacheSole = new HashMap<String, ImageIcon>();
    public static Map<String, ImageIcon> cacheUp = new HashMap<String, ImageIcon>();

    public CacheImage() {

    }

    public static void loadImageToTable(String iconPath, Map<String, ImageIcon> map, JTable outTable, int numberColumnIndex, int numberColumnImage) {
        File F = new File(cachePath.get(iconPath));
        Map<String, String> fileMap = new HashMap<>();

        FilenameFilter imageFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                return lowercaseName.endsWith(".jpg");
            }
        };

        String[] nameList = F.list(imageFilter);
        File[] fileList = F.listFiles(imageFilter);

        for (int i = 0; i < nameList.length; i++) {
            fileMap.put(nameList[i], fileList[i].toString());
        }

        if (map.isEmpty()) {
            for (int i = 0; i < outTable.getRowCount(); i++) {
                try {
                    ImageIcon image = new ImageIcon(fileMap.get(outTable.getValueAt(i, numberColumnIndex).toString() + ".jpg"));
                    outTable.setValueAt(image, i, numberColumnImage);
                    map.put(outTable.getValueAt(i, numberColumnIndex).toString(), image);
                } catch (NullPointerException ex) {

                }
            }
        } else {
            for (int i = 0; i < outTable.getRowCount(); i++) {
                try {
                    ImageIcon image = map.get(outTable.getValueAt(i, numberColumnIndex).toString());
                    if (image == null) {
                        image = new ImageIcon(cachePath.get(iconPath) + "\\" + outTable.getValueAt(i, numberColumnIndex).toString() + ".jpg");
                        map.put(outTable.getValueAt(i, numberColumnIndex).toString(), image);
                    }
                    outTable.setValueAt(image, i, numberColumnImage);
                } catch (NullPointerException ex) {

                }
            }
        }

    }

    public static void clearCache(Map<String, ImageIcon> map) {
        map.clear();
    }

    public static Map<String, ImageIcon> getMapByType(String type) {
        switch (type) {
            case "1":
                return cacheModel;
            case "2":
                break;
            case "3":
                return cacheSole;
            case "4":
                return cacheHeel;
            case "5":
                return cacheUp;
        }
        return null;
    }
}
