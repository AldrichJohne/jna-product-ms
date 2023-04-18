package com.ajru.pharmacy_product_system.business.repository;

import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProductSoldRepository extends JpaRepository<ProductSold, Long> {
    List<ProductSold> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);

    List<ProductSold> findByProductId(Long id);
}

