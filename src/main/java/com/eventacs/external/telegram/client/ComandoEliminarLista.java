package com.eventacs.external.telegram.client;

import java.util.HashMap;

public class ComandoEliminarLista {

    enum estadosEliminarLista{
        inicio, esperaIdLista
    }

    private static HashMap<Long, estadosEliminarLista> eliminarListaStates = new HashMap<Long, estadosEliminarLista>();

    public void eliminarLista(String[] parts, HashMap<Long, estados> chatStates, long chatId, TacsBot tacsBot){

        StringBuilder messageToSend = new StringBuilder ();
        StringBuilder messageError = new StringBuilder ();

        if(parts[0].equalsIgnoreCase("/cancelar")){
            eliminarListaStates.remove(chatId);
            chatStates.put(chatId,estados.inicio);
            tacsBot.mostrarMenuComandos(chatId);
            return;
        }

        if(!Validaciones.usuarioVerificado(chatId, tacsBot)){
            messageToSend.append("Debe hacer /login para utilizar este comando");
            tacsBot.enviarMensaje(messageToSend, chatId);
            chatStates.put(chatId,estados.inicio);
            return;
        }

        if(!eliminarListaStates.containsKey(chatId)){
            eliminarListaStates.put(chatId, estadosEliminarLista.inicio);
        }

        switch (eliminarListaStates.get(chatId)) {
            case inicio:
                messageToSend = tacsBot.listasDeEventosDisponibles(chatId);
                messageToSend.append("Ingrese el ID de la lista que desea eliminar");
                tacsBot.enviarMensaje(messageToSend, chatId);
                eliminarListaStates.put(chatId, estadosEliminarLista.esperaIdLista);
                break;
            case esperaIdLista:

                if (!Validaciones.idListaValida(Validaciones.formatearId(parts[0]), messageError, tacsBot.getAccessToken(chatId), tacsBot.getUserId(chatId), tacsBot)) {
                    messageToSend.append(messageError.toString() + "\n");
                    messageToSend.append("Ingrese el ID de la lista que desea eliminar");
                    tacsBot.enviarMensaje(messageToSend, chatId);
                    eliminarListaStates.put(chatId, estadosEliminarLista.esperaIdLista);
                    break;
                }

                try {
                    tacsBot.eliminarLista(Validaciones.formatearId(parts[0]), chatId);
                    messageToSend.append("Lista Eliminada");
                } catch (Exception e) {
                    e.printStackTrace();
                    messageToSend.append("Error al eliminar lista");
                }

                tacsBot.enviarMensaje(messageToSend, chatId);
                tacsBot.mostrarMenuComandos(chatId);
                eliminarListaStates.remove(chatId);
                chatStates.put(chatId, estados.inicio);
        }

    }

}
