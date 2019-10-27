package com.tokenunion.pro.ui.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anypocket.pro.R;
import com.tokenunion.pro.ui.base.BaseViewHolder;
import com.tokenunion.pro.ui.wallet.model.AssetBillListBean;

import java.util.List;

public class BillRecordAdapter extends RecyclerView.Adapter<BillRecordAdapter.BillViewHolder> {

    private List<AssetBillListBean.AssetBillsBean> mData;
    private LayoutInflater mInflater;

    public BillRecordAdapter(Context context, List<AssetBillListBean.AssetBillsBean> data) {
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

    class BillViewHolder extends BaseViewHolder<AssetBillListBean.AssetBillsBean> {

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
        public void bindData(final AssetBillListBean.AssetBillsBean item, int position) {
            nameView.setText(item.getSymbol());
            datetimeView.setText(item.getBillTime());
            switch (item.getInOutType()) {
                case "IN":
                    balanceView.setText("+ " + item.getAmount());
                    balanceView.setSelected(false);
                    typeView.setText(item.getBillType());//R.string.rush_charge);
                    break;
                case "OUT":
                    balanceView.setText("- " + item.getAmount());
                    balanceView.setSelected(true);
                    typeView.setText(item.getBillType());//R.string.withdraw);
                    break;

                default:
                    break;
            }
        }
    }
}

