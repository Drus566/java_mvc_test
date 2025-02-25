package iplm.data.repository.detail;

import iplm.data.repository.RepositoryType;
import iplm.data.types.Detail;

import java.util.ArrayList;
import java.util.List;

public class DetailRepository {
    List<Detail> m_details;
    OrientDBDetailHandler m_orient_db_handler;

    public DetailRepository() {
        m_orient_db_handler = new OrientDBDetailHandler();
        m_details = new ArrayList<>();
    }

    public String add(Detail detail, RepositoryType type) {
        String result = "";
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.add(detail);
                break;
            case MYSQL:
                break;
        }
        return result;
    }

    public String remove(String id) {
        return "";
    }

    public Detail findById(int id) {
        return null;
    }

    public List<Detail> getAll() {
        return null;
    }

    public RepositoryType getType() {
        return RepositoryType.LOCAL;
    }

    public Object getParameter(String name) {
        return null;
    }
}
