package my.guisap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import my.guisap.forms.UpdateForm;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel;

/**
 *
 * @author DaN
 */
public class MainClass {

    public static JFrame mainWindow;

    public static void setupSubstance() {
        try {
            final String fileName = System.getProperty("user.home") + System.getProperty("file.separator") + "insubstantial.txt";
            final Properties properties = new Properties();

            org.pushingpixels.substance.api.SubstanceLookAndFeel laf = new SubstanceMistSilverLookAndFeel();
            UIManager.setLookAndFeel(laf);
            UIManager.put(SubstanceLookAndFeel.SHOW_EXTRA_WIDGETS, Boolean.TRUE);
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);

            UIManager.put("OptionPane.yesButtonText", "Да");
            UIManager.put("OptionPane.noButtonText", "Нет");
            UIManager.put("OptionPane.cancelButtonText", "Отмена");

            Runtime.getRuntime().addShutdownHook(new Thread() {

                @Override
                public void run() {
                    try {
                        String skinClassName = SubstanceLookAndFeel.getCurrentSkin().getClass().getCanonicalName();
                        properties.setProperty("skinClassName", skinClassName);
                        properties.store(new FileOutputStream(fileName), fileName);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            });
            properties.load(new FileInputStream(fileName));

            String skinClassName = properties.getProperty("skinClassName");
            SubstanceLookAndFeel.setSkin(skinClassName);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        setupSubstance();
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (GuiStaticVariables.CONFIGS_DIRECTORY != null) {
                    File myPath = new File(GuiStaticVariables.CONFIGS_DIRECTORY);
                    myPath.mkdirs();
                }
                mainWindow = new MainWindow();
                mainWindow.setVisible(true);
                FormRegister fr = FormRegister.getInstance();
                fr.openForm(new UpdateForm(true), FormRegister.SELECT_KEY_FORM);
            }
        });
    }
}
