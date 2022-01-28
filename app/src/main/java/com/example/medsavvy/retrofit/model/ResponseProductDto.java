package com.example.medsavvy.retrofit.model;

import java.util.List;

public class ResponseProductDto {

    private String name;
    private String description;
    private String imageUrl;
    private List<MerchantProductDetailDto> merchantProductDetailDtos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<MerchantProductDetailDto> getMerchantProductDetailDtos() {
        return merchantProductDetailDtos;
    }

    public void setMerchantProductDetailDtos(List<MerchantProductDetailDto> merchantProductDetailDtos) {
        this.merchantProductDetailDtos = merchantProductDetailDtos;
    }
}
