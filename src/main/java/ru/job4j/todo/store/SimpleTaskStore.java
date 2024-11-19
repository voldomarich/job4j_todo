package ru.job4j.todo.store;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.CrudRepository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class SimpleTaskStore implements TaskStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTaskStore.class);
    @NonNull
    private final CrudRepository crudRepository;

    public Task save(Task task) {
        Task result = null;
        try {
            crudRepository.run(session -> session.persist(task));
            result = task;
        } catch (Exception e) {
            LOGGER.error("Ошибка при сохранении задачи: " + e.getMessage());
        }
        return result;
    }

    public boolean update(Task task) {
        boolean result = false;
        try {
            crudRepository.run(
                    "UPDATE Task SET title = :fTitle, description = :fDescription, user_id = :fUserId WHERE id = :fId",
                    Map.of("fId", task.getId(),
                            "fTitle", task.getTitle(),
                            "fDescription", task.getDescription(),
                            "fUserId", task.getUser().getId())
            );
            result = true;
        } catch (Exception e) {
            LOGGER.error("Ошибка при обновлении задачи: " + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean updateDoneToTrue(int id) {
        boolean result = false;
        try {
            crudRepository.run("UPDATE Task SET done = :fDone WHERE id = :fId",
                    Map.of("fId", id, "fDone", true));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Ошибка при обновлении статуса задачи: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Optional<Task> findById(int id) {
        Optional<Task> result = Optional.empty();
        try {
            result = crudRepository.optional("FROM Task i JOIN FETCH i.priority WHERE i.id = :fId",
                    Task.class, Map.of("fId", id));
        } catch (Exception e) {
            LOGGER.error("Произошла ошибка во время поиска: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Collection<Task> findByUser(User user) {
        List<Task> result = new ArrayList<>();
        try {
            result = crudRepository.query("FROM Task i JOIN FETCH i.priority WHERE i.user = :fUser", Task.class,
                    Map.of("fUser", user));
        } catch (Exception e) {
            LOGGER.error("Произошла ошибка во время поиска: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Collection<Task> findByDoneAndUser(boolean done, User user) {
        List<Task> result = new ArrayList<>();
        try {
            result = crudRepository.query("FROM Task i JOIN FETCH i.priority WHERE i.done = :fDone AND i.user = :fUser",
                    Task.class, Map.of("fDone", done, "fUser", user));
        } catch (Exception e) {
            LOGGER.error("Произошла ошибка во время поиска: " + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        try {
            crudRepository.run("DELETE Task WHERE id = :fId", Map.of("fId", id));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Произошла ошибка при удалении: " + e.getMessage());
        }
        return result;
    }
}
