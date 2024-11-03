package com.ecommerce.project.controller;


import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

//    By using @Autowired we can remove this constructor.
//    public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/public/categories")
    // we can use the get RequestMapping instead of all other Mappings.
    //@RequestMapping(value = "/public/categories", method = RequestMethod.GET )
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @PostMapping("/public/categories")
    public ResponseEntity<String> createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added Successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        try{
            String status= categoryService.deleteCategory(categoryId);
            // we can use any of these methods!
            //return new ResponseEntity<>(status, HttpStatus.OK);
            //return ResponseEntity.Ok(status);
            return  ResponseEntity.status(HttpStatus.OK).body(status);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long categoryId){
        try{
            Category savedCategory = categoryService.updateCategory(category, categoryId);
            // we can use any of these methods!
//            return new ResponseEntity<>(status, HttpStatus.OK);
//            return ResponseEntity.OK(status);
            return new ResponseEntity<>("Updated Category with category id: " +categoryId , HttpStatus.OK);

        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
