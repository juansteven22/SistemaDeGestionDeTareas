package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTask() {
        Task task = new Task("Test Task", "Description", new Date());
        taskService.createTask(task);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task 1", "Description 1", new Date()));
        tasks.add(new Task("Task 2", "Description 2", new Date()));

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }
}