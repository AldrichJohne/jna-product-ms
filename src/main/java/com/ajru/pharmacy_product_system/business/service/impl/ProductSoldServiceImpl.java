package com.ajru.pharmacy_product_system.business.service.impl;

import com.ajru.pharmacy_product_system.business.commons.exception.ProductNotFoundException;
import com.ajru.pharmacy_product_system.business.model.entity.Product;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.repository.ProductSoldRepository;
import com.ajru.pharmacy_product_system.business.service.ProductService;
import com.ajru.pharmacy_product_system.business.service.ProductSoldService;
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

    public ProductSoldServiceImpl(
            ProductSoldRepository productSoldRepository,
            ProductService productService,
            @Value("${discount.rate}") double discountRate) {
        this.productSoldRepository = productSoldRepository;
        this.productService = productService;
        this.discountRate = discountRate;
    }

    @Override
    public ProductSold sellProduct(Long id, ProductSold productSold, Boolean isDiscounted) {

        productService.getProduct(productSold.getProductId());

        double totalSrp = this.getAmountSrp(productSold.getSrp(), productSold.getSoldQuantity(), isDiscounted);

        double totalCapitalPrice = this.getAmountPrice(productSold.getPrice(), productSold.getSoldQuantity());

        double profit = this.getProfit(totalSrp, totalCapitalPrice);

        ProductSold productSoldFinal;
        productSoldFinal = productSold;
        productSoldFinal.setAmount(totalSrp);
        productSoldFinal.setProfit(profit);
        productSoldFinal.setIsDiscounted(String.valueOf(isDiscounted));

        productSoldRepository.save(productSoldFinal);

        productService.updateProductOnCashierActivity(
                productSold.getProductId());

        return productSoldFinal;
    }

    public List<ProductSold> getProductSold() {
        return new ArrayList<>(productSoldRepository.findAll());
    }

    @Override
    public ProductSold getProductSold(Long id) {
        return productSoldRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(id));
    }

    @Override
    public ProductSold deleteProductSoldRecordAndReverseProductData(Long productSoldId) {
        ProductSold productSoldToDelete = getProductSold(productSoldId);
        Long productId = productSoldToDelete.getProductId();
        productSoldRepository.delete(productSoldToDelete);

        productService.updateProductOnCashierActivity(productId);

        return productSoldToDelete;
    }

    private double getAmountSrp(double srp, int soldQuantity, boolean isDiscounted) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double finalGrossAmount;
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
        return price * soldQuantity;
    }

    private double getProfit(double totalSrp, double totalCapitalPrice) {
        return totalSrp - totalCapitalPrice;
    }

}
