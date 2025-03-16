package iplm.utility;

import java.awt.*;

public class StringUtility {
    public static String cutToChar(String text, char c) {
        String result = text;
        int index = text.indexOf(c);
        if (index != -1) result = text.substring(0, index);
        return result;
    }

    public static String cutBetweenChars(String text, char f, char l) {
        String result = text;
        int start_index = text.indexOf('[');
        int end_index = text.indexOf(']');
        if (start_index != -1 && end_index != -1 && start_index < end_index) result = text.substring(start_index + 1, end_index);
        return result;
    }
}
