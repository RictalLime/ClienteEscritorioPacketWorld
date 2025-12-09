/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.ColaboradorImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Colaborador;
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
public class FXMLModuloColaboradoresController implements Initializable, NotificadoOperacion {
    
    private ObservableList<Colaborador> colaboradores;

    @FXML
    private ImageView imgRegresar;
    @FXML
    private TextField tfBuscar;
    @FXML
    private TableView<Colaborador> tablaColaboradores;
    @FXML
    private TableColumn colNoPersonal;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colRol;
    @FXML
    private TableColumn colCurp;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarLaInformacion();
    }


    private void configurarTabla() {
           colNoPersonal.setCellValueFactory(new PropertyValueFactory("noPersonal"));
           colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
           colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
           colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
           colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
           colRol.setCellValueFactory(new PropertyValueFactory("rol"));
           colCurp.setCellValueFactory(new PropertyValueFactory("curp"));
    }

    private void cargarLaInformacion() {
           colaboradores = FXCollections.observableArrayList();
           List<Colaborador> lista = ColaboradorImp.obtenerColaboradores();
           if (lista != null) {
               colaboradores.addAll(lista);
               tablaColaboradores.setItems(colaboradores);
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
    private void irBuscar(MouseEvent event) {
        if(!tfBuscar.getText().isEmpty()){
            String dato = tfBuscar.getText();
            buscarColaborador(dato);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Campo de buscar Vacio", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void irRegistrarColaboradors(MouseEvent event) {
        irAFormulario(this, null);
    }

    @FXML
    private void irEliminarColaborador(MouseEvent event) {
        Colaborador colaborador = tablaColaboradores.getSelectionModel().getSelectedItem();
        if(colaborador!= null){
            Mensaje mensaje = ColaboradorImp.eliminarColaborador(colaborador.getIdColaborador());
            if(!mensaje.isError()){
                Utilidades.mostrarAlertaSimple("Correcto", "Colaborador Eliminado correctamente", Alert.AlertType.INFORMATION);
                cargarLaInformacion();
            }else{
                Utilidades.mostrarAlertaSimple("Error", "No puedes Eliminar un colaborador que este asignado a una unidad.", Alert.AlertType.ERROR);
            }
            
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un colaborador", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void irEditarColaborador(MouseEvent event) {
        Colaborador colaborador = tablaColaboradores.getSelectionModel().getSelectedItem();
        if(colaborador!= null){
            irAFormulario(this, colaborador);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un colaborador", Alert.AlertType.ERROR);
        }
    }

    private void buscarColaborador(String dato) {
        colaboradores.clear();
        tablaColaboradores.setItems(colaboradores);
        List<Colaborador> lista = ColaboradorImp.obtenerColaboradoresNoPersonal(dato);
        System.out.println(lista);
        if (lista!=null && !lista.isEmpty()) {
            colaboradores.addAll(lista);
            tablaColaboradores.setItems(colaboradores);
        }else{
           lista = ColaboradorImp.obtenerColaboradoresNombre(dato);
           if (lista!=null && !lista.isEmpty()) {
                colaboradores.addAll(lista);
                tablaColaboradores.setItems(colaboradores);
           }else{
               Integer rol = obtenerRol(dato);
               if(rol>0){
                   lista = ColaboradorImp.obtenerColaboradoresRol(rol);
                   if(lista!=null && !lista.isEmpty()){
                        colaboradores.addAll(lista);
                        tablaColaboradores.setItems(colaboradores);
                   }
               }else{
                        Utilidades.mostrarAlertaSimple("Aviso", "No se encontro el colaborador", Alert.AlertType.WARNING);
                       cargarLaInformacion();
               }
           }
        }
    }
    
    private void irAFormulario(NotificadoOperacion observador, Colaborador colaborador){
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioColaboradores.fxml"));
            Parent root = loader.load();
            
            FXMLFormularioColaboradoresController controlador = loader.getController();
            controlador.initializeValores(observador, colaborador);
            
            Stage ecena = new Stage();
            Scene ecenario = new Scene(root);
            ecena.setScene(ecenario);
            ecena.setTitle("Formulario Colaboador");
            ecena.initModality(Modality.APPLICATION_MODAL);
            ecena.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(FXMLModuloColaboradoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private Integer obtenerRol(String dato) {
       Integer rol = -1;
       switch (dato) {
        case "Administrador":
            rol = 1;
            break;
        case "Ejecutivo de tienda":
            rol = 2;
            break;
        case "Conductor":
            rol = 3;
            break;
        }
       return rol;
    }

    public void notificarOperacion(String tipo, String nombre) {
        //System.out.println("tipo:" + tipo +"Nombre:" + nombre);
        cargarLaInformacion();
    }
}
