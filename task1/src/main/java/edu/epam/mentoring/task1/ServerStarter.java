package edu.epam.mentoring.task1;

import edu.epam.mentoring.task1.edu.epam.mentoring.servlets.LogServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * Created by eugen on 10.07.2016.
 */
public class ServerStarter {
    private static final int PORT = 4444;

    public static void main(String[] args) throws Exception {
        Server server = new Server(PORT);
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(RequestHandler.class, "/");
        servletHandler.addServletWithMapping(LogServlet.class, "/log");
        server.setHandler(servletHandler);

        server.start();
        server.join();
    }
}
