/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

/**
 *
 * @author Tron7
 */
public class HistorialDeEnvio {
    private Integer idHistorialDeEnvio;
    private Integer idEstadoDeEnvio;
    private Integer idColaborador;
    private String colaborador;
    private String noGuia;
    private String motivo;
    private String tiempoDeCambio;
    

    public HistorialDeEnvio() {
    }

    public HistorialDeEnvio(Integer idHistorialDeEnvio, Integer idEstadoDeEnvio, Integer idColaborador, String colaborador, String noGuia, String motivo, String tiempoDeCambio) {
        this.idHistorialDeEnvio = idHistorialDeEnvio;
        this.idEstadoDeEnvio = idEstadoDeEnvio;
        this.idColaborador = idColaborador;
        this.colaborador = colaborador;
        this.noGuia = noGuia;
        this.motivo = motivo;
        this.tiempoDeCambio = tiempoDeCambio;
    }

    public Integer getIdHistorialDeEnvio() {
        return idHistorialDeEnvio;
    }

    public Integer getIdEstadoDeEnvio() {
        return idEstadoDeEnvio;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public String getColaborador() {
        return colaborador;
    }

    public String getNoGuia() {
        return noGuia;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getTiempoDeCambio() {
        return tiempoDeCambio;
    }

    public void setIdHistorialDeEnvio(Integer idHistorialDeEnvio) {
        this.idHistorialDeEnvio = idHistorialDeEnvio;
    }

    public void setIdEstadoDeEnvio(Integer idEstadoDeEnvio) {
        this.idEstadoDeEnvio = idEstadoDeEnvio;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public void setNoGuia(String noGuia) {
        this.noGuia = noGuia;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setTiempoDeCambio(String tiempoDeCambio) {
        this.tiempoDeCambio = tiempoDeCambio;
    }
}
