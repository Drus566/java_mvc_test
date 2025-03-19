package iplm.data.repository.detail;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.db.record.ORecordLazySet;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import iplm.data.db.OrientDBDriver;
import iplm.data.types.Detail;
import iplm.data.types.DetailName;
import iplm.data.types.DetailParameter;
import iplm.data.types.DetailParameterType;
import iplm.utility.DateTimeUtility;
import iplm.utility.StringUtility;

import java.util.ArrayList;
import java.util.Iterator;

public class OrientDBDetailHandler {
    enum C {
        detail("Detail"),
        detail_parameter("DetailParameter"),
        detail_parameter_type("DetailParameterType"),
        detail_name("DetailName");

        private String m_string;

        C(String string) {
            m_string = string;
        }

        public String s() {
            return m_string;
        }
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
        alias("alias"),
        detail_id("detail_id"),
        params("params");

        private String m_string;

        P(String string) {
            m_string = string;
        }

        public String s() {
            return m_string;
        }
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
        } catch (OException e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
        }

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
        } catch (OException e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
        }

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
        } catch (OException e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
        }

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
        } catch (OException e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
        }

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
        query.append(",");
        query.append("alias: '").append(detail_parameter_type.alias).append("'");
        query.append("}");

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query.toString());

            while (rs.hasNext()) {
                OResult item = rs.next();
                result = item.getProperty(P.rid.s()).toString();
                break;
            }
        } catch (OException e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
        }

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
        } catch (OException e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
        }

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
        query.append("', ").append(P.alias).append(" = '").append(detail_parameter_type.alias);
        query.append("' RETURN AFTER @rid WHERE @rid = ").append(detail_parameter_type.id);

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query.toString());

            while (rs.hasNext()) {
                OResult item = rs.next();
                result = item.getProperty(P.rid.s()).toString();
                break;
            }
        } catch (OException e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
        }

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
                dpt.alias = item.getProperty(P.alias.s()).toString();

                result.add(dpt);
            }
        } catch (OException e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
        }

        return result;
    }

    synchronized public ArrayList<String> getDetailParameterTypeReferences(String detail_parameter_type_rid) {
        if (!OrientDBDriver.getInstance().isConnect()) {
            OrientDBDriver.getInstance().setLastError("Нет соединения с базой данных");
            return null;
        }

        ArrayList<String> result = new ArrayList<>();
        String query = "FIND REFERENCES " + detail_parameter_type_rid + " [" + C.detail_parameter.s() + "]";

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().command(query, C.detail_parameter_type.s());

            while (rs.hasNext()) {
                OResult item = rs.next();
                System.out.println(item);
                String rid = item.getProperty("rid").toString();
                result.add(rid);
            }
        }
        catch (OException e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
            result = null;
        }
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
        if (!detail.decimal_number.trim().isEmpty())
            detail_element.setProperty(P.decimal_number.s(), detail.decimal_number);
        else detail_element.setProperty(P.decimal_number.s(), null);
        detail_element.setProperty(P.name.s(), detail.name);
        detail_element.setProperty(P.description.s(), detail.description);
        detail_element.setProperty(P.deleted.s(), detail.deleted);
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
        try {
            detail_element.save();
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        try {
            detail_element = OrientDBDriver.getInstance().getSession().load(new ORecordId(id));
        } catch (Exception e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
            return result;
        }

        try {
            OrientDBDriver.getInstance().getSession().begin();

            detail_element.setProperty(P.deleted.s(), true);
            detail_element.save();
            result = true;

            OrientDBDriver.getInstance().getSession().commit();
        } catch (Exception e) {
            OrientDBDriver.getInstance().getSession().rollback();
            OrientDBDriver.getInstance().setLastError(e.getMessage());
            result = false;
        }
        return result;
    }

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
        try {
            detail_element = OrientDBDriver.getInstance().getSession().load(new ORecordId(detail.id));
        } catch (Exception e) {
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
        detail_element.setProperty(P.deleted.s(), detail.deleted);

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
        } catch (Exception e) {
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
                            dpt.alias = pt_element.getProperty(P.alias.s());
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
            String error = e.getMessage();
            if (e.getMessage() == null) error = e.toString();
            OrientDBDriver.getInstance().setLastError(error);
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

        String n_request = StringUtility.cutToChar(request, '[').trim();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT FROM Detail WHERE ");

        if (!n_request.isEmpty()) {
            sb.append("SEARCH_CLASS(");
            sb.append("'");
            sb.append(n_request).append(" || ");
            sb.append(n_request).append("*").append(" || ");
            sb.append("*").append(n_request).append("*").append(" || ");
            sb.append(n_request).append("~");
            sb.append("'");
            sb.append(", { \"sort\": [{ 'field': 'name', reverse:false, type:'STRING' }]}");
            sb.append(") = true");

            if (request.indexOf('[') != -1) {
                sb.append(" AND ");
            }
        }

        boolean first = true;

        // Есть ли запрос с параметрами
        if (request.contains("[")) {
            String params_str = StringUtility.getBetweenChars(request, '[', ']').trim();
            if (params_str != null && !params_str.isEmpty()) {
                if (params_str.equals("!")) {
                    if (!first) sb.append(" AND ");
                    sb.append(" params != [] and params != null");
                }
                else {
                    String[] params = params_str.split(",");
                    for (String param : params) {
                        String[] key_val = param.trim().split("=");
                        if (key_val.length == 2) {
                            String key = key_val[0].trim();
                            String val = key_val[1].trim();

                            boolean is_range = val.indexOf('(') == -1 ? false : true;
                            boolean is_string = val.indexOf("\"") == -1 ? false : true;

                            String dec_str = val;
                            int range_val = -1;
                            String range_str = StringUtility.getBetweenChars(val, '(', ')');
                            if (range_str != null) {
                                try {
                                    range_val = Integer.parseInt(range_str);
                                    dec_str = StringUtility.cutToChar(val, '(');
                                }
                                catch(Exception e) {}
                            }

                            if (!first) sb.append(" AND ");
                            else first = false;

                            sb.append(" params IN (");
                            sb.append(" SELECT @rid FROM DetailParameter ");
                            sb.append(" WHERE ");
                            sb.append("type IN (SELECT @rid FROM DetailParameterType WHERE ");
                            sb.append("SEARCH_CLASS(");
                            sb.append("'");
                            sb.append(key).append(" || ");
                            sb.append(key).append("*").append(" || ");
                            sb.append("*").append(key).append("*").append(" || ");
                            sb.append(key).append("~");
                            sb.append("'");
//                        if (n_request.isEmpty()) sb.append(", { \"sort\": [{ 'field': 'е', reverse:false, type:'STRING' }]}");
                            sb.append(") = true)");


                            if (is_string) {
                                String str_val = val.replace("\"", "");
//                            sb.append(" AND SEARCH_CLASS('value:");
                                sb.append("AND SEARCH_CLASS(");
                                sb.append("'");
                                sb.append(str_val).append(" || ");
                                sb.append(str_val).append("*").append(" || ");
                                sb.append("*").append(str_val).append("*").append(" || ");
                                sb.append(str_val).append("~");
//                            sb.append("'");
//                            sb.append(", { \"sort\": [{ 'field': 'name', reverse:false, type:'STRING' }]}");
//                            sb.append(") = true)");
                            }
                            else {
                                sb.append(" AND SEARCH_CLASS('value:");
                                try {
                                    int i = Integer.parseInt(dec_str, 10);

                                    if (is_range) {
                                        int min = i - range_val;
                                        int max = i + range_val;
                                        sb.append("[").append(min).append(" TO ");
                                        sb.append(max).append("]");
                                    }
                                    else sb.append(val);
                                }
                                catch (Exception e) {
                                    try {
                                        float f = Float.parseFloat(dec_str);
                                        if (is_range) {
                                            int min = (int) (f - range_val);
                                            int max = (int) (f + range_val);
                                            sb.append("[").append(min).append(" TO ");
                                            sb.append(max).append("]");
                                        }
                                        else sb.append(val);
                                    }
                                    catch (Exception ee) {}
                                }
                            }
                            sb.append("') = true)");
                        }
                    }
                }
            }
            else if (params_str != null && params_str.isEmpty()) {
                if (!first) sb.append(" AND ");
                sb.append(" params = [] or params = null");
            }
        }

        sb.append(" AND deleted = false");

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
                            dpt.alias = pt_element.getProperty(P.alias.s());
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
