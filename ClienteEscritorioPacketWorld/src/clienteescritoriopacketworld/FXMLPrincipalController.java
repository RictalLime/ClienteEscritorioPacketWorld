/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritoriopacketworld;

import clienteescritoriopacketworld.pojo.Colaborador;
import clienteescritoriopacketworld.utilidad.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tron7
 */
public class FXMLPrincipalController implements Initializable {
    
    @FXML
    private ImageView imgCerrarSesion;
    @FXML
    private ImageView moduloPaquetes;
    @FXML
    private ImageView moduloUnidades;
    @FXML
    private ImageView moduloClientes;
    @FXML
    private ImageView moduloColaboradores;
    @FXML
    private ImageView moduloEnvios;
    
    private Colaborador colaborador;
    @FXML
    private ImageView moduloAsignaciones;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
    
    private void moduloColaboradores(){
        try {
            Stage escenarioBase = (Stage) moduloColaboradores.getScene().getWindow();
                    
            Parent principal = FXMLLoader.load(getClass().getResource("FXMLModuloColaboradores.fxml"));
            Scene escenaPrincipal = new Scene(principal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Packet World Colaboradores");
            escenarioBase.show();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "No podemos ir al modulo Colaboradores :(", Alert.AlertType.ERROR);
        }
    }
}
