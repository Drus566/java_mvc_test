package iplm.data.types;

import java.util.ArrayList;

public class DetailParameterType {
    /* Тип данных параметра */
    public enum Type {
        DEC("Число"),
//        FLOAT("Число с плавающей точкой"),
        STRING("Строка"),
//        RANGE("Диапазон"),
        BOOL("Флаг");

        private String m_string;
        Type(String string) { m_string = string; }
        public String s() { return m_string; }
    }

    /* ID */
    public String id;
    /* Имя (например Материал) */
    public String name;
    /* Тип данных */
    public String type;
    /* Перечисление вариантов значения параметра */
    public ArrayList<Object> vals_enum;
    /* Доступно ли кастомное значение */
    public boolean custom_val;
    /* Хранится ли вместо единичного значения перечисление значений */
    public boolean enumeration;
    /* Занят ли параметр (например редактируется другим) */
    public boolean busy;
    /* Кем занят параметр */
    public String user_busy;
}
