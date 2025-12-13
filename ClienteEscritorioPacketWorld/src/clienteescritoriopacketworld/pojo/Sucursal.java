/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

/**
 *
 * @author Tron7
 */
public class Sucursal {
    private Integer idSucursal;
    private String codigoSucursal;
    private String nombre;
    private String estatus;
    private Integer idCalle;
    private String calle;
    private String numero;
    private Integer idColonia;
    private String colonia;
    private String codigoPostal;
    private Integer idCiudad;
    private String ciudad;
    private Integer idEstado;
    private String estado;

    public Sucursal() {
    }

    public Sucursal(Integer idSucursal, String codigoSucursal, String nombre, String estatus, Integer idCalle, String calle, String numero, Integer idColonia, String colonia, String codigoPostal, Integer idCiudad, String ciudad, Integer idEstado, String estado) {
        this.idSucursal = idSucursal;
        this.codigoSucursal = codigoSucursal;
        this.nombre = nombre;
        this.estatus = estatus;
        this.idCalle = idCalle;
        this.calle = calle;
        this.numero = numero;
        this.idColonia = idColonia;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
        this.idCiudad = idCiudad;
        this.ciudad = ciudad;
        this.idEstado = idEstado;
        this.estado = estado;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEstatus() {
        return estatus;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumero() {
        return numero;
    }

    public String getColonia() {
        return colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getIdCalle() {
        return idCalle;
    }

    public Integer getIdColonia() {
        return idColonia;
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIdCalle(Integer idCalle) {
        this.idCalle = idCalle;
    }

    public void setIdColonia(Integer idColonia) {
        this.idColonia = idColonia;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
    
}
