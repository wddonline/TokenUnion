package com.iconiz.tokenunion.ui.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.mine.model.Message;
import com.iconiz.tokenunion.utils.DatetimeUtils;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> mData;
    private LayoutInflater mInflater;
    private OnItemClickedListener mOnItemClickedListener;

    private int mMargin;

    public MessageAdapter(Context context, List<Message> data) {
        this.mData = data;
        mInflater = LayoutInflater.from(context);
        mMargin = DensityUtils.dip2px(context, 12);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_message, parent, false);
        MessageViewHolder viewHolder = new MessageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.bindData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.mOnItemClickedListener = onItemClickedListener;
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        TextView titleView;
        TextView dateView;
        TextView contentView;
        TextView moneyView;
        TextView clickView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.item_message_root);
            titleView = itemView.findViewById(R.id.item_message_title);
            dateView = itemView.findViewById(R.id.item_message_date);
            contentView = itemView.findViewById(R.id.item_message_content);
            moneyView = itemView.findViewById(R.id.item_message_money);
            clickView = itemView.findViewById(R.id.item_message_click);
        }

        public void bindData(final Message msg, int position) {
            ((LinearLayout.LayoutParams)rootView.getLayoutParams()).topMargin = position == 0 ? mMargin : 0;
            titleView.setText(msg.getTitle());
            dateView.setText(DatetimeUtils.getFuzzyTime(msg.getCreateTime()));
            contentView.setText(msg.getContent());
            switch (msg.getType()) {
                case Message.TYPE_MONEY:
                    clickView.setText(R.string.view_money_record);
                    moneyView.setVisibility(View.VISIBLE);
                    String str = (msg.getMoney() > 0 ? "+ " : "- ") + msg.getMoney() + " " + msg.getCurrency();
                    SpannableString span = new SpannableString(str);
                    int color = msg.getMoney() > 0 ? Color.parseColor("#009688") : Color.parseColor("#E51C23");
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
                    span.setSpan(colorSpan, 0, str.lastIndexOf(' '), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    moneyView.setText(span);
                    break;
                case Message.TYPE_INVITE:
                    clickView.setText(R.string.invite_friend);
                    moneyView.setVisibility(View.GONE);
                    break;
                case Message.TYPE_UPGRADE:
                    clickView.setText(R.string.customer_center);
                    moneyView.setVisibility(View.GONE);
                    break;
            }
            clickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickedListener.onItemClicked(msg);
                }
            });
        }
    }

    public interface OnItemClickedListener {

        void onItemClicked(Message msg);

    }

}
