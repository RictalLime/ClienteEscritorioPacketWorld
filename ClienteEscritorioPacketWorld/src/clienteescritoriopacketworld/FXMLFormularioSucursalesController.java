/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.SucursalImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Sucursal;
import clienteescritoriopacketworld.utilidad.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tron7
 */
public class FXMLFormularioSucursalesController implements Initializable {
    
    @FXML
    private TextField tfCodigoSucursal;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfEstatus;
    @FXML
    private ComboBox<Calle> cbCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private ComboBox<Colonia> cbColonia;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private ComboBox<Ciudad> cbCiudad;
    @FXML
    private ComboBox<Estado> cbEstado;
    @FXML
    private Button btnGuardar;
    //@FXML
    //private ComboBox<Rol> cbRol;

    private NotificadoOperacion observador; 
    private Sucursal sucursalEditado;
    private boolean modoEdicion = false;
    
    //ObservableList<Rol> tiposDeColaboradores;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initializeValores(NotificadoOperacion observador, Sucursal sucursalEditado){
        this.sucursalEditado = sucursalEditado;
        this.observador = observador;
        if(sucursalEditado!=null){
            modoEdicion = true;
            llenarcampos();
            btnGuardar.setText("Editar");
            tfCodigoSucursal.setEditable(false);
        }
    }
    
    @FXML
    private void regresarPrincipal(MouseEvent event) {
        cerrarVentana();
    }
    
    @FXML
    private void onClickGuardar(ActionEvent event) {
        Sucursal sucursal = new Sucursal();
        sucursal.setCodigoSucursal(tfCodigoSucursal.getText());
        sucursal.setNombre(tfNombre.getText());
        sucursal.setEstatus(tfEstatus.getText());
        sucursal.setCalle(tfCalle.getText());
        sucursal.setNumero(tfNumero.getText());
        sucursal.setColonia(tfColonia.getText());
        sucursal.setCodigoPostal(tfCodigoPostal.getText());
        sucursal.setCiudad(tfCiudad.getText());
        sucursal.setEstado(tfEstado.getText());
        if(validarCampos(sucursal)){
            if(!modoEdicion){
                guardarDatosSucursal(sucursal);
            }else{
                sucursal.setIdSucursal(sucursalEditado.getIdSucursal());
                editarDatosSucursal(sucursal);
            }
            
        }
    }
    
    private boolean validarCampos(Sucursal sucursal) {
        if (sucursal.getNombre() == null || sucursal.getNombre().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Nombre es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (sucursal.getCalle() == null || sucursal.getCalle().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Calle es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (sucursal.getNumero() != null && sucursal.getNumero().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Numero es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (sucursal.getColonia() == null || sucursal.getColonia().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Colonia es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (sucursal.getCodigoPostal() == null || !sucursal.getCodigoPostal().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Codigo Postal es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (sucursal.getEstado() == null || sucursal.getEstado().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Estado es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }
    
    private void guardarDatosSucursal(Sucursal sucursal){
        Mensaje msj = SucursalImp.registrarSucursal(sucursal);
        if(!msj.isError()){
            Utilidades.mostrarAlertaSimple("Registro Exitoso", "Sucursal: " + sucursal.getNombre()+" Agregado", Alert.AlertType.INFORMATION);
            observador.notificarOperacion("Guardar", sucursal.getNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
            sucursal = null;
        }
    }
    
    private void cerrarVentana(){
        Stage base = (Stage) tfNombre.getScene().getWindow();
        base.close();
    }
    
    private void editarDatosSucursal(Sucursal sucursal){
        Mensaje msj = SucursalImp.editarSucursal(sucursal);
        if(!msj.isError()){
            Utilidades.mostrarAlertaSimple("Edición", "Sucursal: " +sucursal.getNombre()+ " Editado" , Alert.AlertType.INFORMATION);
            observador.notificarOperacion("Edición", sucursal.getNombre());
            cerrarVentana();
        }else{
            sucursal = null;
            Utilidades.mostrarAlertaSimple("Error", "No se pudo editar el colaborador", Alert.AlertType.ERROR);
        }
    }
    
    private void llenarcampos() {
        tfNombre.setText(sucursalEditado.getNombre());
        tfCalle.setText(sucursalEditado.getCalle());
        tfNumero.setText(sucursalEditado.getNumero());
        tfColonia.setText(sucursalEditado.getColonia());
        tfCodigoPostal.setText(sucursalEditado.getCodigoPostal());
        tfEstado.setText(sucursalEditado.getEstado());
        tfCodigoSucursal.setEditable(false);
        tfEstatus.setEditable(false);
    }
}
