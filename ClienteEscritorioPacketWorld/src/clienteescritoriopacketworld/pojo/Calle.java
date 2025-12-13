/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

/**
 *
 * @author Tron7
 */
public class Calle {
    private Integer idCalle;
    private String nombre;
    private Integer idColonia;

    public Calle() {
    }

    public Calle(Integer idCalle, String nombre, Integer idColonia) {
        this.idCalle = idCalle;
        this.nombre = nombre;
        this.idColonia = idColonia;
    }

    public Integer getIdCalle() {
        return idCalle;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getIdColonia() {
        return idColonia;
    }

    public void setIdCalle(Integer idCalle) {
        this.idCalle = idCalle;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdColonia(Integer idColonia) {
        this.idColonia = idColonia;
    }
}
