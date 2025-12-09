/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

/**
 *
 * @author Tron7
 */
public class Paquete {
    private Integer idPaquete;
    private Integer idEnvio;
    private String descripcion;
    private String dimensiones;
    private float peso;
    private float alto;
    private float ancho;
    private float profundidad;
    private String noGuia;

    public Paquete() {
    }

    public Paquete(Integer idPaquete, Integer idEnvio, String descripcion, String dimensiones, float peso, float alto, float ancho, float profundidad, String noGuia) {
        this.idPaquete = idPaquete;
        this.idEnvio = idEnvio;
        this.descripcion = descripcion;
        this.dimensiones = dimensiones;
        this.peso = peso;
        this.alto = alto;
        this.ancho = ancho;
        this.profundidad = profundidad;
        this.noGuia = noGuia;
    }

    public Integer getIdPaquete() {
        return idPaquete;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public float getPeso() {
        return peso;
    }

    public float getAlto() {
        return alto;
    }

    public float getAncho() {
        return ancho;
    }

    public float getProfundidad() {
        return profundidad;
    }

    public String getNoGuia() {
        return noGuia;
    }

    public void setIdPaquete(Integer idPaquete) {
        this.idPaquete = idPaquete;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setAlto(float alto) {
        this.alto = alto;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public void setProfundidad(float profundidad) {
        this.profundidad = profundidad;
    }

    public void setNoGuia(String noGuia) {
        this.noGuia = noGuia;
    }
}
