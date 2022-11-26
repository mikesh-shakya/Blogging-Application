package com.mikesh.blog.services;

import com.mikesh.blog.payloads.CategoryDTO;
import java.util.List;

public interface CategoryServices {

    // Create
    CategoryDTO addCategory(CategoryDTO categoryDTO);

    // Update
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);

    // Read
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Integer categoryId);

    // Delete
    void deleteCategory(Integer categoryId);
}
