package com.eventacs.external.telegram.client;

import java.util.HashMap;

public class ComandoRevisarEventos {

    enum estadosRevisarEventos{
        inicio, esperaIdLista
    }

    private static HashMap<Long, estadosRevisarEventos> revisarEventosStates = new HashMap<Long, estadosRevisarEventos>();

    public void revisarEventos(String[] parts, HashMap<Long, estados> chatStates, long chatId, TacsBot tacsBot) {

        StringBuilder mensajeAEnviar = new StringBuilder ();
        StringBuilder mensajeDeError = new StringBuilder ();

        if(parts[0].equalsIgnoreCase("/cancelar")){
            revisarEventosStates.remove(chatId);
            chatStates.put(chatId,estados.inicio);
            tacsBot.mostrarMenuComandos(chatId);
            return;
        }

        if(!Validaciones.usuarioVerificado(chatId, tacsBot)){
            mensajeAEnviar.append("Debe hacer /login para utilizar este comando");
            tacsBot.enviarMensaje(mensajeAEnviar, chatId);
            chatStates.put(chatId,estados.inicio);
            return;
        }

        if(!revisarEventosStates.containsKey(chatId)){
            revisarEventosStates.put(chatId, estadosRevisarEventos.inicio);
        }

        switch (revisarEventosStates.get(chatId)){
            case inicio:
                mensajeAEnviar.append("Ingrese el ID de la lista de la cual desea ver todos los eventos");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                revisarEventosStates.put(chatId, estadosRevisarEventos.esperaIdLista);
                break;
            case esperaIdLista:

                if(!Validaciones.idListaValida(Validaciones.formatearId(parts[0]), mensajeDeError, tacsBot.getAccessToken(chatId))){
                    mensajeAEnviar.append(mensajeDeError.toString()+"\n");
                    mensajeAEnviar.append("Ingrese el ID de la lista a la cual desea agregar el evento");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    revisarEventosStates.put(chatId, estadosRevisarEventos.esperaIdLista);
                    break;
                }

                tacsBot.revisarEventos(Validaciones.formatearId(parts[0]),chatId);
                tacsBot.mostrarMenuComandos(chatId);
                revisarEventosStates.remove(chatId);
                chatStates.put(chatId,estados.inicio);
                break;
             default:
                break;
        }
    }
}
