package com.example.medsavvy.retrofit.network;

import com.example.medsavvy.retrofit.model.OrderedProducts;
import com.example.medsavvy.retrofit.model.Orders;
import com.example.medsavvy.retrofit.model.OrdersDto;
import com.example.medsavvy.retrofit.model.ResponseOrderDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IPostOrderApi {

    @POST("order")
    Call<Void> save(@Body Orders orderDto);

    @GET("order/user/{id}")
    Call<List<OrdersDto>> findUserOrders(@Path("id") String id);


    @GET("orderedproducts/{id}")
    Call<List<ResponseOrderDto>> findAnOrder(@Path("id") Long id);
}
