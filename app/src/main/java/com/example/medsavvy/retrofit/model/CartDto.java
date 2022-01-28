package com.example.medsavvy.retrofit.model;

import java.util.List;

public class CartDto {
    private String id;
    private String email;
    private List<CartProduct> productList;

    public CartDto(String email, List<CartProduct> productList) {
        this.email = email;
        this.productList = productList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CartProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<CartProduct> productList) {
        this.productList = productList;
    }
}
