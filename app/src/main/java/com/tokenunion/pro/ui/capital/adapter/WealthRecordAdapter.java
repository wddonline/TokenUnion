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
import com.tokenunion.pro.ui.capital.model.WealthRecord;
import com.tokenunion.pro.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WealthRecordAdapter extends RecyclerView.Adapter<WealthRecordAdapter.RecordViewHolder> {

    private Context mContext;
    private List<WealthRecord> mData;
    private OnItemClickedListener mOnItemClickedListener;

    public WealthRecordAdapter(Context context, List<WealthRecord> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    public void refreshData(List<WealthRecord> records) {
        mData.clear();
        if (records != null) {
            mData.addAll(records);
        }
        notifyDataSetChanged();
    }

    public void appendData(List<WealthRecord> records) {
        if (records == null) return;
        int start = mData.size();
        mData.addAll(records);
        notifyItemRangeInserted(start, records.size());
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wealth_record, parent, false);
        RecordViewHolder holder = new RecordViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        WealthRecord record = mData.get(position);
        holder.bindData(record, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.mOnItemClickedListener = onItemClickedListener;
    }

    class RecordViewHolder extends BaseViewHolder<WealthRecord> {

        @BindView(R.id.item_wealth_record_click)
        View clickView;
        @BindView(R.id.item_wealth_record_name)
        TextView nameView;
        @BindView(R.id.item_wealth_record_value)
        TextView valueView;
        @BindView(R.id.item_wealth_record_time)
        TextView timeView;
        @BindView(R.id.item_wealth_record_desc)
        TextView descView;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(WealthRecord item, int position) {
            nameView.setText(item.getSymbol());
            if (item.getAmount() > 0) {
                valueView.setText("+ " + StringUtils.getAbsoluteNum(item.getAmount(), 2));
                valueView.setSelected(false);
            } else {
                valueView.setText("- " + StringUtils.getAbsoluteNum(item.getAmount(), 2));
                valueView.setSelected(true);
            }
            timeView.setText(item.getOrderTime());
            descView.setText(item.getStatDesc());
            clickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickedListener == null) return;
                    mOnItemClickedListener.onItemClicked(item, position);
                }
            });
        }
    }

    public interface OnItemClickedListener {

        void onItemClicked(WealthRecord record, int position);
    }

}
