package LOTD.project.global.util;

public class StringUtil {

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty() || s.equals(" ") || s.equals("");
    }

}
