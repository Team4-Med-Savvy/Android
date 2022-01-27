package com.example.medsavvy.retrofit.model;

import java.util.List;

public class ResponseCartDto {
    private String id;
    private String email;
    private List<ResponseCartProductDto> productList;

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

    public List<ResponseCartProductDto> getProductList() {
        return productList;
    }

    public void setProductList(List<ResponseCartProductDto> productList) {
        this.productList = productList;
    }
}
