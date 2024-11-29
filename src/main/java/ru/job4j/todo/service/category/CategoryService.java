package ru.job4j.todo.service.category;

import ru.job4j.todo.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    List<Category> findByListOfId(List<Integer> categoriesId);
}
