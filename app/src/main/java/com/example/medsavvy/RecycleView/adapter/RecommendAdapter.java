package com.example.medsavvy.RecycleView.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.medsavvy.R;
import com.example.medsavvy.RecycleView.ApiProduct;

import java.net.URI;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder>{
    private final List<ApiProduct> apiResponseList;
    private final IApiResponseClick mUserDataInterface;
    public RecommendAdapter(List<ApiProduct> apiResponseList, IApiResponseClick iApiResponseClick) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface = iApiResponseClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_recommend,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApiProduct apiProduct = apiResponseList.get(position);
        holder.tvName.setText(apiProduct.getName());
        holder.tvPrice.setText(apiProduct.getPrice()+"");

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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final ImageView ivProduct;
        private final TextView tvPrice;
        private final View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            tvName = view.findViewById(R.id.tv_recommend_name);
            ivProduct = view.findViewById(R.id.iv_recommend_img);
            tvPrice = view.findViewById(R.id.tv_recommend_price);
        }
    }
}
