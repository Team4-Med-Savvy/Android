package com.example.medsavvy.retrofit.networkmanager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderRetrofitBuilder {
    private  static Retrofit instance;

    private OrderRetrofitBuilder(){

    }

    public static  Retrofit getInstance() {
        if (instance == null) {
            synchronized (OrderRetrofitBuilder.class) {
                if (instance == null) {
                    instance = new Retrofit.Builder().baseUrl("http://10.177.1.115:8187/")
                            .addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient()).build();
                }
            }
        }
        return instance;
    }
}
