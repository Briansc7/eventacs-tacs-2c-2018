package com.eventacs.external.telegram.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ComandoBuscarEvento {

    enum estadosBuscarEvento{
        inicio, esperaKeyword, esperaCategoria, esperaOtraCategoria, esperaFechaInicio, esperaFechaFin
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ComandoBuscarEvento.class);

    private static HashMap<Long, estadosBuscarEvento> buscarEventoStates = new HashMap<Long, estadosBuscarEvento>();

    private static HashMap<Long, Optional<String>> keywordGuardado = new HashMap<Long, Optional<String>>();

    private static HashMap<Long, Optional<List<String>>> categoriasGuardadas = new HashMap<Long, Optional<List<String>>>();

    private static HashMap<Long, Optional<LocalDate>> fechaInicioGuardada = new HashMap<Long, Optional<LocalDate>>();

    private static HashMap<Long, Optional<LocalDate>> fechaFinGuardada = new HashMap<Long, Optional<LocalDate>>();

    private Validaciones validaciones = new Validaciones();


    public void buscarEventos(String[] parts, HashMap<Long, estados> chatStates, long chatId, TacsBot tacsBot) {

        StringBuilder mensajeAEnviar = new StringBuilder ();
        StringBuilder mensajeDeError = new StringBuilder ();

        Optional<String> keyword = Optional.empty();
        Optional<List<String>> categories = Optional.empty();
        Optional<LocalDate> startDate = Optional.of(LocalDate.now());
        Optional<LocalDate> endDate = Optional.of(LocalDate.now().plusDays(1));

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
                if(categoriasGuardadas.containsKey(chatId))
                    categories = categoriasGuardadas.get(chatId);
                else
                    categories = Optional.of(new ArrayList<>());

                if(!Validaciones.categoriaValida(parts[0], mensajeDeError)){
                    mensajeAEnviar.append(mensajeDeError.toString()+"\n");
                    mensajeAEnviar.append("Ingrese el id de la categoría");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    buscarEventoStates.put(chatId, estadosBuscarEvento.esperaCategoria);
                    break;
                }

                categories.map(c -> c.add(parts[0])); //105 es Música
                LOGGER.info("Lista de categorias: "+categories);
                categoriasGuardadas.put(chatId, categories);
                mensajeAEnviar.append("Desea agregar otra categoría?\nIngrese Si/No");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                buscarEventoStates.put(chatId, estadosBuscarEvento.esperaOtraCategoria);
                break;
            case esperaOtraCategoria:
                switch (parts[0]){
                    case "si":
                    case "Si":
                    case "SI":
                        mensajeAEnviar.append("Ingrese el id de la categoría");
                        tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                        buscarEventoStates.put(chatId, estadosBuscarEvento.esperaCategoria);
                        break;
                    case "no":
                    case "No":
                    case "NO":
                        mensajeAEnviar.append("Ingrese la fecha de inicio en formato AAAA-MM-DD");
                        tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                        buscarEventoStates.put(chatId, estadosBuscarEvento.esperaFechaInicio);
                        break;
                    default:
                        mensajeAEnviar.append("Opción inválida.\nDesea agregar otra categoría?\nIngrese Si/No");
                        tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                }
                break;
            case esperaFechaInicio:

                if(!Validaciones.fechainicioValida(parts[0], mensajeDeError)){
                    mensajeAEnviar.append(mensajeDeError.toString()+"\n");
                    mensajeAEnviar.append("Ingrese la fecha de inicio en formato AAAA-MM-DD");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    buscarEventoStates.put(chatId, estadosBuscarEvento.esperaFechaInicio);
                    break;
                }

                fechaInicioGuardada.put(chatId, Optional.of(LocalDate.parse(parts[0])));
                mensajeAEnviar.append("Ingrese ingrese la fecha de fin en formato AAAA-MM-DD");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                buscarEventoStates.put(chatId, estadosBuscarEvento.esperaFechaFin);
                break;
            case esperaFechaFin:

                if(!Validaciones.fechafinValida(parts[0], mensajeDeError)){
                    mensajeAEnviar.append(mensajeDeError.toString()+"\n");
                    mensajeAEnviar.append("Ingrese la fecha de fin en formato AAAA-MM-DD");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    buscarEventoStates.put(chatId, estadosBuscarEvento.esperaFechaFin);
                    break;
                }

                fechaFinGuardada.put(chatId, Optional.of(LocalDate.parse(parts[0])));
                mensajeAEnviar.append("Buscando...");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);

                keyword = keywordGuardado.get(chatId);
                categories = categoriasGuardadas.get(chatId);
                startDate = fechaInicioGuardada.get(chatId);
                endDate = fechaFinGuardada.get(chatId);

                Optional<BigInteger> page = Optional.of(BigInteger.ONE);

                mensajeAEnviar = tacsBot.buscarEventos(keyword, categories, startDate, endDate, page);
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);

                buscarEventoStates.remove(chatId);
                keywordGuardado.remove(chatId);
                categoriasGuardadas.remove(chatId);
                fechaInicioGuardada.remove(chatId);
                fechaFinGuardada.remove(chatId);
                chatStates.put(chatId,estados.inicio);
                break;
            default:
                break;
        }


    }

}
