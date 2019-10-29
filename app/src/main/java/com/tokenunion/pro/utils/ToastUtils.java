package com.tokenunion.pro.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tokenunion.pro.R;
import com.yidaichu.android.common.utils.DensityUtils;

public class ToastUtils {

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }

    public static void showToast(Context context, String str) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        TextView textView = view.findViewById(R.id.layout_toast_text);
        textView.setText(str);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, DensityUtils.dip2px(context, 69));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
