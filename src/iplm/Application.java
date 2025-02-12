package iplm;

import iplm.managers.ApplicationManager;
import iplm.style.Style;

public class Application {
    public static String HOME_PATH = "";
    public static String RESOURCES_PATH = HOME_PATH + "resources";
    public static String RESOURCES_ICONS = RESOURCES_PATH + "/icons";
    public static String TEMP_PATH = HOME_PATH + "temp";
    public static String LOGS_PATH = HOME_PATH + "logs";
    public static String CONFIG_PATH = HOME_PATH + "config.xml";

    public static void main(String[] args) {
        new Application();
    }

    public Application() {
        Resources.getInstance().init();
        Style.getInstance().init();
        ApplicationManager.getInstance().start();
    }
}
