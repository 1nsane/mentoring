package edu.epam.mentoring.task1.edu.epam.mentoring.exceptions;

import edu.epam.mentoring.task1.edu.epam.mentoring.servlets.AbstractTextServlet;

/**
 * Created by eugen on 12.07.2016.
 */
public abstract class AbstractParameterException extends Exception {
    protected String parameterName;

    public String getParameterName() {
        return parameterName;
    }

    public AbstractParameterException setParameterName(String parameterName) {
        this.parameterName = parameterName;
        return this;
    }
}
