package com.view;

import com.dao.ServiciosDAO;
import com.dao.VehiculosDAO;
import com.model.Servicio;
import com.model.Vehiculo;
import com.utils.ExportExcel;
import com.utils.Filter;
import com.utils.Validaciones;
import com.utils.table.RenderTable;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author keatnis
 */
/*
TODO: UPDATE KM ENTITY VEHICULO Y ACTUALIZAR 
 */
public class Servicios extends javax.swing.JPanel {

    public static String FECHA_SERVICIO;
    private Vehiculo vehiculo;
    private VehiculosDAO vehiculosDAO;
    private Servicio servicio;
    private ServiciosDAO serviciosDAO;

    public Servicios() {
        initComponents();
        clearAll();
        this.showForms(false, true);
        this.vehiculosDAO = new VehiculosDAO();
        this.serviciosDAO = new ServiciosDAO();
        this.showData(tblAllServices);

    }

    private void getITems() {
        String res = (String) this.cmbSearch.getEditor().getItem();
        cmbSearch.removeAllItems();

        List<Vehiculo> ve = vehiculosDAO.getByKey(res);

        for (Vehiculo vehiculol : ve) {

            cmbSearch.addItem(vehiculol.getId() + " - MARCA: " + vehiculol.getMarca() + " MODELO: "
                    + vehiculol.getModelo() + " NUM_SERIE: " + vehiculol.getDescripcion());

        }

        cmbSearch.repaint();
        cmbSearch.setPopupVisible(true);

//        vehiculo = new Vehiculo();
//        vehiculo.setId(ve.get(0).getId());
//        cmbSearch.addItemListener(new java.awt.event.ItemListener() {
//            public void itemStateChanged(java.awt.event.ItemEvent evt) {
//
//                vehiculo.setId(ve.get(cmbSearch.getSelectedIndex()).getId());
//
//            }
//        });
    }

    public void showForms(boolean showForm, boolean showList) {

        panelForm.setVisible(showForm);
        if (panelForm.isVisible()) {
            this.getAccessibleContext();
        }
        panelList.setVisible(showList);
    }

    private void validacionForm() {
        Validaciones.dateNoSeleted(txtFechaServicio);
        if (!rbEfectivo.isSelected() && !rbTransferencia.isSelected()) {
            JOptionPane.showMessageDialog(null, "METODO DE PAAGO NO SELECCIONADO");
            return;
        }


        /* MORE VALIDATION*/
    }

    private void clearAll() {
        txtKM.setText("");
        txtCantidad.setValue(0);
        txtPrecio.setText("");
        txtImporte.setText("");
        cmbEmpresa.removeAllItems();
        txtDescripcion.setText(null);
        txtObservacion.setText(null);
        txtFechaServicio.setDate(null);
        bgMetodoPago.clearSelection();
        txtFechaProximoServicio.setDate(null);
        cmbSearch.removeAllItems();

    }

    private void save() {
        servicio = new Servicio();
        validacionForm();
        servicio.setFecha(txtFechaServicio.getDate());
        servicio.setCantidad(Float.parseFloat(txtCantidad.getText()));
        servicio.setPrecio(Float.parseFloat(txtPrecio.getText()));
        servicio.setImporte(Float.parseFloat(txtImporte.getText()));
        servicio.setEmpresa((String) cmbEmpresa.getSelectedItem());
        servicio.setDescripcion(txtDescripcion.getText());
        servicio.setObservaciones(txtObservacion.getText());
        if (!bgMetodoPago.getSelection().isSelected()) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado el método de pago ","Aviso",JOptionPane.ERROR_MESSAGE);
            return;

        } else if (rbTransferencia.isSelected()) {

            servicio.setMetodoPago("TRANSFERENCIA/DEPOSITO");

        } else if (rbEfectivo.isSelected()) {
            servicio.setMetodoPago("EFECTIVO");
        };
        servicio.setProximoServicio(txtFechaProximoServicio.getDate());

        servicio.setKm(Float.parseFloat(txtKM.getText()));
        vehiculo = new Vehiculo();
        String[] idd = cmbSearch.getSelectedItem().toString().split(" ");
        vehiculo.setId(Integer.parseInt(idd[0]));
        idd = null;
        servicio.setVehiculo(vehiculo);
        System.out.println("id_vehiculo" + vehiculo.getId());
        //TODO: UPDATE KM IN ENTITY VEHICULO
        serviciosDAO.save(servicio);
        if (vehiculo.getKmActual() < Float.parseFloat(txtKM.getText())) {
            vehiculosDAO.updateKM(vehiculo.getId(), Float.parseFloat(txtKM.getText()));
        }
    }

    private void showData(JTable table) {
        Filter.removeAllRows(table);
        Object[] titles = new Object[]{
            "ID VEHICULO", "NUM. SERIE", "MARCA", "MODELO", "TIPO", "DESCRIPCION", "KM",
            "ID SERVICIO", "DESCRIPCION", "FECHA SERVICIO", "CANTIDAD", "PRECIO", "IMPORTE", "METODO DE PAGO", "EMPRESA RESPONSABLE", "OBSERVACIONES", "PROX. SERVICIO"};
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

        List<Servicio> list = serviciosDAO.getAllServicesByDate();

        for (Servicio servicio : list) {

            model.addRow(new Object[]{
                servicio.getVehiculo().getId(),
                servicio.getVehiculo().getNumSerie(),
                servicio.getVehiculo().getMarca(),
                servicio.getVehiculo().getModelo(),
                servicio.getVehiculo().getType(),
                servicio.getVehiculo().getDescripcion(),
                servicio.getKm(),
                servicio.getId(),
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

        int[] anchos = {80, 200, 150, 150, 80, 200, 80, 80, 200, 150, 80, 80, 180, 200, 180, 200, 150};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    private void deleteAction() {

        int idServicio = Integer.valueOf(
                tblAllServices.getValueAt(tblAllServices.getSelectedRow(), 7)
                        .toString());
        serviciosDAO.delete(idServicio);
        this.showData(tblAllServices);
    }

    private void update() {
        int idServicio = Integer.valueOf(
                tblAllServices.getValueAt(tblAllServices.getSelectedRow(), 7)
                        .toString());

        validacionForm();
        servicio = new Servicio();
        servicio.setId(idServicio);
        servicio.setFecha(txtFechaServicio.getDate());
        servicio.setCantidad(Float.parseFloat(txtCantidad.getText()));
        servicio.setPrecio(Float.parseFloat(txtPrecio.getText()));
        servicio.setImporte(Float.parseFloat(txtImporte.getText()));
        servicio.setEmpresa((String) cmbEmpresa.getSelectedItem());
        servicio.setDescripcion(txtDescripcion.getText());
        servicio.setObservaciones(txtObservacion.getText());
        if (!bgMetodoPago.getSelection().isSelected()) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado el método de pago ");
            return;

        } else if (rbTransferencia.isSelected()) {

            servicio.setMetodoPago("TRANSFERENCIA/DEPOSITO");

        } else if (rbEfectivo.isSelected()) {
            servicio.setMetodoPago("EFECTIVO");
        };
        servicio.setProximoServicio(txtFechaProximoServicio.getDate());
        servicio.setKm(Float.parseFloat(txtKM.getText()));
        vehiculo = new Vehiculo();
        int idVehiculo = Integer.valueOf(
                tblAllServices.getValueAt(tblAllServices.getSelectedRow(), 0)
                        .toString());
        vehiculo.setId(idVehiculo);

        servicio.setVehiculo(vehiculo);

        serviciosDAO.update(servicio);
        if (vehiculo.getKmActual() < Float.parseFloat(txtKM.getText())) {
            vehiculosDAO.updateKM(vehiculo.getId(), Float.parseFloat(txtKM.getText()));
        }
        showData(tblAllServices);
    }

    private void editAction() {
        int row = tblAllServices.getSelectedRow();

        btnSave.setText("Actualizar datos");
        btnNew.repaint();
        /*
          "ID VEHICULO", "NUM. SERIE", "MARCA", "MODELO", "TIPO", "DESCRIPCION", "KM",
            "ID SERVICIO", "DESCRIPCION", "FECHA SERVICIO","CANTIDAD","PRECIO", "IMPORTE", "METODO DE PAGO", "OBSERVACIONES"};
         */

        txtKM.setText(String.valueOf(tblAllServices.getValueAt(row, 6)));
        txtCantidad.setText(String.valueOf(tblAllServices.getValueAt(row, 10)));
        txtPrecio.setText(String.valueOf(tblAllServices.getValueAt(row, 11)));
        txtImporte.setText(String.valueOf(tblAllServices.getValueAt(row, 12)));
        cmbEmpresa.addItem(String.valueOf(tblAllServices.getValueAt(row, 14)));
        // cmbEmpresa.setSelectedItem(String.valueOf(tblAllServices.getValueAt(row, 15)));
        txtDescripcion.setText(String.valueOf(tblAllServices.getValueAt(row, 8)));
        txtObservacion.setText(String.valueOf(tblAllServices.getValueAt(row, 15)));
        String fechaServicio = String.valueOf(tblAllServices.getValueAt(row, 9));
        txtFechaServicio.setDate(Validaciones.returnDate(fechaServicio));

        String metodo = String.valueOf(tblAllServices.getValueAt(row, 13));
        if (metodo.equals("EFECTIVO")) {
            rbEfectivo.setSelected(true);
        } else {
            rbTransferencia.setSelected(true);
        }
        String proxServ = String.valueOf(tblAllServices.getValueAt(row, 16));
        txtFechaProximoServicio.setDate(Validaciones.returnDate(proxServ));
        showForms(true, false);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        bgMetodoPago = new javax.swing.ButtonGroup();
        panelList = new javax.swing.JPanel();
        panelOptions = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblAllServices = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        txtSearch = new com.utils.components.txtPlaceholder();
        panelForm = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnSearch = new javax.swing.JButton();
        cmbSearch = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtImporte = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextPane();
        jLabel8 = new javax.swing.JLabel();
        cmbEmpresa = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservacion = new javax.swing.JTextPane();
        rbEfectivo = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        rbTransferencia = new javax.swing.JRadioButton();
        btnSave = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtPrecio = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFechaProximoServicio = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        txtCantidad = new javax.swing.JFormattedTextField();
        txtKM = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtFechaServicio = new com.toedter.calendar.JDateChooser();

        setLayout(new java.awt.GridBagLayout());

        panelOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));
        panelOptions.setLayout(new java.awt.GridLayout(4, 0, 0, 5));

        btnNew.setText("Nevo registro");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        panelOptions.add(btnNew);

        btnEdit.setText("editar");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        panelOptions.add(btnEdit);

        btnEliminar.setText("eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        panelOptions.add(btnEliminar);

        btnExportar.setText("exportar");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });
        panelOptions.add(btnExportar);

        tblAllServices.setAutoCreateRowSorter(true);
        tblAllServices.setModel(new javax.swing.table.DefaultTableModel(
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
        tblAllServices.setPreferredSize(null);
        jScrollPane4.setViewportView(tblAllServices);

        jLabel13.setText("Ultimos servicios registrados");

        txtSearch.setPlaceholder("Buscar ...");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panelListLayout = new javax.swing.GroupLayout(panelList);
        panelList.setLayout(panelListLayout);
        panelListLayout.setHorizontalGroup(
            panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListLayout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(357, 357, 357))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addComponent(panelOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        panelListLayout.setVerticalGroup(
            panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelListLayout.createSequentialGroup()
                        .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(panelList, gridBagConstraints);

        btnSearch.setText("Buscar");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        cmbSearch.setEditable(true);
        cmbSearch.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSearchItemStateChanged(evt);
            }
        });

        jLabel2.setText("Fecha del servicio");

        jLabel10.setText("Kilometraje actual");

        jLabel3.setText("Cantidad");

        jLabel5.setText("Precio Unitario");

        jLabel7.setText("Metodo de Pago");

        jLabel6.setText("Importe");

        txtImporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtImporteKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel4.setText("Descripcion del Servicio efectuado");

        jScrollPane1.setViewportView(txtDescripcion);

        jLabel8.setText("Empresa Responsable");

        cmbEmpresa.setEditable(true);

        jLabel9.setText("Observaciones");

        jScrollPane2.setViewportView(txtObservacion);

        bgMetodoPago.add(rbEfectivo);
        rbEfectivo.setText("Efectivo");

        bgMetodoPago.add(rbTransferencia);
        rbTransferencia.setText("<html><center>Transferencia/Depósito</center></html>");
        rbTransferencia.setMaximumSize(null);
        rbTransferencia.setName("Transferencia"); // NOI18N

        btnSave.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.focusColor"));
        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtPrecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrecioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Proximo servicio"));

        jLabel1.setText("Fecha");

        txtFechaProximoServicio.setDateFormatString("dd/MM/yyyy");
        txtFechaProximoServicio.setName(""); // NOI18N

        jButton2.setText("calcular prox.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtFechaProximoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaProximoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        txtCantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel4))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(61, 61, 61)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(rbEfectivo)
                            .addComponent(rbTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btnCancelar)
                            .addGap(34, 34, 34)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(198, 198, 198))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbEmpresa, txtImporte});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbEfectivo))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave)
                            .addComponent(btnCancelar))
                        .addGap(16, 16, 16)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(rbTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(150, 150, 150))
        );

        txtKM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKMKeyTyped(evt);
            }
        });

        jLabel11.setText("Vehivulo");

        txtFechaServicio.setDateFormatString("dd/MM/yyyy");
        txtFechaServicio.setName(""); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(406, 406, 406)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtKM, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtFechaServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1063, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSearch)
                    .addComponent(btnSearch)
                    .addComponent(jLabel11))
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(panelForm, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        getITems();

    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        this.showForms(true, false);
        btnNew.setEnabled(false);
        btnSave.setText("guardar");
        btnSave.repaint();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (btnSave.getText().endsWith("ardar")) {
            save();
            clearAll();
            this.showForms(false, true);
            // this.showForm(list);
            this.showData(tblAllServices);
            btnNew.setEnabled(true);
        } else if (btnSave.getText().startsWith("Actualizar")) {
            update();
            clearAll();
            this.showForms(false, true);

            btnNew.setEnabled(true);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        clearAll();
        this.showForms(false, true);
        this.btnNew.setEnabled(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        Filter.searchInTable(txtSearch, tblAllServices);
    }//GEN-LAST:event_txtSearchKeyTyped

    private void txtImporteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImporteKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);
    }//GEN-LAST:event_txtImporteKeyTyped

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (tblAllServices.getSelectedRow() >= 0) {
            this.showForms(true, false);
            editAction();

        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        this.deleteAction();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtKMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKMKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);
    }//GEN-LAST:event_txtKMKeyTyped

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);

    }//GEN-LAST:event_txtPrecioKeyTyped

    private void txtPrecioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyReleased
        Float calcular = Float.valueOf(txtCantidad.getText()) * Float.valueOf(txtPrecio.getText());
        txtImporte.setText(String.valueOf(calcular));
    }//GEN-LAST:event_txtPrecioKeyReleased

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        try {
            ExportExcel.exportarExcel(tblAllServices);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnExportarActionPerformed

    private void cmbSearchItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSearchItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSearchItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgMetodoPago;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbEmpresa;
    private javax.swing.JComboBox<String> cmbSearch;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel panelForm;
    private javax.swing.JPanel panelList;
    private javax.swing.JPanel panelOptions;
    private javax.swing.JRadioButton rbEfectivo;
    private javax.swing.JRadioButton rbTransferencia;
    private javax.swing.JTable tblAllServices;
    private javax.swing.JFormattedTextField txtCantidad;
    private javax.swing.JTextPane txtDescripcion;
    private com.toedter.calendar.JDateChooser txtFechaProximoServicio;
    private com.toedter.calendar.JDateChooser txtFechaServicio;
    private javax.swing.JTextField txtImporte;
    private javax.swing.JTextField txtKM;
    private javax.swing.JTextPane txtObservacion;
    private javax.swing.JFormattedTextField txtPrecio;
    private com.utils.components.txtPlaceholder txtSearch;
    // End of variables declaration//GEN-END:variables
}
