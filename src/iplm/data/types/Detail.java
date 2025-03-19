package iplm.data.types;

import java.util.ArrayList;

public class Detail {
    /* Уникальный айди (для БД) */
    public String id;
    /* Имя (например Молоток) */
    public String name;
    /* Децимальный номер */
    public String decimal_number;
    /* Описание */
    public String description;
    /* Список параметров */
    public ArrayList<DetailParameter> params;

    /* Занят ли */
    public boolean busy;
    /* Удален ли */
    public boolean deleted;
    /* Пользователь, кем занят */
    public User user_busy;
    /* Таймастамп создания */
    public String created_at;
    /* Таймстамп обновления */
    public String updated_at;

    public Detail() {
        id = "";
        name = "";
        decimal_number = "";
        description = "";
        params = new ArrayList<>();
        busy = false;
        user_busy = null;
        created_at = null;
        updated_at = null;
    }

    public String paramsToString() {
        StringBuilder sb = new StringBuilder();
        if (params != null) {
            int counter = 0;
            for (DetailParameter dp : params) {
                sb.append(dp.type.name);
                sb.append(": ");
                sb.append(dp.value);
                if (counter < params.size() - 1) sb.append(", ");
                ++counter;
            }
        }

        return sb.toString();
    }
}
