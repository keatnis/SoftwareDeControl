package com.model;

import java.io.Serializable;
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

/**
 *
 * @author keatnis
 */
@Entity
@Table(name = "RECARGA_COMBUSTIBLE")
@NamedQueries({
    @NamedQuery(name = "RecargaCombustible.findAll", query = "SELECT r FROM RecargaCombustible r"),
    @NamedQuery(name = "RecargaCombustible.findById", query = "SELECT r FROM RecargaCombustible r WHERE r.id = :id"),
    @NamedQuery(name = "RecargaCombustible.findByOdometroActual", query = "SELECT r FROM RecargaCombustible r WHERE r.odometroActual = :odometroActual"),
    @NamedQuery(name = "RecargaCombustible.findByTipoCombustible", query = "SELECT r FROM RecargaCombustible r WHERE r.tipoCombustible = :tipoCombustible"),
    @NamedQuery(name = "RecargaCombustible.findByPrecioxlitro", query = "SELECT r FROM RecargaCombustible r WHERE r.precioxlitro = :precioxlitro"),
    @NamedQuery(name = "RecargaCombustible.findByLitros", query = "SELECT r FROM RecargaCombustible r WHERE r.litros = :litros"),
    @NamedQuery(name = "RecargaCombustible.findByMonto", query = "SELECT r FROM RecargaCombustible r WHERE r.monto = :monto")})
public class RecargaCombustible implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "odometro_actual")
    private Float odometroActual;
    @Column(name = "tipo_combustible")
    private String tipoCombustible;
    @Basic(optional = false)
    @Column(name = "precioxlitro")
    private float precioxlitro;
    @Basic(optional = false)
    @Column(name = "litros")
    private float litros;
    @Basic(optional = false)
    @Column(name = "monto")
    private Float monto;
    @Basic(optional = false)
    @Column(name = "gasolinera")
    private String gasolinera;
    @Basic(optional = false)
    @Column(name = "tipo_pago")
    private String tipoPago;
    @ManyToOne()
    @JoinColumn(name = "detalle_combustible_id")
    private DetalleCombustible detalleCompustible;
//    @ManyToOne()
//    @JoinColumn(name = "asignacion_unidad_id")
//    private AsignacionUnidad asignacionUnidad;
    
    public RecargaCombustible() {
    }

    public RecargaCombustible(Integer id) {
        this.id = id;
    }

    public RecargaCombustible(Integer id, Float odometroActual, String tipoCombustible, float precioxlitro, float litros, Float monto, String gasolinera, String tipoPago, DetalleCombustible detalleCompustible) {
        this.id = id;
        this.odometroActual = odometroActual;
        this.tipoCombustible = tipoCombustible;
        this.precioxlitro = precioxlitro;
        this.litros = litros;
        this.monto = monto;
        this.gasolinera = gasolinera;
        this.tipoPago = tipoPago;
        this.detalleCompustible = detalleCompustible;
    }

//    public RecargaCombustible(Integer id, Float odometroActual, String tipoCombustible, float precioxlitro, float litros, Float monto, String gasolinera, String tipoPago, DetalleCombustible detalleCompustible, AsignacionUnidad asignacionUnidad) {
//        this.id = id;
//        this.odometroActual = odometroActual;
//        this.tipoCombustible = tipoCombustible;
//        this.precioxlitro = precioxlitro;
//        this.litros = litros;
//        this.monto = monto;
//        this.gasolinera = gasolinera;
//        this.tipoPago = tipoPago;
//        this.detalleCompustible = detalleCompustible;
//        this.asignacionUnidad = asignacionUnidad;
//        
//    }
//
//   
//    public AsignacionUnidad getAsignacionUnidad() {
//        return asignacionUnidad;
//    }
//
//    public void setAsignacionUnidad(AsignacionUnidad asignacionUnidad) {
//        this.asignacionUnidad = asignacionUnidad;
//    }

    public DetalleCombustible getDetalleCompustible() {
        return detalleCompustible;
    }

    public void setDetalleCompustible(DetalleCombustible detalleCompustible) {
        this.detalleCompustible = detalleCompustible;
    }
    public String getGasolinera(){
        return gasolinera;
    }
    public void setGasolinera(String gasolinera){
         this.gasolinera=gasolinera;
    }
     public String getMetodoPago(){
        return tipoPago;
    }
    public void setMetodoPago(String metodoPago){
         this.tipoPago=metodoPago;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getOdometroActual() {
        return odometroActual;
    }

    public void setOdometroActual(Float odometroActual) {
        this.odometroActual = odometroActual;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public float getPrecioxlitro() {
        return precioxlitro;
    }

    public void setPrecioxlitro(float precioxlitro) {
        this.precioxlitro = precioxlitro;
    }

    public float getLitros() {
        return litros;
    }

    public void setLitros(float litros) {
        this.litros = litros;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
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
        if (!(object instanceof RecargaCombustible)) {
            return false;
        }
        RecargaCombustible other = (RecargaCombustible) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.RecargaCombustible[ id=" + id + " ]";
    }

}
