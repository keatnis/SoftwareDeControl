package com.view;

import com.dao.NominaDAO;
import com.dao.TrabajadoresDAO;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatFormattedTextField;
import com.model.Nomina;
import com.model.Operador;
import com.toedter.calendar.JDateChooser;
import com.utils.DropXlsx;
import com.utils.Filter;
import com.utils.Validaciones;
import com.utils.table.RenderTable;
import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author keatnis
 */
public class CalcularNomina extends javax.swing.JPanel {

    private TrabajadoresDAO trabajadoresDAO;
    private Operador operador;
    private Nomina nomina;
    private NominaDAO nominaDAO;
    private int[] workDays;
    private Object fechaUltmPago;
    Cursor WAIT_CURSOR
            = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
    Cursor DEFAULT_CURSOR
            = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    boolean isWaitCursorShowing = false;

    public CalcularNomina() {
        initComponents();
        this.trabajadoresDAO = new TrabajadoresDAO();
        this.nominaDAO = new NominaDAO();
        this.showForms(true, false);
//        DropXlsx dropXlsx = new DropXlsx();
//        dropXlsx.setJtable(tblNomina);
    }

    private void save() {
        nomina = new Nomina();
        operador = new Operador();
        String[] idOperador = cmbSearch.getSelectedItem().toString().split(" ");
        int idoperadorint = Integer.parseInt(idOperador[0]);

        operador.setId(idoperadorint);
        nomina.setOperador(operador);
        nomina.setFechaPago(payDate.getDate());
        nomina.setFechaInicio(startDate.getDate());
        nomina.setFechaFin(endDate.getDate());
        nomina.setSueldoDiario(Float.parseFloat(txtSueldoDiario.getText()));
        nomina.setDiasLaborados(Integer.parseInt(txtDiasLaborales.getText()));
        nomina.setSueldoNeto(Float.parseFloat(txtTotalSueldoNeto.getText()));
        nomina.setDescuentos(Float.valueOf(txtPrestamos.getText()));

        nominaDAO.save(nomina);
    }

    private void saveAll(JTable table) {
        /*
            Object[] titles = new Object[]{
                     "ID", "NOMBRE", "A.PATERNO", "A.MATERNO", "PUESTO", "TELEFONO", "ULTM. PAGO",6
                     "SUELDO DIARIO", "DIAS LABORADOS", "SUBTOTAL", "DESCUENTOS", "TOTAL", "FECHA DE PAGO", "DiasLaborales"} 13;
         */

        for (int i = 0; i < table.getRowCount(); i++) {
            nomina = new Nomina();
            operador = new Operador((int) table.getValueAt(i, 0));
            nomina.setOperador(operador);
            nomina.setFechaPago(Validaciones.returnDate(String.valueOf(table.getValueAt(i, 12))));
            nomina.setFechaInicio(startDateALL.getDate());
            nomina.setFechaFin(endDateALL.getDate());
            nomina.setSueldoDiario((float) table.getValueAt(i, 7));
            nomina.setDiasLaborados((int) table.getValueAt(i, 8));
            nomina.setSueldoNeto((float) table.getValueAt(i, 11));
            nomina.setDescuentos((float) table.getValueAt(i, 9));
         //   nomina.setFechaPago(Validaciones.returnDate(TOOL_TIP_TEXT_KEY));
            nominaDAO.saveAll(nomina);
        }
        JOptionPane.showMessageDialog(null, "Datos guardados");
    }

    private void searchOperador() {
        String res = (String) this.cmbSearch.getEditor().getItem();
        fechaUltmPago = null;
        //  clearAll();
        if (!res.isEmpty()) {
            this.cmbSearch.removeAllItems();

            List<Operador> ve = trabajadoresDAO.searchOperador(res);

            for (Operador operadorl : ve) {

                this.cmbSearch.addItem(operadorl.getId() + " " + operadorl.getNombre() + " "
                        + operadorl.getApePaterno() + " " + operadorl.getApeMaterno());

            }
            this.cmbSearch.repaint();
            cmbSearch.setPopupVisible(true);
            operador = null;
            operador = new Operador();
            operador = ve.get(cmbSearch.getSelectedIndex());
            fechaUltmPago = nominaDAO.getLastPayDate(operador.getId());

            if (ve.size() >= 0) {

                txtSueldoDiario.setText(operador.getJob().getSueldoDiario() + "");
                txtSueldoDiario.repaint();
                workDays = operador.getJob().getDiasLaborales();
                mostrarDiasLaborales(workDays);
                if (fechaUltmPago == null) {
                    lbFechaUltimoPago.setText("No hay registro.");
                } else {
                    lbFechaUltimoPago.setText(fechaUltmPago.toString());

                }

            } else {
                return;
            }
            cmbSearch.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent ie) {
                    if (ve.size() >= 0 && !res.isEmpty()) {

                        operador = new Operador();
                        operador = ve.get(cmbSearch.getSelectedIndex());
                        txtSueldoDiario.setText(operador.getJob().getSueldoDiario() + "");
                        txtSueldoDiario.repaint();
                        workDays = operador.getJob().getDiasLaborales();
                        mostrarDiasLaborales(workDays);
                        if (fechaUltmPago == null) {
                            lbFechaUltimoPago.setText("No hay registro.");
                        } else {
                            lbFechaUltimoPago.setText(fechaUltmPago.toString());

                        }

                    }
                }
            });
        } else {
            this.cmbSearch.removeAllItems();
            JOptionPane.showMessageDialog(null,
                    "Introduzca los datos del operador nombre y/o apellido,"
                    + " de clic en buscar y seleccione los datos del operador.");
        }

    }

    public int diasHabiles(Calendar fechaInicial, Calendar fechaFinal, List<Date> listaFechasNoLaborables, int[] diasTrabajo) {
        int diffDays = 0;

        boolean diaHabil = false;
        //mientras la fecha inicial sea menor o igual que la fecha final se cuentan los dias
        if (diasTrabajo != null && diasTrabajo.length > 0) {
            while (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)) {

                if (!listaFechasNoLaborables.isEmpty()) {
                    for (Date date : listaFechasNoLaborables) {
                        Date fechaNoLaborablecalendar = fechaInicial.getTime();
                        //si el dia de la semana de la fecha minima es diferente de sabado o domingo
                        for (int i = 0; i < diasTrabajo.length; i++) {
                            if (fechaInicial.get(Calendar.DAY_OF_WEEK) == diasTrabajo[i] && !fechaNoLaborablecalendar.equals(date)) {
                                //se aumentan los dias de diferencia entre min y max
                                diaHabil = true;
                                diffDays++;
                            } else {
                                diaHabil = false;
                                break;
                            }
                        }

                    }
                } else {
                    for (int i = 0; i < diasTrabajo.length; i++) {
                        if (fechaInicial.get(Calendar.DAY_OF_WEEK) == diasTrabajo[i]) {
                            //se aumentan los dias de diferencia entre min y max
                            diffDays++;
                        }
                    }

                }
                if (diaHabil == true) {
                    diffDays++;
                }
                //se suma 1 dia para hacer la validacion del siguiente dia.
                fechaInicial.add(Calendar.DATE, 1);
            }
        }

        return diffDays;
    }

    private void calcularNominaIndividual() {
        List<Date> diasNoTrabajados = new ArrayList<>();
        String[] idOperador = cmbSearch.getSelectedItem().toString().split(" ");
        int idoperadorint = Integer.parseInt(idOperador[0]);

        int diasLaborados = this.diasHabiles(startDate.getCalendar(),
                endDate.getCalendar(), diasNoTrabajados, workDays);
        txtDiasLaborales.setText("" + diasLaborados);
        float sueldo = Float.parseFloat(txtSueldoDiario.getText());

        float subtotal = diasLaborados * sueldo;
        txtSueldoSub.setText(subtotal + "");
        /*
            calcular el total incluyendo los prestamos y tiempos extras durante el periodo seleccionado
         */
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = startDate.getCalendar().getTime()
                .toString();
        String endDateString = (formatofecha.format(endDate.getDate()));
        Double prestamos = nominaDAO.getTotalPrestanos(idoperadorint, startDateString, endDateString);
        // float extras = 0;
        if (prestamos == null) {
            prestamos = 0.0;
        }
        txtPrestamos.setText(String.format("%.2f", prestamos) + "");
        txtPrestamos.repaint();
        float neto = (float) (subtotal - prestamos);

        txtTotalSueldoNeto.setText(String.format("%.2f", neto) + "");
    }

    private void mostrarDiasLaborales(int[] dias) {

        cbMonday.setSelected((dias[0] != 0));
        cbThusday.setSelected(dias[1] != 0);
        cbWenesday.setSelected((dias[2] > 0));
        cbThurday.setSelected(dias[3] != 0);
        cbFriday.setSelected(dias[4] != 0);
        cbSaturday.setSelected(dias[5] != 0);
        cbSunday.setSelected(dias[0] != 0);
    }

    private Double getTotalPrestamos(Integer idOperador, Object startDateObj) {

        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");

        String startDateString = (formatofecha.format(this.lastDateNomina(startDateObj).getTime()));
        String endDateString = (formatofecha.format(endDateALL.getDate()));
        Double resultadoPrestamos;
        resultadoPrestamos = nominaDAO.getTotalPrestanos(idOperador, startDateString, endDateString);
        if (resultadoPrestamos == null) {
            return resultadoPrestamos = 0.0;
        } else {
            return resultadoPrestamos;

        }

    }

    private int calcularDias(int[] diasSemana, Object lastDate) {

        List< Date> diasNoTrabajados = new ArrayList<>();
      
        return this.diasHabiles(lastDateNomina(lastDate),
                endDateALL.getCalendar(), diasNoTrabajados, diasSemana);
    }

    private Calendar lastDateNomina(Object lastDate) {
        Calendar ultmoDiaNominaAnt = Calendar.getInstance();
        /*
        para los que no se les registro la fecha del ultimo calculo, se tomara el de calendario    
        verificar si esta correcto el aumento de fecha
         */
        if (cbUltFecha.isSelected() && lastDate != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                ultmoDiaNominaAnt.setTime(sdf.parse(lastDate.toString()));// all done
                ultmoDiaNominaAnt.add(Calendar.DATE, 1);
                //   System.out.println("dede " + ultmoDiaNominaAnt.getTime());

            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            return ultmoDiaNominaAnt;
        } else {
            return ultmoDiaNominaAnt = startDateALL.getCalendar();

        }

    }

    private Float sustraccion(Object subtotal, Object descuentos) {
        Float sub = Float.valueOf(subtotal.toString());
        Float desc = Float.valueOf(descuentos.toString());

        return (sub - desc);
    }

    private void calcularNominaTodos(JTable table) {
        if (isWaitCursorShowing != true) {
            isWaitCursorShowing = true;
            this.setCursor(WAIT_CURSOR);
        }
        Filter.removeAllRows(table);

        List<Date> diasNoTrabajados = new ArrayList<>();

        Object[] titles = new Object[]{
            "ID", "NOMBRE", "A.PATERNO", "A.MATERNO", "PUESTO", "TELEFONO", "ULTM. PAGO",
            "SUELDO DIARIO", "DIAS LABORADOS", "SUBTOTAL", "DESCUENTOS", "TOTAL", "FECHA DE PAGO", "PAGADO", "DEL."};
        /*coloco el nombre de las  columnas de la tabla USER a el modelo */
        DefaultTableModel model;
        model = new DefaultTableModel(null, titles) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return !(column != 12 && column != 13);

            }

            @Override
            public Class getColumnClass(int columnIndex) {
                if (columnIndex != 13) {
                    return super.getColumnClass(columnIndex);
                } else {
                    return Boolean.class;
                }
            }

            @Override
            public Object getValueAt(int row, int column) {

                switch (column) {
                    case 9:
                        return Float.valueOf(getValueAt(row, 7).toString()) * Integer.valueOf(getValueAt(row, 8).toString());
                    //   return "2";
                    case 10:
                        return getTotalPrestamos((int) getValueAt(row, 0), getValueAt(row, 6));
                    case 11:
                        return sustraccion(getValueAt(row, 9), getValueAt(row, 10));
                    default:
                        return super.getValueAt(row, column);
                }

            }

        };
        RenderTable render = new RenderTable();

        table.setDefaultRenderer(Object.class,
                render);
//        /* obtengo la lista de */
        JButton deleteButton = new JButton(new FlatSVGIcon("com/utils/icon/delete.svg"));
        List<Operador> list = trabajadoresDAO.operadores();
       
        for (Operador op : list) {
                   
          
            model.addRow(new Object[]{
                op.getId(),
                op.getNombre(),
                op.getApePaterno(),
                op.getApeMaterno(),
                op.getJob().getPuesto(),
                op.getTelefono(),
                nominaDAO.getLastPayDate(op.getId()),
                op.getJob().getSueldoDiario(),
                calcularDias(op.getJob().getDiasLaborales(),
                nominaDAO.getLastPayDate(op.getId())),
                null,
                null,
                null,
                null,
                false,
                deleteButton
            }
            );
            /*establecemos el modelo  al Jtable llamado jTabla*/

        }

        table.setRowHeight(
                30);
        table.setModel(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.doLayout();
        /* asignamos el ancho de cada columna de la tabla*/

        int[] anchos = {30, 150, 150, 200, 150, 150, 150, 200, 150, 100, 100, 100,
            100, 150, 50};
        for (int i = 0;
                i < table.getColumnCount();
                i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        if (isWaitCursorShowing) {
            isWaitCursorShowing = false;
            this.setCursor(DEFAULT_CURSOR);
        }
    }

    public void showForms(boolean nominaOne, boolean nominaALL) {

        this.nominaALL.setVisible(nominaALL);
        if (this.nominaALL.isVisible()) {
            this.getAccessibleContext();
        }

        this.nominaOne.setVisible(nominaOne);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tipoNomina = new javax.swing.ButtonGroup();
        dropXlsx1 = new com.utils.DropXlsx();
        panel = new javax.swing.JTabbedPane();
        nominaOne = new javax.swing.JPanel();
        cmbSearch = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSueldoDiario = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDiasLaborales = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPrestamos = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSueldoSub = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        startDate = new com.toedter.calendar.JDateChooser();
        endDate = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtTotalSueldoNeto = new javax.swing.JTextField();
        btnCalcular = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        payDate = new com.toedter.calendar.JDateChooser();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lbFechaUltimoPago = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        cbMonday = new javax.swing.JCheckBox();
        cbThusday = new javax.swing.JCheckBox();
        cbWenesday = new javax.swing.JCheckBox();
        cbThurday = new javax.swing.JCheckBox();
        cbFriday = new javax.swing.JCheckBox();
        cbSaturday = new javax.swing.JCheckBox();
        cbSunday = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        btnPrestamo = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        nominaALL = new javax.swing.JPanel();
        startDateALL = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        endDateALL = new com.toedter.calendar.JDateChooser();
        cbUltFecha = new javax.swing.JCheckBox();
        btnCalcularALL = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNomina = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtSearch = new com.utils.components.txtPlaceholder();

        setLayout(new java.awt.BorderLayout());

        cmbSearch.setEditable(true);

        jLabel4.setText("Trabajador");

        jLabel6.setText("Sueldo diario");

        jLabel7.setText("Dias laborados");

        jLabel9.setText("Prestamos");

        txtPrestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrestamosActionPerformed(evt);
            }
        });

        jLabel11.setText("Subtotal");

        jLabel12.setText("Fecha inicio");

        jLabel13.setText("Fecha corte");

        jLabel14.setText("Total");

        txtTotalSueldoNeto.setForeground(new java.awt.Color(0, 153, 255));

        btnCalcular.setText("Calcular Nomina");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        jLabel15.setText("Fecha pago");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles"));

        jLabel8.setText("Ultimo pago");

        lbFechaUltimoPago.setFont(new java.awt.Font("Cantarell", 0, 14)); // NOI18N
        lbFechaUltimoPago.setForeground(new java.awt.Color(255, 102, 51));

        jLabel5.setText("Dias de trabajo:");

        jToolBar1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jToolBar1.setEnabled(false);
        jToolBar1.setMargin(new java.awt.Insets(2, 10, 0, 2));
        jToolBar1.setName("dis"); // NOI18N

        cbMonday.setText("L");
        cbMonday.setEnabled(false);
        cbMonday.setFocusable(false);
        cbMonday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbMonday.setIconTextGap(8);
        cbMonday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbMonday);

        cbThusday.setText("M");
        cbThusday.setEnabled(false);
        cbThusday.setFocusable(false);
        cbThusday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbThusday.setIconTextGap(8);
        cbThusday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbThusday);

        cbWenesday.setText("M");
        cbWenesday.setEnabled(false);
        cbWenesday.setFocusable(false);
        cbWenesday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbWenesday.setIconTextGap(8);
        cbWenesday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbWenesday);

        cbThurday.setText("J");
        cbThurday.setEnabled(false);
        cbThurday.setFocusable(false);
        cbThurday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbThurday.setIconTextGap(8);
        cbThurday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbThurday);

        cbFriday.setText("V");
        cbFriday.setEnabled(false);
        cbFriday.setFocusable(false);
        cbFriday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbFriday.setIconTextGap(8);
        cbFriday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbFriday);

        cbSaturday.setText("S");
        cbSaturday.setEnabled(false);
        cbSaturday.setFocusable(false);
        cbSaturday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbSaturday.setIconTextGap(8);
        cbSaturday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbSaturday);

        cbSunday.setText("D");
        cbSunday.setEnabled(false);
        cbSunday.setFocusable(false);
        cbSunday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbSunday.setIconTextGap(8);
        cbSunday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbSunday);

        jLabel10.setText("Opciones");

        btnPrestamo.setText("Prestamo");
        btnPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrestamoActionPerformed(evt);
            }
        });

        jButton1.setText("Registrar incidencias");

        jButton3.setText("Ver historial de Nominas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbFechaUltimoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                    .addComponent(btnPrestamo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnPrestamo, jButton1, jButton3});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbFechaUltimoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(btnPrestamo)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jButton4.setText("guardar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout nominaOneLayout = new javax.swing.GroupLayout(nominaOne);
        nominaOne.setLayout(nominaOneLayout);
        nominaOneLayout.setHorizontalGroup(
            nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nominaOneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(38, 38, 38)
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbSearch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(endDate, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(btnCalcular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSueldoDiario, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(txtDiasLaborales, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(txtSueldoSub, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(txtPrestamos, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(txtTotalSueldoNeto, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(payDate, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(39, 39, 39)
                .addComponent(btnBuscar)
                .addGap(18, 18, 18)
                .addComponent(btnLimpiar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        nominaOneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCalcular, endDate, jButton4, payDate, startDate, txtDiasLaborales, txtPrestamos, txtSueldoDiario, txtSueldoSub, txtTotalSueldoNeto});

        nominaOneLayout.setVerticalGroup(
            nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nominaOneLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpiar)
                    .addComponent(cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(34, 34, 34)
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addComponent(btnCalcular)
                        .addGap(25, 25, 25)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSueldoDiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(15, 15, 15)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDiasLaborales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSueldoSub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(15, 15, 15)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrestamos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(15, 15, 15)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotalSueldoNeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(15, 15, 15)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(payDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jButton4)
                .addContainerGap())
        );

        panel.addTab("Nomina Individual", nominaOne);

        jLabel2.setText("Periodo de: ");

        jLabel3.setText("Hasta:");

        cbUltFecha.setSelected(true);
        cbUltFecha.setText("Ult.Fecha de Nomina Ant.");
        cbUltFecha.setToolTipText("");
        cbUltFecha.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbUltFechaItemStateChanged(evt);
            }
        });
        cbUltFecha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbUltFechaFocusGained(evt);
            }
        });

        btnCalcularALL.setText("calcular");
        btnCalcularALL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularALLActionPerformed(evt);
            }
        });

        tblNomina.setAutoCreateRowSorter(true);
        tblNomina.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblNomina.setShowGrid(true);
        tblNomina.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNominaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNomina);

        jButton6.setText("guardar nomina en la base");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("exportar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton5.setText("enviar recibos (WP)");

        jButton2.setText("Importar");

        txtSearch.setPlaceholder("Buscar en la tabla ...");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout nominaALLLayout = new javax.swing.GroupLayout(nominaALL);
        nominaALL.setLayout(nominaALLLayout);
        nominaALLLayout.setHorizontalGroup(
            nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(nominaALLLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nominaALLLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbUltFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(nominaALLLayout.createSequentialGroup()
                                .addComponent(startDateALL, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(endDateALL, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35)
                        .addComponent(btnCalcularALL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nominaALLLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addGap(19, 19, 19)
                        .addComponent(jButton6)))
                .addContainerGap())
        );
        nominaALLLayout.setVerticalGroup(
            nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nominaALLLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nominaALLLayout.createSequentialGroup()
                        .addGroup(nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startDateALL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(endDateALL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbUltFecha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton7)
                        .addComponent(btnCalcularALL)
                        .addComponent(jButton2)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6)))
        );

        panel.addTab("Nomina General", nominaALL);

        add(panel, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCalcularALLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularALLActionPerformed
        if (startDateALL.getDate() != null && endDateALL.getDate() != null) {
            calcularNominaTodos(tblNomina);
        } else {
            JOptionPane.showMessageDialog(null, "Debe de seleccionar el periodo de nomina.");
        }

    }//GEN-LAST:event_btnCalcularALLActionPerformed

    private void btnPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrestamoActionPerformed
        PrestamoView dialog = new PrestamoView(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dialog.setVisible(false);
            }
        });
        dialog.cmbOperador.getEditor().setItem(cmbSearch.getEditor().getItem().toString());
        dialog.cmbOperador.repaint();
        dialog.starDate.setDate(startDate.getDate());
        dialog.endDate.setDate(endDate.getDate());
        dialog.repaint();
        dialog.setVisible(true);

    }//GEN-LAST:event_btnPrestamoActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        clearAll();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        searchOperador();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        calcularNominaIndividual();
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void txtPrestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrestamosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrestamosActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        save();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        saveAll(tblNomina);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

    }//GEN-LAST:event_jButton7ActionPerformed

    private void cbUltFechaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbUltFechaItemStateChanged

        // startDateALL.setEnabled(!cbUltFecha.isSelected());

    }//GEN-LAST:event_cbUltFechaItemStateChanged

    private void cbUltFechaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbUltFechaFocusGained
        cbUltFecha.setToolTipText("Si hay un registro de la ultima fecha de nomina, se tomara la de que ingrese.");
    }//GEN-LAST:event_cbUltFechaFocusGained

    private void tblNominaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNominaMouseClicked
        if (evt.getClickCount() == 2 && tblNomina.getSelectedColumn() == 14) {
            int row = tblNomina.getSelectedRow();
            DefaultTableModel modelt = (DefaultTableModel) tblNomina.getModel();
            modelt.removeRow(row);
        }
    }//GEN-LAST:event_tblNominaMouseClicked

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
     Filter.searchInTable(txtSearch, tblNomina);
    }//GEN-LAST:event_txtSearchKeyTyped

    private void clearAll() {
        cmbSearch.getEditor().setItem("");
        lbFechaUltimoPago.setText("");
        startDate.setDate(null);
        endDate.setDate(null);
        txtSueldoDiario.setText("");
        txtDiasLaborales.setText("");
        txtSueldoSub.setText("");
        txtPrestamos.setText("");
        txtTotalSueldoNeto.setText("");
        payDate.setDate(null);
        cmbSearch.removeAllItems();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnCalcularALL;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnPrestamo;
    private javax.swing.JCheckBox cbFriday;
    private javax.swing.JCheckBox cbMonday;
    private javax.swing.JCheckBox cbSaturday;
    private javax.swing.JCheckBox cbSunday;
    private javax.swing.JCheckBox cbThurday;
    private javax.swing.JCheckBox cbThusday;
    private javax.swing.JCheckBox cbUltFecha;
    private javax.swing.JCheckBox cbWenesday;
    private javax.swing.JComboBox<String> cmbSearch;
    private com.utils.DropXlsx dropXlsx1;
    private com.toedter.calendar.JDateChooser endDate;
    private com.toedter.calendar.JDateChooser endDateALL;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbFechaUltimoPago;
    private javax.swing.JPanel nominaALL;
    private javax.swing.JPanel nominaOne;
    private javax.swing.JTabbedPane panel;
    private com.toedter.calendar.JDateChooser payDate;
    private com.toedter.calendar.JDateChooser startDate;
    private com.toedter.calendar.JDateChooser startDateALL;
    private javax.swing.JTable tblNomina;
    private javax.swing.ButtonGroup tipoNomina;
    private javax.swing.JTextField txtDiasLaborales;
    private javax.swing.JTextField txtPrestamos;
    private com.utils.components.txtPlaceholder txtSearch;
    private javax.swing.JTextField txtSueldoDiario;
    private javax.swing.JTextField txtSueldoSub;
    private javax.swing.JTextField txtTotalSueldoNeto;
    // End of variables declaration//GEN-END:variables

}
