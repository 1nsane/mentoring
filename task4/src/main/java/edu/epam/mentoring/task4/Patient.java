package edu.epam.mentoring.task4;

import org.apache.log4j.Logger;

/**
 * Created by Yevgeniy_Vtulkin on 9/12/2016.
 */
public class Patient {
    private static Logger logger = Logger.getLogger(Patient.class);

    public static void main(String[] args) {
        logger.debug(Patient.class.getSimpleName() + ": hello from patient");
    }
}
