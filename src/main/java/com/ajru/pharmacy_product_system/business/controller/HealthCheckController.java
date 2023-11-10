package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.GenericGoodResponseDto;
import com.ajru.pharmacy_product_system.business.model.dto.HealthCheckDto;
import com.ajru.pharmacy_product_system.business.service.impl.HealthCheckServiceImpl;
import com.ajru.pharmacy_product_system.commons.constants.LogType;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import com.ajru.pharmacy_product_system.commons.dto.GenericExceptionResponseDto;
import com.ajru.pharmacy_product_system.commons.service.LoggerCentralService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/product-ms/health")
public class HealthCheckController {

    private final HealthCheckServiceImpl healthCheckService;
    private final LoggerCentralService loggerCentral;

    public HealthCheckController(
            final HealthCheckServiceImpl healthCheckService,
            final LoggerCentralService loggerCentral) {
        this.healthCheckService = healthCheckService;
        this.loggerCentral = loggerCentral;
    }

    @GetMapping("/check")
    public Object getInfo(
            final HttpServletRequest request
    ) {
        this.loggerCentral.logApiRequest(
                LogType.INFO,
                "API Request: health check",
                request.getMethod(),
                request.getRequestURL().toString()
        );
        final GenericExceptionResponseDto exceptionResponseDto = new GenericExceptionResponseDto();
        try {
            final GenericGoodResponseDto genericGoodResponseDto = new GenericGoodResponseDto();
            this.loggerCentral.logApiResponse(LogType.INFO, "Success");

            final HealthCheckDto response = this.healthCheckService.healthCheck(request);

            genericGoodResponseDto.setResponseTitle("Health check");
            genericGoodResponseDto.setResponseDescription("Health check success");
            genericGoodResponseDto.setResponseObject(response);

            return genericGoodResponseDto;
        } catch (Exception err) {
            loggerCentral.logException(err);

            exceptionResponseDto.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            exceptionResponseDto.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            exceptionResponseDto.setMessage(err.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponseDto);
        }

    }
}
