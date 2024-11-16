package ru.job4j.todo.repository.user;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.CrudRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUserRepository.class);
    private final CrudRepository crudRepository;

    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
            return Optional.ofNullable(user);
        } catch (Exception e) {
            LOGGER.error("Error saving user '{}' : {}", user.getName(), e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        try {
            return crudRepository.optional(
                    "FROM User WHERE login = :login AND password = :password", User.class,
                    Map.of("login", login, "password", password)
            );
        } catch (Exception e) {
            LOGGER.error("Error finding user by login '{}' and password '{}': {}", login, password, e.getMessage());
            return Optional.empty();
        }
    }

    public Collection<User> findAll() {
        return crudRepository.query("FROM User u order by u.id ASC", User.class);
    }

    public boolean deleteById(int userId) {
        crudRepository.optional(
                "DELETE FROM User u WHERE u.id = :fId", User.class,
                Map.of("fId", userId)
        );
        return true;
    }
}
