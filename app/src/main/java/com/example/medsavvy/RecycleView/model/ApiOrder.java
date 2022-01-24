package com.example.medsavvy.RecycleView.model;

import java.util.Date;

public class ApiOrder {
    private String orderid;
    private Double amount;
    private String date;

    public ApiOrder(String orderid,Double amount,String date){
        this.orderid=orderid;
        this.amount=amount;
        this.date=date;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
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
