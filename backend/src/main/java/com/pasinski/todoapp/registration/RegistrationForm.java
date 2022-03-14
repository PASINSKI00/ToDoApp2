package com.pasinski.todoapp.registration;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "First name of the new user", required = true, example ="Krystian")
    private String name;

    @NotNull
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$")
    @ApiModelProperty(notes = "String + @ + String + . + String" ,required = true, example = "email@email.com")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$") //Minimum eight characters, at least one uppercase and lowercase letter, one number, one special character
    @ApiModelProperty(notes = "Minimum eight characters, at least one uppercase and lowercase letter, one number, one special character", required = true, example = "Password1!")
    private String password;

    @NotNull
    @ApiModelProperty(required = true, example = "Password1!")
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
