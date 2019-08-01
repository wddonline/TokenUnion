package com.iconiz.tokenunion.ui.capital.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseViewHolder;
import com.iconiz.tokenunion.ui.capital.model.WealthCurrency;

import java.util.List;

public class WealthDetailAdapter extends RecyclerView.Adapter<WealthDetailAdapter.WealthViewHolder> {

    private Context mContext;
    private List<WealthCurrency> mData;
    private LayoutInflater mInflater;

    public WealthDetailAdapter(Context context, List<WealthCurrency> data) {
        this.mContext = context;
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WealthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_wealth_detail, parent, false);
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

    class WealthViewHolder extends BaseViewHolder<WealthCurrency> {

        TextView[] itemViews;

        public WealthViewHolder(@NonNull View itemView) {
            super(itemView);
            itemViews = new TextView[4];
            int id;
            for (int i = 0; i < itemViews.length; i++) {
                id = mContext.getResources().getIdentifier("item_wealth_detail_item" + i, "id", mContext.getPackageName());
                itemViews[i] = itemView.findViewById(id);
            }
        }

        @Override
        public void bindData(WealthCurrency item, int position) {
            itemViews[0].setText(item.getCurrency());
            itemViews[1].setText(item.getAmount());
            itemViews[2].setText(item.getBalance());
            itemViews[3].setText(item.getRatio());
        }
    }
}
