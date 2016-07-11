package edu.epam.mentoring.task1;

import edu.epam.mentoring.DBHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Yevgeniy_Vtulkin on 7/11/2016.
 */
public class RequestHandler {
    private HttpServletRequest httpRequest;
    private HttpServletResponse httpResponse;
    private DBHandler db;

    private final String[] dictionary = {"компьютер", "рюкзак", "монитор"};

    public RequestHandler(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws SQLException {
        db = new DBHandler();
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
    }

    public void proceedRequest() throws IOException, SQLException {
        String action = httpRequest.getParameter("action");
        if (isEmptySrting(action)) {
            httpResponse.getWriter().println("action parameter is not defined");
            return;
        }
        switch (action) {
            case "save":
                save();
                break;
            case "censorship":
                censorship();
                break;
            case "dictionary":
                dictionary();
                break;
            default:
                httpResponse.getWriter().println("action parameter defined incorrectly");
        }
    }

    private void save() throws IOException, SQLException {
        String text = httpRequest.getParameter("text");
        if (isEmptySrting(text)) {
            httpResponse.getWriter().println("text parameter is not defined");
            return;
        }
        db.saveToDB(text);
        httpResponse.getWriter().println("saved to db");
    }

    private void censorship() throws IOException, SQLException {
        String textForChange = getTextById();
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
        httpResponse.getWriter().println("Source text: " + textForChange);
        httpResponse.getWriter().println("<br>Result text: " + resultText);
    }

    private void dictionary() throws IOException, SQLException {
        String textForChange = getTextById();
        if (textForChange == null) {
            return;
        }
        String dictionaryList = httpRequest.getParameter("list");
        if (isEmptySrting(dictionaryList)) {
            httpResponse.getWriter().println("text with such id does not exist");
            return;
        }
        String resultText = textForChange;
        String[] list = dictionaryList.split("\\s*,\\s*");
        String dictionaryRegex = dictionaryRegex();
        while(resultText.matches(".*(" + dictionaryRegex + ").*")) {
            resultText = resultText.replaceFirst(dictionaryRegex, getRandomString(list));
        }
        httpResponse.getWriter().println("Source text: " + textForChange);
        httpResponse.getWriter().println("<br>Result text: " + resultText);
    }

    private String getTextById() throws IOException, SQLException {
        int intId;
        String id = httpRequest.getParameter("id");
        if (isEmptySrting(id)) {
            httpResponse.getWriter().println("text id parameter is not defined");
            return null;
        }
        try {
            intId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            httpResponse.getWriter().println("text id parameter defined incorrectly");
            return null;
        }
        String textForChange = db.getById(intId);
        if (textForChange == null) {
            httpResponse.getWriter().println("text with such id does not exist");
            return null;
        }
        return textForChange;
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
