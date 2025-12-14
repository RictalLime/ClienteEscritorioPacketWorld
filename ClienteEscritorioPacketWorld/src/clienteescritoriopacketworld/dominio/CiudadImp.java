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
import java.util.List;

/**
 *
 * @author Tron7
 */
public class CiudadImp {
    public static List<Ciudad> obtenerCiudadesPorIdEstado(Integer idEstado){
        List<Ciudad> ciudades = null;
        String url = Constantes.URL_WS+"ciudad/por-estado/"+idEstado;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if (respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                //enviar una lista.
                Type tipoLista = new TypeToken<List<Ciudad>>(){}.getType();
                ciudades = gson.fromJson(respuesta.getContenido(), tipoLista);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ciudades;
    }
    
    public static Ciudad obtenerCiudadPorId(Integer idCiudad){
        Ciudad ciudad = null;
        String url = Constantes.URL_WS + "ciudad/id/" + idCiudad;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                ciudad = gson.fromJson(respuesta.getContenido(), Ciudad.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ciudad;
    }
}
