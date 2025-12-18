/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.ClienteImp;
import clienteescritoriopacketworld.dominio.ColaboradorImp;
import clienteescritoriopacketworld.dominio.EnvioImp;
import clienteescritoriopacketworld.dominio.HistorialDeEnvioImp;
import clienteescritoriopacketworld.dominio.CiudadImp;
import clienteescritoriopacketworld.dominio.EstadoImp;
import clienteescritoriopacketworld.dominio.SucursalImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Cliente;
import clienteescritoriopacketworld.pojo.Colaborador;
import clienteescritoriopacketworld.pojo.EstadoDeEnvio;
import clienteescritoriopacketworld.pojo.Envio;
import clienteescritoriopacketworld.pojo.Ciudad;
import clienteescritoriopacketworld.pojo.Estado;
import clienteescritoriopacketworld.pojo.HistorialDeEnvio;
import clienteescritoriopacketworld.pojo.Sucursal;
import clienteescritoriopacketworld.utilidad.Constantes;
import clienteescritoriopacketworld.utilidad.Utilidades;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
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
 * @author Tron7
 */
public class FXMLFormularioEnviosController implements Initializable {

    @FXML 
    private ImageView imgRegresar;
    @FXML 
    private Button btnGuardar;
    @FXML 
    private TextField tfCalle;
    @FXML 
    private TextField tfNumero;
    @FXML 
    private TextField tfColonia;
    @FXML 
    private TextField tfCodigoPostal;
    @FXML 
    private TextField tfNumeroGuia;
    @FXML 
    private TextField tfCostoEnvio;
    @FXML 
    private TextField tfDestino;
    @FXML 
    private ComboBox<Ciudad> cbCiudad;
    @FXML 
    private ComboBox<Estado> cbEstado;
    @FXML 
    private ComboBox<Cliente> cbClientes;
    @FXML 
    private ComboBox<Colaborador> cbConductores;
    @FXML 
    private ComboBox<EstadoDeEnvio> cbEstadoDeEnvio;
    @FXML 
    private Label lEstadoEnvio;
    @FXML 
    private Label lMotivo;
    @FXML 
    private TextField tfMotivo;
    private NotificadoOperacion observador;
    private Envio envioEditado;
    private Colaborador colaboradorLoguiado;
    private boolean modoEdicion = false;
    ObservableList<Cliente> tiposDeClientes;
    ObservableList<Colaborador> tiposDeConductores;
    ObservableList<EstadoDeEnvio> tiposDeEstadosDeEnvio;
    private ObservableList<Estado> estados;
    private ObservableList<Ciudad> ciudades;
    @FXML
    private ComboBox<Sucursal> cbSucursal;
    private ObservableList<Sucursal> sucursales;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarClientes();
        cargarConductores();
        cargarEstadosDeEnvio();
        cargarEstados();
        cargarSucursales();
        cbEstado.setOnAction(event -> cargarCiudades());
        // Ocultar controles de edición
        lEstadoEnvio.setVisible(false);
        cbEstadoDeEnvio.setVisible(false);
        lMotivo.setVisible(false);
        tfMotivo.setVisible(false);
    }
    
    public void initializeValores(NotificadoOperacion observador, Envio envioEditado, Colaborador colaboradorLoguiado) {
        this.envioEditado = envioEditado;
        this.colaboradorLoguiado = colaboradorLoguiado;
        this.observador = observador;

        if (envioEditado != null) {
            modoEdicion = true;

            tfNumeroGuia.setEditable(false);

            lEstadoEnvio.setVisible(true);
            cbEstadoDeEnvio.setVisible(true);
            lMotivo.setVisible(true);
            tfMotivo.setVisible(true);

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
    
    private void cargarSucursales() {
        List<Sucursal> listaAPI = SucursalImp.obtenerSucursales();
        sucursales = FXCollections.observableArrayList();
        sucursales.addAll(listaAPI);
        cbSucursal.setItems(sucursales);
    }
    
    private void llenarCampos() {
        tfDestino.setText(envioEditado.getDestino());
        tfNumeroGuia.setText(envioEditado.getNoGuia());
        tfCalle.setText(envioEditado.getOrigenCalle());
        tfNumero.setText(envioEditado.getOrigenNumero());
        tfCodigoPostal.setText(envioEditado.getOrigenCodigoPostal());
        tfColonia.setText(envioEditado.getOrigenColonia());
        tfCostoEnvio.setText("" + envioEditado.getCostoDeEnvio());
        for (Estado e : cbEstado.getItems()) {
            if (e.getNombre().equals(envioEditado.getOrigenEstado())) {
                cbEstado.setValue(e);
                break;
            }
        }
        cargarCiudades();
        for (Ciudad c : cbCiudad.getItems()) {
            if (c.getNombre().equals(envioEditado.getOrigenCiudad())) {
                cbCiudad.setValue(c);
                break;
            }
        }
        cbClientes.getSelectionModel().select(buscarCliente(envioEditado.getIdCliente()));
        cbConductores.getSelectionModel().select(buscarConductor(envioEditado.getIdColaborador()));
        cbEstadoDeEnvio.getSelectionModel().select(buscarEstadoEnvio(envioEditado.getIdEstadoDeEnvio()));
    }
    
    @FXML
    private void onClickGuardar(ActionEvent event) {
        Envio envio = new Envio();
        envio.setOrigenCalle(tfCalle.getText());
        envio.setOrigenNumero(tfNumero.getText());
        envio.setOrigenColonia(tfColonia.getText());
        envio.setOrigenCodigoPostal(tfCodigoPostal.getText());
        envio.setIdOrigenCiudad(cbCiudad.getValue() != null ? cbCiudad.getValue().getIdCiudad() : null);
        envio.setIdOrigenEstado(cbEstado.getValue() != null ? cbEstado.getValue().getIdEstado() : null);
        envio.setIdDestinoCiudad(cbCiudad.getValue() != null ? cbCiudad.getValue().getIdCiudad() : null);
        envio.setIdDestinoEstado(cbEstado.getValue() != null ? cbEstado.getValue().getIdEstado() : null);
        envio.setNoGuia(tfNumeroGuia.getText());
        
        envio.setDestinoCalle(envio.getOrigenCalle());
        envio.setDestinoNumero(envio.getOrigenNumero());
        envio.setDestinoColonia(envio.getOrigenColonia());
        envio.setDestinoCodigoPostal(envio.getOrigenCodigoPostal());


        try {
            if (tfCostoEnvio.getText().trim().isEmpty()) {
                Utilidades.mostrarAlertaSimple("Error", "El costo del envío es obligatorio.", Alert.AlertType.ERROR);
                return;
            }
            envio.setCostoDeEnvio(Float.parseFloat(tfCostoEnvio.getText()));
        } catch (NumberFormatException e) {
            Utilidades.mostrarAlertaSimple("Error", "El costo del envío debe ser un número válido.", Alert.AlertType.ERROR);
            return;
        }

        envio.setIdCliente(cbClientes.getValue() != null ? cbClientes.getValue().getIdCliente() : -1);
        envio.setIdColaborador(cbConductores.getValue() != null ? cbConductores.getValue().getIdColaborador() : -1);
        envio.setIdEstadoDeEnvio(cbEstadoDeEnvio.getValue() != null ? cbEstadoDeEnvio.getValue().getIdEstadoDeEnvio() : 1);

        envio.setDestino(tfDestino.getText());
        
        Sucursal sucursalSel = cbSucursal.getValue();
        envio.setIdSucursal(sucursalSel != null ? sucursalSel.getIdSucursal() : null);

        if (validarCampos(envio)) {
            if (!modoEdicion) {
                guardarDatosEnvio(envio);
            } else {
                envio.setIdEnvio(envioEditado.getIdEnvio());
                editarDatosEnvio(envio);
            }
        }
    }
    
    private void cerrarVentana() {
        Stage base = (Stage) tfCalle.getScene().getWindow();
        base.close();
    }


    @FXML
    private void irPantallaPrincipal(MouseEvent event) {
        cerrarVentana();
    }

    private void cargarClientes() {
        tiposDeClientes = FXCollections.observableArrayList();
        tiposDeClientes.addAll(ClienteImp.obtenerClientes());
        cbClientes.setItems(tiposDeClientes);
    }

    private void cargarConductores() {
        tiposDeConductores = FXCollections.observableArrayList();
        tiposDeConductores.addAll(ColaboradorImp.obtenerConductores());
        cbConductores.setItems(tiposDeConductores);
    }

    private void cargarEstadosDeEnvio() {
        tiposDeEstadosDeEnvio = FXCollections.observableArrayList();
        tiposDeEstadosDeEnvio.addAll(EnvioImp.obtenerEstadosDeEnvios());
        cbEstadoDeEnvio.setItems(tiposDeEstadosDeEnvio);
    }

    private int buscarCliente(Integer idCliente) {
        for (int i = 0; i < tiposDeClientes.size(); i++) {
            if (tiposDeClientes.get(i).getIdCliente() == idCliente) return i;
        }
        return -1;
    }

    private int buscarConductor(Integer idColaborador) {
        for (int i = 0; i < tiposDeConductores.size(); i++) {
            if (tiposDeConductores.get(i).getIdColaborador() == idColaborador) return i;
        }
        return -1;
    }

    private int buscarEstadoEnvio(Integer idEstadoDeEnvio) {
        for (int i = 0; i < tiposDeEstadosDeEnvio.size(); i++) {
            if (tiposDeEstadosDeEnvio.get(i).getIdEstadoDeEnvio() == idEstadoDeEnvio) return i;
        }
        return -1;
    }

    private boolean validarCampos(Envio envio) {

        if (envio.getOrigenCalle() == null || envio.getOrigenCalle().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Calle es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (envio.getOrigenNumero() == null || envio.getOrigenNumero().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Número es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (envio.getOrigenColonia() == null || envio.getOrigenColonia().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Colonia es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (envio.getOrigenCodigoPostal() == null || envio.getOrigenCodigoPostal().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Código Postal es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (envio.getIdOrigenCiudad() == null || envio.getIdOrigenCiudad() <= 0) {
            Utilidades.mostrarAlertaSimple("Validación", "Debe seleccionar una ciudad.", Alert.AlertType.WARNING);
            return false;
        }
        if (envio.getIdOrigenEstado() == null || envio.getIdOrigenEstado() <= 0) {
            Utilidades.mostrarAlertaSimple("Validación", "Debe seleccionar un estado.", Alert.AlertType.WARNING);
            return false;
        }
        if (envio.getIdDestinoCiudad() == null || envio.getIdDestinoCiudad() <= 0) {
            Utilidades.mostrarAlertaSimple("Validación", "Debe seleccionar una ciudad destino.", Alert.AlertType.WARNING);
            return false;
        }
        if (envio.getIdDestinoEstado() == null || envio.getIdDestinoEstado() <= 0) {
            Utilidades.mostrarAlertaSimple("Validación", "Debe seleccionar un estado destino.", Alert.AlertType.WARNING);
            return false;
        }
        if (envio.getDestino() == null || envio.getDestino().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Destino es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (envio.getCostoDeEnvio() <= 0) {
            Utilidades.mostrarAlertaSimple("Validación", "El costo del envío debe ser mayor a 0.", Alert.AlertType.WARNING);
            return false;
        }
        if (envio.getNoGuia() == null || envio.getNoGuia().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El número de guía es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }

        if ((envio.getIdEstadoDeEnvio() == 4 || envio.getIdEstadoDeEnvio() == 5) &&
            (tfMotivo.getText() == null || tfMotivo.getText().trim().isEmpty())) {

            Utilidades.mostrarAlertaSimple("Validación", "Debe ingresar un motivo para estados 'Cancelado' o 'Detenido'.", Alert.AlertType.WARNING);
            return false;
        }
        if (envio.getIdSucursal() == null || envio.getIdSucursal() <= 0) {
            Utilidades.mostrarAlertaSimple("Validación", "Debe seleccionar una sucursal.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }


    private void guardarDatosEnvio(Envio envio) {
        Mensaje msj = EnvioImp.agregarEnvio(envio);

        if (!msj.isError()) {
            if (!enviarHistorial(envio)) {
                Utilidades.mostrarAlertaSimple("Registro Exitoso", "Envío agregado correctamente.", Alert.AlertType.INFORMATION);
                observador.notificarOperacion("Guardar", envio.getNoGuia());
                cerrarVentana();
            }
        } else {
            Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarDatosEnvio(Envio envio) {
        Mensaje msj = EnvioImp.editarEnvio(envio);

        if (!msj.isError()) {
            if (!enviarHistorial(envio)) {
                Utilidades.mostrarAlertaSimple("Edición Exitosa", "Envío editado correctamente.", Alert.AlertType.INFORMATION);
                observador.notificarOperacion("Edición", envio.getNoGuia());
                cerrarVentana();
            }
        } else {
            Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private boolean enviarHistorial(Envio envio) {

        HistorialDeEnvio historial = new HistorialDeEnvio();

        historial.setIdEstadoDeEnvio(envio.getIdEstadoDeEnvio());
        historial.setIdColaborador(colaboradorLoguiado.getIdColaborador());
        historial.setNoGuia(envio.getNoGuia());
        historial.setMotivo(tfMotivo.getText().isEmpty() ? "S/M" : tfMotivo.getText());
        historial.setTiempoDeCambio(LocalDate.now().toString());

        Mensaje mensaje = HistorialDeEnvioImp.registrarHistorialEnvio(historial);

        return mensaje.isError();
    }
}
