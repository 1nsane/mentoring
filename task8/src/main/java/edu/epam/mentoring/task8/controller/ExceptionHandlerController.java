package edu.epam.mentoring.task8.controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;

import static edu.epam.mentoring.task8.util.ModelAndViewUtil.view;

/**
 * Created by eugen on 23.09.2016.
 */
@ControllerAdvice
public class ExceptionHandlerController {
    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class
    })
    public ModelAndView handleBadRequest(Exception e) {
        return view("error", "message", messageSource.getMessage("error.bad_request", null, null));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ModelAndView handleFormBadRequest(Exception e) {
        return view("error", "message", messageSource.getMessage("error.form_number_invalid", null, null));
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAnyException(Exception e) {
        String message = messageSource.getMessage("error.unknown_error", null, null) + "<br />" + e.getMessage();
        return view("error", "message", message);
    }
}
