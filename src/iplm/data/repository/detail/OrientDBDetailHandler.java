package iplm.data.repository.detail;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultInternal;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import iplm.data.db.OrientDBDriver;
import iplm.data.types.Detail;
import iplm.data.types.DetailParameter;
import iplm.utility.DateTimeUtility;

import java.util.ArrayList;
import java.util.List;

//SELECT name
//        FROM Detail
//        WHERE name LIKE '%ш%%и%'
//        LIMIT 5;

public class OrientDBDetailHandler {
    enum C {
        detail("Detail"),
        detail_parameter("DetailParameter");

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
        enumeration("enumeration"),
        type("type"),
        value("value"),
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
//    UPDATE ParentDoc MERGE {children:[#12:2, #!12:33]} WHERE @rid = #46:0
//    UPDATE ParentDoc MERGE {children:[#14:44]} RETURN AFTER @rid WHERE @rid = #48:0
    public String delete(String id) {
        StringBuilder query_builder = new StringBuilder();
        query_builder.append("UPDATE Detail SET deleted = true RETURN AFTER @rid WHERE @rid = ");
        query_builder.append(id);
        String result = null;
        return result;
    }

    public String update(Detail detail) {
        OrientDBDriver.getInstance().getSession().begin();

        OElement doc = OrientDBDriver.getInstance().getSession().load(new ORecordId(detail.id));


        String result = null;
        doc.setProperty(P.name.s(), detail.name);
        doc.setProperty(P.decimal_number.s(), detail.decimal_number);
        doc.setProperty(P.description.s(), detail.description);

        List<OIdentifiable> prev_link_list = null;
        List<OIdentifiable> link_list = null;
        if (detail.params != null && !detail.params.isEmpty()) {
            if (link_list == null) link_list = new ArrayList<>();

//            OClass params = OrientDBDriver.getInstance().getSession().getMetadata().getSchema().createClass(C.detail_parameter.s());
//            params.createProperty()
            for (DetailParameter dp : detail.params) {
                link_list.add(new ORecordId(dp.id));
            }
        }

        OrientDBDriver.getInstance().getSession().commit();

        return result;
    }

    public ArrayList<Detail> get(String request) {
        ArrayList<Detail> result = null;

        String query = "SELECT FROM Detail WHERE SEARCH_CLASS(?) = true;";

//        String query = "SELECT name, decimal_number, @rid FROM Detail \n" +
//        "WHERE decimal_number LIKE '?';\n";



//        String query = "SELECT name, decimal_number, @rid\n" +
//                "FROM Detail \n" +
//                "WHERE @rid IN (\n" +
//                "    SELECT rid\n" +
//                "    FROM INDEX:Detail.russian \n" +
//                "    WHERE key = ?\n" +
//                ")\n";

        StringBuilder sb = new StringBuilder();
        sb.append(request).append("~").append(" || ");
        sb.append(request).append("*").append(" || ");
        sb.append("*").append(request).append("*").append(" || ");
        sb.append(request);

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().query(query, sb.toString());

            while (rs.hasNext()) {
                if (result == null) result = new ArrayList<>();
                Detail d = new Detail();
                OResult item = rs.next();
                d.name = item.getProperty(P.name.s());
                d.decimal_number = item.getProperty(P.decimal_number.s());
                d.id = item.getProperty(P.rid.s()).toString();
                result.add(d);
            }
        }
        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }

        return result;
    }

    public ArrayList<Detail> getAll() {
        ArrayList<Detail> result = null;
        String query = "SELECT * FROM Detail";

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().query(query);

            while (rs.hasNext()) {
                if (result == null) result = new ArrayList<>();
                Detail d = new Detail();
                OResult item = rs.next();
                d.name = item.getProperty(P.name.s());
                d.decimal_number = item.getProperty(P.decimal_number.s());
                d.description = item.getProperty(P.description.s());
                d.id = item.getProperty(P.rid.s()).toString();
                result.add(d);
            }
        }
        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }

        return result;
    }

    public Detail getById(String id) {
        Detail result = null;
        String query = "SELECT * FROM Detail WHERE @rid = " + id;

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            OResultSet rs = OrientDBDriver.getInstance().getSession().query(query);

            while (rs.hasNext()) {
                if (result == null) result = new Detail();
                OResult item = rs.next();
                result.name = item.getProperty(P.name.s());
                result.decimal_number = item.getProperty(P.decimal_number.s());
                result.description = item.getProperty(P.description.s());
                result.id = item.getProperty(P.rid.s()).toString();
                List<Object> el = item.getProperty("parameters");

                System.out.println(el);
                for (Object o : el) {
                    OResultInternal ri = (OResultInternal) o;
//                    System.out.println(ri);
                    ri.getPropertyNames();
                    DetailParameter dp = new DetailParameter();
                    dp.name = ri.getProperty(P.name.s());
                    dp.value = ri.getProperty(P.value.s()).toString();
                    result.params.add(dp);
                }
//                for (ODocument p : pp) {
//                    System.out.println(p.getPropertyNames());
//                }
//                System.out.println(item.getProperty("parameters"));
//                for (DetailParameter dp : result.params) {
//                    dp.name = item.getProperty()
//                }
                break;
            }
        }
        catch (OException e) { OrientDBDriver.getInstance().setLastError(e.getMessage()); }

        return result;
    }

    public String add(Detail detail) {
        String result;
        String current_timestamp = DateTimeUtility.timestamp();

        OElement doc = OrientDBDriver.getInstance().getSession().newInstance(C.detail.s());
        doc.setProperty(P.name.s(), detail.name);
        doc.setProperty(P.decimal_number.s(), detail.decimal_number);
        doc.setProperty(P.description.s(), detail.description);
        doc.setProperty(P.busy.s(), false);
        doc.setProperty(P.user_busy.s(), detail.user_busy);
        doc.setProperty(P.deleted.s(), false);
        doc.setProperty(P.created_at.s(), current_timestamp);

        List<OElement> parameters = new ArrayList<>();
        for (DetailParameter dp : detail.params) {
            OElement p = OrientDBDriver.getInstance().getSession().newInstance(C.detail_parameter.s());
            p.setProperty(P.name.s(), dp.name);
            p.setProperty(P.type.s(), dp.type);
            p.setProperty(P.deleted.s(), false);
            p.setProperty(P.custom_val.s(), false);
            p.setProperty(P.created_at.s(), current_timestamp);
            p.setProperty(P.enumeration.s(), false);
            p.setProperty(P.value.s(), dp.value);
            parameters.add(p);
        }
        doc.setProperty(P.parameters.s(), parameters, OType.EMBEDDEDLIST);

        try {
            OrientDBDriver.getInstance().getSession().activateOnCurrentThread();
            doc.save();
            result = doc.getIdentity().toString();
        }
        catch (OException e) {
            OrientDBDriver.getInstance().setLastError(e.getMessage());
            result = "";
        }
        return result;
    }
}
