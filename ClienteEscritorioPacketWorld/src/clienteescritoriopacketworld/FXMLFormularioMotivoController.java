/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.HistorialDeBajaImp;
import clienteescritoriopacketworld.dominio.UnidadImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.HistorialDeBaja;
import clienteescritoriopacketworld.pojo.Unidad;
import clienteescritoriopacketworld.utilidad.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tron7
 */
public class FXMLFormularioMotivoController implements Initializable {

    @FXML
    private TextField tfMotivo;
    @FXML
    private Button btnGuardar;

    private Unidad unidadSeleccionada;
    private NotificadoOperacion observador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initializeValores(NotificadoOperacion observador, Unidad unidad) {
        this.observador = observador;
        this.unidadSeleccionada = unidad;
    }

    @FXML
    private void onClickGuardar() {
        String motivo = tfMotivo.getText();
        if (motivo == null || motivo.trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "Debe ingresar un motivo de baja.", Alert.AlertType.WARNING);
            return;
        }

        // Crear historial de baja
        HistorialDeBaja historial = new HistorialDeBaja();
        historial.setIdUnidad(unidadSeleccionada.getIdUnidad());
        historial.setMotivo(motivo);

        Mensaje msjHistorial = HistorialDeBajaImp.agregarHistorialDeBaja(historial);

        if (!msjHistorial.isError()) {
            // Eliminar la unidad
            Mensaje msjUnidad = UnidadImp.eliminarUnidad(unidadSeleccionada.getIdUnidad());
            if (!msjUnidad.isError()) {
                Utilidades.mostrarAlertaSimple("Correcto", "Unidad dada de baja correctamente.", Alert.AlertType.INFORMATION);
                observador.notificarOperacion("Eliminar", unidadSeleccionada.getMarca());
                cerrarVentana();
            } else {
                Utilidades.mostrarAlertaSimple("Error", msjUnidad.getMensaje(), Alert.AlertType.ERROR);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Error", msjHistorial.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void regresarPrincipal(MouseEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage base = (Stage) tfMotivo.getScene().getWindow();
        base.close();
    }
}