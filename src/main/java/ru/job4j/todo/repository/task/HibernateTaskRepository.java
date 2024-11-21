package ru.job4j.todo.repository.task;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CrudRepository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateTaskRepository.class);
    @NonNull
    private final CrudRepository crudRepository;

    public Task save(Task task) {
        Task result = null;
        try {
            crudRepository.run(session -> session.persist(task));
            result = task;
        } catch (Exception e) {
            LOGGER.error("Произошла ошибка при сохранении задачи: " + e.getMessage());
        }
        return result;
    }

    public boolean markDone(int taskId) {
        boolean result = false;
        try {
            crudRepository.run(
                    "UPDATE Task t SET t.done = :fDone WHERE t.id = :fId",
                    Map.of("fDone", true, "fId", taskId));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Произошла ошибка при изменении статуса: " + e.getMessage());
        }
        return result;
    }

    public boolean update(Task task) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(task));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Произошла ошибка при обновлении задачи: " + e.getMessage());
        }
        return result;
    }

    public boolean deleteById(int taskId) {
        boolean result = false;
        try {
            crudRepository.run("DELETE Task WHERE id = :fId", Map.of("fId", taskId));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Произошла ошибка при удалении: " + e.getMessage());
        }
        return result;
    }

    public void deleteAll() {
        crudRepository.query("DELETE FROM Task", Task.class);
    }

    public Optional<Task> findById(int taskId) {
        Optional<Task> result = Optional.empty();
        try {
            result = crudRepository.optional("FROM Task i JOIN FETCH i.priority WHERE i.id = :fId",
                    Task.class, Map.of("fId", taskId));
        } catch (Exception e) {
            LOGGER.error("Произошла ошибка во время поиска: " + e.getMessage());
        }
        return result;
    }

    public Collection<Task> findAll() {
        List<Task> result = new ArrayList<>();
        try {
            result = crudRepository.query("FROM Task i JOIN FETCH i.priority order by i.id ASC", Task.class);
        } catch (Exception e) {
            LOGGER.error("Произошла ошибка во время поиска: " + e.getMessage());
        }
        return result;
    }

    public Collection<Task> findByStatus(boolean status) {
        List<Task> result = new ArrayList<>();
        try {
            result = crudRepository.query("FROM Task i JOIN FETCH i.priority WHERE i.user = :fUser", Task.class,
                    Map.of("fStatus", status));
        } catch (Exception e) {
            LOGGER.error("Произошла ошибка во время поиска: " + e.getMessage());
        }
        return result;
    }
}
