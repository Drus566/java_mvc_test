package iplm.style;

import com.formdev.flatlaf.util.SystemInfo;

import javax.swing.*;
import java.util.prefs.Preferences;

public class Style {
    public static final String KEY_SYSTEM_SCALE_FACTOR = "systemScaleFactor";
    private static Style INSTANCE;

    private static final String PREFS_ROOT_PATH = "/iplm";
    private static Preferences state;

    Style() { state = Preferences.userRoot().node(PREFS_ROOT_PATH); }

    public static Preferences getState() { return state; }

    public void init() { FlatLaf.init(); }

    public static Style getInstance() {
        if (INSTANCE == null) INSTANCE = new Style();
        return INSTANCE;
    }
}
