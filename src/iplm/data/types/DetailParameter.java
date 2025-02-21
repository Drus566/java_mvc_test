package iplm.data.types;

import java.util.ArrayList;

public class DetailParameter {
    /* Тип данных параметра */
    enum Type {
        INT,
        FLOAT,
        STRING,
        RANGE,
        BOOL,
    }

    /* Уникальное имя (например material) */
    String uniq_name;
    /* Имя (например Материал) */
    String name;
    /* Тип данных */
    Type type;
    /* Сам объект */
    Object value;
    /* Перечисление вариантов значения параметра */
    ArrayList<Object> vals_enum;

    /* Доступно ли кастомное значение */
    boolean custom_val;
    /* Хранится ли вместо единичного значения перечисление значений */
    boolean enumeration;
    /* Удален (скрыт) ли параметр */
    boolean deleted;
    /* Редактируемый ли параметр */
    boolean edited;
    /* Занят ли параметр (например редактируется другим) */
    boolean busy;
    /* Кем занят параметр */
    String user_busy;
}
