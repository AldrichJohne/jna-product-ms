package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.GenericGoodResponseDto;
import com.ajru.pharmacy_product_system.business.model.dto.ProductSoldDto;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.service.ProductSoldService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product-ms/cashier")
public class CashierController {

    private final ProductSoldService productSoldService;
    private final LoggerCentralService loggerCentral;

    public CashierController(
            final ProductSoldService productSoldService,
            final LoggerCentralService loggerCentral) {
        this.productSoldService = productSoldService;
        this.loggerCentral = loggerCentral;
    }

    @GetMapping("/products/sell")
    public ResponseEntity<Object> fetchSoldProduct(
            final HttpServletRequest request
    ) {
        this.loggerCentral.logApiRequest(
                LogType.INFO,
                "API Request: fetch sold products",
                request.getMethod(),
                request.getRequestURL().toString()
        );
        final GenericExceptionResponseDto exceptionResponseDto = new GenericExceptionResponseDto();
        try {
            final List<ProductSold> productSold = productSoldService.getProductSold();
            final List<ProductSoldDto> productSoldDto = productSold.stream().map(ProductSoldDto::from).collect(Collectors.toList());

            final GenericGoodResponseDto genericGoodResponseDto = new GenericGoodResponseDto();

            this.loggerCentral.logApiResponse(LogType.INFO, "Successfully fetch all sold products record");

            genericGoodResponseDto.setResponseTitle("Get all sold products record");
            genericGoodResponseDto.setResponseDescription("Successfully fetch all sold products record");
            genericGoodResponseDto.setResponseObject(productSoldDto);

            return new ResponseEntity<>(genericGoodResponseDto, HttpStatus.OK);
        } catch (Exception err) {
            loggerCentral.logException(err);
            exceptionResponseDto.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            exceptionResponseDto.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            exceptionResponseDto.setMessage(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponseDto);
        }

    }

    @DeleteMapping("/product/sell/{id}")
    public ResponseEntity<Object> deleteProductSoldAndReverseProductData(
            @PathVariable final Long id,
            final HttpServletRequest request
    ) {
        this.loggerCentral.logApiRequest(
                LogType.INFO,
                "API Request: delete a sold product record",
                request.getMethod(),
                request.getRequestURL().toString()
        );
        final GenericExceptionResponseDto exceptionResponseDto = new GenericExceptionResponseDto();
        try {
            final ProductSold deletedSoldProductRecord = productSoldService.deleteProductSoldRecordAndReverseProductData(id);
            final GenericGoodResponseDto genericGoodResponseDto = new GenericGoodResponseDto();

            this.loggerCentral.logApiResponse(
                    LogType.INFO,
                    String.format("Successfully delete sold product record: %s",
                            deletedSoldProductRecord.getProductName()));

            genericGoodResponseDto.setResponseTitle("Delete sold product/s record");
            genericGoodResponseDto.setResponseDescription("Successfully deleted record");
            genericGoodResponseDto.setResponseObject(deletedSoldProductRecord);

            return new ResponseEntity<>(genericGoodResponseDto, HttpStatus.OK);
        } catch (final ProductNotFoundException | NullPointerException err) {
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
