package com.view;

import com.dao.DashboardDAO;
import com.model.Flete;
import com.model.ModelCard;
import com.model.Servicio;
import com.utils.Filter;
import com.utils.table.RenderTable;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author keatnis
 */
public class Dashboard extends javax.swing.JPanel {

    /**
     * Creates new form Dashboard
     */
    private DashboardDAO dashboardDAO = new DashboardDAO();
    private int trabajadores, vehiculos, recargas, fletes;

    public Dashboard() {
        initComponents();
        init();

    }

    private void init() {
       Object[] dashboardGets =  dashboardDAO.getCountDashboard();
        String[] count = Arrays.toString(dashboardGets).split(", ");

        cardFletes.setData(new ModelCard(count[3].replace("]", ""), "Fletes del Mes", "/com/utils/icon/check.png"));
        cardOperadores.setData(new ModelCard(count[2], "Total Trabajadores", "/com/utils/icon/worker.png"));
        cardVehiculos.setData(new ModelCard(count[1], "Total Vehiculos", "/com/utils/icon/truck.png"));
        cardRecargas.setData(new ModelCard(count[0].substring(1), "Recargas del mes", "/com/utils/icon/gas-station.png"));

        // mostras fletes activos
        this.showFletesData(tblFletes);
        this.showServicesData(tblServicios);
    }

    private void showFletesData(JTable table) {
        Filter.removeAllRows(table);

        Object[] titles = new Object[]{
            "ID FLETE", "OPERADOR", "VEHICULO", "LUGAR TRABAJO", "CONCEPTO FLETE",
            "SALIDA", "RECIBE", "FECHA INICIO", "FECHA FIN", "STATUS"
        };
        /*coloco el nombre de las  columnas de la tabla USER a el modelo */
        DefaultTableModel model = new DefaultTableModel(null, titles) {

            // desactivamos la opcion de editar los datos en la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // RenderStatus render = new RenderStatus();

        //  table.setDefaultRenderer(Object.class, render);
        /* obtengo la lista de */
        List<Flete> list = dashboardDAO.getDataFleteOrderByDate();

        for (Flete flete : list) {

            model.addRow(new Object[]{
                flete.getId(),
                flete.getAsignacionUnidad().getOperador().getNombre() + " " + flete.getAsignacionUnidad().getOperador().getApePaterno(),
                flete.getAsignacionUnidad().getVehiculo().getMarca() + " Modelo: " + flete.getAsignacionUnidad().getVehiculo().getModelo() + " NO.SERIE: " + flete.getAsignacionUnidad().getVehiculo().getNumSerie(),
                flete.getLugarTrabajo().getClaveTrabajo() + " - " + flete.getLugarTrabajo().getNombreTrabajo() + " PERIODO: " + flete.getLugarTrabajo().getPeriodo(),
                flete.getConcepto(),
                flete.getLugarSalida(),
                flete.getRecibe(),
                flete.getAsignacionUnidad().getFechaInicio(),
                flete.getAsignacionUnidad().getFechaFin(),
                flete.getStatus(),});
            /*establecemos el modelo  al Jtable llamado jTabla*/

        }

        // table.setRowHeight(30);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.doLayout();
        /* asignamos el ancho de cada columna de la tabla*/

        int[] anchos = {80, 200, 300, 300, 150, 200, 150, 80, 200, 150};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    private void showServicesData(JTable table) {
        Filter.removeAllRows(table);
        Object[] titles = new Object[]{
            "NUM. SERIE", "MARCA", "MODELO", "TIPO", "DESCRIPCION", "KM",
            "FECHA PROX. SERVICIO"};
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

        List<Servicio> list = dashboardDAO.getServiceByMonth();
        if (list == null) {
            return;
        }
        for (Servicio servicio : list) {

            model.addRow(new Object[]{
                servicio.getVehiculo().getNumSerie(),
                servicio.getVehiculo().getMarca(),
                servicio.getVehiculo().getModelo(),
                servicio.getVehiculo().getType(),
                servicio.getVehiculo().getDescripcion(),
                servicio.getKm(),
                servicio.getProximoServicio()

            });
            /*establecemos el modelo  al Jtable llamado jTabla*/

        }

        // table.setRowHeight(30);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.doLayout();
        /* asignamos el ancho de cada columna de la tabla*/

        int[] anchos = {200, 200, 200, 200, 200, 200, 200};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        root = new javax.swing.JPanel();
        cards = new javax.swing.JPanel();
        cardOperadores = new com.utils.components.Card();
        cardVehiculos = new com.utils.components.Card();
        cardRecargas = new com.utils.components.Card();
        cardFletes = new com.utils.components.Card();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblFletes = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblServicios = new javax.swing.JTable();

        setLayout(new java.awt.CardLayout());

        cards.setLayout(new java.awt.GridLayout(1, 0, 11, 0));

        cardOperadores.setColor1(new java.awt.Color(0, 153, 255));
        cards.add(cardOperadores);

        cardVehiculos.setColor1(new java.awt.Color(204, 204, 0));
        cards.add(cardVehiculos);

        cardRecargas.setColor1(new java.awt.Color(102, 204, 0));
        cards.add(cardRecargas);

        cardFletes.setColor1(new java.awt.Color(255, 153, 0));
        cards.add(cardFletes);

        jLabel3.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel3.setText("Fletes activos");

        tblFletes.setAutoCreateRowSorter(true);
        tblFletes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblFletes);

        jLabel4.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N
        jLabel4.setText("Servicios de este mes");

        tblServicios.setAutoCreateRowSorter(true);
        tblServicios.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblServicios);

        javax.swing.GroupLayout rootLayout = new javax.swing.GroupLayout(root);
        root.setLayout(rootLayout);
        rootLayout.setHorizontalGroup(
            rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(rootLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1203, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cards, javax.swing.GroupLayout.DEFAULT_SIZE, 1215, Short.MAX_VALUE))
        );
        rootLayout.setVerticalGroup(
            rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootLayout.createSequentialGroup()
                .addGap(184, 184, 184)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(rootLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(cards, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(628, Short.MAX_VALUE)))
        );

        jScrollPane3.setViewportView(root);

        add(jScrollPane3, "card2");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.utils.components.Card cardFletes;
    private com.utils.components.Card cardOperadores;
    private com.utils.components.Card cardRecargas;
    private com.utils.components.Card cardVehiculos;
    private javax.swing.JPanel cards;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel root;
    private javax.swing.JTable tblFletes;
    private javax.swing.JTable tblServicios;
    // End of variables declaration//GEN-END:variables
}
