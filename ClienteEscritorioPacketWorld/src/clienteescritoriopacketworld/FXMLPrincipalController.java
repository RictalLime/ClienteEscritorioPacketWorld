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
import javafx.scene.input.MouseEvent;
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
    @FXML
    private ImageView moduloAsignaciones;
    @FXML
    private ImageView moduloSucursales;
    
    private Colaborador colaborador;
    
    
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
    
    private void moduloUnidades(){
        try{
            Stage escenarioBase = (Stage) moduloUnidades.getScene().getWindow();
            
            Parent principal = FXMLLoader.load(getClass().getResource("FXMLModuloUnidades.fxml"));
            Scene escenaPrincipal = new Scene(principal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Packet World Unidades");
            escenarioBase.show();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Error", "No podemos ir al modulo Unidades", Alert.AlertType.ERROR);
        }
    }
    
    private void moduloClientes(){
        try{
            Stage escenarioBase = (Stage) moduloClientes.getScene().getWindow();
            
            Parent principal = FXMLLoader.load(getClass().getResource("FXMLModuloClientes.fxml"));
            Scene escenaPrincipal = new Scene(principal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Packet World Clientes");
            escenarioBase.show();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Error", "No podemos ir al modulo Clientes", Alert.AlertType.ERROR);
        }
    }
    
    private void moduloEnvios() {
        try {

            Stage escenarioBase = (Stage) moduloEnvios.getScene().getWindow();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLModuloEnvios.fxml"));
            Parent principal = loader.load();

            FXMLModuloEnviosController controlador = loader.getController();
            controlador.inizializar(colaborador);

            Scene escenaPrincipal = new Scene(principal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Packet World Envios");
            escenarioBase.show();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "No podemos ir al módulo Envios", Alert.AlertType.ERROR);
        }
    }
    
    private void moduloPaquetes(){
        try{
            Stage escenarioBase = (Stage) moduloUnidades.getScene().getWindow();
            
            Parent principal = FXMLLoader.load(getClass().getResource("FXMLModuloPaquetes.fxml"));
            Scene escenaPrincipal = new Scene(principal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Packet World Paquetes");
            escenarioBase.show();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Error", "No podemos ir al modulo Paquetes", Alert.AlertType.ERROR);
        }
    }
    
    private void moduloAsignaciones(){
        try{
            Stage escenarioBase = (Stage) moduloUnidades.getScene().getWindow();
            
            Parent principal = FXMLLoader.load(getClass().getResource("FXMLModuloAsignacion.fxml"));
            Scene escenaPrincipal = new Scene(principal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Packet World Asignaciones");
            escenarioBase.show();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Error", "No podemos ir al modulo Asignaciones", Alert.AlertType.ERROR);
        }
    }
    
    private void moduloSucursales(){
        try{
            Stage escenarioBase = (Stage) moduloUnidades.getScene().getWindow();
            
            Parent principal = FXMLLoader.load(getClass().getResource("FXMLModuloSucursal.fxml"));
            Scene escenaPrincipal = new Scene(principal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Packet World Sucursal");
            escenarioBase.show();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Error", "No podemos ir al modulo Sucursales", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void irModuloColaboradores() {
        moduloColaboradores();
    }

    @FXML
    private void irModuloPaquetes(MouseEvent event) {
        moduloPaquetes();
    }

    @FXML
    private void irModuloUnidades(MouseEvent event) {
        moduloUnidades();
    }

    @FXML
    private void irModuloClientes(MouseEvent event) {
        moduloClientes();
    }

    @FXML
    private void irModuloEnvios(MouseEvent event) {
        moduloEnvios();
    }

    @FXML
    private void irModuloAsignaciones(MouseEvent event) {
        moduloAsignaciones();
    }
    
    @FXML
    private void irModuloSucursales(MouseEvent event) {
        moduloSucursales();
    }

    @FXML
    private void cambiarMouse(MouseEvent event) {
        ((ImageView) event.getSource()).setCursor(javafx.scene.Cursor.HAND); 
    }

    @FXML
    private void cerrarSesion(MouseEvent event) {
        try{
            Stage escenarioBase = (Stage) imgCerrarSesion.getScene().getWindow();
            colaborador = null;
            Parent principal = FXMLLoader.load(getClass().getResource("FXMLInicioSesion.fxml"));
            Scene escenaPrincipal = new Scene(principal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Inicio Sesion");
            escenarioBase.show();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Error", "No se puede cerrar sesión", Alert.AlertType.ERROR);
        }
    }
}
