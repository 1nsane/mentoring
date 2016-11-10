package edu.epam.mentoring.task9.service;

import org.springframework.stereotype.Service;

/**
 * Created by eugen on 10/29/2016.
 */
@Service
public class MainService {
    public String getString(String toConcat) {
        return toConcat + "12345";
    }
}
