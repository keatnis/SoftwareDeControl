package com.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author keatnis
 */
@Entity
@Table(name = "OPERADOR")
@NamedQueries({
    @NamedQuery(name = "Operador.findAll", query = "SELECT o FROM Operador o"),
    @NamedQuery(name = "Operador.findById", query = "SELECT o FROM Operador o WHERE o.id = :id"),
    @NamedQuery(name = "Operador.findByNombre", query = "SELECT o FROM Operador o WHERE o.nombre = :nombre"),
    @NamedQuery(name = "Operador.findByApePaterno", query = "SELECT o FROM Operador o WHERE o.apePaterno = :apePaterno"),
    @NamedQuery(name = "Operador.findByApeMaterno", query = "SELECT o FROM Operador o WHERE o.apeMaterno = :apeMaterno"),
    @NamedQuery(name = "Operador.findByCalle", query = "SELECT o FROM Operador o WHERE o.calle = :calle"),
    @NamedQuery(name = "Operador.findByNum", query = "SELECT o FROM Operador o WHERE o.num = :num"),
    @NamedQuery(name = "Operador.findByColonia", query = "SELECT o FROM Operador o WHERE o.colonia = :colonia"),
    @NamedQuery(name = "Operador.findByCiudad", query = "SELECT o FROM Operador o WHERE o.ciudad = :ciudad"),
    @NamedQuery(name = "Operador.findByTelefono", query = "SELECT o FROM Operador o WHERE o.telefono = :telefono"),})
@NamedNativeQuery(name = "Operador.exitsById", query = "select count(operador_id) from OPERADOR  where operador_id= :operador_id;")
@NamedNativeQuery(name = "Operador.findAllConcat", query = "select o.operador_id, concat(o.nombre,' ',o.ape_paterno,' ',o.ape_materno)AS nombre,o.puesto,o.telefono,\n"
        + "o.telefono2, concat(o.calle,' ',o.colonia,' ',o.num,' ',o.ciudad,' ',' ',o.estado)as direccion, o.typeblood,o.`file`\n"
        + ", concat(c.nombre,' ',c.ape_paterno,' ',c.ape_materno,' ',c.parentesco,' ',c.telefono) as contacto \n"
        + "FROM OPERADOR as o\n"
        + "inner join CONTACTO_EMERGENCIA as c ON o.operador_id = c.id;")
public class Operador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "operador_id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "ape_paterno")
    private String apePaterno;
    @Basic(optional = false)
    @Column(name = "ape_materno")
    private String apeMaterno;
    @Basic(optional = false)
    @Column(name = "calle")
    private String calle;
    @Column(name = "num")
    private String num;
    @Basic(optional = false)
    @Column(name = "colonia")
    private String colonia;
    @Basic(optional = false)
    @Column(name = "ciudad")
    private String ciudad;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "telefono2")
    private String telefono2;
    @Basic(optional = false)
    @Column(name = "alergias")
    private String alergias;
    @Basic(optional = false)
    @Column(name = "typeblood")
    private String typeblood;
    @Column(name = "status")
    private boolean status;
    @Column(name = "file")
    private byte[] file;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "OPERADOR_EMERGENCIA",
            joinColumns = @JoinColumn(name = "operador_id", referencedColumnName = "operador_id"))
    private ContactoEmergencia contactoEmergencia;
     @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "JOB_OPERADOR",
            joinColumns = @JoinColumn(name = "operador_id", referencedColumnName = "operador_id"))
    private Job job;

    public Operador() {
    }

    public Operador(Integer id, String nombre, String apePaterno, String apeMaterno, String calle, String num, String colonia, String ciudad, String estado, String telefono, String telefono2, String alergias, String typeblood, boolean status, byte[] file, Job job, ContactoEmergencia contactoEmergencia) {
        this.id = id;
        this.nombre = nombre;
        this.apePaterno = apePaterno;
        this.apeMaterno = apeMaterno;
        this.calle = calle;
        this.num = num;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.estado = estado;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.alergias = alergias;
        this.typeblood = typeblood;
        this.status = status;
        this.file = file;
        this.job = job;
        this.contactoEmergencia = contactoEmergencia;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getTypeblood() {
        return typeblood;
    }

    public void setTypeblood(String typeblood) {
        this.typeblood = typeblood;
    }

    public ContactoEmergencia getContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(ContactoEmergencia contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Operador(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApePaterno() {
        return apePaterno;
    }

    public void setApePaterno(String apePaterno) {
        this.apePaterno = apePaterno;
    }

    public String getApeMaterno() {
        return apeMaterno;
    }

    public void setApeMaterno(String apeMaterno) {
        this.apeMaterno = apeMaterno;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

}
