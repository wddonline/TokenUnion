package com.tokenunion.pro.ui.mine.view.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.login.UserApi;
import com.tokenunion.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.UserAccount;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.SecurityCodeView;

public class GoogleValidatorActivity extends BaseActivity {

    private TextView mTextViewOurCode;
    private SecurityCodeView mSecurityCodeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_validator);
        initData();
        initViews();

        requestOurCode();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.google_identify);
        mTextViewOurCode = findViewById(R.id.activity_google_ourcode);
        mSecurityCodeView = findViewById(R.id.activity_google_validator);

        TextView hintView = findViewById(R.id.activity_google_validator_hint);
        String str = getString(R.string.google_code_miss);
        int start = str.indexOf('？') + 1;
        setLinkStyle(hintView, start, str.length());
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
                    case R.id.activity_google_validator_hint:
//                        ToastUtils.showToast(GoogleValidatorActivity.this, "validator_hint");
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
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#DE524F"));

        label.setSpan(clickableSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        label.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        txView.setText(label);
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onLoginClicked(View view) {
        doBind();
    }

    public void onGoogleCodeCopy(View view) {
        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText(null, mTextViewOurCode.getText().toString()));
        ToastUtils.showToast(this, R.string.input_google_code_copy_success);
    }

    private void requestOurCode(){
        UserApi.getGoogleCode(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextViewOurCode.setText((String)object);
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(GoogleValidatorActivity.this, errMessage);
                    }
                });
            }
        });

    }

    private boolean verifyInput(){
        if(null == mSecurityCodeView.getInputCode()
                || mSecurityCodeView.getInputCode().length() < 6){
            return false;
        }
        if(mTextViewOurCode.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }

    private void doBind(){
        if(!verifyInput()){
            return;
        }
        String googleAppCode = mSecurityCodeView.getInputCode();
        UserApi.bindGoogleCode(mTextViewOurCode.getText().toString(),
                googleAppCode, mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        ToastUtils.showToast(GoogleValidatorActivity.this, getString(R.string.binded));
                        UserAccount.getInstance().getUserBean().setIsBindGoogle(1);
                        UserAccount.getInstance().saveUserInfo();
                        finish();
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(GoogleValidatorActivity.this, errMessage);
                    }
                });
            }
        });
    }
}
