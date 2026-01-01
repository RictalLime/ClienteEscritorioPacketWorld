/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.EnvioImp;
import clienteescritoriopacketworld.dominio.PaqueteImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Envio;
import clienteescritoriopacketworld.pojo.Paquete;
import clienteescritoriopacketworld.utilidad.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tron7
 */
public class FXMLFormularioPaquetesController implements Initializable {

    @FXML
    private ImageView imgRegresar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField tfPeso;
    @FXML
    private TextField tfAlto;
    @FXML
    private TextField tfAncho;
    @FXML
    private TextField tfProfundidad;
    @FXML
    private ComboBox<Envio> cbEnvios;

    /**
     * Initializes the controller class.
     */
    ObservableList<Envio> listaEnvios;
    
    private NotificadoOperacion observador; 
    private Paquete paqueteEditado;
    private boolean modoEdicion = false;
    @FXML
    private TextField tfDescripcion;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEnvios();
    }
    
    public void initializeValores(NotificadoOperacion observador, Paquete paqueteEditado ){
        this.paqueteEditado = paqueteEditado;
        this.observador = observador;
        if(paqueteEditado!= null){
            modoEdicion = true;
            cbEnvios.setDisable(true);
            llenarcampos();
            btnGuardar.setText("Editar");
        }
    }
    
    @FXML
    private void regresarPrincipal(MouseEvent event) {
        cerrarVentana();    
    }
    
    @FXML
    private void onClickGuardar(ActionEvent event) {
        Paquete paquete = new Paquete();
        paquete.setDescripcion(tfDescripcion.getText());
        try {
            if (tfPeso.getText().trim().isEmpty()) {
                Utilidades.mostrarAlertaSimple("Error", "El peso del paquete es obligatorio.", Alert.AlertType.ERROR);
                return;
            }
            float peso = Float.parseFloat(tfPeso.getText());
            paquete.setPeso(peso);

            if (tfAlto.getText().trim().isEmpty()) {
                Utilidades.mostrarAlertaSimple("Error", "La altura del paquete es obligatoria.", Alert.AlertType.ERROR);
                return;
            }
            float alto = Float.parseFloat(tfAlto.getText());
            paquete.setAlto(alto);

            if (tfAncho.getText().trim().isEmpty()) {
                Utilidades.mostrarAlertaSimple("Error", "El ancho del paquete es obligatorio.", Alert.AlertType.ERROR);
                return;
            }
            float ancho = Float.parseFloat(tfAncho.getText());
            paquete.setAncho(ancho);

            if (tfProfundidad.getText().trim().isEmpty()) {
                Utilidades.mostrarAlertaSimple("Error", "La profundidad del paquete es obligatoria.", Alert.AlertType.ERROR);
                return;
            }
            float profundidad = Float.parseFloat(tfProfundidad.getText());
            paquete.setProfundidad(profundidad);

        } catch (NumberFormatException e) {
            Utilidades.mostrarAlertaSimple("Error", "Los campos Peso, Alto, Ancho y Profundidad deben contener valores numéricos válidos.", Alert.AlertType.ERROR);
            return;
        }
        paquete.setIdEnvio((cbEnvios.getSelectionModel().getSelectedItem()!= null)?
               cbEnvios.getSelectionModel().getSelectedItem().getIdEnvio(): -1);
        if(validarCampos(paquete)){
            if(!modoEdicion){
               guardarDatosPaquete(paquete);
           }else{
               paquete.setIdPaquete(paqueteEditado.getIdPaquete());
               editarDatosPaquete(paquete);
           }
        }
    }
    
    private void llenarcampos() {
        tfPeso.setText(""+paqueteEditado.getPeso());
        tfAlto.setText(""+paqueteEditado.getAlto());
        tfProfundidad.setText(""+paqueteEditado.getProfundidad());
        tfAncho.setText(""+paqueteEditado.getAncho());
        tfDescripcion.setText(paqueteEditado.getDescripcion());
        int poscicion = buscarIdEnvio(paqueteEditado.getIdEnvio());
        cbEnvios.getSelectionModel().select(poscicion);
    }
    
    private void cargarEnvios() {
        listaEnvios = FXCollections.observableArrayList();
        listaEnvios.addAll(EnvioImp.obtenerEnvios());  
        cbEnvios.setItems(listaEnvios);
    }
    
    private int buscarIdEnvio(Integer idEnvio) {
        for(int i=0; i<listaEnvios.size();i++){
            if(listaEnvios.get(i).getIdEnvio()== idEnvio){
                return i;
            }
        }
        return -1;
    }
    
    private boolean validarCampos(Paquete paquete) {
        if (paquete.getDescripcion() == null || paquete.getDescripcion().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Error", "La descripción del paquete es obligatoria.", Alert.AlertType.ERROR);
            return false;
        }
        if (paquete.getPeso() <= 0) {
            Utilidades.mostrarAlertaSimple("Error", "El peso del paquete debe ser un valor positivo.", Alert.AlertType.ERROR);
            return false;
        }
        if (paquete.getAlto() <= 0) {
            Utilidades.mostrarAlertaSimple("Error", "La altura del paquete debe ser un valor positivo.", Alert.AlertType.ERROR);
            return false;
        }
        if (paquete.getAncho() <= 0) {
            Utilidades.mostrarAlertaSimple("Error", "El ancho del paquete debe ser un valor positivo.", Alert.AlertType.ERROR);
            return false;
        }
        if (paquete.getProfundidad() <= 0) {
            Utilidades.mostrarAlertaSimple("Error", "La profundidad del paquete debe ser un valor positivo.", Alert.AlertType.ERROR);
            return false;
        }
        if (paquete.getIdEnvio() == null || paquete.getIdEnvio() <= 0) {
            Utilidades.mostrarAlertaSimple("Error", "Debe seleccionarse un envío válido para el paquete.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }
    
    private void guardarDatosPaquete(Paquete paquete) {
        Mensaje msj = PaqueteImp.registrarPaquete(paquete);
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Registro Exitoso", "Paquete Agregado", Alert.AlertType.INFORMATION);

            actualizarCostoEnvio(paquete.getIdEnvio());

            observador.notificarOperacion("Guardar", "" + paquete.getIdEnvio());
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error", "No se pudo agregar el paquete", Alert.AlertType.ERROR);
        }
    }
    
    private void editarDatosPaquete(Paquete paquete) {
        Mensaje msj = PaqueteImp.editarPaquete(paquete);
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Edición", "Paquete Editado", Alert.AlertType.INFORMATION);

            actualizarCostoEnvio(paquete.getIdEnvio());

            observador.notificarOperacion("Editar", "" + paquete.getIdEnvio());
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error", "No se puede editar el paquete", Alert.AlertType.ERROR);
        }
    }
    
    private void actualizarCostoEnvio(int idEnvio) {
        Mensaje msj = EnvioImp.recalcularCostoEnvio(idEnvio);
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Costo actualizado", msj.getMensaje(), Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlertaSimple("Error", "No se pudo recalcular el costo del envío.", Alert.AlertType.ERROR);
        }
    }
    
    private void cerrarVentana() {
        Stage base = (Stage) tfAlto.getScene().getWindow();
        base.close();
    }
}
