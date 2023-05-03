package com.ajru.pharmacy_product_system.business.service;

import com.ajru.pharmacy_product_system.business.model.entity.Classification;
import com.ajru.pharmacy_product_system.business.repository.ClassificationRepository;
import com.ajru.pharmacy_product_system.business.model.entity.Product;
import com.ajru.pharmacy_product_system.commons.exception.ClassificationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClassificationService {

    private final ClassificationRepository classificationRepository;

    @Autowired
    public ClassificationService(ClassificationRepository classificationRepository) {
        this.classificationRepository = classificationRepository;
    }

    public Classification addClassification(Classification classification) {
        return classificationRepository.save(classification);
    }

    public List<Classification> getClassifications() {
        return new ArrayList<>(classificationRepository.findAll());
    }

    public Classification getClassification(Long id) {
        return classificationRepository.findById(id).orElseThrow(() ->
                new ClassificationNotFoundException(id));
    }

    @Transactional
    public Classification editClassification(Long id, Classification classification) {
        Classification classificationToEdit = getClassification(id);
        classificationToEdit.setName(classification.getName());
        return classificationToEdit;
    }

    //add product then direct to a classification
    @Transactional
    public Classification addProductToClassificationDirectly(Long classificationId, Product product) {
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
            throw new ClassificationNotFoundException(classificationId);
        }
    }

}
