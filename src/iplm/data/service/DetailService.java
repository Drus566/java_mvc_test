package iplm.data.service;

import iplm.data.repository.RepositoryType;
import iplm.data.repository.detail.DetailRepository;
import iplm.data.types.Detail;

public class DetailService {
    DetailRepository m_repository;
    RepositoryType m_repository_type;

    public void setRepositoryType(RepositoryType type) {
        m_repository_type = type;
    }

    public DetailService(RepositoryType type) {
        m_repository_type = type;
    }

    public DetailService() {
        // Заголовки столбцов
    }

    /* return id */
    public String add(Detail d) {
        m_repository.add(d);
        return "";
    }

    /* return id */
    public String remove(String id) {
        m_repository.remove(id);
        return "";
    }

    /* return id */
    public String update(Detail d) {
        return "";
    }
}
