/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.ColaboradorImp;
import clienteescritoriopacketworld.dominio.RolImp;
import clienteescritoriopacketworld.dominio.SucursalImp;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.interfaz.NotificadoOperacion;
import clienteescritoriopacketworld.pojo.Colaborador;
import clienteescritoriopacketworld.pojo.Rol;
import clienteescritoriopacketworld.pojo.Sucursal;
import clienteescritoriopacketworld.utilidad.Utilidades;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author b1nc0
 */
public class FXMLFormularioColaboradoresController implements Initializable {

    @FXML
    private ImageView imgRegresar;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCURP;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfNoPersonal;
    @FXML
    private ComboBox<Rol> cbRol;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private ComboBox<Sucursal> cbSucursal;
    @FXML
    private TextField tfNoLicencia;
    @FXML
    private ImageView imgFotografia;
    @FXML
    private Button btnSeleccionarFoto;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lNoLicencia;

    private NotificadoOperacion observador;
    private Colaborador colaboradorEditado;
    private boolean modoEdicion = false;
    private ObservableList<Rol> roles;
    private ObservableList<Sucursal> sucursales;
    private File foto; 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarRoles();
        // Estado inicial
        cbSucursal.setDisable(true);
        tfNoLicencia.setVisible(false);
        lNoLicencia.setVisible(false);
        
        // Ocultar foto al registrar (inicio)
        imgFotografia.setVisible(false);
        btnSeleccionarFoto.setVisible(false);
    }    

    public void initializeValores(NotificadoOperacion observador, Colaborador colaboradorEditado) {
        this.observador = observador;
        this.colaboradorEditado = colaboradorEditado;
        if (colaboradorEditado != null) {
            modoEdicion = true;
            // Mostrar foto al editar
            imgFotografia.setVisible(true);
            btnSeleccionarFoto.setVisible(true);
            cargarDatosEdicion();
        }
    }
    
    private void cargarRoles(){
        roles = FXCollections.observableArrayList();
        RolImp rolImp = new RolImp();
        List<Rol> lista = rolImp.obtenerRoles();
        if(lista != null){
            roles.addAll(lista);
            cbRol.setItems(roles);
        }
    }
    
    private void cargarDatosEdicion() {
        // Seleccionar rol
        if(colaboradorEditado.getIdRol() != null){
             for(Rol rol : cbRol.getItems()){
                 if(rol.getIdRol().equals(colaboradorEditado.getIdRol())){
                     cbRol.getSelectionModel().select(rol);
                     habilitarCampos(null); // Actualizar visibilidad según rol
                     break;
                 }
             }
        }
        
        tfNombre.setText(colaboradorEditado.getNombre());
        tfApellidoPaterno.setText(colaboradorEditado.getApellidoPaterno());
        tfApellidoMaterno.setText(colaboradorEditado.getApellidoMaterno());
        
        // Cargar No. Personal y bloquear edición
        tfNoPersonal.setText(colaboradorEditado.getNoPersonal());
        tfNoPersonal.setEditable(false);
        
        tfCURP.setText(colaboradorEditado.getCurp());
        tfCorreo.setText(colaboradorEditado.getCorreo());
        
        // Cargar contraseña existente
        tfPassword.setText(colaboradorEditado.getContrasena());
        
        tfNoLicencia.setText(colaboradorEditado.getNumeroDeLicencia());
        
        // La carga de sucursales depende de si ya tenemos un rol seleccionado que habilite el combo
        // Se maneja dentro de habilitarCampos, pero aseguramos la carga correcta aqui
        if (cbRol.getValue() != null) {
             String rol = cbRol.getValue().getNombre();
             if (rol.equalsIgnoreCase("Ejecutivo de tienda") || rol.equalsIgnoreCase("Conductor")) {
                 cargarSucursales();
             }
        }

        // Cargar foto si existe
        if (colaboradorEditado.getFoto() != null && colaboradorEditado.getFoto().length > 0) {
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(colaboradorEditado.getFoto());
                Image img = new Image(bis);
                imgFotografia.setImage(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    private void regresarPrincipal(MouseEvent event) {
        cerrarVentana();
    }

    @FXML
    private void habilitarCampos(ActionEvent event) {
        Rol rolSeleccionado = cbRol.getValue();
        
        if (rolSeleccionado != null) {
            String nombreRol = rolSeleccionado.getNombre() != null ? rolSeleccionado.getNombre().trim() : "";
            
            boolean esConductor = nombreRol.equalsIgnoreCase("Conductor");
            boolean esEjecutivo = nombreRol.equalsIgnoreCase("Ejecutivo de tienda");
            
            // 1. Logica para Sucursal (Activa para Conductor y Ejecutivo)
            if (esConductor || esEjecutivo) {
                cbSucursal.setDisable(false);
                cargarSucursales();
            } else {
                cbSucursal.setDisable(true);
                cbSucursal.getSelectionModel().clearSelection();
                cbSucursal.setItems(null); // Limpiar items si se deshabilita
            }
            
            // 2. Logica para Licencia (Solo visible para Conductor)
            if (esConductor) {
                tfNoLicencia.setVisible(true);
                lNoLicencia.setVisible(true);
            } else {
                tfNoLicencia.setVisible(false);
                lNoLicencia.setVisible(false);
                tfNoLicencia.clear();
            }
        }
    }

    @FXML
    private void seleccionarFoto(ActionEvent event) {
        FileChooser dialogoImagen = new FileChooser();
        dialogoImagen.setTitle("Selecciona una foto");
        FileChooser.ExtensionFilter filtroImg = new FileChooser.ExtensionFilter("Archivos de imagen (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        dialogoImagen.getExtensionFilters().add(filtroImg);
        Stage escenarioActual = (Stage) tfNombre.getScene().getWindow();
        foto = dialogoImagen.showOpenDialog(escenarioActual);
        
        if(foto != null){
            try {
                FileInputStream is = new FileInputStream(foto);
                Image image = new Image(is);
                imgFotografia.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private byte[] archivoAByte(File archivo) throws IOException {
        try (FileInputStream fis = new FileInputStream(archivo);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }    
        
    private void cargarSucursales() {
        SucursalImp sucursalImp = new SucursalImp();
        List<Sucursal> listaAPI = sucursalImp.obtenerSucursales();
        ObservableList<Sucursal> activas = FXCollections.observableArrayList();
        
        if (listaAPI != null) {
            for (Sucursal s : listaAPI) {
                if (s.getEstatus() != null && s.getEstatus().equalsIgnoreCase("activa")) {
                    activas.add(s);
                }
            }
        }
        
        // Logica para mantener sucursal seleccionada en edicion incluso si esta inactiva
        if (modoEdicion && colaboradorEditado != null && colaboradorEditado.getIdSucursal() != null) {
            boolean yaIncluida = activas.stream()
                    .anyMatch(s -> s.getIdSucursal().equals(colaboradorEditado.getIdSucursal()));
            if (!yaIncluida && listaAPI != null) {
                listaAPI.stream()
                        .filter(s -> s.getIdSucursal().equals(colaboradorEditado.getIdSucursal()))
                        .findFirst()
                        .ifPresent(activas::add);
            }
        }

        sucursales = activas;
        cbSucursal.setItems(sucursales);
        
        if (modoEdicion && colaboradorEditado != null && colaboradorEditado.getIdSucursal() != null) {
            for (Sucursal s : cbSucursal.getItems()) {
                if (s.getIdSucursal().equals(colaboradorEditado.getIdSucursal())) {
                    cbSucursal.getSelectionModel().select(s);
                    break;
                }
            }
        }
    }

    @FXML
    private void guardarDatos(ActionEvent event) {
        String errores = validarCampos();
        if(!errores.isEmpty()){
            Utilidades.mostrarAlertaSimple("Campos Inválidos", errores, Alert.AlertType.WARNING);
            return;
        }

        Colaborador colaborador = new Colaborador();
        // Datos básicos
        colaborador.setNombre(tfNombre.getText());
        colaborador.setApellidoPaterno(tfApellidoPaterno.getText());
        colaborador.setApellidoMaterno(tfApellidoMaterno.getText());
        colaborador.setCurp(tfCURP.getText().toUpperCase()); // Convertir a mayusculas
        colaborador.setCorreo(tfCorreo.getText());
        colaborador.setNoPersonal(tfNoPersonal.getText());
        colaborador.setContrasena(tfPassword.getText());
        
        // Rol
        if(cbRol.getValue() != null){
            colaborador.setIdRol(cbRol.getValue().getIdRol());
        }
        
        // Sucursal (si aplica)
        if (!cbSucursal.isDisabled() && cbSucursal.getValue() != null) {
            colaborador.setIdSucursal(cbSucursal.getValue().getIdSucursal());
        } else {
            colaborador.setIdSucursal(null);
        }
        
        // No. Licencia (si aplica)
        if (tfNoLicencia.isVisible()) {
            colaborador.setNumeroDeLicencia(tfNoLicencia.getText());
        } else {
            colaborador.setNumeroDeLicencia(null);
        }

        // Fotografía
        try {
            if (foto != null) {
                colaborador.setFoto(archivoAByte(foto));
            } else if (modoEdicion) {
                colaborador.setFoto(colaboradorEditado.getFoto());
            }
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Error Foto", "Error al procesar la fotografía", Alert.AlertType.ERROR);
            return;
        }

        ColaboradorImp colaboradorImp = new ColaboradorImp();

        if (modoEdicion) {
            colaborador.setIdColaborador(colaboradorEditado.getIdColaborador());
            Mensaje msj = colaboradorImp.editarColaborador(colaborador);
            if (!msj.isError()) {
                Utilidades.mostrarAlertaSimple("Éxito", msj.getMensaje(), Alert.AlertType.INFORMATION);
                if (observador != null) observador.notificarOperacion("Actualizar", colaborador.getNombre());
                cerrarVentana();
            } else {
                Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        } else {
            Mensaje msj = colaboradorImp.registrarColaborador(colaborador);
            if (!msj.isError()) {
                Utilidades.mostrarAlertaSimple("Éxito", msj.getMensaje(), Alert.AlertType.INFORMATION);
                if (observador != null) observador.notificarOperacion("Registrar", colaborador.getNombre());
                cerrarVentana();
            } else {
                Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        }
    }
    
    private String validarCampos(){
        StringBuilder mensaje = new StringBuilder();
        
        if(tfNombre.getText().isEmpty() || tfApellidoPaterno.getText().isEmpty() || 
           tfApellidoMaterno.getText().isEmpty() || tfCURP.getText().isEmpty() || 
           tfCorreo.getText().isEmpty() || tfNoPersonal.getText().isEmpty() || 
           tfPassword.getText().isEmpty()){
            mensaje.append("Todos los campos básicos son obligatorios.\n");
        }
        
        if(cbRol.getValue() == null){
            mensaje.append("Debes seleccionar un Rol.\n");
        } else {
            String rol = cbRol.getValue().getNombre();
            
            boolean esConductor = rol != null && rol.trim().equalsIgnoreCase("Conductor");
            boolean esEjecutivo = rol != null && rol.trim().equalsIgnoreCase("Ejecutivo de tienda");
            
            if((esEjecutivo || esConductor) && cbSucursal.getValue() == null){
                mensaje.append("Para este rol es obligatorio seleccionar una Sucursal.\n");
            }
            if(esConductor && tfNoLicencia.getText().isEmpty()){
                mensaje.append("El número de licencia es obligatorio para conductores.\n");
            }
        }
        
        // Validación de CURP Realista usando Regex
        if (!tfCURP.getText().isEmpty()) {
            if (!esCurpValida(tfCURP.getText())) {
                mensaje.append("El formato de la CURP es inválido. Verifique caracteres, fecha o entidad.\n");
            }
        }

        return mensaje.toString();
    }

    /**
     * Valida la CURP usando una expresión regular estándar.
     */
    private boolean esCurpValida(String curp) {
        String regex = "^[A-Z]{4}\\d{6}[HM]{1}(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)[B-DF-HJ-NP-TV-Z]{3}[0-9A-Z]{1}\\d{1}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(curp.toUpperCase());
        return matcher.matches();
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
    
}