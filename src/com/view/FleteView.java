package com.view;

import com.dao.DetalleCombustibleDAO;
import com.dao.FleteDAO;
import com.dao.LugarTrabajoDAO;
import com.dao.TrabajadoresDAO;
import com.dao.VehiculosDAO;
import com.model.AsignacionUnidad;
import java.util.List;
import com.model.DetalleCombustible;
import com.model.Flete;
import com.model.Operador;
import com.model.Vehiculo;
import com.model.Workplace;
import java.util.Date;
import com.model.RecargaCombustible;
import com.utils.ExportExcel;
import com.utils.Filter;
import com.utils.Validaciones;
import com.utils.table.RenderStatus;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author keatnis
 */
public class FleteView extends javax.swing.JPanel {

    private VehiculosDAO vehiculoDao;

    private Vehiculo vehiculo;
    private Operador operador;
    private Workplace workplace;

    private DetalleCombustible detalleCombustible;
    private DetalleCombustibleDAO detalleCombustibleDAO;
    private TrabajadoresDAO trabajadoresDAO;
    private LugarTrabajoDAO workplaceDAO;

    private AsignacionUnidad asignacionUnidad;
    private Flete flete;
    //  private LugarTrabajo lugarTrabajo;
    private RecargaCombustible recargaCombustible;
    private float KM_VEHICULO;
    private FleteDAO fleteDAO;
    private Float KM_actual;
//EEE MMM dd HH:mm:ss z yyyy
    public FleteView() {
        initComponents();
        this.detalleCombustibleDAO = new DetalleCombustibleDAO();
        this.vehiculoDao = new VehiculosDAO();
        this.trabajadoresDAO = new TrabajadoresDAO();
        this.fleteDAO = new FleteDAO();
        this.workplaceDAO = new LugarTrabajoDAO();

        this.showForms(false, true);
        this.showData(tblFlete);

        getTipoCombustible();
        this.cmbTipoCompustible.setSelectedIndex(0);
        getWorkplaces();

    }

    /*
        Puede cambiar los colores del status en com.utils.table.RenderStatus
     */
    public void showData(JTable table) {
        Filter.removeAllRows(table);

        Object[] titles = new Object[]{
            "ID FLETE", "OPERADOR", "VEHICULO", "LUGAR TRABAJO", "CONCEPTO FLETE",
            "SALIDA", "RECIBE", "FECHA INICIO", "FECHA FIN", "STATUS", "KM INICIAL", "KM FINAL",
            "ID ASIG.", "ID lUGAR TRABAJO", "ID OPERADOR", "ID VEHICULO", "RESPONSABLE CARGA",
            "ODOMETRO ACTUAL", "PRECIOXLITRO", "LITROS", "TIPO COMB.", "MONTO", "GASOLINERA", "TIPO DE PAGO", "ID RECARGA"};
        /*coloco el nombre de las  columnas de la tabla USER a el modelo */
        DefaultTableModel model = new DefaultTableModel(null, titles) {

            // desactivamos la opcion de editar los datos en la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        RenderStatus render = new RenderStatus();

        table.setDefaultRenderer(Object.class, render);
        /* obtengo la lista de */

        List<Flete> list = fleteDAO.getDataFlete();

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
                flete.getStatus(),
                flete.getAsignacionUnidad().getKmInicio(),
                flete.getAsignacionUnidad().getKmFinal(),
                flete.getAsignacionUnidad().getId(),
                flete.getLugarTrabajo().getId(),
                flete.getAsignacionUnidad().getOperador().getId(),
                flete.getAsignacionUnidad().getVehiculo().getId(),
                flete.getResponsable(),
                flete.getRecargaCombustible().getOdometroActual(),
                flete.getRecargaCombustible().getPrecioxlitro(),
                flete.getRecargaCombustible().getLitros(),
                flete.getRecargaCombustible().getTipoCombustible(),
                flete.getRecargaCombustible().getMonto(),
                flete.getRecargaCombustible().getGasolinera(),
                flete.getRecargaCombustible().getMetodoPago(),
                flete.getRecargaCombustible().getId()

            });
            /*establecemos el modelo  al Jtable llamado jTabla*/

        }

        // table.setRowHeight(30);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.doLayout();
        /* asignamos el ancho de cada columna de la tabla*/

        int[] anchos = {80, 200, 300, 300, 200, 200, 200, 200, 200, 150, 150, 150,
            0, 0, 0, 0, 100, 100, 100, 100, 100, 100, 100, 100, 100};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        this.hideColumns(table, new int[]{12, 13, 14, 15, 24});

    }

    public void hideColumns(JTable tbl, int columna[]) {
        for (int i = 0; i < columna.length; i++) {
            tbl.getColumnModel().getColumn(columna[i]).setMaxWidth(0);
            tbl.getColumnModel().getColumn(columna[i]).setMinWidth(0);
            tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMaxWidth(0);
            tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMinWidth(0);
        }
    }

    private String parse12hTo24h(String time) {
        String result = null;
        if (!time.isEmpty()) {
            result
                    = // Text representing the value of our date-time object.
                    LocalTime.parse( // Class representing a time-of-day value without a date and without a time zone.
                            time, // Your `String` input text.
                            DateTimeFormatter.ofPattern( // Define a formatting pattern to match your input text.
                                    "hh:mm a",
                                    Locale.US // `Locale` determines the human language and cultural norms used in localization. Needed here to translate the `AM` & `PM` value.
                            ) // Returns a `DateTimeFormatter` object.
                    ) // Return a `LocalTime` object.
                            .format(DateTimeFormatter.ofPattern("HH:mm"));
//LocalTime.parse(result)
        }

        return result;
    }

    private LocalTime parse24hTo12h(String time) {
        //   time = Hora1.getText().substring(0, 5);

        //  String HoraFinal = Hora2.getText().substring(0, 5);
        // LocalTime hourStart = LocalTime.parse(horaInicio);
        String result
                = // Text representing the value of our date-time object.
                LocalTime.parse( // Class representing a time-of-day value without a date and without a time zone.
                        time, // Your `String` input text.
                        DateTimeFormatter.ofPattern( // Define a formatting pattern to match your input text.
                                "hh:mm",
                                Locale.US // `Locale` determines the human language and cultural norms used in localization. Needed here to translate the `AM` & `PM` value.
                        ) // Returns a `DateTimeFormatter` object.
                ) // Return a `LocalTime` object.
                        .format(DateTimeFormatter.ofPattern("HH:mm a"));

        return LocalTime.parse(result);
    }

    private void validateForm() {
        // operador
        //actualizar el km del vehiculo
        Validaciones.cmbEditorNoSelected(cmbOperador,
                "No ha seleccionado el operador, introduzca los datos en el campo y luego pulse el boton buscar para buscar en la base de datos.");
        Validaciones.cmbEditorNoSelected(cmbVehiculo,
                "No ha introducido los datos del vehiculo. Favor de introducirlos.");
        Validaciones.dateNoSeleted(fechaRecarga, "Fecha de recarga");
        Validaciones.esCajaVacia(txtLitrosCargados, "Introduzca los litros cargados, en caso de que no se ha cargado debe de ponder el valor 0");
    }

    private void save() {
        validateForm();
        flete = new Flete();
        asignacionUnidad = new AsignacionUnidad();
        vehiculo = new Vehiculo();
        String[] vehiculoID = cmbVehiculo.getSelectedItem().toString().split(" ");

        vehiculo.setId(Integer.parseInt(vehiculoID[0]));
        vehiculoID = null;
        operador = new Operador();
        String[] operadorID = cmbOperador.getSelectedItem().toString().split(" ");

        operador.setId(Integer.parseInt(operadorID[0]));
        operadorID = null;
        asignacionUnidad.setVehiculo(vehiculo);
        asignacionUnidad.setOperador(operador);

        String horaInicio = this.parse12hTo24h(Hora1.getText());
        String horaFinal = this.parse12hTo24h(Hora2.getText());

        asignacionUnidad.setFechaInicio(Validaciones.dateTimeReturn(startDate, horaInicio, "Inicio"));
        asignacionUnidad.setFechaFin(Validaciones.dateTimeReturn(endDate, horaFinal, "Final/Termino"));
        asignacionUnidad.setKmInicio(Float.parseFloat(txtKMIncicial.getText()));
        asignacionUnidad.setKmFinal(Float.parseFloat(txtKMFinal.getText()));

        recargaCombustible = new RecargaCombustible();
        recargaCombustible.setOdometroActual(Float.parseFloat(txtOdometroActual.getText()));
        recargaCombustible.setPrecioxlitro(detalleCombustible.getPrecio());
        recargaCombustible.setLitros(Float.parseFloat(txtLitrosCargados.getText()));
        recargaCombustible.setTipoCombustible(detalleCombustible.getTipo());
        recargaCombustible.setMonto(Float.parseFloat(txtMonto.getText()));
        recargaCombustible.setGasolinera(cmbGasolinera.getEditor().getItem().toString());
        recargaCombustible.setFechaRecarga(fechaRecarga.getDate());
        if (!rbEfectivo.isSelected() && !rbTransferencia.isSelected()) {
            JOptionPane.showMessageDialog(null, "no ha seleccionado el tipo de pago");
            return;
        } else if (rbEfectivo.isSelected()) {
            recargaCombustible.setMetodoPago(rbEfectivo.getText());
        } else if (rbTransferencia.isSelected()) {
            recargaCombustible.setMetodoPago(rbTransferencia.getText());
        }

        // recargaCombustible.setAsignacionUnidad(asignacionUnidad);
        String[] workString = cmbLugarTrabajo.getSelectedItem().toString().split(" ");
        if (workString.length < 0) {
            JOptionPane.showMessageDialog(null, "Lugar de trabajo no seleccionado");
        }
        workplace = new Workplace();
        workplace.setId(Integer.parseInt(workString[0]));
        flete.setLugarTrabajo(workplace);
        flete.setLugarSalida((String) cmbSalida.getSelectedItem());
        flete.setConcepto(txtConcepto.getText());
        flete.setRecibe((String) cmbRecibe.getSelectedItem());
        flete.setStatus((String) cmbStatusFlete.getSelectedItem());
        flete.setResponsable(cmbResponsableCarga.getEditor().getItem().toString());
        fleteDAO.saveFlete(flete, recargaCombustible, asignacionUnidad);

        this.showData(tblFlete);
        this.showForms(false, true);
        if (!txtKMFinal.getText().equals("0") && KM_actual < Float.valueOf(txtKMIncicial.getText())) {
            vehiculoDao.updateKM(vehiculo.getId(), Float.valueOf(txtKMIncicial.getText()));
        } else if (!txtKMFinal.equals("0") && KM_actual < Float.valueOf(txtKMFinal.getText())) {
            vehiculoDao.updateKM(vehiculo.getId(), Float.valueOf(txtKMFinal.getText()));
        } else {
            JOptionPane.showMessageDialog(null,
                    "no se actualizo el Kilometraje del vehiculo, puede actualizar los datos usando la opcion editar desde el listado de los datos");
        }

    }

    private void update() {

    }

    private void editAction() {
        /*

                    "ID FLETE", "OPERADOR", "VEHICULO", "LUGAR TRABAJO", "CONCEPTO FLETE",4
            "SALIDA", "RECIBE", "FECHA INICIO", "FECHA FIN", "STATUS", "KM INICIAL", "KM FINAL",11
            "ID ASIG.", "ID lUGAR TRABAJO", "ID OPERADOR", "ID VEHICULO", "ID RESPONSABLE", "RESPONSABLE CARGA",17
            "ODOMETRO ACTUAL", "PRECIOXLITRO", "LITROS", "TIPO COMB.", "MONTO","GASOLINERA","TIPO DE PAGO"}; 24
         */
        int row = tblFlete.getSelectedRow();

        txtConcepto.setText(String.valueOf(tblFlete.getValueAt(row, 4)));
        Hora1.setText("0000");
        txtKMFinal.setText(String.valueOf(tblFlete.getValueAt(row, 11)));
        txtKMIncicial.setText(String.valueOf(tblFlete.getValueAt(row, 10)));
        txtLitrosCargados.setText(String.valueOf(tblFlete.getValueAt(row, 20)));
        txtMonto.setText(String.valueOf(tblFlete.getValueAt(row, 22)));
        txtOdometroActual.setText(String.valueOf(tblFlete.getValueAt(row, 18)));
        startDate.setDate(Validaciones.returnDate(String.valueOf(tblFlete.getValueAt(row, 7))));
        endDate.setDate(Validaciones.returnDate(String.valueOf(tblFlete.getValueAt(row, 8))));
        cmbGasolinera.setSelectedIndex(0);
        cmbLugarTrabajo.setSelectedItem(String.valueOf(tblFlete.getValueAt(row, 13)));
        cmbOperador.setSelectedItem(String.valueOf(tblFlete.getValueAt(row, 14)));
        cmbRecibe.setSelectedItem(String.valueOf(tblFlete.getValueAt(row, 6)));
        cmbResponsableCarga.setSelectedItem(String.valueOf(tblFlete.getValueAt(row, 16)));
        cmbSalida.setSelectedItem(String.valueOf(tblFlete.getValueAt(row, 13)));
        cmbStatusFlete.setSelectedItem(String.valueOf(tblFlete.getValueAt(row, 9)));
        cmbTipoCompustible.setSelectedItem(String.valueOf(tblFlete.getValueAt(row, 21)));
        cmbVehiculo.setSelectedItem(String.valueOf(tblFlete.getValueAt(row, 15)));
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return LocalDate.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    public void showForms(boolean showForm, boolean showList) {

        panelForm.setVisible(showForm);
        if (panelForm.isVisible()) {
            this.getAccessibleContext();
        }

        panelList.setVisible(showList);
    }

    private void getTipoCombustible() {
        this.cmbTipoCompustible.removeAllItems();

        List<DetalleCombustible> ve = detalleCombustibleDAO.getAllDetalles();

        for (DetalleCombustible vehiculol : ve) {

            this.cmbTipoCompustible.addItem(vehiculol.getTipo());

        }

        cmbTipoCompustible.repaint();
        if (ve.size() == 1) {
            cmbTipoCompustible.setSelectedIndex(0);
            detalleCombustible = new DetalleCombustible();
            detalleCombustible.setId(ve.get(cmbTipoCompustible.getSelectedIndex()).getId());
            detalleCombustible.setPrecio(ve.get(cmbTipoCompustible.getSelectedIndex()).getPrecio());

            detalleCombustible.setTipo(ve.get(cmbTipoCompustible.getSelectedIndex()).getTipo());
        } else {
            cmbTipoCompustible.setSelectedIndex(0);
            detalleCombustible = new DetalleCombustible();
            detalleCombustible.setId(ve.get(cmbTipoCompustible.getSelectedIndex()).getId());
            detalleCombustible.setPrecio(ve.get(cmbTipoCompustible.getSelectedIndex()).getPrecio());
            detalleCombustible.setTipo(ve.get(cmbTipoCompustible.getSelectedIndex()).getTipo());
        }

        cmbTipoCompustible.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                detalleCombustible = new DetalleCombustible();
                detalleCombustible.setId(ve.get(cmbTipoCompustible.getSelectedIndex()).getId());
                detalleCombustible.setPrecio(ve.get(cmbTipoCompustible.getSelectedIndex()).getPrecio());
                detalleCombustible.setTipo(ve.get(cmbTipoCompustible.getSelectedIndex()).getTipo());

            }
        });

        cmbTipoCompustible.repaint();

    }

    private void getWorkplaces() {
        this.cmbLugarTrabajo.removeAllItems();

        List<Workplace> ve = workplaceDAO.getWorkplaces();

        for (Workplace workplacel : ve) {

            this.cmbLugarTrabajo.addItem(workplacel.getId() + " " + workplacel.getClaveTrabajo() + " " + workplacel.getNombreTrabajo());

        }

        this.cmbLugarTrabajo.repaint();
        //cmbLugarTrabajo.setPopupVisible(true);

    }

    private void getVehiculo() {
        String res = (String) this.cmbVehiculo.getEditor().getItem();
        if (!res.isEmpty()) {
            this.cmbVehiculo.removeAllItems();

            List<Vehiculo> ve = vehiculoDao.getByKey(res);

            for (Vehiculo vehiculol : ve) {

                cmbVehiculo.addItem(vehiculol.getId() + " MARCA: " + vehiculol.getMarca() + " MODELO: "
                        + vehiculol.getModelo() + " NUM_SERIE: " + vehiculol.getDescripcion());

            }
            vehiculo = new Vehiculo();
            this.cmbVehiculo.repaint();
            if (ve.size() == 1) {
                cmbVehiculo.setSelectedIndex(0);

                vehiculo.setId(ve.get(0).getId());
                vehiculo.setTipoCombustible(ve.get(0).getTipoCombustible());
                String kmactual = String.valueOf(ve.get(0).getKmActual());
                KM_actual = Float.parseFloat(kmactual);
                txtKMIncicial.setText(kmactual);
            } else {
                vehiculo.setId(ve.get(cmbVehiculo.getSelectedIndex()).getId());
                vehiculo.setTipoCombustible(ve.get(cmbVehiculo.getSelectedIndex()).getTipoCombustible());
                txtKMIncicial.setText(String.valueOf(ve.get(cmbVehiculo.getSelectedIndex()).getKmActual()));
            }

            cmbVehiculo.setPopupVisible(true);

            cmbVehiculo.addItemListener(new java.awt.event.ItemListener() {
                @Override
                public void itemStateChanged(java.awt.event.ItemEvent evt) {

                    vehiculo.setId(ve.get(cmbVehiculo.getSelectedIndex()).getId());
                    vehiculo.setTipoCombustible(ve.get(cmbVehiculo.getSelectedIndex()).getTipoCombustible());
                    txtKMIncicial.setText(String.valueOf(ve.get(cmbVehiculo.getSelectedIndex()).getKmActual()));
                }
            }
            );

        } else {
            this.cmbOperador.removeAllItems();
            JOptionPane.showMessageDialog(null,
                    "Introduzca los datos del vehiculo ");
            return;
        }

    }

    private void deleteAction() {

    }

    private void searchOperador() {
        String res = (String) this.cmbOperador.getEditor().getItem();
        if (!res.isEmpty()) {
            this.cmbOperador.removeAllItems();

            List<Operador> ve = trabajadoresDAO.searchOperador(res);

            for (Operador operadorl : ve) {

                this.cmbOperador.addItem(operadorl.getId() + " " + operadorl.getNombre() + " " + operadorl.getApePaterno() + " " + operadorl.getApeMaterno());

            }
            this.cmbOperador.repaint();
            cmbOperador.setPopupVisible(true);
            operador = new Operador();
//            if (ve.size()==1) {
//                
//            }
//            this.cmbOperador.addItemListener(new java.awt.event.ItemListener() {
//                @Override
//                public void itemStateChanged(java.awt.event.ItemEvent evt) {
//                    
//                    txtKMIncicial.setText(ve.get(cmbOperador.getSelectedIndex()).);
//                    operador.setId(ve.get(cmbOperador.getSelectedIndex()).getId());
//                }

         //   });
        } else {
            this.cmbOperador.removeAllItems();
            JOptionPane.showMessageDialog(null,
                    "Introduzca los datos del operador nombre y/o apellido, de clic en buscar y seleccione los datos del operador.");
            return;
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tipoPago = new javax.swing.ButtonGroup();
        dateUtil1 = new com.toedter.calendar.DateUtil();
        panelForm = new javax.swing.JScrollPane();
        panelForm2 = new javax.swing.JPanel();
        asignacion = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmbVehiculo = new javax.swing.JComboBox<>();
        cmbOperador = new javax.swing.JComboBox<>();
        btnSearchVehicle = new javax.swing.JButton();
        btnOperador = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtKMIncicial = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtKMFinal = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        endDate = new com.toedter.calendar.JDateChooser();
        startDate = new com.toedter.calendar.JDateChooser();
        Hora1 = new com.utils.components.TimeChooser();
        Hora2 = new com.utils.components.TimeChooser();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        combustible = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbGasolinera = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        fechaRecarga = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        rbEfectivo = new javax.swing.JRadioButton();
        rbTransferencia = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        cmbTipoCompustible = new javax.swing.JComboBox<>();
        txtLitrosCargados = new javax.swing.JTextField();
        txtMonto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtOdometroActual = new javax.swing.JTextField();
        btnDetalleCombustible = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        detallesFlete = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        cmbLugarTrabajo = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        cmbStatusFlete = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        cmbSalida = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        cmbResponsableCarga = new javax.swing.JComboBox<>();
        cmbTrabajador = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtConcepto = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        cmbRecibe = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        panelList = new javax.swing.JPanel();
        panelOptions = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnChangeStatus = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFlete = new javax.swing.JTable();
        txtSearch = new com.utils.components.txtPlaceholder();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        lbActivo = new javax.swing.JLabel();
        lbTerminado = new javax.swing.JLabel();
        lbBloqueado = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        panelForm.setToolTipText("");
        panelForm.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panelForm2.setLayout(new javax.swing.BoxLayout(panelForm2, javax.swing.BoxLayout.Y_AXIS));

        asignacion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Asignacion Unidad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cantarell", 1, 18))); // NOI18N
        asignacion.setToolTipText("");
        asignacion.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N

        jLabel4.setText("Vehiculo/Unidad");

        jLabel9.setText("Operador");

        cmbVehiculo.setEditable(true);
        cmbVehiculo.setToolTipText("Puede buscar por modelo, marca o por numero de serie");
        cmbVehiculo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmbVehiculoMouseEntered(evt);
            }
        });

        cmbOperador.setEditable(true);
        cmbOperador.setToolTipText("Buscar por nombre");

        btnSearchVehicle.setText("Buscar");
        btnSearchVehicle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchVehicleActionPerformed(evt);
            }
        });

        btnOperador.setText("Buscar");
        btnOperador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOperadorActionPerformed(evt);
            }
        });

        jLabel10.setText("* se mostraran las unidades disponibles");

        jLabel13.setText("Fecha Inicio");

        jLabel15.setText("KM inicial");

        txtKMIncicial.setForeground(new java.awt.Color(204, 0, 0));
        txtKMIncicial.setText("0");
        txtKMIncicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKMIncicialKeyTyped(evt);
            }
        });

        jLabel14.setText("Fecha Fin");

        txtKMFinal.setText("0");
        txtKMFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKMFinalKeyTyped(evt);
            }
        });

        jLabel16.setText("KM final");

        endDate.setDateFormatString("dd/MM/yyyy");
        endDate.setMaximumSize(null);

        startDate.setDateFormatString("dd/MM/yyyy");
        startDate.setMaximumSize(null);

        Hora1.setToolTipText("Horario de 12 h incluido AM / PM");
        Hora1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Hora1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Hora1MouseEntered(evt);
            }
        });

        Hora2.setToolTipText("Horario de 12 h incluido AM / PM");
        Hora2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Hora2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Hora2MouseEntered(evt);
            }
        });

        jLabel22.setText("Hora:");

        jLabel23.setText("Hora:");

        javax.swing.GroupLayout asignacionLayout = new javax.swing.GroupLayout(asignacion);
        asignacion.setLayout(asignacionLayout);
        asignacionLayout.setHorizontalGroup(
            asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(asignacionLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(asignacionLayout.createSequentialGroup()
                        .addGroup(asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(asignacionLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKMIncicial)
                                    .addGroup(asignacionLayout.createSequentialGroup()
                                        .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Hora1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(cmbVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16)
                            .addGroup(asignacionLayout.createSequentialGroup()
                                .addComponent(btnSearchVehicle)
                                .addGap(36, 36, 36)
                                .addComponent(jLabel9)))
                        .addGap(18, 18, 18)
                        .addGroup(asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtKMFinal)
                            .addGroup(asignacionLayout.createSequentialGroup()
                                .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Hora2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbOperador, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOperador)))
                .addContainerGap(625, Short.MAX_VALUE))
        );
        asignacionLayout.setVerticalGroup(
            asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(asignacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(2, 2, 2)
                .addGroup(asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(cmbVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchVehicle)
                    .addComponent(jLabel9)
                    .addComponent(cmbOperador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOperador))
                .addGap(31, 31, 31)
                .addGroup(asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Hora1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(jLabel23))
                    .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Hora2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22)))
                .addGap(10, 10, 10)
                .addGroup(asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(txtKMIncicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(asignacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtKMFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)))
                .addGap(35, 35, 35))
        );

        panelForm2.add(asignacion);

        combustible.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recarga de combustible", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cantarell", 1, 18))); // NOI18N
        combustible.setToolTipText("");

        jLabel1.setText("Gasolinera");

        cmbGasolinera.setEditable(true);

        jLabel2.setText("Fecha:");

        fechaRecarga.setDateFormatString("dd/MM/yyyy");
        fechaRecarga.setMaximumSize(null);

        jLabel3.setText("Tipo de pago:");

        tipoPago.add(rbEfectivo);
        rbEfectivo.setText("Efectivo");

        tipoPago.add(rbTransferencia);
        rbTransferencia.setText("Transferencia/Deposito");

        jLabel5.setText("Tipo de Combustible:");

        cmbTipoCompustible.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTipoCompustibleItemStateChanged(evt);
            }
        });

        txtLitrosCargados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLitrosCargadosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLitrosCargadosKeyTyped(evt);
            }
        });

        txtMonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoKeyTyped(evt);
            }
        });

        jLabel6.setText("Litros Cargados");

        jLabel7.setText("Monto $");

        jLabel8.setText("Odometro actual:");

        txtOdometroActual.setText("0");

        btnDetalleCombustible.setText("Ajuste ");
        btnDetalleCombustible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetalleCombustibleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout combustibleLayout = new javax.swing.GroupLayout(combustible);
        combustible.setLayout(combustibleLayout);
        combustibleLayout.setHorizontalGroup(
            combustibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(combustibleLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(combustibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(combustibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaRecarga, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                    .addComponent(cmbTipoCompustible, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtLitrosCargados)
                    .addComponent(txtMonto))
                .addGap(20, 20, 20)
                .addComponent(btnDetalleCombustible)
                .addGap(15, 15, 15)
                .addGroup(combustibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(combustibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOdometroActual)
                    .addGroup(combustibleLayout.createSequentialGroup()
                        .addGroup(combustibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbEfectivo)
                            .addComponent(rbTransferencia))
                        .addGap(0, 169, Short.MAX_VALUE))
                    .addComponent(cmbGasolinera, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(695, 695, 695))
        );
        combustibleLayout.setVerticalGroup(
            combustibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(combustibleLayout.createSequentialGroup()
                .addGroup(combustibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(combustibleLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(combustibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel2)
                            .addComponent(fechaRecarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(cmbGasolinera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, combustibleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(combustibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel5)
                            .addComponent(cmbTipoCompustible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDetalleCombustible)
                            .addComponent(jLabel3)
                            .addComponent(rbEfectivo))
                        .addGap(18, 18, 18)))
                .addGroup(combustibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtLitrosCargados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbTransferencia))
                .addGap(24, 24, 24)
                .addGroup(combustibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtOdometroActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66))
        );

        panelForm2.add(combustible);

        detallesFlete.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle flete", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cantarell", 1, 18))); // NOI18N

        jLabel11.setText("Lugar de trabajo");

        cmbLugarTrabajo.setEditable(true);
        cmbLugarTrabajo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", " " }));

        jLabel17.setText("Estatus");

        cmbStatusFlete.setEditable(true);
        cmbStatusFlete.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "En espera", "Preparado", "Activo", "Terminado", "Bloqueado", "Descargado", " " }));

        jLabel18.setText("Lugar salida");

        cmbSalida.setEditable(true);
        cmbSalida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Almacen", " " }));

        jLabel19.setText("Responsable de carga");

        cmbResponsableCarga.setEditable(true);
        cmbResponsableCarga.setToolTipText("Buscar por nombre");

        cmbTrabajador.setText("Buscar");
        cmbTrabajador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTrabajadorActionPerformed(evt);
            }
        });

        jLabel21.setText("Concepto");

        txtConcepto.setColumns(20);
        txtConcepto.setRows(5);
        jScrollPane1.setViewportView(txtConcepto);

        jLabel20.setText("Recibe");

        cmbRecibe.setEditable(true);
        cmbRecibe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Destino 1", " " }));

        javax.swing.GroupLayout detallesFleteLayout = new javax.swing.GroupLayout(detallesFlete);
        detallesFlete.setLayout(detallesFleteLayout);
        detallesFleteLayout.setHorizontalGroup(
            detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detallesFleteLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbSalida, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbLugarTrabajo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
                .addGap(129, 129, 129)
                .addGroup(detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detallesFleteLayout.createSequentialGroup()
                        .addComponent(cmbStatusFlete, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(detallesFleteLayout.createSequentialGroup()
                        .addGroup(detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbResponsableCarga, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbRecibe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addComponent(cmbTrabajador)))
                .addGap(173, 173, 173))
        );
        detallesFleteLayout.setVerticalGroup(
            detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detallesFleteLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel11)
                    .addComponent(cmbLugarTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel18)
                    .addComponent(cmbSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(detallesFleteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detallesFleteLayout.createSequentialGroup()
                        .addGroup(detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel17)
                            .addComponent(cmbStatusFlete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel19)
                            .addComponent(cmbResponsableCarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbTrabajador))
                        .addGap(37, 37, 37))
                    .addGroup(detallesFleteLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(detallesFleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbRecibe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(detallesFlete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 482, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(detallesFlete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelForm2.add(jPanel1);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancel, new java.awt.GridBagConstraints());

        btnSave.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.focusColor"));
        btnSave.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel2.add(btnSave, new java.awt.GridBagConstraints());

        panelForm2.add(jPanel2);

        jPanel3.setPreferredSize(new java.awt.Dimension(977, 50));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1801, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        panelForm2.add(jPanel3);

        panelForm.setViewportView(panelForm2);

        add(panelForm, java.awt.BorderLayout.CENTER);

        panelOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));
        panelOptions.setLayout(new java.awt.GridLayout(1, 7, 0, 5));

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
        panelOptions.add(jSeparator1);

        btnChangeStatus.setText("Cambiar Estatus");
        btnChangeStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeStatusActionPerformed(evt);
            }
        });
        panelOptions.add(btnChangeStatus);

        tblFlete.setAutoCreateRowSorter(true);
        tblFlete.setModel(new javax.swing.table.DefaultTableModel(
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
        tblFlete.setShowGrid(true);
        jScrollPane2.setViewportView(tblFlete);

        txtSearch.setPlaceholder("Buscar ...");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        jPanel4.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jLabel12.setText("Estatus:    ");
        jPanel4.add(jLabel12);

        lbActivo.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        lbActivo.setForeground(new java.awt.Color(204, 204, 0));
        lbActivo.setText("Activo");
        lbActivo.setIconTextGap(10);
        jPanel4.add(lbActivo);

        lbTerminado.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        lbTerminado.setForeground(new java.awt.Color(51, 204, 0));
        lbTerminado.setText("Terminado");
        lbTerminado.setIconTextGap(10);
        jPanel4.add(lbTerminado);

        lbBloqueado.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        lbBloqueado.setForeground(new java.awt.Color(153, 0, 0));
        lbBloqueado.setText("Bloqueado");
        lbBloqueado.setIconTextGap(10);
        jPanel4.add(lbBloqueado);

        javax.swing.GroupLayout panelListLayout = new javax.swing.GroupLayout(panelList);
        panelList.setLayout(panelListLayout);
        panelListLayout.setHorizontalGroup(
            panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelListLayout.createSequentialGroup()
                        .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelListLayout.createSequentialGroup()
                                .addComponent(panelOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 846, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35))
                    .addGroup(panelListLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelListLayout.setVerticalGroup(
            panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListLayout.createSequentialGroup()
                .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        add(panelList, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDetalleCombustibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetalleCombustibleActionPerformed

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
    }//GEN-LAST:event_btnDetalleCombustibleActionPerformed

    private void cmbTipoCompustibleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTipoCompustibleItemStateChanged

        txtMonto.setText("");
        if (!txtMonto.getText().equals("")) {
            Float precioxLitro = this.detalleCombustible.getPrecio();
            Float litros = Float.parseFloat(txtLitrosCargados.getText());
            txtMonto.setText(litros * precioxLitro + "");
        }

    }//GEN-LAST:event_cmbTipoCompustibleItemStateChanged

    private void txtLitrosCargadosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLitrosCargadosKeyReleased

        Float precioxLitro = detalleCombustible.getPrecio();
        Float litros = Float.parseFloat(txtLitrosCargados.getText());
        txtMonto.setText(String.format("%.2f", litros * precioxLitro));

    }//GEN-LAST:event_txtLitrosCargadosKeyReleased

    private void cmbVehiculoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbVehiculoMouseEntered
        cmbVehiculo.setToolTipText("Puede buscar por modelo, marca o por numero de serie");
    }//GEN-LAST:event_cmbVehiculoMouseEntered

    private void btnSearchVehicleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchVehicleActionPerformed
        getVehiculo();
        cmbTipoCompustible.setSelectedItem(vehiculo.getTipoCombustible());
    }//GEN-LAST:event_btnSearchVehicleActionPerformed

    private void btnOperadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOperadorActionPerformed
        searchOperador();
    }//GEN-LAST:event_btnOperadorActionPerformed

    private void cmbTrabajadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTrabajadorActionPerformed
        searchTrabajador();
    }//GEN-LAST:event_cmbTrabajadorActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (btnSave.getText().endsWith("ardar")) {
            save();
            clearAll();

            btnNew.setEnabled(true);
        } else if (btnSave.getText().startsWith("Actualizar")) {
            // update();
            clearAll();
            this.showForms(false, true);

            btnNew.setEnabled(true);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtKMIncicialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKMIncicialKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);
    }//GEN-LAST:event_txtKMIncicialKeyTyped

    private void txtKMFinalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKMFinalKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);
    }//GEN-LAST:event_txtKMFinalKeyTyped

    private void txtMontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);

    }//GEN-LAST:event_txtMontoKeyTyped

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        this.showForms(true, false);
        btnNew.setEnabled(false);
        btnSave.setText("guardar");
        btnSave.repaint();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (tblFlete.getSelectedRow() >= 0) {
            this.showForms(true, false);
            editAction();

        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        this.deleteAction();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        try {
            ExportExcel.exportarExcel(tblFlete);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnExportarActionPerformed

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        Filter.searchInTable(txtSearch, tblFlete);
    }//GEN-LAST:event_txtSearchKeyTyped

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        clearAll();
        this.showForms(false, true);
        this.btnNew.setEnabled(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnChangeStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeStatusActionPerformed
        StatusFlete dialog = new StatusFlete(new javax.swing.JFrame(), true);
        if (tblFlete.getSelectedRow() >= 0) {
            int row = tblFlete.getSelectedRow();
            String status = String.valueOf(tblFlete.getValueAt(row, 9));

            String fechaInicial = String.valueOf(tblFlete.getValueAt(row, 7));
            String fechaFinal = String.valueOf(tblFlete.getValueAt(row, 8));
            LocalDateTime dateEnd = null, dateStart = null;

            if (!fechaFinal.equals("null")) {

                dateEnd = LocalDateTime.parse(fechaFinal);
            }
            if (!fechaInicial.equals("null")) {
                dateStart = LocalDateTime.parse(fechaInicial);
            }

            String kmInicial = String.valueOf(tblFlete.getValueAt(row, 10));
            String kmFinal = String.valueOf(tblFlete.getValueAt(row, 11));
            int idFlete = Integer.parseInt(tblFlete.getValueAt(row, 0).toString());
            int idAsig = Integer.parseInt(tblFlete.getValueAt(row, 12).toString());
            int idWork = Integer.parseInt(tblFlete.getValueAt(row, 13).toString());
            int idOperador = Integer.parseInt(tblFlete.getValueAt(row, 14).toString());
            int idVehiculo = Integer.parseInt(tblFlete.getValueAt(row, 15).toString());
            int idRecarga = Integer.parseInt(tblFlete.getValueAt(row, 24).toString());
            dialog.dataFlete(idFlete, idAsig, idWork, idOperador,
                    idVehiculo, idRecarga, status, dateStart, dateEnd, kmInicial, kmFinal);
            dialog.setVisible(true);
        }
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //showData(tblFlete);
                dialog.setVisible(false);

            }

        });
        if (!dialog.isVisible()) {
            this.showData(tblFlete);
        }

    }//GEN-LAST:event_btnChangeStatusActionPerformed

    private void txtLitrosCargadosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLitrosCargadosKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);
    }//GEN-LAST:event_txtLitrosCargadosKeyTyped

    private void Hora1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Hora1MouseEntered
        Hora1.setToolTipText("Horario de 12 h incluido AM / PM");
    }//GEN-LAST:event_Hora1MouseEntered

    private void Hora2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Hora2MouseEntered
        Hora2.setToolTipText("Horario de 12 h incluido AM / PM");
    }//GEN-LAST:event_Hora2MouseEntered

    private void Hora1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Hora1MouseClicked

        Hora1.updateUI();
        Hora1.repaint();
    }//GEN-LAST:event_Hora1MouseClicked

    private void Hora2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Hora2MouseClicked
        Hora1.updateUI();
        Hora1.repaint();
    }//GEN-LAST:event_Hora2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.utils.components.TimeChooser Hora1;
    private com.utils.components.TimeChooser Hora2;
    private javax.swing.JPanel asignacion;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnChangeStatus;
    private javax.swing.JButton btnDetalleCombustible;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnOperador;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearchVehicle;
    private javax.swing.JComboBox<String> cmbGasolinera;
    private javax.swing.JComboBox<String> cmbLugarTrabajo;
    private javax.swing.JComboBox<String> cmbOperador;
    private javax.swing.JComboBox<String> cmbRecibe;
    private javax.swing.JComboBox<String> cmbResponsableCarga;
    private javax.swing.JComboBox<String> cmbSalida;
    private javax.swing.JComboBox<String> cmbStatusFlete;
    private javax.swing.JComboBox<String> cmbTipoCompustible;
    private javax.swing.JButton cmbTrabajador;
    private javax.swing.JComboBox<String> cmbVehiculo;
    private javax.swing.JPanel combustible;
    private com.toedter.calendar.DateUtil dateUtil1;
    private javax.swing.JPanel detallesFlete;
    private com.toedter.calendar.JDateChooser endDate;
    private com.toedter.calendar.JDateChooser fechaRecarga;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbActivo;
    private javax.swing.JLabel lbBloqueado;
    private javax.swing.JLabel lbTerminado;
    private javax.swing.JScrollPane panelForm;
    private javax.swing.JPanel panelForm2;
    private javax.swing.JPanel panelList;
    private javax.swing.JPanel panelOptions;
    private javax.swing.JRadioButton rbEfectivo;
    private javax.swing.JRadioButton rbTransferencia;
    private com.toedter.calendar.JDateChooser startDate;
    private javax.swing.JTable tblFlete;
    private javax.swing.ButtonGroup tipoPago;
    private javax.swing.JTextArea txtConcepto;
    private javax.swing.JTextField txtKMFinal;
    private javax.swing.JTextField txtKMIncicial;
    private javax.swing.JTextField txtLitrosCargados;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtOdometroActual;
    private com.utils.components.txtPlaceholder txtSearch;
    // End of variables declaration//GEN-END:variables

    private void searchTrabajador() {
        String res = (String) this.cmbResponsableCarga.getEditor().getItem();
        if (!res.isEmpty()) {
            this.cmbResponsableCarga.removeAllItems();

            List<Operador> ve = trabajadoresDAO.searchOperador(res);

            for (Operador operadorl : ve) {

                this.cmbResponsableCarga.addItem(operadorl.getId() + " " + operadorl.getNombre() + " " + operadorl.getApePaterno() + " " + operadorl.getApeMaterno());

            }
            this.cmbResponsableCarga.repaint();
            cmbResponsableCarga.setPopupVisible(true);

        } else {
            this.cmbResponsableCarga.removeAllItems();
            JOptionPane.showMessageDialog(null,
                    "Introduzca los datos del operador nombre y/o apellido, de clic en buscar y seleccione los datos del operador.");
            return;
        }

    }

    private void clearAll() {
        txtConcepto.setText("");
        Hora1.setText("");
        Hora2.setText("");
        txtKMFinal.setText("0");
        txtKMIncicial.setText("0");
        txtLitrosCargados.setText("0");
        txtMonto.setText("0");
        txtOdometroActual.setText("0");
        startDate.setDate(null);
        endDate.setDate(null);
        //  cmbGasolinera.setSelectedIndex(0);
        //cmbLugarTrabajo.setSelectedIndex(-1);
        cmbOperador.removeAllItems();
        cmbRecibe.setSelectedIndex(0);
        //  cmbResponsableCarga.setSelectedIndex(0);
        cmbSalida.setSelectedIndex(0);
        cmbStatusFlete.setSelectedIndex(0);
        cmbTipoCompustible.setSelectedIndex(0);
        cmbResponsableCarga.removeAllItems();
        cmbVehiculo.removeAllItems();
        fechaRecarga.setDate(null);
        cmbGasolinera.getEditor().setItem("");
    }
}
