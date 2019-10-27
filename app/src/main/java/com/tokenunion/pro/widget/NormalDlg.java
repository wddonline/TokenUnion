package com.tokenunion.pro.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anypocket.pro.R;
import com.tokenunion.pro.utils.StringUtils;

import static com.anypocket.pro.R.style.BottomDialog;

/**
 * Create by: xiaohansong
 * Time: 2019-08-02 18:38
 * -
 * Description: 通用验证码输入及验证框
 */
public class NormalDlg extends Dialog {

    private Button mYesBtn;
    private Button mNoBtn;
    private TextView mTitleTv;
    private TextView mMessageTv;
    private String mTitleStr;
    private String mMessageStr;
    private String mYesStr, mNoStr;

    /*  -------------------------------- 接口监听 -------------------------------------  */

    private OnNoClickListener mNoClickListener;
    private OnYesClickListener mYesClickListener;

    public interface OnYesClickListener {
        void onYesClick();
    }

    public interface OnNoClickListener {
        void onNoClick();
    }

    public void setNoClickListener(String str, OnNoClickListener onNoClickListener) {
        if (str != null) {
            mNoStr = str;
        }
        this.mNoClickListener = onNoClickListener;
    }

    public void setYesClickListener(String str, OnYesClickListener onYesClickListener) {
        if (str != null) {
            mYesStr = str;
        }
        this.mYesClickListener = onYesClickListener;
    }


    public NormalDlg(@NonNull Context context) {
//        super(context);
        super(context, BottomDialog);//风格主题
    }

    public NormalDlg(@NonNull Context context, int themeResId) {
//        super(context, themeResId);
        super(context, BottomDialog);//风格主题
    }

    protected NormalDlg(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_dlg_layout);//自定义布局
        //按空白处不能取消动画
//        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        mYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mYesClickListener != null) {
                    mYesClickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        mNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNoClickListener != null) {
                    mNoClickListener.onNoClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (!StringUtils.isEmpty(mTitleStr)) {
            mTitleTv.setVisibility(View.VISIBLE);
            mTitleTv.setText(mTitleStr);
        }
        if (!StringUtils.isEmpty(mMessageStr)) {
            mMessageTv.setVisibility(View.VISIBLE);
            mMessageTv.setText(mMessageStr);
        }
        //如果设置按钮的文字
        if (!StringUtils.isEmpty(mYesStr)) {
            mYesBtn.setText(mYesStr);
        }
        if (!StringUtils.isEmpty(mNoStr)) {
            mNoBtn.setText(mNoStr);
        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        mYesBtn = (Button) findViewById(R.id.yes);
        mNoBtn = (Button) findViewById(R.id.no);
        mTitleTv = (TextView) findViewById(R.id.title);
        mMessageTv = (TextView) findViewById(R.id.message);
    }

    /*  ---------------------------------- set方法 传值-------------------------------------  */
//为外界设置一些public 公开的方法，来向自定义的dialog传递值
    public void setTitle(String title) {
        mTitleStr = title;
    }

    public void setMessage(String message) {
        mMessageStr = message;
    }
}
