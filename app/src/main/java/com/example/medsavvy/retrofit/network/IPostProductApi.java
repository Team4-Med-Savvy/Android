package com.example.medsavvy.retrofit.network;


import com.example.medsavvy.retrofit.model.ProductDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IPostProductApi {
    @GET("product/findlist/{id}")
    Call<List<ProductDto>> getProduct(@Path("id") String id);

    @GET("product/recommend")
    Call<List<ProductDto>> getrecommend();
//
//    @GET("product/{id}")
//    Call<ProductDto> getProductDetails(@Path("id") String id);
}
