/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

/**
 *
 * @author Tron7
 */
public class TipoUnidad {
    private Integer idTipoUnidad;
    private String nombre;

    public TipoUnidad() {
    }

    public TipoUnidad(Integer idTipoUnidad, String nombre) {
        this.idTipoUnidad = idTipoUnidad;
        this.nombre = nombre;
    }

    public Integer getIdTipoUnidad() {
        return idTipoUnidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setIdTipoUnidad(Integer idTipoUnidad) {
        this.idTipoUnidad = idTipoUnidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return  nombre;
    }
}
