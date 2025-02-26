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
        params = new ArrayList<>();
    }
}
