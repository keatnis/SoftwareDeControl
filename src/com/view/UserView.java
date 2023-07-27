package com.view;

import com.dao.UserDao;
import com.utils.Hash;
import java.util.List;
import com.utils.Filter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author keatnis
 */
public class UserView extends javax.swing.JPanel {

    private com.model.User user;
    private UserDao userDao;

    /**
     * Creates new form User
     */
    public UserView() {
        initComponents();
        this.userDao = new UserDao();
        showData(tblUser);
        this.showForms(false, true);
    }

    public void showForms(boolean showForm, boolean showList) {

        panelForm.setVisible(showForm);
        if (panelForm.isVisible()) {
            this.getAccessibleContext();
        }
        panelList.setVisible(showList);
    }

    //metodo para  asignar los valores de cada atributo y usar el dao para guardar los datos en la db
    private void save() {
        user = new com.model.User();
        user.setNombre(txtName.getText());
        user.setApePaterno(txtApaterno.getText());
        user.setApeMaterno(txtAMaterno.getText());
        user.setNickname(txtUsuario.getText());
        /* encirtamos la contraseña con sha */
        String pass = password.getPassword().toString();
        String newPass = Hash.sha1(pass);
        System.out.println("pass en user view " + newPass);
        String md5 =Hash.md5(pass);
        System.out.println("encrp "+md5);
        user.setPassword(pass);
        user.setRole((String) cmbRole.getSelectedItem());
        userDao.addUser(user);

    }

    // metodo para limpiar los componentes
    private void cleaAll() {
        txtName.setText(null);
        txtAMaterno.setText("");
        txtApaterno.setText("");
        txtUsuario.setText("");
        password.setText(null);
        cmbRole.setSelectedIndex(0);

    }

    private void showData(JTable table) {
        Filter.removeAllRows(table);
        Object[] titles = new Object[]{"ID", "NOMBRE COMPLETO", "USUARIO", "ROL"};
        /*coloco el nombre de las  columnas de la tabla USER a el modelo */
        DefaultTableModel model = new DefaultTableModel(null, titles) {

            // desactivamos la opcion de editar los datos en la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
//        RenderTable render = new RenderTable();
        //  tblUser.setDefaultRenderer(Object.class, render);
        /* obtengo la lista de */
        List<com.model.User> list = userDao.getData();
        for (com.model.User usr : list) {
            model.addRow(new Object[]{
                usr.getId(),
                usr.getNombre() + " " + usr.getApePaterno() + " " + usr.getApeMaterno(),
                usr.getNickname(),
                usr.getRole()

            });
            /*establecemos el modelo  al Jtable llamado jTabla*/

        }
        table.setModel(model);
        table.doLayout();
        /* asignamos el ancho de cada columna de la tabla*/

        int[] anchos = {30, 200, 80, 50};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    private void editAction() {
        int row = tblUser.getSelectedRow();

        btnSave.setText("actualizar datos");
        btnNew.repaint();
        //pasando datos al formulario
        String[] nombre = String.valueOf(tblUser.getValueAt(row, 1)).split(" ");

        txtName.setText(nombre[0]);
        txtApaterno.setText(nombre[1]);
        txtAMaterno.setText(nombre[2]);
        nombre = null;
        /* para cambiar la contraseña del usuario debe ser administrador */
        password.setEnabled(true);
        txtUsuario.setText((String) tblUser.getValueAt(row, 2));
        cmbRole.setSelectedItem(tblUser.getValueAt(row, 3));
        this.showForms(true, false);
        //  showForm(formPanel);
    }

    private void update() {

        user = new com.model.User();
        int idUser = Integer.valueOf(
                tblUser.getValueAt(tblUser.getSelectedRow(), 0)
                        .toString());
        user.setId(idUser);
        user.setNombre(txtName.getText());
        user.setApePaterno(txtApaterno.getText());
        user.setApeMaterno(txtAMaterno.getText());
        user.setNickname(txtUsuario.getText());
        user.setRole((String) cmbRole.getSelectedItem());
        String pass = password.getPassword().toString();
        String newPass = Hash.sha1(pass);
        System.out.println("pass en user view " + newPass);
        String md5 =Hash.md5(pass);
        System.out.println("encrp "+md5);
      user.setPassword(newPass);
        userDao.update(user);
        showData(tblUser);
        this.showForms(false, true);
    }

    private void deleteAction() {
        int idUser = Integer.valueOf(
                tblUser.getValueAt(tblUser.getSelectedRow(), 0)
                        .toString());
        userDao.delete(idUser);
        this.showForms(false, true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        root = new javax.swing.JPanel();
        panelList = new javax.swing.JPanel();
        txtSearch = new com.utils.components.txtPlaceholder();
        panelOptions = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        tbnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        panelForm = new javax.swing.JPanel();
        options = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        form = new javax.swing.JPanel();
        lbName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lbApa = new javax.swing.JLabel();
        txtApaterno = new javax.swing.JTextField();
        lbAma = new javax.swing.JLabel();
        txtAMaterno = new javax.swing.JTextField();
        lbUser = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lbUser1 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        cmbRole = new javax.swing.JComboBox<>();

        setLayout(new java.awt.GridBagLayout());

        root.setLayout(new java.awt.CardLayout());

        panelList.setPreferredSize(new java.awt.Dimension(800, 400));

        txtSearch.setPlaceholder("Buscar ...");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

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

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblUser);

        javax.swing.GroupLayout panelListLayout = new javax.swing.GroupLayout(panelList);
        panelList.setLayout(panelListLayout);
        panelListLayout.setHorizontalGroup(
            panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListLayout.createSequentialGroup()
                .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelListLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(panelOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelListLayout.setVerticalGroup(
            panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(panelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelListLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(panelOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        root.add(panelList, "card2");

        panelForm.setPreferredSize(new java.awt.Dimension(800, 400));

        btnSave.setBackground(new java.awt.Color(51, 51, 51));
        btnSave.setFont(new java.awt.Font("Cantarell", 1, 16)); // NOI18N
        btnSave.setForeground(new java.awt.Color(204, 204, 0));
        btnSave.setText("guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("cancelar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout optionsLayout = new javax.swing.GroupLayout(options);
        options.setLayout(optionsLayout);
        optionsLayout.setHorizontalGroup(
            optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel)
                .addGap(18, 18, 18)
                .addComponent(btnSave)
                .addContainerGap())
        );
        optionsLayout.setVerticalGroup(
            optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        form.setLayout(new java.awt.GridLayout(3, 1, 10, 30));

        lbName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbName.setText("Nombre:");
        lbName.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        form.add(lbName);
        form.add(txtName);

        lbApa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbApa.setText("A. Paterno:");
        form.add(lbApa);
        form.add(txtApaterno);

        lbAma.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbAma.setText("A. Materno");
        form.add(lbAma);
        form.add(txtAMaterno);

        lbUser.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbUser.setText("Usuario");
        form.add(lbUser);
        form.add(txtUsuario);

        lbUser1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbUser1.setText("Password");
        form.add(lbUser1);
        form.add(password);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Role");
        form.add(jLabel1);

        cmbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Admin", "User" }));
        form.add(cmbRole);

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(form, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGap(459, 459, 459)
                .addComponent(options, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(form, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(options, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        root.add(panelForm, "card3");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 6);
        add(root, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        Filter.searchInTable(txtSearch, tblUser);
    }//GEN-LAST:event_txtSearchKeyTyped

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (tblUser.getSelectedRow() >= 0) {
            editAction();
        }

    }//GEN-LAST:event_btnEditActionPerformed

    private void tbnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnDeleteActionPerformed
        deleteAction();
        showData(tblUser);
    }//GEN-LAST:event_tbnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (btnSave.getText().endsWith("ardar")) {
            save();
            cleaAll();
            // this.showForm(list);
            this.showData(tblUser);
            btnNew.setEnabled(true);
        } else if (btnSave.getText().endsWith("datos")) {
            update();
            cleaAll();
//            this.showForm(list);
            btnNew.setEnabled(true);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        cleaAll();
        this.showForms(false, true);
        btnNew.setEnabled(true);

    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        this.showForms(true, false);
        btnNew.setEnabled(false);
        btnSave.setText("guardar");
        btnSave.repaint();
        password.setEnabled(true);
    }//GEN-LAST:event_btnNewActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton btnCancel;
    javax.swing.JButton btnEdit;
    javax.swing.JButton btnNew;
    javax.swing.JButton btnSave;
    javax.swing.JComboBox<String> cmbRole;
    javax.swing.JPanel form;
    javax.swing.JLabel jLabel1;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JLabel lbAma;
    javax.swing.JLabel lbApa;
    javax.swing.JLabel lbName;
    javax.swing.JLabel lbUser;
    javax.swing.JLabel lbUser1;
    javax.swing.JPanel options;
    javax.swing.JPanel panelForm;
    javax.swing.JPanel panelList;
    javax.swing.JPanel panelOptions;
    javax.swing.JPasswordField password;
    javax.swing.JPanel root;
    javax.swing.JTable tblUser;
    javax.swing.JButton tbnDelete;
    javax.swing.JTextField txtAMaterno;
    javax.swing.JTextField txtApaterno;
    javax.swing.JTextField txtName;
    com.utils.components.txtPlaceholder txtSearch;
    javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
