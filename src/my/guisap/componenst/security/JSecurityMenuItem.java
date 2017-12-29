/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.componenst.security;

import java.util.ArrayList;
import javax.swing.JMenuItem;
import my.guisap.utils.SecurityManager;

/**
 *
 * @author DanchenkoUA
 */
public class JSecurityMenuItem extends JMenuItem {

    ArrayList<Integer> allowedPrivelege = new ArrayList<>();

    public JSecurityMenuItem(ArrayList<Integer> allowedPriv) {
        super.setVisible(false);
        this.allowedPrivelege = allowedPriv;
    }

    public void setVisible(SecurityManager sm) {
        super.setVisible(sm.hasPremition(allowedPrivelege));
    }
}
