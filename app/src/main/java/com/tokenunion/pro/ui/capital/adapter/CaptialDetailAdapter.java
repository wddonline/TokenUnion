package com.tokenunion.pro.ui.capital.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.base.BaseViewHolder;
import com.tokenunion.pro.ui.capital.model.ProfitListBean;
import com.tokenunion.pro.utils.FormatUtils;

public class CaptialDetailAdapter extends RecyclerView.Adapter<CaptialDetailAdapter.TradeViewHolder> {

    private ProfitListBean mData;
    private LayoutInflater mInflater;

    public CaptialDetailAdapter(Context context, ProfitListBean data) {
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_product_detail, parent, false);
        TradeViewHolder holder = new TradeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TradeViewHolder holder, int position) {
        holder.bindData(mData.getOrders().get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.getOrders().size();
    }

    class TradeViewHolder extends BaseViewHolder<ProfitListBean.OrdersBean> {

        TextView datetimeView;
        TextView valueView;

        public TradeViewHolder(@NonNull View itemView) {
            super(itemView);
            datetimeView = itemView.findViewById(R.id.item_product_detail_date);
            valueView = itemView.findViewById(R.id.item_product_detail_value);
        }

        @Override
        public void bindData(ProfitListBean.OrdersBean item, int position) {

            if(null != item) {
                datetimeView.setText(item.getSettleDate()); // 结算日期
                valueView.setSelected(item.getProfitRate() < 0);
                valueView.setText((item.getProfitRate() > 0 ? "+ " : "- ") +
                        FormatUtils.effectiveNum(item.getAmount() + "", 4)); // 保留4位小数
            }

        }
    }
}
