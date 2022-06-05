package com.ajru.pharmacy_product_system.controller;

import ch.qos.logback.classic.joran.action.EvaluatorAction;
import com.ajru.pharmacy_product_system.model.Classification;
import com.ajru.pharmacy_product_system.model.Product;
import com.ajru.pharmacy_product_system.model.dto.ClassificationDto;
import com.ajru.pharmacy_product_system.model.dto.ProductDto;
import com.ajru.pharmacy_product_system.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/classifications")
@CrossOrigin(origins = "http://localhost:4200")
public class ClassificationController {

    private final ClassificationService classificationService;

    @Autowired
    public ClassificationController(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ClassificationDto> addClassification(@RequestBody final ClassificationDto classificationDto) {
        Classification classification = classificationService.addClassification(Classification.from(classificationDto));
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    @RequestMapping
    public ResponseEntity<List<ClassificationDto>> getClassifications() {
        List<Classification> classifications = classificationService.getClassifications();
        List<ClassificationDto> classificationsDto = classifications.stream().map(ClassificationDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(classificationsDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}")
    public ResponseEntity<ClassificationDto> getClassification(@PathVariable final Long id) {
        Classification classification = classificationService.getClassification(id);
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ClassificationDto> deleteClassification(@PathVariable final Long id) {
        Classification classification = classificationService.deleteClassification(id);
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<ClassificationDto> getClassification(@PathVariable final Long id, @RequestBody final ClassificationDto classificationDto) {
        Classification classification = classificationService.editClassification(id, Classification.from(classificationDto));
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    //add product
    @RequestMapping(value = "{classificationId}/products/{productId}/add", method = RequestMethod.POST)
    public ResponseEntity<ClassificationDto> addProductToClassification(@PathVariable final Long classificationId,
                                                                        @PathVariable final Long productId) {
        Classification classification = classificationService.addProductToClassification(classificationId, productId);
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }

    @RequestMapping(value = "{classificationId}/products/{productId}/remove", method = RequestMethod.DELETE)
    public ResponseEntity<ClassificationDto> removeProductFromClassification(@PathVariable final Long classificationId,
                                                                        @PathVariable final Long productId) {
        Classification classification = classificationService.removeProductFromClassification(classificationId, productId);
        return new ResponseEntity<>(ClassificationDto.from(classification), HttpStatus.OK);
    }
}
