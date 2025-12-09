/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.dominio;

import clienteescritoriopacketworld.conexion.ConexionAPI;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.pojo.Envio;
import clienteescritoriopacketworld.pojo.EstadoDeEnvio;
import clienteescritoriopacketworld.pojo.RespuestaHTTP;
import clienteescritoriopacketworld.utilidad.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

/**
 *
 * @author Tron7
 */
public class EnvioImp {
    public static List<Envio>obtenerEnvios(){
        List<Envio> envios = null;
        String url = Constantes.URL_WS+"envio/obtener-envios";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoLista = new TypeToken<List<Envio>>(){}.getType();
                envios = gson.fromJson(respuesta.getContenido(), tipoLista);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return envios;
    }
    
    public static List<Envio>obtenerEnviosPorNoGuia(String noGuia){
        List<Envio> envios = null;
        String url = Constantes.URL_WS+"envio/obtener-envios-por-noguia/"+noGuia;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoLista = new TypeToken<List<Envio>>(){}.getType();
                envios = gson.fromJson(respuesta.getContenido(), tipoLista);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return envios;
    }
    
    public static Mensaje agregarEnvio(Envio envio){
        Mensaje mensaje = null;
        String url = Constantes.URL_WS+"envio/agregar-envio";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(envio);
            RespuestaHTTP respuesta = ConexionAPI.peticionBody(url,Constantes.PETICION_POST, parametros, Constantes.APPLICATION_JSON);
            if(respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
                mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                mensaje.setError(true);
                mensaje.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            mensaje.setError(true);
            mensaje.setMensaje(e.getMessage());
        }
        
        return mensaje;
    }
    public static Mensaje editarEnvio(Envio envio){
        Mensaje mensaje = null;
        String url = Constantes.URL_WS+"envio/editar-envio";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(envio);
            RespuestaHTTP respuesta = ConexionAPI.peticionBody(url, "PUT", parametros, Constantes.APPLICATION_JSON);
            if(respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
                mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                mensaje.setError(true);
                mensaje.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            mensaje.setError(true);
            mensaje.setMensaje(e.getMessage());
        }
        
        return mensaje;
    }
    public static Mensaje eliminarEnvio(Integer idEnvio){
        Mensaje mensaje = null;
        String url = Constantes.URL_WS+"envio/eliminar-envio/"+idEnvio;
        Gson gson = new Gson();
        try {
            RespuestaHTTP respuesta = ConexionAPI.peticionSinBody(url, Constantes.PETICION_DELETE);
            if(respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
                mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                mensaje.setError(true);
                mensaje.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            mensaje.setError(true);
            mensaje.setMensaje(e.getMessage());
        }
        
        return mensaje;
    }
    
     public static List<EstadoDeEnvio>obtenerEstadosDeEnvios(){
        List<EstadoDeEnvio> envios = null;
        String url = Constantes.URL_WS+"envio/obtener-estados-envios";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoLista = new TypeToken<List<EstadoDeEnvio>>(){}.getType();
                envios = gson.fromJson(respuesta.getContenido(), tipoLista);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return envios;
    }
}
