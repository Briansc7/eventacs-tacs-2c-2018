package com.eventacs.external.telegram.client;

import java.util.HashMap;

public class ComandoRevisarEventos {

    enum estadosRevisarEventos{
        inicio, esperaIdLista
    }

    private static HashMap<Long, estadosRevisarEventos> revisarEventosStates = new HashMap<Long, estadosRevisarEventos>();

    public void revisarEventos(String[] parts, HashMap<Long, estados> chatStates, long chatId, TacsBot tacsBot) {

        StringBuilder mensajeAEnviar = new StringBuilder ();

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
                tacsBot.revisarEventos(parts[0],chatId);
                revisarEventosStates.put(chatId, estadosRevisarEventos.inicio);
                chatStates.put(chatId,estados.inicio);
                break;
             default:
                break;
        }
    }
}
