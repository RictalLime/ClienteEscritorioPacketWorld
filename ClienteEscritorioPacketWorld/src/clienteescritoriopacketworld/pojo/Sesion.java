/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clienteescritoriopacketworld.pojo;

/**
 *
 * @author Tron7
 */
public class Sesion {
    private static Colaborador colaboradorActual;

    public static void setColaboradorActual(Colaborador c) {
        colaboradorActual = c;
    }

    public static Colaborador getColaboradorActual() {
        return colaboradorActual;
    }

    public static void cerrarSesion() {
        colaboradorActual = null;
    }
}