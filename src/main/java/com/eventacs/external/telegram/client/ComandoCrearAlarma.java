package com.eventacs.external.telegram.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ComandoCrearAlarma {
    enum estadosCrearAlarma {
        inicio, esperaNombre, esperaKeyword, esperaCategoria, esperaOtraCategoria, esperaFechaInicio, esperaFechaFin,
        esperaDiaFechInic, esperaMesFechInic, esperaAnioFechInic,
        esperaDiaFechFin, esperaMesFechFin, esperaAnioFechFin,
        preguntaQuiereKeyword, preguntaQuiereCategoria, preguntaQuiereFechaInicio, preguntaQuiereFechaFin,
        buscando
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ComandoBuscarEvento.class);

    private static HashMap<Long, estadosCrearAlarma> crearAlarmaStates = new HashMap<Long, estadosCrearAlarma>();

    private static HashMap<Long, String> nombreGuardado = new HashMap<Long, String>();

    private static HashMap<Long, Optional<String>> keywordGuardado = new HashMap<Long, Optional<String>>();

    private static HashMap<Long, Optional<List<String>>> categoriasGuardadas = new HashMap<Long, Optional<List<String>>>();

    private static HashMap<Long, Optional<LocalDate>> fechaInicioGuardada = new HashMap<Long, Optional<LocalDate>>();

    private static HashMap<Long, Optional<LocalDate>> fechaFinGuardada = new HashMap<Long, Optional<LocalDate>>();

    private static HashMap<Long, String> fechaInicioParcial = new HashMap<Long, String>();

    private static HashMap<Long, String> fechaFinParcial = new HashMap<Long, String>();

    private Validaciones validaciones = new Validaciones();


    public void crearAlarma(String messageTextReceived, HashMap<Long, estados> chatStates, long chatId, TacsBot tacsBot) {

        StringBuilder mensajeAEnviar = new StringBuilder();
        StringBuilder mensajeDeError = new StringBuilder();

        String[] parts = messageTextReceived.split(" ");

        String fechaParcial, dia, mes, anio;

        Optional<String> keyword = Optional.empty();
        Optional<List<String>> categories = Optional.empty();
        Optional<LocalDate> startDate = Optional.of(LocalDate.now());
        Optional<LocalDate> endDate = Optional.of(LocalDate.now().plusDays(1));

        if (parts[0].equalsIgnoreCase("/cancelar")) {
            crearAlarmaStates.remove(chatId);
            chatStates.put(chatId, estados.inicio);
            tacsBot.mostrarMenuComandos(chatId);
            return;
        }

        if (!Validaciones.usuarioVerificado(chatId, tacsBot)) {
            mensajeAEnviar.append("Debe hacer /login para utilizar este comando");
            tacsBot.enviarMensaje(mensajeAEnviar, chatId);
            chatStates.put(chatId, estados.inicio);
            return;
        }

        if (!crearAlarmaStates.containsKey(chatId)) {
            crearAlarmaStates.put(chatId, estadosCrearAlarma.inicio);
        }

        switch (crearAlarmaStates.get(chatId)) {
            case inicio:
                mensajeAEnviar.append("Ingrese el nombre para la nueva alarma");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaNombre);
                break;
            case esperaNombre:
                nombreGuardado.put(chatId, messageTextReceived);
                mensajeAEnviar.append("Desea agregar un keyword a su búsqueda?\nIngrese Si/No");
                tacsBot.enviarMensajeConOpcionSiNo(mensajeAEnviar, chatId);
                crearAlarmaStates.put(chatId, estadosCrearAlarma.preguntaQuiereKeyword);
                break;
            case preguntaQuiereKeyword:

                switch (parts[0].toLowerCase()) {
                    case "si":
//                    case "Si":
//                    case "SI":
                        mensajeAEnviar.append("Ingrese el keyword");
                        tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                        crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaKeyword);
                        break;
                    case "no":
//                    case "No":
//                    case "NO":
                        keywordGuardado.put(chatId, Optional.empty());
                        mensajeAEnviar.append("Desea agregar una categoría a su búsqueda?\nIngrese Si/No");
                        tacsBot.enviarMensajeConOpcionSiNo(mensajeAEnviar, chatId);
                        crearAlarmaStates.put(chatId, estadosCrearAlarma.preguntaQuiereCategoria);
                        break;
                    default:
                        mensajeAEnviar.append("Opción inválida.\nDesea agregar un keyword a su búsqueda?\nIngrese Si/No");
                        tacsBot.enviarMensajeConOpcionSiNo(mensajeAEnviar, chatId);
                }


                break;

            case esperaKeyword:
                keywordGuardado.put(chatId, Optional.of(messageTextReceived));
                mensajeAEnviar.append("Desea agregar una categoría a su búsqueda?\nIngrese Si/No");
                tacsBot.enviarMensajeConOpcionSiNo(mensajeAEnviar, chatId);
                crearAlarmaStates.put(chatId, estadosCrearAlarma.preguntaQuiereCategoria);
                break;

            case preguntaQuiereCategoria:
                switch (parts[0].toLowerCase()) {
                    case "si":
//                    case "Si":
//                    case "SI":
                        mensajeAEnviar = tacsBot.categoriasDisponibles(chatId);
                        mensajeAEnviar.append("\nIngrese el id de la categoría");
                        tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                        crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaCategoria);
                        break;
                    case "no":
//                    case "No":
//                    case "NO":
                        categoriasGuardadas.put(chatId, Optional.empty());
                        mensajeAEnviar.append("Desea agregar una fecha de inicio a su búsqueda?\nIngrese Si/No");
                        tacsBot.enviarMensajeConOpcionSiNo(mensajeAEnviar, chatId);
                        crearAlarmaStates.put(chatId, estadosCrearAlarma.preguntaQuiereFechaInicio);
                        break;
                    default:
                        mensajeAEnviar.append("Opción inválida.\nDesea agregar una categoría a su búsqueda?\nIngrese Si/No");
                        tacsBot.enviarMensajeConOpcionSiNo(mensajeAEnviar, chatId);
                }
                break;

            case esperaCategoria:
                if (categoriasGuardadas.containsKey(chatId))
                    categories = categoriasGuardadas.get(chatId);
                else
                    categories = Optional.of(new ArrayList<>());

                if (!Validaciones.categoriaValida(Validaciones.formatearId(parts[0]), mensajeDeError, tacsBot.getAccessToken(chatId))) {
                    mensajeAEnviar.append(mensajeDeError.toString() + "\n");
                    mensajeAEnviar.append("Ingrese el id de la categoría");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaCategoria);
                    break;
                }

                categories.map(c -> c.add(Validaciones.formatearId(parts[0]))); //105 es Música
                LOGGER.info("Lista de categorias: " + categories);
                categoriasGuardadas.put(chatId, categories);
                mensajeAEnviar.append("Desea agregar otra categoría?\nIngrese Si/No");
                tacsBot.enviarMensajeConOpcionSiNo(mensajeAEnviar, chatId);
                crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaOtraCategoria);
                break;
            case esperaOtraCategoria:
                switch (parts[0].toLowerCase()) {
                    case "si":
//                    case "Si":
//                    case "SI":
                        mensajeAEnviar.append("Ingrese el id de la categoría");
                        tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                        crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaCategoria);
                        break;
                    case "no":
//                    case "No":
//                    case "NO":
                        mensajeAEnviar.append("Desea agregar una fecha de inicio a su búsqueda?\nIngrese Si/No");
                        tacsBot.enviarMensajeConOpcionSiNo(mensajeAEnviar, chatId);
                        crearAlarmaStates.put(chatId, estadosCrearAlarma.preguntaQuiereFechaInicio);
                        break;
                    default:
                        mensajeAEnviar.append("Opción inválida.\nDesea agregar otra categoría?\nIngrese Si/No");
                        tacsBot.enviarMensajeConOpcionSiNo(mensajeAEnviar, chatId);
                }
                break;

            case preguntaQuiereFechaInicio:
                switch (parts[0].toLowerCase()) {
                    case "si":
//                    case "Si":
//                    case "SI":
                        mensajeAEnviar.append("\nIngrese el día de la fecha de inicio");
                        tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                        crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaDiaFechInic);
                        break;
                    case "no":
//                    case "No":
//                    case "NO":
                        fechaInicioGuardada.put(chatId, Optional.empty());
                        mensajeAEnviar.append("Desea agregar una fecha de fin a su búsqueda?\nIngrese Si/No\"");
                        tacsBot.enviarMensajeConOpcionSiNo(mensajeAEnviar, chatId);
                        crearAlarmaStates.put(chatId, estadosCrearAlarma.preguntaQuiereFechaFin);
                        break;
                    default:
                        mensajeAEnviar.append("Opción inválida.\nDesea agregar una fecha de inicio a su búsqueda?\nIngrese Si/No");
                        tacsBot.enviarMensajeConOpcionSiNo(mensajeAEnviar, chatId);
                }
                break;
            case esperaDiaFechInic:

                if (!Validaciones.isNumeric(parts[0])) {
                    mensajeAEnviar.append("No ha ingresado un número válido\n");
                    mensajeAEnviar.append("Ingrese el día de la fecha de inicio\n");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaDiaFechInic);
                    break;
                }

                if (parts[0].length() > 2) {
                    mensajeAEnviar.append("El día no puede ser de más de 2 dígitos\n");
                    mensajeAEnviar.append("Ingrese el día de la fecha de inicio\n");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaDiaFechInic);
                    break;
                }

                switch (parts[0].length()) {
                    case 1:
                        dia = "0" + parts[0];
                        break;
                    case 2:
                    default:
                        dia = parts[0];
                        break;
                }

                fechaInicioParcial.put(chatId, dia);
                mensajeAEnviar.append("Ingrese el mes de la fecha de inicio");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaMesFechInic);
                break;
            case esperaMesFechInic:

                if (!Validaciones.isNumeric(parts[0])) {
                    mensajeAEnviar.append("No ha ingresado un número válido\n");
                    mensajeAEnviar.append("Ingrese el mes de la fecha de inicio\n");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaMesFechInic);
                    break;
                }

                if (parts[0].length() > 2) {
                    mensajeAEnviar.append("El mes no puede ser de más de 2 dígitos\n");
                    mensajeAEnviar.append("Ingrese el mes de la fecha de inicio\n");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaMesFechInic);
                    break;
                }

                switch (parts[0].length()) {
                    case 1:
                        mes = "0" + parts[0];
                        break;
                    case 2:
                    default:
                        mes = parts[0];
                        break;
                }

                fechaParcial = fechaInicioParcial.get(chatId);
                fechaParcial = mes + "-" + fechaParcial;
                fechaInicioParcial.put(chatId, fechaParcial);
                mensajeAEnviar.append("Ingrese el año de la fecha de inicio");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaAnioFechInic);
                break;
            case esperaAnioFechInic:
                fechaParcial = fechaInicioParcial.get(chatId);
                fechaParcial = parts[0] + "-" + fechaParcial;


                if (!Validaciones.fechainicioValida(fechaParcial, mensajeDeError)) {
                    mensajeAEnviar.append(mensajeDeError.toString() + "\n");
                    mensajeAEnviar.append("Ingrese el día de la fecha de inicio");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaDiaFechInic);
                    break;
                }

                fechaInicioGuardada.put(chatId, Optional.of(LocalDate.parse(fechaParcial)));
                LOGGER.info("Fecha inicio guardada: " + fechaInicioGuardada.get(chatId));
                mensajeAEnviar.append("Desea agregar una fecha de fin a su búsqueda?\nIngrese Si/No");
                tacsBot.enviarMensajeConOpcionSiNo(mensajeAEnviar, chatId);
                crearAlarmaStates.put(chatId, estadosCrearAlarma.preguntaQuiereFechaFin);
                break;

            case preguntaQuiereFechaFin:
                switch (parts[0].toLowerCase()) {
                    case "si":
//                    case "Si":
//                    case "SI":
                        mensajeAEnviar.append("\nIngrese el día de la fecha de fin");
                        tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                        crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaDiaFechFin);
                        break;
                    case "no":
//                    case "No":
//                    case "NO":
                        fechaFinGuardada.put(chatId, Optional.empty());
                        try{
                            tacsBot.crearAlarma(nombreGuardado.get(chatId),
                                    keywordGuardado.get(chatId),
                                    categoriasGuardadas.get(chatId),
                                    fechaInicioGuardada.get(chatId),
                                    fechaFinGuardada.get(chatId),
                                    Optional.of(BigInteger.ONE),
                                    chatId);

                            mensajeAEnviar.append("Alarma creada");
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            mensajeDeError.append("Error al crear alarma");
                            mensajeAEnviar.append(mensajeDeError.toString() + "\n");
                        }

                        tacsBot.enviarMensaje(mensajeAEnviar, chatId);

                        crearAlarmaStates.remove(chatId);
                        chatStates.put(chatId, estados.inicio);

                        tacsBot.mostrarMenuComandos(chatId);
                        break;
                    default:
                        mensajeAEnviar.append("Opción inválida.\nDesea agregar una fecha de fin a su búsqueda?\nIngrese Si/No");
                        tacsBot.enviarMensajeConOpcionSiNo(mensajeAEnviar, chatId);
                }
                break;


            case esperaDiaFechFin:

                if (!Validaciones.isNumeric(parts[0])) {
                    mensajeAEnviar.append("No ha ingresado un número válido\n");
                    mensajeAEnviar.append("Ingrese el día de la fecha de fin\n");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaDiaFechFin);
                    break;
                }

                if (parts[0].length() > 2) {
                    mensajeAEnviar.append("El día no puede ser de más de 2 dígitos\n");
                    mensajeAEnviar.append("Ingrese el día de la fecha de fin\n");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaDiaFechFin);
                    break;
                }

                switch (parts[0].length()) {
                    case 1:
                        dia = "0" + parts[0];
                        break;
                    case 2:
                    default:
                        dia = parts[0];
                        break;
                }

                fechaFinParcial.put(chatId, dia);
                mensajeAEnviar.append("Ingrese el mes de la fecha de fin");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaMesFechFin);
                break;
            case esperaMesFechFin:

                if (!Validaciones.isNumeric(parts[0])) {
                    mensajeAEnviar.append("No ha ingresado un número válido\n");
                    mensajeAEnviar.append("Ingrese el mes de la fecha de fin\n");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaMesFechFin);
                    break;
                }

                if (parts[0].length() > 2) {
                    mensajeAEnviar.append("El mes no puede ser de más de 2 dígitos\n");
                    mensajeAEnviar.append("Ingrese el mes de la fecha de fin\n");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaMesFechFin);
                    break;
                }

                switch (parts[0].length()) {
                    case 1:
                        mes = "0" + parts[0];
                        break;
                    case 2:
                    default:
                        mes = parts[0];
                        break;
                }

                fechaParcial = fechaFinParcial.get(chatId);
                fechaParcial = mes + "-" + fechaParcial;
                fechaFinParcial.put(chatId, fechaParcial);
                mensajeAEnviar.append("Ingrese el año de la fecha de fin");
                tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaAnioFechFin);
                break;
            case esperaAnioFechFin:
                fechaParcial = fechaFinParcial.get(chatId);
                fechaParcial = parts[0] + "-" + fechaParcial;


                //para evitar problemas con que la fecha sea opcional, se soluciona agregando una fecha ficticia que nunca se va a usar
                if (!Validaciones.fechafinValida(fechaParcial, fechaInicioGuardada.get(chatId).orElse(LocalDate.of(0, 1, 1)), mensajeDeError)) {
                    mensajeAEnviar.append(mensajeDeError.toString() + "\n");
                    mensajeAEnviar.append("Ingrese el día de la fecha de fin");
                    tacsBot.enviarMensaje(mensajeAEnviar, chatId);
                    crearAlarmaStates.put(chatId, estadosCrearAlarma.esperaDiaFechFin);
                    break;
                }

                fechaFinGuardada.put(chatId, Optional.of(LocalDate.parse(fechaParcial)));
                LOGGER.info("Fecha inicio guardada: " + fechaFinGuardada.get(chatId));

                try{
                    tacsBot.crearAlarma(nombreGuardado.get(chatId),
                            keywordGuardado.get(chatId),
                            categoriasGuardadas.get(chatId),
                            fechaInicioGuardada.get(chatId),
                            fechaFinGuardada.get(chatId),
                            Optional.of(BigInteger.ONE),
                            chatId);

                    mensajeAEnviar.append("Alarma creada");
                }
                catch (Exception e){
                    e.printStackTrace();
                    mensajeDeError.append("Error al crear alarma");
                    mensajeAEnviar.append(mensajeDeError.toString() + "\n");
                }

                tacsBot.enviarMensaje(mensajeAEnviar, chatId);

                crearAlarmaStates.remove(chatId);
                chatStates.put(chatId, estados.inicio);

                tacsBot.mostrarMenuComandos(chatId);

                break;
            default:
                break;
        }
    }

}
