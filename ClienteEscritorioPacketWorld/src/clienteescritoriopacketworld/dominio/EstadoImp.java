/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.dominio;

import clienteescritoriopacketworld.conexion.ConexionAPI;
import clienteescritoriopacketworld.pojo.Estado;
import clienteescritoriopacketworld.pojo.RespuestaHTTP;
import clienteescritoriopacketworld.utilidad.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Tron7
 */
public class EstadoImp {

    public static HashMap<String, Object> obtenerTodosLosEstados() {
        HashMap<String, Object> respuestaFinal = new LinkedHashMap<>();
        String url = Constantes.URL_WS + "estado/todos";

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(url);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            try {
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Estado>>(){}.getType();
                List<Estado> estados = gson.fromJson(respuestaAPI.getContenido(), tipoLista);

                respuestaFinal.put(Constantes.KEY_ERROR, false);
                respuestaFinal.put(Constantes.KEY_LISTA, estados);

            } catch (Exception e) {
                respuestaFinal.put(Constantes.KEY_ERROR, true);
                respuestaFinal.put(Constantes.KEY_MENSAJE, "Error al procesar la información de estados");
            }

        } else {
            respuestaFinal.put(Constantes.KEY_ERROR, true);

            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuestaFinal.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_URL);
                    break;

                case Constantes.ERROR_PETICION:
                    respuestaFinal.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_PETICION);
                    break;

                default:
                    respuestaFinal.put(Constantes.KEY_MENSAJE,
                            "Lo sentimos, no se pudo obtener la lista de estados. Inténtelo más tarde.");
            }
        }

        return respuestaFinal;
    }

    public static HashMap<String, Object> obtenerEstadoPorId(Integer idEstado) {
        HashMap<String, Object> respuestaFinal = new LinkedHashMap<>();
        String url = Constantes.URL_WS + "estado/id/" + idEstado;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(url);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            try {
                Gson gson = new Gson();
                Estado estado = gson.fromJson(respuestaAPI.getContenido(), Estado.class);

                respuestaFinal.put(Constantes.KEY_ERROR, false);
                respuestaFinal.put(Constantes.KEY_LISTA, estado);

            } catch (Exception e) {
                respuestaFinal.put(Constantes.KEY_ERROR, true);
                respuestaFinal.put(Constantes.KEY_MENSAJE, "Error al procesar el estado solicitado");
            }

        } else {
            respuestaFinal.put(Constantes.KEY_ERROR, true);

            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuestaFinal.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_URL);
                    break;

                case Constantes.ERROR_PETICION:
                    respuestaFinal.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_PETICION);
                    break;

                default:
                    respuestaFinal.put(Constantes.KEY_MENSAJE,
                            "No se pudo obtener el estado solicitado. Inténtelo más tarde.");
            }
        }

        return respuestaFinal;
    }
}