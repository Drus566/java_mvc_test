package iplm.data.types;

import java.util.ArrayList;

public class Detail {
    /* Уникальный айди (для БД) */
    private int id;
    /* Уникальное наименование (например molotok) */
    private String uniq_name;
    /* Имя (например Молоток) */
    private String name;
    /* Децимальный номер */
    private String decimal_number;
    /* Описание */
    private String description;
    /* Список параметров */
    private ArrayList<DetailParameter> params;
}
