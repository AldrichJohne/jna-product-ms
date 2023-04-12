package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.HealthCheckDto;
import com.ajru.pharmacy_product_system.business.service.impl.HealthCheckServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/health")
public class HealthCheckController {

    private final HealthCheckServiceImpl healthCheckService;

    public HealthCheckController(HealthCheckServiceImpl healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @GetMapping("/check")
    public HealthCheckDto getInfo(
            final HttpServletRequest request
    ) {
        return this.healthCheckService.healthCheck(request);
    }
}
