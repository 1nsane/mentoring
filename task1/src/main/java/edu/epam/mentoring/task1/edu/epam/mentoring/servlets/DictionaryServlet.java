package edu.epam.mentoring.task1.edu.epam.mentoring.servlets;

import edu.epam.mentoring.task1.edu.epam.mentoring.util.Helper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Yevgeniy_Vtulkin on 7/11/2016.
 */
public class DictionaryServlet extends AbstractTextServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sourceText = null;
        try {
            sourceText = getTextById(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] list = getDictionaryList(req);
        if (list == null || list.length == 0) {
            resp.getWriter().println("empty list parameter");
            return;
        }
        String resultText = sourceText;
        String dictionaryRegex = dictionaryRegex();
        while(resultText.matches(".*(" + dictionaryRegex + ").*")) {
            resultText = resultText.replaceFirst(dictionaryRegex, getRandomString(list));
        }
        resp.getWriter().println("Source text: " + sourceText);
        resp.getWriter().println("<br>Result text: " + resultText);
    }

    private String[] getDictionaryList(HttpServletRequest req) {
        String list = req.getParameter("list");
        return !Helper.isEmptyString(list) ? list.split("\\s*,\\s*") : null;
    }

    private String getRandomString(String[] list) {
        return list[new Random().nextInt(list.length)];
    }
}
