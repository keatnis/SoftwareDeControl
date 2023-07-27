package com.model;

import java.io.Serializable;
import java.util.Date;
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
    private Long id;
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

    public Prestamo(Long id, float prestamo, Date fehcaPrestamo, String descripcion, Operador operador) {
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
        return fehcaPrestamo;
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
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prestamo)) {
            return false;
        }
        Prestamo other = (Prestamo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Prestamo[ id=" + id + " ]";
    }

}
