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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tron7
 */
public class FXMLFormularioUnidadesController implements Initializable {
    
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfNumeroIdentificacion;
    @FXML
    private TextField tfVin;
    @FXML
    private ComboBox<TipoUnidad> cbTipoUnidad;
    
    /**
     * Initializes the controller class.
     */
    
    private NotificadoOperacion observador; 
    private Unidad unidadEditada;
    private boolean modoEdicion = false;
    private HistorialDeBaja historial = null;
    
    ObservableList<TipoUnidad> tiposDeUnidades;
    ObservableList<EstadoUnidad> tiposDeEstado;
    
    
    @FXML
    private TextField tfAnio;
    @FXML
    private Label lEstadoUnidad;
    @FXML
    private ComboBox<EstadoUnidad> cbEstadoUnidad;
    @FXML
    private Label lMotivo;
    @FXML
    private TextField tfMotivo;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lEstadoUnidad.setVisible(false);
        lMotivo.setVisible(false);
        cbEstadoUnidad.setVisible(false);
        tfMotivo.setVisible(false);
        cargarTiposDeUnidad();
        cargarEstadoUnidad();
    }
    
    public void initializeValores(NotificadoOperacion observador, Unidad unidadEditada){
        this.unidadEditada = unidadEditada;
        this.observador = observador;
        if(unidadEditada!= null){
            modoEdicion = true;
            agregarHistorial();
            lEstadoUnidad.setVisible(true);
            cbEstadoUnidad.setVisible(true);
            llenarcampos();
            btnGuardar.setText("Editar");
             if(unidadEditada.getIdEstadoUnidad()==2){
                lMotivo.setVisible(true);
                tfMotivo.setVisible(true);
                llenarMotivo();
            }
        }
    }

    @FXML
    private void regresarPrincipal(MouseEvent event) {
        cerrarVentana();
    }
    
    @FXML
    private void onClickGuardar(ActionEvent event) {
        Unidad unidad= new Unidad();
        unidad.setMarca(tfMarca.getText());
        unidad.setModelo(tfModelo.getText());
        unidad.setVin(tfVin.getText());
        unidad.setNii(tfNumeroIdentificacion.getText());
        unidad.setAnio(tfAnio.getText());
        unidad.setIdTipoUnidad((cbTipoUnidad.getSelectionModel().getSelectedItem()!= null)?
                cbTipoUnidad.getSelectionModel().getSelectedItem().getIdTipoUnidad(): -1);
        unidad.setIdEstadoUnidad((cbEstadoUnidad.getSelectionModel().getSelectedItem()!= null)?
                cbEstadoUnidad.getSelectionModel().getSelectedItem().getIdEstadoUnidad(): 1);
        if(validarCampos(unidad)){
            if(!modoEdicion){
                guardarDatosUnidad(unidad);
            }else{
                unidad.setIdUnidad(unidadEditada.getIdUnidad());
                if(unidad.getIdEstadoUnidad()==2){
                    unidad.setMotivo(tfMotivo.getText());
                }
                editarUnidad(unidad);
            }
        }
    }
    
    private void llenarcampos() {
        tfMarca.setText(unidadEditada.getMarca());
        tfModelo.setText(unidadEditada.getModelo());
        tfVin.setText(unidadEditada.getVin());
        tfVin.setEditable(false);
        tfNumeroIdentificacion.setText(unidadEditada.getNii());
        
        tfAnio.setText(unidadEditada.getAnio().substring(0, 4));
        int poscicion = buscarIdTipoUnidad(unidadEditada.getIdTipoUnidad());
        cbTipoUnidad.getSelectionModel().select(poscicion);
        int pocicionUnidad = buscarIdEstadoUnidad(unidadEditada.getIdEstadoUnidad());
        cbEstadoUnidad.getSelectionModel().select(pocicionUnidad);
    }
    
    private int buscarIdTipoUnidad(int idTipoUnidad){
        for(int i=0; i<tiposDeUnidades.size();i++){
            if(tiposDeUnidades.get(i).getIdTipoUnidad() == idTipoUnidad){
                return i;
            }
        }
        return -1;
    }
     
    private void cerrarVentana(){
        Stage base = (Stage) tfModelo.getScene().getWindow();
        base.close();
    }
     
    private void cargarTiposDeUnidad(){
        tiposDeUnidades = FXCollections.observableArrayList();
        tiposDeUnidades.addAll(UnidadImp.obtenerTiposUnidades());  
        cbTipoUnidad.setItems(tiposDeUnidades);
    }
    
    private boolean validarCampos(Unidad unidad) {
        if (unidad.getMarca() == null || unidad.getMarca().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Error", "La marca es obligatoria.", Alert.AlertType.ERROR);
            return false;
        }
        if (unidad.getModelo() == null || unidad.getModelo().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Error", "El modelo es obligatorio.", Alert.AlertType.ERROR);
            return false;
        }
        if (unidad.getAnio() == null || !unidad.getAnio().matches("\\d{4}")) {
            Utilidades.mostrarAlertaSimple("Error", "El año debe ser un valor numérico de 4 dígitos.", Alert.AlertType.ERROR);
            return false;
        }
        if (unidad.getVin() == null || !unidad.getVin().matches("[A-HJ-NPR-Z0-9]{17}")) {
            Utilidades.mostrarAlertaSimple("Error", "El VIN debe tener exactamente 17 caracteres alfanuméricos (sin las letras I, O, Q).", Alert.AlertType.ERROR);
            return false;
        }
        if (unidad.getNii() == null || !unidad.getNii().equals(generarNII(unidad))) {
            Utilidades.mostrarAlertaSimple("", "El Número de Identificación Interno (NII) no coincide con el formato requerido (Año + primeros 4 caracteres del VIN).",Alert.AlertType.ERROR);
            return false;
        }
        if (unidad.getIdTipoUnidad() == null || unidad.getIdTipoUnidad() < 0) {
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un Tipo De Unidad.", Alert.AlertType.ERROR);
            return false;
        }
        if (unidad.getIdEstadoUnidad() == null || unidad.getIdEstadoUnidad() < 0) {
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un Estado de la unidad", Alert.AlertType.ERROR);
            return false;
        }
        if (!unidad.getMarca().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{1,50}$")) {
            Utilidades.mostrarAlertaSimple("Error", "La marca solo puede contener letras y espacios (máx. 50).", Alert.AlertType.ERROR);
            return false;
        }
        if (!unidad.getModelo().matches("^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ\\s-]{1,50}$")) {
            Utilidades.mostrarAlertaSimple("Error", "El modelo solo puede contener letras, números y guiones (máx. 50).", Alert.AlertType.ERROR);
            return false;
        }
        int anioInt = Integer.parseInt(unidad.getAnio());
        int anioActual = java.time.Year.now().getValue();
        if (anioInt < 1980 || anioInt > anioActual + 1) {
            Utilidades.mostrarAlertaSimple("Error", "El año debe estar entre 1980 y " + (anioActual + 1) + ".", Alert.AlertType.ERROR);
            return false;
        }
        if (unidad.getVin().matches("^(0{17}|[A-Z0-9])\\1{16}$")) {
            Utilidades.mostrarAlertaSimple("Error", "El VIN no puede ser repetitivo o inválido.", Alert.AlertType.ERROR);
            return false;
        }
        if (unidad.getIdEstadoUnidad() == 2 && (unidad.getMotivo() == null || unidad.getMotivo().trim().length() < 5)) {
            Utilidades.mostrarAlertaSimple("Error", "Debe ingresar un motivo válido (mínimo 5 caracteres).", Alert.AlertType.ERROR);
            return false;
        }
        if (UnidadImp.obtenerUnidadesPorVin(unidad.getVin()) != null) {
            Utilidades.mostrarAlertaSimple("Error", "Ya existe una unidad registrada con este VIN.", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private String generarNII(Unidad unidad) {
        String anio = unidad.getAnio();
        String vin = unidad.getVin();
        if (anio != null && vin != null && vin.length() >= 4) {
            return anio + vin.substring(0, 4);
        }
        return null;
    }
    
    private void guardarDatosUnidad(Unidad unidad){
        Mensaje msj = UnidadImp.agregarUnidad(unidad);
        if(!msj.isError()){
            Utilidades.mostrarAlertaSimple("Registro Exitoso", "Unidad: " + unidad.getMarca()+" Agregado", Alert.AlertType.INFORMATION);
            observador.notificarOperacion("Guardar", unidad.getMarca());
            cerrarVentana();
        }else{
            unidad = null;
            Utilidades.mostrarAlertaSimple("Error", "No se puede Guardar la unidad", Alert.AlertType.ERROR);
        }
    }
    
    private void editarUnidad(Unidad unidad){
        if(unidad.getIdEstadoUnidad()==2 && historial == null){
            HistorialDeBaja baja = new HistorialDeBaja();
            baja.setIdUnidad(unidad.getIdUnidad());
            baja.setMotivo(unidad.getMotivo());
            HistorialDeBajaImp.agregarHistorialDeBaja(baja);
        }else{
            if(unidad.getIdEstadoUnidad()==1 && historial != null){
                HistorialDeBajaImp.eliminarHistorialDeBaja(historial.getIdHistorialDeBaja());
            }
        }
        if(unidad.getIdEstadoUnidad()==2 && historial != null){
            HistorialDeBaja baja = new HistorialDeBaja();
            baja.setIdUnidad(unidad.getIdUnidad());
            baja.setMotivo(unidad.getMotivo());
            baja.setIdHistorialDeBaja(historial.getIdHistorialDeBaja());
            HistorialDeBajaImp.editarHistorialDeBaja(baja);
        }
        Mensaje msj = UnidadImp.EditarUnidad(unidad);
        if(!msj.isError()){
            Utilidades.mostrarAlertaSimple("Editar", "Unidad editada correctamente", Alert.AlertType.INFORMATION);
            observador.notificarOperacion("Guardar", unidad.getMarca());
            cerrarVentana();
        }else{
            unidad = null;
            Utilidades.mostrarAlertaSimple("Error", "No se pudo editar la unidad", Alert.AlertType.ERROR);
        }
    }

    private void cargarEstadoUnidad() {
        tiposDeEstado = FXCollections.observableArrayList();
        tiposDeEstado.addAll(UnidadImp.obtenerTiposDeEstados());  
        cbEstadoUnidad.setItems(tiposDeEstado);
    }

    private int buscarIdEstadoUnidad(Integer idEstadoUnidad) {
        for(int i=0; i<tiposDeEstado.size();i++){
            if(tiposDeEstado.get(i).getIdEstadoUnidad()== idEstadoUnidad){
                return i;
            }
        }
        return -1;
    }

    @FXML
    private void detectarEstado(ActionEvent event) {
        if(cbEstadoUnidad.getSelectionModel().getSelectedItem().getIdEstadoUnidad()==2){
            lMotivo.setVisible(true);
            tfMotivo.setVisible(true);
        }else{
            lMotivo.setVisible(false);
            tfMotivo.setVisible(false);
        }
    }
    
    private void agregarHistorial(){
        historial = HistorialDeBajaImp.obtenerHistorialPorIdUnidad(unidadEditada.getIdUnidad());
    }

    private void llenarMotivo() {
        tfMotivo.setText(historial.getMotivo());
    }
}
