package com.iconiz.tokenunion.ui.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseViewHolder;
import com.iconiz.tokenunion.ui.wallet.model.BillRecord;
import com.iconiz.tokenunion.utils.DatetimeUtils;

import java.util.List;

public class BillRecordAdapter extends RecyclerView.Adapter<BillRecordAdapter.BillViewHolder> {

    private List<BillRecord> mData;
    private LayoutInflater mInflater;

    public BillRecordAdapter(Context context, List<BillRecord> data) {
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_bill_record, parent, false);
        BillViewHolder holder = new BillViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        holder.bindData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class BillViewHolder extends BaseViewHolder<BillRecord> {

        TextView nameView;
        TextView balanceView;
        TextView datetimeView;
        TextView typeView;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.item_bill_record_name);
            balanceView = itemView.findViewById(R.id.item_bill_record_balance);
            datetimeView = itemView.findViewById(R.id.item_bill_record_datetime);
            typeView = itemView.findViewById(R.id.item_bill_record_type);
        }

        @Override
        public void bindData(final BillRecord item, int position) {
            nameView.setText(item.getName());
            datetimeView.setText(DatetimeUtils.timeStamp2Date(item.getDatetime(), "yyyy-MM-dd HH:mm:ss"));
            switch (item.getType()) {
                case BillRecord.TYPE_CHARGE:
                    balanceView.setText("+ " + item.getBalance());
                    balanceView.setSelected(false);
                    typeView.setText(R.string.rush_charge);
                    break;
                case BillRecord.TYPE_WITHDRAW:
                    balanceView.setText("- " + item.getBalance());
                    balanceView.setSelected(true);
                    typeView.setText(R.string.withdraw);
                    break;
                case BillRecord.TYPE_INTEREST:
                    balanceView.setText("+ " + item.getBalance());
                    balanceView.setSelected(false);
                    typeView.setText(R.string.interest);
                    break;
                case BillRecord.TYPE_TRANSFER:
                    balanceView.setText("+ " + item.getBalance());
                    balanceView.setSelected(false);
                    typeView.setText(R.string.transfer);
                    break;
                case BillRecord.TYPE_SUBSIDY:
                    balanceView.setText("+ " + item.getBalance());
                    balanceView.setSelected(false);
                    typeView.setText(R.string.subsidy);
                    break;
            }
        }
    }
}

