package edu.epam.mentoring.task1.servlets;

import edu.epam.mentoring.task1.util.Helper;

import java.io.IOException;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

/**
 * Created by Yevgeniy_Vtulkin on 7/12/2016.
 */
public abstract class AbstractTextServlet extends AbstractServlet {
    protected final String[] dictionary = {"компьютер", "рюкзак", "монитор"};

    protected String getTextById(String strId) throws Exception {
        int id = Helper.parseInt(strId);
        String textForChange = null;

        try {
            textForChange = db.getById(id);
        } catch (Exception e) {
            throw new RuntimeException("a database error occurred");
        }

        if (textForChange == null) {
            throw new IOException("text with such id does not exist");
        }
        return textForChange;
    }

    protected String dictionaryRegex() {
        return asList(dictionary).stream().collect(joining("|"));
    }
}
