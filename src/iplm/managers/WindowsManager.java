package iplm.managers;

import iplm.gui.window.AWindow;

import java.util.List;

public class WindowsManager {
    private static final WindowsManager INSTANCE = new WindowsManager();

    private List<AWindow> windows;

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
