package iplm.managers;

import iplm.mvc.builder.ViewBuilder;
import iplm.mvc.builder.component.ViewComponent;
import iplm.mvc.views.IView;

import java.util.HashMap;
import java.util.Map;

public class ViewsManager {
    private Map<ViewComponent, IView> m_views;

    public ViewsManager() {
        m_views = new HashMap<>();
    }

    public void buildViews() {
        ViewBuilder builder = new ViewBuilder();
        for (ViewComponent view_component : ViewComponent.values()) {
            if (view_component.isActive()) m_views.put(view_component, builder.build(view_component));
        }
    }

    public IView getView(String view_name) {
        IView result = null;
        for (Map.Entry<ViewComponent, IView> entry : m_views.entrySet()) {
            ViewComponent key = entry.getKey();
            if (key.getName().equals(view_name)) {
                WindowsManager.getInstance().showWindow(key.getWindowName());
                break;
            }
        }
        return result;
    }

    public void showStartWindow() {
        for (Map.Entry<ViewComponent, IView> entry : m_views.entrySet()) {
            ViewComponent key = entry.getKey();
            if (key.isInit() && key.getVisible()) {
                WindowsManager.getInstance().showWindow(key.getWindowName());
                break;
            }
        }
    }
}
