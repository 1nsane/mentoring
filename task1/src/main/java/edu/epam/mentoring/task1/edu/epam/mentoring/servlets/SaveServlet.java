package edu.epam.mentoring.task1.edu.epam.mentoring.servlets;

import edu.epam.mentoring.task1.edu.epam.mentoring.util.Helper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yevgeniy_Vtulkin on 7/11/2016.
 */
public class SaveServlet extends AbstractServlet {
    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Helper.parseInt(req.getParameter("id"));
        if (id < 0) {
            throw new IllegalArgumentException("id format is incorrect");
        }

        String text = req.getParameter("text");
        if (Helper.isEmptyString(text)) {
            throw new IllegalArgumentException("text format is incorrect");
        }

        db.saveToDB(id, text);

        resp.getWriter().println("saved to database");
    }
}
