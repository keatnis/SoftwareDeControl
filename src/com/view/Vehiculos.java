package com.view;

import com.dao.DetalleCombustibleDAO;
import com.dao.VehiculosDAO;
import com.model.DetalleCombustible;
import com.model.Vehiculo;
import com.utils.Filter;
import com.utils.Validaciones;
import com.utils.table.RenderTable;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author keatnis
 */
public class Vehiculos extends javax.swing.JPanel {

    private VehiculosDAO vehiculosDao;

    private Vehiculo vehiculo;
    private DetalleCombustible detalleCombustible;
    private DetalleCombustibleDAO detalleCombustibleDAO;

    public Vehiculos() {
        initComponents();
        this.showForms(false, true);
        this.vehiculosDao = new VehiculosDAO();
        this.detalleCombustibleDAO = new DetalleCombustibleDAO();
        showData(tblVehiculos);
        clearAll();
        getTipoCombustible();
    }

    private void getTipoCombustible() {
        this.cmbTipoCombustible.removeAllItems();

        List<DetalleCombustible> ve = detalleCombustibleDAO.getAllDetalles();

        for (DetalleCombustible vehiculol : ve) {

            this.cmbTipoCombustible.addItem(vehiculol.getTipo());

            detalleCombustible = new DetalleCombustible();
            detalleCombustible.setId(vehiculol.getId());
            detalleCombustible.setPrecio(vehiculol.getPrecio());
            detalleCombustible.setTipo(vehiculol.getTipo());

        }
        this.cmbTipoCombustible.repaint();

    }

    private void showData(JTable table) {
        Filter.removeAllRows(table);
        Object[] titles = new Object[]{
            "ID", "MARCA", "MODELO", "DESCRIPCION", "NUM.SERIE", "KM/ACTUAL",
            "TIPO COMBUSTIBLE", "TIPO DE VEH.", "CAPACIDAD MAX. COMB.", "STATUS", "RENTA TERMINA"};
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
        List<Vehiculo> list = vehiculosDao.getAllVehiculos();

        for (Vehiculo vehiculo : list) {

            model.addRow(new Object[]{
                vehiculo.getId(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getDescripcion(),
                vehiculo.getNumSerie(),
                vehiculo.getKmActual(),
                vehiculo.getTipoCombustible(),
                vehiculo.getType(),
                vehiculo.getCapacidad(),
                vehiculo.getStatus(),
                vehiculo.getFinRenta()

            });
            /*establecemos el modelo  al Jtable llamado jTabla*/

        }

        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.doLayout();
        /* asignamos el ancho de cada columna de la tabla*/

        int[] anchos = {30, 100, 100, 150, 100, 100, 100, 100, 100, 100, 100};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    public void showForms(boolean showForm, boolean showList) {

        panelForm.setVisible(showForm);
        if (panelForm.isVisible()) {
            this.getAccessibleContext();
        }
        panelList.setVisible(showList);
    }

    private void clearAll() {
        txtMarca.setText("");
        txtModelo.setText("");
        txtNumSerie.setText("");
        txtKm.setText("");
        cmbTipoCombustible.setSelectedIndex(0);
        cmbTipo.setSelectedIndex(0);
        txtCapacidad.setText("");
        cmbEstado.setSelectedIndex(0);
        txtFechaFinRenta.setDate(null);
        txtDescripcion.setText("");
    }

    private void editAction() {
        int row = tblVehiculos.getSelectedRow();

        btnSave.setText("actualizar datos");
        btnNew.repaint();

        txtMarca.setText(String.valueOf(tblVehiculos.getValueAt(row, 1)));
        txtModelo.setText(String.valueOf(tblVehiculos.getValueAt(row, 2)));
        txtDescripcion.setText(String.valueOf(tblVehiculos.getValueAt(row, 3)));
        txtNumSerie.setText(String.valueOf(tblVehiculos.getValueAt(row, 4)));

        txtKm.setText(String.valueOf(tblVehiculos.getValueAt(row, 5)));
        cmbTipoCombustible.setSelectedItem(tblVehiculos.getValueAt(row, 6));
        cmbTipo.setSelectedItem(String.valueOf(tblVehiculos.getValueAt(row, 7)));
        txtCapacidad.setText(String.valueOf(tblVehiculos.getValueAt(row, 8)));
        cmbEstado.setSelectedItem(String.valueOf(tblVehiculos.getValueAt(row, 9)));
        txtFechaFinRenta.setDate(Validaciones.returnDate(
                String.valueOf(tblVehiculos.getValueAt(row, 10))));

    }

    private void deleteAction() {
        int idUser = Integer.valueOf(
                tblVehiculos.getValueAt(tblVehiculos.getSelectedRow(), 0)
                        .toString());
        vehiculosDao.delete(idUser);
        showData(tblVehiculos);

    }

    private void save() {
        vehiculo = new Vehiculo();
        vehiculo.setMarca(txtMarca.getText());
        vehiculo.setModelo(txtModelo.getText());
        vehiculo.setNumSerie(txtNumSerie.getText());
        vehiculo.setDescripcion(txtDescripcion.getText());
        vehiculo.setKmActual(Float.parseFloat(txtKm.getText()));
        vehiculo.setTipoCombustible((String) cmbTipoCombustible.getSelectedItem());
        vehiculo.setType((String) cmbTipo.getSelectedItem());
        vehiculo.setCapacidad(Float.parseFloat(txtCapacidad.getText()));
        vehiculo.setStatus((String) cmbEstado.getSelectedItem());

        //Parsing the given String to Date object
        Date date1 = txtFechaFinRenta.getDate();
//        Validaciones.dateNoSeleted(date1);
//        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
//        String fecha = (formatofecha.format(txtFechaFinRenta.getDate()));
        vehiculo.setFinRenta(txtFechaFinRenta.getDate());
        vehiculosDao.addVehiculo(vehiculo);
    }

    private void update() {
        vehiculo = new Vehiculo();
        int idUser = Integer.valueOf(
                tblVehiculos.getValueAt(tblVehiculos.getSelectedRow(), 0)
                        .toString());

        vehiculo.setId(idUser);
        vehiculo.setMarca(txtMarca.getText());
        vehiculo.setModelo(txtModelo.getText());
        vehiculo.setNumSerie(txtNumSerie.getText());
        vehiculo.setDescripcion(txtDescripcion.getText());
        vehiculo.setKmActual(Float.parseFloat(txtKm.getText()));
        vehiculo.setTipoCombustible((String) cmbTipoCombustible.getSelectedItem());
        vehiculo.setType((String) cmbTipo.getSelectedItem());
        vehiculo.setCapacidad(Float.parseFloat(txtCapacidad.getText()));
        vehiculo.setStatus((String) cmbEstado.getSelectedItem());
        vehiculo.setFinRenta(txtFechaFinRenta.getDate());

        vehiculosDao.update(vehiculo);
        this.showData(tblVehiculos);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTextField6 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        panelList = new javax.swing.JPanel();
        panelOptions = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        tbnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVehiculos = new javax.swing.JTable();
        txtSearch = new com.utils.components.txtPlaceholder();
        panelForm = new javax.swing.JPanel();
        formulario = new javax.swing.JPanel();
        lbMarca = new javax.swing.JLabel();
        lbMarca1 = new javax.swing.JLabel();
        lbMarca2 = new javax.swing.JLabel();
        lbMarca3 = new javax.swing.JLabel();
        cmbTipoCombustible = new javax.swing.JComboBox<>();
        lbMarca4 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField();
        txtNumSerie = new javax.swing.JTextField();
        txtKm = new javax.swing.JTextField();
        AddCombustible = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtCapacidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextPane();
        txtFechaFinRenta = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        btnCancelar = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        jTextField6.setText("jTextField6");

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.CardLayout());

        panelOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));
        panelOptions.setToolTipText("");
        panelOptions.setLayout(new java.awt.GridLayout(3, 1, 15, 10));

        btnNew.setText("Nuevo");
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

        tbnDelete.setText("eliminar");
        tbnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbnDeleteActionPerformed(evt);
            }
        });
        panelOptions.add(tbnDelete);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        tblVehiculos.setAutoCreateRowSorter(true);
        tblVehiculos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblVehiculos.setPreferredSize(null);
        tblVehiculos.setShowGrid(true);
        jScrollPane1.setViewportView(tblVehiculos);

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
            .addGroup(panelListLayout.createSequentialGroup()
                .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelListLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelListLayout.setVerticalGroup(
            panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelListLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panelListLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(panelOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel1.add(panelList, "card2");

        lbMarca.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbMarca.setText("Marca");

        lbMarca1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbMarca1.setText("Modelo");

        lbMarca2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbMarca2.setText("Num. Serie");

        lbMarca3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbMarca3.setText("Km. Actual");

        cmbTipoCombustible.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Diesel", "Magna", "Premium", "Otro" }));

        lbMarca4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbMarca4.setText("Tipo de Combustible");

        txtKm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKmKeyTyped(evt);
            }
        });

        AddCombustible.setText("+");
        AddCombustible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCombustibleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout formularioLayout = new javax.swing.GroupLayout(formulario);
        formulario.setLayout(formularioLayout);
        formularioLayout.setHorizontalGroup(
            formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formularioLayout.createSequentialGroup()
                .addGroup(formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(formularioLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(formularioLayout.createSequentialGroup()
                                .addComponent(lbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(formularioLayout.createSequentialGroup()
                                .addComponent(lbMarca1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtModelo))))
                    .addGroup(formularioLayout.createSequentialGroup()
                        .addGroup(formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbMarca4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbMarca3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbMarca2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNumSerie)
                            .addComponent(txtKm)
                            .addComponent(cmbTipoCombustible, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddCombustible)
                .addGap(31, 31, 31))
        );

        formularioLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lbMarca, lbMarca1, lbMarca2, lbMarca3});

        formularioLayout.setVerticalGroup(
            formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, formularioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbMarca1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbMarca2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMarca3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbMarca4)
                    .addGroup(formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbTipoCombustible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(AddCombustible))))
        );

        jLabel1.setText("Tipo ");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Excabadora", "Camion de", "Otro", " " }));

        jLabel2.setText("Capacidad de tanque/ L");

        txtCapacidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCapacidadKeyTyped(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Termino de Renta: ");

        txtDescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("Descripcion"));
        jScrollPane2.setViewportView(txtDescripcion);

        txtFechaFinRenta.setDateFormatString("dd/MM/yyyy");

        jLabel4.setText("Estado:");

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Contratado", "Activo", "Inactivo", " " }));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSave.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.focusColor"));
        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelFormLayout.createSequentialGroup()
                                .addComponent(formulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelFormLayout.createSequentialGroup()
                                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cmbTipo, 0, 138, Short.MAX_VALUE)
                                            .addComponent(txtCapacidad)
                                            .addComponent(cmbEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(panelFormLayout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtFechaFinRenta, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGap(308, 308, 308)
                        .addComponent(btnCancelar)
                        .addGap(48, 48, 48)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(130, Short.MAX_VALUE))
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(formulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtFechaFinRenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSave))
                .addContainerGap(131, Short.MAX_VALUE))
        );

        jPanel1.add(panelForm, "card3");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 0.8;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        showForms(true, false);
        btnNew.setEnabled(false);
        btnSave.setText("guardar");
        btnSave.repaint();

    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (tblVehiculos.getSelectedRow() >= 0) {
            editAction();
            showForms(true, false);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void tbnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnDeleteActionPerformed
        deleteAction();

    }//GEN-LAST:event_tbnDeleteActionPerformed

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        Filter.searchInTable(txtSearch, tblVehiculos);
    }//GEN-LAST:event_txtSearchKeyTyped

    private void txtKmKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKmKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);
    }//GEN-LAST:event_txtKmKeyTyped

    private void txtCapacidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCapacidadKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);
    }//GEN-LAST:event_txtCapacidadKeyTyped

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        if (btnSave.getText().endsWith("ardar")) {
            save();
            clearAll();
            this.showForms(false, true);
            // this.showForm(list);
            this.showData(tblVehiculos);
            btnNew.setEnabled(true);
        } else if (btnSave.getText().endsWith("datos")) {
            update();
            clearAll();
            this.showForms(false, true);
            btnNew.setEnabled(true);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        clearAll();
        btnNew.setEnabled(true);
        this.showForms(false, true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void AddCombustibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCombustibleActionPerformed
          DetallesCombustible dialog = new DetallesCombustible(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dialog.setVisible(false);
            }
        });
        dialog.setVisible(true);
    //    cmbTipoCombustible.removeAllItems();
        this.getTipoCombustible();
    //    this.cmbTipoCombustible.repaint();
        
        this.repaint();
        
    }//GEN-LAST:event_AddCombustibleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddCombustible;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JComboBox<String> cmbTipoCombustible;
    private javax.swing.JPanel formulario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JLabel lbMarca;
    private javax.swing.JLabel lbMarca1;
    private javax.swing.JLabel lbMarca2;
    private javax.swing.JLabel lbMarca3;
    private javax.swing.JLabel lbMarca4;
    private javax.swing.JPanel panelForm;
    private javax.swing.JPanel panelList;
    private javax.swing.JPanel panelOptions;
    private javax.swing.JTable tblVehiculos;
    private javax.swing.JButton tbnDelete;
    private javax.swing.JTextField txtCapacidad;
    private javax.swing.JTextPane txtDescripcion;
    private com.toedter.calendar.JDateChooser txtFechaFinRenta;
    private javax.swing.JTextField txtKm;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNumSerie;
    private com.utils.components.txtPlaceholder txtSearch;
    // End of variables declaration//GEN-END:variables
}
