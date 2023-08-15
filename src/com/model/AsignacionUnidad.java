package com.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ASIGNACION_UNIDAD")
@NamedQueries({
    @NamedQuery(name = "AsignacionUnidad.findAll", query = "SELECT a FROM AsignacionUnidad a"),
    @NamedQuery(name = "AsignacionUnidad.findById", query = "SELECT a FROM AsignacionUnidad a WHERE a.id = :id"),
    @NamedQuery(name = "AsignacionUnidad.findByFechaInicio", query = "SELECT a FROM AsignacionUnidad a WHERE a.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "AsignacionUnidad.findByFechaFin", query = "SELECT a FROM AsignacionUnidad a WHERE a.fechaFin = :fechaFin"),
    @NamedQuery(name = "AsignacionUnidad.findByKmInicio", query = "SELECT a FROM AsignacionUnidad a WHERE a.kmInicio = :kmInicio"),
    @NamedQuery(name = "AsignacionUnidad.findByKmFinal", query = "SELECT a FROM AsignacionUnidad a WHERE a.kmFinal = :kmFinal"),
    @NamedQuery(name = "AsignacionUnidad.findByStatus", query = "SELECT a FROM AsignacionUnidad a WHERE a.status = :status")})
public class AsignacionUnidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    // @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaInicio;

    @Basic(optional = false)
    @Column(name = "fecha_fin")
    // @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaFin;
    @Basic(optional = false)
    @Column(name = "km_inicio")
    private Float kmInicio;
    @Basic(optional = false)
    @Column(name = "km_final")
    private Float kmFinal;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;

    @ManyToOne()
    @JoinColumn(name = "operador_id")
    private Operador operador;
    @ManyToOne()
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;
//    @Basic(optional = false)
////    @Column(name = "operador_id")
////    private Integer operador_id;

    public AsignacionUnidad() {
    }

    public AsignacionUnidad(Integer id, LocalDateTime fechaInicio, LocalDateTime fechaFin, Float kmInicio, Float kmFinal, String status, Operador operador, Vehiculo vehiculo) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.kmInicio = kmInicio;
        this.kmFinal = kmFinal;
        this.status = status;
        this.operador = operador;
        this.vehiculo = vehiculo;
    }

    public LocalDateTime getFechaInicio() {
       
        if (fechaInicio.toString().isBlank()) {
            return fechaInicio = null;
        } else {
            return fechaInicio;
        }

    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public AsignacionUnidad(Integer id) {
        this.id = id;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getKmInicio() {
        return kmInicio;
    }

    public void setKmInicio(Float kmInicio) {
        this.kmInicio = kmInicio;
    }

    public Float getKmFinal() {
        return kmFinal;
    }

    public void setKmFinal(float kmFinal) {
        this.kmFinal = kmFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof AsignacionUnidad)) {
            return false;
        }
        AsignacionUnidad other = (AsignacionUnidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.AsignacionUnidad[ id=" + id + " ]";
    }

}
