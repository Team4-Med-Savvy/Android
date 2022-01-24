package com.example.medsavvy.RecycleView;

public class ApiProduct {
    private String name;
    private String image;
    private Double price;

    public ApiProduct(String name,String image,Double price)
    {
        this.name=name;
        this.image=image;
        this.price=price;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
