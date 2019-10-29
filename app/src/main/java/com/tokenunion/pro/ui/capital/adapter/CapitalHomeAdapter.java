package com.tokenunion.pro.ui.capital.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.ui.base.BaseViewHolder;
import com.tokenunion.pro.ui.capital.model.FinanceListBean;
import com.tokenunion.pro.R;

import java.util.List;

public class CapitalHomeAdapter extends RecyclerView.Adapter<CapitalHomeAdapter.CapitalViewHolder> {

    private List<FinanceListBean> mData;
    private LayoutInflater mInflater;
    private OnItemClickedListener mOnItemClickedListener;

    public CapitalHomeAdapter(Context context, List<FinanceListBean> data) {
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CapitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_capital_home, parent, false);
        CapitalViewHolder holder = new CapitalViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CapitalViewHolder holder, int position) {
        holder.bindData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.mOnItemClickedListener = onItemClickedListener;
    }

    class CapitalViewHolder extends BaseViewHolder<FinanceListBean> {

        TextView currencyView;
        View hotView;
        TextView rateView;
        TextView[] spotViews;
        TextView balanceView;
        View clickView;

        public CapitalViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyView = itemView.findViewById(R.id.item_capital_home_currency);
            hotView = itemView.findViewById(R.id.item_capital_home_hot);
            rateView = itemView.findViewById(R.id.item_capital_home_rate);
            spotViews = new TextView[2];
            spotViews[0] = itemView.findViewById(R.id.item_capital_home_spot0);
            spotViews[1] = itemView.findViewById(R.id.item_capital_home_spot1);
            balanceView = itemView.findViewById(R.id.item_capital_home_balance);
            clickView = itemView.findViewById(R.id.item_capital_home_click);
        }

        @Override
        public void bindData(final FinanceListBean item, int position) {
            currencyView.setText(item.getSymbol()); // 币种
            currencyView.setTag(item);
//            hotView.setVisibility(item.isHot() ? View.VISIBLE : View.GONE);
            rateView.setText(item.getAmount()); // 持有金额
            balanceView.setText(item.getBal()+ " USD"); // 持币数量
            if (item.getDigest() == null) {
                spotViews[0].setVisibility(View.GONE);
                spotViews[1].setVisibility(View.GONE);
            } else {
                for (int i = 0; i < spotViews.length; i++) {
                    if (i < item.getDigest().size()) {
                        spotViews[i].setVisibility(View.VISIBLE);
                        spotViews[i].setText(item.getDigest().get(i));
//                        spotViews[i].setSelected(item.isHot());
//                        if (item.isHot() && i == 0) {
//                            spotViews[i].setClickable(true);
//                        } else {
//                            spotViews[i].setClickable(false);
//                        }
                    } else {
                        spotViews[i].setVisibility(View.GONE);
                    }
                }
            }
            clickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickedListener == null) return;
                    mOnItemClickedListener.onItemClicked(item,
                            (null != item.getProducts() && item.getProducts().size()>1)); // 产品数量大于1，则为多产品的item
                }
            });

        }
    }

    public interface OnItemClickedListener {

        void onItemClicked(FinanceListBean financeListBean, boolean isSuperNode);

    }
}
