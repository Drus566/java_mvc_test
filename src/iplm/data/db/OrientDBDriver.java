package iplm.data.db;

import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.*;

import javax.swing.*;

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
