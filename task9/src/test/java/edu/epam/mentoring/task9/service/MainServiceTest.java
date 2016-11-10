package edu.epam.mentoring.task9.service;

import org.junit.Test;

/**
 * Created by eugen on 10/30/2016.
 */
public class MainServiceTest {
    private MainService mainService = new MainService();

    @Test
    public void testGetString() {
        assert mainService.getString("").equals("12345");
        assert mainService.getString("qwerty").equals("qwerty12345");
    }
}
