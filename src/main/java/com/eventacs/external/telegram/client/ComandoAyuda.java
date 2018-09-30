package com.eventacs.external.telegram.client;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.util.HashMap;


public class ComandoAyuda{
    public void mostrarAyuda() {

    }

    public void mostrarAyuda(String[] parts, HashMap<Long, estados> chatStates, long chatId, TacsBot tacsBot) {

        StringBuilder mensajeAEnviar = new StringBuilder ();

        switch (parts.length){
            case 1:
                mensajeAEnviar.append("Comandos disponibles:\n\n");
                mensajeAEnviar.append("/ayuda para mostrar este mensaje\n\n");
                mensajeAEnviar.append("Buscar eventos con /buscarevento keyword IdCategoria fechaYhoraInicio fechaYhoraFin\n\n");
                mensajeAEnviar.append("Agregar eventos a una lista de eventos con /agregarevento IdLista IdEvento\n\n");
                mensajeAEnviar.append("Ver eventos de una lista de eventos con /revisareventos IdLista");
                break;
            case 2:
                switch (parts[1]){
                    case "buscarevento":
                        mensajeAEnviar.append("Buscar eventos con /buscarevento keyword IdCategoria fechaYhoraInicio fechaYhoraFin\n\n");
                        mensajeAEnviar.append("Ej.: /buscarevento party 105 2018-09-18T00:00:00 2018-09-19T00:00:00\n");
                        break;
                    case "agregarevento":
                        mensajeAEnviar.append("Agregar eventos a una lista de eventos con /agregarevento IdLista IdEvento\n\n");
                        mensajeAEnviar.append("Ej.: /agregarevento id1 50399583511\n");
                        break;
                    case "revisareventos":
                        mensajeAEnviar.append("Ver eventos de una lista de eventos con /revisareventos IdLista\n\n");
                        mensajeAEnviar.append("Ej.: /revisareventos id1\n");
                        break;
                    default:
                        mensajeAEnviar.append("No existe el comando ").append(parts[1]).append("\n");
                        break;
                }
                break;
            default:
                mensajeAEnviar.append("Cantidad de argumentos inválido\n");
                mensajeAEnviar.append("Utilizar /ayuda o /ayuda nombre-comando");
                break;
        }

        SendMessage message = new SendMessage().setChatId(chatId).setText(mensajeAEnviar.toString());

        try {
            // Se envía el mensaje
            tacsBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


}
