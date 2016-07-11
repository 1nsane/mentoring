package edu.epam.mentoring.task1.edu.epam.mentoring.servlets;

import edu.epam.mentoring.DBHandler;
import edu.epam.mentoring.HashMapDBHandler;

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
public abstract class GenericServlet extends HttpServlet {
    protected DBHandler db;

    protected final String[] dictionary = {"компьютер", "рюкзак", "монитор"};

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            db = new HashMapDBHandler();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected String getTextById(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        int id = parseId(req, resp);
        if (id < 0) {
            return null;
        }
        String textForChange = db.getById(id);
        if (textForChange == null) {
            resp.getWriter().println("text with such id does not exist");
            return null;
        }
        return textForChange;
    }

    protected Integer parseId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int intId = -1;
        try {
            intId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            resp.getWriter().println("text id parameter defined incorrectly");
        }
        return intId;
    }

    protected String generateCensore(String source) {
        return source.replaceAll(".", "*");
    }

    protected String dictionaryRegex() {
        return Arrays.asList(dictionary).stream().collect(Collectors.joining("|"));
    }

    protected String getRandomString(String[] list) {
        return list[new Random().nextInt(list.length)];
    }

    protected boolean isEmptySrting(String str) {
        return str == null || str.isEmpty();
    }
}
