package com.ajru.pharmacy_product_system.business.controller;

import com.ajru.pharmacy_product_system.business.model.dto.DailyMonthlyReportDto;
import com.ajru.pharmacy_product_system.business.service.ReportService;
import com.ajru.pharmacy_product_system.business.service.impl.ReportServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(final ReportServiceImpl reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/bydaterange")
    public ResponseEntity<DailyMonthlyReportDto> getProductSoldByDateRange(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return new ResponseEntity<>(reportService.getProductSoldByDateRange(startDate, endDate), HttpStatus.OK);
    }
}
