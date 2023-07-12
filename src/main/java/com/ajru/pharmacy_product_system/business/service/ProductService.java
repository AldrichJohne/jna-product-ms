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
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "fetch single products by unique id from the database using repository class");
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
    public Product editProduct(Long id, Product product) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "update the product similar to what is in the body of the request from the database");
        Product productToEditFinal;

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "getting product to update",
                currentMethodName);
        Product productToEdit = getProduct(id);

        logger.info(StringConstants.SERVICE_LAYER_UPDATE_FROM_TO.getValue(),
                "ProductName",
                productToEdit.getName(),
                product.getName(),
                currentMethodName);
        productToEdit.setName(product.getName());

        logger.info(StringConstants.SERVICE_LAYER_UPDATE_FROM_TO.getValue(),
                "TotalStock",
                productToEdit.getTotalStock(),
                product.getTotalStock(),
                currentMethodName);
        productToEdit.setTotalStock(product.getTotalStock());

        logger.info(StringConstants.SERVICE_LAYER_UPDATE_FROM_TO.getValue(),
                "ExpiryDate",
                productToEdit.getExpiryDate(),
                product.getExpiryDate(),
                currentMethodName);
        productToEdit.setExpiryDate(product.getExpiryDate());

        productToEditFinal = productToEdit;

        final String newRemainingStock = String.valueOf(productToEdit.getTotalStock() - productToEdit.getSold());
        logger.info(StringConstants.SERVICE_LAYER_UPDATE_FROM_TO.getValue(),
                "RemainingStock (TotalStock - Sold)",
                productToEditFinal.getRemainingStock(),
                newRemainingStock,
                currentMethodName);
        productToEditFinal.setRemainingStock(Integer.parseInt(newRemainingStock));

        final String newTotalPriceRemaining = String.valueOf(productToEdit.getRemainingStock() * productToEdit.getPricePerPc());
        logger.info(StringConstants.SERVICE_LAYER_UPDATE_FROM_TO.getValue(),
                "TotalPriceRemaining (RemainingStock * PricePerPc)",
                productToEditFinal.getTotalPriceRemaining(),
                newTotalPriceRemaining,
                currentMethodName);
        productToEditFinal.setTotalPriceRemaining(Integer.parseInt(newTotalPriceRemaining));

        final String newPriceSold = String.valueOf(productToEdit.getSold() * productToEdit.getPricePerPc());
        logger.info(StringConstants.SERVICE_LAYER_UPDATE_FROM_TO.getValue(),
                "TotalPriceSold (Sold * PricePerPc)",
                productToEditFinal.getTotalPriceSold(),
                newPriceSold,
                currentMethodName);
        productToEditFinal.setTotalPriceSold(Integer.parseInt(newPriceSold));

        final String newProfit = String.valueOf((productToEdit.getSrpPerPc() - productToEdit.getPricePerPc()) * productToEdit.getSold());
        logger.info(StringConstants.SERVICE_LAYER_UPDATE_FROM_TO.getValue(),
                "TotalPriceSold ((SrpPerPc - PricePerPc) * Sold)",
                productToEditFinal.getProfit(),
                newProfit,
                currentMethodName);
        productToEditFinal.setProfit(Integer.parseInt(newProfit));

        return productToEditFinal;
    }

    @Transactional
    public void updateProductOnCashierActivity(Long productId) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "update a product being sold by unique id from the database using repository class");

        List<ProductSold> productSoldList = productSoldRepository.findByProductId(productId);
        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "getting product to update",
                currentMethodName);
        Product productParentToUpdate = getProduct(productId);

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "calculating products , soldQuantity, profit and gross amount",
                currentMethodName);
        int totalQuantity = 0;
        double totalProfit = 0.00;
        double totalGrossAmount = 0.00;
        for (ProductSold sold : productSoldList) {
            totalQuantity = totalQuantity + sold.getSoldQuantity();
            totalProfit = totalProfit + sold.getProfit();
            totalGrossAmount = totalGrossAmount + sold.getAmount();
        }

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "setting product's gross",
                currentMethodName);
        productParentToUpdate.setGross(String.valueOf(totalGrossAmount));

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "setting product's sold quantity",
                currentMethodName);
        productParentToUpdate.setSold(totalQuantity);

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "setting product's total profit",
                currentMethodName);
        productParentToUpdate.setProfit(totalProfit);

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "setting product's remaining stock",
                currentMethodName);
        productParentToUpdate.setRemainingStock(
                productParentToUpdate.getTotalStock() - totalQuantity
        );

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "setting product's price remaining",
                currentMethodName);
        productParentToUpdate.setTotalPriceRemaining(
                productParentToUpdate.getRemainingStock() * productParentToUpdate.getPricePerPc()
        );

        logger.info(StringConstants.SERVICE_LAYER_DESCRIPTION.getValue(),
                "setting product's total price sold",
                currentMethodName);
        productParentToUpdate.setTotalPriceSold(
               totalQuantity * productParentToUpdate.getPricePerPc()
        );
    }

}
