package com.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author keatnis
 */
@Entity
@Table(name = "WORKPLACE")
@NamedQueries({
    @NamedQuery(name = "Workplace.findAll", query = "SELECT w FROM Workplace w"),
    @NamedQuery(name = "Workplace.findById", query = "SELECT w FROM Workplace w WHERE w.id = :id"),
    @NamedQuery(name = "Workplace.findByClaveTrabajo", query = "SELECT w FROM Workplace w WHERE w.claveTrabajo = :claveTrabajo"),
    @NamedQuery(name = "Workplace.findByNombreTrabajo", query = "SELECT w FROM Workplace w WHERE w.nombreTrabajo = :nombreTrabajo"),
    @NamedQuery(name = "Workplace.findByPeriodo", query = "SELECT w FROM Workplace w WHERE w.periodo = :periodo")})
public class Workplace implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "clave_trabajo")
    private String claveTrabajo;
    @Basic(optional = false)
    @Column(name = "nombre_trabajo")
    private String nombreTrabajo;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fehca_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "periodo")
    private String periodo;

    public Workplace() {
    }

    public Workplace(Integer id) {
        this.id = id;
    }

    public Workplace(Integer id, String claveTrabajo, String nombreTrabajo, Date fechaInicio, Date fechaFin, String periodo) {
        this.id = id;
        this.claveTrabajo = claveTrabajo;
        this.nombreTrabajo = nombreTrabajo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.periodo = periodo;
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return new java.sql.Date(fechaInicio.getTime());
        } else {
            return fechaInicio = null;
        }
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        if (fechaFin != null) {
            return new java.sql.Date(fechaFin.getTime());
        } else {
            return fechaFin = null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClaveTrabajo() {
        return claveTrabajo;
    }

    public void setClaveTrabajo(String claveTrabajo) {
        this.claveTrabajo = claveTrabajo;
    }

    public String getNombreTrabajo() {
        return nombreTrabajo;
    }

    public void setNombreTrabajo(String nombreTrabajo) {
        this.nombreTrabajo = nombreTrabajo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
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
        if (!(object instanceof Workplace)) {
            return false;
        }
        Workplace other = (Workplace) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Workplace[ id=" + id + " ]";
    }

}
