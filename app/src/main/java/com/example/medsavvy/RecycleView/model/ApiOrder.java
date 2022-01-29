package com.example.medsavvy.RecycleView.model;

import java.util.Date;

public class ApiOrder {
    private Long orderid;
    private Double amount;
    private String date;

    public ApiOrder(){

    }
    public ApiOrder(Long orderid,Double amount,String date){
        this.orderid=orderid;
        this.amount=amount;
        this.date=date;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
