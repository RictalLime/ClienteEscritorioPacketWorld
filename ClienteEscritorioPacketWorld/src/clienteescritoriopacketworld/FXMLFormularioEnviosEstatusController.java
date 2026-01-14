/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.EnvioImp;
import clienteescritoriopacketworld.dominio.HistorialDeEnvioImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Colaborador;
import clienteescritoriopacketworld.pojo.Envio;
import clienteescritoriopacketworld.pojo.EstadoDeEnvio;
import clienteescritoriopacketworld.pojo.HistorialDeEnvio;
import clienteescritoriopacketworld.utilidad.Utilidades;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tron7
 */
public class FXMLFormularioEnviosEstatusController implements Initializable {

    @FXML 
    private Label lEstadoEnvio;
    @FXML 
    private ComboBox<EstadoDeEnvio> cbEstadoDeEnvio;
    @FXML 
    private Label lMotivo;
    @FXML 
    private TextField tfMotivo;
    @FXML
    private Button btnGuardar;

    private ObservableList<EstadoDeEnvio> tiposDeEstadosDeEnvio;
    private Envio envioEditado;
    private Colaborador colaboradorLogueado;
    private NotificadoOperacion observador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lMotivo.setVisible(false);
        tfMotivo.setVisible(false);
    }

    public void initializeValores(NotificadoOperacion observador, Envio envioEditado, Colaborador colaboradorLogueado) {
        this.envioEditado = envioEditado;
        this.colaboradorLogueado = colaboradorLogueado;
        this.observador = observador;

        cargarEstadosDeEnvio();

        cbEstadoDeEnvio.getSelectionModel().select(buscarEstadoEnvio(envioEditado.getIdEstadoDeEnvio()));
    }

    private void cargarEstadosDeEnvio() {
        tiposDeEstadosDeEnvio = FXCollections.observableArrayList();
        tiposDeEstadosDeEnvio.addAll(EnvioImp.obtenerEstadosDeEnvios());
        cbEstadoDeEnvio.setItems(tiposDeEstadosDeEnvio);
    }

    private int buscarEstadoEnvio(Integer idEstadoDeEnvio) {
        for (int i = 0; i < tiposDeEstadosDeEnvio.size(); i++) {
            if (tiposDeEstadosDeEnvio.get(i).getIdEstadoDeEnvio() == idEstadoDeEnvio) return i;
        }
        return -1;
    }

    @FXML
    private void detectarEstado(ActionEvent event) {
        EstadoDeEnvio estadoSel = cbEstadoDeEnvio.getValue();
        if (estadoSel != null && (estadoSel.getIdEstadoDeEnvio() == 4 || estadoSel.getIdEstadoDeEnvio() == 5)) {
            lMotivo.setVisible(true);
            tfMotivo.setVisible(true);
        } else {
            lMotivo.setVisible(false);
            tfMotivo.setVisible(false);
        }
    }

    @FXML
    private void onClickGuardar(ActionEvent event) {
        if (cbEstadoDeEnvio.getValue() == null) {
            Utilidades.mostrarAlertaSimple("Validación", "Debe seleccionar un estado.", Alert.AlertType.WARNING);
            return;
        }

        if ((cbEstadoDeEnvio.getValue().getIdEstadoDeEnvio() == 4 || cbEstadoDeEnvio.getValue().getIdEstadoDeEnvio() == 5)
            && (tfMotivo.getText() == null || tfMotivo.getText().trim().isEmpty())) {
            Utilidades.mostrarAlertaSimple("Validación", "Debe ingresar un motivo para estados 'Cancelado' o 'Detenido'.", Alert.AlertType.WARNING);
            return;
        }

        envioEditado.setIdEstadoDeEnvio(cbEstadoDeEnvio.getValue().getIdEstadoDeEnvio());
        HistorialDeEnvio historial = new HistorialDeEnvio();
        historial.setIdEstadoDeEnvio(envioEditado.getIdEstadoDeEnvio());
        historial.setIdColaborador(colaboradorLogueado.getIdColaborador());
        historial.setNoGuia(envioEditado.getNoGuia());
        String motivo = (tfMotivo != null && tfMotivo.getText() != null && !tfMotivo.getText().trim().isEmpty())
                ? tfMotivo.getText()
                : "S/M";
        historial.setMotivo(motivo);
        historial.setTiempoDeCambio(LocalDate.now().toString());

        Mensaje mensajeHistorial = HistorialDeEnvioImp.registrarHistorialEnvio(historial);

        if (!mensajeHistorial.isError()) {
            Mensaje msj = EnvioImp.editarEnvio(envioEditado);
            if (!msj.isError()) {
                Utilidades.mostrarAlertaSimple("Edición Exitosa", "Estado de envío actualizado correctamente.", Alert.AlertType.INFORMATION);
                observador.notificarOperacion("Edición", envioEditado.getNoGuia());
                cerrarVentana();
            } else {
                Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Error", mensajeHistorial.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void regresarPrincipal(MouseEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage base = (Stage) cbEstadoDeEnvio.getScene().getWindow();
        base.close();
    }
}
