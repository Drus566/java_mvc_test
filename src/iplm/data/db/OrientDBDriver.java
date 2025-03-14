package iplm.data.db;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.db.*;
import com.orientechnologies.orient.core.record.OElement;
import iplm.utility.DateTimeUtility;
import iplm.utility.FilesystemUtility;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class OrientDBDriver {
    private static OrientDBDriver INSTANCE;

    private final static String DEFAULT_ADDRESS = "remote:localhost";
    private final static String DEFAULT_USERNAME = "root";
    private final static String DEFAULT_PASSWORD = "root";

    private final static String DEFAULT_DATABASE = "test";

    private OrientDB m_db;
    private ODatabaseSession m_session;

    public OrientDB getDB() { return m_db; }
    public ODatabaseSession getSession() { return m_session; }

    private String m_last_error;
    public String getLastError() { return m_last_error; }
    public void setLastError(String last_error) { m_last_error = last_error; }

    public boolean isConnect() { return m_db != null && m_session != null; }

    public OrientDBDriver() {}

    public void connect() { connect(DEFAULT_ADDRESS, DEFAULT_USERNAME, DEFAULT_PASSWORD); }

    public void init(String address, String username, String password, String db_name, String db_username, String db_password) {
        try {
            m_db = new OrientDB(address, username, password, OrientDBConfig.defaultConfig());
            m_session = m_db.open(db_name, db_username, db_password);
        }
        catch (Exception e) { JOptionPane.showMessageDialog(null, e.getMessage()); }
    }

    public void connect(String address, String username, String password) {
        m_db = new OrientDB(address, username, password, OrientDBConfig.defaultConfig());
    }

    public void createSession() { createSession(DEFAULT_DATABASE, DEFAULT_USERNAME, DEFAULT_PASSWORD); }

    public void createSession(String db_name, String db_username, String db_password) {
        m_session = m_db.open(db_name, db_username, db_password);
    }

    public void dropDatabase(String db_name) {
        m_db.drop(db_name);
    }

    public void close() {
        m_session.close();
        m_db.close();
    }

    public static OrientDBDriver getInstance() {
        if (INSTANCE == null) INSTANCE = new OrientDBDriver();
        return INSTANCE;
    }

    /* SQL REQUESTS */
    public void createClass(String class_str) {
        String query = "CREATE CLASS " + class_str;
    }

    public void createProperty(String property_str) {
        String query = "CREATE PROPERTY " + property_str;
    }

    public void createLink(String link_str) {
        String query = "CREATE LINK " + link_str;
    }

    public boolean initDetailClasses() {
        boolean result = true;
        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
//            OrientDBDriver.getInstance().getSession().begin();

            ArrayList<String> queries = new ArrayList<>();
            queries.add("CREATE CLASS Detail IF NOT EXISTS");
            queries.add("CREATE PROPERTY Detail.name STRING (MANDATORY TRUE)");
            queries.add("CREATE PROPERTY Detail.decimal_number STRING");
            queries.add("CREATE PROPERTY Detail.description STRING");
            queries.add("CREATE PROPERTY Detail.busy BOOLEAN (MANDATORY TRUE)");
            queries.add("CREATE PROPERTY Detail.deleted BOOLEAN");
            queries.add("CREATE PROPERTY Detail.created_at DATETIME (MANDATORY TRUE)");
            queries.add("CREATE PROPERTY Detail.updated_at DATETIME");
            queries.add("CREATE PROPERTY Detail.busy_user LINK OUser");

            queries.add("CREATE CLASS DetailParameter IF NOT EXISTS");
//            queries.add("CREATE PROPERTY DetailParameter.value ANY (MANDATORY TRUE)");
            queries.add("CREATE PROPERTY DetailParameter.value STRING (MANDATORY TRUE)");
            queries.add("CREATE PROPERTY DetailParameter.info STRING");
            queries.add("CREATE PROPERTY DetailParameter.detail_id LINK Detail");
            queries.add("CREATE PROPERTY Detail.params LINKSET DetailParameter");

            queries.add("CREATE CLASS DetailParameterType IF NOT EXISTS");
            queries.add("CREATE PROPERTY DetailParameterType.name STRING (MANDATORY TRUE)");
            queries.add("CREATE PROPERTY DetailParameterType.value_type STRING");

            queries.add("CREATE CLASS DetailName IF NOT EXISTS");
            queries.add("CREATE PROPERTY DetailName.name STRING");
            queries.add("CREATE PROPERTY DetailParameter.type LINK DetailParameterType");

            queries.add("CREATE INDEX Detail.decimal_number ON Detail (decimal_number) UNIQUE METADATA { \"ignoreNullValues\": true }");
            queries.add("CREATE INDEX DetailName.name ON DetailName (name) UNIQUE");
            queries.add("CREATE INDEX DetailParameterType.name ON DetailParameterType (name) UNIQUE");

            queries.add("CREATE INDEX Detail.all_search ON Detail(name, decimal_number, description, deleted) FULLTEXT ENGINE LUCENE METADATA {\"analyzer\": \"org.apache.lucene.analysis.ru.RussianAnalyzer\", \"indexRadix\": true, \"ignoreChars\": \"\", \"separatorChars\": \"\", \"minWordLength\": 1, \"allowLeadingWildcard\":true }");
            queries.add("CREATE INDEX DetailParameter.all_search ON DetailParameter(value, info) FULLTEXT ENGINE LUCENE METADATA {\"allowLeadingWildcard\":true }");
            queries.add("CREATE INDEX DetailParameterType.all_search ON DetailParameterType(name, value_type) FULLTEXT ENGINE LUCENE METADATA {\"allowLeadingWildcard\":true }");

            for (String query : queries) { OrientDBDriver.getInstance().getSession().command(query); }
//            OrientDBDriver.getInstance().getSession().commit();
        }
        catch (OException e) {
//            OrientDBDriver.getInstance().getSession().rollback();
//            OrientDBDriver.getInstance().setLastError(e.getMessage());
            System.out.println(e.getMessage());
            result = false;
        }
        return result;
    }

    public boolean initDetailData() {
        // Получение имен;
        String details_path = "C:\\База\\Детали";
        Set<String> uniq_names = new HashSet<>();

        HashMap<String, String> fullname_map = new HashMap<>();

        ArrayList<String> fullnames = FilesystemUtility.getAllDirsNamesInDir(details_path);
        for (String f : fullnames) {
            String[] parts = f.split(" - ");
            if (parts != null && parts.length > 1) {
                String decimal_number = parts[0].trim();
                String name = parts[1].trim();

                fullname_map.put(decimal_number, name);
                uniq_names.add(name);
            }
        }

        if (!uniq_names.isEmpty()) {
            try {
                OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
                OrientDBDriver.getInstance().getSession().begin();

                for (String name : uniq_names) {
                    OElement e = OrientDBDriver.getInstance().getSession().newElement("DetailName");
                    e.setProperty("name", name);
                    e.save();
                }
                OrientDBDriver.getInstance().getSession().commit();
            }
            catch (OException e) {
                OrientDBDriver.getInstance().getSession().rollback();
                System.out.println(e.getMessage());
                return false;
            }
        }

        if (!fullname_map.isEmpty()) {
            try {
                OrientDBDriver.getInstance().getSession().activateOnCurrentThread();

                for (HashMap.Entry<String,String> entry : fullname_map.entrySet()) {
                    OElement e = OrientDBDriver.getInstance().getSession().newElement("Detail");
                    e.setProperty("busy", false);
                    e.setProperty("busy_user", null);
                    e.setProperty("created_at", DateTimeUtility.timestamp());
                    e.setProperty("decimal_number", entry.getKey());
                    e.setProperty("deleted", false);
                    e.setProperty("description", "");
                    e.setProperty("name", entry.getValue());
                    e.setProperty("params", "");
                    e.setProperty("updated_at", DateTimeUtility.timestamp());
                    e.save();
                }
            }
            catch (OException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

//    public boolean fillDetailNameData() {
//        boolean result = true;
//        try {
//            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
//            OrientDBDriver.getInstance().getSession().begin();
//
//            ArrayList<String> detail_names = new ArrayList<>();
//            detail_names.add("Каркас");
//            detail_names.add("Прокладка");
//            detail_names.add("Шпилька");
//            detail_names.add("Планка");
//            detail_names.add("Скобка");
//            detail_names.add("Штырек");
//            detail_names.add("Корпус");
//            detail_names.add("Пластина");
//            detail_names.add("Шина");
//
//            for (String name : detail_names) {
//                OElement e = OrientDBDriver.getInstance().getSession().newElement("DetailName");
//                e.setProperty("name", name);
//                e.save();
//            }
//            OrientDBDriver.getInstance().getSession().commit();
//        }
//        catch (OException e) {
//            OrientDBDriver.getInstance().getSession().rollback();
////            OrientDBDriver.getInstance().setLastError(e.getMessage());
//            System.out.println(e.getMessage());
//            result = false;
//        }
//        return result;
//    }

//    public boolean fillDetailParameterType() {
//        boolean result = true;
//        try {
//            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
//            OrientDBDriver.getInstance().getSession().begin();
//
//            ArrayList<String> detail_names = new ArrayList<>();
//            detail_names.add("Каркас");
//            detail_names.add("Прокладка");
//            detail_names.add("Шпилька");
//            detail_names.add("Планка");
//            detail_names.add("Скобка");
//            detail_names.add("Штырек");
//            detail_names.add("Корпус");
//            detail_names.add("Пластина");
//            detail_names.add("Шина");
//
//            for (String name : detail_names) {
//                OElement e = OrientDBDriver.getInstance().getSession().newElement("DetailName");
//                e.setProperty("name", name);
//                e.save();
//            }
//            OrientDBDriver.getInstance().getSession().commit();
//        }
//        catch (OException e) {
//            OrientDBDriver.getInstance().getSession().rollback();
////            OrientDBDriver.getInstance().setLastError(e.getMessage());
//            System.out.println(e.getMessage());
//            result = false;
//        }
//        return result;
//    }


    // CREATE CLASS Detail
//    CREATE PROPERTY Detail.description STRING
//    CREATE PROPERTY Detail.decimal_number STRING
//    CREATE PROPERTY Detail.busy BOOLEAN (MANDATORY TRUE)
//    CREATE PROPERTY Detail.created_at DATETIME (MANDATORY TRUE)
//    CREATE PROPERTY Detail.updated_at DATETIME
//    CREATE PROPERTY Detail.busy_user LINK OUser

    // CREATE CLASS DetailParameter
    // CREATE PROPERTY DetailParameter.value ANY (MANDATORY TRUE)
    //CREATE PROPERTY DetailParameter.detail_id LINK Detail

    // CREATE PROPERTY Detail.params LINKSET DetailParameter

    // CREATE CLASS DetailParameterType
    // CREATE PROPERTY DetailParameterType.custom_value BOOLEAN
    // CREATE PROPERTY DetailParameterType.enum BOOLEAN
    // CREATE PROPERTY DetailParameterType.name STRING (MANDATORY TRUE)
    // CREATE PROPERTY DetailParameterType.value_type STRING

    // CREATE CLASS DetailName
    // CREATE PROPERTY DetailName.name STRING

    // CREATE PROPERTY Detail.name LINK DetailName

    // CREATE PROPERTY DetailParameter.type LINK DetailParameterType
}
