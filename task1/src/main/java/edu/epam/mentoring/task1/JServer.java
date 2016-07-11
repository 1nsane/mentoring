package edu.epam.mentoring.task1;

import org.eclipse.jetty.server.Server;

/**
 * Created by eugen on 10.07.2016.
 */
public class JServer {
    private static final int PORT = 4444;

    public static void main(String[] args) throws Exception {
        Server server = new Server(PORT);
        server.setHandler(new RequestHandler());

        server.start();
        server.join();
    }
}
