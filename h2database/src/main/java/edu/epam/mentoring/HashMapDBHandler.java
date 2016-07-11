package edu.epam.mentoring;

import org.h2.tools.Server;

import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eugen on 11.07.2016.
 */
public class HashMapDBHandler implements DBHandler {
    private ConcurrentHashMap<Integer, String> dbMap;

    public HashMapDBHandler() throws SQLException {
        dbMap = new ConcurrentHashMap<>();
    }

    public void saveToDB(int id, String data) throws SQLException {
        dbMap.put(id, data);
    }

    public String getById(int id) throws SQLException {
        return dbMap.get(id);
    }
}
