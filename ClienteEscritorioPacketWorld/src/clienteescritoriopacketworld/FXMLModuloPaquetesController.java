/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.PaqueteImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Paquete;
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
public class FXMLModuloPaquetesController implements Initializable, NotificadoOperacion {

    @FXML
    private ImageView imgRegresar;
    @FXML
    private TextField tfBuscar;
    @FXML
    private ImageView imgBuscar;
    @FXML
    private TableView<Paquete> tvTablaPaquetes;
    @FXML
    private TableColumn colNoGuia;
    @FXML
    private TableColumn colDimensiones;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colPeso;
    
    private ObservableList<Paquete> paquetes;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    private void irRegistrarPaquete(MouseEvent event) {
        irAFormulario(this, null);
    }

    @FXML
    private void irEliminarPaquete(MouseEvent event) {
        Paquete paquete = tvTablaPaquetes.getSelectionModel().getSelectedItem();
        if(paquete!= null){
            Mensaje mensaje = PaqueteImp.eliminarPaquete(paquete.getIdPaquete());
            if(!mensaje.isError()){
                Utilidades.mostrarAlertaSimple("Correcto", "Paquete eliminado correctamente", Alert.AlertType.INFORMATION);
                cargarLaInformacion();
            }else{
                Utilidades.mostrarAlertaSimple("Error", "No se pudo eliminar el paquete", Alert.AlertType.ERROR);
            }
            
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un Paquete", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void irEditarPaquete(MouseEvent event) {
        Paquete paquete = tvTablaPaquetes.getSelectionModel().getSelectedItem();
        if(paquete!= null){
            irAFormulario(this, paquete);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un Paqute", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificarOperacion(String tipo, String nombre) {
        cargarLaInformacion();
    }
    
    private void configurarTabla() {
        colNoGuia.setCellValueFactory(new PropertyValueFactory("noGuia"));
        colDimensiones.setCellValueFactory(new PropertyValueFactory("dimensiones"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colPeso.setCellValueFactory(new PropertyValueFactory("peso"));
    }

    private void cargarLaInformacion() {
           paquetes = FXCollections.observableArrayList();
           List<Paquete> lista = PaqueteImp.obtenerPaquetes();
           if (lista != null) {
               paquetes.addAll(lista);
               tvTablaPaquetes.setItems(paquetes);
           }else{
               Utilidades.mostrarAlertaSimple("ERROR", "Lo sentimos por el momento no se puede cargar la informacion"
                       + "de los Paquetes, por favor intentélo mas tarde", Alert.AlertType.ERROR);
               cerrarVentana();
           }
    }
    private void cerrarVentana(){
            ((Stage) tfBuscar.getScene().getWindow()).close();
    }
    
    private void irAFormulario(NotificadoOperacion observador, Paquete paquete){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioPaquetes.fxml"));
            Parent root = loader.load();
            
            FXMLFormularioPaquetesController controlador = loader.getController();
            controlador.initializeValores(observador, paquete);
            
            Stage ecena = new Stage();
            Scene ecenario = new Scene(root);
            ecena.setScene(ecenario);
            ecena.setTitle("Formulario Paquetes");
            ecena.initModality(Modality.APPLICATION_MODAL);
            ecena.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLModuloColaboradoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void irABuscar(MouseEvent event) {
        if(!tfBuscar.getText().isEmpty()){
            String noGuia = tfBuscar.getText();
            buscarPaquete(noGuia);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Campo de buscar Vacio", Alert.AlertType.ERROR);
        }
    }

    private void buscarPaquete(String noGuia) {
        try {
            paquetes.clear();
            tvTablaPaquetes.setItems(paquetes);
            List<Paquete> lista = PaqueteImp.obtenerPaquetesPorNoGuia(noGuia);
            if (!lista.isEmpty()) {
                paquetes.addAll(lista);
                tvTablaPaquetes.setItems(paquetes);
            }else{
                Utilidades.mostrarAlertaSimple("Aviso", "No se encontro la(s) Paquetes", Alert.AlertType.WARNING);
                cargarLaInformacion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
