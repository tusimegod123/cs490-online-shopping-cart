package com.cs490.shoppingCart.ProductManagementModule.mapper;

import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryResponse;
import com.cs490.shoppingCart.ProductManagementModule.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    @Mapping(target = "categoryId", ignore = true)
    Category fromCategoryRequestToDomain(CategoryRequest categoryRequest);

    CategoryResponse fromCategoryResponseToDomain(Category category);

    Category fromCategoryResponseToCategory(CategoryResponse categoryResponse);
}
