package com.pasinski.todoapp.registration;

import com.pasinski.todoapp.user.AppUser;
import com.pasinski.todoapp.user.AppUserRole;
import com.pasinski.todoapp.user.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;

    public String signUpUser(RegistrationForm registrationForm){
        if(!registrationForm.getPassword().equals(registrationForm.getRePassword()))
            throw new IllegalStateException("Passwords do not match");

        return appUserService.signUpUser(new AppUser(
                registrationForm.getName(),
                registrationForm.getEmail(),
                registrationForm.getPassword(),
                AppUserRole.USER
        ));
    }
}
