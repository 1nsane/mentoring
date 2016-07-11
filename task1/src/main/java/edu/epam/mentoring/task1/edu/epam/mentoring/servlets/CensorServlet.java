package edu.epam.mentoring.task1.edu.epam.mentoring.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Yevgeniy_Vtulkin on 7/11/2016.
 */
public class CensorServlet extends AbstractTextServlet  {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String textForChange = null;
        try {
            textForChange = getTextById(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (textForChange == null) {
            return;
        }
        String resultText = textForChange.replaceAll(dictionaryRegex(), "***");
        resp.getWriter().println("Source text: " + textForChange);
        resp.getWriter().println("<br>Result text: " + resultText);
    }
}
