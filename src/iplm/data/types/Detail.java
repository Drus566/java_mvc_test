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

    public Detail() {
        params = new ArrayList<>();
    }
}
