package com.utils.icon;

import com.dao.NominaDAO;
import com.dao.TrabajadoresDAO;
import com.model.Operador;
import com.utils.Filter;
import com.utils.table.RenderTable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author keatnis
 */
public class CalcularNomina extends javax.swing.JPanel {

    private TrabajadoresDAO trabajadoresDAO;
    private Operador operador;

    private NominaDAO nominaDAO;

    public CalcularNomina() {
        initComponents();
        this.trabajadoresDAO = new TrabajadoresDAO();
        this.nominaDAO = new NominaDAO();
        this.showForms(false, true);
    }

    private void searchOperador() {
        this.cmbSearch.removeAllItems();

        String res = (String) this.cmbSearch.getEditor().getItem();
        List<Operador> ve = trabajadoresDAO.searchOperador(res);

        for (Operador operadorl : ve) {

            this.cmbSearch.addItem(operadorl.getId() + " " + operadorl.getNombre() + " "
                    + operadorl.getApePaterno() + " " + operadorl.getApeMaterno());

        }
        this.cmbSearch.setSelectedIndex(0);
        this.cmbSearch.repaint();
        cmbSearch.setPopupVisible(true);
        operador = new Operador();
        if (ve.size() == 0) {
            int id = ve.get(0).getId();
            operador.setId(id);
            txtSueldoDiario.setText(ve.get(0).getJob().getSueldoDiario() + "");
            lbFechaUltimoPago.setText(nominaDAO.getLastPayDate(id).get(0).getFechaPago() + "");
        }

        this.cmbSearch.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent evt) {

                int id = ve.get(cmbSearch.getSelectedIndex()).getId();
                operador.setId(id);
                txtSueldoDiario.setText(Float.toString(ve.get(cmbSearch.getSelectedIndex()).getJob().getSueldoDiario()));
                lbFechaUltimoPago.setText(nominaDAO.getLastPayDate(id).get(cmbSearch.getSelectedIndex()).getFechaPago() + "");
            }

        });
    }
    
    public int diasHabiles(Calendar fechaInicial, Calendar fechaFinal, List<Date> listaFechasNoLaborables) {
        int diffDays = 0;
        boolean diaHabil = false;
        //mientras la fecha inicial sea menor o igual que la fecha final se cuentan los dias
        while (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)) {

            if (!listaFechasNoLaborables.isEmpty()) {
                for (Date date : listaFechasNoLaborables) {
                    Date fechaNoLaborablecalendar = fechaInicial.getTime();
                    //si el dia de la semana de la fecha minima es diferente de sabado o domingo
                    if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && !fechaNoLaborablecalendar.equals(date)) {
                        //se aumentan los dias de diferencia entre min y max
                        diaHabil = true;
                    } else {
                        diaHabil = false;
                        break;
                    }
                }
            } else {
                if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                    //se aumentan los dias de diferencia entre min y max
                    diffDays++;
                }
            }
            if (diaHabil == true) {
                diffDays++;
            }
            //se suma 1 dia para hacer la validacion del siguiente dia.
            fechaInicial.add(Calendar.DATE, 1);
        }
        return diffDays;
    }

    private void calcularNominaIndividual() {
        List<Date> diasNoTrabajados = new ArrayList<>();

        int diasLaborados = this.diasHabiles(startDate.getCalendar(),
                endDate.getCalendar(), diasNoTrabajados);
        txtDiasLaborales.setText("" + diasLaborados);
        float sueldo = Float.parseFloat(txtSueldoDiario.getText());

        float subtotal = diasLaborados * sueldo;
        txtSueldoSub.setText(subtotal + "");
        /*
            calcular el total incluyendo los prestamos y tiempos extras
         */
        float prestamos = 0;
        float extras = 0;
        txtPrestamos.setText(prestamos + "");
    }

    private void calcularNominaTodos(JTable table) {
        Filter.removeAllRows(table);
        Object[] titles = new Object[]{
            "ID", "NOMBRE", "A.PATERNO", "A.MATERNO", "PUESTO", "TELEFONO",
            "SUELDO DIARIO", "TOTAL DIAS LABORALES", "SUBTOTAL", "DESCUENTOS", "PAGO EXTRA", "TOTAL", "FECHA DE PAGO"};
        /*coloco el nombre de las  columnas de la tabla USER a el modelo */
        DefaultTableModel model = new DefaultTableModel(null, titles) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        RenderTable render = new RenderTable();
        table.setDefaultRenderer(Object.class, render);
        /* obtengo la lista de */
        List<Operador> list = trabajadoresDAO.operadores();

        for (Operador op : list) {

            model.addRow(new Object[]{
                op.getId(),
                op.getNombre(),
                op.getApePaterno(),
                op.getApeMaterno(),
                op.getJob().getPuesto(),
                op.getTelefono(),
                op.getJob().getSueldoDiario(),
                op.getJob().getTotalDiasLaborales(),
                op.getJob().getSueldoDiario() * op.getJob().getTotalDiasLaborales(),
                "00",
                "00",
                ((op.getJob().getSueldoDiario() * op.getJob().getTotalDiasLaborales())),
                "2023/08/04"

            });
            /*establecemos el modelo  al Jtable llamado jTabla*/

        }

        table.setRowHeight(30);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.doLayout();
        /* asignamos el ancho de cada columna de la tabla*/

        int[] anchos = {30, 150, 150, 200, 150, 150, 150, 150, 100, 100, 100,
            100, 100};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
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
        root = new javax.swing.JPanel();
        nominaALL = new javax.swing.JPanel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNomina = new javax.swing.JTable();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        nominaOne = new javax.swing.JPanel();
        cmbSearch = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSueldoDiario = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDiasLaborales = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lbFechaUltimoPago = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtPrestamos = new javax.swing.JTextField();
        txtExtras = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSueldoSub = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        startDate = new com.toedter.calendar.JDateChooser();
        endDate = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnCalcular = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        payDate = new com.toedter.calendar.JDateChooser();
        btnBuscar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        tipo = new javax.swing.JPanel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();

        jLabel2.setText("Dia inicio");

        jLabel3.setText("Dia corte");

        tblNomina.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblNomina);

        jCheckBox1.setText("A partir de la ultima fecha de pago");

        jButton2.setText("calcular");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout nominaALLLayout = new javax.swing.GroupLayout(nominaALL);
        nominaALL.setLayout(nominaALLLayout);
        nominaALLLayout.setHorizontalGroup(
            nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nominaALLLayout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addComponent(jLabel3)
                .addGap(28, 28, 28)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jButton2)
                .addGap(317, 317, 317))
            .addGroup(nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(nominaALLLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1)
                    .addContainerGap()))
        );
        nominaALLLayout.setVerticalGroup(
            nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nominaALLLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addGroup(nominaALLLayout.createSequentialGroup()
                        .addGroup(nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1)))
                .addContainerGap(469, Short.MAX_VALUE))
            .addGroup(nominaALLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nominaALLLayout.createSequentialGroup()
                    .addContainerGap(79, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        cmbSearch.setBackground(new java.awt.Color(204, 204, 204));
        cmbSearch.setEditable(true);

        jLabel4.setText("Trabajador");

        jLabel6.setText("Sueldo diario");

        jLabel7.setText("Dias laborados");

        jLabel8.setText("Ultimo pago");

        lbFechaUltimoPago.setFont(new java.awt.Font("Cantarell", 0, 14)); // NOI18N
        lbFechaUltimoPago.setForeground(new java.awt.Color(255, 102, 51));
        lbFechaUltimoPago.setText("fecha");

        jLabel9.setText("Prestamos");

        jLabel10.setText("Tiempo extra");

        txtPrestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrestamosActionPerformed(evt);
            }
        });

        jLabel11.setText("Subtotal");

        jLabel12.setText("Fecha inicio");

        jLabel13.setText("Fecha corte");

        jButton1.setText("Guardar registro");

        jLabel14.setText("Total");

        txtTotal.setForeground(new java.awt.Color(0, 153, 255));

        btnCalcular.setText("Calcular");
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

        jButton5.setText("ver/añadir");

        jButton6.setText("ver/añadir");

        javax.swing.GroupLayout nominaOneLayout = new javax.swing.GroupLayout(nominaOne);
        nominaOne.setLayout(nominaOneLayout);
        nominaOneLayout.setHorizontalGroup(
            nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nominaOneLayout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(lbFechaUltimoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(nominaOneLayout.createSequentialGroup()
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel6))
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jLabel7))
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jLabel11))
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jLabel15))
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jLabel12))
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jLabel13))
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(18, 18, 18)
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSueldoDiario, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiasLaborales, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSueldoSub, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrestamos, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtExtras, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCalcular)
                            .addComponent(jButton5)
                            .addComponent(jButton6))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addComponent(payDate, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(nominaOneLayout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscar))
            .addGroup(nominaOneLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        nominaOneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {endDate, jButton1, payDate, startDate, txtDiasLaborales, txtExtras, txtPrestamos, txtSueldoDiario, txtSueldoSub, txtTotal});

        nominaOneLayout.setVerticalGroup(
            nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nominaOneLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel4))
                    .addComponent(cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGap(12, 12, 12)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(lbFechaUltimoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(9, 9, 9)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(nominaOneLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel13))
                            .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btnCalcular)))
                .addGap(12, 12, 12)
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6))
                    .addComponent(txtSueldoDiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiasLaborales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jButton5))
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtSueldoSub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jButton6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nominaOneLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtPrestamos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel10))
                    .addComponent(txtExtras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(25, 25, 25)
                .addGroup(nominaOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nominaOneLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel15))
                    .addComponent(payDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tipoNomina.add(jRadioButton2);
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Todos");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        tipoNomina.add(jRadioButton1);
        jRadioButton1.setText("Individual");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Seleccione una opcion:");

        javax.swing.GroupLayout tipoLayout = new javax.swing.GroupLayout(tipo);
        tipo.setLayout(tipoLayout);
        tipoLayout.setHorizontalGroup(
            tipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1150, Short.MAX_VALUE)
            .addGroup(tipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tipoLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jRadioButton1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jRadioButton2)
                    .addContainerGap(831, Short.MAX_VALUE)))
        );
        tipoLayout.setVerticalGroup(
            tipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
            .addGroup(tipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tipoLayout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addGroup(tipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(tipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jRadioButton1))
                        .addComponent(jRadioButton2))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout rootLayout = new javax.swing.GroupLayout(root);
        root.setLayout(rootLayout);
        rootLayout.setHorizontalGroup(
            rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootLayout.createSequentialGroup()
                .addGap(0, 27, Short.MAX_VALUE)
                .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nominaALL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootLayout.createSequentialGroup()
                    .addContainerGap(215, Short.MAX_VALUE)
                    .addComponent(nominaOne, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(216, Short.MAX_VALUE)))
        );
        rootLayout.setVerticalGroup(
            rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(580, Short.MAX_VALUE))
            .addGroup(rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootLayout.createSequentialGroup()
                    .addContainerGap(54, Short.MAX_VALUE)
                    .addComponent(nominaALL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(26, Short.MAX_VALUE)))
            .addGroup(rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootLayout.createSequentialGroup()
                    .addContainerGap(85, Short.MAX_VALUE)
                    .addComponent(nominaOne, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(41, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1189, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(root, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(root, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        searchOperador();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        calcularNominaIndividual();

    }//GEN-LAST:event_btnCalcularActionPerformed

    private void txtPrestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrestamosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrestamosActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        calcularNominaTodos(tblNomina);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        this.showForms(true, false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        this.showForms(false, true);
    }//GEN-LAST:event_jRadioButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JComboBox<String> cmbSearch;
    private com.toedter.calendar.JDateChooser endDate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JCheckBox jCheckBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbFechaUltimoPago;
    private javax.swing.JPanel nominaALL;
    private javax.swing.JPanel nominaOne;
    private com.toedter.calendar.JDateChooser payDate;
    private javax.swing.JPanel root;
    private com.toedter.calendar.JDateChooser startDate;
    private javax.swing.JTable tblNomina;
    private javax.swing.JPanel tipo;
    private javax.swing.ButtonGroup tipoNomina;
    private javax.swing.JTextField txtDiasLaborales;
    private javax.swing.JTextField txtExtras;
    private javax.swing.JTextField txtPrestamos;
    private javax.swing.JTextField txtSueldoDiario;
    private javax.swing.JTextField txtSueldoSub;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
