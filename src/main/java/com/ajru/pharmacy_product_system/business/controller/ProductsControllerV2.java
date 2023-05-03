package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.ProductDto;
import com.ajru.pharmacy_product_system.business.service.ProductServiceV2;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductsControllerV2 {
    private final ProductServiceV2 productServiceV2;

    public ProductsControllerV2(ProductServiceV2 productServiceV2) {
        this.productServiceV2 = productServiceV2;
    }

    @PostMapping("/save-all/{classificationId}")
    String batchSaveProducts(@PathVariable final Long classificationId,
                             @RequestBody final List<ProductDto> productDto) {
        productServiceV2.setUpProducts(classificationId, productDto);
        return "Success";
    }
}
