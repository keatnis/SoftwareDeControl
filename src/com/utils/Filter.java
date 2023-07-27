package com.utils;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author keatnis
 */
public class Filter {

    public static void searchInTable(JTextField txtSearch, JTable table) {
        DefaultTableModel modelt = (DefaultTableModel) table.getModel();
        String query = txtSearch.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modelt);
        table.setRowSorter(tr);
        if (query != "") {
            tr.setRowFilter(RowFilter.regexFilter("(?i)" + query));
            //rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        } else {
            table.setRowSorter(tr);
        }
    }

    public static void removeAllRows(JTable table) {
        DefaultTableModel tb = (DefaultTableModel) table.getModel();
        int a = table.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }
    }
}
