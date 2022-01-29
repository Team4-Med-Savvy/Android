package com.example.medsavvy.retrofit.networkmanager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRetrofitBuilder {

    private  static Retrofit instance;

    private UserRetrofitBuilder(){

    }

    public static  Retrofit getInstance() {
        if (instance == null) {
            synchronized (UserRetrofitBuilder.class) {
                if (instance == null) {
                    instance = new Retrofit.Builder().baseUrl("http://10.177.1.70:8183/")
                            .addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient()).build();
                }
            }
        }
        return instance;
    }
}
