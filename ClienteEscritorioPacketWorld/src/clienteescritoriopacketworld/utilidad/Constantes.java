/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.utilidad;

/**
 *
 * @author Tron7
 */
public class Constantes {
    
    public static final String URL_WS = "http://localhost:8095/packet-world/api/";
    public static final String SUBLIMAS_WS = "http://sublimas.com.mx:8080/calculadora/api/envios/distancia/";
    public static final int ERROR_MALFORMED_URL = 1001;
    public static final int ERROR_PETICION = 1002;
    public static final String MSJ_ERROR_URL = "Lo sentimos su solicitud no puede ser realizada en este momento, por favor intente mas tarde.";
    public static final String MSJ_ERROR_PETICION = "Lo sentimos teneos problemas de conexion en este momento, por favor intente mas tarde.";
    
    //LLAVES Hash
    public static final String KEY_ERROR = "error";
    public static final String KEY_MENSAJE = "mensaje";
    public static final String KEY_LISTA = "lista";
    public static final String KEY_OBJECT = "objeto";
    
    //PETICION
    public static final String PETICION_GET = "GET";
    public static final String PETICION_POST = "POST";
    public static final String PETICION_PUT = "PUT";
    public static final String PETICION_DELETE = "DELETE";
    
    //CONTENT TYPE
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
}