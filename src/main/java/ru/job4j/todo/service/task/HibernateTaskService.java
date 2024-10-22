package ru.job4j.todo.service.task;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.task.TaskRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class HibernateTaskService implements TaskService {

    private final TaskRepository taskRepository;

    public HibernateTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public boolean markDone(int id) {
        return taskRepository.markDone(id);
    }

    @Override
    public boolean update(Task task) {
        return taskRepository.update(task);
    }

    @Override
    public boolean deleteById(int id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        taskRepository.deleteAll();
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Collection<Task> findByStatus(boolean status) {
        return taskRepository.findByStatus(status);
    }
}
