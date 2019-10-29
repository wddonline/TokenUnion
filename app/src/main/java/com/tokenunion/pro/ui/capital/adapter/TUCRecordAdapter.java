package com.tokenunion.pro.ui.capital.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.ui.base.BaseViewHolder;
import com.tokenunion.pro.ui.capital.model.ProfitListBean;
import com.tokenunion.pro.ui.capital.model.TimeOrderListBean;
import com.tokenunion.pro.R;

import java.util.ArrayList;
import java.util.List;

public class TUCRecordAdapter extends RecyclerView.Adapter {

    private final int TYPE_HEADER = 0;
    private final int TYPE_HOLD_HEADER = 1;
    private final int TYPE_HOLD_DATA = 2;
    private final int TYPE_INCOME_DATA = 3;

    private LayoutInflater mInflater;
    private List<RecordItem> mData;
    private OnItemClickedListener mOnItemClickedListener;

    public TUCRecordAdapter(Context context, TimeOrderListBean holds, ProfitListBean bills) {
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
        setData(holds, bills);
    }

    private void setData(TimeOrderListBean holds, ProfitListBean bills) {
        RecordItem item = new RecordItem();
        item.type = TYPE_HEADER;
        mData.add(item);

        item = new RecordItem();
        item.type = TYPE_HOLD_HEADER;
        item.isBill = false;
        mData.add(item);

        int index = 0;
        if (holds != null) {
            for (TimeOrderListBean.TimeOrdersBean record : holds.getTimeOrders()) {
                item = new RecordItem();
                item.type = TYPE_HOLD_DATA;
                item.any = record;
                item.hasDivider = index != 0;
                mData.add(item);
                index++;
            }
        }

        item = new RecordItem();
        item.type = TYPE_HEADER;
        item.isBill = true;
        mData.add(item);

        if (bills != null) {
            index = 0;
            for (ProfitListBean.OrdersBean bill : bills.getOrders()) {
                item = new RecordItem();
                item.type = TYPE_INCOME_DATA;
                item.bill = bill;
                item.hasDivider = index != 0;
                mData.add(item);
                index++;
            }
        }
    }

    public void refreshData(TimeOrderListBean holds, ProfitListBean bills) {
        setData(holds, bills);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view;
        switch (viewType) {
            case TYPE_HEADER:
                view = mInflater.inflate(R.layout.item_any_record_header, parent, false);
                holder = new HeaderViewHolder(view);
                break;
            case TYPE_HOLD_HEADER:
                view = mInflater.inflate(R.layout.item_any_record_hold_header, parent, false);
                holder = new SubHeaderViewHolder(view);
                break;
            case TYPE_HOLD_DATA:
                view = mInflater.inflate(R.layout.item_any_record_hold_data, parent, false);
                holder = new ANYViewHolder(view);
                break;
            case TYPE_INCOME_DATA:
                view = mInflater.inflate(R.layout.item_any_record_income_data, parent, false);
                holder = new BillViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecordItem item = mData.get(position);
        switch (item.type) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bindData(item, position);
                break;
            case TYPE_HOLD_HEADER:
                ((SubHeaderViewHolder) holder).bindData(item, position);
                break;
            case TYPE_HOLD_DATA:
                ((ANYViewHolder) holder).bindData(item, position);
                break;
            case TYPE_INCOME_DATA:
                ((BillViewHolder) holder).bindData(item, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).type;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.mOnItemClickedListener = onItemClickedListener;
    }

    class HeaderViewHolder extends BaseViewHolder<RecordItem> {

        TextView headerView;
        View moreView;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            headerView = itemView.findViewById(R.id.item_any_record_header_title);
            moreView = itemView.findViewById(R.id.item_any_record_header_more);
        }

        @Override
        public void bindData(RecordItem item, int position) {
            if (item.isBill) {
                headerView.setText(R.string.hold_any_income);
                moreView.setVisibility(View.VISIBLE);
                moreView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickedListener == null) return;
                        mOnItemClickedListener.onBillRecordClicked();
                    }
                });
            } else {
                headerView.setText(R.string.hold_any_record);
                moreView.setVisibility(View.GONE);
            }
        }
    }

    class SubHeaderViewHolder extends BaseViewHolder<RecordItem> {

        public SubHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(RecordItem item, int position) {

        }
    }

    class ANYViewHolder extends BaseViewHolder<RecordItem> {

        View dividerView;
        TextView timeView;
        TextView amountView;
        TextView periodView;
        TextView incomeView;

        public ANYViewHolder(@NonNull View itemView) {
            super(itemView);
            dividerView = itemView.findViewById(R.id.item_any_record_hold_data_divider);
            timeView = itemView.findViewById(R.id.item_any_record_hold_data_time);
            amountView = itemView.findViewById(R.id.item_any_record_hold_data_amount);
            periodView = itemView.findViewById(R.id.item_any_record_hold_data_period);
            incomeView = itemView.findViewById(R.id.item_any_record_hold_data_income);
        }

        @Override
        public void bindData(RecordItem item, int position) {
            dividerView.setVisibility(item.hasDivider ? View.VISIBLE : View.GONE);
            timeView.setText(item.any.getOrderTime());
            amountView.setText(item.any.getAmount());
            String text = item.any.getDeadline();
            SpannableString span = new SpannableString(text);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CAAC89"));
            span.setSpan(colorSpan, 0, text.indexOf('/'), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            periodView.setText(span);
            incomeView.setText(item.any.getProfitAmount()); // 预期收益
        }
    }

    class BillViewHolder extends BaseViewHolder<RecordItem> {

        View dividerView;
        TextView timeView;
        TextView valueView;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            dividerView = itemView.findViewById(R.id.item_any_record_income_data_divider);
            timeView = itemView.findViewById(R.id.item_any_record_income_data_time);
            valueView = itemView.findViewById(R.id.item_any_record_income_data_value);
        }

        @Override
        public void bindData(RecordItem item, int position) {
            dividerView.setVisibility(item.hasDivider ? View.VISIBLE : View.GONE);
            timeView.setText(item.bill.getSettleDate());
            valueView.setText("+ " + item.bill.getAmount());
            valueView.setSelected(false);

//            switch (item.bill.getType()) {
//                case BillRecord.TYPE_CHARGE:
//                    valueView.setText("+ " + item.bill.getBalance());
//                    valueView.setSelected(false);
//                    break;
//                case BillRecord.TYPE_WITHDRAW:
//                    valueView.setText("- " + item.bill.getBalance());
//                    valueView.setSelected(true);
//                    break;
//                case BillRecord.TYPE_INTEREST:
//                    valueView.setText("+ " + item.bill.getBalance());
//                    valueView.setSelected(false);
//                    break;
//                case BillRecord.TYPE_TRANSFER:
//                    valueView.setText("+ " + item.bill.getBalance());
//                    valueView.setSelected(false);
//                    break;
//                case BillRecord.TYPE_SUBSIDY:
//                    valueView.setText("+ " + item.bill.getBalance());
//                    valueView.setSelected(false);
//                    break;
//            }
        }
    }

    private class RecordItem {

        int type;
        boolean hasDivider = false;
        boolean isBill;
        TimeOrderListBean.TimeOrdersBean any;
        ProfitListBean.OrdersBean bill;

    }

    public interface OnItemClickedListener {

        void onBillRecordClicked();

    }

}
