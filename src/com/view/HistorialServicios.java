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

    public HistorialServicios() {
        initComponents();
        this.vehiculosDAO = new VehiculosDAO();
        this.serviciosDAO = new ServiciosDAO();
    }

    private void getITems() {
        String res = (String) this.cmbSearch.getEditor().getItem();
        if (!res.isEmpty()) {
            cmbSearch.removeAllItems();

           
            List<Vehiculo> ve = vehiculosDAO.getByKey(res);

            for (Vehiculo vehiculol : ve) {

                cmbSearch.addItem(vehiculol.getId() + " MARCA: " + vehiculol.getMarca() + " MODELO: "
                        + vehiculol.getModelo() + " NUM_SERIE: " + vehiculol.getDescripcion());

            }

            cmbSearch.repaint();
            cmbSearch.setPopupVisible(true);
        } else {
            this.cmbSearch.removeAllItems();
            JOptionPane.showMessageDialog(null,
                    "Introduzca los datos del vehiculo marca o modelo, luego de clic en el boton buscarr");
            return;
        }

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
                servicio.getVehiculo().getId() + " MARCA: "
                + servicio.getVehiculo().getMarca() + "MODELO: "
                + servicio.getVehiculo().getModelo() + " NUM.SERIE "
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

        root = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblServices = new javax.swing.JTable();
        btnSearch = new javax.swing.JButton();
        btnShow = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cmbSearch = new javax.swing.JComboBox<>();
        btnExportar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

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
        tblServices.setShowGrid(true);
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
        jLabel1.setText("Buscar vehiculo por marca, modelo o num. serie.");

        cmbSearch.setEditable(true);

        btnExportar.setText("Exportar datos");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rootLayout = new javax.swing.GroupLayout(root);
        root.setLayout(rootLayout);
        rootLayout.setHorizontalGroup(
            rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnSearch)
                .addGap(30, 30, 30)
                .addComponent(btnShow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 388, Short.MAX_VALUE)
                .addComponent(btnExportar)
                .addGap(33, 33, 33))
            .addGroup(rootLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootLayout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1219, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        rootLayout.setVerticalGroup(
            rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addGroup(rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addGroup(rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnShow)
                        .addComponent(btnExportar)))
                .addGap(20, 20, 20))
            .addGroup(rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(rootLayout.createSequentialGroup()
                    .addGap(54, 54, 54)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(25, Short.MAX_VALUE)))
        );

        jScrollPane1.getAccessibleContext().setAccessibleParent(null);

        add(root, java.awt.BorderLayout.PAGE_START);
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel root;
    private javax.swing.JTable tblServices;
    // End of variables declaration//GEN-END:variables
}
