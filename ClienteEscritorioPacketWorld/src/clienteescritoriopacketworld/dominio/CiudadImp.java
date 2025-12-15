/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.dominio;

import clienteescritoriopacketworld.conexion.ConexionAPI;
import clienteescritoriopacketworld.pojo.Ciudad;
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
public class CiudadImp {

    public static HashMap<String, Object> obtenerCiudadesPorIdEstado(Integer idEstado) {
        HashMap<String, Object> respuestaFinal = new LinkedHashMap<>();
        String url = Constantes.URL_WS + "ciudad/por-estado/" + idEstado;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(url);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            try {
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Ciudad>>() {}.getType();
                List<Ciudad> ciudades = gson.fromJson(respuestaAPI.getContenido(), tipoLista);

                respuestaFinal.put(Constantes.KEY_ERROR, false);
                respuestaFinal.put(Constantes.KEY_LISTA, ciudades);

            } catch (Exception e) {
                respuestaFinal.put(Constantes.KEY_ERROR, true);
                respuestaFinal.put(Constantes.KEY_MENSAJE, "Error al procesar la información de ciudades");
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
                            "No se pudo obtener la lista de ciudades. Inténtelo más tarde.");
            }
        }

        return respuestaFinal;
    }


    public static HashMap<String, Object> obtenerCiudadPorId(Integer idCiudad) {
        HashMap<String, Object> respuestaFinal = new LinkedHashMap<>();
        String url = Constantes.URL_WS + "ciudad/id/" + idCiudad;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(url);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            try {
                Gson gson = new Gson();
                Ciudad ciudad = gson.fromJson(respuestaAPI.getContenido(), Ciudad.class);

                respuestaFinal.put(Constantes.KEY_ERROR, false);
                respuestaFinal.put(Constantes.KEY_LISTA, ciudad);

            } catch (Exception e) {
                respuestaFinal.put(Constantes.KEY_ERROR, true);
                respuestaFinal.put(Constantes.KEY_MENSAJE, "Error al procesar la ciudad solicitada");
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
                            "No se pudo obtener la ciudad solicitada. Inténtelo más tarde.");
            }
        }

        return respuestaFinal;
    }
}