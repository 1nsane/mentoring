package edu.epam.mentoring.task10;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

/**
 * Created by eugen on 16.09.2016.
 */
public class ServerStarter {
    private static final int PORT = 63002;
    private static final String CONTEXT_PATH = "/";
    private static final String MAPPING_URL = "/";
    private static final String CONFIG_LOCATION = "edu.epam.mentoring.task10.config";
    private static final String DEFAULT_PROFILE = "dev";

    private static final Logger logger = Logger.getLogger(ServerStarter.class);

    public static void main(String[] args) {
        new ServerStarter().startServer();
    }

    public void startServer() {
        try {
            Server server = new Server(PORT);
            server.setHandler(getServletContextHandler(server, getContext()));
            server.start();
            server.join();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private WebAppContext getServletContextHandler(Server server, WebApplicationContext context) throws IOException {
        WebAppContext contextHandler = new WebAppContext();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(CONTEXT_PATH);
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(context));
        contextHandler.setResourceBase(".");
        return contextHandler;
    }

    private WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
        return context;
    }
}
