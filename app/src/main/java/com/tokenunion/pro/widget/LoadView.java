package com.tokenunion.pro.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tokenunion.pro.R;

/**
 * Created by richard on 12/5/16.
 */

public class LoadView extends FrameLayout {

    public enum LoadStatus {
        Loading, //加载中
        Normal, //加载成功
        Request_Failure, //加载失败
        Network_Error, //网络异常
        No_Data //没有数据
    }

    private View loadView;
    private View clickView;
    private ImageView hintImageView;
    private TextView hintTextView;
    private TextView loadTextView;

    private LoadStatus status = LoadStatus.Loading;
    private OnReloadClickedListener listener;

    public LoadView(Context context) {
        this(context, null);
    }

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = View.inflate(context, R.layout.layout_common_load_status, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(view, lp);

        loadView = view.findViewById(R.id.layout_common_load_status_loading);
        loadTextView = view.findViewById(R.id.layout_common_load_loading_text);
        clickView = view.findViewById(R.id.layout_common_load_status_click);
        hintImageView = view.findViewById(R.id.layout_common_load_status_img);
        hintTextView = view.findViewById(R.id.layout_common_load_status_hint);

        clickView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) return;
                setStatus(LoadStatus.Loading);
                listener.onReloadClicked();
            }
        });
    }

    public LoadStatus getStatus() {
        return status;
    }

    public void setStatus(LoadStatus status, String... msg) {
        this.status = status;
        String defaultStr = "";
        switch (status) {
            case Loading:
                loadView.setVisibility(VISIBLE);
                clickView.setVisibility(GONE);

                if(null != msg && msg.length>0){
                    if (TextUtils.isEmpty(msg[0])) {
                        loadTextView.setText(defaultStr);
                    } else {
                        loadTextView.setText(msg[0]);
                    }
                }
                break;
            case Normal:
                setVisibility(GONE);
                break;
            case Network_Error:
                setVisibility(VISIBLE);
                loadView.setVisibility(GONE);
                clickView.setVisibility(VISIBLE);
                hintImageView.setImageResource(R.mipmap.img_no_connection);
                defaultStr = getContext().getString(R.string.no_connection_error);
                break;
            case No_Data:
                setVisibility(VISIBLE);
                loadView.setVisibility(GONE);
                clickView.setVisibility(VISIBLE);
                hintImageView.setImageResource(R.mipmap.img_no_data);
                defaultStr = getContext().getString(R.string.no_data_error);
                break;
            case Request_Failure:
                setVisibility(VISIBLE);
                loadView.setVisibility(GONE);
                clickView.setVisibility(VISIBLE);
                hintImageView.setImageResource(R.mipmap.img_request_failure);
                defaultStr = getContext().getString(R.string.unknown_error);
                break;
            default:
                break;
        }
        if (null != msg && msg.length > 0) {
            if (TextUtils.isEmpty(msg[0])) {
                hintTextView.setText(defaultStr);
            } else {
                hintTextView.setText(msg[0]);
            }
        } else {
            hintTextView.setText(R.string.unknown_error);
        }
    }

    public void setOnReloadClickedListener(OnReloadClickedListener listener) {
        this.listener = listener;
    }

    public interface OnReloadClickedListener {

        void onReloadClicked();

    }
}
