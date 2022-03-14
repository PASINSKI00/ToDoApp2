package com.pasinski.todoapp.registration;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@ApiOperation(value = "/api/v1/register", tags = "Registration Controller")
@Validated
@RestController
@RequestMapping("/api/v1/register")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @ApiOperation(value = "Register A New User", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code=201, message = "User has been successfully registered", response = ResponseEntity.class),
            @ApiResponse(code=400, message = "Passwords do not match || Email already taken", response = ResponseEntity.class)
    })
    @PostMapping()
    public ResponseEntity<String> signUpUser(@Valid @RequestBody RegistrationForm registrationForm){
        String message;
        try {
            message = registrationService.signUpUser(registrationForm);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }
}
