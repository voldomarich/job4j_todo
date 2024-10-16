package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.SimpleTaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private SimpleTaskService taskService;

    public TaskController(SimpleTaskService taskService) {
        this.taskService = taskService;
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
        return "tasks/edit";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute("Task") Task task, Model model) {
        taskService.save(task);
        model.addAttribute("tasks", taskService.findAll());
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

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Task is not found");
            return "errors/404";
        }
        return "redirect:/";
    }
}
