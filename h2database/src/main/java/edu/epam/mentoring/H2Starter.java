package edu.epam.mentoring;

import org.h2.tools.Server;

import java.sql.SQLException;

/**
 * Created by eugen on 11.07.2016.
 */
public class H2Starter {
    public static void main(String[] args) throws SQLException {
        Server.createTcpServer("-tcpPort", "6666", "-tcpAllowOthers").start();
    }
}
