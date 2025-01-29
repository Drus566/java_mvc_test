package iplm.managers;

import iplm.gui.window.AWindow;

import java.util.ArrayList;
import java.util.List;

public class WindowsManager {
    private static final WindowsManager INSTANCE = new WindowsManager();

    private List<AWindow> windows;

    private WindowsManager() {
        windows = new ArrayList<>();
    }

    public void showWindow(String window_name) {
        for (AWindow window : windows) {
            if (window.getName().equals(window_name)) window.show();
        }
    }
    public void addWindow(AWindow window) {
        windows.add(window);
    }
    public void removeWindow(AWindow window) {
        windows.remove(window);
    }

    public static WindowsManager getInstance() {
        return INSTANCE;
    }
}
