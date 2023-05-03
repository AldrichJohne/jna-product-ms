package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.ClassificationDto;
import com.ajru.pharmacy_product_system.business.model.dto.ProductDto;
import com.ajru.pharmacy_product_system.business.service.ProductServiceV2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-ms/v2/products")
public class ProductsControllerV2 {
    private final ProductServiceV2 productServiceV2;

    public ProductsControllerV2(ProductServiceV2 productServiceV2) {
        this.productServiceV2 = productServiceV2;
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProductDto>> batchSaveProducts(@RequestBody final List<ProductDto> productDto) {
        return new ResponseEntity<>(productServiceV2.setUpProducts(productDto), HttpStatus.OK);
    }
}
