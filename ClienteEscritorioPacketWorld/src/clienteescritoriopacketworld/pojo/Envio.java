/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

/**
 *
 * @author Tron7
 */
public class Envio {
    private Integer idEnvio;
    private Integer idCliente;
    private String cliente;
    private String destino;
    private String origen;
    private String origenCalle;
    private String origenNumero;
    private String origenColonia;
    private String origenCodigoPostal;
    private Integer idOrigenCiudad;
    private String origenCiudad;
    private Integer idOrigenEstado;
    private String origenEstado;
    private String noGuia;
    private float costoDeEnvio;
    private Integer idEstadoDeEnvio;
    private String estadoDeEnvio;
    private Integer idColaborador;
    private String colaborador;
    private String destinoCalle;
    private String destinoColonia;
    private Integer idDestinoCiudad;
    private String destinoCiudad;
    private Integer idDestinoEstado;
    private String destinoEstado;
    private String destinoNumero;
    private String destinoCodigoPostal;
    private Integer idSucursal;
    
    public Envio() {
    }

    public Envio(Integer idEnvio, Integer idCliente, String cliente, String destino, String origen, String origenCalle, String origenNumero, String origenColonia, String origenCodigoPostal, Integer idOrigenCiudad, String origenCiudad, Integer idOrigenEstado, String origenEstado, String noGuia, float costoDeEnvio, Integer idEstadoDeEnvio, String estadoDeEnvio, Integer idColaborador, String colaborador, String destinoCalle, String destinoColonia, Integer idDestinoCiudad, String destinoCiudad, Integer idDestinoEstado, String destinoEstado, String destinoNumero, String destinoCodigoPostal, Integer idSucursal) {
        this.idEnvio = idEnvio;
        this.idCliente = idCliente;
        this.cliente = cliente;
        this.destino = destino;
        this.origen = origen;
        this.origenCalle = origenCalle;
        this.origenNumero = origenNumero;
        this.origenColonia = origenColonia;
        this.origenCodigoPostal = origenCodigoPostal;
        this.idOrigenCiudad = idOrigenCiudad;
        this.origenCiudad = origenCiudad;
        this.idOrigenEstado = idOrigenEstado;
        this.origenEstado = origenEstado;
        this.noGuia = noGuia;
        this.costoDeEnvio = costoDeEnvio;
        this.idEstadoDeEnvio = idEstadoDeEnvio;
        this.estadoDeEnvio = estadoDeEnvio;
        this.idColaborador = idColaborador;
        this.colaborador = colaborador;
        this.destinoCalle = destinoCalle;
        this.destinoColonia = destinoColonia;
        this.idDestinoCiudad = idDestinoCiudad;
        this.destinoCiudad = destinoCiudad;
        this.idDestinoEstado = idDestinoEstado;
        this.destinoEstado = destinoEstado;
        this.destinoNumero = destinoNumero;
        this.destinoCodigoPostal = destinoCodigoPostal;
        this.idSucursal = idSucursal;
    }
    
    public Integer getIdEnvio() {
        return idEnvio;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDestino() {
        return destino;
    }

    public String getOrigen() {
        return origen;
    }

    public String getOrigenCalle() {
        return origenCalle;
    }

    public String getOrigenNumero() {
        return origenNumero;
    }

    public String getOrigenColonia() {
        return origenColonia;
    }

    public String getOrigenCodigoPostal() {
        return origenCodigoPostal;
    }

    public String getOrigenCiudad() {
        return origenCiudad;
    }

    public String getOrigenEstado() {
        return origenEstado;
    }

    public String getNoGuia() {
        return noGuia;
    }

    public float getCostoDeEnvio() {
        return costoDeEnvio;
    }

    public Integer getIdEstadoDeEnvio() {
        return idEstadoDeEnvio;
    }

    public String getEstadoDeEnvio() {
        return estadoDeEnvio;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public String getColaborador() {
        return colaborador;
    }

    public Integer getIdOrigenCiudad() {
        return idOrigenCiudad;
    }

    public Integer getIdOrigenEstado() {
        return idOrigenEstado;
    }

    public String getDestinoCalle() {
        return destinoCalle;
    }

    public String getDestinoColonia() {
        return destinoColonia;
    }

    public Integer getIdDestinoCiudad() {
        return idDestinoCiudad;
    }

    public String getDestinoCiudad() {
        return destinoCiudad;
    }

    public Integer getIdDestinoEstado() {
        return idDestinoEstado;
    }

    public String getDestinoEstado() {
        return destinoEstado;
    }

    public String getDestinoNumero() {
        return destinoNumero;
    }

    public String getDestinoCodigoPostal() {
        return destinoCodigoPostal;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }
    
    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setOrigenCalle(String origenCalle) {
        this.origenCalle = origenCalle;
    }

    public void setOrigenNumero(String origenNumero) {
        this.origenNumero = origenNumero;
    }

    public void setOrigenColonia(String origenColonia) {
        this.origenColonia = origenColonia;
    }

    public void setOrigenCodigoPostal(String origenCodigoPostal) {
        this.origenCodigoPostal = origenCodigoPostal;
    }

    public void setOrigenCiudad(String origenCiudad) {
        this.origenCiudad = origenCiudad;
    }

    public void setOrigenEstado(String origenEstado) {
        this.origenEstado = origenEstado;
    }

    public void setNoGuia(String noGuia) {
        this.noGuia = noGuia;
    }

    public void setCostoDeEnvio(float costoDeEnvio) {
        this.costoDeEnvio = costoDeEnvio;
    }

    public void setIdEstadoDeEnvio(Integer idEstadoDeEnvio) {
        this.idEstadoDeEnvio = idEstadoDeEnvio;
    }

    public void setEstadoDeEnvio(String estadoDeEnvio) {
        this.estadoDeEnvio = estadoDeEnvio;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public void setIdOrigenCiudad(Integer idOrigenCiudad) {
        this.idOrigenCiudad = idOrigenCiudad;
    }

    public void setIdOrigenEstado(Integer idOrigenEstado) {
        this.idOrigenEstado = idOrigenEstado;
    }

    public void setDestinoCalle(String destinoCalle) {
        this.destinoCalle = destinoCalle;
    }

    public void setDestinoColonia(String destinoColonia) {
        this.destinoColonia = destinoColonia;
    }

    public void setIdDestinoCiudad(Integer idDestinoCiudad) {
        this.idDestinoCiudad = idDestinoCiudad;
    }

    public void setDestinoCiudad(String destinoCiudad) {
        this.destinoCiudad = destinoCiudad;
    }

    public void setIdDestinoEstado(Integer idDestinoEstado) {
        this.idDestinoEstado = idDestinoEstado;
    }

    public void setDestinoEstado(String destinoEstado) {
        this.destinoEstado = destinoEstado;
    }

    public void setDestinoNumero(String destinoNumero) {
        this.destinoNumero = destinoNumero;
    }

    public void setDestinoCodigoPostal(String destinoCodigoPostal) {
        this.destinoCodigoPostal = destinoCodigoPostal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }
    
    @Override
    public String toString() {
        return "noGuia: " + noGuia;
    }
}
