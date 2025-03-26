package iplm.data.config;

import iplm.Application;
import iplm.utility.DialogUtility;
import iplm.utility.FilesystemUtility;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

/*
[Settings]
DetailPath=C:\Baza\Details
*  */

public class DetailConfig {
    private static DetailConfig INSTANCE;
    private static String config_path = Application.CONFIG_DETAIL_PATH;

    public DetailConfig() { }

    public boolean writeSVNPath(String path) {
        Wini ini = openConfig();
        if (ini == null) return false;

        ini.put("Settings", "DetailPath", path);
        try { ini.store(); }
        catch (IOException e) {
            DialogUtility.showErrorDialog(e.getMessage());
            return false;
        }
        return true;
    }

    public String readSVNPath() {
        Wini ini = openConfig();
        if (ini == null) return null;

        String path = ini.get("Settings", "DetailPath");
        return path;
    }

    private Wini openConfig() {
        FilesystemUtility.createFile(config_path);
        Wini ini;
        try { ini = new Wini(new File(config_path)); }
        catch (IOException e) {
            DialogUtility.showErrorDialog(e.getMessage());
            return null;
        }
        return ini;
    }

    public static synchronized DetailConfig getInstance() {
        if (INSTANCE == null) INSTANCE = new DetailConfig();
        return INSTANCE;
    }
}
