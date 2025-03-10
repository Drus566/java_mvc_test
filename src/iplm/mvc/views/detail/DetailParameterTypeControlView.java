package iplm.mvc.views.detail;

import iplm.gui.window.detail.DetailParameterTypeControlWindow;
import iplm.managers.WindowsManager;
import iplm.mvc.views.IView;

public class DetailParameterTypeControlView implements IView {
    private DetailParameterTypeControlWindow m_detail_parameter_type_control_window;

    public DetailParameterTypeControlWindow getDetailNameControlWindow() { return m_detail_parameter_type_control_window; }

    @Override
    public void init() {
        if (m_detail_parameter_type_control_window == null) {
            m_detail_parameter_type_control_window = new DetailParameterTypeControlWindow();
            WindowsManager.getInstance().addWindow(m_detail_parameter_type_control_window);
        }
    }
}
