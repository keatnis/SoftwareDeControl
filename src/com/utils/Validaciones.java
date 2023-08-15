package com.utils;

import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author keatnis
 */
public class Validaciones {
    //Metodo para validar cajas solo numeros

    public static void esNumeroEntero(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if ((c < '0') || (c > '9')) {
            evt.consume();
            // JOptionPane.showInputDialog("Ingresa solo numero");
        }
    }

    public static void soloRecibeTextoConPunto(java.awt.event.KeyEvent evt) {

        char c = evt.getKeyChar();
        if (!Character.isLetter(evt.getKeyChar())
                && !(evt.getKeyChar() == KeyEvent.VK_SPACE)
                && !(c == '.')) {
            //  && !(evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)){
            evt.consume();
        }
    }

    public static void soloRecibeNumeroConPunto(java.awt.event.KeyEvent evt) {

        char c = evt.getKeyChar();
        if (!Character.isDigit(evt.getKeyChar())
                && !(evt.getKeyChar() == KeyEvent.VK_SPACE)
                && !(c == '.')) {
            //  && !(evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)){
            evt.consume();
        }
    }

    public static void soloRecibeTextoyNumero(java.awt.event.KeyEvent evt) {

        char c = evt.getKeyChar();
        if (!Character.isLetterOrDigit(evt.getKeyChar())
                && !(evt.getKeyChar() == KeyEvent.VK_SPACE)) {
            //  && !(evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)){
            evt.consume();
        }
    }

    public static void soloRecibeTexto(java.awt.event.KeyEvent evt) {

        char c = evt.getKeyChar();
        if (!Character.isLetter(evt.getKeyChar())
                && !(evt.getKeyChar() == KeyEvent.VK_SPACE)) {
            // && !(c=='.')){
            //  && !(evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)){
            evt.consume();
        }
    }

    public static boolean esNumeroNoValido(javax.swing.JTextField jt, int li, int ls, String msg) {
        int valor = Integer.parseInt(jt.getText());
        if (valor < li || valor > ls) {
            JOptionPane.showMessageDialog(null, msg);
            jt.setText(null);
            jt.requestFocus();
            return true;
        }
        return false;
    }

    //Método para validar caja no vacia
    public static boolean esCajaVacia(javax.swing.JTextField cajaTexto, String msj) {
        if (cajaTexto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, msj, "Aviso", JOptionPane.WARNING_MESSAGE);
            cajaTexto.requestFocus();
            return true;
        } else {
            return false;
        }
    }

    public static void cmbNoSeleccionado(JComboBox cmb, String msj) {
        if (cmb.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, msj, "Aviso", JOptionPane.WARNING_MESSAGE);
            cmb.requestFocus();

        }

    }

    //Revisar la longitud de un campo de texto
    public static void maxLength(java.awt.event.KeyEvent evt, javax.swing.JTextField jt, int lim) {
        if (jt.getText().length() == lim) {
            evt.consume();
        }
    }

    public static void minLength(java.awt.event.KeyEvent evt, javax.swing.JTextField jt, int min) {
        if (jt.getText().length() < min) {
            System.out.println("aun no es el min.");
        }
    }

    public static void dateNoSeleted(JDateChooser dateChooser, String nombreComponente) {

        if (dateChooser.getDate() == null || dateChooser.getDate().equals("")) {
            dateChooser.setDate(null);
            JOptionPane.showMessageDialog(null, nombreComponente + " no seleccionada ");
        }
    }

    public static void dateNoSeletedWhithMessage(JDateChooser dateChooser, String msj) {
        if (dateChooser.getDate() == null || dateChooser.getDate().equals("")) {
            JOptionPane.showMessageDialog(null, msj);
        }
    }

    public static Date returnDate(String dateString) {
        Date date = null;
        try {
            if (!dateString.equals("null") || dateString != null) {
                date = new SimpleDateFormat("yyyy-MM-dd")
                        .parse(dateString);
            }
        } catch (ParseException ex) {
             JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return date;
       
    }

    public static LocalDateTime dateTimeReturn(JDateChooser date, String time, String nameTime) {
        /*
        DATE: 2022-02-01 TIME: 24:00
         */
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
        //     fechaFinal= formatofecha.format(endDate.getDate());
        LocalDateTime localDateTime = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String strHora = null;

        if (date.getDate() != null && time != null) {
            strHora = formatofecha.format(date.getDate()) + " " + time;
            localDateTime = LocalDateTime.parse(strHora, formatter);
        } else if (date.getDate() != null && time == null) {

            int opcion = JOptionPane.showConfirmDialog(null, 
                    "no se establecio la hora " + nameTime + "\n Desea establecerlo?  .", "Confirmacion", JOptionPane.YES_NO_OPTION);
            if (opcion == 0) {
                strHora = formatofecha.format(date.getDate()) + " ";
            } else {
                strHora = formatofecha.format(date.getDate()) + " " + "00:00";
                localDateTime = LocalDateTime.parse(strHora, formatter);
            }

        } else if (date.getDate() == null && time != null) {
            strHora = " " + time;
            JOptionPane.showConfirmDialog(null, 
                    "no se establecio la fecha de " + nameTime + ", Desea establecerlo? \n .", "Confirmacion", JOptionPane.YES_NO_OPTION);
            localDateTime = LocalDateTime.parse(strHora, formatter);
        } else {
            localDateTime = null;
        }
        return localDateTime;
    }

    public static void cmbEditorNoSelected(JComboBox comboBox, String msj) {

        if (comboBox.getEditor().getItem().toString().isEmpty() || comboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, msj);
        }
    }
}
