package com.example.medsavvy.RecycleView.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.medsavvy.R;
import com.example.medsavvy.RecycleView.model.ApiOrderHist;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolderOrderHist>{
    private final List<ApiOrderHist> apiResponseList;
    int cnt=1;
    private final OrderHistoryAdapter.IApiResponseClick mUserDataInterface;
    public OrderHistoryAdapter(List<ApiOrderHist> apiResponseList, OrderHistoryAdapter.IApiResponseClick iApiResponseClick) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface = iApiResponseClick;
    }


    @NonNull
    @Override
    public ViewHolderOrderHist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_order_history,parent, false);
        return new ViewHolderOrderHist(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOrderHist holder, int position) {
        ApiOrderHist apiorder = apiResponseList.get(position);
         holder.tvorderId.setText("Product "+cnt );
         cnt++;
        holder.tvamount.setText(apiorder.getAmount()+"");
        holder.tvmerchantId.setText(apiorder.getMerchantId());
        holder.tvquantity.setText(apiorder.getQuantity()+"");
        holder.tvproductname.setText(apiorder.getName());
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
        void onUserClick(ApiOrderHist apiOrder);
    }

    public static class ViewHolderOrderHist extends RecyclerView.ViewHolder {
        private final TextView tvorderId;
        private final TextView tvproductname;
        private final TextView tvamount;
        private final TextView tvquantity;
        private final TextView tvmerchantId;
        private final View rootView;

        public ViewHolderOrderHist(View view) {
            super(view);
            rootView = view;
            tvorderId = view.findViewById(R.id.tv_ordhist_oid);
            tvproductname=view.findViewById(R.id.tv_ordhist_prodname);
            tvamount=view.findViewById(R.id.tv_ordhist_amount);
            tvquantity=view.findViewById(R.id.tv_ordhist_quant);
            tvmerchantId=view.findViewById(R.id.tv_ordhist_merchid);

        }
    }
}
