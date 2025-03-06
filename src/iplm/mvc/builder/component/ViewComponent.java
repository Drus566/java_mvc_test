package iplm.mvc.builder.component;

public enum ViewComponent {
    DetailsView(true, "DetailsView", "DetailsWindow", true, false),
    DetailСontrolView(true, "DetailСontrolView", "DetailСontrolWindow", true, false),
    DetailNameControlView(true, "DetailNameControlView", "DetailNameControlWindow", true, false),
    DetailParameterTypeControlView(true, "DetailParameterTypeControlView", "DetailParameterTypeControlWindow", true, false);

    private boolean m_active;
    private String m_name;
    private String m_window_name;
    private boolean m_init;
    private boolean m_visible;

    ViewComponent(boolean active, String name, String window_name, boolean init, boolean visible) {
        m_name = name;
        m_active = active;
        m_window_name = window_name;
        m_init = init;
        m_visible = visible;
    }

    public boolean isActive() { return m_active; }
    public String getName() { return m_name; }
    public String getWindowName() { return m_window_name; }
    public boolean isInit() { return m_init; }
    public boolean getVisible() { return m_visible; }

    public void setInit(boolean init) { m_init = init; }
    public void setVisible(boolean visible) { m_visible = visible; }
}


//    DetailsView("DetailsView", false, false);
//
//    private String m_name;
//    private boolean m_visible;
//    private boolean m_init;
//
//    ViewComponent(String name, boolean visible, boolean init) {
//        m_name = name;
//        m_visible = visible;
//        m_init = init;
//    }
//
//    public String getName() { return m_name; }
//    public boolean getVisible() { return m_visible; }
//    public boolean isInit() { return m_init; }
//
//    public void setVisible() { m_visible = m_visible; }
//    public void setInit(boolean init) { m_init = init; }
