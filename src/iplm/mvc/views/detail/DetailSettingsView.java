package iplm.mvc.views.detail;

import iplm.gui.window.detail.DetailControlWindow;
import iplm.gui.window.detail.DetailSettingsWindow;
import iplm.managers.WindowsManager;
import iplm.mvc.views.IView;

import javax.swing.text.View;

public class DetailSettingsView implements IView {
    private DetailSettingsWindow m_detail_settings_window;

    public DetailSettingsWindow getDetailSettingsWindow() { return m_detail_settings_window; }

    @Override
    public void init() {
        if (m_detail_settings_window == null) {
            m_detail_settings_window = new DetailSettingsWindow();
            WindowsManager.getInstance().addWindow(m_detail_settings_window);
        }
    }
}
