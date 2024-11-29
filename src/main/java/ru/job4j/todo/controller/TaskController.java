package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.category.CategoryService;
import ru.job4j.todo.service.priority.PriorityService;
import ru.job4j.todo.service.task.HibernateTaskService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
@ThreadSafe
public class TaskController {

    private final HibernateTaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String getPageList(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/done")
    public String getAllDone(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(true));
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getAllNew(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(false));
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Task with id=" + id + " is not found");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping("/update/{id}")
    public String getEditPage(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Task with id=" + id + " is not found");
            return "errors/404";
        }
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("task", taskOptional.get());
        return "tasks/edit";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute("Task") Task task, HttpSession session,
                             @RequestParam(value = "category.id") List<Integer> categoriesId) {
        var user = (User) session.getAttribute("user");
        task.setUser(user);
        task.setCreated(LocalDateTime.now());
        List<Category> categories = categoryService.findByListOfId(categoriesId);
        task.setCategories(categories);
        taskService.save(task);
        return "redirect:/tasks/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Task is not found");
            return "errors/404";
        }
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Task is not found");
            return "errors/404";
        }
        return "redirect:/";
    }

    @PostMapping("/done/{id}")
    public String updateStatus(@ModelAttribute Task task, Model model, @PathVariable int id) {
        var isUpdated = taskService.markDone(id);
        if (!isUpdated) {
            model.addAttribute("message", "Task is not found");
            return "errors/404";
        }
        return "redirect:/";
    }
}
