package edu.epam.mentoring.task8;

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

/**
 * Created by eugen on 16.09.2016.
 */
public class ServerStarter {
    private static final int PORT = 63001;
    private static final String CONTEXT_PATH = "/";
    private static final String MAPPING_URL = "/";
    private static final String CONFIG_LOCATION = "edu.epam.mentoring.task8.config";
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

        ErrorPageErrorHandler errorHandler = new ErrorPageErrorHandler();
        errorHandler.addErrorPage(SC_NOT_FOUND, "/layout/404.jsp");
        contextHandler.setErrorHandler(errorHandler);

        contextHandler.setContextPath(CONTEXT_PATH);
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(context));
        contextHandler.setResourceBase(new ClassPathResource("webapp").getURI().toString());

        setUpJsp(server, contextHandler);

        return contextHandler;
    }

    private void setUpJsp(Server server, WebAppContext contextHandler) {
        contextHandler.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                "(.*jstl\\S+\\.jar)|(.*spring-webmvc\\S+\\.jar)$");

        org.eclipse.jetty.webapp.Configuration.ClassList classlist =
                org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration",
                "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");
    }

    private WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
        return context;
    }
}
