package com.cs490.shoppingCart.ProductManagementModule.controller;

import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryResponseDTO;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.model.Category;
import com.cs490.shoppingCart.ProductManagementModule.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public Category addCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @GetMapping()
    public ResponseEntity<?> getAllCategories() {
        List<Category> categoryList = categoryService.getAllCategories();
        CategoryResponseDTO response = new CategoryResponseDTO(categoryList);

        if(categoryList != null && categoryList.size() > 0){
            return new ResponseEntity<>(response, HttpStatus.OK);
        } return new ResponseEntity<>("No records found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) throws ItemNotFoundException {

        Category category;

        try {
            category = categoryService.getCategoryById(id);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) throws ItemNotFoundException {

        boolean isDelete = categoryService.deleteCategoryById(id);

        if (isDelete) {
            return new ResponseEntity<>("Category with id: " + id + " is deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Category with id: " + id + " cannot be deleted", HttpStatus.NOT_FOUND);
        }
    }

}
