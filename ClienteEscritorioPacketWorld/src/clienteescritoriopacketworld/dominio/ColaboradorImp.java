/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.dominio;

import clienteescritoriopacketworld.conexion.ConexionAPI;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.pojo.Colaborador;
import clienteescritoriopacketworld.pojo.RespuestaHTTP;
import clienteescritoriopacketworld.utilidad.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author Tron7
 */
public class ColaboradorImp {
    public static List<Colaborador> obtenerColaboradores(){
        List<Colaborador> colaboradores = null;
        String url = Constantes.URL_WS+"colaborador/obtener-colaboradores";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if (respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                //enviar una lista.
                Type tipoListaColaborador = new TypeToken<List<Colaborador>>(){}.getType();
                colaboradores = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return colaboradores;
    }
    
    public static List<Colaborador> obtenerColaboradoresNombre(String nombre){
        List<Colaborador> colaboradores = null;
        String url = Constantes.URL_WS+"colaborador/obtener-colaboradores-nombre/"+nombre;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if (respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                //enviar una lista.
                Type tipoListaColaborador = new TypeToken<List<Colaborador>>(){}.getType();
                colaboradores = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return colaboradores;
    }
    
    public static List<Colaborador> obtenerColaboradoresNoPersonal(String noPersonal){
        List<Colaborador> colaboradores = null;
        String url = Constantes.URL_WS+"colaborador/obtener-colaboradores-noPersonal/"+ noPersonal;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if (respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                //enviar una lista.
                Type tipoListaColaborador = new TypeToken<List<Colaborador>>(){}.getType();
                colaboradores = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return colaboradores;
    }
    
    public static List<Colaborador> obtenerColaboradoresRol(Integer rol){
        List<Colaborador> colaboradores = null;
        String url = Constantes.URL_WS+"colaborador/obtener-colaboradores-rol/"+rol;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if (respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                //enviar una lista.
                Type tipoListaColaborador = new TypeToken<List<Colaborador>>(){}.getType();
                colaboradores = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return colaboradores;
    }
    
    public static Mensaje registrarColaborador(Colaborador colaborador){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "colaborador/agregar-colaborador";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(colaborador);
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
    public static Mensaje editarColaborador(Colaborador colaborador){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "colaborador/editar-colaborador";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(colaborador);
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
    public static Mensaje eliminarColaborador(Integer idColaborador){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "colaborador/eliminar-colaborador/"+idColaborador;
        Gson gson = new Gson();
        try {
            RespuestaHTTP respuesta = ConexionAPI.peticionSinBody(url, Constantes.PETICION_DELETE);
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
    public static Mensaje subirFoto(Integer idColaborador, byte[] foto) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "colaborador/subir-foto/" + idColaborador;
        Gson gson = new Gson();
        try {
            // Realizar la petición PUT directamente con los bytes
            RespuestaHTTP respuesta = ConexionAPI.peticionPUTBINARIO(url, foto);

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
    public static byte[] obtenerFoto(Integer idColaborador) {
        byte[] foto = null;
        Colaborador colaborador = null;
        String url = Constantes.URL_WS + "colaborador/obtener-foto/" + idColaborador;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        Gson gson = new Gson();
        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            try {
                colaborador = gson.fromJson(respuesta.getContenido(), Colaborador.class);
                String fotografia = colaborador.getFotografia();
                if (fotografia != null) {
                    fotografia = fotografia.replace("\n", "").replace("\r", "").trim();
                    foto = Base64.getDecoder().decode(fotografia);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return foto;
    }
    
    public static List<Colaborador> obtenerConductores(){
        List<Colaborador> colaboradores = null;
        String url = Constantes.URL_WS+"colaborador/obtener-conductores";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if (respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                //enviar una lista.
                Type tipoListaColaborador = new TypeToken<List<Colaborador>>(){}.getType();
                colaboradores = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return colaboradores;
    }
    public static List<Colaborador> obtenerConductoresSinAsignar(){
        List<Colaborador> colaboradores = null;
        String url = Constantes.URL_WS+"colaborador/obtener-conductores-sin-asignar";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if (respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                //enviar una lista.
                Type tipoListaColaborador = new TypeToken<List<Colaborador>>(){}.getType();
                colaboradores = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return colaboradores;
    }
    
    public static boolean verificarExistencia(Integer idColaborador) {
        // Reutilizamos la URL de obtener foto porque ya sabemos que devuelve 200 si existe y algo distinto si no.
        String url = Constantes.URL_WS + "colaborador/obtener-foto/" + idColaborador;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        // Si el código es HTTP_OK (200), el registro sigue en la base de datos.
        return (respuesta.getCodigo() == HttpURLConnection.HTTP_OK);
    }
}