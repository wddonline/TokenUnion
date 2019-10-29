package com.tokenunion.pro.ui.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.base.BaseViewHolder;
import com.tokenunion.pro.ui.wallet.model.TradeBalanceBean;

import java.util.List;

public class WalletHomeAdapter extends RecyclerView.Adapter<WalletHomeAdapter.WealthViewHolder> {

    private List<TradeBalanceBean.SymbolBalsBean> mData;
    private LayoutInflater mInflater;

    public WalletHomeAdapter(Context context, List<TradeBalanceBean.SymbolBalsBean> data) {
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WealthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_wealth_home, parent, false);
        WealthViewHolder holder = new WealthViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WealthViewHolder holder, int position) {
        holder.bindData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class WealthViewHolder extends BaseViewHolder<TradeBalanceBean.SymbolBalsBean> {

        TextView[] valueViews;

        public WealthViewHolder(@NonNull View itemView) {
            super(itemView);
            valueViews = new TextView[3];
            valueViews[0] = itemView.findViewById(R.id.item_wealth_home_value0);
            valueViews[1] = itemView.findViewById(R.id.item_wealth_home_value1);
            valueViews[2] = itemView.findViewById(R.id.item_wealth_home_value2);
        }

        @Override
        public void bindData(final TradeBalanceBean.SymbolBalsBean item, int position) {
            valueViews[0].setText(item.getSymbol());
            valueViews[1].setText(item.getAmount());
            valueViews[2].setText(item.getBal()+ " USD");
        }
    }
}
