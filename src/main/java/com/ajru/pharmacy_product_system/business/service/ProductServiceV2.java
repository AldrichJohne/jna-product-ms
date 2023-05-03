package com.ajru.pharmacy_product_system.business.service;

import com.ajru.pharmacy_product_system.business.model.dto.ProductDto;
import com.ajru.pharmacy_product_system.business.model.entity.Product;

import java.util.List;

public interface ProductServiceV2 {
    List<Product> setUpProducts(Long id, List<ProductDto> products);
}
