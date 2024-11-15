package ru.job4j.todo.repository.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.CrudRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private final CrudRepository crudRepository;

    public Optional<User> save(User user) {
        crudRepository.run(session -> session.persist(user));
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "FROM User WHERE login = :login AND password = :password", User.class,
                Map.of("login", login, "password", password)
        );
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
