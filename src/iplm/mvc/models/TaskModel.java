package iplm.mvc.models;

public class TaskModel {
    private String name;
    private boolean completed;

    public TaskModel(String name) {
        this.name = name;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markAsCompleted() {
        this.completed = true;
    }
}
