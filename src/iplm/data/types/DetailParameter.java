package iplm.data.types;

public class DetailParameter {
    /* Тип данных параметра */
    public enum Type {
        INT("Целое"),
        FLOAT("С плавающей точкой"),
        STRING("Строка"),
        RANGE("Диапазон"),
        BOOL("Булеан");

        private String m_string;
        Type(String string) { m_string = string; }
        public String s() { return m_string; }
    }

    /* Имя (например Материал) */
    public String name;
    /* Тип данных */
    public String type;
    /* Сам объект */
    public Object value;
    /* Перечисление вариантов значения параметра */
//    public ArrayList<Object> vals_enum;

    /* Доступно ли кастомное значение */
    public boolean custom_val;
    /* Хранится ли вместо единичного значения перечисление значений */
    public boolean enumeration;
    /* Удален (скрыт) ли параметр */
    public boolean deleted;
    /* Занят ли параметр (например редактируется другим) */
    public boolean busy;
    /* Кем занят параметр */
    public String user_busy;
}
