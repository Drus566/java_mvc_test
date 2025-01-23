package iplm.controllers;

import iplm.views.TaskView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskController {
    private TaskView view;

    public TaskController(TaskView view) {
        this.view = view;

        this.view.addTaskListener(e -> {
            String taskName = view.getTaskInput();
            if (!taskName.isEmpty()) {
                view.addTaskToList(taskName);
                view.setTaskInput(""); // очистка поля ввода
            }
        });
    }
}
