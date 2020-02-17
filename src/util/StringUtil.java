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

    public static int stringToInt(String s) {
        int res;
        try {
            res = Integer.valueOf(s);
        } catch (Exception e) {
            return -1;
        }
        return res;
    }

}
