/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.UnidadImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Unidad;
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
public class FXMLModuloUnidadesController implements Initializable, NotificadoOperacion {

    @FXML
    private ImageView imgRegresar;
    @FXML
    private TableView<Unidad> tvTablaUnidades;
    @FXML
    private TableColumn tcMarca;
    @FXML
    private TableColumn tcModelo;
    @FXML
    private TableColumn tcAnio;
    @FXML
    private TableColumn tcVin;
    @FXML
    private TableColumn tcNii;
    @FXML
    private TextField tfBuscar;
    
    private ObservableList<Unidad> listaUnidades;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarLaInformacion();
        
        // Listener para búsqueda en tiempo real
        tfBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                tvTablaUnidades.setItems(listaUnidades);
            } else {
                filtrarUnidades(newValue);
            }
        });
    }    
    
    private void configurarTabla(){
        tcMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        tcModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        tcAnio.setCellValueFactory(new PropertyValueFactory("anio"));
        tcVin.setCellValueFactory(new PropertyValueFactory("vin"));
        tcNii.setCellValueFactory(new PropertyValueFactory("nii"));
    }
    
    private void cargarLaInformacion(){
        listaUnidades = FXCollections.observableArrayList();
        UnidadImp unidadImp = new UnidadImp();
        List<Unidad> lista = unidadImp.obtenerUnidades();
        
        if(lista != null){
            listaUnidades.addAll(lista);
            tvTablaUnidades.setItems(listaUnidades);
        } else {
             Utilidades.mostrarAlertaSimple("Error de conexión", "No se pudo recuperar la información de unidades.", Alert.AlertType.ERROR);
        }
    }
    
    private void filtrarUnidades(String busqueda) {
        ObservableList<Unidad> listaFiltrada = FXCollections.observableArrayList();
        String filtro = busqueda.toLowerCase();

        for (Unidad unidad : listaUnidades) {
            // Verificamos VIN
            String vin = unidad.getVin() != null ? unidad.getVin().toLowerCase() : "";
            // Verificamos Marca
            String marca = unidad.getMarca() != null ? unidad.getMarca().toLowerCase() : "";
            // Verificamos NII
            String nii = unidad.getNii() != null ? unidad.getNii().toLowerCase() : "";

            // Si alguno coincide, se agrega
            if (vin.contains(filtro) || marca.contains(filtro) || nii.contains(filtro)) {
                listaFiltrada.add(unidad);
            }
        }
        tvTablaUnidades.setItems(listaFiltrada);
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
    private void irRegistrarUnidad(MouseEvent event) {
        irAFormulario(this, null);
    }

    @FXML
    private void irEditarUnidad(MouseEvent event) {
        int posicion = tvTablaUnidades.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Unidad unidad = tvTablaUnidades.getItems().get(posicion);
            irAFormulario(this, unidad);
        } else {
            Utilidades.mostrarAlertaSimple("Selección requerida", "Debe seleccionar una unidad de la lista para editar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void irEliminarUnidad(MouseEvent event) {
        int posicion = tvTablaUnidades.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Unidad unidad = tvTablaUnidades.getItems().get(posicion);
            boolean confirmar = Utilidades.mostrarAlertaConfirmacion("Eliminar Unidad", 
                    "¿Está seguro de eliminar la unidad con VIN: " + unidad.getVin() + "?");
            
            if(confirmar){
                UnidadImp unidadImp = new UnidadImp();
                Mensaje msj = unidadImp.eliminarUnidad(unidad.getIdUnidad());
                if(!msj.isError()){
                    Utilidades.mostrarAlertaSimple("Éxito", msj.getMensaje(), Alert.AlertType.INFORMATION);
                    cargarLaInformacion();
                } else {
                    Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
                }
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selección requerida", "Debe seleccionar una unidad de la lista para eliminar.", Alert.AlertType.WARNING);
        }
    }
    
    private void irAFormulario(NotificadoOperacion observador, Unidad unidad){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioUnidades.fxml"));
            Parent root = loader.load();
            
            FXMLFormularioUnidadesController controlador = loader.getController();
            controlador.initializeValores(observador, unidad);
            
            Stage ecena = new Stage();
            Scene ecenario = new Scene(root);
            ecena.setScene(ecenario);
            ecena.setTitle("Formulario Unidades");
            ecena.initModality(Modality.APPLICATION_MODAL);
            ecena.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLModuloUnidadesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notificarOperacion(String tipo, String nombre) {
      cargarLaInformacion();
      System.out.println("Operación: " + tipo + " realizada en unidad: " + nombre);
    }    
    
}