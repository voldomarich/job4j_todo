package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    boolean markDone(int id);

    boolean update(Task task);

    boolean deleteById(int id);

    void deleteAll();

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> findByStatus(boolean status);
}
