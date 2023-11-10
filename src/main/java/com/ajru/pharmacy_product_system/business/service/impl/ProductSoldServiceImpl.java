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

        productService.getProduct(productSold.getProductId());

        final double totalSrp = this.getAmountSrp(productSold.getSrp(), productSold.getSoldQuantity(), isDiscounted);

        final double totalCapitalPrice = this.getAmountPrice(productSold.getPrice(), productSold.getSoldQuantity());

        final double profit = this.getProfit(totalSrp, totalCapitalPrice);

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

        ProductSold productSoldToDelete = this.getProductSold(productSoldId);

        Long productId = 0L;

        try {
            productId = productSoldToDelete.getProductId();
        } catch (final NullPointerException err) {
            throw new NullPointerException();
        }

        productSoldRepository.delete(productSoldToDelete);

        productService.updateProductOnCashierActivity(productId);

        return productSoldToDelete;
    }

    private double getAmountSrp(double srp, int soldQuantity, boolean isDiscounted) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        final double dblFnlGross;

        if (Boolean.TRUE.equals(isDiscounted)) {
            String finalGrossAmountStr = decimalFormat.format(
                    (srp * discountRate) * soldQuantity
            );
            dblFnlGross = Double.parseDouble(finalGrossAmountStr);
        } else {
            dblFnlGross = srp * soldQuantity;
        }

        return dblFnlGross;
    }

    private double getAmountPrice(final double dblPrice, final int dblQuantity) {
        return dblPrice * dblQuantity;
    }

    private double getProfit(final double dblSrp, final double dblCapital) {
        return dblSrp - dblCapital;
    }

}
