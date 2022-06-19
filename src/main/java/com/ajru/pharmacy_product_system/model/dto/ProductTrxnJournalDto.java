package com.ajru.pharmacy_product_system.model.dto;

import com.ajru.pharmacy_product_system.model.ProductTrxnJournal;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class ProductTrxnJournalDto {

    private Long id;
    private String quantitySold;
    private LocalDateTime soldDate;
    private PlainProductDto plainProductDto;

    public static ProductTrxnJournalDto from(ProductTrxnJournal productTrxnJournal) {
        ProductTrxnJournalDto productTrxnJournalDto = new ProductTrxnJournalDto();

        productTrxnJournalDto.setId(productTrxnJournal.getId());
        productTrxnJournalDto.setQuantitySold(productTrxnJournal.getQuantitySold());
        productTrxnJournalDto.setSoldDate(productTrxnJournal.getSoldDate());

        if(Objects.nonNull(productTrxnJournal.getProduct())) {
            productTrxnJournalDto.setPlainProductDto(PlainProductDto.from(productTrxnJournal.getProduct()));
        }

        return productTrxnJournalDto;
    }
}
