package com.pasinski.todoapp.todo.category;


import com.pasinski.todoapp.todo.task.TaskRepository;
import com.pasinski.todoapp.user.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    public void addCategory(Category category) {
        this.categoryRepository.save(category);
    }

    public Category updateCategory(Category updatedCategory) {
         Category category = this.categoryRepository.getById(updatedCategory.getId());
         category.setName(updatedCategory.getName());
         return category;
    }

    public void deleteCategory(Category category) {
        this.categoryRepository.delete(category);
    }
}
