package edu.epam.mentoring.task1.edu.epam.mentoring.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static edu.epam.mentoring.task1.edu.epam.mentoring.util.Helper.parseInt;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

/**
 * Created by Yevgeniy_Vtulkin on 7/12/2016.
 */
public class AbstractTextServlet extends AbstractServlet {
    protected final String[] dictionary = {"компьютер", "рюкзак", "монитор"};

    protected String getTextById(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = parseInt(req.getParameter("id"));
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

    protected String dictionaryRegex() {
        return asList(dictionary).stream().collect(joining("|"));
    }
}
