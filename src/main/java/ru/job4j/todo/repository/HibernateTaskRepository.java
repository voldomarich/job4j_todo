package ru.job4j.todo.repository;

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
            LOGGER.error("Error saving task '{}' : {}", task.getTitle(), e.getMessage(), e);
        } finally {
            session.close();
        }
        return task;
    }

    public boolean markDone(Task task) {
        boolean isMarked = false;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            int updatedRows = session.createQuery(
                            "UPDATE Task t SET t.done = :fDone WHERE t.id = :fId")
                    .setParameter("fDone", true)
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            transaction.commit();
            isMarked = updatedRows > 0;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            LOGGER.error("Error marking task '{}' : {}", task.getTitle(), e.getMessage(), e);
        } finally {
            session.close();
        }
        return isMarked;
    }

    public boolean update(Task task) {
        boolean isUpdated = false;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            int updatedRows = session.createQuery(
                            "UPDATE Task t SET t.description = :fDescription WHERE t.id = :fId")
                    .setParameter("fDescription", "new description")
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            transaction.commit();
            isUpdated = updatedRows > 0;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            LOGGER.error("Error updating task '{}' : {}", task.getTitle(), e.getMessage(), e);
        } finally {
            session.close();
        }
        return isUpdated;
    }

    public boolean deleteById(int taskId) {
        boolean isDeleted = false;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            int updatedRows = session.createQuery(
                            "DELETE FROM Task t WHERE t.id = :fId")
                    .setParameter("fId", taskId)
                    .executeUpdate();
            transaction.commit();
            isDeleted = updatedRows > 0;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            LOGGER.error("Error deleting task by Id '{}': {}", taskId, e.getMessage(), e);
        } finally {
            session.close();
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
            LOGGER.error("Error deleting all of tasks '{}'", e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    public Optional<Task> findById(int taskId) {
        Session session = sessionFactory.openSession();
        try {
            Query<Task> query = session.createQuery(
                    "from Task t where t.id = :fId", Task.class);
            query.setParameter("fId", taskId);
            return query.uniqueResultOptional();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Collection<Task> findAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM Task t order by t.id ASC", Task.class)
                    .getResultList();
        } finally {
            if (session != null) {
                session.close();
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
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
