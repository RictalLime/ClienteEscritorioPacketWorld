/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.EnvioImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Colaborador;
import clienteescritoriopacketworld.pojo.Envio;
import clienteescritoriopacketworld.utilidad.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tron7
 */
public class FXMLModuloEnviosController implements Initializable, NotificadoOperacion {

    private ObservableList<Envio> envios;
    
    @FXML
    private ImageView imgRegresar;
    @FXML
    private TextField tfBuscar;
    @FXML
    private TableView<Envio> tvTablaEnvios;
    @FXML
    private TableColumn colNoGuia;
    @FXML
    private TableColumn colOrigen;
    @FXML
    private TableColumn colDestino;
    @FXML
    private TableColumn colConductor;
    @FXML
    private TableColumn colCliente;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colCostoEnvio;

    /**
     * Initializes the controller class.
     */
    
    private Colaborador colaboradorLoguiado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarLaInformacion();
    }
    
    public void inizializar(Colaborador colaboradorLoguiado) {
        this.colaboradorLoguiado = colaboradorLoguiado;
    }
    
    public void irPantallaPrincipal(){
        try {
            Stage escenarioBase = (Stage) imgRegresar.getScene().getWindow();
                    
            Parent principal = FXMLLoader.load(getClass().getResource("FXMLPrincipal.fxml"));
            Scene escenaPrincipal = new Scene(principal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Packet World Principal");
            escenarioBase.show();
        } catch (IOException ex) {
            // Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
            Utilidades.mostrarAlertaSimple("Error", "No podemos ir a la pantalla principal :(", Alert.AlertType.ERROR);
        }
    }
    
    private void configurarTabla() {
           colNoGuia.setCellValueFactory(new PropertyValueFactory("noGuia"));
           colOrigen.setCellValueFactory(new PropertyValueFactory("origen"));
           colDestino.setCellValueFactory(new PropertyValueFactory("destino"));
           colConductor.setCellValueFactory(new PropertyValueFactory("colaborador"));
           colCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
           colEstado.setCellValueFactory(new PropertyValueFactory("estadoDeEnvio"));
           colCostoEnvio.setCellValueFactory(new PropertyValueFactory("costoDeEnvio"));
    }
    
    private void cargarLaInformacion() {
        envios = FXCollections.observableArrayList();
        List<Envio> lista = EnvioImp.obtenerEnvios();
           
        if (lista != null) {
            envios.addAll(lista);
            tvTablaEnvios.setItems(envios);
        }else{
            Utilidades.mostrarAlertaSimple("ERROR", "Lo sentimos por el momento no se puede cargar la informacion"
                + "de los Envios, por favor intentélo mas tarde", Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }

    private void cerrarVentana(){
        ((Stage) tfBuscar.getScene().getWindow()).close();
    }
    
    private void irAFormulario(NotificadoOperacion observador, Envio envio){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioEnvios.fxml"));
            Parent root = loader.load();
            
            FXMLFormularioEnviosController controlador = loader.getController();
            controlador.initializeValores(observador, envio, colaboradorLoguiado);
            
            Stage ecena = new Stage();
            Scene ecenario = new Scene(root);
            ecena.setScene(ecenario);
            ecena.setTitle("Formulario Envios");
            ecena.initModality(Modality.APPLICATION_MODAL);
            ecena.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(FXMLModuloColaboradoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void regresarPrincipal(MouseEvent event) {
        irPantallaPrincipal();
    }


    @FXML
    private void irBuscar(MouseEvent event) {
         if(!tfBuscar.getText().isEmpty()){
            String noGuia = tfBuscar.getText();
            buscarEnvio(noGuia);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Campo de buscar Vacio", Alert.AlertType.ERROR);
            cargarLaInformacion();
        }
    }

    @FXML
    private void irRegistrarEnvio(MouseEvent event) {
        irAFormulario(this, null);
    }

    @FXML
    private void irEliminarEnvio(MouseEvent event) {
        Envio envio = tvTablaEnvios.getSelectionModel().getSelectedItem();
        if(envio!= null){
            Mensaje mensaje = EnvioImp.eliminarEnvio(envio.getIdEnvio());
            if(!mensaje.isError()){
                Utilidades.mostrarAlertaSimple("Correcto", "Envío Eliminado correctamente", Alert.AlertType.WARNING);
                cargarLaInformacion();
            }else{
                Utilidades.mostrarAlertaSimple("Error", "No se pudo eliminar el envío", Alert.AlertType.ERROR);
            }
            
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un envío", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void irEditarEnvio(MouseEvent event) {
        Envio envio = tvTablaEnvios.getSelectionModel().getSelectedItem();
        if(envio!= null){
            irAFormulario(this, envio);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un envío", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void irActualizarEstatus(MouseEvent event) {
        Envio envio = tvTablaEnvios.getSelectionModel().getSelectedItem();
        if(envio!= null){
            irAFormularioEstatus(this, envio);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un envío", Alert.AlertType.ERROR);
        }
    }
    
    private void irAFormularioEstatus(NotificadoOperacion observador, Envio envio){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioEnviosEstatus.fxml"));
            Parent root = loader.load();
            
            FXMLFormularioEnviosEstatusController controlador = loader.getController();
            controlador.initializeValores(observador, envio, colaboradorLoguiado);
            
            Stage ecena = new Stage();
            Scene ecenario = new Scene(root);
            ecena.setScene(ecenario);
            ecena.setTitle("Formulario Envios Motivo");
            ecena.initModality(Modality.APPLICATION_MODAL);
            ecena.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(FXMLModuloColaboradoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notificarOperacion(String tipo, String nombre) {
        cargarLaInformacion();
    }

    private void buscarEnvio(String noGuia) {
        envios.clear();
        tvTablaEnvios.setItems(envios);
        List<Envio> lista = EnvioImp.obtenerEnviosPorNoGuia(noGuia);
        if (lista!=null && !lista.isEmpty()) {
            envios.addAll(lista);
            tvTablaEnvios.setItems(envios);
        }else{
            Utilidades.mostrarAlertaSimple("Aviso", "No se encontro el Envio", Alert.AlertType.WARNING);
            cargarLaInformacion();
        }
        
    }
}