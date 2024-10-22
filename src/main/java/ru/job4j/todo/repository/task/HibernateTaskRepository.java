package ru.job4j.todo.repository.task;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateTaskRepository.class);
    private final SessionFactory sessionFactory;

    public Task save(Task task) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(task);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            LOGGER.error("Error saving task '{}' : {}", task.getTitle(), e.getMessage());
        } finally {
            session.close();
            LOGGER.info("Session closed after saving tasks");
        }
        return task;
    }

    public boolean markDone(int id) {
        boolean isMarked = false;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            int updatedRows = session.createQuery(
                            "UPDATE Task t SET t.done = :fDone WHERE t.id = :fId", Task.class)
                    .setParameter("fDone", true)
                    .setParameter("fId", id)
                    .executeUpdate();
            transaction.commit();
            isMarked = updatedRows > 0;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            LOGGER.error("Error marking task with id: '{}' : {}", id, e.getMessage());
        } finally {
            session.close();
            LOGGER.info("Session closed after marking tasks as done");
        }
        return isMarked;
    }

    public boolean update(Task task) {
        boolean isUpdated = false;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            int updatedRows = session.createQuery(
                            "UPDATE Task t SET "
                    + "t.title = :fTitle, t.description = :fDescription WHERE t.id = :fId", Task.class)
                    .setParameter("fTitle", task.getTitle())
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            transaction.commit();
            isUpdated = updatedRows > 0;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            LOGGER.error("Error updating task '{}' : {}", task.getTitle(), e.getMessage());
        } finally {
            session.close();
            LOGGER.info("Session closed after updating tasks");
        }
        return isUpdated;
    }

    public boolean deleteById(int taskId) {
        boolean isDeleted = false;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            int updatedRows = session.createQuery(
                            "DELETE FROM Task t WHERE t.id = :fId", Task.class)
                    .setParameter("fId", taskId)
                    .executeUpdate();
            transaction.commit();
            isDeleted = updatedRows > 0;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            LOGGER.error("Error deleting task by Id '{}': {}", taskId, e.getMessage());
        } finally {
            session.close();
            LOGGER.info("Session closed after deleteById");
        }
        return isDeleted;
    }

    @Override
    public void deleteAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createQuery("DELETE FROM Task", Task.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            LOGGER.error("Error deleting all tasks '{}'", e.getMessage(), e);
        } finally {
            session.close();
            LOGGER.info("Session closed after deleteAll");
        }
    }

    public Optional<Task> findById(int taskId) {
        Session session = sessionFactory.openSession();
        try {
            Query<Task> query = session.createQuery(
                    "from Task t where t.id = :fId", Task.class);
            query.setParameter("fId", taskId);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            LOGGER.error("Error deleting task by Id '{}': {}", taskId, e.getMessage());
            return Optional.empty();
        } finally {
            if (session != null) {
                session.close();
                LOGGER.info("Session closed after findAll");
            }
        }
    }

    public Collection<Task> findAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM Task t order by t.id ASC", Task.class)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.error("Error finding all tasks", e);
            return Collections.emptyList();
        } finally {
            if (session != null) {
                session.close();
                LOGGER.info("Session closed after findAll");
            }
        }
    }

    public Collection<Task> findByStatus(boolean status) {
        Session session = sessionFactory.openSession();
        try {
            Query<Task> query = session.createQuery(
                    "from Task t where t.done = :fStatus", Task.class);
            query.setParameter("fStatus", status);
            return query.list();
        } catch (Exception e) {
            LOGGER.error("Error finding tasks by status: '{}'", status, e);
            return Collections.emptyList();
        } finally {
            if (session != null) {
                session.close();
                LOGGER.info("Session closed after findByStatus");
            }
        }
    }
}
