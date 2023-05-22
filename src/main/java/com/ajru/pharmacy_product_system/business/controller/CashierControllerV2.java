package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.ProductSoldDto;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.service.ProductSoldServiceV2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-ms/cashier/v2")
public class CashierControllerV2 {
    private final ProductSoldServiceV2 productSoldServiceV2;

    public CashierControllerV2(ProductSoldServiceV2 productSoldServiceV2) {
        this.productSoldServiceV2 = productSoldServiceV2;
    }

    //sell Product
    @PostMapping("/product/batch/sell")
    public ResponseEntity<List<ProductSoldDto>> sellProduct(
            @RequestBody final List<ProductSoldDto> productSoldDtoList) {
        List<ProductSoldDto> productSoldList = productSoldServiceV2.batchSell(productSoldDtoList);
        return new ResponseEntity<>(productSoldList, HttpStatus.OK);
    }
}
