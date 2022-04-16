package com.pasinski.todoapp.todo.task;

import com.pasinski.todoapp.security.OwnershipChecker;
import com.pasinski.todoapp.todo.category.Category;
import com.pasinski.todoapp.todo.category.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {
    private TaskRepository taskRepository;
    private CategoryRepository categoryRepository;
    private OwnershipChecker ownershipChecker;

    public List<Task> getTask(Long categoryId) {
        List<Task> tasks = new ArrayList<>();
        Category category = categoryRepository.getById(categoryId);

        if(ownershipChecker.checkIfUserDoesntOwnCategory(category.getId()))
            throw new AccessDeniedException("You don't have access to this category");

        Task task = new Task();
        task.setCategory(category);

        Example<Task> example = Example.of(task);
        tasks = taskRepository.findAll(example);

        task.setFinished(true);
        example = Example.of(task);
        tasks.addAll(taskRepository.findAll(example));

        return tasks;
    }

    public Task addTask(NewTaskForm newTaskForm) {
        Category category;
        category = categoryRepository.getById(newTaskForm.categoryId());

        if(ownershipChecker.checkIfUserDoesntOwnCategory(category.getId()))
            throw new AccessDeniedException("You don't have access to this category");

        Task task = new Task();
        task.setName(newTaskForm.name());
        task.setDue_date(newTaskForm.dueDate());
        task.setDescription(newTaskForm.description());
        task.setCategory(category);
        return taskRepository.save(task);
    }

    public Task updateTask(Task updatedTask) {
        Task task = taskRepository.getById(updatedTask.getId());
        if(ownershipChecker.checkIfUserDoesntOwnCategory(task.getCategory().getId()))
            throw new AccessDeniedException("You don't have access to this category");

        task.setName(updatedTask.getName());
        task.setDue_date(updatedTask.getDue_date());
        task.setDescription(updatedTask.getDescription());
        task.setFinished(updatedTask.isFinished());
        taskRepository.save(task);
        return task;
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.getById(taskId);

        if(ownershipChecker.checkIfUserDoesntOwnCategory(task.getCategory().getId()))
            throw new AccessDeniedException("You don't have access to this category");

        taskRepository.delete(task);
    }

    public List<Task> getPlannedTasks() {
        List<Task> tasks = new ArrayList<>();

        Category category = new Category();
        category.setUser(ownershipChecker.getLoggedInUser());

        Task task = new Task();
        task.setCategory(category);

        Example<Task> example = Example.of(task);
        tasks = taskRepository.findAll(example);
        tasks = tasks.stream().filter(t -> t.getDue_date() != null).collect(Collectors.toList());

        return tasks;
    }
}
