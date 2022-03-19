package com.pasinski.todoapp.todo.task;

import com.pasinski.todoapp.todo.category.Category;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Api(tags = "Task Controller", consumes = "application/json", produces = "application/json")
@RestController
@AllArgsConstructor
@Validated
@RequestMapping(value = "task", produces = "application/json")
public class TaskController {
    private TaskService taskService;

    @ApiOperation(value = "Get all of your tasks from specified category", notes = "Returns all of the tasks from a certain category based on its id, belonging to a logged in User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = Task.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", examples = @Example(value = {@ExampleProperty(value = "You have got to be logged in to access this functionality", mediaType = "application/json")})),
            @ApiResponse(code = 403, message = "You don't have access to this category", examples = @Example(value = {@ExampleProperty(value = "You don't have access to this category", mediaType = "application/json")}))
    })
    @GetMapping()
    public ResponseEntity<?> getTask(@RequestParam Long categoryId){
        List<Task> taskList = new ArrayList<>();
        try {
            taskList = taskService.getTask(categoryId);
        }
        catch (AccessDeniedException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN); }
        catch (NoSuchElementException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED); }

        return new ResponseEntity<List<Task>>(taskList,HttpStatus.OK);
    }

    @ApiOperation(value = "Add a task")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = Task.class),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", examples = @Example(value = {@ExampleProperty(value = "You have got to be logged in to access this functionality", mediaType = "application/json")})),
            @ApiResponse(code = 403, message = "You don't have access to this category", examples = @Example(value = {@ExampleProperty(value = "You don't have access to this category", mediaType = "application/json")}))
    })
    @PostMapping()
    public ResponseEntity<?> addTask(@RequestBody @Valid Task task){
        try {
            task = taskService.addTask(task);
        }
        catch (AccessDeniedException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN); }
        catch (NoSuchElementException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED); }

        return new ResponseEntity<Task>(task,HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update task")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = Task.class),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", examples = @Example(value = {@ExampleProperty(value = "You have got to be logged in to access this functionality", mediaType = "application/json")})),
            @ApiResponse(code = 403, message = "You don't have access to this category", examples = @Example(value = {@ExampleProperty(value = "You don't have access to this category", mediaType = "application/json")}))
    })
    @PutMapping()
    public ResponseEntity<?> updateTask(@RequestBody @Valid Task updatedTask){
        try {
            taskService.updateTask(updatedTask);
        }
        catch (AccessDeniedException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN); }
        catch (NoSuchElementException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED); }

        return new ResponseEntity<Task>(updatedTask,HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a task")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = Task.class),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", examples = @Example(value = {@ExampleProperty(value = "You have got to be logged in to access this functionality", mediaType = "application/json")})),
            @ApiResponse(code = 403, message = "You don't have access to this category", examples = @Example(value = {@ExampleProperty(value = "You don't have access to this category", mediaType = "application/json")}))
    })
    @DeleteMapping()
    public ResponseEntity<?> deleteTask(@RequestParam Long taskId) {
        try {
            taskService.deleteTask(taskId);
        }
        catch (AccessDeniedException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN); }
        catch (NoSuchElementException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED); }

        return new ResponseEntity<String>("Task deleted successfully",HttpStatus.OK);
    }
}
