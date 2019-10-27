package com.tokenunion.pro.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tokenunion.pro.ui.mine.view.activity.GoogleValidatorActivity;
import com.anypocket.pro.R;
import com.tokenunion.pro.config.UserAccount;

import static com.anypocket.pro.R.style.BottomDialog;

/**
 * Create by: xiaohansong
 * Time: 2019-08-02 18:38
 * -
 * Description: google验证码输入及验证框
 */
public class GoogleCodeVerifyDlg extends Dialog {

    private OnDialogListener mOnDialogListener;
    private String mGoogleCode;

    public String getGoogleCode() {
        return mGoogleCode;
    }

    public void setGoogleCode(String googleCode) {
        mGoogleCode = googleCode;
    }

    public interface OnDialogListener {
        void onCloseClick();
        void onInputComplete();
    }


    public void setOnDialogListener(OnDialogListener onDialogListener) {
        this.mOnDialogListener = onDialogListener;
    }

    public GoogleCodeVerifyDlg(@NonNull Context context) {
//        super(context);
        super(context, BottomDialog);//风格主题
    }

    public GoogleCodeVerifyDlg(@NonNull Context context, int themeResId) {
//        super(context, themeResId);
        super(context, BottomDialog);//风格主题
    }

    protected GoogleCodeVerifyDlg(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_code_verify_dlg_layout);//自定义布局
        //按空白处不能取消动画
//        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        SecurityCodeView securityCodeView = findViewById(R.id.dlg_google_validator);
        findViewById(R.id.dlg_google_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleCode = securityCodeView.getInputCode();
                if(null != mOnDialogListener){
                    mOnDialogListener.onCloseClick();
                }
            }
        });

        securityCodeView.setInputCompletedListener(new SecurityCodeView.InputCompletedListener() {
            @Override
            public void inputComplete() {
                mGoogleCode = securityCodeView.getInputCode();
                if(null != mOnDialogListener){
                    mOnDialogListener.onInputComplete();
                }
            }

            @Override
            public void deleteContent(boolean isDelete) {

            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
    }

    /**
     * 将TextView设置成带链接的、可以点击的样式
     * @param txView
     * @param start
     * @param end
     */
    private void setLinkStyle(TextView txView, int start, int end){
        String strValue = txView.getText().toString();
        SpannableString label = new SpannableString(strValue);
        txView.setMovementMethod(LinkMovementMethod.getInstance());

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                switch (widget.getId()){
                    case R.id.dlg_google_validator_go_bind:
                        Intent intent = new Intent(getContext(), GoogleValidatorActivity.class);
                        getContext().startActivity(intent);
                        dismiss();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0080FF"));

        label.setSpan(clickableSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        label.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        txView.setText(label);
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        TextView goBindView = findViewById(R.id.dlg_google_validator_go_bind);
        // 未绑定google验证码，则提示绑定
        if(UserAccount.getInstance().isLogin() && UserAccount.getInstance().getUserBean().isBindedGoogle()){
            goBindView.setVisibility(View.GONE);
            return;
        }

        String text = getContext().getString(R.string.google_code_not_bind);
        int pos = 0;
        if(text.contains("，")){
            pos = text.indexOf("，");
        }else if(text.contains(",")){
            pos = text.indexOf(",");
        }
        setLinkStyle(goBindView, pos+ 1, text.length());
    }
}
