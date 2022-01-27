package com.example.medsavvy.retrofit.networkmanager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductRetrofitBuilder {
    private  static Retrofit instance;

    private ProductRetrofitBuilder(){

    }

    public static  Retrofit getInstance() {
        if (instance == null) {
            synchronized (ProductRetrofitBuilder.class) {
                if (instance == null) {
                    instance = new Retrofit.Builder().baseUrl("http://10.177.1.115:8184/")
                            .addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient()).build();
                }
            }
        }
        return instance;
    }
}
