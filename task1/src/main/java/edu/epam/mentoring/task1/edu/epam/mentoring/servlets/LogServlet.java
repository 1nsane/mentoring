package edu.epam.mentoring.task1.edu.epam.mentoring.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yevgeniy_Vtulkin on 7/11/2016.
 */
public class LogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("log of 2+2 is " + Math.log(2+2));
    }
}
