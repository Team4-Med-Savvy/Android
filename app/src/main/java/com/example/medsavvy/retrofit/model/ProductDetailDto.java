package com.example.medsavvy.retrofit.model;

public class ProductDetailDto {
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private long merchantStock;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getMerchantStock() {
        return merchantStock;
    }

    public void setMerchantStock(long merchantStock) {
        this.merchantStock = merchantStock;
    }
}
