/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.dominio.IniciarSesionImp;
import clienteescritoriopacketworld.dto.RSAutenticacionLogin;
import clienteescritoriopacketworld.pojo.Colaborador;
import clienteescritoriopacketworld.utilidad.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tron7
 */
public class FXMLInicioSesionController implements Initializable {
    
    @FXML
    private TextField tfNumeroPersonal;
    @FXML
    private PasswordField pfContrasenia;
    @FXML
    private Label errorNumeroPersonal;
    @FXML
    private Label errorContraseña;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private void irPantallaPrincipal(Colaborador colaborador){
        try {
            Stage escenarioBase = (Stage) tfNumeroPersonal.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPrincipal.fxml"));
            Parent principal = loader.load() ;
            
            FXMLPrincipalController controlador = loader.getController();
            controlador.setColaborador(colaborador);
            Scene escenaPrincipal = new Scene(principal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Packet World");
            escenarioBase.show();
        } catch (IOException ex) {
           Utilidades.mostrarAlertaSimple("Error", "No podemos ir a la pantalla principal :(", Alert.AlertType.ERROR);
        }   
    }
    
    
    private boolean validarCampos(String noPersonal, String contrasena){
        boolean camposValidos = true;
        errorNumeroPersonal.setText("");
        errorContraseña.setText("");

        if(noPersonal.isEmpty()){
            camposValidos = false;
            errorNumeroPersonal.setText("Numero Personal boligatorio");
        }
        if(contrasena.isEmpty()){
            camposValidos = false;
            errorContraseña.setText("Contraseña obligatoria");
        }
        return camposValidos;
    }
    
    @FXML
    private void btnIniciarSesion(ActionEvent event) {
         String noPersonal = tfNumeroPersonal.getText();
         String contrasena = pfContrasenia.getText();
        
        if(validarCampos(noPersonal, contrasena)){
            verificarCredenciales(noPersonal, contrasena);
        } 
    }
    private void verificarCredenciales(String noPersonal, String password){
        RSAutenticacionLogin respuesta = IniciarSesionImp.iniciarSesion(noPersonal, password);
        if(!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Bienvenido", "Bienvenido " + respuesta.getColaborador().getNombre(), Alert.AlertType.INFORMATION);
            irPantallaPrincipal(respuesta.getColaborador());
        }else{
            Utilidades.mostrarAlertaSimple("Atención", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
}
