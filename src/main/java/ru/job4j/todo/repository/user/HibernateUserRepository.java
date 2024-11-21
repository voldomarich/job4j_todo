package ru.job4j.todo.repository.user;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.CrudRepository;

import java.util.*;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUserRepository.class);
    private final CrudRepository crudRepository;

    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            LOGGER.error("Ошибка при сохранении пользователя '{}' : {}", user.getName(), e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        try {
            return crudRepository.optional(
                    "FROM User WHERE login = :login AND password = :password", User.class,
                    Map.of("login", login, "password", password)
            );
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске пользователя по логину '{}' и паролю '{}': {}", login, password, e.getMessage());
        }
        return Optional.empty();
    }

    public Collection<User> findAll() {
        List<User> result = new ArrayList<>();
        try {
            result = crudRepository.query("FROM User u order by u.id ASC", User.class);
        } catch (Exception e) {
            LOGGER.error("Произошла ошибка во время поиска списка пользователей: " + e.getMessage());
        }
        return result;
    }

    public boolean deleteById(int userId) {
        boolean result = false;
        try {
            crudRepository.run("DELETE User u WHERE u.id = :fId", Map.of("fId", userId));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Ошибка при удалении пользователя: " + e.getMessage());
        }
        return result;
    }
}
