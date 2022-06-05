package com.ajru.pharmacy_product_system.model;

import com.ajru.pharmacy_product_system.model.dto.ClassificationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "classification")
@Table(name = "class",uniqueConstraints = {@UniqueConstraint(name = "classification_name_unique", columnNames = "name")})
public class Classification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "name",nullable = false, unique = true)
    private String name;

    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "class_id")
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    //ClassificationDto to classification
    public static Classification from(ClassificationDto classificationDto) {
        Classification classification = new Classification();
        classification.setName(classificationDto.getName());
        return classification;
    }
}
