package com.cs490.shoppingCart.ProductManagementModule.service;

import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.IdNotMatchException;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.model.Category;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(CategoryRequest category, Long categoryId) throws ItemNotFoundException, IdNotMatchException;
    List<CategoryResponse> getAllCategories() throws ItemNotFoundException;
    CategoryResponse getCategoryById(Long id) throws ItemNotFoundException;
    boolean deleteCategoryById(Long id);
}
