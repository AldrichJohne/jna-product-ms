package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.ProductDto;
import com.ajru.pharmacy_product_system.business.service.ProductServiceV2;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/product-ms/v2/products")
public class ProductsControllerV2 {
    private final ProductServiceV2 productServiceV2;
    private final Logger logger;

    public ProductsControllerV2(ProductServiceV2 productServiceV2) {
        this.productServiceV2 = productServiceV2;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProductDto>> batchSaveProducts(
            @RequestBody final List<ProductDto> productDto,
            final HttpServletRequest request) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "batch addition of products", request.getMethod(), request.getRequestURL());
        final List<ProductDto> productDtoList = productServiceV2.setUpProducts(productDto);
        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }
}
