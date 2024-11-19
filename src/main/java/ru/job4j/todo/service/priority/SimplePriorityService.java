package ru.job4j.todo.service.priority;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.store.PriorityStore;

import java.util.Collection;

@Service
public class SimplePriorityService implements PriorityService {

    private final PriorityStore priorityStore;

    public SimplePriorityService(PriorityStore priorityStore) {
        this.priorityStore = priorityStore;
    }

    @Override
    public Collection<Priority> findAll() {
        return priorityStore.findAll();
    }
}
