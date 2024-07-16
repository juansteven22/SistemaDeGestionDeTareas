package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class TaskRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Task task) {
        if (task.getId() == null) {
            entityManager.persist(task);
        } else {
            entityManager.merge(task);
        }
    }

    @Transactional
    public void delete(Task task) {
        entityManager.remove(entityManager.contains(task) ? task : entityManager.merge(task));
    }

    public Task findById(Long id) {
        return entityManager.find(Task.class, id);
    }

    public List<Task> findAll() {
        return entityManager.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }
}