package com.ajru.pharmacy_product_system.business.service;

import com.ajru.pharmacy_product_system.business.model.entity.Product;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import com.ajru.pharmacy_product_system.commons.exception.ProductNotFoundException;
import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import com.ajru.pharmacy_product_system.business.repository.ProductRepository;
import com.ajru.pharmacy_product_system.business.repository.ProductSoldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSoldRepository productSoldRepository;
    private final Logger logger;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductSoldRepository productSoldRepository) {
        this.productRepository = productRepository;
        this.productSoldRepository = productSoldRepository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public List<Product> getProducts() {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "fetch all products from the database using repository class");
        return new ArrayList<>(productRepository.findAll());
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
        new ProductNotFoundException(id));
    }

    public Product deleteProduct(Long id) {
        Product product = getProduct(id);
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "delete single products by unique id in the database using repository class");
        productRepository.delete(product);
        return product;
    }

    @Transactional
    public Product updateProduct(final Long id, final Product product) {
        Product productToEditFinal;

        Product productToEdit = getProduct(id);

        productToEdit.setName(product.getName());

        productToEdit.setTotalStock(product.getTotalStock());

        productToEdit.setExpiryDate(product.getExpiryDate());

        productToEditFinal = productToEdit;

        final String newRemainingStock = String.valueOf(productToEdit.getTotalStock() - productToEdit.getSold());

        productToEditFinal.setRemainingStock(Integer.parseInt(newRemainingStock));

        final String newTotalPriceRemaining = String.valueOf(productToEdit.getRemainingStock() * productToEdit.getPricePerPc());

        productToEditFinal.setTotalPriceRemaining((int) Double.parseDouble(newTotalPriceRemaining));

        final String newPriceSold = String.valueOf(productToEdit.getSold() * productToEdit.getPricePerPc());

        productToEditFinal.setTotalPriceSold((int) Double.parseDouble(newPriceSold));

        final String newProfit = String.valueOf((productToEdit.getSrpPerPc() - productToEdit.getPricePerPc()) * productToEdit.getSold());

        productToEditFinal.setProfit((int) Double.parseDouble(newProfit));

        return productToEditFinal;
    }

    @Transactional
    public void updateProductOnCashierActivity(final Long productId) {

        final List<ProductSold> productSoldList;
        int totalQuantity = 0;
        double totalProfit = 0.00;
        double totalGrossAmount = 0.00;

        try {
            productSoldList = productSoldRepository.findByProductId(productId);
        } catch (final Exception err) {
            throw new ProductNotFoundException(productId);
        }

        final Product productParentToUpdate = getProduct(productId);

        for (final ProductSold sold : productSoldList) {
            totalQuantity = totalQuantity + sold.getSoldQuantity();
            totalProfit = totalProfit + sold.getProfit();
            totalGrossAmount = totalGrossAmount + sold.getAmount();
        }

        productParentToUpdate.setGross(String.valueOf(totalGrossAmount));

        productParentToUpdate.setSold(totalQuantity);

        productParentToUpdate.setProfit(totalProfit);

        productParentToUpdate.setRemainingStock(
                productParentToUpdate.getTotalStock() - totalQuantity
        );

        productParentToUpdate.setTotalPriceRemaining(
                productParentToUpdate.getRemainingStock() * productParentToUpdate.getPricePerPc()
        );

        productParentToUpdate.setTotalPriceSold(
               totalQuantity * productParentToUpdate.getPricePerPc()
        );
    }

}
