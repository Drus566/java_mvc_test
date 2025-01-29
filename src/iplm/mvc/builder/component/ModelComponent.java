package iplm.mvc.builder.component;

public enum ModelComponent {
    DetailModel(true, "DetailModel", true);

    private boolean m_active;
    private String m_name;
    private boolean m_init;

    ModelComponent(boolean active, String name, boolean init) {
        m_active = active;
        m_name = name;
        m_init = init;
    }

    public boolean isActive() { return m_active; }
    public String getName() { return m_name; }
    public boolean isInit() { return m_init; }

    public void setInit(boolean init) { m_init = init; }
}
