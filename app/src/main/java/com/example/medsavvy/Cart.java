package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medsavvy.RecycleView.adapter.CartAdapter;
import com.example.medsavvy.RecycleView.model.ApiProduct;
import com.example.medsavvy.retrofit.model.ProductDto;
import com.example.medsavvy.retrofit.model.ResponseCartDto;
import com.example.medsavvy.retrofit.model.ResponseCartProductDto;
import com.example.medsavvy.retrofit.network.IPostCartApi;
import com.example.medsavvy.retrofit.network.IPostProductApi;
import com.example.medsavvy.retrofit.networkmanager.CartRetrofilBuilder;
import com.example.medsavvy.retrofit.networkmanager.ProductRetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Cart extends AppCompatActivity implements CartAdapter.IApiResponseClick {
int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
    }

    private void init()
    {
        Retrofit retrofit= CartRetrofilBuilder.getInstance();
        IPostCartApi iPostCartApi=retrofit.create(IPostCartApi.class);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);
        Call<ResponseCartDto> productresponse=iPostCartApi.getCartByEmail(sharedPreferences.getString("em","default"));

        productresponse.enqueue(new Callback<ResponseCartDto>() {
            @Override
            public void onResponse(Call<ResponseCartDto> call, Response<ResponseCartDto> response) {
                List<ResponseCartProductDto> productlist=response.body().getProductList();
                List<ApiProduct> userDataList=new ArrayList<>();

                for(int i=0;i<productlist.size();i++)
                {
                    ApiProduct apiProduct=new ApiProduct();
                    apiProduct.setName(productlist.get(i).getTitle());
                    apiProduct.setImage(productlist.get(i).getImage());
                    apiProduct.setPrice(Double.parseDouble(productlist.get(i).getPrice().toString()));
                    userDataList.add(apiProduct);
                }

                RecyclerView recyclerView=findViewById(R.id.recycle);
                CartAdapter cartAdapter=new CartAdapter(userDataList,Cart.this);
                LinearLayoutManager VerticalLayout= new LinearLayoutManager(Cart.this,LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(VerticalLayout);
                recyclerView.setAdapter(cartAdapter);
            }

            @Override
            public void onFailure(Call<ResponseCartDto> call, Throwable t) {

            }
        });
    }


    @Override
    public void onUserClick(ApiProduct userDatamodel) {
        Toast.makeText(this, "You have purchased this" + userDatamodel, Toast.LENGTH_SHORT).show();

    }
}