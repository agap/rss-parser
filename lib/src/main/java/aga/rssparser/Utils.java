package aga.rssparser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author artem
 * Created on 17.08.14.
 */
public class Utils {
    private static final SimpleDateFormat RFC_822_FORMATTER = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");

    public static String capitalize(final String arg) {
        if (arg == null || arg.isEmpty()) {
            return arg;
        } else {
            return Character.toUpperCase(arg.charAt(0)) + arg.substring(1);
        }
    }

    public static Date toDate(final String value) {
        try {
            return RFC_822_FORMATTER.parse(value);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
