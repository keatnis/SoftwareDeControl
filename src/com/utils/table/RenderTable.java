
package com.utils.table;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author keatnis
 * 
 * Render cell of the table for an button {edit,delete}
 */
public class RenderTable extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
            // añadimos el botón
           if ( value instanceof JButton) {
            JButton btn =(JButton) value;
               if (isSelected) {
                btn.setForeground(table.getSelectionForeground());
                btn.setBackground(table.getSelectionBackground());
            } else {
                btn.setForeground(table.getForeground());
                btn.setBackground(UIManager.getColor("Button.background"));
            }
               return btn;
           }
           return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
    
}
