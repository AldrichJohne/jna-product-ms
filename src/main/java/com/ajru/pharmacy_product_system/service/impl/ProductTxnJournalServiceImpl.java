package com.ajru.pharmacy_product_system.service.impl;

import com.ajru.pharmacy_product_system.model.ProductTrxnJournal;
import com.ajru.pharmacy_product_system.reposiroty.ProductTrxnJournalRepository;
import com.ajru.pharmacy_product_system.service.ProductTxnJournalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductTxnJournalServiceImpl implements ProductTxnJournalService {

    private final ProductTrxnJournalRepository productTrxnJournalRepository;

    public ProductTxnJournalServiceImpl(ProductTrxnJournalRepository productTrxnJournalRepository) {
        this.productTrxnJournalRepository = productTrxnJournalRepository;
    }

    public ProductTrxnJournal sellProduct(ProductTrxnJournal productTrxnJournal) {
        return productTrxnJournalRepository.save(productTrxnJournal);
    }

    public List<ProductTrxnJournal> getSoldItemList() {
        return StreamSupport
                .stream(productTrxnJournalRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
