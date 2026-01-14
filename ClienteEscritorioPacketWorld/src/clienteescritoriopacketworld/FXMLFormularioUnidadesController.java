/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.HistorialDeBajaImp;
import clienteescritoriopacketworld.dominio.UnidadImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.EstadoUnidad;
import clienteescritoriopacketworld.pojo.HistorialDeBaja;
import clienteescritoriopacketworld.pojo.TipoUnidad;
import clienteescritoriopacketworld.pojo.Unidad;
import clienteescritoriopacketworld.utilidad.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author b1nc0
 */
public class FXMLFormularioUnidadesController implements Initializable {

    @FXML
    private ImageView imgRegresar;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfVin;
    @FXML
    private ComboBox<EstadoUnidad> cbEstadoUnidad;
    @FXML
    private Label lEstadoUnidad;
    @FXML
    private TextField tfNumeroIdentificacion;
    @FXML
    private TextField tfAnio;
    @FXML
    private ComboBox<TipoUnidad> cbTipoUnidad;
    @FXML
    private TextField tfMotivo;
    @FXML
    private Label lMotivo;

    private NotificadoOperacion observador;
    private Unidad unidadEditada;
    private boolean modoEdicion = false;
    private ObservableList<TipoUnidad> tiposDeUnidad;
    private ObservableList<EstadoUnidad> tiposDeEstado;
    private HistorialDeBaja historial;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTiposUnidad();
        cargarEstadoUnidad();
        tfMotivo.setVisible(false);
        lMotivo.setVisible(false);
        cbEstadoUnidad.setDisable(true);
        lEstadoUnidad.setVisible(false);
        cbEstadoUnidad.setVisible(false);
        
        // Bloquear edición manual del NII, ya que es automático
        tfNumeroIdentificacion.setEditable(false);
        
        // Listeners para generar NII en tiempo real
        tfVin.textProperty().addListener((observable, oldValue, newValue) -> {
            generarNII();
        });
        
        tfAnio.textProperty().addListener((observable, oldValue, newValue) -> {
            generarNII();
        });
    }    

    public void initializeValores(NotificadoOperacion observador, Unidad unidadEditada) {
        this.observador = observador;
        this.unidadEditada = unidadEditada;
        if (unidadEditada != null) {
            modoEdicion = true;
            cargarDatosEdicion();
        }
    }
    
    private void cargarTiposUnidad() {
        tiposDeUnidad = FXCollections.observableArrayList();
        UnidadImp unidadImp = new UnidadImp();
        // Corrección de nombre de método
        List<TipoUnidad> lista = unidadImp.obtenerTiposUnidades();
        if(lista != null){
            tiposDeUnidad.addAll(lista);
            cbTipoUnidad.setItems(tiposDeUnidad);
        }
    }

    private void cargarDatosEdicion() {
        cbEstadoUnidad.setDisable(false);
        lEstadoUnidad.setVisible(true);
        cbEstadoUnidad.setVisible(true);
        
        // Cargar tipos
        int posicionTipo = buscarIdTipoUnidad(unidadEditada.getIdTipoUnidad());
        cbTipoUnidad.getSelectionModel().select(posicionTipo);
        
        int posicionEstado = buscarIdEstadoUnidad(unidadEditada.getIdEstadoUnidad());
        cbEstadoUnidad.getSelectionModel().select(posicionEstado);
        
        // Cargar campos de texto
        tfMarca.setText(unidadEditada.getMarca());
        tfModelo.setText(unidadEditada.getModelo());
        tfAnio.setText(unidadEditada.getAnio());
        tfVin.setText(unidadEditada.getVin());
        
        tfNumeroIdentificacion.setText(unidadEditada.getNii());
    }

    private int buscarIdTipoUnidad(Integer idTipoUnidad) {
        for(int i = 0; i < tiposDeUnidad.size(); i++){
            if(tiposDeUnidad.get(i).getIdTipoUnidad().equals(idTipoUnidad)){
                return i;
            }
        }
        return -1;
    }

    @FXML
    private void regresarPrincipal(MouseEvent event) {
        cerrarVentana();
    }

    @FXML
    private void guardarDatos(ActionEvent event) {
        if(tfMarca.getText().isEmpty() || tfModelo.getText().isEmpty() || tfAnio.getText().isEmpty() || tfVin.getText().isEmpty()){
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Por favor llene todos los campos", Alert.AlertType.WARNING);
            return;
        }
        
        // Validación de longitud del VIN (mínimo 4 caracteres para el NII)
        if(tfVin.getText().length() < 4){
             Utilidades.mostrarAlertaSimple("VIN inválido", "El VIN debe tener al menos 4 caracteres", Alert.AlertType.WARNING);
             return;
        }

        Unidad unidad = new Unidad();
        unidad.setMarca(tfMarca.getText());
        unidad.setModelo(tfModelo.getText());
        unidad.setAnio(tfAnio.getText());
        unidad.setVin(tfVin.getText());
        unidad.setNii(tfNumeroIdentificacion.getText()); 
        
        if(cbTipoUnidad.getValue() != null){
             unidad.setIdTipoUnidad(cbTipoUnidad.getValue().getIdTipoUnidad());
        }else{
            Utilidades.mostrarAlertaSimple("Selección requerida", "Seleccione un tipo de unidad", Alert.AlertType.WARNING);
            return;
        }
        
        UnidadImp unidadImp = new UnidadImp();

        if(modoEdicion){
            unidad.setIdUnidad(unidadEditada.getIdUnidad());
            
            // Estado
            if(cbEstadoUnidad.getValue() != null){
                unidad.setIdEstadoUnidad(cbEstadoUnidad.getValue().getIdEstadoUnidad());
                
                // Si es baja (id 2 segun lógica anterior), crear historial
                if(cbEstadoUnidad.getValue().getIdEstadoUnidad() == 2 && !tfMotivo.getText().isEmpty()){
                    agregarHistorial();
                }
            } else {
                 unidad.setIdEstadoUnidad(unidadEditada.getIdEstadoUnidad());
            }

            // Corrección: editarUnidad (minúscula)
            Mensaje msj = unidadImp.EditarUnidad(unidad);
            if(!msj.isError()){
                Utilidades.mostrarAlertaSimple("Éxito", "Unidad editada correctamente", Alert.AlertType.INFORMATION);
                if(observador != null) observador.notificarOperacion("Actualizar", unidad.getMarca());
                cerrarVentana();
            }else{
                Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        }else{
            // Corrección: registrarUnidad (nombre estándar)
            Mensaje msj = unidadImp.agregarUnidad(unidad);
            if(!msj.isError()){
                Utilidades.mostrarAlertaSimple("Éxito", msj.getMensaje(), Alert.AlertType.INFORMATION);
                if(observador != null) observador.notificarOperacion("Registrar", unidad.getMarca());
                cerrarVentana();
            }else{
                Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        }
    }

    private void cargarEstadoUnidad() {
        tiposDeEstado = FXCollections.observableArrayList();
        UnidadImp unidadImp = new UnidadImp();
        // Corrección de nombre de método
        List<EstadoUnidad> lista = unidadImp.obtenerTiposDeEstados();
        if(lista != null){
            tiposDeEstado.addAll(lista);  
            cbEstadoUnidad.setItems(tiposDeEstado);
        }
    }

    private int buscarIdEstadoUnidad(Integer idEstadoUnidad) {
        for(int i=0; i<tiposDeEstado.size();i++){
            if(tiposDeEstado.get(i).getIdEstadoUnidad().equals(idEstadoUnidad)){
                return i;
            }
        }
        return -1;
    }

    @FXML
    private void detectarEstado(ActionEvent event) {
        if(cbEstadoUnidad.getSelectionModel().getSelectedItem() != null){
             if(cbEstadoUnidad.getSelectionModel().getSelectedItem().getIdEstadoUnidad() == 2){ // 2 = Baja
                lMotivo.setVisible(true);
                tfMotivo.setVisible(true);
            }else{
                lMotivo.setVisible(false);
                tfMotivo.setVisible(false);
            }
        }
    }
    
    private void agregarHistorial(){
        historial = new HistorialDeBaja();
        historial.setMotivo(tfMotivo.getText());
        historial.setIdUnidad(unidadEditada.getIdUnidad());
        
        HistorialDeBajaImp historialImp = new HistorialDeBajaImp();
        // Corrección: registrarBaja
        Mensaje msj = historialImp.agregarHistorialDeBaja(historial);
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) tfMarca.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Genera el Número de Identificación Interno (NII)
     * Formato: Año + Primeros 4 caracteres del VIN
     */
    private void generarNII() {
        String anio = tfAnio.getText();
        String vin = tfVin.getText();
        
        // Solo generamos si tenemos datos suficientes
        if (anio != null && !anio.isEmpty() && vin != null && vin.length() >= 4) {
            String nii = anio + vin.substring(0, 4).toUpperCase();
            tfNumeroIdentificacion.setText(nii);
        }
    }
}