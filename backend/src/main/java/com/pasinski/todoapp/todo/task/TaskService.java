package com.pasinski.todoapp.todo.task;

import com.pasinski.todoapp.todo.category.Category;
import com.pasinski.todoapp.todo.category.CategoryRepository;
import com.pasinski.todoapp.user.AppUser;
import com.pasinski.todoapp.user.AppUserRepository;
import org.springframework.data.domain.Example;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private CategoryRepository categoryRepository;
    private AppUserRepository appUserRepository;

    public List<Task> getTask(Long categoryId) {
        Category category = categoryRepository.getById(categoryId);

        if(checkIfUserDoesntOwnCategory(category, getLoggedInUser()))
            throw new AccessDeniedException("You don't have access to this category");

        Task task = new Task();
        task.setCategory(category);

        Example<Task> example = Example.of(task);

        return taskRepository.findAll(example);
    }

    public Task addTask(Task task) {
        Category category;
        category = categoryRepository.getById(task.getCategory().getId());

        if(checkIfUserDoesntOwnCategory(category, getLoggedInUser()))
            throw new AccessDeniedException("You don't have access to this category");

        return taskRepository.save(task);
    }

    public Task updateTask(Task updatedTask) {
        if(checkIfUserDoesntOwnCategory(updatedTask.getCategory(), getLoggedInUser()))
            throw new AccessDeniedException("You don't have access to this category");

        Task task = taskRepository.getById(updatedTask.getId());
        task.setName(updatedTask.getName());
        task.setDue_date(updatedTask.getDue_date());
        task.setDescription(updatedTask.getDescription());

        return task;
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.getById(taskId);

        if(checkIfUserDoesntOwnCategory(task.getCategory(), getLoggedInUser()))
            throw new AccessDeniedException("You don't have access to this category");

        taskRepository.delete(task);
    }

    private AppUser getLoggedInUser() throws NoSuchElementException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchElementException("You have got to be logged in to access this functionality"));
    }

    private boolean checkIfUserDoesntOwnCategory(Category category, AppUser appUser){
        return !Objects.equals(category.getUser().getId(), appUser.getId());
    }
}
