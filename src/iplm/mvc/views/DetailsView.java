package iplm.mvc.views;

import iplm.data.types.Detail;
import iplm.gui.window.DetailsWindow;
import iplm.interfaces.observer.IObserver;
import iplm.managers.WindowsManager;

public class DetailsView implements IView, IObserver<Detail> {
    private DetailsWindow m_window;

    @Override
    public void init() {
        if (m_window == null) {
            m_window = new DetailsWindow();
            WindowsManager.getInstance().addWindow(m_window);
        }
    }

    public DetailsWindow getWindow() { return m_window; }

    @Override
    public void update(Detail subject) {

    }

    @Override
    public boolean isActive() {
        return m_window != null;
    }
}
