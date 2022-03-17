package com.pasinski.todoapp.registration;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Null;

@Api(tags = "Registration Controller", consumes = "application/json", produces = "application/json")
@Validated
@RestController
@RequestMapping(value = "/api/v1/register", produces = "application/json")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @ApiOperation(value = "Register a new User")
    @ApiResponses(value = {
            @ApiResponse(code=201, message = "CREATED", response = String.class, examples = @Example(value = {@ExampleProperty(value = "User created successfully", mediaType = "application/json")})),
            @ApiResponse(code = 400, message = "Passwords do not match", response = String.class, examples = @Example(value = {@ExampleProperty(value = "Passwords do not match", mediaType = "application/json")})),
            @ApiResponse(code = 401, message = "Email already taken", response = String.class, examples = @Example(value = {@ExampleProperty(value = "Email already taken", mediaType = "application/json")}))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<String> signUpUser(@Valid @RequestBody RegistrationForm registrationForm){
        try {
            registrationService.signUpUser(registrationForm);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User created successfully",HttpStatus.CREATED);
    }
}
