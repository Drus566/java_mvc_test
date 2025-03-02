package iplm.data.service;

import iplm.data.repository.RepositoryType;
import iplm.data.repository.detail.DetailRepository;
import iplm.data.types.Detail;

import java.util.ArrayList;

public class DetailService {
    DetailRepository m_repository;
    RepositoryType m_repository_type;

    public void setRepositoryType(RepositoryType type) {
        m_repository_type = type;
    }

    public DetailService(RepositoryType type) {
        m_repository = new DetailRepository();
        m_repository_type = type;
    }

    public DetailService() {}

    public ArrayList<Detail> get(String request) { return m_repository.get(request, m_repository_type); }

    public Detail getById(String id) { return m_repository.getById(id, m_repository_type); }

    public ArrayList<Detail> getAll() { return m_repository.getAll(m_repository_type); }

    /* return id */
    public String add(Detail d) { return m_repository.add(d, m_repository_type); }

    /* return id */
    public String delete(String id) { return m_repository.delete(id, m_repository_type); }

    /* return id */
    public String update(Detail detail) { return m_repository.update(detail, m_repository_type); }

    public boolean rebuildIndex() { return m_repository.rebuildIndex(m_repository_type); }
}
