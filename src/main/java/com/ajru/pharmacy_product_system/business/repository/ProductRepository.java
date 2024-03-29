package com.ajru.pharmacy_product_system.business.repository;

import com.ajru.pharmacy_product_system.business.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Transactional
    @Query(value = "SELECT * FROM Product p WHERE p.classification_id like :id%", nativeQuery = true)
    List<Product> findProductByClass(@Param("id") long id);

}
