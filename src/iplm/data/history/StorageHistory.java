package iplm.data.history;

import com.google.gson.reflect.TypeToken;
import iplm.utility.JsonUtility;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class StorageHistory {
    public static int MAX_COUNT_REQUESTS = 30;
    public static final String filepath_storage = "storage";
    public static final String filepath_storage_settings = "storage_settings";

    private static HashMap<String, Integer> storage_settings = new HashMap<>();
    private static HashMap<String, ArrayList<RequestHistory>> storage = new HashMap<>();

    public static void add(String type, RequestHistory request) {
        if (!storage.containsKey(type)) storage.put(type, new ArrayList<>(MAX_COUNT_REQUESTS));
        else {
            ArrayList<RequestHistory> r = storage.get(type);
            if (r.size() < MAX_COUNT_REQUESTS) r.add(request);
            else {
                if (!storage_settings.containsKey(type)) storage_settings.put(type, 0);

                int index = storage_settings.get(type);
                RequestHistory rh = storage.get(type).get(index);
                rh.request_history_type = request.request_history_type;
                rh.params.clear();
                rh.params = request.params;

                if (index < MAX_COUNT_REQUESTS) ++index;
                else index = 0;

                storage_settings.put(type, index);
            }
        }
    }

    public static void remove(String type, RequestHistory request) {
        if (!storage.containsKey(type)) return;
        ArrayList<RequestHistory> rh_array = storage.get(type);
        for (RequestHistory rh : rh_array) {
            if (rh == request) {
                rh.request_history_type = RequestHistoryType.EMPTY;
                rh.params.clear();
            }
        }
    }

    public static void loadHistory() {
        Type type = new TypeToken<HashMap<String, ArrayList<RequestHistory>>>(){}.getType();
        storage = JsonUtility.fromJson(filepath_storage, type);
        storage_settings = JsonUtility.fromJson(filepath_storage_settings, type);
    }

    public static void saveHistory() {
        JsonUtility.toJson(filepath_storage, storage);
        JsonUtility.toJson(filepath_storage_settings, storage);
    }
}
