/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

/**
 *
 * @author Tron7
 */
public class Estado {
    private Integer idEstado;
    private String nombre;

    public Estado() {
    }

    public Estado(Integer idEstado, String nombre) {
        this.idEstado = idEstado;
        this.nombre = nombre;
    }

    // Getters
    public Integer getIdEstado() {
        return idEstado;
    }

    public String getNombre() {
        return nombre;
    }

    // Setters
    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
