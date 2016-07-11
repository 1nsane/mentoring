package edu.epam.mentoring.task1.edu.epam.mentoring.servlets;

import edu.epam.mentoring.DBHandler;
import edu.epam.mentoring.HashMapDBHandler;
import edu.epam.mentoring.task1.edu.epam.mentoring.util.Helper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Yevgeniy_Vtulkin on 7/11/2016.
 */
public abstract class AbstractServlet extends HttpServlet {
    protected DBHandler db;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            db = new HashMapDBHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean isEmptySrting(String str) {
        return str == null || str.isEmpty();
    }
}
