package com.ajru.pharmacy_product_system.business.service;

import com.ajru.pharmacy_product_system.business.model.dto.DailyMonthlyReportDto;

import java.time.LocalDate;

public interface ReportService {

    DailyMonthlyReportDto getProductSoldByDateRange(LocalDate startDate, LocalDate endDate);
}

