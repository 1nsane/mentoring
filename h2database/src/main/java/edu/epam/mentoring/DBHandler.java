package edu.epam.mentoring;

import org.h2.tools.Server;

import java.sql.*;

/**
 * Created by eugen on 11.07.2016.
 */
public class DBHandler {
    private static Connection conn;

    public DBHandler() throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:~/MENTORING", "sa", "");
    }

    private final String LOAD_FROM_CSV = "CREATE TABLE MENTORING AS SELECT * FROM CSVREAD('task1.csv');";
    private final String GET_BY_ID = "SELECT * MENTORING TASK WHERE ID=";
    private final String INSERT = "INSERT INTO MENTORING (TEXT) VALUES (?)";

    public void loadDataFromCSV(String source) throws ClassNotFoundException, SQLException {
        conn.createStatement().execute(LOAD_FROM_CSV);
    }

    public void pushToDB(String data) throws SQLException {
        conn.createStatement().executeUpdate(INSERT);
    }

    public String getById(int id) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery(GET_BY_ID + id);
        if (rs.next())
            return rs.getString("TEXT");
        return null;
    }
}
