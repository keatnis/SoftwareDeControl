package com.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author keatnis
 */
@Entity
@Table(name = "DETALLE_COMBUSTIBLE")
public class DetalleCombustible implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private Integer id;
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "precioxlitro")
    private float precio;

    public DetalleCombustible() {
    }

    public DetalleCombustible(Integer id, String tipo, float precio) {
        this.id = id;
        this.tipo = tipo;
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }





    @Override
    public String toString() {
        return "com.model.DetalleCombustible[ id=" + id + " ]";
    }

}
