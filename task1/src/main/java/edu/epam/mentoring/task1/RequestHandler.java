package edu.epam.mentoring.task1;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by eugen on 10.07.2016.
 */
public class RequestHandler extends AbstractHandler {
    public void handle(String s, Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse)
            throws IOException, ServletException {
        request.setHandled(true);
        httpResponse.setContentType("text/html;charset=utf-8");

        httpResponse.getWriter().println("<h2>Это русский текст</h2>");
    }
}
