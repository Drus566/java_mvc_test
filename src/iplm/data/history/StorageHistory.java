package iplm.data.history;

import com.google.gson.reflect.TypeToken;
import iplm.utility.JsonUtility;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class StorageHistory {
    private static StorageHistory INSTANCE;

    public static int MAX_COUNT_REQUESTS = 30;
    public static final String FILEPATH_STORAGE = "storage";
    public static final String FILEPATH_ID_COUNTER = "storage_id_counter";
    public static final String FILEPATH_STORAGE_SETTINGS = "storage_settings";

    private int id_counter = 0;
    private HashMap<StorageHistoryType, Integer> storage_settings;
    private HashMap<StorageHistoryType, ArrayList<RequestHistory>> storage;

    StorageHistory() {}

    public void init() {
        loadHistory();
        if (storage_settings == null) storage_settings = new HashMap<>();
        if (storage == null) storage = new HashMap<>();
    }

    public synchronized RequestHistory add(StorageHistoryType storage_history_type, RequestHistoryType request_history_type, HashMap<String, Object> params) {
        if (!storage.containsKey(storage_history_type)) storage.put(storage_history_type, new ArrayList<>(MAX_COUNT_REQUESTS));

        ArrayList<RequestHistory> r = storage.get(storage_history_type);
        RequestHistory result = null;

        for (int i = 0; i < r.size(); i++) {
            RequestHistory temp = r.get(i);
            if (temp.type != RequestHistoryType.EMPTY) {
                String query = (String)params.get("Query");
                String new_query = (String)params.get("Query");
                if (query.equalsIgnoreCase(new_query)) return result;
            }
        }

        result = new RequestHistory(id_counter, request_history_type, params);

        if (r.size() < MAX_COUNT_REQUESTS) r.add(result);
        else {
            if (!storage_settings.containsKey(storage_history_type)) storage_settings.put(storage_history_type, 0);

            int index = storage_settings.get(storage_history_type);
            RequestHistory rh = storage.get(storage_history_type).get(index);
            rh.type = result.type;
            rh.params.clear();
            rh.params = result.params;

            if (index < MAX_COUNT_REQUESTS) ++index;
            else index = 0;

            storage_settings.put(storage_history_type, index);
        }

        if (id_counter == Integer.MAX_VALUE) id_counter = 0;
        else ++id_counter;

        return result;
    }

    public synchronized void remove(StorageHistoryType type, int id) {
        if (!storage.containsKey(type)) return;

        ArrayList<RequestHistory> rh_array = storage.get(type);
        for (RequestHistory rh : rh_array) {
            if (rh.id == id) {
                rh.type = RequestHistoryType.EMPTY;
                rh.params.clear();
            }
        }
    }

    public synchronized ArrayList<RequestHistory> search(StorageHistoryType type, String query) {
        ArrayList<RequestHistory> result = null;

        if (!storage.containsKey(type)) return result;

        ArrayList<RequestHistory> rh_array = storage.get(type);
        for (RequestHistory rh : rh_array) {
            if (rh.type == RequestHistoryType.EMPTY) continue;

            String q = (String) rh.params.get("Query");
            if (q != null && !q.isEmpty() && q.contains(query)) {
                if (result == null) result = new ArrayList<>();
                result.add(rh);
            }
        }
        return result;
    }

    public synchronized void loadHistory() {
        Type storage_type = new TypeToken<HashMap<String, ArrayList<RequestHistory>>>(){}.getType();
        storage = JsonUtility.fromJson(FILEPATH_STORAGE, storage_type);

        Type storage_id_counter_type = new TypeToken<Integer>(){}.getType();
        Integer result = JsonUtility.fromJson(FILEPATH_ID_COUNTER, storage_id_counter_type);
        if (result != null) id_counter = result.intValue();

        Type storage_settings_type = new TypeToken<HashMap<StorageHistoryType, Integer>>(){}.getType();
        storage_settings = JsonUtility.fromJson(FILEPATH_STORAGE_SETTINGS, storage_settings_type);
    }

    public synchronized void saveHistory() {
        JsonUtility.toJson(FILEPATH_STORAGE, storage);
        JsonUtility.toJson(FILEPATH_ID_COUNTER, id_counter);
        JsonUtility.toJson(FILEPATH_STORAGE_SETTINGS, storage_settings);
    }

    public static synchronized StorageHistory getInstance() {
        if (INSTANCE == null) INSTANCE = new StorageHistory();
        return INSTANCE;
    }
}
