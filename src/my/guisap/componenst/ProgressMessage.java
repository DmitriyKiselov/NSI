/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.componenst;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import my.guisap.GuiStaticVariables;

/**
 *
 * @author DaN
 */
public class ProgressMessage extends JWindow {

    JLabel label = new JLabel();

    public ProgressMessage(String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.GridLayout());
        label.setText(message);
        panel.add(label);
        panel.setPreferredSize(new Dimension((int) (350 * GuiStaticVariables.scaleWidth), (int) (55 * GuiStaticVariables.scaleHeight)));
        getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
