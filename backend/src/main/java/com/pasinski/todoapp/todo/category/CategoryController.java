package com.pasinski.todoapp.todo.category;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("api/v1/category")
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> categories = new ArrayList<>();
        try {
            categories = categoryService.getCategories();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> addCategory(@RequestBody Category category){
        try {
            categoryService.addCategory(category);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Category created successfully", HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        Category updatedCategory;
        try {
           updatedCategory = categoryService.updateCategory(category);
         } catch (Exception e) {
            return new ResponseEntity<Category>(category,HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity<Category>(updatedCategory, HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteCategory(@RequestBody Category category){
        try {
            categoryService.deleteCategory(category);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Category deleted successfully", HttpStatus.CREATED);
    }
}
