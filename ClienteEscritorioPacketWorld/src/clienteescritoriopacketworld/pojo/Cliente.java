/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

/**
 *
 * @author Tron7
 */
public class Cliente {
    private Integer idCliente;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String calle;
    private String numeroDeCasa;
    private String colonia;
    private String codigoPostal;
    private Integer idCiudad;
    private String ciudad;
    private Integer idEstado;
    private String estado;
    private String telefono;
    private String correo;

    public Cliente() {
    }

    public Cliente(Integer idCliente, String nombre, String apellidoPaterno, String apellidoMaterno, String calle, String numeroDeCasa, String colonia, String codigoPostal, Integer idCiudad, String ciudad, Integer idEstado, String estado, String telefono, String correo) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.calle = calle;
        this.numeroDeCasa = numeroDeCasa;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
        this.idCiudad = idCiudad;
        this.ciudad = ciudad;
        this.idEstado = idEstado;
        this.estado = estado;
        this.telefono = telefono;
        this.correo = correo;
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

    public String getCalle() {
        return calle;
    }

    public String getNumeroDeCasa() {
        return numeroDeCasa;
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

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public Integer getIdEstado() {
        return idEstado;
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

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumeroDeCasa(String numeroDeCasa) {
        this.numeroDeCasa = numeroDeCasa;
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

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
    
    @Override
    public String toString() {
        return correo;
    }
}
