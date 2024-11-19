package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Optional;

public interface TaskStore {

    Task save(Task task);

    boolean update(Task task);

    boolean updateDoneToTrue(int id);

    Optional<Task> findById(int id);

    Collection<Task> findByUser(User user);

    Collection<Task> findByDoneAndUser(boolean done, User user);

    boolean deleteById(int id);
}
