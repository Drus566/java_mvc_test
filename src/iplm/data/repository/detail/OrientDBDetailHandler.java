package iplm.data.repository.detail;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.db.record.ORecordLazySet;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import iplm.data.db.OrientDBDriver;
import iplm.data.types.Detail;
import iplm.data.types.DetailName;
import iplm.data.types.DetailParameter;
import iplm.data.types.DetailParameterType;
import iplm.utility.DateTimeUtility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

//SELECT name
//        FROM Detail
//        WHERE name LIKE '%ш%%и%'
//        LIMIT 5;

public class OrientDBDetailHandler {
    enum C {
        detail("Detail"),
        detail_parameter("DetailParameter"),
        detail_parameter_type("DetailParameterType"),
        detail_name("DetailName");

        private String m_string;
        C(String string) { m_string = string; }
        public String s() { return m_string; }
    }

    enum P {
        rid("@rid"),
        name("name"),
        decimal_number("decimal_number"),
        busy("busy"),
        created_at("created_at"),
        updated_at("updated_at"),
        user_busy("user_busy"),
        description("description"),
        deleted("deleted"),
        parameters("parameters"),
        enumeration("enum"),
        type("type"),
        value("value"),
        count("count"),
        value_type("value_type"),
        detail_id("detail_id"),
        params("params"),
        info("info"),
        custom_val("custom_val");

        private String m_string;
        P(String string) { m_string = string; }
        public String s() { return m_string; }
    }

    OrientDB m_db;
    ODatabaseSession m_session;

    OrientDBDetailHandler() {
        m_db = OrientDBDriver.getInstance().getDB();
        m_session = OrientDBDriver.getInstance().getSession();
    }

    /******* Detail Name ************/
    synchronized public String addDetailName(String name) {
        if (!OrientDBDriver.getInstance().isConnect()) {
            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
            return null;
        }

        String result = null;
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(C.detail_name.s());
        query.append(" CONTENT { name: '");
        query.append(name).append("'}");

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query.toString());

            while (rs.hasNext()) {
                OResult item = rs.next();
                result = item.getProperty(P.rid.s()).toString();
                break;
            }
        }
        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }

        return result;
    }

    synchronized public boolean deleteDetailName(String id) {
        if (!OrientDBDriver.getInstance().isConnect()) {
            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
            return false;
        }

        boolean result = false;
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(C.detail_name.s());
        query.append(" WHERE @rid = ").append(id);

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query.toString());

            while (rs.hasNext()) {
                OResult item = rs.next();
                long count = item.getProperty(P.count.s());
                if (count > 0) result = true;
                break;
            }
        }
        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }

        return result;
    }

    synchronized public String updateDetailName(DetailName detail_name) {
        if (!OrientDBDriver.getInstance().isConnect()) {
            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
            return null;
        }

        String result = null;
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(C.detail_name.s());
        query.append(" SET ").append(P.name.s()).append(" = '").append(detail_name.name);
        query.append("' RETURN AFTER @rid WHERE @rid = ").append(detail_name.id);

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query.toString());

            while (rs.hasNext()) {
                OResult item = rs.next();
                result = item.getProperty(P.rid.s()).toString();
                break;
            }
        }
        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }

        return result;
    }

    synchronized public ArrayList<DetailName> getDetailNames() {
        if (!OrientDBDriver.getInstance().isConnect()) {
            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
            return null;
        }

        ArrayList<DetailName> result = null;
        String query = "SELECT * FROM ?";

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query, C.detail_name.s());

            while (rs.hasNext()) {
                if (result == null) result = new ArrayList<>();
                OResult item = rs.next();
                DetailName dn = new DetailName();
                dn.id = item.getProperty(P.rid.s()).toString();
                dn.name = item.getProperty(P.name.s()).toString();
                result.add(dn);
            }
        }
        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }

        return result;
    }

    /************** Detail Parameter Type ********************/

    synchronized public String addDetailParameterType(DetailParameterType detail_parameter_type) {
        if (!OrientDBDriver.getInstance().isConnect()) {
            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
            return null;
        }

        String result = null;
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(C.detail_parameter_type.s());
        query.append(" CONTENT { name: '").append(detail_parameter_type.name).append("'");
        query.append(",");
        query.append("value_type: '").append(detail_parameter_type.type).append("'");
        query.append("}");

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query.toString());

            while (rs.hasNext()) {
                OResult item = rs.next();
                result = item.getProperty(P.rid.s()).toString();
                break;
            }
        }
        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }

        return result;
    }

    synchronized public boolean deleteDetailParameterType(String id) {
        if (!OrientDBDriver.getInstance().isConnect()) {
            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
            return false;
        }

        boolean result = false;
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(C.detail_parameter_type.s());
        query.append(" WHERE @rid = ").append(id);

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query.toString());

            while (rs.hasNext()) {
                OResult item = rs.next();
                long count = item.getProperty(P.count.s());
                if (count > 0) result = true;
                break;
            }
        }
        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }

        return result;
    }

    synchronized public String updateDetailParameterType(DetailParameterType detail_parameter_type) {
        if (!OrientDBDriver.getInstance().isConnect()) {
            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
            return null;
        }

        String result = null;
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(C.detail_parameter_type.s());
        query.append(" SET ").append(P.name.s()).append(" = '").append(detail_parameter_type.name);
        query.append("', ").append(P.value_type).append(" = '").append(detail_parameter_type.type);
        query.append(" RETURN AFTER @rid WHERE @rid = ").append(detail_parameter_type.id);

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query.toString());

            while (rs.hasNext()) {
                OResult item = rs.next();
                result = item.getProperty(P.rid.s()).toString();
                break;
            }
        }
        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }

        return result;
    }

    synchronized public ArrayList<DetailParameterType> getDetailParameterTypes() {
        if (!OrientDBDriver.getInstance().isConnect()) {
            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
            return null;
        }

        ArrayList<DetailParameterType> result = null;
        String query = "SELECT * FROM ?";

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query, C.detail_parameter_type.s());

            while (rs.hasNext()) {
                if (result == null) result = new ArrayList<>();
                OResult item = rs.next();
                DetailParameterType dpt = new DetailParameterType();
                dpt.id = item.getProperty(P.rid.s()).toString();
                dpt.name = item.getProperty(P.name.s()).toString();
                dpt.type = item.getProperty(P.value_type.s()).toString();
                result.add(dpt);
            }
        }
        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }

        return result;
    }

    /************** Detail ********************/

    synchronized public String addDetail(Detail detail) {
        if (!OrientDBDriver.getInstance().isConnect()) {
            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
            return null;
        }

        String result = null;

        OElement detail_element = OrientDBDriver.getInstance().getSession().newInstance(C.detail.s());
        detail_element.setProperty(P.busy.s(), false);
        detail_element.setProperty(P.user_busy.s(), null);
        detail_element.setProperty(P.created_at.s(), DateTimeUtility.timestamp());
        if (!detail.decimal_number.isEmpty()) detail_element.setProperty(P.decimal_number.s(), detail.decimal_number);
        detail_element.setProperty(P.name.s(), detail.name);
        detail_element.setProperty(P.description.s(), detail.description);
        detail_element.setProperty(P.updated_at.s(), DateTimeUtility.timestamp());

        ArrayList<OElement> param_elements = null;
        if (detail.params != null) {
            for (DetailParameter p : detail.params) {
                if (param_elements == null) param_elements = new ArrayList<>();
                OElement parameter = OrientDBDriver.getInstance().getSession().newInstance(C.detail_parameter.s());
                parameter.setProperty(P.value.s(), p.value);
                parameter.setProperty(P.type.s(), new ORecordId(p.type.id));
                param_elements.add(parameter);
            }
        }

        // Создание детали
        try { detail_element.save(); }
        catch (Exception e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
            return null;
        }

        try {
            OrientDBDriver.getInstance().getSession().begin();
            ArrayList<ORecordId> param_ids = new ArrayList<>();

            detail.id = detail_element.getIdentity().toString();

            if (param_elements != null) {
                for (int i = 0; i < param_elements.size(); i++) {
                    OElement p = param_elements.get(i);
                    p.setProperty(P.detail_id.s(), new ORecordId(detail.id));
                    // Создание параметра
                    p.save();
                    detail.params.get(i).id = p.getIdentity().toString();
                    param_ids.add((ORecordId) p.getIdentity());
                }
                detail_element.setProperty(P.params.s(), param_ids);
                // Обновление детали (добавление связи с параметрами)
                detail_element.save();
            }
            result = detail.id;
            OrientDBDriver.getInstance().getSession().commit();
        }
        catch (Exception e) {
            OrientDBDriver.getInstance().getSession().rollback();
            String error_message = e.getMessage();
            if (error_message == null) error_message = e.toString();
            OrientDBDriver.getInstance().setLastError(error_message);
            result = null;
        }
        return result;
    }

    synchronized public boolean deleteDetail(String id) {
        boolean result = false;

        if (!OrientDBDriver.getInstance().isConnect()) {
            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
            return result;
        }

        OElement detail_element = null;
        try { detail_element = OrientDBDriver.getInstance().getSession().load(new ORecordId(id)); }
        catch (Exception e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
            return result;
        }

        try {
            OrientDBDriver.getInstance().getSession().begin();

            detail_element.setProperty(P.deleted.s(), true);
            detail_element.save();
            result = true;

            OrientDBDriver.getInstance().getSession().commit();
        }
        catch (Exception e) {
            OrientDBDriver.getInstance().getSession().rollback();
            OrientDBDriver.getInstance().setLastError(e.getMessage());
            result = false;
        }
        return result;
    }


//    public boolean dropDetail(String id) {
//        boolean result = false;
//
//        if (!OrientDBDriver.getInstance().isConnect()) {
//            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
//            return result;
//        }
//
//        OElement detail_element = null;
//        try { detail_element = OrientDBDriver.getInstance().getSession().load(new ORecordId(id)); }
//        catch (Exception e) {
//            OrientDBDriver.getInstance().setLastError(e.getMessage());
//            return result;
//        }
//
//        ORecordLazySet lazy_set = detail_element.getProperty(P.params.s());
//        ArrayList<ORecordId> params_ids = null;
//        if (lazy_set != null && !lazy_set.isEmpty()) {
//            if (params_ids == null) params_ids = new ArrayList<>();
//            Iterator<OIdentifiable> iterator = lazy_set.iterator();
//            while (iterator.hasNext()) {
//                OIdentifiable element = iterator.next();
//                params_ids.add((ORecordId) element.getRecord().getIdentity());
//            }
//        }
//
//        try {
//            OrientDBDriver.getInstance().getSession().begin();
//
//            if (params_ids != null) {
//                for (ORecordId ri : params_ids) {
//                    OrientDBDriver.getInstance().getSession().delete(new ORecordId(ri));
//                }
//            }
//
//            OrientDBDriver.getInstance().getSession().delete(detail_element.getIdentity());
//            result = true;
//
//            OrientDBDriver.getInstance().getSession().commit();
//        }
//        catch (Exception e) {
//            OrientDBDriver.getInstance().getSession().rollback();
//            OrientDBDriver.getInstance().setLastError(e.getMessage());
//            result = false;
//        }
//        return result;
//    }


    synchronized public String updateDetail(Detail detail) {
        if (!OrientDBDriver.getInstance().isConnect()) {
            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
            return null;
        }

        String result = null;

        //            OElement detail_element = OrientDBDriver.getInstance().getSession().load(new ORecordId(current_id));
//
//            boolean busy = detail_element.getProperty("busy");
//            if (busy) {
//                JOptionPane.showMessageDialog(null, "Деталь уже редактируется другим пользователем", "Информация", JOptionPane.INFORMATION_MESSAGE);
//                return;
//            }
        OElement detail_element = null;
        try { detail_element = OrientDBDriver.getInstance().getSession().load(new ORecordId(detail.id)); }
        catch (Exception e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
            return result;
        }

        detail_element.setProperty(P.busy.s(), false);
        detail_element.setProperty(P.user_busy.s(), null);
//        detail_element.setProperty(P.created_at.s(), DateTimeUtility.timestamp());
        detail_element.setProperty(P.decimal_number.s(), detail.decimal_number);
        detail_element.setProperty(P.name.s(), detail.name);
        detail_element.setProperty(P.description.s(), detail.description);
        detail_element.setProperty(P.updated_at.s(), DateTimeUtility.timestamp());

        ArrayList<OElement> new_param_elements = null;
        if (detail.params != null) {
            for (DetailParameter p : detail.params) {
                if (new_param_elements == null) new_param_elements = new ArrayList<>();
                OElement parameter = OrientDBDriver.getInstance().getSession().newInstance(C.detail_parameter.s());
                parameter.setProperty(P.value.s(), p.value);
                parameter.setProperty(P.type.s(), new ORecordId(p.type.id));
                new_param_elements.add(parameter);
            }
        }

        ORecordLazySet lazy_set = detail_element.getProperty(P.params.s());
        ArrayList<ORecordId> last_params_ids = null;
        if (lazy_set != null && !lazy_set.isEmpty()) {
            if (last_params_ids == null) last_params_ids = new ArrayList<>();
            Iterator<OIdentifiable> iterator = lazy_set.iterator();
            while (iterator.hasNext()) {
                OIdentifiable element = iterator.next();
                last_params_ids.add((ORecordId) element.getRecord().getIdentity());
            }
        }

        try {
            OrientDBDriver.getInstance().getSession().begin();

            if (last_params_ids != null) {
                for (ORecordId ri : last_params_ids) {
                    OrientDBDriver.getInstance().getSession().delete(new ORecordId(ri));
                }
            }

            ArrayList<ORecordId> param_ids = new ArrayList<>();

            if (new_param_elements != null) {
                for (int i = 0; i < new_param_elements.size(); i++) {
                    OElement p = new_param_elements.get(i);
                    p.setProperty(P.detail_id.s(), new ORecordId(detail.id));
                    // Создание параметра
                    p.save();
                    detail.params.get(i).id = p.getIdentity().toString();
                    param_ids.add((ORecordId) p.getIdentity());
                }
                detail_element.setProperty(P.params.s(), param_ids);
            }
            // Обновление детали (добавление связи с параметрами)
            detail_element.save();
            result = detail.id;
            OrientDBDriver.getInstance().getSession().commit();
        }
        catch (Exception e) {
            OrientDBDriver.getInstance().getSession().rollback();
            OrientDBDriver.getInstance().setLastError(e.getMessage());
            result = null;
        }
        return result;
    }

    synchronized public ArrayList<Detail> getAllDetails(boolean depends) {
        if (!OrientDBDriver.getInstance().isConnect()) {
            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
            return null;
        }

        ArrayList<Detail> result = new ArrayList<>();

        String query = "SELECT * FROM ? WHERE deleted = false OR deleted IS NULL";
        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OrientDBDriver.getInstance().getSession().begin();

            // Получить список деталей
            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query, C.detail.s());

            while (rs.hasNext()) {
                OResult item = rs.next();

                Detail d = new Detail();
                d.id = item.getProperty(P.rid.s()).toString();
                d.name = item.getProperty(P.name.s()).toString();
                d.decimal_number = item.getProperty(P.decimal_number.s());
                d.description = item.getProperty(P.description.s());
                d.busy = item.getProperty(P.busy.s());
                d.user_busy = item.getProperty(P.user_busy.s());
                d.created_at = item.getProperty(P.created_at.s()).toString();
                d.updated_at = item.getProperty(P.updated_at.s()).toString();
                ORecordLazySet lazy_set = item.getProperty(P.params.s());

                if (depends && lazy_set != null && !lazy_set.isEmpty()) {
                    ArrayList<ORecordId> ids = new ArrayList<>();
                    Iterator<OIdentifiable> iterator = lazy_set.iterator();

                    while (iterator.hasNext()) {
                        OIdentifiable element = iterator.next();
                        ids.add((ORecordId) element.getRecord().getIdentity());
                    }

                    if (ids != null && !ids.isEmpty()) {
                        ArrayList<DetailParameter> params = new ArrayList<>();
                        for (ORecordId rid : ids) {
                            DetailParameter dp = new DetailParameter();

                            // Получить параметр детали по ID
                            OElement p_element = OrientDBDriver.getInstance().getSession().load(new ORecordId(rid));
                            dp.id = p_element.getProperty(P.rid.s()).toString();
                            dp.detail_id = p_element.getProperty(P.detail_id.s()).toString();
                            dp.value = p_element.getProperty(P.value.s());

                            OIdentifiable t_id = p_element.getProperty(P.type.s());
                            // Получить тип параметра детали по ID
                            OElement pt_element = OrientDBDriver.getInstance().getSession().load(t_id.getRecord().getIdentity());
                            DetailParameterType dpt = new DetailParameterType();
                            dpt.name = pt_element.getProperty(P.name.s());
                            dpt.type = pt_element.getProperty(P.value_type.s()).toString();

                            dp.type = dpt;

                            params.add(dp);
                        }
                        d.params = params;
                    }
                }
                result.add(d);
            }
            OrientDBDriver.getInstance().getSession().commit();
        }
        catch (Exception e) {
            OrientDBDriver.getInstance().getSession().rollback();
            OrientDBDriver.getInstance().setLastError(e.getMessage());
            result = null;
        }
        return result;
    }

    synchronized public ArrayList<Detail> getDetails(String request, boolean depends) {
        if (OrientDBDriver.getInstance().getSession() == null) {
            OrientDBDriver.getInstance().setLastError("Нет подключения к БД");
            return null;
        }

        ArrayList<Detail> result = new ArrayList<>();

        String query = "SELECT FROM Detail WHERE SEARCH_CLASS(?) = true;";
//        SELECT FROM Detail WHERE SEARCH_CLASS('deleted:true && шт~ || шт* || *шт* || шт') = true;
//        SELECT FROM Detail WHERE SEARCH_CLASS('(шина~ || шина* || *шина* || шина) AND -deleted:true', {
//                "sort": [ { 'field': 'name', reverse:true, type:'STRING' }]
//}) = true;
//

//        String q = "SELECT FROM Detail WHERE SEARCH_CLASS('(шина~ || шина* || *шина* || шина) AND -deleted:true', { \"sort\": [ { 'field': 'name', reverse:true, type:'STRING' }]}) = true;";
        // SELECT FROM Detail WHERE SEARCH_CLASS("~ || * || *sad* || sad) = true
        //
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT FROM Detail WHERE SEARCH_CLASS(");
        sb.append("'(");
        sb.append(request).append(" || ");
        sb.append(request).append("*").append(" || ");
        sb.append("*").append(request).append("*").append(" || ");
        sb.append(request).append("~");
        sb.append(") AND -").append(P.deleted.s()).append(":").append("true'");
        sb.append(", { \"sort\": [{ 'field': 'name', reverse:false, type:'STRING' }]}");
        sb.append(") = true;");

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OrientDBDriver.getInstance().getSession().begin();

            OResultSet rs = OrientDBDriver.getInstance().getSession().command(sb.toString());

            while (rs.hasNext()) {
                OResult item = rs.next();

                Detail d = new Detail();
                d.id = item.getProperty(P.rid.s()).toString();
                d.name = item.getProperty(P.name.s()).toString();
                d.decimal_number = item.getProperty(P.decimal_number.s());
                d.description = item.getProperty(P.description.s());
                d.busy = item.getProperty(P.busy.s());
                d.user_busy = item.getProperty(P.user_busy.s());
                d.created_at = item.getProperty(P.created_at.s()).toString();
                d.updated_at = item.getProperty(P.updated_at.s()).toString();
                ORecordLazySet lazy_set = item.getProperty(P.params.s());

                if (depends && lazy_set != null && !lazy_set.isEmpty()) {
                    ArrayList<ORecordId> ids = new ArrayList<>();
                    Iterator<OIdentifiable> iterator = lazy_set.iterator();

                    while (iterator.hasNext()) {
                        OIdentifiable element = iterator.next();
                        ids.add((ORecordId) element.getRecord().getIdentity());
                    }

                    if (ids != null && !ids.isEmpty()) {
                        ArrayList<DetailParameter> params = new ArrayList<>();
                        for (ORecordId rid : ids) {
                            DetailParameter dp = new DetailParameter();

                            // Получить параметр детали по ID
                            OElement p_element = OrientDBDriver.getInstance().getSession().load(new ORecordId(rid));
                            dp.id = p_element.getProperty(P.rid.s()).toString();
                            dp.detail_id = p_element.getProperty(P.detail_id.s()).toString();
                            dp.value = p_element.getProperty(P.value.s());

                            OIdentifiable t_id = p_element.getProperty(P.type.s());
                            // Получить тип параметра детали по ID
                            OElement pt_element = OrientDBDriver.getInstance().getSession().load(t_id.getRecord().getIdentity());
                            DetailParameterType dpt = new DetailParameterType();
                            dpt.name = pt_element.getProperty(P.name.s());
                            dpt.type = pt_element.getProperty(P.value_type.s()).toString();

                            dp.type = dpt;

                            params.add(dp);
                        }
                        d.params = params;
                    }
                }
                result.add(d);
            }
            OrientDBDriver.getInstance().getSession().commit();
        }
        catch (OException e) {
            OrientDBDriver.getInstance().getSession().rollback();
            OrientDBDriver.getInstance().setLastError(e.getMessage());
            result = null;
        }

        return result;
    }

    synchronized public Detail getDetailByID(String id, boolean depends) {
        if (OrientDBDriver.getInstance().getSession() == null) {
            OrientDBDriver.getInstance().setLastError("Нет подключения к БД");
            return null;
        }

        Detail result = null;

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OrientDBDriver.getInstance().getSession().begin();

            ORecordLazySet lazy_set = null;

            String query = "SELECT * FROM ? WHERE @rid = " + id + " AND (deleted = false OR deleted IS NULL)";
            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query, C.detail.s());
            while (rs.hasNext()) {
                if (result == null) result = new Detail();
                OResult item = rs.next();
                result.id = item.getProperty(P.rid.s()).toString();
                result.name = item.getProperty(P.name.s()).toString();
                result.decimal_number = item.getProperty(P.decimal_number.s());
                result.description = item.getProperty(P.description.s());
                result.busy = item.getProperty(P.busy.s());
                result.user_busy = item.getProperty(P.user_busy.s());
                result.created_at = item.getProperty(P.created_at.s()).toString();
                result.updated_at = item.getProperty(P.updated_at.s()).toString();
                lazy_set = item.getProperty(P.params.s());
                break;
            }

            if (result == null) return result;

            if (depends && lazy_set != null && !lazy_set.isEmpty()) {
                ArrayList<ORecordId> ids = new ArrayList<>();
                Iterator<OIdentifiable> iterator = lazy_set.iterator();

                while (iterator.hasNext()) {
                    OIdentifiable element = iterator.next();
                    ids.add((ORecordId) element.getRecord().getIdentity());
                }

                if (ids != null && !ids.isEmpty()) {
                    ArrayList<DetailParameter> params = new ArrayList<>();
                    for (ORecordId rid : ids) {
                        DetailParameter dp = new DetailParameter();

                        // Получить параметр детали по ID
                        OElement p_element = OrientDBDriver.getInstance().getSession().load(new ORecordId(rid));
                        dp.id = p_element.getProperty(P.rid.s()).toString();
                        dp.detail_id = p_element.getProperty(P.detail_id.s()).toString();
                        dp.value = p_element.getProperty(P.value.s());

                        OIdentifiable t_id = p_element.getProperty(P.type.s());
                        // Получить тип параметра детали по ID
                        OElement pt_element = OrientDBDriver.getInstance().getSession().load(t_id.getRecord().getIdentity());
                        DetailParameterType dpt = new DetailParameterType();
                        dpt.id = pt_element.getProperty(P.rid.s()).toString();
                        dpt.name = pt_element.getProperty(P.name.s());
                        dpt.type = pt_element.getProperty(P.value_type.s()).toString();

                        dp.type = dpt;

                        params.add(dp);
                    }
                    result.params = params;
                }
            }
            OrientDBDriver.getInstance().getSession().commit();
        }
        catch (OException e) {
            OrientDBDriver.getInstance().getSession().rollback();
            String error = e.getMessage();
            if (error == null) error = e.toString();
            OrientDBDriver.getInstance().setLastError(error);
            result = null;
        }

        return result;
    }

//    public boolean rebuildIndex() {
//        boolean result = true;
//        try {
//            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
//            OrientDBDriver.getInstance().getSession().command("REBUILD INDEX *");
//        }
//        catch (OException e) {
//            OrientDBDriver.getInstance().setLastError(e.getMessage());
//            result = false;
//        }
//        return result;
//    }






//    insert into animal set name = 'dog', children = [<rid>]
    //    {"analyzer": "org.apache.lucene.analysis.ru.RussianAnalyzer", "indexRadix": true, "ignoreChars": "", "separatorChars": "", "minWordLength": 1, "allowLeadingWildcard":true}

//    {"analyzer": "org.apache.lucene.analysis.ru.RussianAnalyzer", "indexRadix": true, "ignoreChars": "-.", "separatorChars": " ",
//            "minWordLength": 1}
//                    "allowLeadingWildcard": true

//    SELECT name, decimal_number, @rid
//    FROM Detail
//    WHERE @rid IN (
//            SELECT rid
//    FROM INDEX:Detail.name_decimal_number_description
//            WHERE key = '\\500** || 500~'
//    )


//    {"analyzer": "org.apache.lucene.analysis.ru.RussianAnalyzer"}
    //        String query = "select from index:Detail.name_decimal_number_description where key = ?";


    // ОБНОВЛЕНИЕ LINK LIST
//    orientdb {db=test}> insert into ParentDoc CONTENT {"name": "PARENTDOC", "children": [#43:0,#42:0]}
    // insert into DetailParameter CONTENT {created_at: x, custom_val: false, deleted: false, enumeration: false, name: "sample_name", type: "Строка", value: "Sample value"} RETURN AFTER @rid
//    UPDATE ParentDoc MERGE {children:[#12:2, #!12:33]} WHERE @rid = #46:0
//    UPDATE ParentDoc MERGE {children:[#14:44]} RETURN AFTER @rid WHERE @rid = #48:0

//    public String delete(String id) {
//        if (OrientDBDriver.getInstance().getSession() == null) {
//            OrientDBDriver.getInstance().setLastError("Нет подключения к БД");
//            return null;
//        }
//
//        String query = "UPDATE Detail SET deleted = true RETURN AFTER @rid WHERE @rid = " + id;
//
//        String result = null;
//        try {
//            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
//            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query, id);
//
//            while (rs.hasNext()) {
//                OResult item = rs.next();
//                result = item.getProperty(P.rid.s()).toString();
//            }
//        }
//        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }
//
//        return result;
//    }
//
//    public String update(Detail detail) {
//        if (OrientDBDriver.getInstance().getSession() == null) {
//            OrientDBDriver.getInstance().setLastError("Нет подключения к БД");
//            return null;
//        }
//
//        String super_rid = null;
//
//
//        try {
//            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
//            OrientDBDriver.getInstance().getSession().begin();
//
//            OElement doc = OrientDBDriver.getInstance().getSession().load(new ORecordId(detail.id));
//            String current_timestamp = DateTimeUtility.timestamp();
//
//            StringBuilder update_detail_query = new StringBuilder();
//            update_detail_query.append("UPDATE Detail MERGE { name: ?, decimal_number: ?, description: ?, params: [");
//
//            /* CREATE NEW PARAMETERS */
//            boolean first = true;
//            for (DetailParameterType dp : detail.params) {
//                StringBuilder sb = new StringBuilder();
//                sb.append("insert into DetailParameter CONTENT {created_at: ?, custom_val: ?, deleted: ?, enumeration: ?, name: ?, type: ?, value: ?}");
//                OResultSet rs = OrientDBDriver.getInstance().getSession().command(sb.toString(), current_timestamp, false, false, false, dp.name, "Строка", dp.value);
//                while (rs.hasNext()) {
//                    if (!first) update_detail_query.append(",");
//                    OResult item = rs.next();
//                    String result = item.getProperty(P.rid.s()).toString();
//                    update_detail_query.append(result);
//                    first = false;
//                }
//            }
//            update_detail_query.append("]} RETURN AFTER @rid WHERE @rid = ").append(detail.id);
//
//            /* DELETE OLD PARAMS */
//            String delete_detail_params = "DELETE FROM DetailParameter WHERE @rid in (select params FROM Detail WHERE @rid = " + detail.id + ")";
//            OrientDBDriver.getInstance().getSession().command(delete_detail_params);
//
//            /* ADD NEW PARAMS */
//
//            OResultSet rs = OrientDBDriver.getInstance().getSession().command(update_detail_query.toString(), detail.name, detail.decimal_number, detail.description);
//            while (rs.hasNext()) {
//                OResult item = rs.next();
//                super_rid = item.getProperty(P.rid.s()).toString();
//            }
//
//            OrientDBDriver.getInstance().getSession().commit();
//        }
//        catch (OException e) {
//            OrientDBDriver.getInstance().getSession().rollback();
//            OrientDBDriver.getInstance().setLastError(e.getMessage());
//            return null;
//        }
//
//        return super_rid;
//    }
//
//    public ArrayList<Detail> get(String request) {
//        if (OrientDBDriver.getInstance().getSession() == null) {
//            OrientDBDriver.getInstance().setLastError("Нет подключения к БД");
//            return null;
//        }
//
//        ArrayList<Detail> result = null;
//
//        String query = "SELECT FROM Detail WHERE SEARCH_CLASS(?) = true;";
//
////        String query = "SELECT name, decimal_number, @rid FROM Detail \n" +
////        "WHERE decimal_number LIKE '?';\n";
//
//
//
////        String query = "SELECT name, decimal_number, @rid\n" +
////                "FROM Detail \n" +
////                "WHERE @rid IN (\n" +
////                "    SELECT rid\n" +
////                "    FROM INDEX:Detail.russian \n" +
////                "    WHERE key = ?\n" +
////                ")\n";
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(request).append("~").append(" || ");
//        sb.append(request).append("*").append(" || ");
//        sb.append("*").append(request).append("*").append(" || ");
//        sb.append(request);
//
//        try {
//            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
//            OResultSet rs = OrientDBDriver.getInstance().getSession().query(query, sb.toString());
//
//            while (rs.hasNext()) {
//                if (result == null) result = new ArrayList<>();
//                Detail d = new Detail();
//                OResult item = rs.next();
//                d.name = item.getProperty(P.name.s());
//                d.decimal_number = item.getProperty(P.decimal_number.s());
//                d.description = item.getProperty(P.description.s());
//                d.id = item.getProperty(P.rid.s()).toString();
//                result.add(d);
//            }
//        }
//        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }
//
//        return result;
//    }
//
//    public boolean rebuildIndex() {
//        boolean result = true;
//        try {
//            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
//            OrientDBDriver.getInstance().getSession().command("REBUILD INDEX *");
//        }
//        catch (OException e) {
//            OrientDBDriver.getInstance().setLastError(e.getMessage());
//            result = false;
//        }
//        return result;
//    }
//
//    public ArrayList<Detail> getAll() {
//        if (OrientDBDriver.getInstance().getSession() == null) {
//            OrientDBDriver.getInstance().setLastError("Нет подключения к БД");
//            return null;
//        }
//
//        ArrayList<Detail> result = null;
//        String query = "SELECT * FROM Detail";
//
//        try {
//            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
//            OResultSet rs = OrientDBDriver.getInstance().getSession().query(query);
//
//            while (rs.hasNext()) {
//                if (result == null) result = new ArrayList<>();
//                Detail d = new Detail();
//                OResult item = rs.next();
//                d.name = item.getProperty(P.name.s());
//                d.decimal_number = item.getProperty(P.decimal_number.s());
//                d.description = item.getProperty(P.description.s());
//                d.id = item.getProperty(P.rid.s()).toString();
//                result.add(d);
//            }
//        }
//        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }
//
//        return result;
//    }
//
//    public Detail getById(String id) {
//        if (OrientDBDriver.getInstance().getSession() == null) {
//            OrientDBDriver.getInstance().setLastError("Нет подключения к БД");
//            return null;
//        }
//
//        Detail result = null;
//        String query = "SELECT * FROM Detail WHERE @rid = " + id;
//
//        try {
//            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
//            OResultSet rs = OrientDBDriver.getInstance().getSession().query(query);
//
//            while (rs.hasNext()) {
//                if (result == null) result = new Detail();
//                OResult item = rs.next();
//                result.name = item.getProperty(P.name.s());
//                result.decimal_number = item.getProperty(P.decimal_number.s());
//                result.description = item.getProperty(P.description.s());
//                result.id = item.getProperty(P.rid.s()).toString();
//                List<Object> el = item.getProperty("parameters");
//
//                System.out.println(el);
//
//                if (el == null || el.isEmpty()) break;
//
//                for (Object o : el) {
//                    OResultInternal ri = (OResultInternal) o;
////                    System.out.println(ri);
//                    ri.getPropertyNames();
//                    DetailParameterType dp = new DetailParameterType();
//                    dp.name = ri.getProperty(P.name.s());
//                    dp.value = ri.getProperty(P.value.s()).toString();
//                    result.params.add(dp);
//                }
////                for (ODocument p : pp) {
////                    System.out.println(p.getPropertyNames());
////                }
////                System.out.println(item.getProperty("parameters"));
////                for (DetailParameter dp : result.params) {
////                    dp.name = item.getProperty()
////                }
//                break;
//            }
//        }
//        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }
//
//        return result;
//    }
//
//    public String add(Detail detail) {
//        if (OrientDBDriver.getInstance().getSession() == null) {
//            OrientDBDriver.getInstance().setLastError("Нет подключения к БД");
//            return null;
//        }
//
//        String result;
//        String current_timestamp = DateTimeUtility.timestamp();
//
//        OElement doc = OrientDBDriver.getInstance().getSession().newInstance(C.detail.s());
//        doc.setProperty(P.name.s(), detail.name);
//        doc.setProperty(P.decimal_number.s(), detail.decimal_number);
//        doc.setProperty(P.description.s(), detail.description);
//        doc.setProperty(P.busy.s(), false);
//        doc.setProperty(P.user_busy.s(), detail.user_busy);
//        doc.setProperty(P.deleted.s(), false);
//        doc.setProperty(P.created_at.s(), current_timestamp);
//
//        List<OElement> parameters = new ArrayList<>();
//        for (DetailParameterType dp : detail.params) {
//            OElement p = OrientDBDriver.getInstance().getSession().newInstance(C.detail_parameter.s());
//            p.setProperty(P.name.s(), dp.name);
//            p.setProperty(P.type.s(), dp.type);
//            p.setProperty(P.deleted.s(), false);
//            p.setProperty(P.custom_val.s(), false);
//            p.setProperty(P.created_at.s(), current_timestamp);
//            p.setProperty(P.enumeration.s(), false);
//            p.setProperty(P.value.s(), dp.value);
//            parameters.add(p);
//        }
//        doc.setProperty(P.parameters.s(), parameters, OType.EMBEDDEDLIST);
//
//        try {
//            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
//            doc.save();
//            result = doc.getIdentity().toString();
//        }
//        catch (OException e) {
//            OrientDBDriver.getInstance().setLastError(e.getMessage());
//            result = "";
//        }
//        return result;
//    }
}
