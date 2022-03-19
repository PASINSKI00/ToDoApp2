package com.pasinski.todoapp.todo.category;

import com.pasinski.todoapp.security.OwnershipChecker;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Api(tags = "Category Controller", consumes = "application/json", produces = "application/json")
@RestController
@Validated
@AllArgsConstructor
@RequestMapping(value = "api/v1/category", produces = "application/json")
public class CategoryController {
    private CategoryService categoryService;

    @ApiOperation(value = "Get all of your categories", notes = "Returns all of the categories belonging to a logged in User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = Category.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", examples = @Example(value = {@ExampleProperty(value = "You have got to be logged in to access this functionality", mediaType = "application/json")}))
    })
    @GetMapping()
    public ResponseEntity<?> getCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            categories = categoryService.getCategories();
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
    }

    @ApiOperation(value = "Add a category", notes = "Assigns a new category to currently logged in User")
    @ApiResponses(value = {
            @ApiResponse(code=201, message = "CREATED", examples = @Example(value = {@ExampleProperty(value = "Category created successfully", mediaType = "application/json")}), response = String.class),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", examples = @Example(value = {@ExampleProperty(value = "You have got to be logged in to access this functionality", mediaType = "application/json")}))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<String> addCategory(@RequestBody Category category){
        try {
            categoryService.addCategory(category);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Category created successfully", HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update an existing category", notes = "Updates a received category of a logged in User, based on ID")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "SUCCESS", response = Category.class),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", response = String.class),
            @ApiResponse(code = 403, message = "You don't have access to this category", examples = @Example(value = {@ExampleProperty(value = "You don't have access to this category", mediaType = "application/json")}))
    })
    @PutMapping()
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
        Category updatedCategory;
        try {
           updatedCategory = categoryService.updateCategory(category);
         }
        catch (AccessDeniedException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN); }
        catch (NoSuchElementException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED); }

        return new ResponseEntity<Category>(updatedCategory, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete an existing category", notes = "Deletes a received category of a logged in User, based on ID")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Category deleted successfully", response = String.class, examples = @Example(value = {@ExampleProperty(value = "Category deleted successfully", mediaType = "application/json")})),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", response = String.class, examples = @Example(value = {@ExampleProperty(value = "You have got to be logged in to access this functionality", mediaType = "application/json")})),
            @ApiResponse(code = 403, message = "You don't have access to this category", examples = @Example(value = {@ExampleProperty(value = "You don't have access to this category", mediaType = "application/json")}))
    })
    @DeleteMapping()
    public ResponseEntity<String> deleteCategory(@RequestBody Category category){
        try {
            categoryService.deleteCategory(category);
        }
        catch (AccessDeniedException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN); }
        catch (NoSuchElementException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED); }

        return new ResponseEntity<String>("Category deleted successfully", HttpStatus.CREATED);
    }
}
