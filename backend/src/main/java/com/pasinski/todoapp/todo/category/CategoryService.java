package com.pasinski.todoapp.todo.category;


import com.pasinski.todoapp.security.OwnershipChecker;
import com.pasinski.todoapp.todo.task.Task;
import com.pasinski.todoapp.todo.task.TaskRepository;
import com.pasinski.todoapp.user.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;
    private OwnershipChecker ownershipChecker;

    public List<Category> getCategories() {
        Category category = new Category();
        category.setUser(ownershipChecker.getLoggedInUser());

        Example<Category> example = Example.of(category);

        return categoryRepository.findAll(example);
    }

    public Category addCategory(String name) {
        Category category = new Category();
        category.setUser(ownershipChecker.getLoggedInUser());
        category.setName(name);
        category = this.categoryRepository.save(category);
        return category;
    }

    public Category updateCategory(Category updatedCategory) {
        if(ownershipChecker.checkIfUserDoesntOwnCategory(updatedCategory.getId()))
            throw new AccessDeniedException("You don't have access to this category");

         Category category = this.categoryRepository.getById(updatedCategory.getId());
         category.setName(updatedCategory.getName());
         return category;
    }

    public void deleteCategory(Long id) {
        if(ownershipChecker.checkIfUserDoesntOwnCategory(id))
            throw new AccessDeniedException("You don't have access to this category");

        Category category = this.categoryRepository.getById(id);
        this.categoryRepository.delete(category);
    }
}
