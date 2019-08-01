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
import com.iconiz.tokenunion.ui.capital.model.Product;

import java.util.List;

public class CapitalHomeAdapter extends RecyclerView.Adapter<CapitalHomeAdapter.CapitalViewHolder> {

    private List<Product> mData;
    private LayoutInflater mInflater;
    private OnItemClickedListener mOnItemClickedListener;

    public CapitalHomeAdapter(Context context, List<Product> data) {
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

    class CapitalViewHolder extends BaseViewHolder<Product> {

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
        public void bindData(final Product item, int position) {
            currencyView.setText(item.getName());
            hotView.setVisibility(item.isHot() ? View.VISIBLE : View.GONE);
            rateView.setText(item.getRate());
            balanceView.setText(item.getBalance());
            if (item.getSpots() == null) {
                spotViews[0].setVisibility(View.GONE);
                spotViews[1].setVisibility(View.GONE);
            } else {
                for (int i = 0; i < spotViews.length; i++) {
                    if (i < item.getSpots().length) {
                        spotViews[i].setVisibility(View.VISIBLE);
                        spotViews[i].setText(item.getSpots()[i]);
                        spotViews[i].setSelected(item.isHot());
                        if (item.isHot() && i == 0) {
                            spotViews[i].setClickable(true);
                        } else {
                            spotViews[i].setClickable(false);
                        }
                    } else {
                        spotViews[i].setVisibility(View.GONE);
                    }
                }
            }
            clickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickedListener == null) return;
                    mOnItemClickedListener.onItemClicked(item, item.isHot());
                }
            });

        }
    }

    public interface OnItemClickedListener {

        void onItemClicked(Product product, boolean isSuperNode);

    }
}
