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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.medsavvy.R;
import com.example.medsavvy.RecycleView.model.ApiProduct;
import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolderCart> {
    int count=0;
    private final List<ApiProduct> apiResponseList;
    private final IApiResponseClick mUserDataInterface;
    public CartAdapter(List<ApiProduct> apiResponseList, IApiResponseClick iApiResponseClick) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface = iApiResponseClick;
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
        ApiProduct apiProduct = apiResponseList.get(position);
        System.out.println("Name "+apiProduct.getName());
        holder.tvName.setText(apiProduct.getName());
        holder.tvPrice.setText(apiProduct.getPrice()+"");
        System.out.println(holder.tvName);
        holder.increment.setOnClickListener(v -> {
            count++;
            holder.display.setText(""+count);
        });

        holder.decrement.setOnClickListener(v -> {
            count--;
            if(count<0)count=0;
            holder.display.setText(""+count);
        });

        Glide.with(holder.ivProduct.getContext()).load(apiProduct.getImage()).placeholder(R.drawable.ic_login).into(holder.ivProduct);
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
        void onUserClick(ApiProduct apiproduct);
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


