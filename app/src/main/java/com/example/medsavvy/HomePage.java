package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.medsavvy.RecycleView.model.ApiProduct;
import com.example.medsavvy.RecycleView.adapter.RecommendAdapter;
import com.example.medsavvy.retrofit.model.ProductDto;
import com.example.medsavvy.retrofit.network.IPostProductApi;
import com.example.medsavvy.retrofit.networkmanager.ProductRetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePage extends AppCompatActivity implements RecommendAdapter.IApiResponseClick{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);
        Boolean userlogged=sharedPreferences.getBoolean("login",false);

        setContentView(R.layout.activity_home_page);
        initApi();

        findViewById(R.id.iv_home_profile).setOnClickListener(v -> {
            Intent i=new Intent(HomePage.this,Login.class);
            startActivity(i);
        });
        findViewById(R.id.iv_home_cart).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(userlogged==false)
                {
                    Toast.makeText(HomePage.this,"Cart Empty.Please Login first",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                startActivity(new Intent(HomePage.this, Cart.class));
            }
        });
        findViewById(R.id.iv_ayur_care).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent intent=new Intent(HomePage.this,Products.class);
                intent.putExtra("category","Ayurvedic care");
                startActivity(intent);               }
        });
        findViewById(R.id.iv_covid).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(HomePage.this,Products.class);
                intent.putExtra("category","Covid essentials");
                startActivity(intent);            }
        });
        findViewById(R.id.iv_surgical).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent intent=new Intent(HomePage.this,Products.class);
                intent.putExtra("category","Surgical");
                startActivity(intent);               }
        });
        findViewById(R.id.iv_skin).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent intent=new Intent(HomePage.this,Products.class);
                intent.putExtra("category","Skin care");
                startActivity(intent);               }
        });
        findViewById(R.id.iv_pet).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent intent=new Intent(HomePage.this,Products.class);
                intent.putExtra("category","Pet care");
                startActivity(intent);               }
        });
        findViewById(R.id.iv_home_bottom).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(HomePage.this, HomePage.class));
            }
        });
        findViewById(R.id.iv_cart_bottom).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(userlogged==false)
                {
                    Toast.makeText(HomePage.this,"Cart Empty.Please Login first",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                startActivity(new Intent(HomePage.this,Cart.class));
            }
        });findViewById(R.id.iv_profile_bottom).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(HomePage.this, Profile.class));
            }
        });


    }

    private void initApi(){
        Retrofit retrofit= ProductRetrofitBuilder.getInstance();

        IPostProductApi iPostProductApi=retrofit.create(IPostProductApi.class);
        Call<List<ProductDto>> productresponse=iPostProductApi.getrecommend();
        productresponse.enqueue(new Callback<List<ProductDto>>() {
            @Override
            public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> responseprod) {
                List<ApiProduct> userDataList=new ArrayList<>();

                for(int i=0;i<responseprod.body().size();i++)
                {
                    String name=responseprod.body().get(i).getTitle();
                    String image=responseprod.body().get(i).getImage();
                    Double price=responseprod.body().get(i).getPrice();
                    String id=responseprod.body().get(i).getId();
                    userDataList.add(new ApiProduct(name,image,price,id));

                }


                RecyclerView recyclerView=findViewById(R.id.recyclehome);
                RecommendAdapter recycleViewAdapter=new RecommendAdapter(userDataList,HomePage.this);
                LinearLayoutManager  HorizontalLayout= new LinearLayoutManager(HomePage.this,LinearLayoutManager.HORIZONTAL,false);
                recyclerView.setLayoutManager(HorizontalLayout);
                recyclerView.setAdapter(recycleViewAdapter);

            }

            @Override
            public void onFailure(Call<List<ProductDto>> call, Throwable t) {
                Log.d("onFailure message", t.getMessage());
                Toast.makeText(HomePage.this,"Error occured",Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onUserClick(ApiProduct userDatamodel) {
    Intent intent=new Intent(HomePage.this,ProductDetail.class);
    intent.putExtra("imageUrl",userDatamodel.getImage());
    intent.putExtra("productName",userDatamodel.getName());
    intent.putExtra("productId",userDatamodel.getId());
        startActivity(intent);
    }
}