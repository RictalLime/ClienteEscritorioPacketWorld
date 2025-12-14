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
import java.util.List;

/**
 *
 * @author Tron7
 */
public class EstadoImp {
    public static List<Estado> obtenerTodosLosEstados() {
        List<Estado> estados = null;
        String url = Constantes.URL_WS + "estado/todos";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                Type tipoLista = new TypeToken<List<Estado>>(){}.getType();
                estados = gson.fromJson(respuesta.getContenido(), tipoLista);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return estados;
    }
    
    public static Estado obtenerEstadoPorId(Integer idEstado) {
        Estado estado = null;
        String url = Constantes.URL_WS + "estado/id/" + idEstado;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                estado = gson.fromJson(respuesta.getContenido(), Estado.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return estado;
    }
}
