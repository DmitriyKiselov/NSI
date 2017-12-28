/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.componenst.security;

import java.util.ArrayList;
import javax.swing.JButton;
import my.guisap.utils.SecurityManager;

/**
 *
 * @author DonchenkoUA
 */
public class JSecurityButton extends JButton {

    SecurityManager sm = SecurityManager.getInstance();
    ArrayList<Integer> allowedPrivelege = new ArrayList<>();

    public JSecurityButton(ArrayList<Integer> allowedPriv) {
        super.setVisible(false);
        this.allowedPrivelege = allowedPriv;
        setVisible();
    }

    private void setVisible() {
        super.setVisible(sm.hasPremition(allowedPrivelege));
    }

    public void update() {
        super.setVisible(sm.hasPremition(allowedPrivelege));
    }
}
