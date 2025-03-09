package iplm.data.types;

import java.util.ArrayList;

public class DetailParameter {
    /* Id */
    public String id;
    /* Id детали */
    public String detail_id;
    /* Тип детали */
    public DetailParameterType type;
    /* Значение */
    public Object value;
    /* Доп информация */
    public String info;

    public DetailParameter() {}

    public DetailParameter(Object value, DetailParameterType type) {
        this.value = value;
        this.type = type;
    }
}
