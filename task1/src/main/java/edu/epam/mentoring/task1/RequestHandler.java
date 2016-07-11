package edu.epam.mentoring.task1;

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
public class RequestHandler extends HttpServlet {
    private DBHandler db;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            db = new HashMapDBHandler();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final String[] dictionary = {"компьютер", "рюкзак", "монитор"};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            proceedRequest(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            proceedRequest(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void proceedRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String action = req.getParameter("action");
        if (isEmptySrting(action)) {
            resp.getWriter().println("action parameter is not defined");
            return;
        }
        switch (action) {
            case "save":
                save(req, resp);
                break;
            case "censorship":
                censorship(req, resp);
                break;
            case "dictionary":
                dictionary(req, resp);
                break;
            default:
                resp.getWriter().println("action parameter defined incorrectly");
        }
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        int id = parseId(req, resp);
        if (id < 0) {
            return;
        }
        String text = req.getParameter("text");
        if (isEmptySrting(text)) {
            resp.getWriter().println("text parameter is not defined");
            return;
        }
        db.saveToDB(id, text);
        resp.getWriter().println("saved to db");
    }

    private void censorship(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String textForChange = getTextById(req, resp);
        if (textForChange == null) {
            return;
        }
        String resultText = textForChange;
        String dictionaryRegex = dictionaryRegex();
        String[] words = textForChange.split("[\\s]+");
        for (String word : words) {
            if (word.matches("^(" + dictionaryRegex + ")$")) {
                resultText = resultText.replaceFirst(word, generateCensore(word));
            }
        }
        resp.getWriter().println("Source text: " + textForChange);
        resp.getWriter().println("<br>Result text: " + resultText);
    }

    private void dictionary(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String textForChange = getTextById(req, resp);
        if (textForChange == null) {
            return;
        }
        String dictionaryList = req.getParameter("list");
        if (isEmptySrting(dictionaryList)) {
            resp.getWriter().println("list parameter is empty");
            return;
        }
        String resultText = textForChange;
        String[] list = dictionaryList.split("\\s*,\\s*");
        String dictionaryRegex = dictionaryRegex();
        while(resultText.matches(".*(" + dictionaryRegex + ").*")) {
            resultText = resultText.replaceFirst(dictionaryRegex, getRandomString(list));
        }
        resp.getWriter().println("Source text: " + textForChange);
        resp.getWriter().println("<br>Result text: " + resultText);
    }

    private String getTextById(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
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

    private Integer parseId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int intId = -1;
        String id = req.getParameter("id");
        if (isEmptySrting(id)) {
            resp.getWriter().println("text id parameter is not defined");
            return null;
        }
        try {
            intId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            resp.getWriter().println("text id parameter defined incorrectly");
        }
        return intId;
    }

    private String generateCensore(String source) {
        return source.replaceAll(".", "*");
    }

    private String dictionaryRegex() {
        return Arrays.asList(dictionary).stream().collect(Collectors.joining("|"));
    }

    private String getRandomString(String[] list) {
        return list[new Random().nextInt(list.length)];
    }

    private boolean isEmptySrting(String str) {
        return str == null || str.isEmpty();
    }
}
