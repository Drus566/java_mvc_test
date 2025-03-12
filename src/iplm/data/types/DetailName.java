package iplm.data.types;

public class DetailName {
    public String id;
    public String name;

    public DetailName() {}

    public DetailName(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() { return name; }
}
