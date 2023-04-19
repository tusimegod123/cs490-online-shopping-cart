package com.cs490.shoppingCart.ProductManagementModule.service;

import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.IdNotMatchException;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.model.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(Category category);
    public CategoryResponse updateCategory(Category category, Long categoryId) throws ItemNotFoundException, IdNotMatchException;
    public List<Category> getAllCategories() throws ItemNotFoundException;

    public CategoryResponse getCategoryById(Long id) throws ItemNotFoundException;

    public boolean deleteCategoryById(Long id);
}
