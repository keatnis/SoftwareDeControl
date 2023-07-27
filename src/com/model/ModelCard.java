package com.model;

import javax.swing.Icon;

/**
 *
 * @author keatnis
 */
public class ModelCard {

    String Values, Description,ruta;
    Icon icon;

    public ModelCard(String Values, String Description, String ruta) {
        this.Values = Values;
        this.Description = Description;
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getValues() {
        return Values;
    }

    public void setValues(String Values) {
        this.Values = Values;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
}
