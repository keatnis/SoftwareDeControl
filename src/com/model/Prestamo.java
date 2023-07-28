package com.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author keatnis
 */
@Entity
@Table(name = "PRESTAMO")
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    private Integer id;
    @Column(name = "prestamo")
    private float prestamo;
    @Column(name = "fecha_prestamo")
    @Temporal(TemporalType.DATE)
    private Date fehcaPrestamo;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @ManyToOne()
    @JoinColumn(name = "operador_id")
    private Operador operador;

    public Prestamo() {
    }

    public Prestamo(Integer id, float prestamo, Date fehcaPrestamo, String descripcion, Operador operador) {
        this.id = id;
        this.prestamo = prestamo;
        this.fehcaPrestamo = fehcaPrestamo;
        this.descripcion = descripcion;
        this.operador = operador;
    }

    public float getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(float prestamo) {
        this.prestamo = prestamo;
    }

    public Date getFehcaPrestamo() {
        if (fehcaPrestamo != null) {
            return  new java.sql.Date(fehcaPrestamo.getTime());
        }else{
            return fehcaPrestamo = null;
        }
        
    }

    public void setFehcaPrestamo(Date fehcaPrestamo) {
        this.fehcaPrestamo = fehcaPrestamo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
          int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

  

    @Override
    public String toString() {
        return "com.model.Prestamo[ id=" + id + " ]";
    }

}
