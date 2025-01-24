package iplm.managers;

import iplm.gui_components.window.IWindow;
import iplm.gui_components.window.MainWindow;

import java.util.List;

public class WindowManager {
    private List<IWindow> windows;

    public MainWindow main_window;

    WindowManager() {
        windows.add(new MainWindow());
    }

    public init() {
        for (IWindow window : windows) {
            window.init();
        }
    }
}
