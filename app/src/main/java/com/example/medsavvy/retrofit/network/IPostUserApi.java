package com.example.medsavvy.retrofit.network;

import com.example.medsavvy.retrofit.model.AuthDto;
import com.example.medsavvy.retrofit.model.ResponseDto;
import com.example.medsavvy.retrofit.model.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IPostUserApi {
    @POST("user/authenticate")
    Call<ResponseDto> generateToken(@Body AuthDto authDto);

    @POST("user/register")
    Call<Void> save(@Body UserDto userDto);
}
