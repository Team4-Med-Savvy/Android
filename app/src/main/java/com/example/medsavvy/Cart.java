package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medsavvy.RecycleView.adapter.CartAdapter;
import com.example.medsavvy.RecycleView.model.ApiCart;
import com.example.medsavvy.RecycleView.model.ApiProduct;
import com.example.medsavvy.retrofit.model.OrderedProducts;
import com.example.medsavvy.retrofit.model.Orders;
import com.example.medsavvy.retrofit.model.ResponseCartDto;
import com.example.medsavvy.retrofit.model.ResponseCartProductDto;
import com.example.medsavvy.retrofit.network.IPostCartApi;
import com.example.medsavvy.retrofit.network.IPostOrderApi;
import com.example.medsavvy.retrofit.networkmanager.CartRetrofilBuilder;
import com.example.medsavvy.retrofit.networkmanager.OrderRetrofitBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Cart extends AppCompatActivity implements CartAdapter.IApiResponseClick {
int count=0;
    Double total_price=Double.parseDouble("0");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
//        List<ResponseCartProductDto> productlist=new ArrayList<>();

        //displayLocalRecyclerView();
        init();
        findViewById(R.id.cart_proceed).setOnClickListener(v -> {
            initorder();
        });
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
                List<ResponseCartProductDto> productlist=new ArrayList<>();
                productlist=response.body().getProductList();
                List<ApiCart> userDataList=new ArrayList<>();

                for(int i=0;i<productlist.size();i++)
                {
                    ApiCart apiProduct=new ApiCart();
                    apiProduct.setName(productlist.get(i).getTitle());
                    apiProduct.setImage(productlist.get(i).getImage());
                    apiProduct.setPrice(Double.parseDouble(productlist.get(i).getPrice().toString()));
                    apiProduct.setQuantity(Long.valueOf(productlist.get(i).getQuantity()));
                    total_price=total_price+Double.parseDouble(productlist.get(i).getPrice().toString());
                    userDataList.add(apiProduct);
                }

                RecyclerView recyclerView=findViewById(R.id.recycle);
                CartAdapter cartAdapter=new CartAdapter(userDataList,Cart.this,retrofit,Cart.this);
                LinearLayoutManager VerticalLayout= new LinearLayoutManager(Cart.this,LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(VerticalLayout);
                recyclerView.setAdapter(cartAdapter);

            }

            @Override
            public void onFailure(Call<ResponseCartDto> call, Throwable t) {

            }

        });
    }
//    public Orders setorder(List<ResponseCartProductDto> productlist)
//    {
//     Orders orders=new Orders();
//        SharedPreferences sharedPreferences = getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);
//        orders.setUserId(sharedPreferences.getString("userId",""));
//    orders.setTotal(Math.round(total_price));
//     orders.setTimeStamp(Calendar.getInstance().getTime().toString());
//     List<OrderedProducts> orderedProductlist=new ArrayList<>();
//
//     for(int i=0;i<productlist.size();i++){
//         OrderedProducts orderedProducts=new OrderedProducts();
//         orderedProducts.setMerchantId(productlist.get(i).getMerchantId());
//         Long quant=Long.valueOf(productlist.get(i).getQuantity());
//         orderedProducts.setQuantity(quant);
//         orderedProducts.setAmount(productlist.get(i).getPrice()*quant);
//         orderedProducts.setProductId(productlist.get(i).getProductId());
//
//         orderedProductlist.add(orderedProducts);
//     }
//        orders.setProducts(orderedProductlist);
//
//        return  orders;
//    }
    private void initorder(){
        Retrofit retrofit= OrderRetrofitBuilder.getInstance();
        IPostOrderApi iPostOrderApi=retrofit.create(IPostOrderApi.class);

        //making order
        Orders orders=new Orders();
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);
        orders.setUserId(sharedPreferences.getString("userId",""));
        orders.setTotal(Math.round(total_price));
        orders.setTimeStamp(Calendar.getInstance().getTime().toString());


        //making product list
        Retrofit retrofit1= CartRetrofilBuilder.getInstance();
        IPostCartApi iPostCartApi=retrofit1.create(IPostCartApi.class);
        Call<ResponseCartDto> productresponse=iPostCartApi.getCartByEmail(sharedPreferences.getString("em","default"));

        productresponse.enqueue(new Callback<ResponseCartDto>() {
            @Override
            public void onResponse(Call<ResponseCartDto> call, Response<ResponseCartDto> response) {
                List<ResponseCartProductDto> productlist = response.body().getProductList();
                List<OrderedProducts> orderedProductlist=new ArrayList<>();

                for(int i=0;i<productlist.size();i++){
                    OrderedProducts orderedProducts=new OrderedProducts();
                    orderedProducts.setMerchantId(productlist.get(i).getMerchantId());
                    Long quant=Long.valueOf(productlist.get(i).getQuantity());
//                    int cnt=sharedPreferences.getInt("count",0);
//                    System.out.println("COUNT:"+cnt);
//                    if(cnt>quant)
//                        quant=Long.parseLong(cnt+ "");
                    orderedProducts.setQuantity(quant);
                    orderedProducts.setAmount(productlist.get(i).getPrice()*quant);
                    orderedProducts.setProductId(productlist.get(i).getProductId());

                    orderedProductlist.add(orderedProducts);
                }
                orders.setProducts(orderedProductlist);

                Call<Void> order=iPostOrderApi.save(orders);

                order.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(Cart.this,"sucess",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(Cart.this,"Fail",Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseCartDto> call, Throwable t) {
                Toast.makeText(Cart.this,"Fail Miserably",Toast.LENGTH_SHORT).show();

            }
        });





    }
    private  void displayLocalRecyclerView(){
//        List<ApiProduct> userDataList=new ArrayList<>();
//        generateUserData(userDataList);
//        RecyclerView recyclerView=findViewById(R.id.recycle);
//        CartAdapter cartAdapter=new CartAdapter(userDataList,Cart.this);
//        LinearLayoutManager VerticalLayout= new LinearLayoutManager(Cart.this,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(VerticalLayout);
//        recyclerView.setAdapter(cartAdapter);
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
    public void onUserClick(ApiCart userDatamodel) {
        Toast.makeText(this, "You have purchased this" + userDatamodel, Toast.LENGTH_SHORT).show();

    }
}