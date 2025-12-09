/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.dominio;

import clienteescritoriopacketworld.conexion.ConexionAPI;
import clienteescritoriopacketworld.dto.Mensaje;
import clienteescritoriopacketworld.pojo.Cliente;
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
public class ClienteImp {
    public static List<Cliente> obtenerClientes(){
        List<Cliente> clientes = null;
        String url = Constantes.URL_WS+"cliente/obtener-clientes";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<Cliente>>(){}.getType();
                clientes = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientes;
    }
    public static List<Cliente> obtenerClientePorNombre(String nombre){
        List<Cliente> clientes = null;
        String url = Constantes.URL_WS+"cliente/obtener-clientes-nombre/"+nombre;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<Cliente>>(){}.getType();
                clientes = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientes;
    }
    public static List<Cliente> obtenerClientePorNumeroDeTelefono(String telefono){
        List<Cliente> clientes = null;
        String url = Constantes.URL_WS+"cliente/obtener-cliente-numero-telefono/"+telefono;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<Cliente>>(){}.getType();
                clientes = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientes;
    }
     public static List<Cliente> obtenerClientePorCorreo(String correo){
        List<Cliente> clientes = null;
        String url = Constantes.URL_WS+"cliente/obtener-cliente-correo/"+correo;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if(respuesta.getCodigo()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            try {
                Type tipoListaColaborador = new TypeToken<List<Cliente>>(){}.getType();
                clientes = gson.fromJson(respuesta.getContenido(), tipoListaColaborador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientes;
    }
    public static Mensaje agregarCliente(Cliente cliente){
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS+"cliente/agregar-cliente";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(cliente);
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
    public static Mensaje editarCliente(Cliente cliente){
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS+"cliente/editar-cliente";
        Gson gson = new Gson();
        try {
            String parametros = gson.toJson(cliente);
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
    public static Mensaje eliminarCliente(Integer idCliente){
        Mensaje mensaje = new Mensaje();
        String url =Constantes.URL_WS+"cliente/eliminar-cliente";
        Gson gson = new Gson();
        try {
            String parametros = String.format("idCliente=%s", idCliente);
            RespuestaHTTP respuesta = ConexionAPI.peticionSinBody(url, Constantes.PETICION_DELETE);
            if(respuesta.getCodigo()== HttpURLConnection.HTTP_OK){
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
}
