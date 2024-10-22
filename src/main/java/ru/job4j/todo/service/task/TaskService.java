package ru.job4j.todo.service.task;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Task save(Task task);

    boolean markDone(int id);

    boolean update(Task task);

    boolean deleteById(int id);

    void deleteAll();

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> findByStatus(boolean status);
}