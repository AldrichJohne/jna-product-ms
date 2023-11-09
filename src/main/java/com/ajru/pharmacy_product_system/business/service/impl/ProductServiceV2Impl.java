package com.ajru.pharmacy_product_system.business.service.impl;

import com.ajru.pharmacy_product_system.business.model.dto.ProductDto;
import com.ajru.pharmacy_product_system.business.model.entity.Classification;
import com.ajru.pharmacy_product_system.business.model.entity.Product;
import com.ajru.pharmacy_product_system.business.repository.ProductRepository;
import com.ajru.pharmacy_product_system.business.service.ClassificationService;
import com.ajru.pharmacy_product_system.business.service.ProductServiceV2;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import com.ajru.pharmacy_product_system.commons.exception.ClassificationNotFoundException;
import com.ajru.pharmacy_product_system.commons.exception.SavingProductException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceV2Impl implements ProductServiceV2 {

    private final ProductRepository productRepository;
    private final ClassificationService classificationService;
    private final Logger logger;

    public ProductServiceV2Impl(final ProductRepository productRepository,
                                final ClassificationService classificationService) {
        this.productRepository = productRepository;
        this.classificationService = classificationService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public List<ProductDto> setUpProducts(final List<ProductDto> productDtoList) {

        final List<Product> products = productDtoList.stream()
                .map(Product::from)
                .collect(Collectors.toList());

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            product.setGross("0");
            product.setSold(0);
            product.setProfit(0);
            product.setTotalPriceSold(0);
            product.setClassification(getClassification(productDtoList, i));
            product.setRemainingStock(product.getTotalStock() - product.getSold());
            product.setTotalPriceRemaining(product.getRemainingStock() * product.getPricePerPc());
        }

        this.batchSaveProducts(products);

        return products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    private Classification getClassification(List<ProductDto> productDtoList, int index) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "getting classification of products",
                currentMethodName);
        try {
            return classificationService.getClassification(productDtoList.get(index).getClassId());
        } catch (Exception err) {
            throw new ClassificationNotFoundException(productDtoList.get(index).getClassId());
        }
    }

    private void batchSaveProducts(final List<Product> products) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "saving multiple products to the database",
                currentMethodName);
        try {
            productRepository.saveAll(products);
        } catch (Exception err) {
            throw new SavingProductException();
        }
    }
}
