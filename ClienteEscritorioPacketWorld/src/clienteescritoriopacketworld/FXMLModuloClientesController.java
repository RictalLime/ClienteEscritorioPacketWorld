/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.ClienteImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Cliente;
import clienteescritoriopacketworld.utilidad.Utilidades;
import java.io.IOException;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class FXMLModuloClientesController implements Initializable, NotificadoOperacion {

    private ObservableList<Cliente> clientes;
    
    @FXML
    private ImageView imgRegresar;
    @FXML
    private TextField ftBuscar;
    @FXML
    private ImageView imgRegistrarCliente;
    @FXML
    private ImageView imgElminarCliente;
    @FXML
    private ImageView imgEditarCliente;
    @FXML
    private TableView<Cliente> tvTablaClientes;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colCorreo;
    
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
            escenarioBase.setTitle("Pakect World Principal");
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
    private void irRegistrarCliente(MouseEvent event) {
        irAFormulario(this, null);
    }

    @FXML
    private void irEliminarCliente(MouseEvent event) {
        Cliente cliente = tvTablaClientes.getSelectionModel().getSelectedItem();
        if(cliente!= null){
            Mensaje mensaje = ClienteImp.eliminarCliente(cliente.getIdCliente());
            if(!mensaje.isError()){
                Utilidades.mostrarAlertaSimple("Correcto", "Cliente Eliminado correctamente", Alert.AlertType.INFORMATION);
                cargarLaInformacion();
            }else{
                Utilidades.mostrarAlertaSimple("Error", "No se pudo eliminar el Cliente", Alert.AlertType.ERROR);
            }
            
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un cliente", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void irEditarCliente(MouseEvent event) {
        Cliente cliente = tvTablaClientes.getSelectionModel().getSelectedItem();
        if(cliente!= null){
            irAFormulario(this, cliente);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un cliente", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void irBuscar(MouseEvent event) {
        if(!ftBuscar.getText().isEmpty()){
            String dato = ftBuscar.getText();
            buscarCliente(dato);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Campo de buscar Vacio", Alert.AlertType.ERROR);
        }
    }
    
    private void configurarTabla() {
           colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
           colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
           colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
           colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
           colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
    }

    private void cargarLaInformacion() {
           clientes = FXCollections.observableArrayList();
           List<Cliente> lista = ClienteImp.obtenerClientes();
           if (lista != null) {
               clientes.addAll(lista);
               tvTablaClientes.setItems(clientes);
           }else{
               Utilidades.mostrarAlertaSimple("ERROR", "Lo sentimos por el momento no se puede cargar la informacion"
                       + "de los Clientes, por favor intentélo mas tarde", Alert.AlertType.ERROR);
               cerrarVentana();
           }
    }
    private void cerrarVentana(){
            ((Stage) ftBuscar.getScene().getWindow()).close();
    } 

    @Override
    public void notificarOperacion(String tipo, String nombre) {
        cargarLaInformacion();
    }
    
    private void buscarCliente(String dato) {
        try {
            clientes.clear();
            tvTablaClientes.setItems(clientes);
            List<Cliente> lista = ClienteImp.obtenerClientePorNombre(dato);
            if (!lista.isEmpty()) {
                clientes.addAll(lista);
                tvTablaClientes.setItems(clientes);
            }else{
               lista = ClienteImp.obtenerClientePorCorreo(dato);
               if (!lista.isEmpty()) {
                   clientes.addAll(lista);
                   tvTablaClientes.setItems(clientes);
               }else{
                   lista = ClienteImp.obtenerClientePorNumeroDeTelefono(dato);
                   if(!lista.isEmpty()){
                        clientes.addAll(lista);
                        tvTablaClientes.setItems(clientes);
                   }else{
                       Utilidades.mostrarAlertaSimple("Aviso", "No se encontro el Cliente(s)", Alert.AlertType.WARNING);
                       cargarLaInformacion();
                   }
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void irAFormulario(NotificadoOperacion observador, Cliente cliente){
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioClientes.fxml"));
            Parent root = loader.load();
            
            FXMLFormularioClientesController controlador = loader.getController();
            controlador.initializeValores(observador, cliente);
            
            Stage ecena = new Stage();
            Scene ecenario = new Scene(root);
            ecena.setScene(ecenario);
            ecena.setTitle("Formulario Cliente");
            ecena.initModality(Modality.APPLICATION_MODAL);
            ecena.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLModuloColaboradoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
