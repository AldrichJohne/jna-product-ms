package com.ajru.pharmacy_product_system.reposiroty;

import com.ajru.pharmacy_product_system.model.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Long> {
}
