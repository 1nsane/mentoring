package edu.epam.mentoring.task9.controller;

import edu.epam.mentoring.task9.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by eugen on 10/29/2016.
 */
@Controller
@RequestMapping("/controller")
public class MainController {
    @Autowired
    private MainService service;

    @RequestMapping(value = "/{toConcat}", method = GET)
    public @ResponseBody String getSomeStringConcatenation(@PathVariable String toConcat) {
        return service.getString(toConcat) + " is for " + toConcat;
    }
}
