package com.ajru.pharmacy_product_system.business.service.impl;

import com.ajru.pharmacy_product_system.business.model.dto.DailyMonthlyReportDto;
import com.ajru.pharmacy_product_system.business.model.dto.ReportByClassDto;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.repository.ProductSoldRepository;
import com.ajru.pharmacy_product_system.business.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        final List<ReportByClassDto> breakdown = new ArrayList<>();
        double totalAmount = 0.0;
        double totalProfit = 0.0;
        double brandedGross = 0.0;
        double brandedProfit = 0.0;
        double genericsGross = 0.0;
        double genericsProfit = 0.0;
        double galenicalsGross = 0.0;
        double galenicalsProfit = 0.0;
        double iceCreamGross = 0.0;
        double iceCreamProfit = 0.0;
        double othersGross = 0.0;
        double othersProfit = 0.0;
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy");

        for (ProductSold productSold : productSoldList) {
            totalAmount += productSold.getAmount();
            totalProfit += productSold.getProfit();
        }

        for (ProductSold productSold : productSoldList) {
            switch (productSold.getClassification()) {
                case "branded":
                    brandedGross = brandedGross + productSold.getAmount();
                    brandedProfit = brandedProfit + productSold.getProfit();
                    break;
                case "generics":
                    genericsGross = genericsGross + productSold.getAmount();
                    genericsProfit = genericsProfit + productSold.getProfit();
                    break;
                case "galenicals":
                    galenicalsGross = galenicalsGross + productSold.getAmount();
                    galenicalsProfit = galenicalsProfit + productSold.getProfit();
                    break;
                case "ice cream":
                    iceCreamGross = iceCreamGross + productSold.getAmount();
                    iceCreamProfit = iceCreamProfit + productSold.getProfit();
                    break;
                default:
                    othersGross = othersGross + productSold.getAmount();
                    othersProfit = othersProfit + productSold.getProfit();
                    break;
            }
        }

        breakdown.add(new ReportByClassDto(
                "branded", String.valueOf(brandedGross), String.valueOf(brandedProfit)
        ));
        breakdown.add(new ReportByClassDto(
                "generics", String.valueOf(genericsGross), String.valueOf(genericsProfit)
        ));
        breakdown.add(new ReportByClassDto(
                "galenicals", String.valueOf(galenicalsGross), String.valueOf(galenicalsProfit)
        ));
        breakdown.add(new ReportByClassDto(
                "ice cream", String.valueOf(iceCreamGross), String.valueOf(iceCreamProfit)
        ));
        breakdown.add(new ReportByClassDto(
                "others", String.valueOf(othersGross), String.valueOf(othersProfit)
        ));

        return new DailyMonthlyReportDto(
                startDate.format(formatter) + " - " + endDate.format(formatter),
                String.valueOf(totalProfit),
                String.valueOf(totalAmount),
                breakdown
        );
    }
}
