package com.example.medsavvy.retrofit.networkmanager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartRetrofilBuilder {
    private  static Retrofit instance;

    private CartRetrofilBuilder(){

    }

    public static  Retrofit getInstance() {
        if (instance == null) {
            synchronized (CartRetrofilBuilder.class) {
                if (instance == null) {
                    instance = new Retrofit.Builder().baseUrl("http://10.177.1.115:8186/")
                            .addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient()).build();
                }
            }
        }
        return instance;
    }
}
