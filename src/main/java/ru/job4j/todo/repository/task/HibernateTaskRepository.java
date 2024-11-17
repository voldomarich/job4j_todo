package ru.job4j.todo.repository.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CrudRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private final CrudRepository crudRepository;

    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    public boolean markDone(int id) {
        crudRepository.run(
                "UPDATE Task t SET t.done = :fDone WHERE t.id = :fId",
                Map.of("fDone", true, "fId", id)
        );
        return true;
    }

    public boolean update(Task task) {
        crudRepository.run(session -> session.merge(task));
        return true;
    }

    public boolean deleteById(int taskId) {
        crudRepository.run(
                "DELETE FROM Task t WHERE t.id = :fId",
                Map.of("fId", taskId)
        );
        return true;
    }

    @Override
    public void deleteAll() {
        crudRepository.query("DELETE FROM Task", Task.class);
    }

    public Optional<Task> findById(int taskId) {
        return crudRepository.optional(
                "FROM User where id = :fId", Task.class,
                Map.of("fId", taskId)
        );
    }

    public Collection<Task> findAll() {
        return crudRepository.query(
                "FROM Task t order by t.id ASC", Task.class);
    }

    public Collection<Task> findByStatus(boolean status) {
        return crudRepository.query(
                "FROM Task t where t.status = :fStatus", Task.class,
                Map.of("fStatus", status)
        );
    }
}
