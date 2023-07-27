package com.view;

import com.dao.UserDao;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.model.User;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import com.utils.Hash;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 *
 * @author keatnis
 */
public class Login extends javax.swing.JFrame {

    /**
     * Login form create thread for the mainSystem use hash for encrypted the
     * password
     */
      Hash hash;
    private Login splashFrame = this;
    public static boolean frameInicio = false;
    public static boolean btnLog = true;

    public Login() {
        if (!this.isActive()) {
            initComponents();
            Image img4 = this.toImage(new ImageIcon(getClass().getResource("/com/utils/icon/icon.jpg")));
            ImageIcon img3 = new ImageIcon(img4.getScaledInstance(
                    lbIcon.getWidth(), lbIcon.getHeight(), Image.SCALE_SMOOTH));
            lbIcon.setIcon(img3);
            btnLog = true;
        }

    }

    public Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }

    private void startThread(String user) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MainSystem appFrame = new MainSystem(user, splashFrame);

                if (!appFrame.isActive()) {
                    appFrame.setLocationRelativeTo(null);
                    appFrame.setVisible(true);
                    frameInicio = true;
                    dispose();

                } else {
                    appFrame.dispose();
                }
            }
        });
        thread.start();
    }

    public static void Events() {

    }

    private void login() {

        UserDao userDao = new UserDao();

        String pass = new String(password.getPassword());
        if (!nick.getText().equals("") && !pass.equals("")) {
             String nuevopass = Hash.getHash(pass,"sha1");
             String passMD5 = Hash.md5(pass);
            List<User> usr = userDao.loginByUser(nick.getText());
            System.out.println("user db "+usr.get(0).getPassword()  + "encriptado sha1 "+nuevopass +"md5 "+passMD5);
            if (usr.get(0).getPassword().equals(nuevopass)) {
                
                startThread(usr.get(0).getNombre().toString());
                
            }else{
                
                return;
            }
            return;
            //  startThread();
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese su usuario y contrseña");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        info = new javax.swing.JPanel();
        lbIcon = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        login = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nick = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnClose = new JButton( new FlatSVGIcon("com/utils/icon/close.svg"));
        lbprogress = new javax.swing.JLabel();
        ProgressBar = new javax.swing.JProgressBar();
        lightDarkMode1 = new com.theme.LightDarkMode();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setAlwaysOnTop(true);
        setMaximumSize(null);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lbIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/utils/icon/icon.jpg"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("JetBrains Mono NL ExtraBold", 0, 14)); // NOI18N
        jLabel3.setText("Agustina Vázquez Ortiz");

        javax.swing.GroupLayout infoLayout = new javax.swing.GroupLayout(info);
        info.setLayout(infoLayout);
        infoLayout.setHorizontalGroup(
            infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(infoLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel3))
        );
        infoLayout.setVerticalGroup(
            infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jLabel3))
        );

        login.setLayout(new java.awt.CardLayout(22, 11));

        jPanel1.setAlignmentX(2.0F);
        jPanel1.setAlignmentY(1.0F);
        jPanel1.setLayout(new java.awt.GridLayout(6, 1, 1, 23));

        jLabel1.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Iniciar Sesión");
        jPanel1.add(jLabel1);

        nick.setText("admin");
        jPanel1.add(nick);

        password.setText("admin");
        jPanel1.add(password);

        btnLogin.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.focusColor"));
        btnLogin.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogin);

        login.add(jPanel1, "card2");

        btnClose.setBorderPainted(false);
        btnClose.setContentAreaFilled(false);
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });

        lbprogress.setText(".");
        lbprogress.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(520, 520, 520)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(lightDarkMode1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(12, 12, 12)
                                .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbprogress, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(ProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lightDarkMode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbprogress, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getAccessibleContext().setAccessibleParent(this);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        login();
      //  btnLog = false;
    }//GEN-LAST:event_btnLoginActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btnCloseMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        FlatLaf.registerCustomDefaultsSource("com.theme");

        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JProgressBar ProgressBar;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnLogin;
    private javax.swing.JPanel info;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbIcon;
    public javax.swing.JLabel lbprogress;
    private com.theme.LightDarkMode lightDarkMode1;
    private javax.swing.JPanel login;
    private javax.swing.JTextField nick;
    private javax.swing.JPasswordField password;
    // End of variables declaration//GEN-END:variables
    public JProgressBar getProgressBar() {
        return ProgressBar;
    }

    public JLabel getlbprogress() {
        return lbprogress;
    }

}
