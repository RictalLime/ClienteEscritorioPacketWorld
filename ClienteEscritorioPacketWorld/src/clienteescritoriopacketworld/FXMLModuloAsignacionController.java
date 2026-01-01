/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.ConductoresAsignadosImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.ConductoresAsignados;
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
public class FXMLModuloAsignacionController implements Initializable, NotificadoOperacion {

    @FXML
    private ImageView imgRegresar;
    @FXML
    private TableView<ConductoresAsignados> tvTablaAsignaciones;
    @FXML
    private TableColumn colNombreColaborador;
    @FXML
    private TableColumn colCarro;
    @FXML
    private TableColumn colVin;

    /**
     * Initializes the controller class.
     */
    
    private ObservableList<ConductoresAsignados> conductoresAsignados;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarLaInformacion();
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
            Utilidades.mostrarAlertaSimple("Error", "No podemos ir a la pantalla principal :(", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void regresarPrincipal(MouseEvent event) {
        irPantallaPrincipal();
    }
    
    @FXML
    private void agregarAsignacion(MouseEvent event) {
        irAFormulario(this, null);
    }
    
    @FXML
    private void editarAsignacion(MouseEvent event) {
        ConductoresAsignados conductoresAsignados = tvTablaAsignaciones.getSelectionModel().getSelectedItem();
        if(conductoresAsignados!= null){
            irAFormulario(this, conductoresAsignados);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona una Asignación", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void eliminarAsignacion(MouseEvent event) {
        ConductoresAsignados conductoresAsignados = tvTablaAsignaciones.getSelectionModel().getSelectedItem();
        if(conductoresAsignados!= null){
            Mensaje mensaje = ConductoresAsignadosImp.eliminar(conductoresAsignados.getIdConductoresAsignados());
            if(!mensaje.isError()){
                Utilidades.mostrarAlertaSimple("Correcto", "Asignación Eliminada correctamente", Alert.AlertType.WARNING);
                cargarLaInformacion();
            }else{
                Utilidades.mostrarAlertaSimple("Error", "No se pudo eliminar la Asignación", Alert.AlertType.ERROR);
            }
            
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona una Asignación", Alert.AlertType.ERROR);
        }
    }
    
    private void configurarTabla() {
        colNombreColaborador.setCellValueFactory(new PropertyValueFactory("conductor"));
        colCarro.setCellValueFactory(new PropertyValueFactory("unidad"));
        colVin.setCellValueFactory(new PropertyValueFactory("vin"));
    }

    private void cargarLaInformacion() {
        conductoresAsignados = FXCollections.observableArrayList();
        List<ConductoresAsignados> lista = ConductoresAsignadosImp.obtenerTodos();
        if (lista != null) {
            conductoresAsignados.addAll(lista);
            tvTablaAsignaciones.setItems(conductoresAsignados);
        }else{
            Utilidades.mostrarAlertaSimple("ERROR", "Lo sentimos por el momento no se puede cargar la informacion"
                    + "de las Asignaciones, por favor intentélo mas tarde", Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }
    private void cerrarVentana(){
        ((Stage) imgRegresar.getScene().getWindow()).close();
    }
    
    private void irAFormulario(NotificadoOperacion observador, ConductoresAsignados conductorAsignado){
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioAsignaciones.fxml"));
            Parent root = loader.load();
            
            FXMLFormularioAsignacionesController controlador = loader.getController();
            controlador.initializeValores(observador, conductorAsignado);
            
            Stage ecena = new Stage();
            Scene ecenario = new Scene(root);
            ecena.setScene(ecenario);
            ecena.setTitle("Formulario Asignaciones");
            ecena.initModality(Modality.APPLICATION_MODAL);
            ecena.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLModuloColaboradoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void notificarOperacion(String tipo, String nombre) {
        cargarLaInformacion();
    }
}
