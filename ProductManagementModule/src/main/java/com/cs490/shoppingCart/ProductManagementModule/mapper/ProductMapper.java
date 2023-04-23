package com.cs490.shoppingCart.ProductManagementModule.mapper;

import com.cs490.shoppingCart.ProductManagementModule.dto.ListProductResponseSpecificID;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(target = "productId", ignore = true)
    Product fromCreateProductRequestToDomain(ProductRequest productRequest);

    @Mapping(target = "user", ignore = true)
    ProductResponse fromCreateProductResponseToDomain(Product product) throws ItemNotFoundException;

    ProductResponse fromGetAllProductResponseToDomain(ProductResponse productResponse);

    ListProductResponseSpecificID fromDomainToListProductResponseSpecificID(Product product);

    ProductResponse fromCreateProductRequestToProductResponse(ProductRequest productRequest);

}
