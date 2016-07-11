package edu.epam.mentoring.task1.edu.epam.mentoring.util;

/**
 * Created by Yevgeniy_Vtulkin on 7/11/2016.
 */
public final class Helper {
    public static int parseInt(String str) {
        int intId = -1;
        try {
            intId = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new RuntimeException("int is not int");
        }
        return intId;
    }
}
