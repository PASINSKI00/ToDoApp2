package com.pasinski.todoapp.todo.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pasinski.todoapp.security.OwnershipChecker;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(tags = "Category Controller", consumes = "application/json", produces = "application/json")
@RestController
@Validated
@AllArgsConstructor
@RequestMapping(value = "api/v1/category", produces = "application/json")
public class CategoryController {
    private CategoryService categoryService;

    @ApiOperation(
            value = "Get all of your categories",
            notes = "Returns all of the categories belonging to a logged in User",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = Category.class, responseContainer = "List", examples = @Example(value = {@ExampleProperty(value = "[\n" +
                    "  {\n" +
                    "    \"id\": 1,\n" +
                    "    \"name\": \"School\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"id\": 2,\n" +
                    "    \"name\": \"Work\"\n" +
                    "  }\n" +
                    "]", mediaType = "application/json")})),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", examples = @Example(value = {@ExampleProperty(value = "You have got to be logged in to access this functionality", mediaType = "application/json")}))
    })
    @GetMapping()
    public ResponseEntity<?> getCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            categories = categoryService.getCategories();
            categories.forEach(category -> category.setUser(null));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
    }


    @ApiOperation(
            value = "Add a category",
            notes = "Assigns a new category to currently logged in User",
            authorizations = {@Authorization(value = "basicAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(code=201, message = "CREATED", examples = @Example(value = {@ExampleProperty(value = "Category created successfully", mediaType = "application/json")}), response = String.class),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", examples = @Example(value = {@ExampleProperty(value = "You have got to be logged in to access this functionality", mediaType = "application/json")}))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<?> addCategory(@RequestBody String name){
        Category category = new Category();
        try {
            category = categoryService.addCategory(name);
            category.setUser(null);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Category>(category, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Update an existing category",
            notes = "Updates a received category of a logged in User, based on ID",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "SUCCESS", examples = @Example(value = {@ExampleProperty(value = "Category updated successfully", mediaType = "application/json")})),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", response = String.class, examples = @Example(value = {@ExampleProperty(value = "You have got to be logged in to access this functionality", mediaType = "application/json")})),
            @ApiResponse(code = 403, message = "FORBIDDEN", examples = @Example(value = {@ExampleProperty(value = "You don't have access to this category", mediaType = "application/json")}))
    })
    @PutMapping()
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
        Category updatedCategory;
        try {
           updatedCategory = categoryService.updateCategory(category);
         }
        catch (AccessDeniedException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN); }
        catch (NoSuchElementException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED); }

        return new ResponseEntity<String>("Category updated successfully", HttpStatus.OK);
    }

    @ApiOperation(
            value = "Delete an existing category",
            notes = "Deletes a received category of a logged in User, based on ID",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Category deleted successfully", response = String.class, examples = @Example(value = {@ExampleProperty(value = "Category deleted successfully", mediaType = "application/json")})),
            @ApiResponse(code = 401, message = "You have got to be logged in to access this functionality", response = String.class, examples = @Example(value = {@ExampleProperty(value = "You have got to be logged in to access this functionality", mediaType = "application/json")})),
            @ApiResponse(code = 403, message = "You don't have access to this category", examples = @Example(value = {@ExampleProperty(value = "You don't have access to this category", mediaType = "application/json")}))
    })
    @DeleteMapping()
    public ResponseEntity<String> deleteCategory(@RequestBody Long id){
        try {
            categoryService.deleteCategory(id);
        }
        catch (AccessDeniedException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN); }
        catch (NoSuchElementException e) { return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED); }

        return new ResponseEntity<String>("Category deleted successfully", HttpStatus.CREATED);
    }
}
