package com.pasinski.todoapp.registration;

import com.pasinski.todoapp.user.AppUser;
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
            @ApiResponse(code=201,
                    message = "CREATED",
                    response = AppUser.class,
                    examples = @Example(value = {@ExampleProperty(value = "User created successfully", mediaType = "application/json")})),
            @ApiResponse(code = 400, message = "BAD REQUEST", response = String.class, examples = @Example(value = {@ExampleProperty(value = "Passwords do not match", mediaType = "application/json")})),
            @ApiResponse(code = 401, message = "UNAUTHORIZED", response = String.class, examples = @Example(value = {@ExampleProperty(value = "Email already taken", mediaType = "application/json")}))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<String> signUpUser(@Valid @RequestBody RegistrationForm registrationForm){
        AppUser appUser;
        try {
            appUser = registrationService.signUpUser(registrationForm);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("User created successfully",HttpStatus.CREATED);
    }
}
