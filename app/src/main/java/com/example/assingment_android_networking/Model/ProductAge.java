package com.example.assingment_android_networking.Model;

import java.util.List;

public class ProductAge  {

    private List<Product> products;

    private String message;

    private int id_product;

    public ProductAge() {
    }

    public ProductAge(List<Product> products, String message, int id_product) {
        this.products = products;
        this.message = message;
        this.id_product = id_product;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }
}
