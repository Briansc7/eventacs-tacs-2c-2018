package com.eventacs.server;

import com.eventacs.external.telegram.client.MainTelegram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Esta clase es la encargada de iniciar la aplicación
 * @author facundofigoli
 */

public class AppInitializer {

    private static final int DEFAULT_PORT = 9000;
    private static final String CONTEXT_PATH = "/";
    private static final String CONFIG_LOCATION_PACKAGE = "com.eventacs";
    private static final String MAPPING_URL = "/";
    private static final Logger LOGGER = LoggerFactory.getLogger(AppInitializer.class);

    public static void main(String[] args) { new AppInitializer().startJetty(getPortFromArgs(args)); }

    private static int getPortFromArgs(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.valueOf(args[0]);
            } catch (NumberFormatException ignore) {
            }
        }
        return DEFAULT_PORT;
    }

    private void startJetty(int port) {
        Server server = new Server(port);
        server.setHandler(getServletContextHandler(getContext()));

        try {

            server.start();

        } catch (Exception e) {
            throw new IllegalArgumentException("Error iniciando el servidor", e);
        }

        LOGGER.info("Server creado");

        try {
            server.join();

        } catch (InterruptedException e) {
            throw new IllegalArgumentException("Error joineando al servidor", e);
        }
    }

    private static ServletContextHandler getServletContextHandler(WebApplicationContext context) {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(CONTEXT_PATH);
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
        return contextHandler;
    }

    private static WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION_PACKAGE);
        return context;
    }
}
