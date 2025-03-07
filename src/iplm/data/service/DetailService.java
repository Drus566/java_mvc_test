package iplm.data.service;

import iplm.data.repository.RepositoryType;
import iplm.data.repository.detail.DetailRepository;
import iplm.data.types.Detail;
import iplm.data.types.DetailName;
import iplm.data.types.DetailParameterType;

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

    public String addDetailName(String name) { return m_repository.addDetailName(name, m_repository_type); }
    public boolean deleteDetailName(String id) { return m_repository.deleteDetailName(id, m_repository_type); }
    public String updateDetailName(DetailName detail_name) { return m_repository.updateDetailName(detail_name, m_repository_type); }
    public ArrayList<DetailName> getDetailNames() { return m_repository.getDetailNames(m_repository_type); }

    public String addDetailParameterType(DetailParameterType detail_parameter_type) { return m_repository.addDetailParameterType(detail_parameter_type, m_repository_type); }
    public boolean deleteDetailParameterType(String id) { return m_repository.deleteDetailParameterType(id, m_repository_type); }
    public String updateDetailParameterType(DetailParameterType detail_parameter_type) { return m_repository.updateDetailParameterType(detail_parameter_type, m_repository_type); }
    public ArrayList<DetailParameterType> getDetailParameterTypes() { return m_repository.getDetailParameterTypes(m_repository_type); }

    public String addDetail(Detail detail) { return m_repository.addDetail(detail, m_repository_type); }
    public boolean deleteDetail(String id) { return m_repository.deleteDetail(id, m_repository_type); }
    public String updateDetail(Detail detail) { return m_repository.updateDetail(detail, m_repository_type); }
    public ArrayList<Detail> getDetails() { return m_repository.getDetails(m_repository_type); }


//    public ArrayList<Detail> get(String request) { return m_repository.get(request, m_repository_type); }
//
//    public Detail getById(String id) { return m_repository.getById(id, m_repository_type); }
//
//    public ArrayList<Detail> getAll() { return m_repository.getAll(m_repository_type); }
//
//    /* return id */
//    public String add(Detail d) { return m_repository.add(d, m_repository_type); }
//
//    /* return id */
//    public String delete(String id) { return m_repository.delete(id, m_repository_type); }
//
//    /* return id */
//    public String update(Detail detail) { return m_repository.update(detail, m_repository_type); }
//
//    public boolean rebuildIndex() { return m_repository.rebuildIndex(m_repository_type); }
}
