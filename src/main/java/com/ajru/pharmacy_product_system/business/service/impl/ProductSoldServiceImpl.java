package com.ajru.pharmacy_product_system.business.service.impl;

import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import com.ajru.pharmacy_product_system.commons.exception.FetchingFromDatabaseException;
import com.ajru.pharmacy_product_system.commons.exception.ProductNotFoundException;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.repository.ProductSoldRepository;
import com.ajru.pharmacy_product_system.business.service.ProductService;
import com.ajru.pharmacy_product_system.business.service.ProductSoldService;
import org.hibernate.ObjectNotFoundException;
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

        productService.getProduct(productSold.getProductId());

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
        try {
            return new ArrayList<>(productSoldRepository.findAll());
        } catch (final Exception err) {
            throw new FetchingFromDatabaseException();
        }
    }

    @Override
    public ProductSold getProductSold(Long id) {
        return productSoldRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(id));
    }

    @Override
    public ProductSold deleteProductSoldRecordAndReverseProductData(Long productSoldId) {

        ProductSold productSoldToDelete = this.getProductSold(productSoldId);//ProductNotFoundException

        Long productId = 0L;

        try {
            productId = productSoldToDelete.getProductId(); //NullPointerException
        } catch (final NullPointerException err) {
            throw new NullPointerException();
        }

        productSoldRepository.delete(productSoldToDelete);

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
