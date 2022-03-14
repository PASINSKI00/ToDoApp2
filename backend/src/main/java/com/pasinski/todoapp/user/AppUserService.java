package com.pasinski.todoapp.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private AppUserRepository appUserRepository;
    private PasswordEncoder bCryptPasswordEncoder;

    public String signUpUser(AppUser appUser) {
        if(appUserRepository.findByEmail(appUser.getEmail()).isPresent())
            throw new IllegalStateException("Email already taken");

        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));

        appUserRepository.save(appUser);
        enableAppUser(appUser);

        return "User has been successfully registered";
    }

    public int enableAppUser(AppUser appUser){
        return appUserRepository.enableAppUser(appUser.getEmail());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }
}
