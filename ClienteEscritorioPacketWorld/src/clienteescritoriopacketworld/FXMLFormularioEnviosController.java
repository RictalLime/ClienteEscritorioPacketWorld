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
    
    //Origen
    @FXML 
    private TextField tfNumeroGuia;
    @FXML 
    private TextField tfCostoEnvio;
    @FXML 
    private ComboBox<Colaborador> cbConductores;
    ObservableList<Colaborador> tiposDeConductores;
    @FXML 
    private Label lEstadoEnvio;
    @FXML 
    private ComboBox<EstadoDeEnvio> cbEstadoDeEnvio;
    ObservableList<EstadoDeEnvio> tiposDeEstadosDeEnvio;
    @FXML 
    private Label lMotivo;
    @FXML 
    private TextField tfMotivo;
    @FXML
    private ComboBox<Sucursal> cbSucursal;
    private ObservableList<Sucursal> sucursales;
    
    //Destino
    @FXML 
    private TextField tfCalle;
    @FXML 
    private TextField tfNumero;
    @FXML 
    private TextField tfColonia;
    @FXML 
    private TextField tfCodigoPostal;
    @FXML 
    private ComboBox<Ciudad> cbCiudad;
    private ObservableList<Ciudad> ciudades;
    @FXML 
    private ComboBox<Estado> cbEstado;
    private ObservableList<Estado> estados;
    @FXML 
    private ComboBox<Cliente> cbClientes;
    ObservableList<Cliente> tiposDeClientes;
    
    private NotificadoOperacion observador;
    private Envio envioEditado;
    private Colaborador colaboradorLoguiado;
    private boolean modoEdicion = false;
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
            btnGuardar.setText("Editar");

            tfNumeroGuia.setText(envioEditado.getNoGuia());
            tfNumeroGuia.setEditable(false);
            tfCostoEnvio.setEditable(false);

            lEstadoEnvio.setVisible(true);
            cbEstadoDeEnvio.setVisible(true);
            lMotivo.setVisible(true);
            tfMotivo.setVisible(true);

            llenarCampos();
        } else {
            modoEdicion = false;

            tfNumeroGuia.setText(EnvioImp.generarNoGuia());
            tfNumeroGuia.setEditable(false);
            tfCostoEnvio.setEditable(false);

            lEstadoEnvio.setVisible(false);
            cbEstadoDeEnvio.setVisible(false);
            lMotivo.setVisible(false);
            tfMotivo.setVisible(false);
        }
    }

    private void llenarCampos() {
        tfCostoEnvio.setText("" + envioEditado.getCostoDeEnvio());
        tfCalle.setText(envioEditado.getDestinoCalle());
        tfNumero.setText(envioEditado.getDestinoNumero());
        tfColonia.setText(envioEditado.getDestinoColonia());
        tfCodigoPostal.setText(envioEditado.getDestinoCodigoPostal());

        for (Sucursal s : cbSucursal.getItems()) {
            if (s.getIdSucursal().equals(envioEditado.getIdSucursal())) {
                cbSucursal.setValue(s);
                break;
            }
        }
        
        for (Estado e : cbEstado.getItems()) {
            if (e.getIdEstado().equals(envioEditado.getIdDestinoEstado())) {
                cbEstado.setValue(e);
                break;
            }
        }
        
        cargarCiudades();
        for (Ciudad c : cbCiudad.getItems()) {
            if (c.getIdCiudad().equals(envioEditado.getIdDestinoCiudad())) {
                cbCiudad.setValue(c);
                break;
            }
        }
        
        cbClientes.getSelectionModel().select(buscarCliente(envioEditado.getIdCliente()));
        cbConductores.getSelectionModel().select(buscarConductor(envioEditado.getIdColaborador()));
        cbEstadoDeEnvio.getSelectionModel().select(buscarEstadoEnvio(envioEditado.getIdEstadoDeEnvio()));
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
    
    @FXML
    private void onClickGuardar(ActionEvent event) {
        Envio envio = new Envio();

        // ORIGEN
        Sucursal sucursalSel = cbSucursal.getValue();
        if (sucursalSel != null) {
            envio.setOrigenCalle(sucursalSel.getCalle());
            envio.setOrigenNumero(sucursalSel.getNumero());
            envio.setOrigenColonia(sucursalSel.getColonia());
            envio.setOrigenCodigoPostal(sucursalSel.getCodigoPostal());
            envio.setIdOrigenCiudad(sucursalSel.getIdCiudad());
            envio.setIdOrigenEstado(sucursalSel.getIdEstado());
            envio.setIdSucursal(sucursalSel.getIdSucursal());
        }

        // DESTINO
        envio.setDestinoCalle(tfCalle.getText());
        envio.setDestinoNumero(tfNumero.getText());
        envio.setDestinoColonia(tfColonia.getText());
        envio.setDestinoCodigoPostal(tfCodigoPostal.getText());
        envio.setIdDestinoCiudad(cbCiudad.getValue() != null ? cbCiudad.getValue().getIdCiudad() : null);
        envio.setIdDestinoEstado(cbEstado.getValue() != null ? cbEstado.getValue().getIdEstado() : null);

        envio.setNoGuia(tfNumeroGuia.getText());

        List<Envio> enviosExistentes = EnvioImp.obtenerEnviosPorNoGuia(envio.getNoGuia());
        if (!modoEdicion) {
            if (enviosExistentes != null && !enviosExistentes.isEmpty()) {
                Utilidades.mostrarAlertaSimple("Error", "El número de guía ya está registrado. Usa uno diferente.", Alert.AlertType.ERROR);
                return;
            }
        } else {
            if (enviosExistentes != null && !enviosExistentes.isEmpty()) {
                Envio encontrado = enviosExistentes.get(0);
                if (!encontrado.getIdEnvio().equals(envioEditado.getIdEnvio())) {
                    Utilidades.mostrarAlertaSimple("Error", "El número de guía ya pertenece a otro envío.", Alert.AlertType.ERROR);
                    return;
                }
            }
        }

        envio.setIdCliente(cbClientes.getValue() != null ? cbClientes.getValue().getIdCliente() : -1);
        envio.setIdColaborador(cbConductores.getValue() != null ? cbConductores.getValue().getIdColaborador() : -1);
        envio.setIdEstadoDeEnvio(cbEstadoDeEnvio.getValue() != null ? cbEstadoDeEnvio.getValue().getIdEstadoDeEnvio() : 1);

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
            tfCostoEnvio.setText("" + envio.getCostoDeEnvio());
            tfCostoEnvio.setEditable(false);

            Utilidades.mostrarAlertaSimple("Registro Exitoso", 
                "Envío agregado correctamente. Costo: $" + envio.getCostoDeEnvio(), 
                Alert.AlertType.INFORMATION);

            observador.notificarOperacion("Guardar", envio.getNoGuia());
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void editarDatosEnvio(Envio envio) {
        Mensaje msj = EnvioImp.editarEnvio(envio);

        if (!msj.isError()) {
            tfCostoEnvio.setText("" + envio.getCostoDeEnvio());
            tfCostoEnvio.setEditable(false);

            Utilidades.mostrarAlertaSimple("Edición Exitosa", 
                "Envío editado correctamente. Costo: $" + envio.getCostoDeEnvio(), 
                Alert.AlertType.INFORMATION);

            observador.notificarOperacion("Edición", envio.getNoGuia());
            cerrarVentana();
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
