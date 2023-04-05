package com.ajru.pharmacy_product_system.business.service.impl;

import com.ajru.pharmacy_product_system.business.model.dto.DailyMonthlyReportDto;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.repository.ProductSoldRepository;
import com.ajru.pharmacy_product_system.business.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ProductSoldRepository productSoldRepository;

    public ReportServiceImpl(ProductSoldRepository productSoldRepository) {
        this.productSoldRepository = productSoldRepository;
    }

    @Override
    public DailyMonthlyReportDto getProductSoldByDateRange(LocalDate startDate, LocalDate endDate) {
        final List<ProductSold> productSoldList  = productSoldRepository.findByTransactionDateBetween(startDate, endDate);
        double totalAmount = 0.0;
        double totalProfit = 0.0;
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy");

        for (ProductSold productSold : productSoldList) {
            totalAmount += productSold.getAmount();
            totalProfit += productSold.getProfit();
        }

        return new DailyMonthlyReportDto(
                startDate.format(formatter) + " - " + endDate.format(formatter),
                String.valueOf(totalProfit),
                String.valueOf(totalAmount)
        );
    }
}
