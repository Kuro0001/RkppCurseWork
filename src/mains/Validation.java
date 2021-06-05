package mains;

/**
 * class for check input text
 * @author Kuro
 * @version 1.0
 */
public class Validation {
    /**
     * method for search Not double chars in string
     * @param s input sting
     * @return "false" if not contain and "true" if contain
     */
    public static boolean checkContainNotDouble(String s) {
        if (s.length() == 0)
            return true;
        for (char c : s.toCharArray())
            if (!Character.isDigit(c) && c !='.') return true;
        return false;
    }
    /**
     * method for search Not int chars in string
     * @param s input sting
     * @return "false" if not contain and "true" if contain
     */
    public static boolean checkContainNotInt(String s) {
        if (s.length() == 0)
            return true;
        for (char c : s.toCharArray())
            if (!Character.isDigit(c)) return true;
        return false;
    }
}
