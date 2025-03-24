package iplm.utility;

import java.awt.*;

public class StringUtility {
    public static String cutToChar(String text, char c) {
        String result = text;
        int index = text.indexOf(c);
        if (index != -1) result = text.substring(0, index);
        return result;
    }

    public static boolean containOnlySymbol(String text, char c) {
        boolean result = true;
        for (char cc : text.toCharArray()) {
            if (cc != c) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static String cutToLastChar(String text, char c) {
        String result = text;
        int index = text.lastIndexOf(c);
        if (index != -1) result = text.substring(0, index);
        return result;
    }

    public static String cutToIndex(String text, int index) {
        String result = text;
        result = text.substring(0, index);
        return result;
    }

    public static String getBetweenChars(String text, char f, char l) {
        String result = null;
        int start_index = text.indexOf(f);
        int end_index = text.lastIndexOf(l);
        if (start_index != -1 && end_index != -1 && start_index < end_index) result = text.substring(start_index + 1, end_index);
        return result;
    }

    public static int getCountSymbols(String text, char s) {
        int count = 0;
        for (char ch : text.toCharArray()) {
            if (ch == s) {
                ++count;
            }
        }
        return count;
    }
}
