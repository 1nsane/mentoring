package edu.epam.mentoring;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yevgeniy_Vtulkin on 7/11/2016.
 */
public interface DBHandler {
    void saveToDB(int id, String data) throws Exception;

    String getById(int id) throws Exception;
}

