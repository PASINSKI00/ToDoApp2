package com.pasinski.todoapp.todo.task;

import com.pasinski.todoapp.security.OwnershipChecker;
import com.pasinski.todoapp.todo.category.Category;
import com.pasinski.todoapp.todo.category.CategoryRepository;
import org.springframework.data.domain.Example;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private CategoryRepository categoryRepository;
    private OwnershipChecker ownershipChecker;

    public List<Task> getTask(Long categoryId) {
        Category category = categoryRepository.getById(categoryId);

        if(ownershipChecker.checkIfUserDoesntOwnCategory(category, ownershipChecker.getLoggedInUser()))
            throw new AccessDeniedException("You don't have access to this category");

        Task task = new Task();
        task.setCategory(category);

        Example<Task> example = Example.of(task);

        return taskRepository.findAll(example);
    }

    public Task addTask(Task task) {
        Category category;
        category = categoryRepository.getById(task.getCategory().getId());

        if(ownershipChecker.checkIfUserDoesntOwnCategory(category, ownershipChecker.getLoggedInUser()))
            throw new AccessDeniedException("You don't have access to this category");

        return taskRepository.save(task);
    }

    public Task updateTask(Task updatedTask) {
        if(ownershipChecker.checkIfUserDoesntOwnCategory(updatedTask.getCategory(), ownershipChecker.getLoggedInUser()))
            throw new AccessDeniedException("You don't have access to this category");

        Task task = taskRepository.getById(updatedTask.getId());
        task.setName(updatedTask.getName());
        task.setDue_date(updatedTask.getDue_date());
        task.setDescription(updatedTask.getDescription());

        return task;
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.getById(taskId);

        if(ownershipChecker.checkIfUserDoesntOwnCategory(task.getCategory(), ownershipChecker.getLoggedInUser()))
            throw new AccessDeniedException("You don't have access to this category");

        taskRepository.delete(task);
    }
}
