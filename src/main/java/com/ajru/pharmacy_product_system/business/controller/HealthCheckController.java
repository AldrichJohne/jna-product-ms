package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.HealthCheckDto;
import com.ajru.pharmacy_product_system.business.service.impl.HealthCheckServiceImpl;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/product-ms/health")
public class HealthCheckController {

    private final HealthCheckServiceImpl healthCheckService;
    private final Logger logger;

    public HealthCheckController(HealthCheckServiceImpl healthCheckService) {
        this.healthCheckService = healthCheckService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping("/check")
    public HealthCheckDto getInfo(
            final HttpServletRequest request
    ) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "health check, get version and business info",
                request.getMethod(),
                request.getRequestURL());

        final HealthCheckDto healthCheckResponse = this.healthCheckService.healthCheck(request);

        logger.info(StringConstants.WEB_RESP.getValue(), healthCheckResponse);

        return healthCheckResponse;
    }
}
