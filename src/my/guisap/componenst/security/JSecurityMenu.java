/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.componenst.security;

import java.util.ArrayList;
import javax.swing.JMenu;
import my.guisap.utils.SecurityManager;

/**
 *
 * @author DonchenkoUA
 */
public class JSecurityMenu extends JMenu {

    ArrayList<Integer> allowedPrivelege = new ArrayList<>();

    public JSecurityMenu(ArrayList<Integer> allowedPriv) {
        super.setVisible(false);
        this.allowedPrivelege = allowedPriv;
    }

    public void setVisible(SecurityManager sm) {
        super.setVisible(sm.hasPremition(allowedPrivelege));
    }
}
