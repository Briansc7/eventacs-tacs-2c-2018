package com.eventacs.external.telegram.client;

public class Validaciones {
    public static boolean categoriaValida(String idCategoria) {
        return true;
    }

    public static boolean fechainicioValida(String part) {
        return true;
    }

    //va a validar tambien que la fecha de fin sea posterior o igual a la de inicio
    public static boolean fechafinValida(String part) {
        return true;
    }
}
