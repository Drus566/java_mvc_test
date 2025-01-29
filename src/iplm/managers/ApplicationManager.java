package iplm.managers;

public class ApplicationManager {
    private static final ApplicationManager INSTANCE = new ApplicationManager();

    private ModelsManager m_models_manager;
    private ViewsManager m_views_manager;
    private ControllersManager m_controllers_manager;

    public ApplicationManager() {
        m_models_manager = new ModelsManager();
        m_views_manager = new ViewsManager();
        m_controllers_manager = new ControllersManager();
    }

    public void start() {
        m_models_manager.buildModels();
        m_views_manager.buildViews();
        m_controllers_manager.buildControllers(m_models_manager, m_views_manager);

        m_views_manager.showStartWindow();
        WindowsManager.getInstance().showWindow("DetailsWindow");
    }

    public void stop() {}
    public void restart() {}
    public void exit() {}
    public void softwareUpdate() {}

    public static ApplicationManager getInstance() {
        return INSTANCE;
    }
}
