package com.ajru.pharmacy_product_system.controller;

import com.ajru.pharmacy_product_system.model.Classification;
import com.ajru.pharmacy_product_system.model.Product;
import com.ajru.pharmacy_product_system.model.dto.ClassificationDto;
import com.ajru.pharmacy_product_system.model.dto.ProductDto;
import com.ajru.pharmacy_product_system.service.ClassificationService;
import com.ajru.pharmacy_product_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/inventory")
public class InventoryController {

    private final ClassificationService classificationService;
    private final ProductService productService;

    @Autowired
    public InventoryController(ClassificationService classificationService, ProductService productService) {
        this.classificationService = classificationService;
        this.productService = productService;
    }

    //for classification

    //add classification
    @RequestMapping(value = "/classifications",method = RequestMethod.POST)
    public ResponseEntity<ClassificationDto> addClassification(@RequestBody final ClassificationDto classificationDto) {
        Classification classification = classificationService.addClassification(Classification.from(classificationDto));
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    //find all classifications
    @RequestMapping("/classifications")
    public ResponseEntity<List<ClassificationDto>> getClassifications() {
        List<Classification> classifications = classificationService.getClassifications();
        List<ClassificationDto> classificationsDto = classifications.stream().map(ClassificationDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(classificationsDto, HttpStatus.OK);
    }

    //find a classification
    @RequestMapping(value = "/classifications/{id}")
    public ResponseEntity<ClassificationDto> getClassification(@PathVariable final Long id) {
        Classification classification = classificationService.getClassification(id);
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    //edit a classification
    @RequestMapping(value = "/classifications/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ClassificationDto> getClassification(@PathVariable final Long id, @RequestBody final ClassificationDto classificationDto) {
        Classification classification = classificationService.editClassification(id, Classification.from(classificationDto));
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    //for product

    //add product with a classification
    @RequestMapping(value = "{classificationId}/products", method = RequestMethod.POST)
    public ResponseEntity<ClassificationDto> addProductToClassificationDirectly(@PathVariable final Long classificationId,
                                                                                @RequestBody final ProductDto productDto) {
        Classification classification = classificationService.addProductToClassificationDirectly(classificationId, Product.from(productDto));
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    //delete a product
    @RequestMapping(value = "/products/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable final Long id) {
        Product product = productService.deleteProduct(id);
        return new ResponseEntity<>(ProductDto.from(product), HttpStatus.OK);
    }

    //edit a product
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ProductDto> editProduct(@PathVariable final Long id, @RequestBody final ProductDto productDto) {
        Product editedProduct = productService.editProduct(id, Product.from(productDto));
        return new ResponseEntity<>(ProductDto.from(editedProduct), HttpStatus.OK);
    }

    //find a product
    @RequestMapping(value = "/products/{id}" )
    public ResponseEntity<ProductDto> getProduct(@PathVariable final Long id) {
        Product product = productService.getProduct(id);
        return new ResponseEntity<>(ProductDto.from(product), HttpStatus.OK);
    }

    //find all products
    @RequestMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<Product> products = productService.getProducts();//store products
        List<ProductDto> productsDto = products.stream().map(ProductDto::from).collect(Collectors.toList());//convert products to productsDto
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }
}
