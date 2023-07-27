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
@Table(name = "FLETE")
@NamedQueries({
    @NamedQuery(name = "Flete.findAll", query = "SELECT f FROM Flete f"),
    @NamedQuery(name = "Flete.findById", query = "SELECT f FROM Flete f WHERE f.id = :id"),
    @NamedQuery(name = "Flete.findByFecha", query = "SELECT f FROM Flete f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "Flete.findByLugarSalida", query = "SELECT f FROM Flete f WHERE f.lugarSalida = :lugarSalida"),
    @NamedQuery(name = "Flete.findByResponsable", query = "SELECT f FROM Flete f WHERE f.responsable = :responsable"),
    @NamedQuery(name = "Flete.findByConcepto", query = "SELECT f FROM Flete f WHERE f.concepto = :concepto"),
    @NamedQuery(name = "Flete.findByRecibe", query = "SELECT f FROM Flete f WHERE f.recibe = :recibe"),
    @NamedQuery(name = "Flete.findByStatus", query = "SELECT f FROM Flete f WHERE f.status = :status ORDER BY f.fecha ASC")
})
public class Flete implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "lugar_salida")
    private String lugarSalida;
    @Column(name = "responsable")
    private String responsable;
    @Basic(optional = false)
    @Column(name = "concepto")
    private String concepto;
    @Basic(optional = false)
    @Column(name = "recibe")
    private String recibe;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @ManyToOne()
    @JoinColumn(name = "workplace_id")
    private Workplace workplace;
    @ManyToOne()
    @JoinColumn(name = "asignacion_unidad_id")
    private AsignacionUnidad asignacionUnidad;
    @ManyToOne()
    @JoinColumn(name = "recarga_combustible_id")
    private RecargaCombustible recargaCombustible;

    public Flete() {
    }

    public Flete(Integer id, Date fecha, String lugarSalida, String responsable, String concepto, String recibe, String status, Workplace workplace, AsignacionUnidad asignacionUnidad, RecargaCombustible recargaCombustible) {
        this.id = id;
        this.fecha = fecha;
        this.lugarSalida = lugarSalida;
        this.responsable = responsable;
        this.concepto = concepto;
        this.recibe = recibe;
        this.status = status;
        this.workplace = workplace;
        this.asignacionUnidad = asignacionUnidad;
        this.recargaCombustible = recargaCombustible;
    }

    public RecargaCombustible getRecargaCombustible() {
        return recargaCombustible;
    }

    public void setRecargaCombustible(RecargaCombustible recargaCombustible) {
        this.recargaCombustible = recargaCombustible;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public Workplace getLugarTrabajo() {
        return workplace;
    }

    public void setLugarTrabajo(Workplace lugarTrabajo) {
        this.workplace = lugarTrabajo;
    }

    public AsignacionUnidad getAsignacionUnidad() {
        return asignacionUnidad;
    }

    public void setAsignacionUnidad(AsignacionUnidad asignacionUnidad) {
        this.asignacionUnidad = asignacionUnidad;
    }

    public Flete(Integer id) {
        this.id = id;
    }

    public Flete(Integer id, Date fecha, String lugarSalida, String concepto, String recibe) {
        this.id = id;
        this.fecha = fecha;
        this.lugarSalida = lugarSalida;
        this.concepto = concepto;
        this.recibe = recibe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getLugarSalida() {
        return lugarSalida;
    }

    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getRecibe() {
        return recibe;
    }

    public void setRecibe(String recibe) {
        this.recibe = recibe;
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
        if (!(object instanceof Flete)) {
            return false;
        }
        Flete other = (Flete) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Flete[ id=" + id + " ]";
    }

}
