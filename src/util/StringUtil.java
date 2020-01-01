package util;

public class StringUtil {

    public static boolean isEmpty(String s) {
        if (s.trim().equals("")) return true;
        return false;
    }

    public static boolean isNum(String s) {
        for (char c : s.toCharArray()) {
            if (!(c >= '0' && c <= '9')) return false;
        }
        return true;
    }

}
