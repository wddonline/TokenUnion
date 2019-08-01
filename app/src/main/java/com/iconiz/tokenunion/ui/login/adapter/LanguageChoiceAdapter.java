package com.iconiz.tokenunion.ui.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseViewHolder;

public class LanguageChoiceAdapter extends RecyclerView.Adapter<LanguageChoiceAdapter.LanViewHolder> {

    private String[] mData;
    private LayoutInflater mInflater;
    private OnItemClickedListener mOnItemClickedListener;

    public LanguageChoiceAdapter(Context context, String[] data) {
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_language_choice, parent, false);
        LanViewHolder viewHolder = new LanViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LanViewHolder holder, int position) {
        holder.bindData(mData[position], position);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.mOnItemClickedListener = onItemClickedListener;
    }

    class LanViewHolder extends BaseViewHolder<String> {

        View clickView;
        TextView nameView;

        public LanViewHolder(@NonNull View itemView) {
            super(itemView);
            clickView = itemView.findViewById(R.id.item_language_choice_click);
            nameView = itemView.findViewById(R.id.item_language_choice_name);
        }

        @Override
        public void bindData(String item, final int position) {
            nameView.setText(item);
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
