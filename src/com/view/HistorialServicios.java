package com.view;

import com.dao.ServiciosDAO;
import com.dao.VehiculosDAO;
import com.model.Servicio;
import com.model.Vehiculo;
import com.utils.ExportExcel;
import com.utils.Filter;
import com.utils.table.RenderTable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author keatnis
 */
public class HistorialServicios extends javax.swing.JPanel {

    private VehiculosDAO vehiculosDAO;
    private final ServiciosDAO serviciosDAO;
    private static Integer ID_VEHICULO = null;

    public HistorialServicios() {
        initComponents();
        this.vehiculosDAO = new VehiculosDAO();
        this.serviciosDAO = new ServiciosDAO();
    }

    private void getITems() {
        String res = (String) this.cmbSearch.getEditor().getItem();
        cmbSearch.removeAllItems();
        //   cmbSearch.setPopupVisible(true);
        ID_VEHICULO = null;
        List<Vehiculo> ve = vehiculosDAO.getByKey(res);

        for (Vehiculo vehiculol : ve) {

            cmbSearch.addItem(vehiculol.getId() + " - MARCA: " + vehiculol.getMarca() + " MODELO: "
                    + vehiculol.getModelo() + " NUM_SERIE: " + vehiculol.getDescripcion());

        }
        
        cmbSearch.repaint();
        cmbSearch.setPopupVisible(true);

    }

    private void showData(JTable table) {
        Filter.removeAllRows(table);
        Object[] titles = new Object[]{
            "ID SERVICIO", "DETALLES DEL VEHICULO", "TIPO VEHICULO", "KM. ACTUAL", "DESCRIPCION SERVICIO", "FECHA SERVICIO",
            "CANTIDAD", "PRECIO", "IMPORTE", "METODO DE PAGO", "EMPRESA RESPONSABLE", "OBSERVACIONES", "PROX. SERVICIO"};
        /*coloco el nombre de las  columnas de la tabla USER a el modelo */
        DefaultTableModel model = new DefaultTableModel(null, titles) {

            // desactivamos la opcion de editar los datos en la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        RenderTable render = new RenderTable();
        table.setDefaultRenderer(Object.class, render);
        /* obtengo la lista de */
        String[] getID = cmbSearch.getSelectedItem().toString().split(" ");

        List<Servicio> list = serviciosDAO.getServiciosById(Integer.parseInt(getID[0]));
        getID = null;

        for (Servicio servicio : list) {

            model.addRow(new Object[]{
                servicio.getId(),
                servicio.getVehiculo().getId() + "- MARCA: "
                + servicio.getVehiculo().getMarca() + "MODELO: "
                + servicio.getVehiculo().getModelo() + " NUM.SERIE"
                + servicio.getVehiculo().getNumSerie(),
                servicio.getVehiculo().getType(),
                // servicio.getVehiculo().getDescripcion(),
                servicio.getVehiculo().getKmActual(),
                servicio.getDescripcion(),
                servicio.getFecha(),
                servicio.getCantidad(),
                servicio.getPrecio(),
                servicio.getImporte(),
                servicio.getMetodoPago(),
                servicio.getEmpresa(),
                servicio.getObservaciones(),
                servicio.getProximoServicio()

            });
            /*establecemos el modelo  al Jtable llamado jTabla*/

        }

        // table.setRowHeight(30);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.doLayout();
        /* asignamos el ancho de cada columna de la tabla*/

        int[] anchos = {80, 400, 150, 200, 200, 150, 150, 150, 200, 200, 180, 150, 180};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblServices = new javax.swing.JTable();
        btnSearch = new javax.swing.JButton();
        btnShow = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cmbSearch = new javax.swing.JComboBox<>();
        btnExportar = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        tblServices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblServices);

        btnSearch.setText("Buscar");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnShow.setText("Mostrar historial");
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Cantarell", 0, 12)); // NOI18N
        jLabel1.setText("Burcar vehiculo por marca, modelo o num. serie.");

        cmbSearch.setEditable(true);

        btnExportar.setText("Exportar datos");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(894, Short.MAX_VALUE)
                .addComponent(btnExportar)
                .addGap(52, 52, 52))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnSearch))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(161, 161, 161)))
                            .addGap(30, 30, 30)
                            .addComponent(btnShow))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1017, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 462, Short.MAX_VALUE)
                .addComponent(btnExportar))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch)
                        .addComponent(btnShow))
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(31, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 5);
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        this.getITems();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        showData(tblServices);
    }//GEN-LAST:event_btnShowActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        try {
            ExportExcel.exportarExcel(tblServices);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnExportarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnShow;
    private javax.swing.JComboBox<String> cmbSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblServices;
    // End of variables declaration//GEN-END:variables
}
