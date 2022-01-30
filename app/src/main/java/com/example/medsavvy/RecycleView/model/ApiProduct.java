package com.example.medsavvy.RecycleView.model;

public class ApiProduct {

    private String id;
    private String name;
    private String image;
    private Double price;

    public ApiProduct(){

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public ApiProduct(String name,String image,Double price,String id)
    {
        this.id=id;
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
