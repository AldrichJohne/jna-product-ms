package com.ajru.pharmacy_product_system.tables.controller;

import com.ajru.pharmacy_product_system.model.Classification;
import com.ajru.pharmacy_product_system.model.Product;
import com.ajru.pharmacy_product_system.model.dto.ProductDto;
import com.ajru.pharmacy_product_system.tables.service.impl.TableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tables")
@CrossOrigin(origins = "http://localhost:4200")
public class TableController {
    private final TableServiceImpl tableService;

    @Autowired
    public TableController(TableServiceImpl tableService) {
        this.tableService = tableService;
    }

    @GetMapping("/class/{id}")
    public ResponseEntity<List<ProductDto>> findProductByClass(@PathVariable long id){
        List<Product> products = tableService.findProductByClass(id);//store products
        List<ProductDto> productsDto = products.stream().map(ProductDto::from).collect(Collectors.toList());//convert products to productsDto
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }
}
