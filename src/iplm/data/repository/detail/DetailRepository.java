package iplm.data.repository.detail;

import iplm.data.repository.RepositoryType;
import iplm.data.types.Detail;

import java.util.ArrayList;
import java.util.List;

public class DetailRepository {
    List<Detail> m_details;

    public DetailRepository() {
        m_details = new ArrayList<>();
    }

    public String add(Detail detail) {
        return "";
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
