/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.ColaboradorImp;
import clienteescritoriopacketworld.dominio.ConductoresAsignadosImp;
import clienteescritoriopacketworld.dominio.UnidadImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Colaborador;
import clienteescritoriopacketworld.pojo.ConductoresAsignados;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tron7
 */
public class FXMLFormularioAsignacionesController implements Initializable {

    @FXML
    private Button btnGuardar;
    @FXML
    private ComboBox<Unidad> cbUnidades;
    @FXML
    private ComboBox<Colaborador> cbColaboradores;
    @FXML
    private TextField tfColaborador;
    @FXML
    private TextField tfUnidad;
    
    ObservableList<Unidad> unidades;
    ObservableList<Colaborador> tiposDeConductores;

    /**
     * Initializes the controller class.
     */
    
    private NotificadoOperacion observador; 
    private  ConductoresAsignados conductorAsignadoEditado;
    private boolean modoEdicion = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarConductores();
        cargarUnidades();
    }    
    public void initializeValores(NotificadoOperacion observador, ConductoresAsignados conductorAsignadoEditado){
        this.conductorAsignadoEditado = conductorAsignadoEditado;
        this.observador = observador;
        if(conductorAsignadoEditado!= null){
            modoEdicion = true;
            llenarcampos();
            btnGuardar.setText("Editar");
        }else{
            validar();
        }
    }

    @FXML
    private void regresarPrincipal(MouseEvent event) {
        cerrarVentana();
    }

    @FXML
    private void onClickGuardar(ActionEvent event) {
        ConductoresAsignados conductor = new ConductoresAsignados();
        conductor.setIdColaborador((cbColaboradores.getSelectionModel().getSelectedItem() != null)?
                cbColaboradores.getSelectionModel().getSelectedItem().getIdColaborador(): conductorAsignadoEditado.getIdColaborador());
        conductor.setIdUnidad((cbUnidades.getSelectionModel().getSelectedItem() != null)?
                cbUnidades.getSelectionModel().getSelectedItem().getIdUnidad(): conductorAsignadoEditado.getIdUnidad());
        if(validarCampos(conductor)){
            if(!modoEdicion){
                guardarDatosAsignacion(conductor);
            }else{
                conductor.setIdConductoresAsignados(conductorAsignadoEditado.getIdConductoresAsignados());
                editarDatosAsignacion(conductor);
            }
        }
        
    }

    private void llenarcampos() {
        tfUnidad.setText(conductorAsignadoEditado.getUnidad());
        tfColaborador.setText(conductorAsignadoEditado.getConductor());
        int pocisionUnidad = buscarCliente(conductorAsignadoEditado.getIdUnidad());
        int pocisionConductor = buscarConductor(conductorAsignadoEditado.getIdColaborador());
        cbUnidades.getSelectionModel().select(pocisionUnidad);
        cbColaboradores.getSelectionModel().select(pocisionConductor);
    }

    private void cargarConductores() {
        tiposDeConductores = FXCollections.observableArrayList();
        tiposDeConductores.addAll(ColaboradorImp.obtenerConductoresSinAsignar());
        cbColaboradores.setItems(tiposDeConductores);
    }

    private void cargarUnidades() {
        unidades = FXCollections.observableArrayList();
        unidades.addAll(UnidadImp.obtenerUnidadesSinAsignar());
        cbUnidades.setItems(unidades);
    }
    private void cerrarVentana(){
        Stage base = (Stage) tfUnidad.getScene().getWindow();
        base.close();
    }

    private int buscarCliente(Integer idUnidad) {
        for(int i=0; i<unidades.size();i++){
            if(unidades.get(i).getIdUnidad() == idUnidad){
                return i;
            }
        }
        return -1;
    }

    private int buscarConductor(Integer idColaborador) {
        for(int i=0; i<tiposDeConductores.size();i++){
            if(tiposDeConductores.get(i).getIdColaborador()== idColaborador){
                return i;
            }
        }
        return -1;
    }

    private boolean validarCampos(ConductoresAsignados conductor) {
        if(conductor.getIdUnidad()<=0){
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un Vehiculo", Alert.AlertType.ERROR);
            return false;
        }
        if(conductor.getIdColaborador()<=0){
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un Conductor", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void guardarDatosAsignacion(ConductoresAsignados conductor) {
        Mensaje msj = ConductoresAsignadosImp.registrar(conductor);
        if(!msj.isError()){
            Utilidades.mostrarAlertaSimple("Registro Exitoso", "Asignación agregada", Alert.AlertType.INFORMATION);
            observador.notificarOperacion("Guardar",""+conductor.getIdColaborador() );
            cerrarVentana();
        }else{
            conductor = null;
            Utilidades.mostrarAlertaSimple("Error", "No se pudo agregar la asignación", Alert.AlertType.ERROR);
        }
    }

    private void editarDatosAsignacion(ConductoresAsignados conductor) {
        Mensaje msj = ConductoresAsignadosImp.editar(conductor);
        if(!msj.isError()){
            Utilidades.mostrarAlertaSimple("Edicion Exitoso", "Asignación editada", Alert.AlertType.INFORMATION);
            observador.notificarOperacion("Guardar",""+conductor.getIdColaborador() );
            cerrarVentana();
        }else{
            conductor = null;
            Utilidades.mostrarAlertaSimple("Error", "No se pudo editar la asignación", Alert.AlertType.ERROR);
        }
    }

    private void validar() {
        if(unidades.isEmpty()||tiposDeConductores.isEmpty()){
            Utilidades.mostrarAlertaSimple("Error", "No hay unidades o conductores disponibles", Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }
}
