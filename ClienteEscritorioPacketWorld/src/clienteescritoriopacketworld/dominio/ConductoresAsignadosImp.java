/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.dominio;

import clienteescritoriopacketworld.conexion.ConexionAPI;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.pojo.ConductoresAsignados;
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
public class ConductoresAsignadosImp {
    public static List<ConductoresAsignados> obtenerTodos() {
        List<ConductoresAsignados> lista = null;
        String url = Constantes.URL_WS + "conductores-asignados/obtener-todos";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoLista = new TypeToken<List<ConductoresAsignados>>() {}.getType();
                lista = gson.fromJson(respuesta.getContenido(), tipoLista);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lista;
    }


    public static Mensaje registrar(ConductoresAsignados conductor) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS + "conductores-asignados/agregar";
        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(conductor);
            RespuestaHTTP respuesta = ConexionAPI.peticionBody(url,Constantes.PETICION_POST, parametros, Constantes.APPLICATION_JSON);
            if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
                mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            } else {
                mensaje.setError(true);
                mensaje.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            mensaje.setError(true);
            mensaje.setMensaje(e.getMessage());
        }

        return mensaje;
    }

 
    public static Mensaje editar(ConductoresAsignados conductor) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS + "conductores-asignados/editar";
        Gson gson = new Gson();

        try {
            String parametros = gson.toJson(conductor);
            RespuestaHTTP respuesta = ConexionAPI.peticionBody(url, "PUT", parametros, Constantes.APPLICATION_JSON);
            if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
                mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            } else {
                mensaje.setError(true);
                mensaje.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            mensaje.setError(true);
            mensaje.setMensaje(e.getMessage());
        }

        return mensaje;
    }

  
    public static Mensaje eliminar(int idConductoresAsignados) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS + "conductores-asignados/eliminar/" + idConductoresAsignados;
        Gson gson = new Gson();

        try {
            RespuestaHTTP respuesta = ConexionAPI.peticionSinBody(url, Constantes.PETICION_DELETE);
            if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
                mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            } else {
                mensaje.setError(true);
                mensaje.setMensaje(respuesta.getContenido());
            }
        } catch (Exception e) {
            mensaje.setError(true);
            mensaje.setMensaje(e.getMessage());
        }

        return mensaje;
    }
}
