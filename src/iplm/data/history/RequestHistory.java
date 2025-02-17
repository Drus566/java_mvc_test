package iplm.data.history;

import java.util.HashMap;

public class RequestHistory {
    public RequestHistoryType request_history_type;
    public HashMap<String, Object> params;

    RequestHistory(RequestHistoryType request_history_type) {
        this.request_history_type = request_history_type;
        params = new HashMap<>();
    }
}
