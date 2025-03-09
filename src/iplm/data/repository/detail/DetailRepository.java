package iplm.data.repository.detail;

import iplm.data.repository.RepositoryType;
import iplm.data.types.Detail;
import iplm.data.types.DetailName;
import iplm.data.types.DetailParameterType;

import java.util.ArrayList;
import java.util.List;

public class DetailRepository {
    List<Detail> m_details;
    OrientDBDetailHandler m_orient_db_handler;

    public DetailRepository() {
        m_orient_db_handler = new OrientDBDetailHandler();
        m_details = new ArrayList<>();
    }

    /* Наименования деталей */
    public String addDetailName(String name, RepositoryType type) {
        String result = null;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.addDetailName(name);
                break;
            case MYSQL:
                break;
        }
        return result;
    }

    public boolean deleteDetailName(String id, RepositoryType type) {
        boolean result = false;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.deleteDetailName(id);
                break;
            case MYSQL:
                break;
        }
        return result;
    }

    public String updateDetailName(DetailName detail_name, RepositoryType type) {
        String result = null;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.updateDetailName(detail_name);
                break;
            case MYSQL:
                break;
        }
        return result;
    }

    public ArrayList<DetailName> getDetailNames(RepositoryType type) {
        ArrayList<DetailName> result = null;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.getDetailNames();
                break;
            case MYSQL:
                break;
        }
        return result;
    }


    /* Тип параметра деталей */
    public String addDetailParameterType(DetailParameterType detail_parameter_type, RepositoryType type) {
        String result = null;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.addDetailParameterType(detail_parameter_type);
                break;
            case MYSQL:
                break;
        }
        return result;
    }

    public boolean deleteDetailParameterType(String id, RepositoryType type) {
        boolean result = false;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.deleteDetailParameterType(id);
                break;
            case MYSQL:
                break;
        }
        return result;
    }

    public String updateDetailParameterType(DetailParameterType detail_parameter_type, RepositoryType type) {
        String result = null;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.updateDetailParameterType(detail_parameter_type);
                break;
            case MYSQL:
                break;
        }
        return result;
    }

    public ArrayList<DetailParameterType> getDetailParameterTypes(RepositoryType type) {
        ArrayList<DetailParameterType> result = null;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.getDetailParameterTypes();
                break;
            case MYSQL:
                break;
        }
        return result;
    }


    /* Детали */
    public String addDetail(Detail detail, RepositoryType type) {
        String result = null;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.addDetail(detail);
                break;
            case MYSQL:
                break;
        }
        return result;
    }

    public boolean deleteDetail(String id, RepositoryType type) {
        boolean result = false;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.deleteDetail(id);
                break;
            case MYSQL:
                break;
        }
        return result;
    }

    public String updateDetail(Detail detail, RepositoryType type) {
        String result = null;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.updateDetail(detail);
                break;
            case MYSQL:
                break;
        }
        return result;
    }

    public ArrayList<Detail> getAllDetails(boolean depends, RepositoryType type) {
        ArrayList<Detail> result = null;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.getAllDetails(depends);
                break;
            case MYSQL:
                break;
        }
        return result;
    }

    public ArrayList<Detail> getDetails(String request, boolean depends, RepositoryType type) {
        ArrayList<Detail> result = null;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.getDetails(request, depends);
                break;
            case MYSQL:
                break;
        }
        return result;
    }

    public Detail getDetailByID(String id, boolean depends, RepositoryType type) {
        Detail result = null;
        switch (type) {
            case ORIENTDB:
                result = m_orient_db_handler.getDetailByID(id, depends);
                break;
            case MYSQL:
                break;
        }
        return result;
    }




//    public ArrayList<Detail> getAll(RepositoryType type) {
//        ArrayList<Detail> result = null;
//        switch (type) {
//            case ORIENTDB:
//                result = m_orient_db_handler.getAll();
//                break;
//            case MYSQL:
//                break;
//        }
//        return result;
//    }
//
//    public Detail getById(String id, RepositoryType type) {
//        Detail result = null;
//        switch (type) {
//            case ORIENTDB:
//                result = m_orient_db_handler.getById(id);
//                break;
//            case MYSQL:
//                break;
//        }
//        return result;
//    }
//
//    public ArrayList<Detail> get(String request, RepositoryType type) {
//        ArrayList<Detail> result = null;
//        switch (type) {
//            case ORIENTDB:
//                result = m_orient_db_handler.get(request);
//                break;
//            case MYSQL:
//                break;
//        }
//        return result;
//    }
//
//    public String add(Detail detail, RepositoryType type) {
//        String result = "";
//        switch (type) {
//            case ORIENTDB:
//                result = m_orient_db_handler.add(detail);
//                break;
//            case MYSQL:
//                break;
//        }
//        return result;
//    }
//
//    public String delete(String id, RepositoryType type) {
//        String result = "";
//        switch (type) {
//            case ORIENTDB:
//                result = m_orient_db_handler.delete(id);
//                break;
//            case MYSQL:
//                break;
//        }
//        return result;
//    }
//
//    public String update(Detail detail, RepositoryType type) {
//        String result = "";
//        switch (type) {
//            case ORIENTDB:
//                result = m_orient_db_handler.update(detail);
//                break;
//            case MYSQL:
//                break;
//        }
//        return result;
//    }
//
//    public Detail findById(int id) {
//        return null;
//    }
//
//    public List<Detail> getAll() {
//        return null;
//    }
//
//    public RepositoryType getType() {
//        return RepositoryType.LOCAL;
//    }
//
//    public Object getParameter(String name) {
//        return null;
//    }
//
//    public boolean rebuildIndex(RepositoryType type) {
//        boolean result = false;
//        switch (type) {
//            case ORIENTDB:
//                result = m_orient_db_handler.rebuildIndex();
//                break;
//            case MYSQL:
//                break;
//        }
//        return result;
//    }
}
