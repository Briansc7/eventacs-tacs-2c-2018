package com.eventacs.external.telegram.client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ComandoBuscarEvento {

    enum estadosBuscarEvento{
        inicio, esperaKeyword, esperaCategoria, esperaFechaInicio, esperaFechaFin
    }

    private static HashMap<Long, estadosBuscarEvento> buscarEventoStates = new HashMap<Long, estadosBuscarEvento>();

    private static HashMap<Long, Optional<String>> keywordGuardado = new HashMap<Long, Optional<String>>();

    private static HashMap<Long, Optional<List<String>>> categoriasGuardadas = new HashMap<Long, Optional<List<String>>>();

    private static HashMap<Long, Optional<LocalDateTime>> fechaInicioGuardada = new HashMap<Long, Optional<LocalDateTime>>();

    private static HashMap<Long, Optional<LocalDateTime>> fechaFinGuardada = new HashMap<Long, Optional<LocalDateTime>>();


    public void buscarEventos(String[] parts, HashMap<Long, estados> chatStates, long chatId, TacsBot tacsBot) {

        StringBuilder mensajeAEnviar = new StringBuilder ();

        Optional<String> keyword = Optional.empty();
        Optional<List<String>> categories = Optional.empty();
        Optional<LocalDateTime> startDate = Optional.of(LocalDateTime.now());
        Optional<LocalDateTime> endDate = Optional.of(LocalDateTime.now().plusDays(1));

        if(!buscarEventoStates.containsKey(chatId)){
            buscarEventoStates.put(chatId, estadosBuscarEvento.inicio);
        }

        switch (buscarEventoStates.get(chatId)){
            case inicio:
                mensajeAEnviar.append("Ingrese el keyword");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                buscarEventoStates.put(chatId, estadosBuscarEvento.esperaKeyword);
                break;
            case esperaKeyword:
                keywordGuardado.put(chatId, Optional.of(parts[0]));
                mensajeAEnviar.append("Ingrese el id de la categoría");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                buscarEventoStates.put(chatId, estadosBuscarEvento.esperaCategoria);
                break;
            case esperaCategoria:
                categories = Optional.of(new ArrayList<>());
                categories.map(c -> c.add(parts[0])); //105 es Música
                categoriasGuardadas.put(chatId, categories);
                mensajeAEnviar.append("Ingrese la fecha de inicio");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                buscarEventoStates.put(chatId, estadosBuscarEvento.esperaFechaInicio);
                break;
            case esperaFechaInicio:
                fechaInicioGuardada.put(chatId, Optional.of(LocalDateTime.parse(parts[0])));
                mensajeAEnviar.append("Ingrese ingrese la fecha de fin");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                buscarEventoStates.put(chatId, estadosBuscarEvento.esperaFechaFin);
                break;
            case esperaFechaFin:
                fechaFinGuardada.put(chatId, Optional.of(LocalDateTime.parse(parts[0])));
                mensajeAEnviar.append("Buscando...");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);

                keyword = keywordGuardado.get(chatId);
                categories = categoriasGuardadas.get(chatId);
                startDate = fechaInicioGuardada.get(chatId);
                endDate = fechaFinGuardada.get(chatId);

                mensajeAEnviar = tacsBot.buscarEventos(keyword, categories, startDate, endDate);
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);

                buscarEventoStates.remove(chatId);
                chatStates.put(chatId,estados.inicio);
                break;
            default:
                break;
        }


    }

}
