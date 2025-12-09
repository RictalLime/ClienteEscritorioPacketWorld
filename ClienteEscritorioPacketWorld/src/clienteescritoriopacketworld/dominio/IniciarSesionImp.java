/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.dominio;

import clienteescritoriopacketworld.conexion.ConexionAPI;
import clienteescritoriopacketworld.dto.RSAutenticacionLogin;
import clienteescritoriopacketworld.pojo.Colaborador;
import clienteescritoriopacketworld.pojo.RespuestaHTTP;
import clienteescritoriopacketworld.utilidad.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

/**
 *
 * @author Tron7
 */
public class IniciarSesionImp {
    public static RSAutenticacionLogin iniciarSesion(String noPersonal, String password){
        RSAutenticacionLogin respuesta = new RSAutenticacionLogin();
        Colaborador colaborador = null;
        String URL = Constantes.URL_WS+"login/iniciar-sesion";
        String parametros = String.format("noPersonal=%s&contrasena=%s", noPersonal,password);
        RespuestaHTTP respuestaWS = ConexionAPI.peticionBody(URL, "POST", parametros, "application/x-www-form-urlencoded");
        if(respuestaWS.getCodigo()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            colaborador = gson.fromJson(respuestaWS.getContenido(), Colaborador.class);
            respuesta.setError(false);
            respuesta.setMensaje("Colaborador Encontrado");
            respuesta.setColaborador(colaborador);
        }else if(respuestaWS.getCodigo()== HttpURLConnection.HTTP_NOT_FOUND){
            respuesta.setError(true);
            respuesta.setMensaje("Error en el servidor Favor de intentarlo más tarde");
        }else{
           respuesta.setError(true);
           respuesta.setMensaje("Numero Personal y/o Contraseña incorrecta");   
        }
        return respuesta;
    }
}
