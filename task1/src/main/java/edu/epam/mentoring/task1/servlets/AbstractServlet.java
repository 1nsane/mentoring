package edu.epam.mentoring.task1.servlets;

import edu.epam.mentoring.DBHandler;
import edu.epam.mentoring.HashMapDBHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yevgeniy_Vtulkin on 7/11/2016.
 */
public abstract class AbstractServlet extends HttpServlet {
    protected static DBHandler db = new HashMapDBHandler();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req, resp);
    }

    protected void doRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            doProcess(req, resp);
        } catch (Exception e) {
            resp.getWriter().println(e.getMessage());
        }
    }

    protected abstract void doProcess(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
