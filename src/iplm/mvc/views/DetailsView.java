package iplm.mvc.views;

import iplm.data.types.Detail;
import iplm.gui.window.detail.DetailsWindow;
import iplm.interfaces.observer.IObserver;
import iplm.managers.WindowsManager;

public class DetailsView implements IView, IObserver<Detail> {
    private DetailsWindow m_details_window;

    public DetailsWindow getDetailsWindow() { return m_details_window; }

    @Override
    public void init() {
        if (m_details_window == null) {
            m_details_window = new DetailsWindow();
            WindowsManager.getInstance().addWindow(m_details_window);
        }
    }

    @Override
    public void update(Detail subject) {

    }

    @Override
    public boolean isActive() {
        return m_details_window != null;
    }
}
