package org.example.productcrud.product;

import jakarta.persistence.*;
import lombok.Data;
import org.example.productcrud.category.Category;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double weight;
    private double price;
    private int index;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories;

    public String getCategoryNames() {
        return categories.stream().map(Category::getName).collect(Collectors.joining(", "));
    }
}
