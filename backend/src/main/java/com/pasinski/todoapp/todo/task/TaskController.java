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
@RequestMapping(value = "api/v1/task", produces = "application/json")
public class TaskController {
    private TaskService taskService;

    @ApiOperation(value = "Get all of your tasks from specified category",
            notes = "Returns all of the tasks from a certain category based on its id, belonging to a logged in User",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", responseContainer = "List",
                    examples = @Example(value = {@ExampleProperty(value = "[\n" +
                            "  {\n" +
                            "    \"id\": 1,\n" +
                            "    \"finished\": false,\n" +
                            "    \"name\": \"Finish school Project\",\n" +
                            "    \"description\": \"Do one more commit\",\n" +
                            "    \"dueDate\": \"2022-04-14\"\n" +
                            "  }\n" +
                            "]", mediaType = "application/json")})),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", examples = @Example(value = {@ExampleProperty(value = "You have got to be logged in to access this functionality", mediaType = "application/json")})),
            @ApiResponse(code = 403, message = "You don't have access to this category", examples = @Example(value = {@ExampleProperty(value = "You don't have access to this category", mediaType = "application/json")}))
    })
    @GetMapping()
    public ResponseEntity<?> getTask(@RequestParam Long categoryId){
        List<Task> taskList = new ArrayList<>();
        try {
            taskList = taskService.getTask(categoryId);
            taskList.forEach(task -> task.setCategory(null));
        }
        catch (AccessDeniedException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN); }
        catch (NoSuchElementException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED); }
        catch (Exception e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST); }

        return new ResponseEntity<List<Task>>(taskList,HttpStatus.OK);
    }

    @ApiOperation(value = "Add a task",
            authorizations = {@Authorization(value = "basicAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS",
                    examples = @Example(
                            value = {
                                    @ExampleProperty(value = "{\n" +
                                            "  \"id\": 6,\n" +
                                            "  \"finished\": false,\n" +
                                            "  \"name\": \"New Task Name\",\n" +
                                            "  \"description\": \"Description of a task\",\n" +
                                            "  \"dueDate\": \"2022-04-12\"\n" +
                                            "}", mediaType = "application/json")})),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", examples = @Example(value = {@ExampleProperty(value = "You have got to be logged in to access this functionality", mediaType = "application/json")})),
            @ApiResponse(code = 403, message = "You don't have access to this category", examples = @Example(value = {@ExampleProperty(value = "You don't have access to this category", mediaType = "application/json")}))
    })
    @PostMapping()
    public ResponseEntity<?> addTask(@RequestBody NewTaskForm newTaskForm){
        Task task = new Task();
        try {
            task = taskService.addTask(newTaskForm);
            task.setCategory(null);
        }
        catch (AccessDeniedException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN); }
        catch (NoSuchElementException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED); }
        catch (Exception e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST); }

        return new ResponseEntity<Task>(task,HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update task",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", examples = @Example(value = {@ExampleProperty(value = "Task updated successfully", mediaType = "application/json")})),
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
        catch (Exception e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST); }

        return new ResponseEntity<String>("Task updated successfully",HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a task",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", examples = @Example(value = {@ExampleProperty(value = "Task deleted successfully", mediaType = "application/json")})),
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
        catch (Exception e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST); }

        return new ResponseEntity<String>("Task deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/planned")
    public ResponseEntity<?> getPlannedTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            tasks = taskService.getPlannedTasks();
        } catch (Exception e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST); }
    	return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }
}
