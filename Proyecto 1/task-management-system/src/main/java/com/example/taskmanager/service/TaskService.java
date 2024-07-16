package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;

import java.util.List;

public interface TaskService {
    void createTask(Task task);
    void updateTask(Task task);
    void deleteTask(Long id);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
}