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
 * @author Tron7
 */
public class FXMLModuloUnidadesController implements Initializable, NotificadoOperacion {
    
    @FXML
    private ImageView imgRegresar;
    @FXML
    private TextField tfBuscar;
    @FXML
    private TableView<Unidad> tvTablaUnidades;
    @FXML
    private TableColumn colMarca;
    @FXML
    private TableColumn colModelo;
    @FXML
    private TableColumn colAnio;
    @FXML
    private TableColumn colVin;
    @FXML
    private TableColumn colTipoDeUnidad;
    @FXML
    private TableColumn colNii;
    
    /**
     * Initializes the controller class.
     */
    
    private ObservableList<Unidad> unidades;
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
            // Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
            Utilidades.mostrarAlertaSimple("Error", "No podemos ir a la pantalla principal :(", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void regresarPrincipal(MouseEvent event) {
        irPantallaPrincipal();
    }


    @FXML
    private void irRegistrarUnidad(MouseEvent event) {
         irAFormulario(this, null);
    }

    @FXML
    private void irEditarUnidad(MouseEvent event) {
        Unidad unidad = tvTablaUnidades.getSelectionModel().getSelectedItem();
        if(unidad!= null){
            irAFormulario(this, unidad);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona una Unidad", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void irEliminarUnidad(MouseEvent event) {
        Unidad unidad = tvTablaUnidades.getSelectionModel().getSelectedItem();
        if(unidad!= null){
            Mensaje mensaje = UnidadImp.eliminarUnidad(unidad.getIdUnidad());
            if(!mensaje.isError()){
                Utilidades.mostrarAlertaSimple("Correcto", "Unidad Eliminada correctamente", Alert.AlertType.INFORMATION);
                cargarLaInformacion();
            }else{
                Utilidades.mostrarAlertaSimple("Error", "No se pudo eliminar la Unidad", Alert.AlertType.ERROR);
            }
            
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona una Unidad", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarUnidad(MouseEvent event) {
        if(!tfBuscar.getText().isEmpty()){
            String dato = tfBuscar.getText();
            buscarUnidad(dato);
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Campo de buscar Vacio", Alert.AlertType.ERROR);
        }
    }
    
    private void configurarTabla() {
           colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
           colModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
           colAnio.setCellValueFactory(new PropertyValueFactory("anio"));
           colVin.setCellValueFactory(new PropertyValueFactory("vin"));
           colTipoDeUnidad.setCellValueFactory(new PropertyValueFactory("tipoUnidad"));
           colNii.setCellValueFactory(new PropertyValueFactory("nii"));
    }

    private void cargarLaInformacion() {
           unidades = FXCollections.observableArrayList();
           List<Unidad> lista = UnidadImp.obtenerUnidades();
           if (lista != null) {
               unidades.addAll(lista);
               tvTablaUnidades.setItems(unidades);
           }else{
               Utilidades.mostrarAlertaSimple("ERROR", "Lo sentimos por el momento no se puede cargar la informacion"
                       + "de las Unidades, por favor intentélo mas tarde", Alert.AlertType.ERROR);
               cerrarVentana();
           }
    }
    private void cerrarVentana(){
            ((Stage) tfBuscar.getScene().getWindow()).close();
    }
    
    private void buscarUnidad(String dato) {
        try {
            unidades.clear();
            tvTablaUnidades.setItems(unidades);
            List<Unidad> lista = UnidadImp.obtenerUnidadesPorMarca(dato);
            if (!lista.isEmpty()) {
                unidades.addAll(lista);
                tvTablaUnidades.setItems(unidades);
            }else{
               lista = UnidadImp.obtenerUnidadesPorNii(dato);
               if (!lista.isEmpty()) {
                   unidades.addAll(lista);
                   tvTablaUnidades.setItems(unidades);
               }else{
                   lista = UnidadImp.obtenerUnidadesPorVin(dato);
                   if(!lista.isEmpty()){
                        unidades.addAll(lista);
                        tvTablaUnidades.setItems(unidades);
                   }else{
                       Utilidades.mostrarAlertaSimple("Aviso", "No se encontro la(s) Unidades", Alert.AlertType.WARNING);
                       cargarLaInformacion();
                   }
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            Logger.getLogger(FXMLModuloColaboradoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notificarOperacion(String tipo, String nombre) {
      cargarLaInformacion();
    }    
    
}
