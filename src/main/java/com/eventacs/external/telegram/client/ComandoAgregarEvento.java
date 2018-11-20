package com.eventacs.external.telegram.client;

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

        if(parts[0].equalsIgnoreCase("/cancelar")){
            agregarEventoStates.remove(chatId);
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

                if(!Validaciones.idListaValida(Validaciones.formatearId(parts[0]), mensajeDeError, tacsBot.getAccessToken(chatId))){
                    mensajeAEnviar.append(mensajeDeError.toString()+"\n");
                    mensajeAEnviar.append("Ingrese el ID de la lista a la cual desea agregar el evento");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    agregarEventoStates.put(chatId, estadosAgregarEvento.esperaIdLista);
                    break;
                }

                idListaGuardado.put(chatId,Validaciones.formatearId(parts[0]));
                mensajeAEnviar.append("Ingrese el ID del evento a agregar a la lista");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                agregarEventoStates.put(chatId, estadosAgregarEvento.esperaIdEvento);
                break;
            case esperaIdEvento:

                if(!Validaciones.idEventoValido(Validaciones.formatearId(parts[0]), mensajeDeError, tacsBot.getAccessToken(chatId))){
                    mensajeAEnviar.append(mensajeDeError.toString()+"\n");
                    mensajeAEnviar.append("Ingrese el ID del evento a agregar a la lista");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    agregarEventoStates.put(chatId, estadosAgregarEvento.esperaIdEvento);
                    break;
                }
/*
                try{
                    tacsBot.agregarEvento(idListaGuardado.get(chatId),Validaciones.formatearId(parts[0]), chatId);
                }
                catch (Exception e){
                    mensajeDeError.append("El id de evento ingresado no existe");
                    mensajeAEnviar.append(mensajeDeError.toString()+"\n");
                    mensajeAEnviar.append("Ingrese el ID del evento a agregar a la lista");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    agregarEventoStates.put(chatId, estadosAgregarEvento.esperaIdEvento);
                    break;
                }*/

                tacsBot.agregarEvento(idListaGuardado.get(chatId),Validaciones.formatearId(parts[0]), chatId);

                mensajeAEnviar.append("Evento Agregado");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                tacsBot.mostrarMenuComandos(chatId);
                agregarEventoStates.remove(chatId);
                chatStates.put(chatId,estados.inicio);
                break;
            default:
                break;
        }

    }
}
