package iplm.data.db;

import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.*;

public class OrientDBDriver {
    private static OrientDBDriver INSTANCE;

    private final static String DEFAULT_ADDRESS = "remote:localhost";
    private final static String DEFAULT_USERNAME = "root";
    private final static String DEFAULT_PASSWORD = "root";

    private final static String DEFAULT_DATABASE = "test";

    private OrientDB m_db;
    private ODatabaseSession m_session;

    public OrientDBDriver() {}

    public void connect() { connect(DEFAULT_ADDRESS, DEFAULT_USERNAME, DEFAULT_PASSWORD); }

    public void init(String address, String username, String password, String db_name, String db_username, String db_password) {
        m_db = new OrientDB(address, username, password, OrientDBConfig.defaultConfig());
        m_session = m_db.open(db_name, db_username, db_password);
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
}
