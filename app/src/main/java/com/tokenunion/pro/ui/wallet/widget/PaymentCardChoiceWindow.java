package com.tokenunion.pro.ui.wallet.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.base.BaseViewHolder;
import com.tokenunion.pro.ui.wallet.model.PaymentCard;
import com.tokenunion.pro.widget.LineDividerDecoration;

import java.util.List;

public class PaymentCardChoiceWindow extends PopupWindow {

    private RecyclerView mRecyclerView;

    private OnMenuSelectedListener mOnMenuSelectedListener;

    private int mLastSelectedPosition = -1;

    public PaymentCardChoiceWindow(Context context, List<PaymentCard> cards, int width) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_payment_card_choice, null);
        mRecyclerView = view.findViewById(R.id.popupwindow_payment_card_choice_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        LineDividerDecoration decoration = new LineDividerDecoration(context, LinearLayoutManager.VERTICAL, Color.parseColor("#BBBBBB"));
        mRecyclerView.addItemDecoration(decoration);

        CardAdapter adapter = new CardAdapter(context, cards);
        mRecyclerView.setAdapter(adapter);
        setContentView(view);

        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        setWidth(width);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setOnMenuSelectedListener(OnMenuSelectedListener onMenuSelectedListener) {
        this.mOnMenuSelectedListener = onMenuSelectedListener;
    }

    private class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

        private List<PaymentCard> cards;
        private LayoutInflater inflater;

        public CardAdapter(Context context, List<PaymentCard> cards) {
            this.cards = cards;
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_payment_card_choice, parent, false);
            CardViewHolder viewHolder = new CardViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
            holder.bindData(cards.get(position), position);
        }

        @Override
        public int getItemCount() {
            return cards.size();
        }

        class CardViewHolder extends BaseViewHolder<PaymentCard> {

            TextView nameView;

            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                nameView = itemView.findViewById(R.id.item_payment_card_choice_name);
            }

            @Override
            public void bindData(final PaymentCard item, final int position) {
                nameView.setText(item.getName());
                nameView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if (position == mLastSelectedPosition) {
                            return;
                        }
                        if (mOnMenuSelectedListener == null) return;
                        mOnMenuSelectedListener.onMenuSelected(item);
                    }
                });
            }
        }

    }

    public interface OnMenuSelectedListener {

        void onMenuSelected(PaymentCard card);

    }

}