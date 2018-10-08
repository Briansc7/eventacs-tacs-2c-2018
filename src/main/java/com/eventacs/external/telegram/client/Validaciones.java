package com.eventacs.external.telegram.client;

public class Validaciones {

    /*
    Clase para realizar las validaciones de los datos ingresados.
    En caso de que el dato no sea válido, se retorna false y se detalla el error en mensajeDeError
     */


    public static boolean categoriaValida(String idCategoria, StringBuilder mensajeDeError) {
        return true;
    }

    public static boolean fechainicioValida(String part, StringBuilder mensajeDeError) {
        return true;
    }

    //va a validar tambien que la fecha de fin sea posterior o igual a la de inicio
    public static boolean fechafinValida(String part, StringBuilder mensajeDeError) {
        return true;
    }

    public static boolean idListaValida(String part, StringBuilder mensajeDeError) {
        return true;
    }

    public static boolean idEventoValido(String part, StringBuilder mensajeDeError) {
        return true;
    }

    public static boolean userPwValido(String username, String pw) {
        return true;
    }
}
