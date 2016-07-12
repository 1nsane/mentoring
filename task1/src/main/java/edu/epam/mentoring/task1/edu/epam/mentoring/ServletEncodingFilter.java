package edu.epam.mentoring.task1.edu.epam.mentoring;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Yevgeniy_Vtulkin on 7/12/2016.
 */
public class ServletEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
    }

    @Override
    public void destroy() {

    }
}
