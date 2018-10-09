package com.eventacs.external.telegram.client;

import com.eventacs.user.model.User;
import com.eventacs.user.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Validaciones {

    /*
    Clase para realizar las validaciones de los datos ingresados.
    En caso de que el dato no sea válido, se retorna false y se detalla el error en mensajeDeError
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TacsBot.class);

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

        UsersRepository repo = new UsersRepository();
        Encriptador encriptador = new Encriptador();
        String pwEncriptado = encriptador.toShae256(pw);

        Optional<User> usuario = repo.getByUserId(username);
        if(!usuario.isPresent()){
            return false;
        }
        else{
            String pwreal = usuario.get().getPassword();
            return pwreal.equals(pwEncriptado);
        }
    }

    public static boolean usuarioVerificado(long chatId, TacsBot tacsBot) {
        return tacsBot.existeUserConChatID(chatId);
    }
}
