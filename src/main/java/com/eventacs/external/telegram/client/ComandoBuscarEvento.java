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
        inicio, esperaKeyword, esperaCategoria, esperaOtraCategoria, esperaFechaInicio, esperaFechaFin,
        esperaDiaFechInic, esperaMesFechInic, esperaAnioFechInic,
        esperaDiaFechFin, esperaMesFechFin, esperaAnioFechFin
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ComandoBuscarEvento.class);

    private static HashMap<Long, estadosBuscarEvento> buscarEventoStates = new HashMap<Long, estadosBuscarEvento>();

    private static HashMap<Long, Optional<String>> keywordGuardado = new HashMap<Long, Optional<String>>();

    private static HashMap<Long, Optional<List<String>>> categoriasGuardadas = new HashMap<Long, Optional<List<String>>>();

    private static HashMap<Long, Optional<LocalDate>> fechaInicioGuardada = new HashMap<Long, Optional<LocalDate>>();

    private static HashMap<Long, Optional<LocalDate>> fechaFinGuardada = new HashMap<Long, Optional<LocalDate>>();

    private static HashMap<Long, String> fechaInicioParcial = new HashMap<Long, String>();

    private static HashMap<Long, String> fechaFinParcial = new HashMap<Long, String>();

    private Validaciones validaciones = new Validaciones();


    public void buscarEventos(String[] parts, HashMap<Long, estados> chatStates, long chatId, TacsBot tacsBot) {

        StringBuilder mensajeAEnviar = new StringBuilder ();
        StringBuilder mensajeDeError = new StringBuilder ();

        String fechaParcial;

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
                mensajeAEnviar = tacsBot.categoriasDisponibles();
                mensajeAEnviar.append("\nIngrese el id de la categoría");
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
                        mensajeAEnviar.append("Ingrese el día de la fecha de inicio");
                        tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                        buscarEventoStates.put(chatId, estadosBuscarEvento.esperaDiaFechInic);
                        break;
                    default:
                        mensajeAEnviar.append("Opción inválida.\nDesea agregar otra categoría?\nIngrese Si/No");
                        tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                }
                break;
            case esperaDiaFechInic:
                fechaInicioParcial.put(chatId,parts[0]);
                mensajeAEnviar.append("Ingrese el mes de la fecha de inicio");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                buscarEventoStates.put(chatId, estadosBuscarEvento.esperaMesFechInic);
                break;
            case esperaMesFechInic:
                fechaParcial = fechaInicioParcial.get(chatId);
                fechaParcial = parts[0]+"-"+fechaParcial;
                fechaInicioParcial.put(chatId,fechaParcial);
                mensajeAEnviar.append("Ingrese el año de la fecha de inicio");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                buscarEventoStates.put(chatId, estadosBuscarEvento.esperaAnioFechInic);
                break;
            case esperaAnioFechInic:
                fechaParcial = fechaInicioParcial.get(chatId);
                fechaParcial = parts[0]+"-"+fechaParcial;


                if(!Validaciones.fechainicioValida(fechaParcial, mensajeDeError)){
                    mensajeAEnviar.append(mensajeDeError.toString()+"\n");
                    mensajeAEnviar.append("La fecha ingresada no es válida.\nIngrese el día de la fecha de inicio");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    buscarEventoStates.put(chatId, estadosBuscarEvento.esperaDiaFechInic);
                    break;
                }

                fechaInicioGuardada.put(chatId, Optional.of(LocalDate.parse(fechaParcial)));
                LOGGER.info("Fecha inicio guardada: " + fechaInicioGuardada.get(chatId));
                mensajeAEnviar.append("Ingrese el día de la fecha de fin");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                buscarEventoStates.put(chatId, estadosBuscarEvento.esperaDiaFechFin);
                break;


            case esperaDiaFechFin:
                fechaFinParcial.put(chatId,parts[0]);
                mensajeAEnviar.append("Ingrese el mes de la fecha de fin");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                buscarEventoStates.put(chatId, estadosBuscarEvento.esperaMesFechFin);
                break;
            case esperaMesFechFin:
                fechaParcial = fechaFinParcial.get(chatId);
                fechaParcial = parts[0]+"-"+fechaParcial;
                fechaFinParcial.put(chatId,fechaParcial);
                mensajeAEnviar.append("Ingrese el año de la fecha de fin");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                buscarEventoStates.put(chatId, estadosBuscarEvento.esperaAnioFechFin);
                break;
            case esperaAnioFechFin:
                fechaParcial = fechaFinParcial.get(chatId);
                fechaParcial = parts[0]+"-"+fechaParcial;


                if(!Validaciones.fechafinValida(fechaParcial, mensajeDeError)){
                    mensajeAEnviar.append(mensajeDeError.toString()+"\n");
                    mensajeAEnviar.append("La fecha ingresada no es válida.\nIngrese el día de la fecha de fin");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    buscarEventoStates.put(chatId, estadosBuscarEvento.esperaDiaFechFin);
                    break;
                }

                fechaFinGuardada.put(chatId, Optional.of(LocalDate.parse(fechaParcial)));
                LOGGER.info("Fecha inicio guardada: " + fechaFinGuardada.get(chatId));

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
