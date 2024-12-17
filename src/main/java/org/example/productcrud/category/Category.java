package org.example.productcrud.category;

import jakarta.persistence.*;
import lombok.Data;
import org.example.productcrud.product.Product;

import java.util.Set;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products;
}
