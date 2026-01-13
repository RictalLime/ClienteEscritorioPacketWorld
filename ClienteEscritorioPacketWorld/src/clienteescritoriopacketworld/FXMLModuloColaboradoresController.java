/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.ColaboradorImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Colaborador;
import clienteescritoriopacketworld.pojo.Sesion;
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
    private TableView<Colaborador> tvTablaColaboradores;
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
               tvTablaColaboradores.setItems(colaboradores);
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
            cargarLaInformacion();
        }
    }
    
    @FXML
    private void irRegistrarColaboradors(MouseEvent event) {
        irAFormulario(this, null);
    }
    
    // --- MÉTODO MODIFICADO CON LÓGICA DE SEGURIDAD ---
    @FXML
    private void irEliminarColaborador(MouseEvent event) {
        Colaborador colaboradorSeleccionado = tvTablaColaboradores.getSelectionModel().getSelectedItem();
        
        if(colaboradorSeleccionado != null){
            
            // 1. Confirmación de seguridad
            boolean confirmar = Utilidades.mostrarAlertaConfirmacion("Eliminar Colaborador", 
                    "¿Estás seguro de eliminar a: " + colaboradorSeleccionado.getNombre() + "?");
            
            if(confirmar){
                Mensaje mensaje = ColaboradorImp.eliminarColaborador(colaboradorSeleccionado.getIdColaborador());
                
                if(!mensaje.isError()){
                    
                    // 2. VALIDACIÓN DE AUTO-ELIMINACIÓN
                    // Comparamos el ID del que acabamos de borrar con el ID del que tiene la sesión abierta
                    if(colaboradorSeleccionado.getIdColaborador().equals(Sesion.getColaboradorActual().getIdColaborador())){
                        Utilidades.mostrarAlertaSimple("Adiós", 
                            "Has eliminado tu propia cuenta. El sistema se cerrará.", 
                            Alert.AlertType.INFORMATION);
                        System.exit(0); // Cierre total de la aplicación
                    }

                    Utilidades.mostrarAlertaSimple("Correcto", "Colaborador Eliminado correctamente", Alert.AlertType.INFORMATION);
                    cargarLaInformacion();
                }else{
                    Utilidades.mostrarAlertaSimple("Error", mensaje.getMensaje(), Alert.AlertType.ERROR);
                }
            }
            
        }else{
            Utilidades.mostrarAlertaSimple("Atención", "Por favor, selecciona un colaborador de la tabla", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void irEditarColaborador(MouseEvent event) {
        Colaborador colaborador = tvTablaColaboradores.getSelectionModel().getSelectedItem();
        if(colaborador!= null){
            irAFormulario(this, colaborador);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un colaborador", Alert.AlertType.ERROR);
        }
    }
    
    private void buscarColaborador(String dato) {
        colaboradores.clear();
        tvTablaColaboradores.setItems(colaboradores);
        List<Colaborador> lista = ColaboradorImp.obtenerColaboradoresNoPersonal(dato);
        if (lista!=null && !lista.isEmpty()) {
            colaboradores.addAll(lista);
            tvTablaColaboradores.setItems(colaboradores);
        }else{
           lista = ColaboradorImp.obtenerColaboradoresNombre(dato);
           if (lista!=null && !lista.isEmpty()) {
                colaboradores.addAll(lista);
                tvTablaColaboradores.setItems(colaboradores);
           }else{
               Integer rol = obtenerRol(dato);
               if(rol>0){
                   lista = ColaboradorImp.obtenerColaboradoresRol(rol);
                   if(lista!=null && !lista.isEmpty()){
                        colaboradores.addAll(lista);
                        tvTablaColaboradores.setItems(colaboradores);
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
    
    @Override
    public void notificarOperacion(String tipo, String nombre) {
        cargarLaInformacion();
    }
}