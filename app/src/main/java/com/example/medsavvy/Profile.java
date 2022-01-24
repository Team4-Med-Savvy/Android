package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.medsavvy.RecycleView.adapter.OrderAdapter;
import com.example.medsavvy.RecycleView.adapter.RecommendAdapter;
import com.example.medsavvy.RecycleView.model.ApiOrder;
import com.example.medsavvy.RecycleView.model.ApiProduct;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity implements OrderAdapter.IApiResponseClick{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        displayLocalRecyclerView();

        findViewById(R.id.iv_profile_login).setOnClickListener(v -> {
            Intent i=new Intent(Profile.this,Login.class);
            startActivity(i);
        });
    }


    private  void displayLocalRecyclerView(){
        List<ApiOrder> userDataList=new ArrayList<>();
        generateUserData(userDataList);
        RecyclerView recyclerView=findViewById(R.id.recycleList);
        OrderAdapter recycleViewAdapter=new OrderAdapter(userDataList,Profile.this);
        LinearLayoutManager HorizontalLayout= new LinearLayoutManager(Profile.this,LinearLayoutManager.HORIZONTAL,false);


        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(recycleViewAdapter);


    }


    private void generateUserData(List<ApiOrder> userDataList) {
        userDataList.add(new ApiOrder("Order 1", 10.0 ,"12/12/1999"));
        userDataList.add(new ApiOrder("Order 2", 10.0 ,"12/12/1999"));
        userDataList.add(new ApiOrder("Order 3", 10.0 ,"12/12/1999"));
        userDataList.add(new ApiOrder("Order 4", 10.0 ,"12/12/1999"));
        userDataList.add(new ApiOrder("Order 5", 10.0 ,"12/12/1999"));

    }

    @Override
    public void onUserClick(ApiOrder userDatamodel) {
//        Intent intent=new Intent(Profile.this,ProductDetail.class);
//        startActivity(intent);
    }
}