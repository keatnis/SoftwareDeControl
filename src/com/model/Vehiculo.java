package com.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author keatnis
 */
@Entity
@Table(name = "VEHICULO")
@NamedQueries({
    @NamedQuery(name = "Vehiculo.findAll", query = "SELECT v FROM Vehiculo v"),
    @NamedQuery(name = "Vehiculo.findById", query = "SELECT v FROM Vehiculo v WHERE v.id = :id"),
    @NamedQuery(name = "Vehiculo.findByMarcaModNum", query = "SELECT v FROM Vehiculo v WHERE v.marca  LIKE '%marca%'"),
    @NamedQuery(name = "Vehiculo.findByModelo", query = "SELECT v FROM Vehiculo v WHERE v.modelo = :modelo"),
    @NamedQuery(name = "Vehiculo.findByNumSerie", query = "SELECT v FROM Vehiculo v WHERE v.numSerie = :numSerie"),
    @NamedQuery(name = "Vehiculo.findByKmActual", query = "SELECT v FROM Vehiculo v WHERE v.kmActual = :kmActual"),
    @NamedQuery(name = "Vehiculo.findByTipoCombustible", query = "SELECT v FROM Vehiculo v WHERE v.tipoCombustible = :tipoCombustible")})

public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vehiculo_id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "marca")
    private String marca;
    @Column(name = "modelo")
    private String modelo;
    @Basic(optional = false)
    @Column(name = "num_serie")
    private String numSerie;
    @Basic(optional = false)
    @Column(name = "km_actual")
    private float kmActual;
    @Basic(optional = false)
    @Column(name = "tipo_combustible")
    private String tipoCombustible;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "type")
    private String type;
    @Column(name = "capacidad")
    private float capacidad;
    @Column(name = "status")
    private String status;
    @Column(name = "fin_renta")
    @Temporal(TemporalType.DATE)
    private Date finRenta;
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servicio> servicios;
    
    public Vehiculo() {
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Vehiculo(Integer id, String marca, String modelo, String numSerie, float kmActual, String tipoCombustible, String descripcion, String type, float capacidad, String status, Date finRenta) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.numSerie = numSerie;
        this.kmActual = kmActual;
        this.tipoCombustible = tipoCombustible;
        this.descripcion = descripcion;
        this.type = type;
        this.capacidad = capacidad;
        this.status = status;
        this.finRenta = finRenta;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(float capacidad) {
        this.capacidad = capacidad;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFinRenta() {
        if (this.finRenta != null) {
            return new java.sql.Date(this.finRenta.getTime());
        } else {
            return this.finRenta = null;
        }
    }

    public void setFinRenta(Date finRenta) {
        this.finRenta = finRenta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public float getKmActual() {
        return kmActual;
    }

    public void setKmActual(float kmActual) {
        this.kmActual = kmActual;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof Vehiculo)) {
            return false;
        }
        Vehiculo other = (Vehiculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Vehiculo[ id=" + id + " ]";
    }

}
