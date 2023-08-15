package com.view;

import com.dao.PrestamoDAO;
import com.dao.TrabajadoresDAO;
import com.model.Operador;
import com.model.Prestamo;
import com.utils.Validaciones;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author keatnis
 */
public class PrestamoView extends javax.swing.JDialog {

    private TrabajadoresDAO trabajadoresDAO;
    private PrestamoDAO prestamoDAO;
    private Operador operador;
    private Prestamo prestamo;
    private int ID_PRESTAMO, ID_OPERADOR = 0;

    public PrestamoView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.prestamoDAO = new PrestamoDAO();
        this.trabajadoresDAO = new TrabajadoresDAO();
    //    this.showForms(true, false);
    }

    private void searchOperador() {

        String res = (String) this.cmbOperador.getEditor().getItem();

        if (!res.isEmpty()) {
            this.cmbOperador.removeAllItems();
            List<Operador> ve = trabajadoresDAO.searchOperador(res);

            for (Operador operadorl : ve) {

                this.cmbOperador.addItem(operadorl.getId() + " " + operadorl.getNombre() + " " + operadorl.getApePaterno() + " " + operadorl.getApeMaterno());

            }
            this.cmbOperador.setSelectedIndex(0);
            this.cmbOperador.repaint();
            cmbOperador.setPopupVisible(true);
        } else {
            this.cmbOperador.removeAllItems();
            JOptionPane.showMessageDialog(null, "Introduzca el nombre o apellido del trabajador");
            return;
        }

    }

    private void validarForm() {
        String res = (String) this.cmbOperador.getEditor().getItem();

        if (res.isEmpty()) {
             JOptionPane.showMessageDialog(null, "Introduzca el nombre o apellido del trabajador en el buscardor y pulse el boton para buscar"); 
        }
        Validaciones.esCajaVacia(txtCantidad, "No introducido la cantidad de prestamo");
        Validaciones.dateNoSeleted(FechaPrestamo,"Fecha prestamo");
    }

    private void validarListadoPrestamo() {

        Validaciones.dateNoSeletedWhithMessage(endDate, "Fecha final no seleccionada");
        Validaciones.dateNoSeletedWhithMessage(starDate, "Fecha inicio no seleccionada");

    }

    private void save() {
        validarForm();
        prestamo = new Prestamo();
        String[] idOperador = cmbOperador.getSelectedItem().toString().split(" ");
              if (idOperador.length < 3) {
            JOptionPane.showMessageDialog(null, "Utilice el boton buscar para obtener el ID del trabajador");
         //   this.showForms(true, false);
            return;
        }
        operador = new Operador();

        operador.setId(Integer.parseInt(idOperador[0]));
        prestamo.setOperador(operador);
        prestamo.setPrestamo(Float.parseFloat(txtCantidad.getText()));
        prestamo.setDescripcion(txtDescripcion.getText());
        prestamo.setFehcaPrestamo(FechaPrestamo.getDate());

        prestamoDAO.save(prestamo);
       // this.showForms(false, true);

    }

    public void showForms(boolean showForm, boolean showList) {

        panelForm.setVisible(showForm);
        if (panelForm.isVisible()) {
            this.getAccessibleContext();
        }

        panelList.setVisible(showList);
    }

    private void showListBetweenDate(JTable table) {

        String[] idOperador = cmbOperador.getSelectedItem().toString().split(" ");

        Object[] titles = new Object[]{
            "ID PRESTAMO", "ID OPERADOR", "OPERADOR", "CANTIDAD", "FECHA", "CONCEPTO"};
        /*coloco el nombre de las  columnas de la tabla USER a el modelo */
        DefaultTableModel model = new DefaultTableModel(null, titles) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = (formatofecha.format(starDate.getDate()));
        String endDateString = (formatofecha.format(endDate.getDate()));
        List<Prestamo> list = prestamoDAO.getPrestamosBetweenDates(Integer.parseInt(idOperador[0]),
                startDateString, endDateString);

        for (Prestamo prestamo : list) {

            model.addRow(new Object[]{
                prestamo.getId(),
                prestamo.getOperador().getId(),
                prestamo.getOperador().getNombre() + " " + prestamo.getOperador().getApePaterno() + " " + prestamo.getOperador().getApeMaterno(),
                prestamo.getPrestamo(),
                prestamo.getFehcaPrestamo(),
                prestamo.getDescripcion()

            });
            /*establecemos el modelo  al Jtable llamado jTabla*/

        }

        //  table.setRowHeight(30);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.doLayout();
        /* asignamos el ancho de cada columna de la tabla*/

        int[] anchos = {30, 30, 250, 150, 200, 150};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    private void editAction() {
        int row = tblPrestamos.getSelectedRow();

        btnSave.setText("actualizar datos");
        btnSave.repaint();
        ID_OPERADOR = Integer.parseInt(tblPrestamos.getValueAt(row, 1).toString());
        ID_PRESTAMO = Integer.parseInt(tblPrestamos.getValueAt(row, 0).toString());

        txtCantidad.setText(String.valueOf(tblPrestamos.getValueAt(row, 3)));
        FechaPrestamo.setDate(Validaciones.returnDate(String.valueOf(tblPrestamos.getValueAt(row, 4))));
        txtDescripcion.setText(String.valueOf(tblPrestamos.getValueAt(row, 5)));
      //  this.showForms(true, false);
    }

    private void update() {
        validarForm();
        prestamo = new Prestamo();

        operador = new Operador();
        operador.setId(ID_OPERADOR);
        prestamo.setId(ID_PRESTAMO);
        prestamo.setOperador(operador);
        prestamo.setPrestamo(Float.parseFloat(txtCantidad.getText()));
        prestamo.setDescripcion(txtDescripcion.getText());
        prestamo.setFehcaPrestamo(FechaPrestamo.getDate());

        prestamoDAO.update(prestamo);
        this.showListBetweenDate(tblPrestamos);
    }

    private void delete() {
        int row = tblPrestamos.getSelectedRow();
        ID_PRESTAMO = Integer.parseInt(tblPrestamos.getValueAt(row, 0).toString());
        prestamoDAO.delete(ID_PRESTAMO);
        this.showListBetweenDate(tblPrestamos);

    }

    private void clearAll() {
        //cmbOperador.getEditor().setItem("");
        txtCantidad.setText("");
        FechaPrestamo.setDate(null);
        txtDescripcion.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        root = new javax.swing.JPanel();
        panelList = new javax.swing.JPanel();
        starDate = new com.toedter.calendar.JDateChooser();
        endDate = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPrestamos = new javax.swing.JTable();
        btnMostrarLista = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        panelForm = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        FechaPrestamo = new com.toedter.calendar.JDateChooser();
        opciones = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cmbOperador = new javax.swing.JComboBox<>();
        btnOperador = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de prestamo");
        setModal(true);

        root.setLayout(new java.awt.BorderLayout());

        jLabel4.setText("Fecha inicio");

        jLabel5.setText("Fecha final");

        tblPrestamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "CANTIDAD", "FECHA"
            }
        ));
        tblPrestamos.setShowGrid(true);
        jScrollPane2.setViewportView(tblPrestamos);

        btnMostrarLista.setText("Mostrar");
        btnMostrarLista.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 153)));
        btnMostrarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarListaActionPerformed(evt);
            }
        });

        btnDelete.setText("eliminar registro");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jButton4.setText("editar registro");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelListLayout = new javax.swing.GroupLayout(panelList);
        panelList.setLayout(panelListLayout);
        panelListLayout.setHorizontalGroup(
            panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(44, 44, 44)
                        .addComponent(btnDelete)
                        .addGap(182, 182, 182))
                    .addGroup(panelListLayout.createSequentialGroup()
                        .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelListLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(starDate, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMostrarLista, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(12, Short.MAX_VALUE))))
        );
        panelListLayout.setVerticalGroup(
            panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(starDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarLista))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(jButton4))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        root.add(panelList, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Cantidad");

        jLabel2.setText("Fecha de prestamo");

        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        btnSave.setText("Registrar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel3.setText("Concepto/Descripcion");

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        FechaPrestamo.setDateFormatString("dd/M/yyyy");

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FechaPrestamo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(2, 2, 2)
                .addComponent(FechaPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave)
                .addContainerGap(165, Short.MAX_VALUE))
        );

        root.add(panelForm, java.awt.BorderLayout.WEST);

        jLabel10.setText("Operador");

        cmbOperador.setEditable(true);
        cmbOperador.setToolTipText("Buscar por nombre");

        btnOperador.setText("Buscar");
        btnOperador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOperadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout opcionesLayout = new javax.swing.GroupLayout(opciones);
        opciones.setLayout(opcionesLayout);
        opcionesLayout.setHorizontalGroup(
            opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(opcionesLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel10)
                .addGap(12, 12, 12)
                .addComponent(cmbOperador, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnOperador)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        opcionesLayout.setVerticalGroup(
            opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(opcionesLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel10))
            .addGroup(opcionesLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(cmbOperador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(opcionesLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnOperador))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(root, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(opciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(root, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        Validaciones.soloRecibeNumeroConPunto(evt);
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        if (btnSave.getText().endsWith("egistrar")) {
            save();
            clearAll();

        } else if (btnSave.getText().endsWith("datos")) {
            this.update();
            clearAll();
            this.btnSave.setText("Registrar");
           // this.showForms(false, true);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnOperadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOperadorActionPerformed
        searchOperador();
    }//GEN-LAST:event_btnOperadorActionPerformed

    private void btnMostrarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarListaActionPerformed
        validarListadoPrestamo();
        showListBetweenDate(tblPrestamos);
    }//GEN-LAST:event_btnMostrarListaActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        editAction();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser FechaPrestamo;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnMostrarLista;
    private javax.swing.JButton btnOperador;
    private javax.swing.JButton btnSave;
    public javax.swing.JComboBox<String> cmbOperador;
    public com.toedter.calendar.JDateChooser endDate;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel opciones;
    private javax.swing.JPanel panelForm;
    private javax.swing.JPanel panelList;
    private javax.swing.JPanel root;
    public com.toedter.calendar.JDateChooser starDate;
    private javax.swing.JTable tblPrestamos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextArea txtDescripcion;
    // End of variables declaration//GEN-END:variables
}
