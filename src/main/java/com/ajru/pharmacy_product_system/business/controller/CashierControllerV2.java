package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.ProductSoldDto;
import com.ajru.pharmacy_product_system.business.service.ProductSoldServiceV2;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/product-ms/cashier/v2")
public class CashierControllerV2 {
    private final ProductSoldServiceV2 productSoldServiceV2;
    private final Logger logger;

    public CashierControllerV2(ProductSoldServiceV2 productSoldServiceV2) {
        this.productSoldServiceV2 = productSoldServiceV2;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    //sell Product
    @PostMapping("/product/batch/sell")
    public ResponseEntity<List<ProductSoldDto>> sellProduct(
            @RequestBody final List<ProductSoldDto> productSoldDtoList,
            final HttpServletRequest request) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "sell product, new API", request.getMethod(), request.getRequestURL());
        List<ProductSoldDto> productSoldList = productSoldServiceV2.batchSell(productSoldDtoList);
        logger.info(StringConstants.WEB_RESP.getValue(), productSoldList);
        return new ResponseEntity<>(productSoldList, HttpStatus.OK);
    }
}
