package com.utils.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author keatnis
 */
public class RenderStatus extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        /*
                ESTATUS : 

                    En espera
                    Preparado
                    Activo
                    Terminado
                    Bloqueado
                    Descargado

         */
        String status = String.valueOf(table.getValueAt(row, 9));
//        if (status.equals("En espera")) {
//            setBackground(new Color(255, 51, 0));
//            setForeground(Color.WHITE);
//        } else if (status.equals("Preparado")) {
//            setBackground(new Color(255, 51, 0));
//            setForeground(Color.WHITE);
//        } 
         if (status.equals("Activo")) {
            setBackground(new Color(204, 204, 0));
            setForeground(new Color(51, 51, 51));
        } else if (status.equals("Terminado")) {
            setBackground(new Color(51, 204, 0));
            setForeground(Color.WHITE);
        }else if (status.equals("Bloqueado")) {
            setBackground(new Color(255, 51, 0));
            setForeground(new Color(51, 51, 51));
        }else{
             setBackground(Color.WHITE);
             setForeground(Color.BLACK);
        }


        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
  

}
