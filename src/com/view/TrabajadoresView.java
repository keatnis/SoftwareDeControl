package com.view;

import com.dao.TrabajadoresDAO;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.model.ContactoEmergencia;
import com.model.Job;
import com.model.Operador;
import com.utils.ChooseFile;
import com.utils.ExportExcel;
import com.utils.Filter;
import com.utils.Validaciones;
import com.utils.table.RenderTable;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author keatnis
 */
public class TrabajadoresView extends javax.swing.JPanel {

    private byte[] pdf;
    private final TrabajadoresDAO trabajadoresDAO;
    private Job job;
    private Operador operador;

    public TrabajadoresView() {
        initComponents();
        this.showForms(false, true);

        this.trabajadoresDAO = new TrabajadoresDAO();
        showData(tblOperador);
        clearAll();

    }

    private void showData(JTable table) {
        Filter.removeAllRows(table);
        Object[] titles = new Object[]{
            "ID", "NOMBRE", "A.PATERNO", "A.MATERNO", "PUESTO", "TELEFONO", "TELEFONO 2",
            "DIRECCION", "CALLE", "NUM", "COLONIA", "CIUDAD", "ESTADO",
            "TIPO SANGRE", "ALERGIAS", "INE", "CONTACTO EMERG.",
            "NOMBRE", "A.PATERNO", "A.MATERNO", "TELEFONO", "PARENTESCO",
            "SUELDO DIARIO", "TOTAL DIAS LABORALES", "FILE"};
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
        List<Operador> list = trabajadoresDAO.operadores();

        for (Operador op : list) {

            model.addRow(new Object[]{
                op.getId(),
                op.getNombre(),
                op.getApePaterno(),
                op.getApeMaterno(),
                op.getJob().getPuesto(),
                op.getTelefono(),
                op.getTelefono2(),
                op.getCalle() + " " + op.getNum() + " " + op.getColonia() + " " + op.getCiudad() + " " + op.getEstado(),
                op.getCalle(),
                op.getNum(),
                op.getColonia(),
                op.getCiudad(),
                op.getEstado(),
                op.getTypeblood(),
                op.getAlergias(),
                getFile(op.getFile()),
                op.getContactoEmergencia().getTelefono() + " " + op.getContactoEmergencia().getNombre() + " " + op.getContactoEmergencia().getParentesco(),
                op.getContactoEmergencia().getNombre(),
                op.getContactoEmergencia().getApePaterno(),
                op.getContactoEmergencia().getApeMaterno(),
                op.getContactoEmergencia().getTelefono(),
                op.getContactoEmergencia().getParentesco(),
                op.getJob().getSueldoDiario(),
                op.getJob().getTotalDiasLaborales(),
                op.getFile()
            });
            /*establecemos el modelo  al Jtable llamado jTabla*/

        }

        table.setRowHeight(30);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.doLayout();
        /* asignamos el ancho de cada columna de la tabla*/

        int[] anchos = {30, 150, 150, 200, 150, 150, 200, 150, 100, 100, 100,
            100, 100, 150, 150, 100, 250, 150, 150, 150, 150, 150, 100, 100, 11};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        this.hideColumns(table, new int[]{8, 9, 10, 11, 12, 17, 18, 19, 20, 21, 24});
    }

    private static JButton getFile(Object object) {

        if (object != null) {
            return new JButton(new FlatSVGIcon("com/utils/icon/pdf.svg"));

        } else {
            return new JButton("Vacio");
        }

    }

    public void hideColumns(JTable tbl, int columna[]) {
        for (int i = 0; i < columna.length; i++) {
            tbl.getColumnModel().getColumn(columna[i]).setMaxWidth(0);
            tbl.getColumnModel().getColumn(columna[i]).setMinWidth(0);
            tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMaxWidth(0);
            tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMinWidth(0);
        }
    }

    private void editAction() {
        int row = tblOperador.getSelectedRow();

        btnSave.setText("actualizar datos");
        btnNew.repaint();
        //pasando datos al formulario

        txtNombre.setText(String.valueOf(tblOperador.getValueAt(row, 1)));
        txtAPaterno.setText(String.valueOf(tblOperador.getValueAt(row, 2)));
        txtAMaterno.setText(String.valueOf(tblOperador.getValueAt(row, 3)));

        txtPuesto.setText(String.valueOf(tblOperador.getValueAt(row, 4)));
        txtTelefono.setText(String.valueOf(tblOperador.getValueAt(row, 5)));
        txtTelefono2.setText(String.valueOf(tblOperador.getValueAt(row, 6)));

        txtCalle.setText(String.valueOf(tblOperador.getValueAt(row, 8)));
        txtNum.setText(String.valueOf(tblOperador.getValueAt(row, 9)));
        txtColonia.setText(String.valueOf(tblOperador.getValueAt(row, 10)));
        txtCiudad.setText(String.valueOf(tblOperador.getValueAt(row, 11)));
        txtEstado.setText(String.valueOf(tblOperador.getValueAt(row, 12)));

        cmbTypeblood.setSelectedItem(tblOperador.getValueAt(row, 13));
        txtAlergias.setText(String.valueOf(tblOperador.getValueAt(row, 14)));
        txtNombreCont.setText(String.valueOf(tblOperador.getValueAt(row, 17)));
        txtAPaternoCont.setText(String.valueOf(tblOperador.getValueAt(row, 18)));
        txtAMaternoCont.setText(String.valueOf(tblOperador.getValueAt(row, 19)));
        txtTelefonoCont.setText(String.valueOf(tblOperador.getValueAt(row, 20)));
        cmbParentescoCont.setSelectedItem(String.valueOf(tblOperador.getValueAt(row, 21)));
        txtSueldoDiario.setText(String.valueOf(tblOperador.getValueAt(row, 22)));
        pdf = (byte[]) tblOperador.getValueAt(row, 24);

        showForms(true, false);

        //  showForm(formPanel);
    }

    private void update() {

        int idUser = Integer.valueOf(
                tblOperador.getValueAt(tblOperador.getSelectedRow(), 0)
                        .toString());

        operador = new Operador();
        operador.setId(idUser);
        operador.setNombre(txtNombre.getText());
        operador.setApePaterno(txtAPaterno.getText());
        operador.setApeMaterno(txtAMaterno.getText());
        operador.setCalle(txtCalle.getText());
        operador.setColonia(txtColonia.getText());
        operador.setCiudad(txtCiudad.getText());
        operador.setEstado(txtEstado.getText());

        operador.setNum(txtNum.getText());
        operador.setTelefono(txtTelefono.getText());
        operador.setTelefono2(txtTelefono2.getText());
        operador.setAlergias(txtAlergias.getText());
        operador.setTypeblood((String) cmbTypeblood.getSelectedItem());

        operador.setFile(pdf);
        // datos contacto emergencia

        ContactoEmergencia contactoEmergencia = new ContactoEmergencia();

        contactoEmergencia.setNombre(txtNombreCont.getText());
        contactoEmergencia.setApeMaterno(txtAPaternoCont.getText());
        contactoEmergencia.setApePaterno(txtAMaternoCont.getText());
        contactoEmergencia.setTelefono(txtTelefonoCont.getText());
        contactoEmergencia.setParentesco((String) cmbParentescoCont.getSelectedItem());

        operador.setContactoEmergencia(contactoEmergencia);

        job = new Job();
        job.setPuesto(txtPuesto.getText());
        job.setSueldoDiario(Float.parseFloat(txtSueldoDiario.getText()));
        boolean[] dias = {cbMonday.isSelected(), cbThusday.isSelected(),
            cbWenesday.isSelected(), cbThurday.isSelected(), cbFriday.isSelected(), cbSaturday.isSelected(), cbSunday.isSelected()};
        job.setDiasLaborales(dias);

        job.setTotalDiasLaborales(countTrueElements(dias));
        operador.setJob(job);
        trabajadoresDAO.edit(operador);

        showData(tblOperador);
    }

    private void deleteAction() {
        int idUser = Integer.valueOf(
                tblOperador.getValueAt(tblOperador.getSelectedRow(), 0)
                        .toString());
        trabajadoresDAO.delete(idUser);
        showData(tblOperador);

    }

    public void showForms(boolean showForm, boolean showList) {

        panelForm.setVisible(showForm);
        if (panelForm.isVisible()) {
            this.getAccessibleContext();
        }
        panelList.setVisible(showList);
    }

    private void save() {
        operador = new Operador();
        operador.setNombre(txtNombre.getText());
        operador.setApePaterno(txtAPaterno.getText());
        operador.setApeMaterno(txtAMaterno.getText());
        operador.setCalle(txtCalle.getText());
        operador.setColonia(txtColonia.getText());
        operador.setCiudad(txtCiudad.getText());
        operador.setEstado(txtEstado.getText());
        operador.setStatus(true);
        operador.setNum(txtNum.getText());
        operador.setTelefono(txtTelefono.getText());
        operador.setTelefono2(txtTelefono2.getText());
        operador.setAlergias(txtAlergias.getText());
        operador.setTypeblood((String) cmbTypeblood.getSelectedItem());

        operador.setFile(pdf);
        // datos contacto emergencia

        ContactoEmergencia contactoEmergencia = new ContactoEmergencia();

        contactoEmergencia.setNombre(txtNombreCont.getText());
        contactoEmergencia.setApeMaterno(txtAPaternoCont.getText());
        contactoEmergencia.setApePaterno(txtAMaternoCont.getText());
        contactoEmergencia.setTelefono(txtTelefonoCont.getText());
        contactoEmergencia.setParentesco((String) cmbParentescoCont.getSelectedItem());

        operador.setContactoEmergencia(contactoEmergencia);
        job = new Job();
        job.setPuesto(txtPuesto.getText());
        job.setSueldoDiario(Float.parseFloat(txtSueldoDiario.getText()));
        boolean[] dias = {cbMonday.isSelected(), cbThusday.isSelected(),
            cbWenesday.isSelected(), cbThurday.isSelected(), cbFriday.isSelected(), cbSaturday.isSelected(), cbSunday.isSelected()};
        job.setDiasLaborales(dias);

        job.setTotalDiasLaborales(countTrueElements(dias));
        operador.setJob(job);
        trabajadoresDAO.save(operador);

    }

    public static int countTrueElements(boolean[] array) {

        int count = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                count++;
            }
        }

        return count;
    }

    private void clearAll() {
        txtNombre.setText("");
        txtAPaterno.setText("");
        txtAMaterno.setText("");
        txtCalle.setText("");
        txtColonia.setText("");
        txtCiudad.setText("");
        txtEstado.setText("");
        txtNum.setText("");
        txtTelefono.setText("");
        txtTelefono2.setText("");
        txtAlergias.setText("");
        cmbTypeblood.setSelectedIndex(0);
        txtPuesto.setText("");
        pdf = null;
        txtNombreCont.setText("");
        txtAPaternoCont.setText("");
        txtAMaternoCont.setText("");
        txtTelefonoCont.setText("");
        cmbParentescoCont.setSelectedIndex(0);
        txtSueldoDiario.setText("");
    }

    //Permite mostrar PDF contenido en la base de datos de tipo byte[]
    public void execPDF(byte[] file) throws IOException {

        InputStream bos = new ByteArrayInputStream(file);

        int tamanoInput = bos.available();
        byte[] datosPDF = new byte[tamanoInput];
        bos.read(datosPDF, 0, tamanoInput);

        OutputStream out = new FileOutputStream("INE.pdf");
        out.write(datosPDF);

        //abrir archivo
        out.flush();
        out.close();
        bos.close();
        try {
            Desktop.getDesktop().open(new File("INE.pdf"));
        } catch (Exception ex) {
        }
        // cn.desconectar();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        diasSemana = new javax.swing.ButtonGroup();
        main = new javax.swing.JPanel();
        panelList = new javax.swing.JPanel();
        panelSearch = new javax.swing.JPanel();
        txtSearch = new com.utils.components.txtPlaceholder();
        spt = new javax.swing.JScrollPane();
        tblOperador = new javax.swing.JTable();
        options = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        panelForm = new javax.swing.JPanel();
        form = new javax.swing.JPanel();
        lbNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lbAPaterno = new javax.swing.JLabel();
        txtAPaterno = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtAMaterno = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTelefono2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPuesto = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtAlergias = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        txtNum = new javax.swing.JTextField();
        txtColonia = new javax.swing.JTextField();
        txtCiudad = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtNombreCont = new javax.swing.JTextField();
        txtAPaternoCont = new javax.swing.JTextField();
        txtAMaternoCont = new javax.swing.JTextField();
        txtTelefonoCont = new javax.swing.JTextField();
        cmbParentescoCont = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        cmbTypeblood = new javax.swing.JComboBox<>();
        lbStatus = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        cbMonday = new javax.swing.JCheckBox();
        cbThusday = new javax.swing.JCheckBox();
        cbWenesday = new javax.swing.JCheckBox();
        cbThurday = new javax.swing.JCheckBox();
        cbFriday = new javax.swing.JCheckBox();
        cbSaturday = new javax.swing.JCheckBox();
        cbSunday = new javax.swing.JCheckBox();
        txtSueldoDiario = new javax.swing.JTextField();
        btns = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        main.setLayout(new java.awt.CardLayout());

        panelList.setLayout(new java.awt.BorderLayout(0, 10));

        txtSearch.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtSearch.setMaximumSize(null);
        txtSearch.setPlaceholder("Buscar ...");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panelSearchLayout = new javax.swing.GroupLayout(panelSearch);
        panelSearch.setLayout(panelSearchLayout);
        panelSearchLayout.setHorizontalGroup(
            panelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelSearchLayout.setVerticalGroup(
            panelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelList.add(panelSearch, java.awt.BorderLayout.PAGE_START);

        tblOperador.setAutoCreateRowSorter(true);
        tblOperador.setModel(new javax.swing.table.DefaultTableModel(
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
        tblOperador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOperadorMouseClicked(evt);
            }
        });
        spt.setViewportView(tblOperador);

        panelList.add(spt, java.awt.BorderLayout.CENTER);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));
        jPanel1.setLayout(new java.awt.GridLayout(5, 0, 0, 4));

        btnNew.setText("Agregar Nuevo");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        jPanel1.add(btnNew);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar);

        btnDelete.setText("eliminar");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete);

        btnExport.setText("Exportar datos");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        jPanel1.add(btnExport);

        javax.swing.GroupLayout optionsLayout = new javax.swing.GroupLayout(options);
        options.setLayout(optionsLayout);
        optionsLayout.setHorizontalGroup(
            optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        optionsLayout.setVerticalGroup(
            optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 435, Short.MAX_VALUE))
        );

        panelList.add(options, java.awt.BorderLayout.EAST);
        options.getAccessibleContext().setAccessibleName("Option");

        main.add(panelList, "card2");

        panelForm.setLayout(new javax.swing.BoxLayout(panelForm, javax.swing.BoxLayout.PAGE_AXIS));

        form.setMaximumSize(null);

        lbNombre.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbNombre.setText("Nombre");

        lbAPaterno.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbAPaterno.setText("A. Paterno");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("A. Materno");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Telefono");

        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Tel.2 /celular");

        txtTelefono2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefono2KeyTyped(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel9.setText("Puesto/cargo");

        jLabel10.setText("Alergias");

        jLabel11.setText("Tipo Sangre");

        jLabel1.setText("Calle");

        jLabel5.setText("No.");

        jLabel2.setText("Colonia");

        jLabel6.setText("Ciudad");

        jLabel8.setText("Estado");

        txtNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumKeyTyped(evt);
            }
        });

        txtEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Contanto de Emergencia"));

        jLabel12.setText("Nombre");

        jLabel13.setText("A. Pateno");

        jLabel14.setText("A. Materno");

        jLabel15.setText("Teléfono");

        jLabel16.setText("Parentesco");

        txtTelefonoCont.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoContKeyTyped(evt);
            }
        });

        cmbParentescoCont.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Esposa", "Esposo", "Hijo", "Hija", "Padre", "Madre", "Hermana", "Hermano", "Amigo", "Amiga", "Otro parentesco" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbParentescoCont, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtTelefonoCont, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                        .addComponent(txtAMaternoCont, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtAPaternoCont, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtNombreCont, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtNombreCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAPaternoCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAMaternoCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefonoCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cmbParentescoCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jButton2.setText("Agregar INE/Licencia");
        jButton2.setToolTipText("* Copia del INE / Licencia");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cmbTypeblood.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "O+", "O-", "AB+", "AB-", "A+", "A-", "B+", "B-" }));

        javax.swing.GroupLayout formLayout = new javax.swing.GroupLayout(form);
        form.setLayout(formLayout);
        formLayout.setHorizontalGroup(
            formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEstado, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCiudad, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtColonia, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNum, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCalle, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(formLayout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                    .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtAlergias, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtTelefono2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtAMaterno, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(formLayout.createSequentialGroup()
                        .addComponent(lbAPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(txtAPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(formLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(formLayout.createSequentialGroup()
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(formLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(txtPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(formLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTypeblood, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 143, Short.MAX_VALUE))
        );
        formLayout.setVerticalGroup(
            formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formLayout.createSequentialGroup()
                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(formLayout.createSequentialGroup()
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lbNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbAPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(cmbTypeblood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(formLayout.createSequentialGroup()
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(txtAMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(txtTelefono2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(txtAlergias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(formLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel1)
                            .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel5)
                            .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(11, 11, 11)
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel6)
                            .addComponent(txtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel8)
                            .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(formLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        panelForm.add(form);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Nómina"));

        jLabel17.setText("Sueldo diario");

        jLabel20.setText("Dias laborales:");

        jToolBar1.setRollover(true);
        jToolBar1.setMargin(new java.awt.Insets(0, 10, 0, 0));
        jToolBar1.setName("dis"); // NOI18N

        cbMonday.setSelected(true);
        cbMonday.setText("Lunes");
        cbMonday.setFocusable(false);
        cbMonday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbMonday.setIconTextGap(8);
        cbMonday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbMonday);

        cbThusday.setSelected(true);
        cbThusday.setText("Martes");
        cbThusday.setFocusable(false);
        cbThusday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbThusday.setIconTextGap(8);
        cbThusday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbThusday);

        cbWenesday.setSelected(true);
        cbWenesday.setText("Miercoles");
        cbWenesday.setFocusable(false);
        cbWenesday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbWenesday.setIconTextGap(8);
        cbWenesday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbWenesday);

        cbThurday.setSelected(true);
        cbThurday.setText("Jueves");
        cbThurday.setFocusable(false);
        cbThurday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbThurday.setIconTextGap(8);
        cbThurday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbThurday);

        cbFriday.setSelected(true);
        cbFriday.setText("viernes");
        cbFriday.setFocusable(false);
        cbFriday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbFriday.setIconTextGap(8);
        cbFriday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbFriday);

        cbSaturday.setText("Sábado");
        cbSaturday.setFocusable(false);
        cbSaturday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbSaturday.setIconTextGap(8);
        cbSaturday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbSaturday);

        diasSemana.add(cbSunday);
        cbSunday.setText("Domingo");
        cbSunday.setFocusable(false);
        cbSunday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cbSunday.setIconTextGap(8);
        cbSunday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbSunday);

        txtSueldoDiario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSueldoDiarioKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSueldoDiario, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(491, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtSueldoDiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelForm.add(jPanel2);

        btnCancel.setText("cancelar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.focusColor"));
        btnSave.setText("guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnsLayout = new javax.swing.GroupLayout(btns);
        btns.setLayout(btnsLayout);
        btnsLayout.setHorizontalGroup(
            btnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCancel)
                .addGap(18, 18, 18)
                .addComponent(btnSave))
        );
        btnsLayout.setVerticalGroup(
            btnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnSave)
                .addComponent(btnCancel))
        );

        panelForm.add(btns);

        main.add(panelForm, "card3");

        add(main, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        this.showForms(true, false);
        btnNew.setEnabled(false);
        btnSave.setText("guardar");
        btnSave.repaint();


    }//GEN-LAST:event_btnNewActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ChooseFile chooseFile = new ChooseFile();
        pdf = chooseFile.agregar_pdf(lbStatus);
        lbStatus.repaint();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumKeyTyped
        Validaciones.esNumeroEntero(evt);
    }//GEN-LAST:event_txtNumKeyTyped

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        if (btnSave.getText().endsWith("ardar")) {
            save();
            clearAll();
            this.showForms(false, true);
            // this.showForm(list);
            this.showData(tblOperador);
            btnNew.setEnabled(true);
        } else if (btnSave.getText().endsWith("datos")) {
            update();
            clearAll();
            this.showForms(false, true);

            btnNew.setEnabled(true);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoActionPerformed

    }//GEN-LAST:event_txtEstadoActionPerformed

    private void txtTelefono2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono2KeyTyped
        Validaciones.esNumeroEntero(evt);
    }//GEN-LAST:event_txtTelefono2KeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        Validaciones.esNumeroEntero(evt);
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtTelefonoContKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoContKeyTyped
        Validaciones.esNumeroEntero(evt);
    }//GEN-LAST:event_txtTelefonoContKeyTyped

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        clearAll();
        this.showForms(false, true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (tblOperador.getSelectedRow() >= 0) {
            this.showForms(true, false);
            editAction();

        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        Filter.searchInTable(txtSearch, tblOperador);
    }//GEN-LAST:event_txtSearchKeyTyped

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        this.deleteAction();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        try {
            ExportExcel.exportarExcel(tblOperador);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnExportActionPerformed

    private void txtSueldoDiarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSueldoDiarioKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);
    }//GEN-LAST:event_txtSueldoDiarioKeyTyped

    private void tblOperadorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOperadorMouseClicked

        if (evt.getClickCount() == 2 && tblOperador.getSelectedColumn() == 15) {
            int row = tblOperador.getSelectedRow();
            //   System.out.println("row and column "+row +" "+column);
            byte[] pdfObject = (byte[]) tblOperador.getValueAt(row, 24);

            try {
                this.execPDF(pdfObject);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }//GEN-LAST:event_tblOperadorMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel btns;
    private javax.swing.JCheckBox cbFriday;
    private javax.swing.JCheckBox cbMonday;
    private javax.swing.JCheckBox cbSaturday;
    private javax.swing.JCheckBox cbSunday;
    private javax.swing.JCheckBox cbThurday;
    private javax.swing.JCheckBox cbThusday;
    private javax.swing.JCheckBox cbWenesday;
    private javax.swing.JComboBox<String> cmbParentescoCont;
    private javax.swing.JComboBox<String> cmbTypeblood;
    private javax.swing.ButtonGroup diasSemana;
    private javax.swing.JPanel form;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbAPaterno;
    private javax.swing.JLabel lbNombre;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JPanel main;
    private javax.swing.JPanel options;
    private javax.swing.JPanel panelForm;
    private javax.swing.JPanel panelList;
    private javax.swing.JPanel panelSearch;
    private javax.swing.JScrollPane spt;
    private javax.swing.JTable tblOperador;
    private javax.swing.JTextField txtAMaterno;
    private javax.swing.JTextField txtAMaternoCont;
    private javax.swing.JTextField txtAPaterno;
    private javax.swing.JTextField txtAPaternoCont;
    private javax.swing.JTextField txtAlergias;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtColonia;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreCont;
    private javax.swing.JTextField txtNum;
    private javax.swing.JTextField txtPuesto;
    private com.utils.components.txtPlaceholder txtSearch;
    private javax.swing.JTextField txtSueldoDiario;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTelefono2;
    private javax.swing.JTextField txtTelefonoCont;
    // End of variables declaration//GEN-END:variables
}
