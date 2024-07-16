package com.example.taskmanager.ui;

import com.example.taskmanager.config.AppConfig;
import com.example.taskmanager.controller.TaskController;
import com.example.taskmanager.model.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class TaskManagementUI extends JFrame {
    private final TaskController taskController;
    /*
    private JList<Task> taskList;
    private DefaultListModel<Task> listModel*/
    private JList<String> taskList;
    private DefaultListModel<String> listModel;

    public TaskManagementUI(TaskController taskController) {
        this.taskController = taskController;
        initUI();
    }

    private void initUI() {
        setTitle("Task Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Task list
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);


        JScrollPane scrollPane = new JScrollPane(taskList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add Task");
        JButton editButton = new JButton("Edit Task");
        JButton deleteButton = new JButton("Delete Task");
        JButton refreshButton = new JButton("Refresh List");

        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(refreshButton);

        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(e -> addTask());
        editButton.addActionListener(e -> editTask());
        deleteButton.addActionListener(e -> deleteTask());
        refreshButton.addActionListener(e -> refreshTaskList());

        add(mainPanel);

        refreshTaskList();
    }

    private void refreshTaskList() {
        List<Task> tasks = taskController.getAllTasks();
        listModel.clear();
        for (Task task : tasks) {
            listModel.addElement(task.toString());
        }
    }

    private void addTask() {
        String title = JOptionPane.showInputDialog(this, "Enter task title:");
        if (title != null && !title.trim().isEmpty()) {
            String description = JOptionPane.showInputDialog(this, "Enter task description:");
            String dueDateStr = JOptionPane.showInputDialog(this, "Enter due date (yyyy-MM-dd):");

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dueDate = dateFormat.parse(dueDateStr);

                Task newTask = new Task(title, description, dueDate);
                taskController.createTask(newTask);
                refreshTaskList();
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            List<Task> tasks = taskController.getAllTasks();
            Task selectedTask = tasks.get(selectedIndex);

            String title = JOptionPane.showInputDialog(this, "Enter new title:", selectedTask.getTitle());
            String description = JOptionPane.showInputDialog(this, "Enter new description:", selectedTask.getDescription());
            String dueDateStr = JOptionPane.showInputDialog(this, "Enter new due date (yyyy-MM-dd):", new SimpleDateFormat("yyyy-MM-dd").format(selectedTask.getDueDate()));

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dueDate = dateFormat.parse(dueDateStr);

                selectedTask.setTitle(title);
                selectedTask.setDescription(description);
                selectedTask.setDueDate(dueDate);

                taskController.updateTask(selectedTask);
                refreshTaskList();
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to edit.", "No Task Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            List<Task> tasks = taskController.getAllTasks();
            Task selectedTask = tasks.get(selectedIndex);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this task?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                taskController.deleteTask(selectedTask.getId());
                refreshTaskList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.", "No Task Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TaskController taskController = context.getBean(TaskController.class);

        SwingUtilities.invokeLater(() -> {
            TaskManagementUI ui = new TaskManagementUI(taskController);
            ui.setVisible(true);
        });
    }
}