package com.example.medsavvy.retrofit.model;

import java.util.List;

public class Orders {

    private String userId;
    private String timeStamp;
    private Long total;
    List<OrderedProducts> products;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<OrderedProducts> getProducts() {
        return products;
    }

    public void setProducts(List<OrderedProducts> products) {
        this.products = products;
    }
}
