package ru.job4j.todo.service.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.category.CategoryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class HibernateCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findByListOfId(List<Integer> categoriesId) {
        return categoryRepository.findByListOfId(categoriesId);
    }
}
