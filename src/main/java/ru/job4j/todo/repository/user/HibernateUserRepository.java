package ru.job4j.todo.repository.user;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUserRepository.class);
    private final SessionFactory sessionFactory;

    public Optional<User> save(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(user);
            transaction.commit();
            return Optional.of(user);
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            LOGGER.error("Error saving user '{}' : {}", user.getName(), e.getMessage());
        } finally {
            session.close();
            LOGGER.info("Session closed after saving of user");
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM User WHERE login = :login AND password = :password", User.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .uniqueResultOptional();
        } catch (Exception e) {
            LOGGER.error("Error finding user by login '{}' and password '{}': {}", login, password, e.getMessage());
        } finally {
            session.close();
            LOGGER.info("Session closed after finding of user by login And password");
        }
        return Optional.empty();
    }

    public Collection<User> findAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM User u order by u.id ASC", User.class)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.error("Error finding all users", e);
            return Collections.emptyList();
        } finally {
            if (session != null) {
                session.close();
                LOGGER.info("Session closed after findAll");
            }
        }
    }

    public boolean deleteById(int userId) {
        boolean isDeleted = false;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            int updatedRows = session.createQuery(
                            "DELETE FROM User u WHERE u.id = :fId", User.class)
                    .setParameter("fId", userId)
                    .executeUpdate();
            transaction.commit();
            isDeleted = updatedRows > 0;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            LOGGER.error("Error deleting user by Id '{}': {}", userId, e.getMessage());
        } finally {
            session.close();
            LOGGER.info("Session closed after deleteById");
        }
        return isDeleted;
    }
}
