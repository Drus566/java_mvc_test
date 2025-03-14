package iplm.data.config;

import iplm.utility.DialogUtility;
import iplm.utility.FilesystemUtility;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

/*
[Settings]
DetailPath=C:\Baza\Details
*  */

public class Config {
    private static Config INSTANCE;
    private static final String config_path = "resources/config/config.ini";

    public Config() { }

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

    public static synchronized Config getInstance() {
        if (INSTANCE == null) INSTANCE = new Config();
        return INSTANCE;
    }
}
