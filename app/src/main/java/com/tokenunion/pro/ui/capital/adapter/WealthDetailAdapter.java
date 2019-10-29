package com.tokenunion.pro.ui.capital.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.ui.base.BaseViewHolder;
import com.tokenunion.pro.ui.capital.model.ListRatioBean;
import com.tokenunion.pro.R;

import java.util.List;

public class WealthDetailAdapter extends RecyclerView.Adapter<WealthDetailAdapter.WealthViewHolder> {

    private Context mContext;
    private List<ListRatioBean.FinanceAssetsBean> mData;
    private LayoutInflater mInflater;

    public WealthDetailAdapter(Context context, List<ListRatioBean.FinanceAssetsBean> data) {
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
        if(null == mData){
            return 0;
        }
        return mData.size();
    }

    class WealthViewHolder extends BaseViewHolder<ListRatioBean.FinanceAssetsBean> {

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
        public void bindData(ListRatioBean.FinanceAssetsBean item, int position) {
            itemViews[0].setText(item.getSymbol());
            itemViews[1].setText(item.getAmount());
            itemViews[2].setText(item.getBal()+ " USD");
            itemViews[3].setText(item.getRatio() +"%");
        }
    }
}
