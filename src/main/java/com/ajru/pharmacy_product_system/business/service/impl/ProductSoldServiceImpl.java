package com.ajru.pharmacy_product_system.business.service.impl;

import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import com.ajru.pharmacy_product_system.commons.exception.ProductNotFoundException;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.repository.ProductSoldRepository;
import com.ajru.pharmacy_product_system.business.service.ProductService;
import com.ajru.pharmacy_product_system.business.service.ProductSoldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSoldServiceImpl implements ProductSoldService {

    private final ProductSoldRepository productSoldRepository;
    private final ProductService productService;
    private final double discountRate;
    private final Logger logger;

    public ProductSoldServiceImpl(
            ProductSoldRepository productSoldRepository,
            ProductService productService,
            @Value("${discount.rate}") double discountRate) {
        this.productSoldRepository = productSoldRepository;
        this.productService = productService;
        this.discountRate = discountRate;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public ProductSold sellProduct(ProductSold productSold, Boolean isDiscounted) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "setup product to be sold");
        productService.getProduct(productSold.getProductId());

        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                "calling getAmountSrp method", currentMethodName);
        double totalSrp = this.getAmountSrp(productSold.getSrp(), productSold.getSoldQuantity(), isDiscounted);

        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                "calling getAmountPrice method", currentMethodName);
        double totalCapitalPrice = this.getAmountPrice(productSold.getPrice(), productSold.getSoldQuantity());

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "calling getProfit method", currentMethodName);
        double profit = this.getProfit(totalSrp, totalCapitalPrice);

        ProductSold productSoldFinal;
        productSoldFinal = productSold;
        productSoldFinal.setAmount(totalSrp);
        productSoldFinal.setProfit(profit);
        productSoldFinal.setIsDiscounted(String.valueOf(isDiscounted));

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "saving updated product to the database", currentMethodName);
        productSoldRepository.save(productSoldFinal);

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "calling updateProductOnCashierActivity in ProductService", currentMethodName);
        productService.updateProductOnCashierActivity(
                productSold.getProductId());

        return productSoldFinal;
    }

    public List<ProductSold> getProductSold() {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "fetching all sold product records");
        return new ArrayList<>(productSoldRepository.findAll());
    }

    @Override
    public ProductSold getProductSold(Long id) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "fetching sold product record by unique id");
        return productSoldRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(id));
    }

    @Override
    public ProductSold deleteProductSoldRecordAndReverseProductData(Long productSoldId) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "setting up deletion of sold product record");

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "calling getProductSold method to fetch the sold record of specific product to be remove",
                currentMethodName);
        ProductSold productSoldToDelete = getProductSold(productSoldId);

        Long productId = productSoldToDelete.getProductId();
        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "deleting sold product record by unique id",
                currentMethodName);
        productSoldRepository.delete(productSoldToDelete);

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "calling updateProductOnCashierActivity in ProductService", currentMethodName);
        productService.updateProductOnCashierActivity(productId);

        return productSoldToDelete;
    }

    private double getAmountSrp(double srp, int soldQuantity, boolean isDiscounted) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "calculating products SRP");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double finalGrossAmount;

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "checking if transaction is discounted",
                currentMethodName);
        if (Boolean.TRUE.equals(isDiscounted)) {
            String finalGrossAmountStr = decimalFormat.format(
                    (srp * discountRate) * soldQuantity
            );
            finalGrossAmount = Double.parseDouble(finalGrossAmountStr);
        } else {
            finalGrossAmount = srp * soldQuantity;
        }
        return finalGrossAmount;
    }

    private double getAmountPrice(double price, int soldQuantity) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "calculating products Price");
        return price * soldQuantity;
    }

    private double getProfit(double totalSrp, double totalCapitalPrice) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "calculating products Profit");
        return totalSrp - totalCapitalPrice;
    }

}
