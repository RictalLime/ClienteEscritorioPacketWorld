/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.SucursalImp;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Sucursal;
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
public class FXMLModuloSucursalController implements Initializable, NotificadoOperacion {
    
    private ObservableList<Sucursal> sucursales;

    @FXML
    private ImageView imgRegresar;
    @FXML
    private TextField tfBuscar;
    @FXML
    private TableView<Sucursal> tablaSucursal;
    @FXML
    private TableColumn colCodigoSucursal;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private TableColumn colCalle;
    @FXML
    private TableColumn colNumero;
    @FXML
    private TableColumn colColonia;
    @FXML
    private TableColumn colCodigoPostal;
    @FXML
    private TableColumn colCiudad;
    @FXML
    private TableColumn colEstado;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarLaInformacion();
    }

    private void configurarTabla() {
           colCodigoSucursal.setCellValueFactory(new PropertyValueFactory("codigoSucursal"));
           colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
           colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
           colCalle.setCellValueFactory(new PropertyValueFactory("calle"));
           colNumero.setCellValueFactory(new PropertyValueFactory("numero"));
           colColonia.setCellValueFactory(new PropertyValueFactory("colonia"));
           colCodigoPostal.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
           colCiudad.setCellValueFactory(new PropertyValueFactory("ciudad"));
           colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
    }
    
    private void cargarLaInformacion() {
           sucursales = FXCollections.observableArrayList();
           List<Sucursal> lista = SucursalImp.obtenerSucursales();
           if (lista != null) {
               sucursales.addAll(lista);
               tablaSucursal.setItems(sucursales);
           }else{
               Utilidades.mostrarAlertaSimple("ERROR", "Lo sentimos por el momento no se puede cargar la informacion"
                       + "de los Colaboradores, por favor intentélo más tarde", Alert.AlertType.ERROR);
               cerrarVentana();
           }
    }
    
    private void cerrarVentana(){
            ((Stage) tfBuscar.getScene().getWindow()).close();
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
    
    @FXML
    private void regresarPrincipal(MouseEvent event) {
        irPantallaPrincipal();
    }
    
    @FXML
    private void irRegistrarSucursal(MouseEvent event) {
        irAFormulario(this, null);
    }
    
    @FXML
    private void irEditarColaborador(MouseEvent event) {
        Sucursal sucursal = tablaSucursal.getSelectionModel().getSelectedItem();
        if(sucursal!= null){
            irAFormulario(this, sucursal);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un colaborador", Alert.AlertType.ERROR);
        }
    }
    
    private void irAFormulario(NotificadoOperacion observador, Sucursal sucursal){
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioSucursales.fxml"));
            Parent root = loader.load();
            
            FXMLFormularioSucursalesController controlador = loader.getController();
            controlador.initializeValores(observador, sucursal);
            
            Stage ecena = new Stage();
            Scene ecenario = new Scene(root);
            ecena.setScene(ecenario);
            ecena.setTitle("Formulario Sucursal");
            ecena.initModality(Modality.APPLICATION_MODAL);
            ecena.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(FXMLModuloColaboradoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void notificarOperacion(String tipo, String nombre) {
        //System.out.println("tipo:" + tipo +"Nombre:" + nombre);
        cargarLaInformacion();
    }
}
