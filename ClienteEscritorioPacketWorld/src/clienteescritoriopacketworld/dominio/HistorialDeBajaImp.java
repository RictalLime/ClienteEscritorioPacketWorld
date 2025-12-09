/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.dominio;

import clienteescritoriopacketworld.conexion.ConexionAPI;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.pojo.HistorialDeBaja;
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
public class HistorialDeBajaImp {
    public static List<HistorialDeBaja> obtenerHistorialesDeBaja() {
        List<HistorialDeBaja> historiales = null;
        String url = Constantes.URL_WS + "historial-baja/obtener-todos";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoLista = new TypeToken<List<HistorialDeBaja>>() {}.getType();
                historiales = gson.fromJson(respuesta.getContenido(), tipoLista);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return historiales;
    }

    public static HistorialDeBaja obtenerHistorialPorIdUnidad(int idUnidad) {
        HistorialDeBaja historial = null;
        String url = Constantes.URL_WS + "historial-baja/obtener-por-idunidad/" + idUnidad;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                historial = gson.fromJson(respuesta.getContenido(), HistorialDeBaja.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return historial;
    }

    public static Mensaje agregarHistorialDeBaja(HistorialDeBaja historial) {
        Mensaje mensaje = null;
        String url = Constantes.URL_WS + "historial-baja/agregar";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(historial);
            RespuestaHTTP respuesta = ConexionAPI.peticionBody(url,Constantes.PETICION_POST, parametros, Constantes.APPLICATION_JSON);
            if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
                mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            } else {
                mensaje = new Mensaje();
                mensaje.setError(true);
                mensaje.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            mensaje = new Mensaje();
            mensaje.setError(true);
            mensaje.setMensaje(e.getMessage());
        }

        return mensaje;
    }

    public static Mensaje editarHistorialDeBaja(HistorialDeBaja historial) {
        Mensaje mensaje = null;
        String url = Constantes.URL_WS + "historial-baja/editar";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(historial);
            RespuestaHTTP respuesta = ConexionAPI.peticionBody(url, "PUT", parametros, Constantes.APPLICATION_JSON);
            if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
                mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            } else {
                mensaje = new Mensaje();
                mensaje.setError(true);
                mensaje.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            mensaje = new Mensaje();
            mensaje.setError(true);
            mensaje.setMensaje(e.getMessage());
        }

        return mensaje;
    }

    public static Mensaje eliminarHistorialDeBaja(int idHistorialDeBaja) {
        Mensaje mensaje = null;
        String url = Constantes.URL_WS + "historial-baja/eliminar/" + idHistorialDeBaja;
        Gson gson = new Gson();
        try {
            RespuestaHTTP respuesta = ConexionAPI.peticionSinBody(url, Constantes.PETICION_DELETE);
            if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
                mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            } else {
                mensaje = new Mensaje();
                mensaje.setError(true);
                mensaje.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            mensaje = new Mensaje();
            mensaje.setError(true);
            mensaje.setMensaje(e.getMessage());
        }

        return mensaje;
    }
}
