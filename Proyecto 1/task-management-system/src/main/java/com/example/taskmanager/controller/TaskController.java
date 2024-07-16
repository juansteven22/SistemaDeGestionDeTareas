package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public void createTask(Task task) {
        taskService.createTask(task);
    }

    public void updateTask(Task task) {
        taskService.updateTask(task);
    }

    public void deleteTask(Long id) {
        taskService.deleteTask(id);
    }

    public Task getTaskById(Long id) {
        return taskService.getTaskById(id);
    }

    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
}