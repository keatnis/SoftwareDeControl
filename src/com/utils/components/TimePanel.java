
package com.utils.components;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
public class TimePanel extends javax.swing.JPanel {

     Calendar cal = Calendar.getInstance();
  private JTextField texto;
  
    public TimePanel(JTextField jtextField) {
        initComponents();

        texto = jtextField;
        //Se coloca el tiempo del sistema
        Hora.setText( (cal.get(Calendar.HOUR)<10)?"0"+cal.get(Calendar.HOUR):String.valueOf(cal.get(Calendar.HOUR)) );
        Minuto.setText( (cal.get(Calendar.MINUTE)<10)?"0"+cal.get(Calendar.MINUTE):String.valueOf(cal.get(Calendar.MINUTE)+1) );

        if( cal.get( Calendar.AM_PM ) == 0 ){  
            AmPm.setText("AM");
        }else
        {
            AmPm.setText("PM");
        }

        //Eventos de los controles del componente
        btnHora1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                ajustarHora("-");                
            }
        });
        btnHora2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                ajustarHora("+");                
            }
        });

        btnMinuto1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                ajustarMinuto("-");                
            }
        });
        btnMinuto2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                ajustarMinuto("+");                
            }
        });
        btnAmPm1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                AmPm.setText( (AmPm.getText().equals("AM"))?"PM":"AM" );             
            }
        });
        btnAmPm2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                AmPm.setText( (AmPm.getText().equals("AM"))?"PM":"AM" );             
            }
        }); 
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JPopupMenu menu = ((JPopupMenu) getParent());
                menu.setVisible(false);
            }
        }); 
        btnSet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JPopupMenu menu = ((JPopupMenu) getParent());
                texto.setText( Hora.getText() + ":"+Minuto.getText() + " " + AmPm.getText() );
                menu.setVisible(false);
            }
        }); 
    }//end constructor
    
   /** 
 * Metodo privado que ajusta el valor de la hora
 * @param String value cadena que puede tener los valores 
 * "+" para incrementar el valor
 * "-" para decrementar el valor
 */
    private void ajustarHora(String value)
    {
        int h = Integer.valueOf( Hora.getText() );                

        if( value.equals("+"))
        {
            h = (h==12)?1:(h+1);    
        }
        else if( value.equals("-"))
        {
            h = (h==1)?12:(h-1);
        }
        String s = (h<10)?"0"+h:String.valueOf(h);
        Hora.setText(s);
    }

    /** 
 * Metodo privado que ajusta el valor del minuto
 * @param String value cadena que puede tener los valores 
 * "+" para incrementar el valor
 * "-" para decrementar el valor
 */
    private void ajustarMinuto(String value)
    {
        int m = Integer.valueOf( Minuto.getText() );                

        if( value.equals("+"))
        {
            m = (m==59)?0:(m+1);    
        }
        else if( value.equals("-"))
        {
            m = (m==1)?59:(m-1);
        }
        String s = (m<10)?"0"+m:String.valueOf(m);
        Minuto.setText(s);
    }  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnHora1 = new javax.swing.JLabel();
        btnHora2 = new javax.swing.JLabel();
        Hora = new javax.swing.JLabel();
        Minuto = new javax.swing.JLabel();
        btnMinuto1 = new javax.swing.JLabel();
        btnMinuto2 = new javax.swing.JLabel();
        AmPm = new javax.swing.JLabel();
        btnAmPm1 = new javax.swing.JLabel();
        btnAmPm2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnSet = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        jLabel1.setText("Seleccionar hora");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        btnHora1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/utils/icon/arrowup.png"))); // NOI18N
        btnHora1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        jPanel1.add(btnHora1, gridBagConstraints);

        btnHora2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/utils/icon/arrowdown.png"))); // NOI18N
        btnHora2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel1.add(btnHora2, gridBagConstraints);

        Hora.setBackground(new java.awt.Color(255, 255, 255));
        Hora.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        Hora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Hora.setText("00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        jPanel1.add(Hora, gridBagConstraints);

        Minuto.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        Minuto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Minuto.setText("00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 6, 0, 0);
        jPanel1.add(Minuto, gridBagConstraints);

        btnMinuto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/utils/icon/arrowup.png"))); // NOI18N
        btnMinuto1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 6, 0, 0);
        jPanel1.add(btnMinuto1, gridBagConstraints);

        btnMinuto2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/utils/icon/arrowdown.png"))); // NOI18N
        btnMinuto2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 11, 0);
        jPanel1.add(btnMinuto2, gridBagConstraints);

        AmPm.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        AmPm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AmPm.setText("00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 32, 0, 20);
        jPanel1.add(AmPm, gridBagConstraints);

        btnAmPm1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/utils/icon/arrowup.png"))); // NOI18N
        btnAmPm1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 32, 0, 20);
        jPanel1.add(btnAmPm1, gridBagConstraints);

        btnAmPm2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/utils/icon/arrowdown.png"))); // NOI18N
        btnAmPm2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 32, 11, 20);
        jPanel1.add(btnAmPm2, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel11.setText(":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 6, 0, 0);
        jPanel1.add(jLabel11, gridBagConstraints);

        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancel.setText("Cancelar");

        btnSet.setFont(new java.awt.Font("Cantarell", 1, 16)); // NOI18N
        btnSet.setText("Aceptar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSet, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancel, btnSet});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel)
                    .addComponent(btnSet))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCancel, btnSet});

    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AmPm;
    private javax.swing.JLabel Hora;
    private javax.swing.JLabel Minuto;
    private javax.swing.JLabel btnAmPm1;
    private javax.swing.JLabel btnAmPm2;
    private javax.swing.JButton btnCancel;
    private javax.swing.JLabel btnHora1;
    private javax.swing.JLabel btnHora2;
    private javax.swing.JLabel btnMinuto1;
    private javax.swing.JLabel btnMinuto2;
    private javax.swing.JButton btnSet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
