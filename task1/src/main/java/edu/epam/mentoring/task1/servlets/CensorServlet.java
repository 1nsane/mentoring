package edu.epam.mentoring.task1.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yevgeniy_Vtulkin on 7/11/2016.
 */
public class CensorServlet extends AbstractTextServlet  {
    @Override
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String textForChange = getTextById(req.getParameter("id"));

        String resultText = textForChange.replaceAll(dictionaryRegex(), "***");

        resp.getWriter().println("Source text: " + textForChange);
        resp.getWriter().println("<br>Result text: " + resultText);
    }
}
