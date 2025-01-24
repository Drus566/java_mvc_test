package iplm.controllers;

import iplm.views.MainView;
import iplm.views.TaskView;

public class MainController {
    private MainView view;
    private Use

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
