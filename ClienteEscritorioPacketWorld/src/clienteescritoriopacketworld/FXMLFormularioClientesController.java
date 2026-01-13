/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.ClienteImp;
import clienteescritoriopacketworld.dominio.CiudadImp;
import clienteescritoriopacketworld.dominio.EstadoImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Ciudad;
import clienteescritoriopacketworld.pojo.Cliente;
import clienteescritoriopacketworld.pojo.Estado;
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
public class FXMLFormularioClientesController implements Initializable {

    @FXML private TextField tfNombre;
    @FXML private TextField tfApellidoPaterno;
    @FXML private TextField tfApellidoMaterno;
    @FXML private Button btnGuardar;
    @FXML private TextField tfCalle;
    @FXML private TextField tfNumero;
    @FXML private TextField tfColonia;
    @FXML private TextField tfCodigoPostal;
    @FXML private TextField tfTelefono;
    @FXML private TextField tfCorreo;

    @FXML private ComboBox<Ciudad> cbCiudad;
    @FXML private ComboBox<Estado> cbEstado;

    private NotificadoOperacion observador;
    private Cliente clienteEditado;
    private boolean modoEdicion = false;
    
    private ObservableList<Estado> estados;
    private ObservableList<Ciudad> ciudades;
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstados();
        cbEstado.setOnAction(event -> cargarCiudades());
    }  
    
    public void initializeValores(NotificadoOperacion observador, Cliente clienteEditado) {
        this.clienteEditado = clienteEditado;
        this.observador = observador;

        if (clienteEditado != null) {
            modoEdicion = true;
            btnGuardar.setText("Editar");
            llenarCampos();
        }
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
    
    private void llenarCampos() {

        tfNombre.setText(clienteEditado.getNombre());
        tfApellidoPaterno.setText(clienteEditado.getApellidoPaterno());
        tfApellidoMaterno.setText(clienteEditado.getApellidoMaterno());
        tfCalle.setText(clienteEditado.getCalle());
        tfNumero.setText(clienteEditado.getNumeroDeCasa());
        tfColonia.setText(clienteEditado.getColonia());
        tfCodigoPostal.setText(clienteEditado.getCp());
        tfTelefono.setText(clienteEditado.getTelefono());
        tfCorreo.setText(clienteEditado.getCorreo());
        
        for (Estado e : cbEstado.getItems()) {
            if (e.getNombre().equals(clienteEditado.getNombreEstado())) {
                cbEstado.setValue(e);
                break;
            }
        }
        cargarCiudades();
        for (Ciudad c : cbCiudad.getItems()) {
            if (c.getNombre().equals(clienteEditado.getNombreCiudad())) {
                cbCiudad.setValue(c);
                break;
            }
        }
    }

    @FXML
    private void regresarPrincipal(MouseEvent event) {
        cerrarVentana();
    }


    @FXML
    private void onClickGuardar(ActionEvent event) {
        Cliente cliente = new Cliente();
        cliente.setNombre(tfNombre.getText());
        cliente.setApellidoPaterno(tfApellidoPaterno.getText());
        cliente.setApellidoMaterno(tfApellidoMaterno.getText());
        cliente.setCalle(tfCalle.getText());
        cliente.setNumeroDeCasa(tfNumero.getText());
        cliente.setColonia(tfColonia.getText());
        cliente.setCp(tfCodigoPostal.getText());
        cliente.setTelefono(tfTelefono.getText());
        cliente.setCorreo(tfCorreo.getText());
        Estado estadoSel = cbEstado.getValue();
        Ciudad ciudadSel = cbCiudad.getValue();

        cliente.setIdEstado(estadoSel != null ? estadoSel.getIdEstado() : null);
        cliente.setIdCiudad(ciudadSel != null ? ciudadSel.getIdCiudad() : null);

        cliente.setNombreEstado(estadoSel != null ? estadoSel.getNombre() : null);
        cliente.setNombreCiudad(ciudadSel != null ? ciudadSel.getNombre() : null);

        if (validarCampos(cliente)) {
            if (!modoEdicion) {
                guadarDatosDelCliente(cliente);
            } else {
                cliente.setIdCliente(clienteEditado.getIdCliente());
                editarDatosDelCliente(cliente);
            }
        }
    }

    private boolean validarCampos(Cliente cliente) {

        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty() || cliente.getNombre().length() > 50) {
            Utilidades.mostrarAlertaSimple("Error", "El nombre es obligatorio.", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!cliente.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
            Utilidades.mostrarAlertaSimple("Error", "El nombre solo puede contener letras.", Alert.AlertType.ERROR);
            return false;
        }

        if (cliente.getApellidoPaterno() == null || cliente.getApellidoPaterno().trim().isEmpty() || cliente.getApellidoPaterno().length() > 50) {
            Utilidades.mostrarAlertaSimple("Error", "El apellido paterno es obligatorio.", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!cliente.getApellidoPaterno().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
            Utilidades.mostrarAlertaSimple("Error", "El apellido paterno solo puede contener letras.", Alert.AlertType.ERROR);
            return false;
        }
        
        if (cliente.getApellidoMaterno() == null || cliente.getApellidoMaterno().trim().isEmpty() || cliente.getApellidoMaterno().length() > 50) {
            Utilidades.mostrarAlertaSimple("Error", "El apellido paterno es obligatorio.", Alert.AlertType.ERROR);
            return false;
        }
        
        if (cliente.getApellidoMaterno() != null && !cliente.getApellidoMaterno().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]*$")) {
            Utilidades.mostrarAlertaSimple("Error", "El apellido materno solo puede contener letras.", Alert.AlertType.ERROR);
            return false;
        }

        if (cliente.getCalle() == null || cliente.getCalle().trim().isEmpty() || cliente.getCalle().length() > 100) {
            Utilidades.mostrarAlertaSimple("Error", "La calle es obligatoria.", Alert.AlertType.ERROR);
            return false;
        }

        if (cliente.getNumeroDeCasa() == null || cliente.getNumeroDeCasa().trim().isEmpty() || !cliente.getNumeroDeCasa().matches("\\d{1,10}")) {
            Utilidades.mostrarAlertaSimple("Error", "El número de casa debe contener solo números (máximo 10 dígitos).", Alert.AlertType.ERROR);
            return false;
        }

        if (cliente.getColonia() == null || cliente.getColonia().trim().isEmpty() || cliente.getColonia().length() > 100) {
            Utilidades.mostrarAlertaSimple("Error", "La colonia es obligatoria.", Alert.AlertType.ERROR);
            return false;
        }

        if (cliente.getCp() == null || !cliente.getCp().matches("\\d{5}")) {
            Utilidades.mostrarAlertaSimple("Error", "El código postal debe tener exactamente 5 dígitos.", Alert.AlertType.ERROR);
            return false;
        }

        if (cliente.getNombreCiudad() == null || cliente.getNombreCiudad().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Error", "Debe seleccionar una ciudad.", Alert.AlertType.ERROR);
            return false;
        }

        if (cliente.getNombreEstado() == null || cliente.getNombreEstado().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Error", "Debe seleccionar un estado.", Alert.AlertType.ERROR);
            return false;
        }

        if (cliente.getTelefono() == null || !cliente.getTelefono().matches("\\d{10}")) {
            Utilidades.mostrarAlertaSimple("Error", "El teléfono debe tener exactamente 10 dígitos.", Alert.AlertType.ERROR);
            return false;
        }

        if (cliente.getCorreo() == null || !cliente.getCorreo().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            Utilidades.mostrarAlertaSimple("Error", "Debe ingresar un correo válido.", Alert.AlertType.ERROR);
            return false;
        }
        
        return true;
    }


    private void guadarDatosDelCliente(Cliente cliente) {
        Mensaje msj = ClienteImp.agregarCliente(cliente);

        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Registro Exitoso", "Cliente agregado correctamente.", Alert.AlertType.INFORMATION);
            observador.notificarOperacion("Guardar", cliente.getNombre());
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error", "No se pudo guardar el cliente.", Alert.AlertType.ERROR);
        }
    }

    private void editarDatosDelCliente(Cliente cliente) {
        Mensaje msj = ClienteImp.editarCliente(cliente);

        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Edición Exitosa", "Cliente editado correctamente.", Alert.AlertType.INFORMATION);
            observador.notificarOperacion("Editado", cliente.getNombre());
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error", "No se pudo editar el cliente.", Alert.AlertType.ERROR);
        }
    }
    
    private void cerrarVentana() {
        Stage base = (Stage) tfNombre.getScene().getWindow();
        base.close();
    }  
}
