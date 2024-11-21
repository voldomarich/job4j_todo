package ru.job4j.todo.repository.priority;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@AllArgsConstructor
public class HibernatePriorityRepository implements PriorityRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernatePriorityRepository.class);
    private final CrudRepository crudRepository;

    @Override
    public Collection<Priority> findAll() {
        List<Priority> result = new ArrayList<>();
        try {
            result = crudRepository.query("FROM Priority", Priority.class);
        } catch (Exception e) {
            LOGGER.error("Произошла ошибка во время поиска списка приоритетов: " + e.getMessage());
        }
        return result;
    }
}
