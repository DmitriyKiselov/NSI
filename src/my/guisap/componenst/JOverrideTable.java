/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guisap.componenst;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author dima
 */
public class JOverrideTable {

    public static void packColumns(JTable table) {
        for (int i = 0, columnCount = table.getColumnCount(); i < columnCount; i++) {
            TableCellRenderer renderer = table.getDefaultRenderer(
                    table.getModel().getColumnClass(i));

            int maxWidth = 0;

            for (int j = 0, rowCount = table.getRowCount(); j < rowCount; j++) {
                Component component = renderer.getTableCellRendererComponent(
                        table,
                        table.getValueAt(j, i),
                        true,
                        true,
                        j,
                        i);

                maxWidth = Math.max(
                        maxWidth, component.getPreferredSize().width);
            }

            table.getColumnModel().getColumn(i).setMinWidth(
                    maxWidth + table.getIntercellSpacing().width);
        }
    }
}
