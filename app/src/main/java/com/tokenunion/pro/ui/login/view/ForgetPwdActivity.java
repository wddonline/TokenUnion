package com.tokenunion.pro.ui.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.login.UserApi;
import com.tokenunion.pro.ui.mine.view.activity.CountryChoiceActivity;
import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.utils.FormatUtils;
import com.tokenunion.pro.utils.ToastUtils;

public class ForgetPwdActivity extends BaseActivity {

    private final int WAITING_DURATION = 60;
    private final int REQUEST_SELECT_COUNTRY = 1000;

    private TextView mCountryView;
    private TextView mCountryHintView;
    private TextView mSmscodeBtn;
    private EditText mPwdView;
    private EditText mPwdAgainView;
    private EditText mPhone;
    private EditText mSmsCode;

    private Handler mHandler = new Handler();

    private int mRemainDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.retrieve_pwd);

        mCountryView = findViewById(R.id.activity_forget_pwd_country);
        mSmscodeBtn = findViewById(R.id.activity_forget_pwd_smscode_btn);
        mPwdView = findViewById(R.id.activity_forget_pwd_pwd);
        mPwdAgainView = findViewById(R.id.activity_forget_pwd_pwd_again);
        mPhone = findViewById(R.id.activity_forget_pwd_phone);
        mSmsCode = findViewById(R.id.activity_forget_pwd_smscode);
        mCountryHintView = findViewById(R.id.activity_forget_pwd_country_hint);

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
                mCountryView.setText(data.getStringExtra(CountryChoiceActivity.EXTRA_KEY_COUNTRY));
                mCountryView.setTag(data.getStringExtra(CountryChoiceActivity.EXTRA_KEY_PHONE_CODE));
                mCountryHintView.setVisibility(View.GONE);
                break;
        }
    }

    private boolean verifySmsInput(){
        // 国家校验
        if(mCountryView.getText().toString().isEmpty()){
//            ToastUtils.showToast(this, getString(R.string.err_phone_format));
            findViewById(R.id.activity_forget_pwd_country_hint).setVisibility(View.VISIBLE);
            mCountryView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_forget_pwd_country_hint).setVisibility(View.GONE);
        }

        // 手机号校验
        if(!FormatUtils.isPhone(mPhone.getText().toString())){
//            ToastUtils.showToast(this, getString(R.string.err_phone_format));
            findViewById(R.id.activity_forget_pwd_phone_hint).setVisibility(View.VISIBLE);
            mPhone.selectAll();
            mPhone.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_forget_pwd_phone_hint).setVisibility(View.GONE);
        }
        return true;
    }

    public void onSmscodeClicked(View view) {
        if(!verifySmsInput()){
            return;
        }
        getSmsCode(this, mPhone.getText().toString(), (String)mCountryView.getTag());

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

    public void onCountryClicked(View view) {
        jumpToActivityForResult(CountryChoiceActivity.class, REQUEST_SELECT_COUNTRY);
    }

    public void onBackClicked(View view) {
        finish();
    }

    /**
     * 检查输入
     * @return
     */
    private boolean verifyInput(){
        String password = mPwdView.getText().toString();
        // 国家校验
        if(mCountryView.getText().toString().isEmpty()){
//            ToastUtils.showToast(this, getString(R.string.err_phone_format));
            findViewById(R.id.activity_forget_pwd_country_hint).setVisibility(View.VISIBLE);
            mCountryView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_forget_pwd_country_hint).setVisibility(View.GONE);
        }


        // 手机号校验
        if(!FormatUtils.isPhone(mPhone.getText().toString())){
//            ToastUtils.showToast(this, getString(R.string.err_phone_format));
            findViewById(R.id.activity_forget_pwd_phone_hint).setVisibility(View.VISIBLE);
            mPhone.selectAll();
            mPhone.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_forget_pwd_phone_hint).setVisibility(View.GONE);
        }

        // 验证码校验
        if(mSmsCode.getText().toString().isEmpty()){
            findViewById(R.id.activity_forget_pwd_smscode_hint).setVisibility(View.VISIBLE);
            mSmsCode.selectAll();
            mSmsCode.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_forget_pwd_smscode_hint).setVisibility(View.GONE);
        }

        // 密码校验
        if(!FormatUtils.isPassword(mPwdView.getText().toString())){
//            ToastUtils.showToast(this, getString(R.string.err_pwd_format));
            findViewById(R.id.activity_forget_pwd_pwd_hint).setVisibility(View.VISIBLE);

            mPwdView.selectAll();
            mPwdView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_forget_pwd_pwd_hint).setVisibility(View.GONE);
        }

        // 密码一致性校验
        if(!password.equals(mPwdAgainView.getText().toString())){
//            ToastUtils.showToast(this, getString(R.string.err_pwd_not_consistency));
            findViewById(R.id.activity_forget_pwd_pwd_again_hint).setVisibility(View.VISIBLE);

            mPwdAgainView.selectAll();
            mPwdAgainView.requestFocus();
            return false;
        }else{
            findViewById(R.id.activity_forget_pwd_pwd_again_hint).setVisibility(View.GONE);
        }
        return true;
    }

    public void onResetPwdClicked(View view) {
        if(!verifyInput()){
            return;
        }

        String countryAbb = (mCountryView.getTag() == null) ? "" : mCountryView.getTag().toString();
        UserApi.forgetLoginPassword(countryAbb, mPhone.getText().toString(),
                mSmsCode.getText().toString(), mPwdAgainView.getText().toString(),
                mActive, new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(ForgetPwdActivity.this, R.string.reset_pwd_success);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(ForgetPwdActivity.this, errMessage);
                            }
                        });
                    }
                });
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
