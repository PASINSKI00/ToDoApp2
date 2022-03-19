package com.pasinski.todoapp.security;

import com.pasinski.todoapp.todo.category.Category;
import com.pasinski.todoapp.user.AppUser;
import com.pasinski.todoapp.user.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.NoSuchElementException;
import java.util.Objects;

@AllArgsConstructor
public class OwnershipChecker {
    private AppUserRepository appUserRepository;

    public AppUser getLoggedInUser() throws NoSuchElementException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchElementException("You have got to be logged in to access this functionality"));
    }

    public boolean checkIfUserDoesntOwnCategory(Category category, AppUser appUser){
        return !Objects.equals(category.getUser().getId(), appUser.getId());
    }

    @Bean
    public OwnershipChecker createOwnershipChecker(){
        return new OwnershipChecker(appUserRepository);
    }
}
