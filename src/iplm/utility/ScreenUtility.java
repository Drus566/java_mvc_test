package iplm.utility;

import java.awt.*;

public class ScreenUtility {
    public static int getWidth() { return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(); }
    public static int getHeight() { return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(); }
    public static Dimension getScreen() { return Toolkit.getDefaultToolkit().getScreenSize(); }
}
