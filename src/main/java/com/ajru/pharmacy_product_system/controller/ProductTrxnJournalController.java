package com.ajru.pharmacy_product_system.controller;

import com.ajru.pharmacy_product_system.model.ProductTrxnJournal;
import com.ajru.pharmacy_product_system.model.dto.ProductTrxnJournalDto;
import com.ajru.pharmacy_product_system.service.impl.ProductTxnJournalServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductTrxnJournalController {

    private final ProductTxnJournalServiceImpl productTrxnJournalService;

    public ProductTrxnJournalController(ProductTxnJournalServiceImpl productTrxnJournalService) {
        this.productTrxnJournalService = productTrxnJournalService;
    }

    @PostMapping
    public ResponseEntity<ProductTrxnJournalDto> sellProduct(@RequestBody final ProductTrxnJournalDto productTrxnJournalDto) {
        ProductTrxnJournal productTrxnJournal = productTrxnJournalService.sellProduct(ProductTrxnJournal.from(productTrxnJournalDto));
        return new ResponseEntity<>(ProductTrxnJournalDto.from(productTrxnJournal), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductTrxnJournalDto>> getSold() {
        List<ProductTrxnJournal> productTrxnJournals = productTrxnJournalService.getSoldItemList();//store products
        List<ProductTrxnJournalDto> productTrxnJournalDtos = productTrxnJournals.stream().map(ProductTrxnJournalDto::from).collect(Collectors.toList());//convert products to productsDto
        return new ResponseEntity<>(productTrxnJournalDtos, HttpStatus.OK);
    }


}
