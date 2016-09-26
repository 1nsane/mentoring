package edu.epam.mentoring.task8.util;

import org.springframework.web.servlet.ModelAndView;

/**
 * Created by eugen on 26.09.2016.
 */
public class ModelAndViewUtil {
    public static ModelAndView view(String viewName, String attributeName, Object attribute) {
        return new ModelAndView(viewName, attributeName, attribute);
    }

    public static ModelAndView view(String viewName) {
        return new ModelAndView(viewName);
    }
}
