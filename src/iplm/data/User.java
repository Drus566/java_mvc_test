package iplm.data;

public class User {
    private int id;
    private String name;

    // Конструкторы, геттеры и сеттеры
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
}
