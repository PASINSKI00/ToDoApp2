package com.pasinski.todoapp.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationForm {
    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$") //Minimum eight characters, at least one uppercase and lowercase letter, one number, one special character
    private String password;

    @NotNull
    private String rePassword;

    @Override
    public String toString() {
        return "RegistrationForm{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rePassword='" + rePassword + '\'' +
                '}';
    }
}
