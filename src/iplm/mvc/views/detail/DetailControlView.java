package iplm.mvc.views.detail;

import iplm.gui.window.detail.DetailControlWindow;
import iplm.managers.WindowsManager;
import iplm.mvc.views.IView;

public class DetailControlView implements IView {
    private DetailControlWindow m_detail_control_window;

    public DetailControlWindow getDetailControlWindow() { return m_detail_control_window; }

    @Override
    public void init() {
        if (m_detail_control_window == null) {
            m_detail_control_window = new DetailControlWindow();
            WindowsManager.getInstance().addWindow(m_detail_control_window);
        }
    }
}
