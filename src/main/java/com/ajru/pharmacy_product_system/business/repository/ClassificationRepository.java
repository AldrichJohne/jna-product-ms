package com.ajru.pharmacy_product_system.business.repository;

import com.ajru.pharmacy_product_system.business.model.entity.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Long> {
}
