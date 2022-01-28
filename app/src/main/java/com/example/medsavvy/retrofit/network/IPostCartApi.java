package com.example.medsavvy.retrofit.network;

import com.example.medsavvy.retrofit.model.CartDto;
import com.example.medsavvy.retrofit.model.RequestCartDto;
import com.example.medsavvy.retrofit.model.ResponseCartDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IPostCartApi {
    @GET(value="cart/email/{email}")
    Call<ResponseCartDto> getCartByEmail(@Path(value="email") String email);

    @POST("cart/{email}/inc")
    Call<Void> addProduct(@Path(value="email") String email, @Body RequestCartDto requestDto);

    @POST("/{email}/{id}/dec")
    Call<Void> deleteProduct(@Path(value="email") String email,@Path(value = "id") String id);

    @POST("cart")
    Call<Void> save(@Body CartDto cartDto);

}
