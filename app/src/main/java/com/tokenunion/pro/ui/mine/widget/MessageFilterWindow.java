package com.tokenunion.pro.ui.mine.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.anypocket.pro.R;

public class MessageFilterWindow extends PopupWindow {

    public static final int TYPE_ALL = 0;
    public static final int TYPE_BILL_RECORD = 1;
    public static final int TYPE_INVITE_RECORD = 2;
    public static final int TYPE_MEM_LEVEL = 3;
    public static final int TYPE_ACCOUNT_SAFETY = 4;
    public static final int TYPE_NOTICE = 5;

    private TextView[] itemViews;

    private OnMenuSelectedListener mOnMenuSelectedListener;

    private int mLastSelectedPosition = 0;

    public MessageFilterWindow(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_msg_filter, null);
        View closeView = view.findViewById(R.id.popupwindow_msg_filter_close);
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        String[] items = context.getResources().getStringArray(R.array.filter_menu);
        itemViews = new TextView[items.length];
        int id;
        for (int i = 0; i < items.length; i++) {
            id = context.getResources().getIdentifier("popupwindow_msg_filter_item" + i, "id", context.getPackageName());
            itemViews[i] = view.findViewById(id);
            itemViews[i].setText(items[i]);
            itemViews[i].setSelected(i == mLastSelectedPosition);
            itemViews[i].setOnClickListener(new OnItemClickedListener(i));
        }
        setContentView(view);

        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void setOnMenuSelectedListener(OnMenuSelectedListener onMenuSelectedListener) {
        this.mOnMenuSelectedListener = onMenuSelectedListener;
    }

    private class OnItemClickedListener implements View.OnClickListener {

        private int position;

        public OnItemClickedListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            dismiss();
            if (position == mLastSelectedPosition) {
                return;
            }
            itemViews[position].setSelected(true);
            itemViews[mLastSelectedPosition].setSelected(false);
            mLastSelectedPosition = position;
            if (mOnMenuSelectedListener == null) return;
            mOnMenuSelectedListener.onMenuSelected(position);
        }
    }

    public interface OnMenuSelectedListener {

        void onMenuSelected(int type);

    }

}