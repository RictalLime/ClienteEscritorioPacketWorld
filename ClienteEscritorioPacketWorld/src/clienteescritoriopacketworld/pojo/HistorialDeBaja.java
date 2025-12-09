/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

/**
 *
 * @author Tron7
 */
public class HistorialDeBaja {
    private Integer idHistorialDeBaja;
    private Integer idUnidad;
    private String motivo;

    public HistorialDeBaja() {
    }

    public HistorialDeBaja(Integer idHistorialDeBaja, Integer idUnidad, String motivo) {
        this.idHistorialDeBaja = idHistorialDeBaja;
        this.idUnidad = idUnidad;
        this.motivo = motivo;
    }

    public Integer getIdHistorialDeBaja() {
        return idHistorialDeBaja;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setIdHistorialDeBaja(Integer idHistorialDeBaja) {
        this.idHistorialDeBaja = idHistorialDeBaja;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
