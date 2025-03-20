package iplm.data.types;

import java.util.ArrayList;

public class DetailParameter {
    /* Id */
    public String id;
    /* Id детали */
    public String detail_id;
    /* ID типа детали */
    public String detail_parameter_type_id;
    /* Тип детали */
    public DetailParameterType type;
    /* Значение */
    public Object value;

    public DetailParameter() {}

    public DetailParameter(Object value, DetailParameterType type) {
        this.value = value;
        this.type = type;
    }
}
