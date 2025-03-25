package iplm;

import iplm.data.db.OrientDBDriver;
import iplm.data.history.StorageHistory;
import iplm.managers.ApplicationManager;
import iplm.style.Style;
import iplm.utility.ThreadUtility;

import java.util.concurrent.TimeUnit;

public class Application {
    public static String HOME_PATH = "";
    public static String RESOURCES_PATH = HOME_PATH + "resources/";
    public static String ICONS_PATH = "icons";
    public static String RESOURCES_ICONS = RESOURCES_PATH + ICONS_PATH; // path into jar
    public static String TEMP_PATH = HOME_PATH + "temp";
    public static String LOGS_PATH = HOME_PATH + "logs";
    public static String CONFIG_PATH = HOME_PATH + "config/config.ini";
    public static String HISTORY_PATH = HOME_PATH + TEMP_PATH + "/history";

    public static void main(String[] args) {
        new Application();
    }

    public Application() {
//        OrientDBDriver.getInstance().init("remote:172.25.143.106", "root", "root", "debug", "root", "root");
        OrientDBDriver.getInstance().init("remote:doc.sbp-invertor.ru", "root", "root", "debug", "root", "root");
        OrientDBDriver.getInstance().initDetailClasses();
//        OrientDBDriver.getInstance().initDetailData();

        ThreadUtility.getInstance().init(5, 10, 5, TimeUnit.SECONDS);
        StorageHistory.getInstance().init();
        Resources.getInstance().init();
        Style.getInstance().init();
        ApplicationManager.getInstance().start();
    }
}
