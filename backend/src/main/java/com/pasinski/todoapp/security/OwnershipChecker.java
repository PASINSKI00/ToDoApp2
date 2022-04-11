package com.pasinski.todoapp.security;

import com.pasinski.todoapp.todo.category.Category;
import com.pasinski.todoapp.user.AppUser;
import com.pasinski.todoapp.user.AppUserRepository;
import com.pasinski.todoapp.todo.category.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.NoSuchElementException;
import java.util.Objects;

@AllArgsConstructor
public class OwnershipChecker {
    private AppUserRepository appUserRepository;
    private CategoryRepository categoryRepository;

    public AppUser getLoggedInUser() throws NoSuchElementException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchElementException("You have got to be logged in to access this functionality"));
    }

    public boolean checkIfUserDoesntOwnCategory(Long Id){
        Category category = categoryRepository.findById(Id).orElseThrow(() -> new NoSuchElementException("Category with id " + Id + " does not exist"));
        return !Objects.equals(category.getUser().getId(), getLoggedInUser().getId());
    }
}
