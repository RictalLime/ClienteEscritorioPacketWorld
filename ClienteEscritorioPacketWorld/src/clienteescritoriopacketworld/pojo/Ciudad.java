/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

/**
 *
 * @author Tron7
 */
public class Ciudad {
    private Integer idCiudad;
    private String nombre;
    private Integer idEstado; // Clave foránea

    public Ciudad() {
    }

    public Ciudad(Integer idCiudad, String nombre, Integer idEstado) {
        this.idCiudad = idCiudad;
        this.nombre = nombre;
        this.idEstado = idEstado;
    }

    // Getters
    public Integer getIdCiudad() {
        return idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    // Setters
    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
}
