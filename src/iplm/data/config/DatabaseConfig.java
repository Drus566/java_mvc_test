package iplm.data.config;

import iplm.Application;
import iplm.utility.DialogUtility;
import iplm.utility.FilesystemUtility;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class DatabaseConfig {
    private static DatabaseConfig INSTANCE;
    private static String database_path = Application.CONFIG_DATABASE_PATH;

    public DatabaseConfig() { }

    public boolean writeProductionValues(HashMap<String, String> entries) {
        return writeValues("Production", entries);
    }

    public boolean writeDevelopValues(HashMap<String, String> entries) {
        return writeValues("Develop", entries);
    }

    public boolean writeValues(String section, HashMap<String, String> entries) {
        Wini ini = openConfig();
        if (ini == null) return false;

        for (HashMap.Entry<String, String> maps : entries.entrySet()) {
            ini.put(section, maps.getKey(), maps.getValue());
        }

        try { ini.store(); }
        catch (IOException e) {
            DialogUtility.showErrorDialog(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean writeValue(String section, String key, String value) {
        Wini ini = openConfig();
        if (ini == null) return false;
        ini.put(section, key, value);
        try { ini.store(); }
        catch (IOException e) {
            DialogUtility.showErrorDialog(e.getMessage());
            return false;
        }
        return true;
    }

    public String readProductionValue(String key) {
        return readValue("Production", key);
    }

    public String readDevelopValue(String key) {
        return readValue("Develop", key);
    }

    public String readServerAddress(String section) { return readValue(section, "server_address"); }
    public String readServerLogin(String section) { return readValue(section, "server_login"); }
    public String readServerPass(String section) { return readValue(section, "server_pass"); }
    public String readDBName(String section) { return readValue(section, "db_name"); }
    public String readDBLogin(String section) { return readValue(section, "db_login"); }
    public String readDBPass(String section) { return readValue(section, "db_pass"); }

    public String readDevelopServerAddress() { return readValue("Develop", "server_address"); }
    public String readDevelopServerLogin() { return readValue("Develop", "server_login"); }
    public String readDevelopServerPass() { return readValue("Develop", "server_pass"); }
    public String readDevelopDBName() { return readValue("Develop", "db_name"); }
    public String readDevelopDBLogin() { return readValue("Develop", "db_login"); }
    public String readDevelopDBPass() { return readValue("Develop", "db_pass"); }

    public String readProductionServerAddress() { return readValue("Production", "server_address"); }
    public String readProductionServerLogin() { return readValue("Production", "server_login"); }
    public String readProductionServerPass() { return readValue("Production", "server_pass"); }
    public String readProductionDBName() { return readValue("Production", "db_name"); }
    public String readProductionDBLogin() { return readValue("Production", "db_login"); }
    public String readProductionDBPass() { return readValue("Production", "db_pass"); }

    public boolean writeServerAddress(String section, String value) { return writeValue(section, "server_address", value); }
    public boolean writeServerLogin(String section, String value) { return writeValue(section, "server_login", value); }
    public boolean writeServerPass(String section, String value) { return writeValue(section, "server_pass", value); }
    public boolean writeDBName(String section, String value) { return writeValue(section, "db_name", value); }
    public boolean writeDBLogin(String section, String value) { return writeValue(section, "db_login", value); }
    public boolean writeDBPass(String section, String value) { return writeValue(section, "db_pass", value); }

    public boolean writeDevelopServerAddress(String value) { return writeValue("Develop", "server_address", value); }
    public boolean writeDevelopServerLogin(String value) { return writeValue("Develop", "server_login", value); }
    public boolean writeDevelopServerPass(String value) { return writeValue("Develop", "server_pass", value); }
    public boolean writeDevelopDBName(String value) { return writeValue("Develop", "db_name", value); }
    public boolean writeDevelopDBLogin(String value) { return writeValue("Develop", "db_login", value); }
    public boolean writeDevelopDBPass(String value) { return writeValue("Develop", "db_pass", value); }

    public boolean writeProductionServerAddress(String value) { return writeValue("Production", "server_address", value); }
    public boolean writeProductionServerLogin(String value) { return writeValue("Production", "server_login", value); }
    public boolean writeProductionServerPass(String value) { return writeValue("Production", "server_pass", value); }
    public boolean writeProductionDBName(String value) { return writeValue("Production", "db_name", value); }
    public boolean writeProductionDBLogin(String value) { return writeValue("Production", "db_login", value); }
    public boolean writeProductionDBPass(String value) { return writeValue("Production", "db_pass", value); }

    public String readValue(String section, String key) {
        Wini ini = openConfig();
        if (ini == null) return null;

        String path = ini.get(section, key);
        return path;
    }

    private Wini openConfig() {
        FilesystemUtility.createFile(database_path);
        Wini ini;
        try { ini = new Wini(new File(database_path)); }
        catch (IOException e) {
            DialogUtility.showErrorDialog(e.getMessage());
            return null;
        }
        return ini;
    }

    public static synchronized DatabaseConfig getInstance() {
        if (INSTANCE == null) INSTANCE = new DatabaseConfig();
        return INSTANCE;
    }
}
