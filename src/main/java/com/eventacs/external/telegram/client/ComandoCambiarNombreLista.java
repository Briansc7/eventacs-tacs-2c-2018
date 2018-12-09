package com.eventacs.external.telegram.client;

import java.util.HashMap;

public class ComandoCambiarNombreLista {

    enum estadosCambiarNombreLista{
        inicio, esperaIdLista, esperaNuevoNombre
    }

    private static HashMap<Long, estadosCambiarNombreLista> cambiarNombreListaStates = new HashMap<Long, estadosCambiarNombreLista>();

    private static HashMap<Long, String> idListaGuardado = new HashMap<Long, String>();

    public void cambiarNombreLista(String messageTextReceived, HashMap<Long, estados> chatStates, long chatId, TacsBot tacsBot){

        StringBuilder messageToSend = new StringBuilder ();
        StringBuilder messageError = new StringBuilder ();

        String[] parts = messageTextReceived.split(" ");

        if(parts[0].equalsIgnoreCase("/cancelar")){
            cambiarNombreListaStates.remove(chatId);
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

        if(!cambiarNombreListaStates.containsKey(chatId)){
            cambiarNombreListaStates.put(chatId, estadosCambiarNombreLista.inicio);
        }

        switch (cambiarNombreListaStates.get(chatId)) {
            case inicio:
                messageToSend = tacsBot.listasDeEventosDisponibles(chatId);
                messageToSend.append("Ingrese el ID de la lista a la que le desea cambiar el nombre");
                tacsBot.enviarMensaje(messageToSend, chatId);
                cambiarNombreListaStates.put(chatId, estadosCambiarNombreLista.esperaIdLista);
                break;
            case esperaIdLista:

                if (!Validaciones.idListaValida(Validaciones.formatearId(parts[0]), messageError, tacsBot.getAccessToken(chatId), tacsBot.getUserId(chatId), tacsBot)) {
                    messageToSend.append(messageError.toString() + "\n");
                    messageToSend.append("Ingrese el ID de la lista a la que le desea cambiar el nombre");
                    tacsBot.enviarMensaje(messageToSend, chatId);
                    cambiarNombreListaStates.put(chatId, estadosCambiarNombreLista.esperaIdLista);
                    break;
                }

                idListaGuardado.put(chatId,Validaciones.formatearId(parts[0]));
                messageToSend.append("Ingrese el nuevo nombre para la lista");
                tacsBot.enviarMensaje(messageToSend, chatId);
                cambiarNombreListaStates.put(chatId, estadosCambiarNombreLista.esperaNuevoNombre);
                break;
            case esperaNuevoNombre:

                try{
                    tacsBot.cambiarNombreLista(idListaGuardado.get(chatId), messageTextReceived, chatId);
                    messageToSend.append("Nombre cambiado");
                }
                catch (Exception e){
                    e.printStackTrace();
                    messageError.append("Error al cambiar el nombre");
                    messageToSend.append(messageError.toString() + "\n");
                }

                tacsBot.enviarMensaje(messageToSend, chatId);
                tacsBot.mostrarMenuComandos(chatId);
                cambiarNombreListaStates.remove(chatId);
                chatStates.put(chatId,estados.inicio);

                break;



        }


    }
}
