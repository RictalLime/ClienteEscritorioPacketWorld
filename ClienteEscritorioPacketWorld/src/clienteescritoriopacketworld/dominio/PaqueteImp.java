/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.dominio;

import clienteescritoriopacketworld.conexion.ConexionAPI;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.pojo.Paquete;
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
public class PaqueteImp {
    public static List<Paquete> obtenerPaquetes(){
        List<Paquete> paquetes = null;
        String url = Constantes.URL_WS + "paquetes/obtener-paquetes";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoListaPaquete = new TypeToken<List<Paquete>>() {}.getType();
                paquetes = gson.fromJson(respuesta.getContenido(), tipoListaPaquete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return paquetes;
    }

    public static List<Paquete> obtenerPaquetesPorEnvio(int idEnvio){
        List<Paquete> paquetes = null;
        String url = Constantes.URL_WS + "paquetes/obtener-paquetes-envio/" + idEnvio;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoListaPaquete = new TypeToken<List<Paquete>>() {}.getType();
                paquetes = gson.fromJson(respuesta.getContenido(), tipoListaPaquete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return paquetes;
    }
    
    public static List<Paquete> obtenerPaquetesPorNoGuia(String noGuia){
        List<Paquete> paquetes = null;
        String url = Constantes.URL_WS + "paquetes/obtener-paquetes-noguia/" + noGuia;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoListaPaquete = new TypeToken<List<Paquete>>() {}.getType();
                paquetes = gson.fromJson(respuesta.getContenido(), tipoListaPaquete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return paquetes;
    }

    public static Mensaje registrarPaquete(Paquete paquete){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "paquetes/agregar-paquete";
        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(paquete);
            RespuestaHTTP respuesta = ConexionAPI.peticionBody(url,Constantes.PETICION_POST, parametros, Constantes.APPLICATION_JSON);

            if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            } else {
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }

        return msj;
    }

    public static Mensaje editarPaquete(Paquete paquete){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "paquetes/editar-paquete";
        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(paquete);
            RespuestaHTTP respuesta = ConexionAPI.peticionBody(url, "PUT", parametros, Constantes.APPLICATION_JSON);

            if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            } else {
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }

        return msj;
    }

    public static Mensaje eliminarPaquete(int idPaquete){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "paquetes/eliminar-paquete/" + idPaquete;
        Gson gson = new Gson();

        try {
            RespuestaHTTP respuesta = ConexionAPI.peticionSinBody(url, Constantes.PETICION_DELETE);

            if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            } else {
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
