package com.cs490.shoppingCart.ProductManagementModule.dto;

import com.cs490.shoppingCart.ProductManagementModule.model.Category;

import java.util.List;

public class CategoryResponseDTO {
    private List<Category> categories;

    public CategoryResponseDTO(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
