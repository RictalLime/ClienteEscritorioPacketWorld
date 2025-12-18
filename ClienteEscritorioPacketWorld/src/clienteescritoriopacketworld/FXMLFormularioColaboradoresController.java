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
 * @author Tron7
 */
public class FXMLFormularioColaboradoresController implements Initializable {
    
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCurp;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfNumeroPersonal;
    @FXML
    private PasswordField pfContrasena;
    @FXML
    private ImageView imgFotografia;
    @FXML
    private TextField tfNoLicencia;
    @FXML
    private Button btnGuardar;
    @FXML
    private ComboBox<Rol> cbRol;
    @FXML
    private ComboBox<Sucursal> cbSucursal;
    private ObservableList<Sucursal> sucursales;


    private NotificadoOperacion observador; 
    private Colaborador colaboradorEditado;
    private boolean modoEdicion = false;
    private File foto;
    private boolean quisoAgregarUnaFoto = false;
    
    ObservableList<Rol> tiposDeColaboradores;
    @FXML
    private Label lNoLicencia;
    @FXML
    private Button btnSeleccionarFoto;
    @FXML
    private Label lFoto;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfNoLicencia.setVisible(false);
        lNoLicencia.setVisible(false);
        lFoto.setVisible(false);
        imgFotografia.setVisible(false);
        btnSeleccionarFoto.setVisible(false);
        cargarTiposDeUsuarios();
        cargarSucursales(); 
    }    
    
    public void initializeValores(NotificadoOperacion observador, Colaborador colaboradorEditado){
        this.colaboradorEditado = colaboradorEditado;
        this.observador = observador;
        if(colaboradorEditado!=null){
            modoEdicion = true;
            llenarcampos();
            btnGuardar.setText("Editar");
            cbRol.setDisable(true);
            tfNumeroPersonal.setEditable(false);
            lFoto.setVisible(true);
            imgFotografia.setVisible(true);
            btnSeleccionarFoto.setVisible(true);
            if(colaboradorEditado.getIdRol()==3){
                tfNoLicencia.setVisible(true);
                lNoLicencia.setVisible(true);
            }
        }
    }

    @FXML
    private void regresarPrincipal(MouseEvent event) {
        cerrarVentana();
    }


    @FXML
    private void onClickGuardar(ActionEvent event) {
        Colaborador colaborador = new Colaborador();
        colaborador.setNombre(tfNombre.getText());
        colaborador.setApellidoPaterno(tfApellidoPaterno.getText());
        colaborador.setApellidoMaterno(tfApellidoMaterno.getText());
        colaborador.setCurp(tfCurp.getText());
        colaborador.setCorreo(tfCorreo.getText());
        colaborador.setNoPersonal(tfNumeroPersonal.getText());
        colaborador.setContrasena(pfContrasena.getText());
        colaborador.setIdRol((cbRol.getSelectionModel().getSelectedItem() != null)?
                cbRol.getSelectionModel().getSelectedItem().getIdRol(): -1
        );
        colaborador.setNumeroDeLicencia(tfNoLicencia.getText());
        Sucursal sucursalSel = cbSucursal.getValue();
        colaborador.setIdSucursal(sucursalSel != null ? sucursalSel.getIdSucursal() : null);
        if(validarCampos(colaborador)){
            if(!modoEdicion){
                guardarDatosColaborador(colaborador);
            }else{
                if(quisoAgregarUnaFoto){
                    try {
                        byte[] fotoBytes = convertirImagenABytes(foto);
                        colaborador.setFoto(fotoBytes);
                    } catch (Exception ex) {
                        Utilidades.mostrarAlertaSimple("Validación", "Debes seleccionar una foto", Alert.AlertType.WARNING);
                   }
                }else{
                    colaborador.setFoto(colaboradorEditado.getFoto());
                }
                colaborador.setIdColaborador(colaboradorEditado.getIdColaborador());
                editarDatosColaborador(colaborador);
            }
            
        }
    }
    
    private boolean validarCampos(Colaborador colaborador) {
        if (colaborador.getNombre() == null || colaborador.getNombre().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Nombre es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (colaborador.getApellidoPaterno() == null || colaborador.getApellidoPaterno().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Apellido Paterno es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (colaborador.getApellidoMaterno() != null && colaborador.getApellidoMaterno().trim().length() > 50) {
            Utilidades.mostrarAlertaSimple("Validación", "El Apellido Materno es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (colaborador.getCurp() == null || colaborador.getCurp().length() != 18) {
            Utilidades.mostrarAlertaSimple("Validación", "La CURP debe tener exactamente 18 caracteres.", Alert.AlertType.WARNING);
            return false;
        }
        if (colaborador.getCorreo() == null || !colaborador.getCorreo().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            Utilidades.mostrarAlertaSimple("Validación", "El correo electrónico no tiene un formato válido.", Alert.AlertType.WARNING);
            return false;
        }
        if (colaborador.getNoPersonal() == null || colaborador.getNoPersonal().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Validación", "El campo Número Personal es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }
        if (colaborador.getContrasena() == null || colaborador.getContrasena().length() < 6) {
            Utilidades.mostrarAlertaSimple("Validación", "La contraseña debe tener al menos 6 caracteres.", Alert.AlertType.WARNING);
            return false;
        }
        if (colaborador.getIdRol() == null || colaborador.getIdRol() <= 0) {
            Utilidades.mostrarAlertaSimple("Validación", "Debe seleccionar un rol válido.", Alert.AlertType.WARNING);
            return false;
        }
        if (colaborador.getIdRol() == 3 && (colaborador.getNumeroDeLicencia() == null || colaborador.getNumeroDeLicencia().trim().isEmpty())) {
            Utilidades.mostrarAlertaSimple("Validación", "El número de licencia es obligatorio para este rol.", Alert.AlertType.WARNING);
            return false;
        }
        if (colaborador.getIdSucursal() == null || colaborador.getIdSucursal() <= 0) {
            Utilidades.mostrarAlertaSimple("Validación", "Debe seleccionar una sucursal.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    
    private void guardarDatosColaborador(Colaborador colaborador){
        Mensaje msj = ColaboradorImp.registrarColaborador(colaborador);
        if(!msj.isError()){
            Utilidades.mostrarAlertaSimple("Registro Exitoso", "Colaborador: " + colaborador.getNombre()+" Agregado", Alert.AlertType.INFORMATION);
            observador.notificarOperacion("Guardar", colaborador.getNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
            colaborador = null;
        }
    }
    private void cerrarVentana(){
        Stage base = (Stage) tfNombre.getScene().getWindow();
        base.close();
    }
    
    private void cargarTiposDeUsuarios(){
        tiposDeColaboradores = FXCollections.observableArrayList();
        tiposDeColaboradores.addAll(RolImp.obtenerRoles());  
        cbRol.setItems(tiposDeColaboradores);
    }
    
    
    private void editarDatosColaborador(Colaborador colaborador){
        Mensaje msj = ColaboradorImp.editarColaborador(colaborador);
        Mensaje fotoEditada = ColaboradorImp.subirFoto(colaborador.getIdColaborador(), colaborador.getFoto());
        if(!msj.isError()){
            Utilidades.mostrarAlertaSimple("Edición", "Colaborador: " +colaborador.getNombre()+ " Editado" , Alert.AlertType.INFORMATION);
            observador.notificarOperacion("Edición", colaborador.getNombre());
            cerrarVentana();
        }else{
            colaborador = null;
            Utilidades.mostrarAlertaSimple("Error", "No se pudo editar el colaborador", Alert.AlertType.ERROR);
        }
    }
    
    
    private void llenarcampos() {
        tfNombre.setText(colaboradorEditado.getNombre());
        tfApellidoMaterno.setText(colaboradorEditado.getApellidoMaterno());
        tfApellidoPaterno.setText(colaboradorEditado.getApellidoPaterno());
        pfContrasena.setText(colaboradorEditado.getContrasena());
        tfCorreo.setText(colaboradorEditado.getCorreo());
        tfCurp.setText(colaboradorEditado.getCurp());
        tfNumeroPersonal.setText(colaboradorEditado.getNoPersonal());
        tfNumeroPersonal.setEditable(false);
        int posicion = buscarIdRol(colaboradorEditado.getIdRol());
        cbRol.getSelectionModel().select(posicion);
        tfNoLicencia.setText(colaboradorEditado.getNumeroDeLicencia());
        colaboradorEditado.setFoto(ColaboradorImp.obtenerFoto(colaboradorEditado.getIdColaborador()));
            if(colaboradorEditado.getFoto()!=null){
                mostrarFotografia(colaboradorEditado.getFoto());
        }
    }
    private int buscarIdRol(int idRol){
        for(int i=0; i<tiposDeColaboradores.size();i++){
            if(tiposDeColaboradores.get(i).getIdRol() == idRol){
                return i;
            }
        }
        return -1;
    }

    @FXML
    private void oyenteDelRol(ActionEvent event) {
        int rol = cbRol.getSelectionModel().getSelectedItem().getIdRol();
        if(rol == 3){
            lNoLicencia.setVisible(true);
            tfNoLicencia.setVisible(true);
        }else{
            lNoLicencia.setVisible(false);
            tfNoLicencia.setVisible(false);
        }
    }
    public void mostrarFotografia(byte[] fotoBytes) {
        if (fotoBytes != null) {
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(fotoBytes);

                Image imagen = new Image(inputStream);
                imgFotografia.setImage(imagen);
            } catch (Exception e) {
                System.err.println("Error al cargar la imagen: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No se recibió fotografía para mostrar.");
        }
    }

    @FXML
    private void selecionarFoto(ActionEvent event) {
        quisoAgregarUnaFoto = true;
        FileChooser fileChooser = new FileChooser();
        
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg")
        );

        foto = fileChooser.showOpenDialog(btnSeleccionarFoto.getScene().getWindow());

        if (foto != null) {
            try {
                Image imagen = new Image(new FileInputStream(foto));
                imgFotografia.setImage(imagen);

            } catch (IOException e) {
                System.err.println("Error al cargar la imagen: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    
    private byte[] convertirImagenABytes(File archivo) throws IOException {
        try (FileInputStream fis = new FileInputStream(archivo);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[16 * 1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }    
    
    private void cargarSucursales() {
        List<Sucursal> listaAPI = SucursalImp.obtenerSucursales();
        sucursales = FXCollections.observableArrayList();
        sucursales.addAll(listaAPI);
        cbSucursal.setItems(sucursales);
    }

}
