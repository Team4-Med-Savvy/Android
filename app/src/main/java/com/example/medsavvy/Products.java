package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.medsavvy.RecycleView.adapter.RecommendAdapter;
import com.example.medsavvy.RecycleView.model.ApiProduct;
import com.example.medsavvy.retrofit.model.ProductDto;
import com.example.medsavvy.retrofit.network.IPostProductApi;
import com.example.medsavvy.retrofit.networkmanager.ProductRetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Products extends AppCompatActivity implements RecommendAdapter.IApiResponseClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
      //  displayLocalRecyclerView();
        initApi();
    }

    private void initApi(){
        Retrofit retrofit= ProductRetrofitBuilder.getInstance();
        IPostProductApi iPostProductApi=retrofit.create(IPostProductApi.class);
        Intent intent=getIntent();
        String cate= intent.getExtras().getString("category");
        Call<List<ProductDto>> productresponse=iPostProductApi.getProduct(cate);
        productresponse.enqueue(new Callback<List<ProductDto>>() {
            @Override
            public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> responseprod) {
                List<ApiProduct> userDataList=new ArrayList<>();

//                for(int i=0;i<5;i++)
//                {
                    String name=responseprod.body().get(0).getTitle();
                    String image=responseprod.body().get(0).getImage();
                    Double price=responseprod.body().get(0).getPrice();
                    userDataList.add(new ApiProduct(name,image,price));

//                }

                RecyclerView recyclerView=findViewById(R.id.recycles);
                RecommendAdapter recycleViewAdapter=new RecommendAdapter(userDataList,Products.this);
                LinearLayoutManager VerticalLayout= new LinearLayoutManager(Products.this,LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(VerticalLayout);
                recyclerView.setAdapter(recycleViewAdapter);

            }

            @Override
            public void onFailure(Call<List<ProductDto>> call, Throwable t) {
                Log.d("onFailure message", t.getMessage());
              Toast.makeText(Products.this,"Error occured",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  void displayLocalRecyclerView(){
        List<ApiProduct> userDataList=new ArrayList<>();
        generateUserData(userDataList);
        RecyclerView recyclerView=findViewById(R.id.recycles);
        RecommendAdapter recycleViewAdapter=new RecommendAdapter(userDataList,Products.this);
        LinearLayoutManager VerticalLayout= new LinearLayoutManager(Products.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(VerticalLayout);
        recyclerView.setAdapter(recycleViewAdapter);
    }


    private void generateUserData(List<ApiProduct> userDataList) {
        userDataList.add(new ApiProduct("Employee 1", "https://fortmyersradon.com/wp-content/uploads/2019/12/dummy-user-img-1.png", 10.0));
        userDataList.add(new ApiProduct("Employee 2", "https://d2cax41o7ahm5l.cloudfront.net/mi/speaker-photo/appliedmicrobiology-minl-2018-Rucha-Ridhorkar-62007-7135.png", 100.0));
        userDataList.add(new ApiProduct("Employee 3", "https://img.icons8.com/bubbles/2x/user-male.png", 23.98));
        userDataList.add(new ApiProduct("Employee 4", "https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-alt-512.png", 76.9));
        userDataList.add(new ApiProduct("Employee 5", "https://toppng.com/uploads/preview/user-font-awesome-nuevo-usuario-icono-11563566658mjtfvilgcs.png", 7665.0));
        userDataList.add(new ApiProduct("Employee 6", "https://image.flaticon.com/icons/png/512/149/149071.png", 123.56));
        userDataList.add(new ApiProduct("Employee 7", "https://www.winhelponline.com/blog/wp-content/uploads/2017/12/user.png", 141.00));
        userDataList.add(new ApiProduct("Employee 8", "https://avatarfiles.alphacoders.com/791/79102.png", 1656.00));
        userDataList.add(new ApiProduct("Employee 9", "https://i.pinimg.com/originals/7c/c7/a6/7cc7a630624d20f7797cb4c8e93c09c1.png", 181.0));
        userDataList.add(new ApiProduct("Employee 10", "https://cdn1.iconfinder.com/data/icons/avatar-2-2/512/Casual_Man_2-512.png", 817.0));
    }

    @Override
    public void onUserClick(ApiProduct userDatamodel) {
        Intent intent=new Intent(this,ProductDetail.class);
        intent.putExtra("imageUrl",userDatamodel.getImage());
        startActivity(intent);
    }
}