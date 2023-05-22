package com.ajru.pharmacy_product_system.business.service;

import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;

import java.util.List;

public interface ProductSoldService {
    ProductSold sellProduct(ProductSold productSold, Boolean isDiscounted);

    List<ProductSold> getProductSold();

    ProductSold getProductSold(Long id);

    ProductSold deleteProductSoldRecordAndReverseProductData(Long productId);
}
