package com.cs490.shoppingCart.ProductManagementModule.controller;

import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.IdNotMatchException;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.service.CategoryService;
import jakarta.validation.Valid;
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

    @PostMapping
    public CategoryResponse addCategory(@RequestBody @Valid CategoryRequest categoryRequest) {

        return categoryService.createCategory(categoryRequest);
    }

    @GetMapping()
    public List<CategoryResponse> getAllCategories() throws ItemNotFoundException {

        List<CategoryResponse> categoryList = categoryService.getAllCategories();
        return categoryList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long id) {

        CategoryResponse categoryResponse;

        try {
            categoryResponse = categoryService.getCategoryById(id);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategoryById(@PathVariable Long id, @RequestBody @Valid CategoryRequest category) {

        CategoryResponse categoryResponse;
        try {
            categoryResponse = categoryService.updateCategory(category,id);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IdNotMatchException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new  ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {

        boolean isDelete = categoryService.deleteCategoryById(id);

        if (isDelete) {
            return new ResponseEntity<>("Category with id: " + id + " is deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Category with id: " + id + " cannot be deleted", HttpStatus.NOT_FOUND);
        }
    }
}
