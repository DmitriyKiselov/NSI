package my.guisap.forms;

import my.guisap.componenst.EmptyForm;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import my.guisap.GuiStaticVariables;
import my.guisap.componenst.ProgressMessage;

/**
 *
 * @author KiselevDA
 */
public class UpdateForm extends EmptyForm {

    ProgressMessage pm;

    public UpdateForm(boolean needCheck) {
        super("Обновление", "updateForm", false, false);

        if (needCheck) {
            if (getActualVersion()) {

            } else {
                super.closeWindow();
            }
        }

        lblHeader.setText("Обновление не требуется");
        saveButt.setText("Обновить");
        saveButt.setEnabled(false);
        getActualVersion();
        render();
    }

    private void render() {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.add(new JLabel("Версия программы: "),
                new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(new JLabel(GuiStaticVariables.VERSIONAPP),
                new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        panel.add(new JLabel("Актуальная версия программы: "),
                new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(new JLabel(GuiStaticVariables.ACTUALVERSIONAPP),
                new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        pnlAttElem.add(panel);
        pack();
        setCenter();
    }

    public boolean getActualVersion() {
        DefaultTableModel dtm = new DefaultTableModel();
        sql.tableFill("SELECT VERSION FROM VERSIONAPP where id='1'", dtm);
        if (dtm.getRowCount() > 0) {
            GuiStaticVariables.ACTUALVERSIONAPP = dtm.getValueAt(0, 0).toString();
        } else {
            JOptionPane.showMessageDialog(this, "Не удается проверить версию", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
        if (!GuiStaticVariables.ACTUALVERSIONAPP.equals(GuiStaticVariables.VERSIONAPP)) {
            lblHeader.setText("Доступно обновление программы");
            saveButt.setEnabled(true);
            return true;
        }
        return false;
    }

    private void update() {

        // Создаем каталог, куда будут распакованы файлы
        final String dstDirectory = new File("").getAbsolutePath();
        final File dstDir = new File(dstDirectory);

        if (!dstDir.exists()) {
            dstDir.mkdir();
        }

        pm = new ProgressMessage("Пожалуйста, подождите! Идет загрузка.");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                byte[] buffer = new byte[1024];
                try (ZipInputStream zInputStream = new ZipInputStream(sql.getBlob("SELECT FILETOUPLOAD FROM VERSIONAPP where id='1'").getBinaryStream())) {
                    // Получаем содержимое ZIP архива
                    ZipEntry zipEntry = zInputStream.getNextEntry();
                    String nextFileName;
                    while (zipEntry != null) {
                        nextFileName = zipEntry.getName();
                        File nextFile = new File(dstDirectory + File.separator
                                + nextFileName);
                        System.out.println("Распаковываем: "
                                + nextFile.getAbsolutePath());
                        // Если мы имеем дело с каталогом - надо его создать. Если
                        // этого не сделать, то не будут созданы пустые каталоги
                        // архива
                        if (zipEntry.isDirectory()) {
                            nextFile.mkdir();
                        } else {
                            // Создаем все родительские каталоги
                            new File(nextFile.getParent()).mkdirs();
                            // Записываем содержимое файла
                            try (FileOutputStream fos
                                    = new FileOutputStream(nextFile)) {
                                int length;
                                while ((length = zInputStream.read(buffer)) > 0) {
                                    fos.write(buffer, 0, length);
                                }
                            }
                        }
                        zipEntry = zInputStream.getNextEntry();
                    }

                    pm.setVisible(false);
                    pm.dispose();
                    JOptionPane.showMessageDialog(null, "Загруженна актуальная версия, требуется перезапуск программы", "Информация", JOptionPane.INFORMATION_MESSAGE);

                    //запуск обновленной программы
                    ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "java -jar", "GUI_Sap.jar");
                    Process updateProcess = processBuilder.start();
                    System.exit(0);

                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при перезаписи файлов", "Ошибка", JOptionPane.ERROR);
                    Logger.getLogger(UpdateForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException | IOException ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при обновлении", "Ошибка", JOptionPane.ERROR);
                    Logger.getLogger(UpdateForm.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

//            }
//        });
    }

    @Override

    public void saveActionPerformed(ActionEvent evt) {
        update();
    }
}
