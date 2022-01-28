package com.example.medsavvy.retrofit.network;


import com.example.medsavvy.retrofit.model.ProductDetailDto;
import com.example.medsavvy.retrofit.model.ProductDto;
import com.example.medsavvy.retrofit.model.ResponseProductDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IPostProductApi {
    @GET("product/findlist/{id}")
    Call<List<ProductDto>> getProduct(@Path("id") String id);

    @GET("product/recommend")
    Call<List<ProductDto>> getrecommend();

    @GET("product/productdetail/{pid}/{mid}")
    Call<ProductDetailDto> finddetail(@Path(value = "pid") String pid, @Path(value = "mid") String mid);

    @GET("product/{id}")
    Call<ProductDto> select(@Path(value = "id") String id);

    @GET("product/productdetaillist/{pid}")
    Call<ResponseProductDto> findmerchantlist(@Path(value = "pid") String pid);
//
//    @GET("product/{id}")
//    Call<ProductDto> getProductDetails(@Path("id") String id);
}
