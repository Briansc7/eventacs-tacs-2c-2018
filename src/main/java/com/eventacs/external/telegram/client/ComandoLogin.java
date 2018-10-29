package com.eventacs.external.telegram.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class ComandoLogin {

    enum estadosLogin{
        inicio, esperaUsername, esperaPw
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(TacsBot.class);

    private static HashMap<Long, estadosLogin> loginStates = new HashMap<Long, estadosLogin>();

    private static HashMap<Long, String> usernameGuardado = new HashMap<Long, String>();


    public void login(String[] parts, HashMap<Long, estados> chatStates, long chatId, TacsBot tacsBot) {

        StringBuilder mensajeAEnviar = new StringBuilder ();
        StringBuilder mensajeDeError = new StringBuilder ();

        if(!loginStates.containsKey(chatId)){
            loginStates.put(chatId, estadosLogin.inicio);
        }

        LOGGER.info("Estado login: " + loginStates.get(chatId));

        switch (loginStates.get(chatId)){
            case inicio:
                mensajeAEnviar.append("Ingrese su Username");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                loginStates.put(chatId, estadosLogin.esperaUsername);
                break;
            case esperaUsername:
                usernameGuardado.put(chatId,parts[0]);
                mensajeAEnviar.append("Ingrese su contraseña");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                loginStates.put(chatId, estadosLogin.esperaPw);
                break;
            case esperaPw:
                String username = usernameGuardado.get(chatId);
                String pw = parts[0];
                if(Validaciones.userPwValido(username, pw)){
                    TacsBot.guardarCuentaTelegram(chatId, username);
                    TacsBot.guardarToken(chatId, username);
                    mensajeAEnviar.append("Login exitoso");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);

                }
                else{
                    mensajeAEnviar.append("usuario o contraseña inválida");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                }

                loginStates.remove(chatId);
                chatStates.put(chatId,estados.inicio);
                break;
            default:
                break;
        }

    }
}
