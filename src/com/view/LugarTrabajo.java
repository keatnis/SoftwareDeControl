package com.view;

import com.dao.LugarTrabajoDAO;
import com.model.Workplace;
import com.utils.Filter;
import com.utils.Validaciones;
import com.utils.table.RenderTable;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author keatnis
 */
public class LugarTrabajo extends javax.swing.JPanel {

    /**
     * Creates new form LugarTrabajo
     */
    private final LugarTrabajoDAO lugarTrabajoDAO;
    private Workplace workplace;

    public LugarTrabajo() {
        initComponents();
        this.lugarTrabajoDAO = new LugarTrabajoDAO();
        showData(tblLugarTrabajo);
        clearAll();
    }

    private void showData(JTable table) {
        Filter.removeAllRows(table);
        Object[] titles = new Object[]{
            "ID", "CLAVE DE TRABAJO", "NOMBRE OBRA", "PERIODO", "FECHA INICIO", "FECHA FIN"};
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
        List<Workplace> list = lugarTrabajoDAO.getWorkplaces();

        for (Workplace workplace : list) {

            model.addRow(new Object[]{
                workplace.getId(),
                workplace.getClaveTrabajo(),
                workplace.getNombreTrabajo(),
                workplace.getPeriodo(),
                workplace.getFechaInicio(),
                workplace.getFechaFin()

            });
            /*establecemos el modelo  al Jtable llamado jTabla*/

        }

        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.doLayout();
        /* asignamos el ancho de cada columna de la tabla*/

        int[] anchos = {40, 200, 200, 200, 150, 150};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    private void save() {
        workplace = new Workplace();
        workplace.setClaveTrabajo(txtClave.getText());
        workplace.setNombreTrabajo(txtNombreObra.getText());
        workplace.setPeriodo(txtPeriodo.getText());
        workplace.setFechaInicio(dateStart.getDate());
        workplace.setFechaFin(dateEnd.getDate());

        lugarTrabajoDAO.save(workplace);
         this.showData(tblLugarTrabajo);
    }

    private void update() {
        workplace = new Workplace();
        int idLugarTrabajo = Integer.valueOf(
                tblLugarTrabajo.getValueAt(tblLugarTrabajo.getSelectedRow(), 0)
                        .toString());
        workplace.setId(idLugarTrabajo);
        workplace.setClaveTrabajo(txtClave.getText());
        workplace.setNombreTrabajo(txtNombreObra.getText());
        workplace.setPeriodo(txtPeriodo.getText());
        workplace.setFechaInicio(dateStart.getDate());
        workplace.setFechaFin(dateEnd.getDate());

        lugarTrabajoDAO.update(workplace);
        this.showData(tblLugarTrabajo);
    }

    private void editAction() {
        int row = tblLugarTrabajo.getSelectedRow();

        btnSave.setText("actualizar datos");
        btnNew.repaint();

        txtClave.setText(String.valueOf(tblLugarTrabajo.getValueAt(tblLugarTrabajo.getSelectedRow(), 1)));
        txtNombreObra.setText(String.valueOf(tblLugarTrabajo.getValueAt(tblLugarTrabajo.getSelectedRow(), 2)));
        txtPeriodo.setText(String.valueOf(tblLugarTrabajo.getValueAt(tblLugarTrabajo.getSelectedRow(), 3)));
        dateStart.setDate(Validaciones.returnDate(String.valueOf(
                tblLugarTrabajo.getValueAt(tblLugarTrabajo.getSelectedRow(), 4))));
        dateEnd.setDate(Validaciones.returnDate(
                String.valueOf(
                tblLugarTrabajo.getValueAt(tblLugarTrabajo.getSelectedRow(), 5))));
    }

    private void clearAll() {
        txtClave.setText("");
        txtNombreObra.setText("");
        txtPeriodo.setText("");
        dateStart.setDate(null);
        dateEnd.setDate(null);
    }

    private void deleteAction() {
        int idLugarTrabajo = Integer.valueOf(
                tblLugarTrabajo.getValueAt(tblLugarTrabajo.getSelectedRow(), 0)
                        .toString());
        lugarTrabajoDAO.delete(idLugarTrabajo);
      this.showData(tblLugarTrabajo);
    }

    public void showForms(boolean showForm, boolean showList) {
        
        panelForm.setVisible(showForm);
        if (panelForm.isVisible()) {
            this.getAccessibleContext();
        }
        panelList.setVisible(showList);
          this.getAccessibleContext();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelList = new javax.swing.JPanel();
        panek = new javax.swing.JPanel();
        txtSearch = new com.utils.components.txtPlaceholder();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLugarTrabajo = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        panelForm = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lbClave = new javax.swing.JLabel();
        txtClave = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNombreObra = new javax.swing.JTextField();
        lbPeriodo = new javax.swing.JLabel();
        dateStart = new com.toedter.calendar.JDateChooser();
        dateEnd = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        txtPeriodo = new javax.swing.JTextField();

        setLayout(new java.awt.CardLayout());

        panelList.setLayout(new java.awt.GridBagLayout());

        txtSearch.setPlaceholder("Buscar ...");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        tblLugarTrabajo.setAutoCreateRowSorter(true);
        tblLugarTrabajo.setModel(new javax.swing.table.DefaultTableModel(
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
        tblLugarTrabajo.setFocusable(false);
        jScrollPane1.setViewportView(tblLugarTrabajo);

        jPanel3.setLayout(new java.awt.GridLayout(3, 0, 0, 4));

        btnNew.setText("Nuevo");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        jPanel3.add(btnNew);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel3.add(btnEditar);

        btnDelete.setText("eliminar");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel3.add(btnDelete);

        javax.swing.GroupLayout panekLayout = new javax.swing.GroupLayout(panek);
        panek.setLayout(panekLayout);
        panekLayout.setHorizontalGroup(
            panekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(panekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panekLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(panekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panekLayout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panekLayout.setVerticalGroup(
            panekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(panekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panekLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12)
                    .addGroup(panekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        panelList.add(panek, new java.awt.GridBagConstraints());

        add(panelList, "card2");

        panelForm.setPreferredSize(new java.awt.Dimension(800, 404));
        panelForm.setLayout(new java.awt.GridBagLayout());

        lbClave.setText("Clave de Trabajo");

        jLabel1.setText("Nombre de la Obra");

        lbPeriodo.setText("Periodo");

        dateStart.setDateFormatString("dd/MM/yyyy");
        dateStart.setMaximumSize(null);

        dateEnd.setDateFormatString("dd/MM/yyyy");
        dateEnd.setMaxSelectableDate(new java.util.Date(253370790090000L));
        dateEnd.setMaximumSize(null);

        jLabel2.setText("Inicio:");

        jLabel3.setText("Termina;");

        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.hoverBackground"));
        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbPeriodo)
                    .addComponent(jLabel1)
                    .addComponent(lbClave)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave))
                    .addComponent(txtNombreObra, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addComponent(txtClave, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(dateStart, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                    .addComponent(dateEnd, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                    .addComponent(txtPeriodo))
                .addGap(114, 114, 114))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dateEnd, dateStart, txtClave, txtNombreObra});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbClave)
                    .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtNombreObra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbPeriodo)
                    .addComponent(txtPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(dateStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(dateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addGap(22, 22, 22))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelForm.add(jPanel4, gridBagConstraints);

        add(panelForm, "card3");
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (tblLugarTrabajo.getSelectedRow() >= 0) {
            this.showForms(true, false);
            editAction();

        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (tblLugarTrabajo.getSelectedRow() >= 0) {
         
            this.deleteAction();

        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        if (btnSave.getText().endsWith("ardar")) {
            save();
            clearAll();
            this.showForms(false, true);
            btnNew.setEnabled(true);
        } else if (btnSave.getText().endsWith("datos")) {
            this.update();
            clearAll();
            this.showForms(false, true);
            btnNew.setEnabled(true);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        this.showForms(true, false);
        btnNew.setEnabled(false);
        btnSave.setText("Guardar");
        btnSave.repaint();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        clearAll();
        this.showForms(false, true);
           btnNew.setEnabled(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
      Filter.searchInTable(txtSearch, tblLugarTrabajo);
    }//GEN-LAST:event_txtSearchKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private com.toedter.calendar.JDateChooser dateEnd;
    private com.toedter.calendar.JDateChooser dateStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbClave;
    private javax.swing.JLabel lbPeriodo;
    private javax.swing.JPanel panek;
    private javax.swing.JPanel panelForm;
    private javax.swing.JPanel panelList;
    private javax.swing.JTable tblLugarTrabajo;
    private javax.swing.JTextField txtClave;
    private javax.swing.JTextField txtNombreObra;
    private javax.swing.JTextField txtPeriodo;
    private com.utils.components.txtPlaceholder txtSearch;
    // End of variables declaration//GEN-END:variables

}
