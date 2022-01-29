package com.example.medsavvy.RecycleView.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.medsavvy.Cart;
import com.example.medsavvy.R;
import com.example.medsavvy.RecycleView.model.ApiCart;
import com.example.medsavvy.RecycleView.model.ApiProduct;
import com.example.medsavvy.retrofit.model.ResponseCartDto;
import com.example.medsavvy.retrofit.model.ResponseCartProductDto;
import com.example.medsavvy.retrofit.network.IPostCartApi;
import com.example.medsavvy.retrofit.networkmanager.CartRetrofilBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolderCart> {
    int count;

    private final List<ApiCart> apiResponseList;
    private final IApiResponseClick mUserDataInterface;
    private final Retrofit retrofit;
    private final Cart cart;
    public CartAdapter(List<ApiCart> apiResponseList, IApiResponseClick iApiResponseClick, Retrofit retrofit, Cart cart) {
        this.cart = cart;
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface = iApiResponseClick;
        this.retrofit=retrofit;
    }

    @NonNull
    @Override
    public ViewHolderCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_cart,parent, false);
        return new ViewHolderCart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCart holder, int position) {
        ApiCart apiProduct = apiResponseList.get(position);
        System.out.println("Name "+apiProduct.getName());
        holder.tvName.setText(apiProduct.getName());
        holder.tvPrice.setText(apiProduct.getPrice()+"");
        holder.display.setText(apiProduct.getQuantity().toString());
        count=Math.round(apiProduct.getQuantity());
        System.out.println(holder.tvName);
        holder.increment.setOnClickListener(v -> {
            count++;
//            SharedPreferences sharedPreferences = cart.getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putInt("count",count);
//            editor.apply();



                Retrofit retrofit= CartRetrofilBuilder.getInstance();
                IPostCartApi iPostCartApi=retrofit.create(IPostCartApi.class);
                SharedPreferences sharedPreferences = cart.getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);

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
                            apiProduct.setQuantity(Long.valueOf(count));
                            userDataList.add(apiProduct);
                        }

//                        RecyclerView recyclerView=findViewById(R.id.recycle);
//                        CartAdapter cartAdapter=new CartAdapter(userDataList,Cart.this,retrofit,Cart.this);
//                        LinearLayoutManager VerticalLayout= new LinearLayoutManager(Cart.this,LinearLayoutManager.VERTICAL,false);
//                        recyclerView.setLayoutManager(VerticalLayout);
//                        recyclerView.setAdapter(cartAdapter);

                    }

                    @Override
                    public void onFailure(Call<ResponseCartDto> call, Throwable t) {

                    }

                });


            holder.display.setText(""+count);
        });

        holder.decrement.setOnClickListener(v -> {
            count--;
            if(count<0)count=0;
            holder.display.setText(""+count);
//
//            SharedPreferences sharedPreferences = cart.getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putInt("count",count);
//            editor.apply();
        });

        Glide.with(holder.ivProduct.getContext()).load("https://rukminim1.flixcart.com/image/416/416/j8osu4w0/chyawanprash/u/g/z/1-chyawanprash-patanjali-original-imaeymvf8tzsbnpz.jpeg?q=70").into(holder.ivProduct);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDataInterface.onUserClick(apiProduct);
            }
        });
    }



    @Override
    public int getItemCount() {
        return apiResponseList.size();
    }

    public interface IApiResponseClick {
        void onUserClick(ApiCart apiproduct);
    }

    public static class ViewHolderCart extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final ImageView ivProduct;
        private final TextView tvPrice;
        private final View rootView;
       private final Button increment;
       private final Button decrement;
       private final TextView display;

       public ViewHolderCart(View view) {
            super(view);
            rootView = view;
            tvName = view.findViewById(R.id.tv_cart_name);
            ivProduct = view.findViewById(R.id.iv_cart_img);
            tvPrice = view.findViewById(R.id.tv_cart_price);
            increment=view.findViewById(R.id.bn_increment);
            decrement=view.findViewById(R.id.bn_decrement);
            display=view.findViewById(R.id.tv_quant);
        }
    }



}


