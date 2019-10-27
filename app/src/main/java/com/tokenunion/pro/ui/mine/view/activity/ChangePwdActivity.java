package com.tokenunion.pro.ui.mine.view.activity;

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
import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.utils.FormatUtils;
import com.tokenunion.pro.utils.Md5Utils;
import com.tokenunion.pro.utils.ToastUtils;

public class ChangePwdActivity extends BaseActivity {

    private final int REQUEST_SELECT_COUNTRY = 1000;
    private final int WAITING_DURATION = 60;

    private TextView mCountryView;
    private EditText mPhoneView;
    private EditText mSmsCodeView;
    private EditText mPwdOriView;
    private EditText mPwdView;
    private EditText mPwdAgainView;
    private TextView mSmscodeBtn;

    private Handler mHandler = new Handler();

    private int mRemainDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.change_pwd);

        mCountryView = findViewById(R.id.activity_change_pwd_country);
        mPwdOriView = findViewById(R.id.activity_change_pwd_old_pwd);
        mPwdView = findViewById(R.id.activity_change_pwd_pwd);
        mPhoneView = findViewById(R.id.activity_change_pwd_phone);
        mSmsCodeView = findViewById(R.id.activity_change_pwd_smscode);
        mPwdAgainView = findViewById(R.id.activity_change_pwd_pwd_again);
        mSmscodeBtn = findViewById(R.id.activity_change_pwd_smscode_btn);
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
                // 隐藏错误提示
                findViewById(R.id.activity_change_pwd_country_hint).setVisibility(View.GONE);
                break;
        }
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onChangePwdClicked(View view) {
        commitChange();
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

    public void onSmscodeClicked(View view) {
        if(!verifySmsInput()){
            return;
        }
        getSmsCode(this, mPhoneView.getText().toString(), (String)mCountryView.getTag());

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

    private boolean verifySmsInput(){
        // 国家校验
        if(mCountryView.getText().toString().isEmpty()){
            findViewById(R.id.activity_change_pwd_country_hint).setVisibility(View.VISIBLE);
            mCountryView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_change_pwd_country_hint).setVisibility(View.GONE);
        }

        // 手机号校验
        if(!FormatUtils.isPhone(mPhoneView.getText().toString())){
            findViewById(R.id.activity_change_pwd_phone_hint).setVisibility(View.VISIBLE);
            mPhoneView.selectAll();
            mPhoneView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_change_pwd_phone_hint).setVisibility(View.GONE);
        }
        return true;
    }

    /**
     * 检查输入
     * @return
     */
    private boolean verifyInput(){
        String password = mPwdView.getText().toString();
        // 国家校验
        if(mCountryView.getText().toString().isEmpty()){
            findViewById(R.id.activity_change_pwd_country_hint).setVisibility(View.VISIBLE);
            mCountryView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_change_pwd_country_hint).setVisibility(View.GONE);
        }


        // 手机号校验
        if(!FormatUtils.isPhone(mPhoneView.getText().toString())){
            findViewById(R.id.activity_change_pwd_phone_hint).setVisibility(View.VISIBLE);
            mPhoneView.selectAll();
            mPhoneView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_change_pwd_phone_hint).setVisibility(View.GONE);
        }

        // 验证码校验
        if(mSmsCodeView.getText().toString().isEmpty()){
            findViewById(R.id.activity_change_pwd_smscode_hint).setVisibility(View.VISIBLE);
            mSmsCodeView.selectAll();
            mSmsCodeView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_change_pwd_smscode_hint).setVisibility(View.GONE);
        }

        // 密码校验
        if(!FormatUtils.isPassword(mPwdView.getText().toString())){
            findViewById(R.id.activity_change_pwd_pwd_hint).setVisibility(View.VISIBLE);

            mPwdView.selectAll();
            mPwdView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_change_pwd_pwd_hint).setVisibility(View.GONE);
        }

        // 密码一致性校验
        if(!password.equals(mPwdAgainView.getText().toString())){
            findViewById(R.id.activity_change_pwd_pwd_again_hint).setVisibility(View.VISIBLE);

            mPwdAgainView.selectAll();
            mPwdAgainView.requestFocus();
            return false;
        }else{
            findViewById(R.id.activity_change_pwd_pwd_again_hint).setVisibility(View.GONE);
        }
        return true;
    }

    public void onCountryClicked(View view) {
        jumpToActivityForResult(CountryChoiceActivity.class, REQUEST_SELECT_COUNTRY);
    }

    private void commitChange() {
        if (!verifyInput()) {
            return;
        }
        UserApi.changeLoginPassword(
                Md5Utils.MD5Encode(mPwdOriView.getText().toString()),
                mPhoneView.getText().toString(),
                mSmsCodeView.getText().toString(),
                mPwdView.getText().toString(),
                mActive,
                new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(ChangePwdActivity.this, R.string.change_pwd_success);
                                finish();
                            }
                        });

                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(ChangePwdActivity.this, errMessage);
                            }
                        });
                    }
                });
    }
}
