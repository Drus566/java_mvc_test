package iplm.data.repository.detail;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.OElement;
import iplm.data.db.OrientDBDriver;
import iplm.data.types.Detail;
import iplm.data.types.DetailParameter;
import iplm.utility.DateTimeUtility;

import java.util.ArrayList;
import java.util.List;

public class OrientDBDetailHandler {
    enum C {
        detail("Detail"),
        detail_parameter("DetailParameter");

        private String m_string;
        C(String string) { m_string = string; }
        public String s() { return m_string; }
    }

    enum P {
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
            System.out.println(parameters.get(0).getPropertyNames());
            doc.save();
            result = doc.getIdentity().toString();
            System.out.println(result);
        }
        catch (OException e) {
            System.out.println(e.getMessage());
            result = "";
        }
        return result;
    }

    public String remove() {
        return "";
    }

    public String update(Detail d) {
        return "";
    }
}
