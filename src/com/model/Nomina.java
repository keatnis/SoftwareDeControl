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
@Table(name = "NOMINA")
@NamedQueries({
    @NamedQuery(name = "Nomina.findAll", query = "SELECT n FROM Nomina n"),})
public class Nomina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @Basic(optional = false)
    @Column(name = "dias_laborados")
    private int diasLaborados;
    @Basic(optional = false)
    @Column(name = "sueldo_diario")
    private float sueldoDiario;
    @Column(name = "prestamos")
    private Float prestamos;
    @Column(name = "descuentos")
    private Float descuentos;
    @Basic(optional = false)
    @Column(name = "sueldo_neto")
    private float sueldoNeto;
    @Column(name = "observaciones")
    private String observaciones;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_fin")
    private Date fechaFin;
    @Basic(optional = false)
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @ManyToOne()
    @JoinColumn(name = "operador_id")
    private Operador operador;

    public Nomina() {
    }

    public Nomina(Integer id, int diasLaborados, float sueldoDiario, Float prestamos, Float descuentos, float sueldoNeto, String observaciones, Date fechaInicio, Date fechaFin, Date fechaPago, Operador operador) {
        this.id = id;
        this.diasLaborados = diasLaborados;
        this.sueldoDiario = sueldoDiario;
        this.prestamos = prestamos;
        this.descuentos = descuentos;
        this.sueldoNeto = sueldoNeto;
        this.observaciones = observaciones;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaPago = fechaPago;
        this.operador = operador;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

   
    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Date getFechaPago() {
        if (fechaPago != null) {
            return new Date(fechaPago.getTime());
        } else {
            return fechaPago = null;
        }

    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(Float prestamos) {
        this.prestamos = prestamos;
    }

    public int getDiasLaborados() {
        return diasLaborados;
    }

    public void setDiasLaborados(int diasLaborados) {
        this.diasLaborados = diasLaborados;
    }

    public float getSueldoDiario() {
        return sueldoDiario;
    }

    public void setSueldoDiario(float sueldoDiario) {
        this.sueldoDiario = sueldoDiario;
    }

    public Float getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(Float descuentos) {
        this.descuentos = descuentos;
    }

    public float getSueldoNeto() {
        return sueldoNeto;
    }

    public void setSueldoNeto(float sueldoNeto) {
        this.sueldoNeto = sueldoNeto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
        if (!(object instanceof Nomina)) {
            return false;
        }
        Nomina other = (Nomina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Nomina[ id=" + id + " ]";
    }

}
