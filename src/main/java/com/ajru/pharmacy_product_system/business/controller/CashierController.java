package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.ProductSoldDto;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.service.ProductSoldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product-ms/cashier")
public class CashierController {

    private final ProductSoldService productSoldService;

    public CashierController(ProductSoldService productSoldService) {
        this.productSoldService = productSoldService;
    }

    //get all products sold
    @GetMapping("/products/sell")
    public ResponseEntity<List<ProductSoldDto>> getProductSold() {
        List<ProductSold> productSold = productSoldService.getProductSold();//store products
        List<ProductSoldDto> productSoldDto = productSold.stream().map(ProductSoldDto::from).collect(Collectors.toList());//convert products to productsDto
        return new ResponseEntity<>(productSoldDto, HttpStatus.OK);
    }

    //sell Product
    @PostMapping("/product/sell/{id}")
    public ResponseEntity<ProductSoldDto> sellProduct(
            @PathVariable final Long id,
            @RequestBody final ProductSoldDto productSoldDto,
            @RequestParam("discountSwitch") final Boolean isDiscounted) {
        ProductSold productSold = productSoldService.sellProduct(ProductSold.from(productSoldDto), isDiscounted);
        return new ResponseEntity<>(ProductSoldDto.from(productSold), HttpStatus.OK);
    }

    @DeleteMapping("/product/sell/{id}")
    public ResponseEntity<ProductSoldDto> deleteProductSoldAndReverseProductData(
            @PathVariable final Long id
    ) {
        ProductSold productSold = productSoldService.deleteProductSoldRecordAndReverseProductData(id);
        return new ResponseEntity<>(ProductSoldDto.from(productSold), HttpStatus.OK);
    }
}
