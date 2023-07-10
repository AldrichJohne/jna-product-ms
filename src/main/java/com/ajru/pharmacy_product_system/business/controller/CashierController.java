package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.ProductSoldDto;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.service.ProductSoldService;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product-ms/cashier")
public class CashierController {

    private final ProductSoldService productSoldService;
    private final Logger logger;

    public CashierController(ProductSoldService productSoldService) {
        this.productSoldService = productSoldService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    //get all products sold
    @GetMapping("/products/sell")
    public ResponseEntity<List<ProductSoldDto>> getProductSold(
            final HttpServletRequest request
    ) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "get products sold records", request.getMethod(), request.getRequestURL());
        List<ProductSold> productSold = productSoldService.getProductSold();//store products
        List<ProductSoldDto> productSoldDto = productSold.stream().map(ProductSoldDto::from).collect(Collectors.toList());//convert products to productsDto
        logger.info(StringConstants.WEB_RESP.getValue(), productSoldDto);
        return new ResponseEntity<>(productSoldDto, HttpStatus.OK);
    }

    //sell Product
    @PostMapping("/product/sell/{id}")
    public ResponseEntity<ProductSoldDto> sellProduct(
            @PathVariable final Long id,
            @RequestBody final ProductSoldDto productSoldDto,
            @RequestParam("discountSwitch") final Boolean isDiscounted,
            final HttpServletRequest request) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "sell product, old API", request.getMethod(), request.getRequestURL());
        ProductSold productSold = productSoldService.sellProduct(ProductSold.from(productSoldDto), isDiscounted);
        logger.info(StringConstants.WEB_RESP.getValue(), ProductSoldDto.from(productSold));
        return new ResponseEntity<>(ProductSoldDto.from(productSold), HttpStatus.OK);
    }

    @DeleteMapping("/product/sell/{id}")
    public ResponseEntity<ProductSoldDto> deleteProductSoldAndReverseProductData(
            @PathVariable final Long id,
            final HttpServletRequest request
    ) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "delete a sold product record", request.getMethod(), request.getRequestURL());
        ProductSold productSold = productSoldService.deleteProductSoldRecordAndReverseProductData(id);
        return new ResponseEntity<>(ProductSoldDto.from(productSold), HttpStatus.OK);
    }
}
