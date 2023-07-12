package com.ajru.pharmacy_product_system.business.service.impl;

import com.ajru.pharmacy_product_system.business.model.dto.DailyMonthlyReportDto;
import com.ajru.pharmacy_product_system.business.model.dto.ReportByClassDto;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.repository.ProductSoldRepository;
import com.ajru.pharmacy_product_system.business.service.ReportService;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ProductSoldRepository productSoldRepository;
    private final Logger logger;

    public ReportServiceImpl(ProductSoldRepository productSoldRepository) {
        this.productSoldRepository = productSoldRepository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public DailyMonthlyReportDto getProductSoldByDateRange(LocalDate startDate, LocalDate endDate) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "setting up report by given date range");

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "finding transactions between given date ranges", currentMethodName);
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

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "calculating Gross and Profit of all fetched transactions", currentMethodName);
        for (ProductSold productSold : productSoldList) {
            totalAmount += productSold.getAmount();
            totalProfit += productSold.getProfit();
        }

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "calculating Gross and Profit of by product category/classification", currentMethodName);
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

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "adding calculated gross and profit by category breakdown", currentMethodName);
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
