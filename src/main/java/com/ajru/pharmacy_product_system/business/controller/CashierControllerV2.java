package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.GenericGoodResponseDto;
import com.ajru.pharmacy_product_system.business.model.dto.ProductSoldDto;
import com.ajru.pharmacy_product_system.business.service.ProductSoldServiceV2;
import com.ajru.pharmacy_product_system.commons.constants.LogType;
import com.ajru.pharmacy_product_system.commons.dto.GenericExceptionResponseDto;
import com.ajru.pharmacy_product_system.commons.exception.ProductNotFoundException;
import com.ajru.pharmacy_product_system.commons.service.LoggerCentralService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/product-ms/cashier/v2")
public class CashierControllerV2 {
    private final ProductSoldServiceV2 productSoldServiceV2;
    private final LoggerCentralService loggerCentral;

    public CashierControllerV2(
            final ProductSoldServiceV2 productSoldServiceV2,
            final LoggerCentralService loggerCentral) {
        this.productSoldServiceV2 = productSoldServiceV2;
        this.loggerCentral = loggerCentral;
    }

    @PostMapping("/product/batch/sell")
    public ResponseEntity<Object> sellProduct(
            @RequestBody final List<ProductSoldDto> productSoldDtoList,
            final HttpServletRequest request) {

        this.loggerCentral.logApiRequest(
                LogType.INFO,
                "API Request: sell products",
                request.getMethod(),
                request.getRequestURL().toString()
        );
        final GenericExceptionResponseDto exceptionResponseDto = new GenericExceptionResponseDto();

        try {
            final List<ProductSoldDto> lstProductSold = productSoldServiceV2.sellMultipleProductsSimultaneously(productSoldDtoList);
            final GenericGoodResponseDto genericGoodResponseDto = new GenericGoodResponseDto();

            this.loggerCentral.logApiResponse(
                    LogType.INFO,
                    String.format("Successfully save sold products to the database, Invoice: %s",
                            lstProductSold.get(0).getInvoiceCode()));

            genericGoodResponseDto.setResponseTitle("Save sold products records");
            genericGoodResponseDto.setResponseDescription("Successfully saved records");
            genericGoodResponseDto.setResponseObject(lstProductSold);

            return new ResponseEntity<>(genericGoodResponseDto, HttpStatus.OK);
        } catch (final ProductNotFoundException err) {
            loggerCentral.logException(err);

            exceptionResponseDto.setErrorCode(HttpStatus.BAD_REQUEST.toString());
            exceptionResponseDto.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            exceptionResponseDto.setMessage(err.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDto);
        } catch (Exception err) {
            loggerCentral.logException(err);

            exceptionResponseDto.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            exceptionResponseDto.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            exceptionResponseDto.setMessage(err.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponseDto);
        }

    }
}
