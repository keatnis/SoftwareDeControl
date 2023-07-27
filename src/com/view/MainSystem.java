package com.view;
import com.utils.icon.CalcularNomina;
import java.awt.BorderLayout;
import java.awt.Component;


import javax.swing.JOptionPane;


public class MainSystem extends javax.swing.JFrame {
    private Login splashFrame;

    public MainSystem() {

        initComponents();
        MenuBar.setVisible(false);
     //   this.showForm(new Dashboard(), "Dashboard");
    
//        this.showForm(new Dasboard(), "Dasboard");

    }


    MainSystem(String  user, Login splashFrame) {
        this.splashFrame = splashFrame;
        setProgress(0, "Cargando componentes del Sistema");
        initComponents();
        setProgress(15, "Conectándose la base de datos");
        setProgress(23, "Cargando Módulos");
        this.showForm(new Dashboard(), "Dashboard");
        setProgress(65, "Cargando interfaces");
        // this.setShape(new RoundRectangle2D.Double(0, 0, getWidth(),getHeight(), 20, 20));
     
        lbUser.setText("Usuario: "+user);
        this.repaint();
        setProgress(65, "Cargando interfaces");
        setProgress(100, "Carga completa");
//        initn();
    }


    private void setProgress(int percent, String information) {
        splashFrame.getlbprogress().setText(information);
        splashFrame.getProgressBar().setValue(percent);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "No se pudo ejecutar");
        }
    }


    public void showForm(Component form, String title) {
        lbTitle.setText(title);
        panelBody.removeAll();
        panelBody.add(form, BorderLayout.CENTER);
        panelBody.repaint();
        panelBody.revalidate();
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popMenuUsuario = new javax.swing.JPopupMenu();
        cambiarAvatar = new javax.swing.JMenuItem();
        cambiarContraseña = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        CerrarSesion = new javax.swing.JMenuItem();
        panelBody = new javax.swing.JPanel();
        panelEnc = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbUser = new javax.swing.JLabel();
        lbTitle = new javax.swing.JLabel();
        MenuBar = new javax.swing.JMenuBar();
        Dasboard = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        menuWorkplace = new javax.swing.JMenuItem();
        recarga = new javax.swing.JMenuItem();
        registrarVehiculo = new javax.swing.JMenu();
        menuAddVehiculo = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        registrarServicio = new javax.swing.JMenuItem();
        historialServicio = new javax.swing.JMenuItem();
        programarServicio = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        menuCalculoNomina = new javax.swing.JMenuItem();
        menuPersonal = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        incidencias = new javax.swing.JMenu();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();

        cambiarAvatar.setText("Cambiar Icono");
        cambiarAvatar.setActionCommand("cvbvb");
        cambiarAvatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarAvatarActionPerformed(evt);
            }
        });
        popMenuUsuario.add(cambiarAvatar);
        cambiarAvatar.getAccessibleContext().setAccessibleName("MenuUser");
        cambiarAvatar.getAccessibleContext().setAccessibleDescription("MenuUser");

        cambiarContraseña.setText("Cambiar conrtraseña");
        cambiarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarContraseñaActionPerformed(evt);
            }
        });
        popMenuUsuario.add(cambiarContraseña);
        popMenuUsuario.add(jSeparator1);

        CerrarSesion.setText("Cerrar Sesion");
        CerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarSesionActionPerformed(evt);
            }
        });
        popMenuUsuario.add(CerrarSesion);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Software");
        setMaximumSize(new java.awt.Dimension(3647, 2147));
        setPreferredSize(new java.awt.Dimension(1305, 750));

        panelBody.setOpaque(false);
        panelBody.setLayout(new java.awt.BorderLayout());

        panelEnc.setBackground(new java.awt.Color(0, 47, 85));
        panelEnc.setInheritsPopupMenu(true);
        panelEnc.setOpaque(false);
        panelEnc.setPreferredSize(new java.awt.Dimension(1374, 74));

        lbUser.setFont(new java.awt.Font("JetBrains Mono NL Light", 1, 14)); // NOI18N
        lbUser.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbUser.setText("User Name");
        lbUser.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lbTitle.setFont(new java.awt.Font("Poppins SemiBold", 1, 20)); // NOI18N
        lbTitle.setText("Titulo del Módulo");

        javax.swing.GroupLayout panelEncLayout = new javax.swing.GroupLayout(panelEnc);
        panelEnc.setLayout(panelEncLayout);
        panelEncLayout.setHorizontalGroup(
            panelEncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 344, Short.MAX_VALUE)
                .addComponent(lbTitle)
                .addGap(329, 329, 329)
                .addComponent(lbUser, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );
        panelEncLayout.setVerticalGroup(
            panelEncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTitle)
                    .addComponent(lbUser))
                .addGap(13, 13, 13)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        MenuBar.setBackground(new java.awt.Color(31, 44, 81));
        MenuBar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        MenuBar.setMaximumSize(new java.awt.Dimension(1054, 32769));
        MenuBar.setPreferredSize(new java.awt.Dimension(903, 28));

        Dasboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/utils/icon/dashboard.png"))); // NOI18N
        Dasboard.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        Dasboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DasboardMouseClicked(evt);
            }
        });
        Dasboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DasboardActionPerformed(evt);
            }
        });
        MenuBar.add(Dasboard);

        jMenu2.setText("Fletes");

        jMenuItem5.setText("Registrar flete");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        menuWorkplace.setText("Registrar Lugar de Trabajo");
        menuWorkplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuWorkplaceActionPerformed(evt);
            }
        });
        jMenu2.add(menuWorkplace);

        recarga.setText("Detalle Combustible");
        recarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recargaActionPerformed(evt);
            }
        });
        jMenu2.add(recarga);

        MenuBar.add(jMenu2);

        registrarVehiculo.setText("Vehiculos");

        menuAddVehiculo.setText("Registrar Vehiculo");
        menuAddVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddVehiculoActionPerformed(evt);
            }
        });
        registrarVehiculo.add(menuAddVehiculo);

        MenuBar.add(registrarVehiculo);

        jMenu3.setText("Servicios");

        registrarServicio.setText("Registrar servicio");
        registrarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrarServicioActionPerformed(evt);
            }
        });
        jMenu3.add(registrarServicio);

        historialServicio.setText("Historial de Servicios");
        historialServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historialServicioActionPerformed(evt);
            }
        });
        jMenu3.add(historialServicio);

        programarServicio.setText("Programar servicio");
        programarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                programarServicioActionPerformed(evt);
            }
        });
        jMenu3.add(programarServicio);

        MenuBar.add(jMenu3);

        jMenu1.setText("Nomina");

        jMenuItem4.setText("Registrar prestamos");
        jMenu1.add(jMenuItem4);

        jMenuItem6.setText("Descuentos/ Horas Extras");
        jMenu1.add(jMenuItem6);

        menuCalculoNomina.setText("Calcular nómina");
        menuCalculoNomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCalculoNominaActionPerformed(evt);
            }
        });
        jMenu1.add(menuCalculoNomina);

        MenuBar.add(jMenu1);

        menuPersonal.setText("Operadores");
        menuPersonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPersonalActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Lista y Registro");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuPersonal.add(jMenuItem1);

        jMenuItem3.setText("Dar de baja");
        menuPersonal.add(jMenuItem3);

        MenuBar.add(menuPersonal);

        incidencias.setText("Usuarios");
        incidencias.setFocusPainted(true);
        incidencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incidenciasActionPerformed(evt);
            }
        });
        incidencias.add(jSeparator2);

        jMenuItem2.setText("Registrar Usuario");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        incidencias.add(jMenuItem2);

        MenuBar.add(incidencias);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelEnc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1105, Short.MAX_VALUE)
                    .addComponent(panelBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panelEnc, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuPersonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPersonalActionPerformed

    }//GEN-LAST:event_menuPersonalActionPerformed

    private void DasboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DasboardMouseClicked
        // TODO add your handling code here:
        this.showForm(new Dashboard(), "Dashboard");
       
    }//GEN-LAST:event_DasboardMouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.showForm(new TrabajadoresView(), "Menú > Trabajadores");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void CerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarSesionActionPerformed

        int option = JOptionPane.showConfirmDialog(null, "Esta seguro(a) de cerrar el sistema?", "Mensaje", JOptionPane.OK_CANCEL_OPTION);
        if (option == 0) {
            this.dispose();
            Login login = new Login();
            login.setVisible(true);
        }


    }//GEN-LAST:event_CerrarSesionActionPerformed

    private void cambiarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarContraseñaActionPerformed
 
    }//GEN-LAST:event_cambiarContraseñaActionPerformed

    private void cambiarAvatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarAvatarActionPerformed
       // this.showForm(new UserView(), "Lista de Personal");
    }//GEN-LAST:event_cambiarAvatarActionPerformed

    private void incidenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incidenciasActionPerformed
        
    }//GEN-LAST:event_incidenciasActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
       this.showForm(new UserView(), "Usuarios");
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void DasboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DasboardActionPerformed
     
    }//GEN-LAST:event_DasboardActionPerformed

    private void menuAddVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddVehiculoActionPerformed
        this.showForm(new Vehiculos(), "Menu: Vehiculos");
    }//GEN-LAST:event_menuAddVehiculoActionPerformed

    private void registrarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrarServicioActionPerformed
       this.showForm(new Servicios(), "Menu: Servicios");
    }//GEN-LAST:event_registrarServicioActionPerformed

    private void programarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_programarServicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_programarServicioActionPerformed

    private void historialServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historialServicioActionPerformed
        this.showForm(new HistorialServicios(), "Menu: Servicios > Historial de servicios ");
    }//GEN-LAST:event_historialServicioActionPerformed

    private void menuWorkplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuWorkplaceActionPerformed
       this.showForm(new LugarTrabajo(), "Menú : Flete > Lugar de Trabajo");
    }//GEN-LAST:event_menuWorkplaceActionPerformed

    private void recargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recargaActionPerformed
        DetallesCombustible dialog = new DetallesCombustible(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dialog.setVisible(false);
            }
        });
        dialog.setVisible(true);
    }//GEN-LAST:event_recargaActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
    this.showForm(new FleteView(), "Flete > Registro de flete");
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void menuCalculoNominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCalculoNominaActionPerformed
        this.showForm(new CalcularNomina(), "Nomina");
    }//GEN-LAST:event_menuCalculoNominaActionPerformed

//    public static void main(ModelUsers user) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new MainSystem(user).setVisible(true);
//            }
//        });
//    }
//    public static void main(ModelUsers user) {
//        FlatIntelliJLaf.registerCustomDefaultsSource("com/style");
//        FlatLightLaf.setup();
//        FlatIntelliJLaf.setup();
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new MainSystem2().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem CerrarSesion;
    private javax.swing.JMenu Dasboard;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenuItem cambiarAvatar;
    private javax.swing.JMenuItem cambiarContraseña;
    private javax.swing.JMenuItem historialServicio;
    private javax.swing.JMenu incidencias;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    public javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUser;
    private javax.swing.JMenuItem menuAddVehiculo;
    private javax.swing.JMenuItem menuCalculoNomina;
    private javax.swing.JMenu menuPersonal;
    private javax.swing.JMenuItem menuWorkplace;
    public javax.swing.JPanel panelBody;
    private javax.swing.JPanel panelEnc;
    private javax.swing.JPopupMenu popMenuUsuario;
    private javax.swing.JMenuItem programarServicio;
    private javax.swing.JMenuItem recarga;
    private javax.swing.JMenuItem registrarServicio;
    private javax.swing.JMenu registrarVehiculo;
    // End of variables declaration//GEN-END:variables
}
