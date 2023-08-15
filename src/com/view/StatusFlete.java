package com.view;

import com.dao.FleteDAO;
import com.dao.VehiculosDAO;
import com.model.AsignacionUnidad;
import com.model.Flete;
import com.model.Operador;
import com.model.Vehiculo;
import com.model.Workplace;
import com.model.RecargaCombustible;
import com.utils.Validaciones;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

/**
 *
 * @author keatnis
 */
public class StatusFlete extends javax.swing.JDialog {

    /**
     * Creates new form StatusFlete
     */
    private AsignacionUnidad asignacionUnidad;
    private Flete flete;
    private FleteDAO fleteDAO;
    private Workplace workplace;
    int id, idAsing, idWork, idOperador, idVehiculo, idRecargaCombustible = 0;
    private Vehiculo vehiculo;
    private Operador operador;
    private RecargaCombustible recargaCombustible;
    private VehiculosDAO vehiculosDAO;
    private float KM_actual;

    public StatusFlete(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.fleteDAO = new FleteDAO();
        this.vehiculosDAO = new VehiculosDAO();
    }

    public void dataFlete(int id, int idAsignacion, int idWork, int operador, int vehiculo, int idRecarga, String status, LocalDateTime dateStart, LocalDateTime dateEnd, String KmInicial, String KMFinal) {
        cmbStatusFlete.setSelectedItem(status);
        if (dateStart != null || !dateStart.equals("null")) {
            startDate.setDate(Validaciones.returnDate(dateStart.toString()));
        } else {
            startDate.setDate(null);
        }
        if (dateEnd == null || dateEnd.equals("null")) {
            endDate.setDate(null);
        } else {
            endDate.setDate(Validaciones.returnDate(dateEnd.toString()));

        }
        txtHora1.setText(dateStart != null ? (dateStart.getHour() + "" + dateStart.getMinute()) : "");

        txtHora2.setText(dateStart != null ? (dateEnd.getHour() + "" + dateEnd.getMinute()) : "");

        txtKMIncicial.setText(KmInicial);
        txtKMFinal.setText(KMFinal);
        this.id = id;
        this.idAsing = idAsignacion;
        this.idWork = idWork;
        this.idOperador = operador;
        this.idVehiculo = vehiculo;
        this.idRecargaCombustible = idRecarga;
    }

    private void updateStatus() {
        flete = new Flete();

        asignacionUnidad = new AsignacionUnidad();

        flete.setId(id);

        asignacionUnidad.setId(idAsing);

        workplace = new Workplace();
        workplace.setId(idWork);
        flete.setLugarTrabajo(workplace);

        asignacionUnidad.setFechaInicio(Validaciones.dateTimeReturn(startDate, txtHora1.getText(), "Inicio"));
        asignacionUnidad.setFechaFin(Validaciones.dateTimeReturn(endDate, txtHora2.getText(), "Final/Termino"));
        asignacionUnidad.setKmInicio(Float.valueOf(txtKMIncicial.getText()));
        asignacionUnidad.setKmFinal(Float.parseFloat(txtKMFinal.getText()));
        operador = new Operador();
        operador.setId(idOperador);
        vehiculo = new Vehiculo();
        vehiculo.setId(idVehiculo);
        asignacionUnidad.setOperador(operador);
        asignacionUnidad.setVehiculo(vehiculo);
        flete.setStatus((String) cmbStatusFlete.getSelectedItem());
        flete.setAsignacionUnidad(asignacionUnidad);
        recargaCombustible = new RecargaCombustible();
        recargaCombustible.setId(idRecargaCombustible);
        flete.setRecargaCombustible(recargaCombustible);
        fleteDAO.updateStatus(flete);

        if (!txtKMFinal.getText().equals("0") && KM_actual < Float.valueOf(txtKMIncicial.getText())) {
            vehiculosDAO.updateKM(vehiculo.getId(), Float.valueOf(txtKMIncicial.getText()));
        } else if (!txtKMFinal.equals("0") && KM_actual < Float.valueOf(txtKMFinal.getText())) {
            vehiculosDAO.updateKM(vehiculo.getId(), Float.valueOf(txtKMFinal.getText()));
        } else {
            JOptionPane.showMessageDialog(null,
                    "no se actualizo el Kilometraje del vehiculo, "
                            + "puede actualizar los datos usando la opcion editar desde el listado de los datos");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cmbStatusFlete = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        endDate = new com.toedter.calendar.JDateChooser();
        txtHora1 = new javax.swing.JFormattedTextField();
        startDate = new com.toedter.calendar.JDateChooser();
        txtKMIncicial = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtKMFinal = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtHora2 = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jLabel2.setText("24hr");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cambiar estatus flete");

        jLabel17.setText("Estatus");

        cmbStatusFlete.setEditable(true);
        cmbStatusFlete.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "En espera", "Preparado", "Activo", "Terminado", "Bloqueado", "Descargado", " " }));

        jLabel14.setText("Fecha Fin");

        endDate.setDateFormatString("dd/MM/yyyy");
        endDate.setMaximumSize(null);

        try {
            txtHora1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        startDate.setDateFormatString("dd/MM/yyyy");
        startDate.setMaximumSize(null);

        txtKMIncicial.setForeground(new java.awt.Color(204, 0, 0));
        txtKMIncicial.setText("0");
        txtKMIncicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKMIncicialKeyTyped(evt);
            }
        });

        jLabel16.setText("KM final");

        txtKMFinal.setText("0");
        txtKMFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKMFinalKeyTyped(evt);
            }
        });

        jLabel15.setText("KM inicial");

        jLabel13.setText("Fecha Inicio");

        jButton1.setText("Actualizar Estatus");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        try {
            txtHora2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel1.setText("24hr");

        jLabel3.setText("24hr");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtHora1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(txtKMIncicial, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(jLabel16)))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtKMFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtHora2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(cmbStatusFlete, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel13))
                    .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHora1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel1)))
                    .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtHora2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel15))
                    .addComponent(txtKMIncicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtKMFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel17))
                    .addComponent(cmbStatusFlete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(21, 21, 21))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtKMIncicialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKMIncicialKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);
    }//GEN-LAST:event_txtKMIncicialKeyTyped

    private void txtKMFinalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKMFinalKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);
    }//GEN-LAST:event_txtKMFinalKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        updateStatus();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbStatusFlete;
    private com.toedter.calendar.JDateChooser endDate;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private com.toedter.calendar.JDateChooser startDate;
    private javax.swing.JFormattedTextField txtHora1;
    private javax.swing.JFormattedTextField txtHora2;
    private javax.swing.JTextField txtKMFinal;
    private javax.swing.JTextField txtKMIncicial;
    // End of variables declaration//GEN-END:variables
}
