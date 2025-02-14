package iplm.utility;

import java.awt.*;

public class ColorUtility {
    public static String colourToString(Color c) {
        return "#" + Integer.toHexString(0xFF000000 | c.getRGB()).substring(2);
    }
}
