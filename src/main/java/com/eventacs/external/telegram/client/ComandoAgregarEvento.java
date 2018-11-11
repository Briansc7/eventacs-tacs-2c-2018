package com.eventacs.external.telegram.client;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.HashMap;

public class ComandoAgregarEvento {

    enum estadosAgregarEvento{
        inicio, esperaIdLista, esperaIdEvento
    }

    private static HashMap<Long, estadosAgregarEvento> agregarEventoStates = new HashMap<Long, estadosAgregarEvento>();

    private static HashMap<Long, String> idListaGuardado = new HashMap<Long, String>();

    public void agregarEvento(String[] parts, HashMap<Long, estados> chatStates, long chatId, TacsBot tacsBot) {

        StringBuilder mensajeAEnviar = new StringBuilder ();
        StringBuilder mensajeDeError = new StringBuilder ();

        if(!Validaciones.usuarioVerificado(chatId, tacsBot)){
            mensajeAEnviar.append("Debe hacer /login para utilizar este comando");
            tacsBot.enviarMensaje(mensajeAEnviar, chatId);
            chatStates.put(chatId,estados.inicio);
            return;
        }

        if(!agregarEventoStates.containsKey(chatId)){
            agregarEventoStates.put(chatId, estadosAgregarEvento.inicio);
        }

        switch (agregarEventoStates.get(chatId)){
            case inicio:
                mensajeAEnviar.append("Ingrese el ID de la lista a la cual desea agregar el evento");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                agregarEventoStates.put(chatId, estadosAgregarEvento.esperaIdLista);
                break;
            case esperaIdLista:

                if(!Validaciones.idListaValida(parts[0], mensajeDeError)){
                    mensajeAEnviar.append(mensajeDeError.toString()+"\n");
                    mensajeAEnviar.append("Ingrese el ID de la lista a la cual desea agregar el evento");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    agregarEventoStates.put(chatId, estadosAgregarEvento.esperaIdLista);
                    break;
                }

                idListaGuardado.put(chatId,parts[0]);
                mensajeAEnviar.append("Ingrese el ID del evento a agregar a la lista");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                agregarEventoStates.put(chatId, estadosAgregarEvento.esperaIdEvento);
                break;
            case esperaIdEvento:

                if(!Validaciones.idEventoValido(parts[0], mensajeDeError)){
                    mensajeAEnviar.append(mensajeDeError.toString()+"\n");
                    mensajeAEnviar.append("Ingrese el ID del evento a agregar a la lista");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    agregarEventoStates.put(chatId, estadosAgregarEvento.esperaIdEvento);
                    break;
                }

                tacsBot.agregarEvento(idListaGuardado.get(chatId),parts[0], chatId);
                mensajeAEnviar.append("Evento Agregado");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                tacsBot.mostrarMenuComandos(chatId);
                agregarEventoStates.put(chatId, estadosAgregarEvento.inicio);
                chatStates.put(chatId,estados.inicio);
                break;
            default:
                break;
        }

    }
}
