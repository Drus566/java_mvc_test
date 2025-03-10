package iplm.mvc.views.detail;

import iplm.gui.window.detail.DetailControlWindow;
import iplm.gui.window.detail.DetailNameControlWindow;
import iplm.managers.WindowsManager;
import iplm.mvc.views.IView;

public class DetailNameControlView implements IView {
    private DetailNameControlWindow m_detail_name_control_window;

    public DetailNameControlWindow getDetailNameControlWindow() { return m_detail_name_control_window; }

    @Override
    public void init() {
        if (m_detail_name_control_window == null) {
            m_detail_name_control_window = new DetailNameControlWindow();
            WindowsManager.getInstance().addWindow(m_detail_name_control_window);
        }
    }
}
