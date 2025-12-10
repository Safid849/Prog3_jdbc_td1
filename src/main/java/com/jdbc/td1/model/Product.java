package com.jdbc.td1.model;

import java.time.Instant;

public class Product {
    private Integer id;
    private String name;
    private Double price;
    private Instant creationDateTime;
    private Category category;

    public Product() {}

    public Product(Integer id, String name, Double price, Instant creationDateTime, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.creationDateTime = creationDateTime;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Méthode demandée
    public String getCategoryName() {
        return category != null ? category.getName() : null;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", creationDateTime=" + creationDateTime +
                ", category=" + (category != null ? category.getName() : "aucune") +
                '}';
    }
}