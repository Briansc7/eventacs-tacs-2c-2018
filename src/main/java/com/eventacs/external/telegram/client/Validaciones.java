package com.eventacs.external.telegram.client;

import com.eventacs.external.eventbrite.model.GetAccessToken;
import com.eventacs.external.telegram.client.httprequest.RequestLogin;
import com.eventacs.user.model.User;
import com.eventacs.user.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.codehaus.jackson.ObjectCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import org.apache.http.impl.client.HttpClients;


public class Validaciones {

    /*
    Clase para realizar las validaciones de los datos ingresados.
    En caso de que el dato no sea válido, se retorna false y se detalla el error en mensajeDeError
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TacsBot.class);
    private static ObjectMapper objectMapper;

    public static boolean categoriaValida(String idCategoria, StringBuilder mensajeDeError) {
        return true;
    }

    public static boolean fechainicioValida(String part, StringBuilder mensajeDeError) {
        return true;
    }

    //va a validar tambien que la fecha de fin sea posterior o igual a la de inicio
    public static boolean fechafinValida(String part, StringBuilder mensajeDeError) {
        return true;
    }

    public static boolean idListaValida(String part, StringBuilder mensajeDeError) {
        return true;
    }

    public static boolean idEventoValido(String part, StringBuilder mensajeDeError) {
        return true;
    }

    public static GetAccessToken userPwValido(String username, String pw) throws IOException  {

        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).setCookieSpec(ClientPNames.COOKIE_POLICY).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        GetAccessToken response = null;

        RequestLogin request = (new RequestLogin(username,pw));
        return response = objectMapper.readValue(httpClient.execute(request.build()).getEntity().getContent(), GetAccessToken.class);

    }

    public static boolean usuarioVerificado(long chatId, TacsBot tacsBot) {
        return tacsBot.existeUserConChatID(chatId);
    }
}
