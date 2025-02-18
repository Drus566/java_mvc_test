package iplm.data.history;

import java.util.HashMap;

public class RequestHistory {
    public int id;
    public RequestHistoryType type;
    public HashMap<String, Object> params;

    RequestHistory(int id) {
        this.id = id;
        this.type = RequestHistoryType.EMPTY;
        params = new HashMap<>();
    }

    RequestHistory(int id, RequestHistoryType request_history_type) {
        this.id = id;
        this.type = request_history_type;
        params = new HashMap<>();
    }

    RequestHistory(int id, RequestHistoryType request_history_type, HashMap<String, Object> params) {
        this.id = id;
        this.type = request_history_type;
        this.params = params;
    }
}
