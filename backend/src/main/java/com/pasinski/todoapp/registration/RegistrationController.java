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
                    examples = @Example(value = {@ExampleProperty(value = "{\n" +
                    "      \"id\": 1,\n" +
                    "      \"name\": \"Krystian\",\n" +
                    "      \"email\": \"email@email.com\",\n" +
                    "      \"password\": \"$2a$10$3BiToe5D64G0uUJtTvY1geFgfAymVQAyS46rW/1rG5csIuc/qFeU6\",\n" +
                    "      \"appUserRole\": \"USER\",\n" +
                    "      \"locked\": false,\n" +
                    "      \"enabled\": true,\n" +
                    "      \"expired\": false,\n" +
                    "      \"username\": \"email@email.com\",\n" +
                    "      \"accountNonExpired\": true,\n" +
                    "      \"credentialsNonExpired\": true,\n" +
                    "      \"authorities\": [\n" +
                    "        {\n" +
                    "          \"authority\": \"task:read\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"authority\": \"ROLE_USER\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"authority\": \"task:write\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"authority\": \"category:read\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"authority\": \"category:write\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"accountNonLocked\": true\n" +
                    "    }", mediaType = "application/json")})),
            @ApiResponse(code = 400, message = "Passwords do not match", response = String.class, examples = @Example(value = {@ExampleProperty(value = "Passwords do not match", mediaType = "application/json")})),
            @ApiResponse(code = 401, message = "Email already taken", response = String.class, examples = @Example(value = {@ExampleProperty(value = "Email already taken", mediaType = "application/json")}))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<?> signUpUser(@Valid @RequestBody RegistrationForm registrationForm){
        AppUser appUser;
        try {
            appUser = registrationService.signUpUser(registrationForm);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<AppUser>(appUser,HttpStatus.CREATED);
    }
}
