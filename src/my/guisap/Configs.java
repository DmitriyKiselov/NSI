/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DonchenkoUA
 */
public class Configs extends Properties {

    public File fConfig = null;

    public Configs(String fileName) {
        fConfig = new File(fileName);
        fillFormPropertiesFromConfigFile(fConfig);
    }

    public void saveProperties(String nameConfiguration) {
        try {
            FileOutputStream fos = new FileOutputStream(fConfig, false);
            this.store(fos, nameConfiguration);
            fos.close();
        } catch (Exception e) {
            System.out.print("Error save property!!!");
        }
    }

    public void loadProperties() {
    }

    private void fillFormPropertiesFromConfigFile(File fConfig) {
        if (fConfig.exists()) {
            FileInputStream fis;
            try {
                fis = new FileInputStream(fConfig);
                try {
                    this.load(fis);
                } catch (IOException ex) {
                    Logger.getLogger(FormRegister.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FormRegister.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
