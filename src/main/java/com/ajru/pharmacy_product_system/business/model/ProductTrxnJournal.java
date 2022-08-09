package com.ajru.pharmacy_product_system.business.model;

import com.ajru.pharmacy_product_system.business.model.dto.ProductTrxnJournalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_transaction_journal")
public class ProductTrxnJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "quantity_sold")
    private String quantitySold;

    @CreationTimestamp
    @Column(name = "sold_date")
    private LocalDateTime soldDate;

    @ManyToOne
    private Product product;

    public static ProductTrxnJournal from(ProductTrxnJournalDto productTrxnJournalDto) {
        ProductTrxnJournal productTrxnJournal = new ProductTrxnJournal();

        productTrxnJournal.setQuantitySold(productTrxnJournalDto.getQuantitySold());
        productTrxnJournal.setSoldDate(productTrxnJournalDto.getSoldDate());

        return productTrxnJournal;
    }

}
