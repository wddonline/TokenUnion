package com.tokenunion.pro.ui.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.base.BaseViewHolder;
import com.tokenunion.pro.ui.login.model.CountryBean;

import java.util.List;

public class CountryChoiceAdapter extends RecyclerView.Adapter<CountryChoiceAdapter.LanViewHolder> {

    private List<CountryBean> mData;
    private LayoutInflater mInflater;
    private OnItemClickedListener mOnItemClickedListener;

    public CountryChoiceAdapter(Context context, List<CountryBean> data) {
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_country_choice, parent, false);
        LanViewHolder viewHolder = new LanViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LanViewHolder holder, int position) {
        holder.bindData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.mOnItemClickedListener = onItemClickedListener;
    }

    class LanViewHolder extends BaseViewHolder<CountryBean> {

        View clickView;
        TextView codeView;
        TextView nameView;

        public LanViewHolder(@NonNull View itemView) {
            super(itemView);
            clickView = itemView.findViewById(R.id.item_country_choice_click);
            codeView = itemView.findViewById(R.id.item_country_choice_code);
            nameView = itemView.findViewById(R.id.item_country_choice_name);
        }

        @Override
        public void bindData(CountryBean item, final int position) {
            codeView.setText(item.getPhoneCode());
            nameView.setText(item.getCountryName());
            clickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickedListener == null) return;
                    mOnItemClickedListener.onItemClicked(position);
                }
            });
        }
    }

    public interface OnItemClickedListener {

        void onItemClicked(int position);

    }

}
