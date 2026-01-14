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
 * @author b1nc0
 */
public class FXMLModuloColaboradoresController implements Initializable, NotificadoOperacion {

    @FXML
    private ImageView imgRegresar;
    @FXML
    private TextField tfBuscar;
    @FXML
    private TableView<Colaborador> tvColaboradores;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colNoPersonal;
    @FXML
    private TableColumn colCURP;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colRol;

    private ObservableList<Colaborador> listaColaboradores;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarLaInformacion();
        
        // Listener para la busqueda en tiempo real
        tfBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                tvColaboradores.setItems(listaColaboradores);
            } else {
                filtrarColaboradores(newValue);
            }
        });
    }    
    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colNoPersonal.setCellValueFactory(new PropertyValueFactory("noPersonal"));
        colCURP.setCellValueFactory(new PropertyValueFactory("curp"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory("rol"));
    }
    
    private void cargarLaInformacion(){
        listaColaboradores = FXCollections.observableArrayList();
        ColaboradorImp colaboradorImp = new ColaboradorImp();
        List<Colaborador> lista = colaboradorImp.obtenerColaboradores();
        
        if(lista != null){
            listaColaboradores.addAll(lista);
            tvColaboradores.setItems(listaColaboradores);
        } else {
            Utilidades.mostrarAlertaSimple("Error al cargar", 
                "No se pudo cargar la lista de colaboradores", 
                Alert.AlertType.ERROR);
        }
    }
    
    // Metodo para realizar el filtrado de busqueda
    private void filtrarColaboradores(String busqueda) {
        ObservableList<Colaborador> listaFiltrada = FXCollections.observableArrayList();
        String filtro = busqueda.toLowerCase();

        for (Colaborador colaborador : listaColaboradores) {
            // Verificamos nombre
            String nombre = colaborador.getNombre() != null ? colaborador.getNombre().toLowerCase() : "";
            // Verificamos numero de personal
            String noPersonal = colaborador.getNoPersonal() != null ? colaborador.getNoPersonal().toLowerCase() : "";
            // Verificamos rol
            String rol = colaborador.getRol() != null ? colaborador.getRol().toLowerCase() : "";

            // Si alguno contiene el texto de busqueda, se agrega a la lista
            if (nombre.contains(filtro) || noPersonal.contains(filtro) || rol.contains(filtro)) {
                listaFiltrada.add(colaborador);
            }
        }
        tvColaboradores.setItems(listaFiltrada);
    }

    @FXML
    private void regresarPrincipal(MouseEvent event) {
         try {
            Stage stage = (Stage) imgRegresar.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLPrincipal.fxml")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void irRegistrarColaboradors(MouseEvent event) {
        irAFormulario(this, null);
    }

    @FXML
    private void irEditarColaborador(MouseEvent event) {
        int posicion = tvColaboradores.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Colaborador colaborador = listaColaboradores.get(posicion);
            irAFormulario(this, colaborador);
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un colaborador", "Para editar debes seleccionar un colaborador de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void irEliminarColaborador(MouseEvent event) {
        int posicion = tvColaboradores.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
           Colaborador colaborador = listaColaboradores.get(posicion);
           String mensaje = "Estás seguro de eliminar a " + colaborador.getNombre() + 
                            " con número de personal: " + colaborador.getNoPersonal();
           
           if(Utilidades.mostrarAlertaConfirmacion("Eliminar Colaborador", mensaje)){
               Mensaje msj = ColaboradorImp.eliminarColaborador(colaborador.getIdColaborador());
               if(!msj.isError()){
                   Utilidades.mostrarAlertaSimple("Éxito", msj.getMensaje(), Alert.AlertType.INFORMATION);
                   cargarLaInformacion();
               }else{
                   Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.WARNING);
                       cargarLaInformacion();
               }
           }
        } else {
             Utilidades.mostrarAlertaSimple("Selecciona un colaborador", "Para eliminar debes seleccionar un colaborador de la tabla", Alert.AlertType.WARNING);
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
        System.out.println("Operación: " + tipo + " realizada por: " + nombre);
    }
}