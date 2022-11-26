package com.mikesh.blog.controllers;

import com.mikesh.blog.payloads.CategoryDTO;
import com.mikesh.blog.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryServices categoryServices;

    @GetMapping
    public List<CategoryDTO> getAllCategories(){
        return this.categoryServices.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public CategoryDTO getCategoryById(@PathVariable Integer categoryId){
        return this.categoryServices.getCategoryById(categoryId);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDTO> addNewCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO addedCategoryDTO = this.categoryServices.addCategory(categoryDTO);
        return new ResponseEntity<>(addedCategoryDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer categoryId){
        CategoryDTO updatedCategoryDTO = this.categoryServices.updateCategory(categoryDTO, categoryId);
        return ResponseEntity.ok(updatedCategoryDTO);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Integer categoryId){
        this.categoryServices.deleteCategory(categoryId);
    }
}
