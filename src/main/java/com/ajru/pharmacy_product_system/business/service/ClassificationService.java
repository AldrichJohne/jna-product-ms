package com.ajru.pharmacy_product_system.business.service;

import com.ajru.pharmacy_product_system.business.model.entity.Classification;
import com.ajru.pharmacy_product_system.business.repository.ClassificationRepository;
import com.ajru.pharmacy_product_system.business.model.entity.Product;
import com.ajru.pharmacy_product_system.commons.constants.StringConstants;
import com.ajru.pharmacy_product_system.commons.exception.ClassificationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClassificationService {

    private final ClassificationRepository classificationRepository;
    private final Logger logger;

    @Autowired
    public ClassificationService(ClassificationRepository classificationRepository) {
        this.classificationRepository = classificationRepository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public Classification addClassification(Classification classification) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "add a classification to the database using repository class");
        return classificationRepository.save(classification);
    }

    public List<Classification> getClassifications() {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "fetch all classification from the database using repository class");
        return new ArrayList<>(classificationRepository.findAll());
    }

    public Classification getClassification(Long id) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "fetch a classification by unique id from the database using repository class");
        return classificationRepository.findById(id).orElseThrow(() ->
                new ClassificationNotFoundException(id));
    }

    @Transactional
    public Classification editClassification(Long id, Classification classification) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "update a classification");
        Classification classificationToEdit = getClassification(id);
        classificationToEdit.setName(classification.getName());
        return classificationToEdit;
    }

    //add product then direct to a classification
    @Transactional
    public Classification addProductToClassificationDirectly(Long classificationId, Product product) {
        final String currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
        logger.info(StringConstants.SERVICE_LAYER.getValue(),
                this.getClass().getName(),
                currentMethodName,
                "add a product in a classification");
        try {
            Product productToAdd;
            product.setGross("0");
            product.setSold(0);
            product.setProfit(0);
            product.setTotalPriceSold(0);
            productToAdd = product;
            productToAdd.setRemainingStock(product.getTotalStock() - product.getSold());
            productToAdd.setTotalPriceRemaining(productToAdd.getRemainingStock() * product.getPricePerPc());

            Classification classification = getClassification(classificationId);
            classification.addProduct(productToAdd);
            product.setClassification(classification);

            return classification;
        } catch (Exception err) {
            System.out.println("13");
            throw new ClassificationNotFoundException(classificationId);
        }
    }

}
