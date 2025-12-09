/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.dominio;

import clienteescritoriopacketworld.conexion.ConexionAPI;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.pojo.EstadoUnidad;
import clienteescritoriopacketworld.pojo.RespuestaHTTP;
import clienteescritoriopacketworld.pojo.TipoUnidad;
import clienteescritoriopacketworld.pojo.Unidad;
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
public class UnidadImp {
    public static List<Unidad> obtenerUnidades(){
        List<Unidad> unidades = null;
        String url = Constantes.URL_WS+"unidad/obtener-unidades";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<Unidad>>(){}.getType();
                unidades = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return unidades;
    }
    
    public static List<Unidad> obtenerUnidadesDisponibles(){
        List<Unidad> unidades = null;
        String url = Constantes.URL_WS+"unidad/obtener-unidades-disponibles";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<Unidad>>(){}.getType();
                unidades = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return unidades;
    }
    public static List<Unidad> obtenerUnidadesPorVin(String vin){
        List<Unidad> unidades = null;
        String url = Constantes.URL_WS+"unidad/obtener-unidad-por-vin/"+vin;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<Unidad>>(){}.getType();
                unidades = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return unidades;
    }
    
    public static List<Unidad> obtenerUnidadesPorMarca(String marca){
        List<Unidad> unidades = null;
        String url = Constantes.URL_WS+"unidad/obtener-unidades-por-marca/"+marca;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<Unidad>>(){}.getType();
                unidades = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return unidades;
    }
    
    public static List<Unidad> obtenerUnidadesPorNii(String nii){
        List<Unidad> unidades = null;
        String url = Constantes.URL_WS+"unidad/obtener-unidades-por-nii/"+nii;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<Unidad>>(){}.getType();
                unidades = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return unidades;
    }
    
    public static Mensaje agregarUnidad(Unidad unidad){
        Mensaje mensaje = null;
        String url = Constantes.URL_WS+"unidad/agregar-unidad";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(unidad);
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
    
    public static Mensaje EditarUnidad(Unidad unidad){
        Mensaje mensaje = null;
        String url = Constantes.URL_WS+"unidad/editar-unidad";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(unidad);
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
    
    public static Mensaje eliminarUnidad(Integer idUnidad){
        Mensaje mensaje = null;
        String url = Constantes.URL_WS+"unidad/eliminar-unidad/"+idUnidad;
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
    public static List<TipoUnidad> obtenerTiposUnidades(){
        List<TipoUnidad> unidades = null;
        String url = Constantes.URL_WS+"unidad/obtener-tipos-unidades";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<TipoUnidad>>(){}.getType();
                unidades = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return unidades;
    }
    public static List<EstadoUnidad> obtenerTiposDeEstados(){
        List<EstadoUnidad> tiposEstado = null;
        String url = Constantes.URL_WS+"unidad/obtener-estados-unidades";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<EstadoUnidad>>(){}.getType();
                tiposEstado = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tiposEstado;
    }
    public static List<Unidad> obtenerUnidadesSinAsignar(){
        List<Unidad> unidades = null;
        String url = Constantes.URL_WS+"unidad/obtener-unidades-sin-asignar";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<Unidad>>(){}.getType();
                unidades = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return unidades;
    }
}
