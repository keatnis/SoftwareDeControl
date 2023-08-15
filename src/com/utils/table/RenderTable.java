package com.utils.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author keatnis
 *
 * Render cell of the table for an button {edit,delete}
 */
public class RenderTable extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        // añadimos el botón
        if (value instanceof JButton) {
            JButton btn = (JButton) value;
            if (isSelected) {
                btn.setForeground(table.getSelectionForeground());
                btn.setBackground(table.getSelectionBackground());
            } else {
                btn.setForeground(table.getForeground());
                btn.setBackground(UIManager.getColor("Button.background"));
            }
            return btn;
        }


        // para formatear la fecha de pago
        if (value instanceof JFormattedTextField) {
            JFormattedTextField ftxt = (JFormattedTextField) value;
          //  ftxt.formattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("dd/MM/yyyy")));
            if (isSelected) {
                ftxt.setForeground(table.getSelectionForeground());
                ftxt.setBackground(table.getSelectionBackground());
            } else {
                ftxt.setForeground(table.getForeground());
                ftxt.setBackground(UIManager.getColor("TextField.background"));
            }
            return  ftxt;
        }
//          if (column == 13) {
//            return new JCheckBox();
//        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

 
}
