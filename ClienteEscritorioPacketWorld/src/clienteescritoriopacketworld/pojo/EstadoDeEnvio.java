/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

/**
 *
 * @author Tron7
 */
public class EstadoDeEnvio {
    private Integer idEstadoDeEnvio;
    private String nombre;

    public EstadoDeEnvio() {
    }

    public EstadoDeEnvio(Integer idEstadoDeEnvio, String nombre) {
        this.idEstadoDeEnvio = idEstadoDeEnvio;
        this.nombre = nombre;
    }

    public Integer getIdEstadoDeEnvio() {
        return idEstadoDeEnvio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setIdEstadoDeEnvio(Integer idEstadoDeEnvio) {
        this.idEstadoDeEnvio = idEstadoDeEnvio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
