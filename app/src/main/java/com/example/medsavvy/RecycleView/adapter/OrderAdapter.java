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
import com.example.medsavvy.RecycleView.model.ApiOrder;
import com.example.medsavvy.RecycleView.model.ApiProduct;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolderOrder>{
    private final List<ApiOrder> apiResponseList;
    private final OrderAdapter.IApiResponseClick mUserDataInterface;
    public OrderAdapter(List<ApiOrder> apiResponseList, OrderAdapter.IApiResponseClick iApiResponseClick) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface = iApiResponseClick;
    }


    @NonNull
    @Override
    public ViewHolderOrder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_order,parent, false);
        return new ViewHolderOrder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOrder holder, int position) {
        ApiOrder apiorder = apiResponseList.get(position);
        holder.tvorderId.setText(apiorder.getOrderid()+"");
        holder.tvPrice.setText(apiorder.getAmount()+"");
        holder.tvdatePurchased.setText(apiorder.getDate());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDataInterface.onUserClick(apiorder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiResponseList.size();
    }

    public interface IApiResponseClick {
        void onUserClick(ApiOrder apiOrder);
    }

    public static class ViewHolderOrder extends RecyclerView.ViewHolder {
        private final TextView tvorderId;
        private final TextView tvdatePurchased;
        private final TextView tvPrice;
        private final View rootView;

        public ViewHolderOrder(View view) {
            super(view);
            rootView = view;
            tvorderId = view.findViewById(R.id.tv_orderID);
            tvdatePurchased = view.findViewById(R.id.tv_date);
            tvPrice = view.findViewById(R.id.tv_amount);
        }
    }
}
