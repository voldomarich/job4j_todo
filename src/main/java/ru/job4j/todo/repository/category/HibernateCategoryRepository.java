package ru.job4j.todo.repository.category;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CrudRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateCategoryRepository.class);
    @NonNull
    private final CrudRepository crudRepository;

    @Override
    public List<Category> findAll() {
        try {
            return crudRepository.query("FROM Category ORDER BY id", Category.class);
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении всех задач" + e.getMessage());
        }
        return List.of();
    }

    @Override
    public List<Category> findByListOfId(List<Integer> categoriesId) {
        try {
            return crudRepository.query(
                    "FROM Category WHERE id IN :fCategoriesId", Category.class,
                    Map.of("fCategoriesId", categoriesId)
            );
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске категорий задач по Id" + e.getMessage());
        }
        return Collections.emptyList();
    }
}
