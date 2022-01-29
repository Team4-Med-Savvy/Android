package com.example.medsavvy.RecycleView.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.medsavvy.Cart;
import com.example.medsavvy.R;
import com.example.medsavvy.RecycleView.model.ApiCart;
import com.example.medsavvy.retrofit.model.ResponseCartDto;
import com.example.medsavvy.retrofit.model.ResponseCartProductDto;
import com.example.medsavvy.retrofit.network.IPostCartApi;
import com.example.medsavvy.retrofit.networkmanager.CartRetrofilBuilder;
import com.example.medsavvy.retrofit.model.RequestCartDto;

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
   private final IPostCartApi iPostCartApi;
   private final Cart cart;

    public CartAdapter(List<ApiCart> apiResponseList, IApiResponseClick iApiResponseClick,Retrofit retrofit,IPostCartApi iPostCartApi,Cart cart)
    {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface = iApiResponseClick;
        this.retrofit=retrofit;
        this.iPostCartApi=iPostCartApi;
        this.cart=cart;
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


                Retrofit retrofit= CartRetrofilBuilder.getInstance();
                IPostCartApi iPostCartApi=retrofit.create(IPostCartApi.class);
                SharedPreferences sharedPreferences = cart.getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);

            apiProduct.setQuantity(apiProduct.getQuantity()+1);
            holder.display.setText(apiProduct.getQuantity()+"");

            RequestCartDto requestCartDto=new RequestCartDto();
            requestCartDto.setPrice(Math.round(apiProduct.getPrice()));
            requestCartDto.setProductId(apiProduct.getId());
            requestCartDto.setMerchantId(apiProduct.getMerchantId());
            Call<Void> cartresponse=iPostCartApi.addProduct(sharedPreferences.getString("em","default"),requestCartDto);

            cartresponse.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(cart,"quantity added",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(cart,"quanity update failed",Toast.LENGTH_SHORT).show();

                }
            });
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

        System.out.println(apiProduct.getImage()+"ImageHERE");
        Glide.with(holder.ivProduct.getContext()).load(apiProduct.getImage()).into(holder.ivProduct);
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


