package com.shoppingcart.productservice.dto;

public class Category {

    private Integer id;
    private String name;
    private String brand;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
