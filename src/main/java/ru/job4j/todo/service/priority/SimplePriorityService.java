package ru.job4j.todo.service.priority;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.priority.PriorityRepository;

import java.util.Collection;

@Service
public class SimplePriorityService implements PriorityService {

    private final PriorityRepository priorityRepository;

    public SimplePriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Override
    public Collection<Priority> findAll() {
        return priorityRepository.findAll();
    }
}
