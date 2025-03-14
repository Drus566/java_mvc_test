package iplm.data.types;

public class DetailParameterType {
    /* Тип данных параметра */
    public enum Type {
        DEC("Число"),
        FLOAT("Число с плавающей точкой"),
        STRING("Строка");

        private String m_string;
        Type(String string) { m_string = string; }
        public String s() { return m_string; }
    }

    public DetailParameterType() {}

    public DetailParameterType(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
        alias = "";
    }

    public DetailParameterType(String id, String name, String type, String alias) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.alias = alias;
    }

    /* ID */
    public String id;
    /* Имя */
    public String name;
    /* Псевдоним */
    public String alias;
    /* Тип данных */
    public String type;

    public String getName() { return name; }
    public String getAlias() { return alias; }
}
