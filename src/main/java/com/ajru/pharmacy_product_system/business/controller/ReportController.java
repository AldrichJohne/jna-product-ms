package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.DailyMonthlyReportDto;
import com.ajru.pharmacy_product_system.business.service.ReportService;
import com.ajru.pharmacy_product_system.business.service.impl.ReportServiceImpl;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@RestController
@RequestMapping("/product-ms/report")
public class ReportController {

    private final ReportService reportService;
    private final Logger logger;

    public ReportController(final ReportServiceImpl reportService) {
        this.reportService = reportService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping("/range")
    public ResponseEntity<DailyMonthlyReportDto> getProductSoldByDateRange(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            final HttpServletRequest request) {
        logger.info(StringConstants.WEB_REQ.getValue(),
                "get gross and profit by date and classification",
                request.getMethod(),
                request.getRequestURL());
        final DailyMonthlyReportDto reportDto = reportService.getProductSoldByDateRange(startDate, endDate);
        logger.info(StringConstants.WEB_RESP.getValue(), reportDto);
        return new ResponseEntity<>(reportDto, HttpStatus.OK);
    }
}
