/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.CiudadImp;
import clienteescritoriopacketworld.dominio.EstadoImp;
import clienteescritoriopacketworld.dominio.SucursalImp;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.pojo.Ciudad;
import clienteescritoriopacketworld.pojo.Estado;
import clienteescritoriopacketworld.pojo.Sucursal;
import clienteescritoriopacketworld.utilidad.Constantes;
import clienteescritoriopacketworld.utilidad.Utilidades;
import java.net.URL;
import java.util.HashMap;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tron7
 */
public class FXMLFormularioSucursalesController implements Initializable {

    @FXML private TextField tfCodigoSucursal;
    @FXML private TextField tfNombre;
    @FXML private TextField tfEstatus;
    @FXML private TextField tfCalle;
    @FXML private TextField tfNumero;
    @FXML private TextField tfColonia;
    @FXML private TextField tfCodigoPostal;

    @FXML private ComboBox<Ciudad> cbCiudad;
    @FXML private ComboBox<Estado> cbEstado;
    @FXML private Button btnGuardar;

    private NotificadoOperacion observador;
    private Sucursal sucursalEditado;
    private boolean modoEdicion = false;
    
    private ObservableList<Estado> estados;
    private ObservableList<Ciudad> ciudades;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstados();
        cbEstado.setOnAction(event -> cargarCiudades());
    }

    public void initializeValores(NotificadoOperacion observador, Sucursal sucursalEditado) {
        this.sucursalEditado = sucursalEditado;
        this.observador = observador;
        if (sucursalEditado != null) {
            modoEdicion = true;
            btnGuardar.setText("Editar");
            llenarCampos();
            tfCodigoSucursal.setEditable(false);
            tfEstatus.setEditable(false);
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

        Estado estadoSel = cbEstado.getValue();
        Ciudad ciudadSel = cbCiudad.getValue();

        if (estadoSel != null) {
            sucursal.setIdEstado(estadoSel.getIdEstado());
            sucursal.setEstado(estadoSel.getNombre());
        }

        if (ciudadSel != null) {
            sucursal.setIdCiudad(ciudadSel.getIdCiudad());
            sucursal.setCiudad(ciudadSel.getNombre());
        }

        if (validarCampos(sucursal)) {
            if (!modoEdicion) {
                guardarDatosSucursal(sucursal);
            } else {
                sucursal.setIdSucursal(sucursalEditado.getIdSucursal());
                editarDatosSucursal(sucursal);
            }
        }
    }

    private boolean validarCampos(Sucursal sucursal) {

        if (sucursal.getCodigoSucursal() == null || sucursal.getCodigoSucursal().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El código de sucursal es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (!sucursal.getCodigoSucursal().matches("^[A-Za-z0-9]{1,10}$")) {
            Utilidades.mostrarAlertaSimple("Validación", "El código de sucursal debe ser alfanumérico (máx. 10 caracteres).", Alert.AlertType.WARNING);
            return false;
        }
        if (sucursal.getNombre() == null || sucursal.getNombre().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Nombre es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (!sucursal.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{1,50}$")) {
            Utilidades.mostrarAlertaSimple("Validación", "El nombre solo puede contener letras y espacios (máx. 50).", Alert.AlertType.WARNING);
            return false;
        }
        if (sucursal.getCalle() == null || sucursal.getCalle().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Calle es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (!sucursal.getCalle().matches("^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ\\s]{1,100}$")) {
            Utilidades.mostrarAlertaSimple("Validación", "La calle solo puede contener letras, números y espacios (máx. 100).", Alert.AlertType.WARNING);
            return false;
        }
        if (sucursal.getNumero() == null || sucursal.getNumero().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Número es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (!sucursal.getNumero().matches("\\d{1,10}")) {
            Utilidades.mostrarAlertaSimple("Validación", "El número debe contener solo dígitos (máx. 10).", Alert.AlertType.WARNING);
            return false;
        }
        if (sucursal.getColonia() == null || sucursal.getColonia().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Colonia es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }

        if (sucursal.getCodigoPostal() == null || !sucursal.getCodigoPostal().matches("\\d{5}")) {
            Utilidades.mostrarAlertaSimple("Validación", "El Código Postal es obligatorio y debe tener 5 dígitos.", Alert.AlertType.WARNING);
            return false;
        }

        if (sucursal.getIdEstado() == null) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Estado es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (cbEstado.getSelectionModel().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "Debe seleccionar un estado.", Alert.AlertType.WARNING);
            return false;
        }
        if (sucursal.getIdCiudad() == null) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Ciudad es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (cbCiudad.getSelectionModel().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "Debe seleccionar una ciudad.", Alert.AlertType.WARNING);
            return false;
        }
        if (sucursal.getEstatus() == null || 
            !(sucursal.getEstatus().equalsIgnoreCase("activa") || sucursal.getEstatus().equalsIgnoreCase("inactiva"))) {
             Utilidades.mostrarAlertaSimple("Validación", "El estatus debe ser 'activa' o 'inactiva'.", Alert.AlertType.WARNING);
             return false;
        }
        int cp = Integer.parseInt(sucursal.getCodigoPostal());
        if (cp < 1000 || cp > 99999) {
            Utilidades.mostrarAlertaSimple("Validación", "El código postal debe estar dentro de un rango válido en México.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void guardarDatosSucursal(Sucursal sucursal) {
        Mensaje msj = SucursalImp.registrarSucursal(sucursal);
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Registro Exitoso", "Sucursal: " + sucursal.getNombre() + " agregada.", Alert.AlertType.INFORMATION);
            observador.notificarOperacion("Guardar", sucursal.getNombre());
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarDatosSucursal(Sucursal sucursal) {
        Mensaje msj = SucursalImp.editarSucursal(sucursal);
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Edición", "Sucursal: " + sucursal.getNombre() + " editada.", Alert.AlertType.INFORMATION);
            observador.notificarOperacion("Edición", sucursal.getNombre());
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error", "No se pudo editar la sucursal.", Alert.AlertType.ERROR);
        }
    }

    private void llenarCampos() {
        tfCodigoSucursal.setText(sucursalEditado.getCodigoSucursal());
        tfNombre.setText(sucursalEditado.getNombre());
        tfEstatus.setText(sucursalEditado.getEstatus());
        tfCalle.setText(sucursalEditado.getCalle());
        tfNumero.setText(sucursalEditado.getNumero());
        tfColonia.setText(sucursalEditado.getColonia());
        tfCodigoPostal.setText(sucursalEditado.getCodigoPostal());

        for (Estado e : cbEstado.getItems()) {
            if (e.getNombre().equals(sucursalEditado.getEstado())) {
                cbEstado.setValue(e);
                break;
            }
        }

        cargarCiudades();

        for (Ciudad c : cbCiudad.getItems()) {
            if (c.getNombre().equals(sucursalEditado.getCiudad())) {
                cbCiudad.setValue(c);
                break;
            }
        }

        tfCodigoSucursal.setEditable(false);
        tfEstatus.setEditable(false);
    }

    private void cargarEstados() {
        HashMap<String, Object> respuesta = EstadoImp.obtenerTodosLosEstados();
        if(!(boolean)respuesta.get(Constantes.KEY_ERROR)){
            List<Estado> estadosAPI = (List<Estado>) respuesta.get(Constantes.KEY_LISTA);
            estados = FXCollections.observableArrayList();
            estados.addAll(estadosAPI);
            cbEstado.setItems(estados);
        }else{
            Utilidades.mostrarAlertaSimple("Error", respuesta.get(Constantes.KEY_MENSAJE).toString(), Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }

    private void cargarCiudades() {
        Estado estadoSel = cbEstado.getValue();
        if (estadoSel == null) return;
        HashMap<String, Object> respuesta = CiudadImp.obtenerCiudadesPorIdEstado(estadoSel.getIdEstado());
        if(!(boolean)respuesta.get(Constantes.KEY_ERROR)){
            List<Ciudad> ciudadesAPI = (List<Ciudad>) respuesta.get(Constantes.KEY_LISTA);
            ciudades = FXCollections.observableArrayList();
            ciudades.addAll(ciudadesAPI);
            cbCiudad.setItems(ciudades);
        }else{
            Utilidades.mostrarAlertaSimple("Error", respuesta.get(Constantes.KEY_MENSAJE).toString(), Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }

    private void cerrarVentana() {
        Stage base = (Stage) tfNombre.getScene().getWindow();
        base.close();
    }
}
