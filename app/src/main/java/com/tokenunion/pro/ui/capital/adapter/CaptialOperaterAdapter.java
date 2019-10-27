package com.tokenunion.pro.ui.capital.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anypocket.pro.R;
import com.tokenunion.pro.ui.base.BaseViewHolder;
import com.tokenunion.pro.ui.capital.model.OrdersBean;

import java.util.List;

public class CaptialOperaterAdapter extends RecyclerView.Adapter<CaptialOperaterAdapter.OperaterViewHolder> {

    private List<OrdersBean.OperaterBean> mData;
    private LayoutInflater mInflater;

    public CaptialOperaterAdapter(Context context, List<OrdersBean.OperaterBean> data) {
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OperaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_operator_detail, parent, false);
        OperaterViewHolder holder = new OperaterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OperaterViewHolder holder, int position) {
        holder.bindData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        if(null == mData) {
            return 0;
        }
        return mData.size();
    }

    class OperaterViewHolder extends BaseViewHolder<OrdersBean.OperaterBean> {

        TextView typeView;
        TextView amountView;
        TextView timeView;
        TextView statView;

        public OperaterViewHolder(@NonNull View itemView) {
            super(itemView);
            typeView = itemView.findViewById(R.id.item_operater_type);
            amountView = itemView.findViewById(R.id.item_operater_amount);
            timeView = itemView.findViewById(R.id.item_operater_time);
            statView = itemView.findViewById(R.id.item_operater_stat);
        }

        @Override
        public void bindData(OrdersBean.OperaterBean item, int position) {

            if(null != item) {
                typeView.setText(item.getType());
//                statView.setSelected(item.getAmount() < 0);
                amountView.setText(item.getAmount());
                timeView.setText(item.getOrderTime());
                statView.setText(item.getStatDesc());
                if(item.getStat() == 2){
                    statView.setSelected(true);
                }else{
                    statView.setSelected(false);
                }
            }

        }
    }
}
