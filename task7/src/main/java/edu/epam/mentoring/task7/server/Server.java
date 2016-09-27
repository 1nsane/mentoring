package edu.epam.mentoring.task7.server;

import edu.epam.mentoring.task7.service.Service;

import java.util.List;

/**
 * Created by eugen on 26.09.2016.
 */
public class Server {
    private String name;
    private List<Service> services;
    private String serviceString;

    public Server(List<Service> services, String serviceString) {
        this.services = services;
        this.serviceString = serviceString;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void start() {
        services.forEach(s -> new Thread(() -> s.doWork(serviceString)).start());
    }

    public void stop() {
        //nothing here yet
    }
}
