package edu.epam.mentoring.task1.util;

/**
 * Created by Yevgeniy_Vtulkin on 7/11/2016.
 */
public final class Helper {
    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new RuntimeException("incorrect integer parameter format");
        }
    }

    public static boolean isEmptyString(String str) {
        return str == null || str.isEmpty();
    }
}
