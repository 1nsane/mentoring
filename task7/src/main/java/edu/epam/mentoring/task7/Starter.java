package edu.epam.mentoring.task7;

import edu.epam.mentoring.task7.server.Server;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by eugen on 27.09.2016.
 */
public class Starter {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Server server = (Server)context.getBean("Server");
        server.start();
        context.registerShutdownHook();
    }
}
