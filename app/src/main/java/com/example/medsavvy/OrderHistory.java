package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.medsavvy.RecycleView.adapter.OrderAdapter;
import com.example.medsavvy.RecycleView.adapter.OrderHistoryAdapter;
import com.example.medsavvy.RecycleView.adapter.RecommendAdapter;
import com.example.medsavvy.RecycleView.model.ApiOrderHist;
import com.example.medsavvy.RecycleView.model.ApiProduct;
import com.example.medsavvy.retrofit.model.OrderedProducts;
import com.example.medsavvy.retrofit.model.ResponseOrderDto;
import com.example.medsavvy.retrofit.network.IPostOrderApi;
import com.example.medsavvy.retrofit.networkmanager.OrderRetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderHistory extends AppCompatActivity implements OrderHistoryAdapter.IApiResponseClick {
    TextView tvorderId,tvproductId,tvamount,tvquantity,tvmerchantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
//        displayLocalRecyclerView();
        initorder();
    }

    private void initorder(){
        Retrofit retrofit= OrderRetrofitBuilder.getInstance();
        IPostOrderApi iPostOrderApi=retrofit.create(IPostOrderApi.class);
        Intent intent=getIntent();
        Long oid=intent.getExtras().getLong("orderid",0);
        Call<List<ResponseOrderDto>> orderproduct=iPostOrderApi.findAnOrder(oid);

        orderproduct.enqueue(new Callback<List<ResponseOrderDto>>() {
            @Override
            public void onResponse(Call<List<ResponseOrderDto>> call, Response<List<ResponseOrderDto>> response) {
                List<ApiOrderHist> orderHists=new ArrayList<>();
                List<ResponseOrderDto> responseOrderDtos=response.body();
                System.out.println(response.body().get(0).getAmount());;

                for(int i=0;i<responseOrderDtos.size();i++)
                {
                    ApiOrderHist apiOrderHist=new ApiOrderHist();
                    apiOrderHist.setName(responseOrderDtos.get(i).getName());
                    apiOrderHist.setImageUrl(responseOrderDtos.get(i).getImageUrl());
                    apiOrderHist.setAmount(responseOrderDtos.get(i).getAmount());
                    apiOrderHist.setQuantity(responseOrderDtos.get(i).getQuantity());
                    apiOrderHist.setMerchantId(responseOrderDtos.get(i).getMerchantId());
                    apiOrderHist.setProductId(responseOrderDtos.get(i).getProductId());

                    orderHists.add(apiOrderHist);
                }
                RecyclerView recyclerView=findViewById(R.id.orderhistory_recycle);
                OrderHistoryAdapter recycleViewAdapter=new OrderHistoryAdapter(orderHists,OrderHistory.this);
                LinearLayoutManager HorizontalLayout= new LinearLayoutManager(OrderHistory.this,LinearLayoutManager.VERTICAL,false);

                recyclerView.setLayoutManager(HorizontalLayout);
                recyclerView.setAdapter(recycleViewAdapter);
            }

            @Override
            public void onFailure(Call<List<ResponseOrderDto>> call, Throwable t) {

            }
        });
    }
    private  void displayLocalRecyclerView(){
//        List<ApiProduct> userDataList=new ArrayList<>();
//        generateUserData(userDataList);
//        RecyclerView recyclerView=findViewById(R.id.orderhistory_recycle);
//       // RecommendAdapter recycleViewAdapter=new RecommendAdapter(userDataList,OrderHistory.this);
//        LinearLayoutManager HorizontalLayout= new LinearLayoutManager(OrderHistory.this,LinearLayoutManager.VERTICAL,false);
//
//        recyclerView.setLayoutManager(HorizontalLayout);
//        recyclerView.setAdapter(recycleViewAdapter);

    }
    private void generateUserData(List<ApiProduct> userDataList) {
//        userDataList.add(new ApiProduct("Employee 1", "https://fortmyersradon.com/wp-content/uploads/2019/12/dummy-user-img-1.png", 10.0));
//        userDataList.add(new ApiProduct("Employee 2", "https://d2cax41o7ahm5l.cloudfront.net/mi/speaker-photo/appliedmicrobiology-minl-2018-Rucha-Ridhorkar-62007-7135.png", 100.0));
//        userDataList.add(new ApiProduct("Employee 3", "https://img.icons8.com/bubbles/2x/user-male.png", 23.98));
//        userDataList.add(new ApiProduct("Employee 4", "https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-alt-512.png", 76.9));
//        userDataList.add(new ApiProduct("Employee 5", "https://toppng.com/uploads/preview/user-font-awesome-nuevo-usuario-icono-11563566658mjtfvilgcs.png", 7665.0));
//        userDataList.add(new ApiProduct("Employee 6", "https://image.flaticon.com/icons/png/512/149/149071.png", 123.56));
//        userDataList.add(new ApiProduct("Employee 7", "https://www.winhelponline.com/blog/wp-content/uploads/2017/12/user.png", 141.00));
//        userDataList.add(new ApiProduct("Employee 8", "https://avatarfiles.alphacoders.com/791/79102.png", 1656.00));
//        userDataList.add(new ApiProduct("Employee 9", "https://i.pinimg.com/originals/7c/c7/a6/7cc7a630624d20f7797cb4c8e93c09c1.png", 181.0));
//        userDataList.add(new ApiProduct("Employee 10", "https://cdn1.iconfinder.com/data/icons/avatar-2-2/512/Casual_Man_2-512.png", 817.0));
    }

    @Override
    public void onUserClick(ApiOrderHist userDatamodel) {
//        Intent intent=new Intent(OrderHistory.this,ProductDetail.class);
//        intent.putExtra("imageUrl",userDatamodel.getImageUrl());
//        startActivity(intent);
    }
}