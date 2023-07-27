
package com.utils.components;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @web https://www.jc-mouse.net/
 * @author Mouse
 */
public class txtPlaceholder extends JTextField{

    private Dimension d = new Dimension(200,32);
    private String placeholder = "";
    private Color phColor= new Color(102, 102, 102);
    private boolean band = true;

    /** Constructor de clase */
    public txtPlaceholder()
    {
        super();
        setSize(d);
        setPreferredSize(d);
        setVisible(true);
        setMargin( new Insets(3,6,3,6));
        //atento a cambios 
        getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                band = (getText().length()>0) ? false:true ;
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                band = false;
            }

            @Override
            public void changedUpdate(DocumentEvent de) {}

        });
    }

    public void setPlaceholder(String placeholder)
    {
        this.placeholder=placeholder;
    }

    public String getPlaceholder()
    {
        return placeholder;
    }

    public Color getPhColor() {
        return phColor;
    }

    public void setPhColor(Color phColor) {
        this.phColor = phColor;
    }    

    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        //color de placeholder 
        g2.setColor( getForeground().darker());
        //dibuja texto
        g2.drawString((band)?placeholder:"",
                     getMargin().left,
                     (getSize().height)/2 + getFont().getSize()/2 );
      }

}//JCTextField:end