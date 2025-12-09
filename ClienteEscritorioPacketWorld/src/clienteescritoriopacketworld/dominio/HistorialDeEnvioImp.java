/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.dominio;

import clienteescritoriopacketworld.conexion.ConexionAPI;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.pojo.HistorialDeEnvio;
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
public class HistorialDeEnvioImp {
    public static List<HistorialDeEnvio> obtenerTodos() {
        List<HistorialDeEnvio> historialEnvio = null;
        String url = Constantes.URL_WS + "historial-envio/obtener-todos";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoListaHistorial = new TypeToken<List<HistorialDeEnvio>>() {}.getType();
                historialEnvio = gson.fromJson(respuesta.getContenido(), tipoListaHistorial);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return historialEnvio;
    }

    public static List<HistorialDeEnvio> obtenerHistorialPorNoGuia(String noGuia) {
        List<HistorialDeEnvio> historialEnvio = null;
        String url = Constantes.URL_WS + "historial-envio/obtener-historial/" + noGuia;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoListaHistorial = new TypeToken<List<HistorialDeEnvio>>() {}.getType();
                historialEnvio = gson.fromJson(respuesta.getContenido(), tipoListaHistorial);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return historialEnvio;
    }

    public static Mensaje registrarHistorialEnvio(HistorialDeEnvio historial) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "historial-envio/agregar";
        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(historial);
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

    public static Mensaje editarHistorialEnvio(HistorialDeEnvio historial) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "historial-envio/editar";
        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(historial);
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

    public static Mensaje eliminarHistorialEnvio(int idHistorialDeEnvio) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "historial-envio/eliminar/" + idHistorialDeEnvio;
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
