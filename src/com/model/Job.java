package com.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author keatnis
 */
@Entity
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "puesto")
    private String puesto;
    @Basic(optional = false)

    @Column(name = "sueldo_diario")
    private float sueldoDiario;
    @Column(name = "dias_laborales")
    private boolean[] diasLaborales;
    @Column(name = "total_diasLaborales")
    @Basic(optional = false)
    private int totalDiasLaborales;

    public Job() {
    }

    public Job(Long id, String puesto, float sueldoDiario, boolean[] diasLaborales, int totalDiasLaborales) {
        this.id = id;
        this.puesto = puesto;
        this.sueldoDiario = sueldoDiario;
        this.diasLaborales = diasLaborales;
        this.totalDiasLaborales = totalDiasLaborales;
    }

    public boolean[] getDiasLaborales() {
        return diasLaborales;
    }

    public void setDiasLaborales(boolean[] diasLaborales) {
        this.diasLaborales = diasLaborales;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public float getSueldoDiario() {
        return sueldoDiario;
    }

    public void setSueldoDiario(float sueldoDiario) {
        this.sueldoDiario = sueldoDiario;
    }

    public int getTotalDiasLaborales() {
        return totalDiasLaborales;
    }

    public void setTotalDiasLaborales(int totalDiasLaborales) {
        this.totalDiasLaborales = totalDiasLaborales;
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
        if (!(object instanceof Job)) {
            return false;
        }
        Job other = (Job) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Job[ id=" + id + " ]";
    }

}
