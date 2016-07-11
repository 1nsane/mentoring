package edu.epam.mentoring;

import org.h2.tools.Server;

import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eugen on 11.07.2016.
 */
public class HashMapDBHandler implements DBHandler {
    private ConcurrentHashMap<Integer, String> dbMap;

    public HashMapDBHandler() {
        dbMap = new ConcurrentHashMap<>();
    }

    public void saveToDB(int id, String data) throws Exception {
        dbMap.put(id, data);
    }

    public String getById(int id) throws Exception {
        return dbMap.get(id);
    }
}
