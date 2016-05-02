package mx.bidg.utils;

/**
 * @author Rafael Viveros
 * Created on 29/04/16.
 */
public class StringFormatter {
    public static String concatWithoutNull(String... values) {
        String result = "";
        for (String value : values) {
            result += (value == null) ? "" : value + " ";
        }
        return result;
    }
}
