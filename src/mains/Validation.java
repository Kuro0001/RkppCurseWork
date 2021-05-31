package mains;

public class Validation {
    public static boolean checkContainNotDouble(String s) {
        if (s.length() == 0)
            return true;
        for (char c : s.toCharArray())
            if (!Character.isDigit(c) && c !='.') return true;
        return false;
    }
    public static boolean checkContainNotInt(String s) {
        if (s.length() == 0)
            return true;
        for (char c : s.toCharArray())
            if (!Character.isDigit(c)) return true;
        return false;
    }
}
