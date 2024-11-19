package ru.job4j.todo.repository.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CrudRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private final CrudRepository crudRepository;

    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    public boolean markDone(int id) {
        int updatedRows = crudRepository.tx(session -> session.createQuery(
                        "UPDATE Task t SET t.done = :fDone WHERE t.id = :fId")
                .setParameter("fDone", true)
                .setParameter("fId", id)
                .executeUpdate()
        );
        return updatedRows > 0;
    }

    public boolean update(Task task) {
        return crudRepository.tx(session -> session.merge(task) != null);
    }

    public boolean deleteById(int taskId) {
        return crudRepository.tx(session -> {
            int rowsAffected = session.createQuery("DELETE FROM Task t WHERE t.id = :fId")
                    .setParameter("fId", taskId)
                    .executeUpdate();
            return rowsAffected > 0;
        });
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
