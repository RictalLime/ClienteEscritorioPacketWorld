/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

/**
 *
 * @author Tron7
 */
public class Colonia {
    private Integer idColonia;
    private String nombre;
    private Integer idCiudad;

    public Colonia() {
    }

    public Colonia(Integer idColonia, String nombre, Integer idCiudad) {
        this.idColonia = idColonia;
        this.nombre = nombre;
        this.idCiudad = idCiudad;
    }

    public Integer getIdColonia() {
        return idColonia;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdColonia(Integer idColonia) {
        this.idColonia = idColonia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }
}
