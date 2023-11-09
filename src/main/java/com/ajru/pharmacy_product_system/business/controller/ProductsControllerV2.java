package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.ProductDto;
import com.ajru.pharmacy_product_system.business.service.ProductServiceV2;
import com.ajru.pharmacy_product_system.commons.constants.LogType;
import com.ajru.pharmacy_product_system.commons.dto.GenericExceptionResponseDto;
import com.ajru.pharmacy_product_system.commons.exception.ClassificationNotFoundException;
import com.ajru.pharmacy_product_system.commons.exception.SavingProductException;
import com.ajru.pharmacy_product_system.commons.service.LoggerCentralService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/product-ms/v2/products")
public class ProductsControllerV2 {
    private final ProductServiceV2 productServiceV2;
    private final LoggerCentralService loggerCentral;

    public ProductsControllerV2(
            final ProductServiceV2 productServiceV2,
            final LoggerCentralService loggerCentral) {
        this.productServiceV2 = productServiceV2;
        this.loggerCentral = loggerCentral;
    }

    @PostMapping("/batch")
    public ResponseEntity<Object> saveMultipleProducts(
            @RequestBody final List<ProductDto> productDto,
            final HttpServletRequest request) {

        final GenericExceptionResponseDto exceptionResponseDto = new GenericExceptionResponseDto();
        this.loggerCentral.logApiRequest(
                LogType.INFO,
                "API Request: save multiple products",
                request.getMethod(),
                request.getRequestURL().toString()
        );

        try {
            final List<ProductDto> productDtoList = productServiceV2.setUpProducts(productDto);
            this.loggerCentral.logApiResponse(LogType.DEBUG, productDtoList);
            return new ResponseEntity<>(productDtoList, HttpStatus.OK);
        } catch (final ClassificationNotFoundException | SavingProductException err) {
            loggerCentral.logException(err);
            exceptionResponseDto.setErrorCode(HttpStatus.BAD_REQUEST.toString());
            exceptionResponseDto.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            exceptionResponseDto.setMessage(err.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDto);
        } catch (final Exception err) {
            loggerCentral.logException(err);
            exceptionResponseDto.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            exceptionResponseDto.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            exceptionResponseDto.setMessage(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponseDto);
        }
    }

}
