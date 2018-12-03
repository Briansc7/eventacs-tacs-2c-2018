package com.eventacs.external.telegram.client;

import java.util.HashMap;

public class ComandoCrearLista {

    enum estadosCrearLista{
        inicio, esperaNombre
    }

    private static HashMap<Long, estadosCrearLista> crearListaStates = new HashMap<Long, estadosCrearLista>();

    public void crearLista(String messageTextReceived, HashMap<Long, estados> chatStates, long chatId, TacsBot tacsBot){

        StringBuilder messageToSend = new StringBuilder ();
        //StringBuilder messageError = new StringBuilder ();

        String[] parts = messageTextReceived.split(" ");

        if(parts[0].equalsIgnoreCase("/cancelar")){
            crearListaStates.remove(chatId);
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

        if(!crearListaStates.containsKey(chatId)){
            crearListaStates.put(chatId, estadosCrearLista.inicio);
        }

        switch (crearListaStates.get(chatId)) {
            case inicio:
                messageToSend.append("Ingrese el nombre para la nueva lista de eventos");
                tacsBot.enviarMensaje(messageToSend, chatId);
                crearListaStates.put(chatId, estadosCrearLista.esperaNombre);
                break;
            case esperaNombre:

                try{
                    tacsBot.crearLista(messageTextReceived, chatId);
                    messageToSend.append("Lista Creada");
                }
                catch (Exception e){
                    e.printStackTrace();
                    messageToSend.append("Error al crear lista");
                }

                tacsBot.enviarMensaje(messageToSend, chatId);
                tacsBot.mostrarMenuComandos(chatId);
                crearListaStates.remove(chatId);
                chatStates.put(chatId,estados.inicio);
        }

    }

}
