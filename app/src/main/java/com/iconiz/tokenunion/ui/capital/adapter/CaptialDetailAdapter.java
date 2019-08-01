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
import com.iconiz.tokenunion.ui.capital.model.TradeRecord;
import com.iconiz.tokenunion.utils.DatetimeUtils;

import java.util.List;

public class CaptialDetailAdapter extends RecyclerView.Adapter<CaptialDetailAdapter.TradeViewHolder> {

    private List<TradeRecord> mData;
    private LayoutInflater mInflater;

    public CaptialDetailAdapter(Context context, List<TradeRecord> data) {
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
        holder.bindData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TradeViewHolder extends BaseViewHolder<TradeRecord> {

        TextView datetimeView;
        TextView valueView;

        public TradeViewHolder(@NonNull View itemView) {
            super(itemView);
            datetimeView = itemView.findViewById(R.id.item_product_detail_date);
            valueView = itemView.findViewById(R.id.item_product_detail_value);
        }

        @Override
        public void bindData(TradeRecord item, int position) {
            datetimeView.setText(DatetimeUtils.timeStamp2Date(item.getDatetime(), "yyyy-MM-dd HH:mm:ss"));
            valueView.setSelected(item.getValue() < 0);
            valueView.setText((item.getValue() > 0 ? "+ " : "- ") + item.getValue());

        }
    }
}
