package edu.epam.mentoring.task1.servlets;

import edu.epam.mentoring.task1.util.Helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * Created by Yevgeniy_Vtulkin on 7/11/2016.
 */
public class DictionaryServlet extends AbstractTextServlet {
    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String sourceText = getTextById(req.getParameter("id"));
        String[] list = getDictionaryList(req);

        if (list == null || list.length == 0) {
            throw new IllegalArgumentException("empty list parameter");
        }

        String resultText = replaceWithDictionary(sourceText, list);

        resp.getWriter().println("Source text: " + sourceText);
        resp.getWriter().println("<br>Result text: " + resultText);
    }

    private String replaceWithDictionary(String sourceText, String[] list) {
        String resultText = sourceText;
        String dictionaryRegex = dictionaryRegex();
        while(resultText.matches(".*(" + dictionaryRegex + ").*")) {
            resultText = resultText.replaceFirst(dictionaryRegex, getRandomString(list));
        }
        return resultText;
    }

    private String[] getDictionaryList(HttpServletRequest req) {
        String list = req.getParameter("list");
        return !Helper.isEmptyString(list) ? list.split("\\s*,\\s*") : null;
    }

    private String getRandomString(String[] list) {
        return list[new Random().nextInt(list.length)];
    }
}
