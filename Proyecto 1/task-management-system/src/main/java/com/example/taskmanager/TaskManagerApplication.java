package com.example.taskmanager;

import com.example.taskmanager.config.AppConfig;
import com.example.taskmanager.controller.TaskController;
import com.example.taskmanager.ui.TaskManagementUI;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;

public class TaskManagerApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SwingUtilities.invokeLater(() -> {
            TaskController taskController = context.getBean(TaskController.class);
            TaskManagementUI ui = new TaskManagementUI(taskController);
            ui.setVisible(true);
        });
    }
}