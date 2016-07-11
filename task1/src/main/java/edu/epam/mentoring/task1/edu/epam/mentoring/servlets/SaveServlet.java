package edu.epam.mentoring.task1.edu.epam.mentoring.servlets;

import edu.epam.mentoring.task1.edu.epam.mentoring.util.Helper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Yevgeniy_Vtulkin on 7/11/2016.
 */
public class SaveServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        int id = Helper.parseInt(req.getParameter("id"));
        if (id < 0) {
            resp.getWriter().println("id format is incorrect");
            return;
        }
        String text = req.getParameter("text");
        if (isEmptySrting(text)) {
            resp.getWriter().println("text format is incorrect");
            return;
        }
        try {
            db.saveToDB(id, text);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("can't save to database");
            return;
        }
        resp.getWriter().println("saved to database");
    }
}
