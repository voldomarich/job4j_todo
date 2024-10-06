package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private final SessionFactory sessionFactory;

    public Task save(Task task) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(task);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.getStatus().canRollback()) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
        return task;
    }

    public boolean markDone(Task task) {
        boolean isMarked = false;
        try (Session session = sessionFactory.openSession()) {
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
                e.printStackTrace();
            }
        }
        return isMarked;
    }

    public boolean update(Task task) {
        boolean isUpdated = false;
        try (Session session = sessionFactory.openSession()) {
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
                e.printStackTrace();
            }
        }
        return isUpdated;
    }

    public boolean deleteById(int taskId) {
        boolean isDeleted = false;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                int updatedRows = session.createQuery(
                                "DELETE FROM Task t WHERE t.id = :fId")
                        .setParameter("fId", taskId)
                        .executeUpdate();
                transaction.commit();
                isDeleted = updatedRows == 0;
            } catch (Exception e) {
                if (transaction != null && transaction.getStatus().canRollback()) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
        return isDeleted;
    }

    @Override
    public void deleteAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createQuery("DELETE FROM Task", Task.class).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.getStatus().canRollback()) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    public Optional<Task> findById(int taskId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Task> query = session.createQuery(
                    "from Task t where t.id = :fId", Task.class);
            query.setParameter("fId", taskId);
            return query.uniqueResultOptional();
        }
    }

    public Collection<Task> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Task t order by t.id ASC", Task.class)
                    .getResultList();
        }
    }

    public Collection<Task> findByStatus(boolean status) {
        try (Session session = sessionFactory.openSession()) {
            Query<Task> query = session.createQuery(
                    "from Task t where t.done = :fStatus", Task.class);
            query.setParameter("fStatus", status);
            return query.list();
        }
    }
}
