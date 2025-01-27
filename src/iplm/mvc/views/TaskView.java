package iplm.mvc.views;

import iplm.data.types.User;
import iplm.interfaces.observer.IObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TaskView implements IObserver<User> {
    private JFrame frame;
    private JTextField taskInput;
    private JButton addButton;
    private JList<String> taskList;
    private DefaultListModel<String> listModel;

    public TaskView() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("To-Do List");
            taskInput = new JTextField(20);
            addButton = new JButton("Add Task");
            listModel = new DefaultListModel<>();
            taskList = new JList<>(listModel);

            frame.setLayout(new BorderLayout());
            frame.add(taskInput, BorderLayout.NORTH);
            frame.add(addButton, BorderLayout.SOUTH);
            frame.add(new JScrollPane(taskList), BorderLayout.CENTER);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setVisible(true);
        });
    }

    public String getTaskInput() {
        return taskInput.getText();
    }

    public void setTaskInput(String text) { taskInput.setText(text); }

    public void addTaskToList(String task) {
        listModel.addElement(task);
    }

    public void addTaskListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    @Override
    public void update(User subject) {
        taskInput.setText(Integer.toString(subject.getId()));
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
