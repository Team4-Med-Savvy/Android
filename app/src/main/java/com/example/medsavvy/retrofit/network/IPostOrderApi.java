package com.example.medsavvy.retrofit.network;

import com.example.medsavvy.retrofit.model.Orders;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IPostOrderApi {

    @POST("order")
    Call<Void> save(@Body Orders orderDto);
}
