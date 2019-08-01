package com.iconiz.tokenunion.ui.login.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.mine.view.activity.CountryChoiceActivity;
import com.iconiz.tokenunion.ui.web.activity.WebActivity;
import com.iconiz.tokenunion.utils.ToastUtils;

public class RegisterActivity extends BaseActivity {

    private final int WAITING_DURATION = 60;
    private final int REQUEST_SELECT_COUNTRY = 1000;

    private TextView mCountryView;
    private TextView mSmscodeBtn;
    private TextView mLicenceView;
    private EditText mPwdView;
    private EditText mPwdAgainView;

    private Handler mHandler = new Handler();

    private int mRemainDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.register_info);

        mCountryView = findViewById(R.id.activity_register_country);
        mSmscodeBtn = findViewById(R.id.activity_register_smscode_btn);
        mLicenceView = findViewById(R.id.activity_register_user_licence);
        mLicenceView.setSelected(true);
        setLicenceLabel();
        mPwdView = findViewById(R.id.activity_register_pwd);
        mPwdAgainView = findViewById(R.id.activity_register_pwd_again);

    }

    private void setLicenceLabel() {
        mLicenceView.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString label = new SpannableString(getString(R.string.user_licence));
        int[] indexs = new int[4];
        int index = 0;
        for (int i = 0; i < label.length(); i++) {
            if (label.charAt(i) == '《') {
                indexs[index] = i;
                index++;
            } else if(label.charAt(i) == '》') {
                indexs[index] = i;
                index++;
            }
        }
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#DE524F"));

        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Bundle bundle = new Bundle();
                bundle.putString("title", getString(R.string.user_licence_title));
                bundle.putString("url", "http://www.baidu.com");
                jumpToActivity(WebActivity.class, bundle);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        label.setSpan(clickSpan, indexs[0], indexs[1] + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        label.setSpan(colorSpan, indexs[0], indexs[1] + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mLicenceView.setText(label);

        clickSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Bundle bundle = new Bundle();
                bundle.putString("title", getString(R.string.privacy_licence_title));
                bundle.putString("url", "http://www.baidu.com");
                jumpToActivity(WebActivity.class, bundle);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }

        };
        label.setSpan(clickSpan, indexs[2], indexs[3] + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        label.setSpan(colorSpan, indexs[2], indexs[3] + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mLicenceView.setText(label);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mSmsAction);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_SELECT_COUNTRY:
                mCountryView.setText(data.getStringExtra("country"));
                break;
        }
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onRegisterClicked(View view) {
        jumpToActivity(SafetySettingsActivity.class);
    }

    public void onCountryClicked(View view) {
        jumpToActivityForResult(CountryChoiceActivity.class, REQUEST_SELECT_COUNTRY);
    }

    public void onUserLicenceClicked(View view) {
        view.setSelected(!view.isSelected());
    }

    public void onSmscodeClicked(View view) {
        ToastUtils.showToast(this, R.string.send_smscode_hint);

        mSmscodeBtn.setEnabled(false);
        mRemainDuration = WAITING_DURATION;
        updateSmsText();
        mHandler.postDelayed(mSmsAction, 1000);
    }

    private Runnable mSmsAction = new Runnable() {

        @Override
        public void run() {
            mRemainDuration--;
            updateSmsText();
            if (mRemainDuration > 0) {
                mHandler.postDelayed(this, 1000);
            }
        }

    };

    private void updateSmsText() {
        if (mRemainDuration == 0) {
            mSmscodeBtn.setText(R.string.get_smscode);
            mSmscodeBtn.setEnabled(true);
        } else {
            String str = getString(R.string.smscode_waitting);
            mSmscodeBtn.setText(String.format(str, mRemainDuration));
        }
    }

    public void onHidePwdAgainClicked(View view) {
        view.setSelected(!view.isSelected());
        showPassword(mPwdAgainView, view.isSelected());
    }

    public void onHidePwdClicked(View view) {
        view.setSelected(!view.isSelected());
        showPassword(mPwdView, view.isSelected());
    }


    private void showPassword(EditText editText, boolean showed) {
        int inputType;
        if (showed) {
            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
        } else {
            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        }
        editText.setInputType(inputType);
        editText.setSelection(editText.getText().length());
    }
}
