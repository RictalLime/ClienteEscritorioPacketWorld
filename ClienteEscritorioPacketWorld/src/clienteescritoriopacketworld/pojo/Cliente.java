/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Tron7
 */
public class Cliente {
    private Integer idCliente;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String correo;
    private String calle;
    private String numeroDeCasa;
    private String colonia;
    //private String codigoPostal;
    @SerializedName("codigoPostal")
    private String cp;
    private Integer idCiudad;
    private String nombreCiudad;
    private Integer idEstado;
    private String nombreEstado;

    public Cliente() {
    }

    public Cliente(Integer idCliente, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String correo, String calle, String numeroDeCasa, String colonia, String cp, Integer idCiudad, String nombreCiudad, Integer idEstado, String nombreEstado) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
        this.calle = calle;
        this.numeroDeCasa = numeroDeCasa;
        this.colonia = colonia;
        this.cp = cp;
        this.idCiudad = idCiudad;
        this.nombreCiudad = nombreCiudad;
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumeroDeCasa() {
        return numeroDeCasa;
    }

    public String getColonia() {
        return colonia;
    }

    public String getCp() {
        return cp;
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumeroDeCasa(String numeroDeCasa) {
        this.numeroDeCasa = numeroDeCasa;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
