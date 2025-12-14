/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.dominio;

import clienteescritoriopacketworld.conexion.ConexionAPI;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.pojo.RespuestaHTTP;
import clienteescritoriopacketworld.pojo.Sucursal;
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
public class SucursalImp {
    public static List<Sucursal> obtenerSucursales(){
        List<Sucursal> sucursales = null;
        String url = Constantes.URL_WS + "sucursal/obtenerSucursales";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if (respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                //enviar una lista.
                Type tipoListaSucursal = new TypeToken<List<Sucursal>>(){}.getType();
                sucursales = gson.fromJson(respuesta.getContenido(), tipoListaSucursal);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sucursales;
    }
    
    public static Mensaje registrarSucursal(Sucursal sucursal){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "sucursal/registrar";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(sucursal);
            RespuestaHTTP respuesta = ConexionAPI.peticionBody(url,Constantes.PETICION_POST, parametros, Constantes.APPLICATION_JSON);
            if(respuesta.getCodigo()== HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj;
    }
    
    public static Mensaje editarSucursal(Sucursal sucursal){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "sucursal/editar";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(sucursal);
            RespuestaHTTP respuesta = ConexionAPI.peticionBody(url, "PUT", parametros, Constantes.APPLICATION_JSON);
            if(respuesta.getCodigo()== HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        
        return msj;
    }
    
    public static Mensaje actualizarEstatusSucursal(Integer idSucursal){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "sucursal/actualizarEstatus/"+idSucursal;
        Gson gson = new Gson();
        try {
            //String parametros = gson.toJson(sucursal);
            RespuestaHTTP respuesta = ConexionAPI.peticionSinBody(url, Constantes.PETICION_PUT);
            if(respuesta.getCodigo()== HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        
        return msj;
    }
}
