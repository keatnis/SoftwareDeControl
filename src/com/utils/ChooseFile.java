package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author keatnis
 */
public class ChooseFile {

    private String rutaArchivo = null;
    File ruta;
    private byte[] pdf;

    public byte[] agregar_pdf(JLabel laberCheck) {
        // Inicia el JFileChooser
        JFileChooser fc = new JFileChooser();
        // Se crea un filtro de extensiones para que solamente pueda seleccionar archivos PDF
        FileFilter ff = new FileNameExtensionFilter("Archivo PDF", "pdf");
        // Se asigna el filtro al objeto JFileChooser
        fc.setFileFilter(ff);
        // Se muestra la ventana de JFilChooser

        int opcionav = fc.showOpenDialog(null);
        File archivoPDF = fc.getSelectedFile();
        rutaArchivo = archivoPDF.getAbsolutePath();
        ruta = new File(rutaArchivo);
        if (opcionav == JFileChooser.APPROVE_OPTION && ruta != null) {
            // Se asigna el archivo seleccionado a un objeto tipo File

            try {
                pdf = new byte[(int) ruta.length()];
                InputStream input = new FileInputStream(ruta);
                input.read(pdf);
                viewPdf();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al agregar archivo pdf " + ex.getMessage());
            }

        }
        return pdf;
    }

    private void viewPdf() {
        int opcionpdf = JOptionPane.showConfirmDialog(null, 
                "¿Quiere ver el archivo agregado?", "Archivo seleccionado", JOptionPane.YES_NO_OPTION);
        if (opcionpdf == 0) {
            try {
                Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,"
                        + "ShellExec_RunDLL " + rutaArchivo);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se puede abrir el archivo de ayuda,"
                        + " probablemente fue borrado", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
