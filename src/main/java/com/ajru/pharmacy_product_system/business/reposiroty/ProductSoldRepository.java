package com.ajru.pharmacy_product_system.business.reposiroty;

import com.ajru.pharmacy_product_system.business.model.entity.ProductSold;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSoldRepository extends JpaRepository<ProductSold, Long> {
}
