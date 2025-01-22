package com.bootcamp.be_java_hisp_w29_g3.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;


public class Post {
    private Integer id;
    private LocalDate date;
    private Product product;
    private Integer category;
    private Double price;
    private Boolean hasProm;
    private Double discount;

    public Post() {
    }


    public Post(Integer id, LocalDate date, Product product, Integer category, Double price, Boolean hasProm, Double discount) {
        this.id = id;
        this.date = date;
        this.product = product;
        this.category = category;
        this.price = price;
        this.hasProm = hasProm;
        this.discount = discount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getHasProm() {
        return hasProm;
    }

    public void setHasProm(Boolean hasProm) {
        this.hasProm = hasProm;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
