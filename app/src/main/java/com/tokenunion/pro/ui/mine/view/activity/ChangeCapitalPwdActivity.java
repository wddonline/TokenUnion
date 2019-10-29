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
import com.tokenunion.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.utils.FormatUtils;
import com.tokenunion.pro.utils.Md5Utils;
import com.tokenunion.pro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeCapitalPwdActivity extends BaseActivity {

    private final int WAITING_DURATION = 60;
    private final int REQUEST_SELECT_COUNTRY = 1000;

    @BindView(R.id.activity_change_capital_pwd_phone)
    EditText activityChangeCapitalPwdPhone;
    @BindView(R.id.activity_change_capital_pwd_phone_hint)
    TextView activityChangeCapitalPwdPhoneHint;
    @BindView(R.id.activity_change_capital_pwd_smscode)
    EditText activityChangeCapitalPwdSmscode;
    @BindView(R.id.activity_change_capital_pwd_smscode_btn)
    TextView activityChangeCapitalPwdSmscodeBtn;
    @BindView(R.id.activity_change_capital_pwd_smscode_hint)
    TextView activityChangeCapitalPwdSmscodeHint;
    @BindView(R.id.activity_change_capital_pwd_pwd)
    EditText activityChangeCapitalPwdPwd;
    @BindView(R.id.activity_change_capital_pwd_pwd_hint)
    TextView activityChangeCapitalPwdPwdHint;
    @BindView(R.id.activity_change_capital_pwd_pwd_again)
    EditText activityChangeCapitalPwdPwdAgain;
    @BindView(R.id.activity_change_capital_pwd_pwd_again_hint)
    TextView activityChangeCapitalPwdPwdAgainHint;
    @BindView(R.id.activity_change_capital_pwd_country)
    TextView activityChangeCapitalPwdCountry;

    private Handler mHandler = new Handler();

    private int mRemainDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_capital_pwd);
        ButterKnife.bind(this);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.change_capital_pwd);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mSmsAction);
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onChangePwdClicked(View view) {
        doRequest();
    }

    public void onHidePwdAgainClicked(View view) {
        view.setSelected(!view.isSelected());
        showPassword(activityChangeCapitalPwdPwdAgain, view.isSelected());
    }

    public void onHidePwdClicked(View view) {
        view.setSelected(!view.isSelected());
        showPassword(activityChangeCapitalPwdPwd, view.isSelected());
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
            activityChangeCapitalPwdSmscodeBtn.setText(R.string.get_smscode);
            activityChangeCapitalPwdSmscodeBtn.setEnabled(true);
        } else {
            String str = getString(R.string.smscode_waitting);
            activityChangeCapitalPwdSmscodeBtn.setText(String.format(str, mRemainDuration));
        }
    }

    private boolean verifyInput() {

        if (!FormatUtils.isPhone(activityChangeCapitalPwdPhone.getText().toString())) {
            activityChangeCapitalPwdPhone.requestFocus();
            activityChangeCapitalPwdPhoneHint.setVisibility(View.VISIBLE);
            return false;
        } else {
            activityChangeCapitalPwdPhoneHint.setVisibility(View.GONE);
        }

        if (activityChangeCapitalPwdSmscode.getText().toString().isEmpty()) {
            activityChangeCapitalPwdSmscode.requestFocus();
            activityChangeCapitalPwdSmscodeHint.setVisibility(View.VISIBLE);
            return false;
        } else {
            activityChangeCapitalPwdSmscodeHint.setVisibility(View.GONE);
        }

        if (!FormatUtils.isPassword(activityChangeCapitalPwdPwd.getText().toString())) {
            activityChangeCapitalPwdPwd.requestFocus();
            activityChangeCapitalPwdPwdHint.setVisibility(View.VISIBLE);
            return false;
        } else {
            activityChangeCapitalPwdPwdHint.setVisibility(View.GONE);
        }

        // 2次密码是否一致
        if (!activityChangeCapitalPwdPwd.getText().toString().equals(
                activityChangeCapitalPwdPwdAgain.getText().toString())) {
            activityChangeCapitalPwdPwdAgain.requestFocus();
            activityChangeCapitalPwdPwdAgainHint.setVisibility(View.VISIBLE);
            return false;
        } else {
            activityChangeCapitalPwdPwdAgainHint.setVisibility(View.GONE);
        }
        return true;
    }

    private void doRequest() {
        if (!verifyInput()) {
            return;
        }
        UserApi.modifyTradePassword(
                Md5Utils.MD5Encode(activityChangeCapitalPwdPwd.getText().toString()),
                activityChangeCapitalPwdPhone.getText().toString(),
                activityChangeCapitalPwdSmscode.getText().toString(),
                mActive,
                new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(ChangeCapitalPwdActivity.this, R.string.change_pwd_success);
                                finish();
                            }
                        });

                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(ChangeCapitalPwdActivity.this, errMessage);
                            }
                        });
                    }
                });
    }

//    @OnClick({R.id.activity_change_capital_pwd_smscode_btn, R.id.tv_test})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.activity_change_capital_pwd_smscode_btn:
//                break;
//            case R.id.tv_test:
//                ToastUtils.showToast(this, "haha");
//                break;
//        }
//    }

    @OnClick({R.id.activity_change_capital_pwd_smscode_btn, R.id.activity_change_capital_pwd_pwd_again_hint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_change_capital_pwd_smscode_btn:
                if(!verifySmsInput()){
                    break;
                }
                getSmsCode(this, activityChangeCapitalPwdPhone.getText().toString(),
                        (String)activityChangeCapitalPwdCountry.getTag());
                ToastUtils.showToast(this, R.string.send_smscode_hint);

                activityChangeCapitalPwdSmscodeBtn.setEnabled(false);
                mRemainDuration = WAITING_DURATION;
                updateSmsText();
                mHandler.postDelayed(mSmsAction, 1000);
                break;
            case R.id.activity_change_capital_pwd_pwd_again_hint:
                break;

            default:
                break;
        }
    }

    private boolean verifySmsInput(){
        // 国家校验
        if(activityChangeCapitalPwdCountry.getText().toString().isEmpty()){
//            ToastUtils.showToast(this, getString(R.string.err_phone_format));
            findViewById(R.id.activity_change_capital_pwd_country_hint).setVisibility(View.VISIBLE);
            activityChangeCapitalPwdCountry.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_change_capital_pwd_country_hint).setVisibility(View.GONE);
        }

        // 手机号校验
        if(!FormatUtils.isPhone(activityChangeCapitalPwdPhone.getText().toString())){
//            ToastUtils.showToast(this, getString(R.string.err_phone_format));
            findViewById(R.id.activity_change_capital_pwd_phone_hint).setVisibility(View.VISIBLE);
            activityChangeCapitalPwdPhone.selectAll();
            activityChangeCapitalPwdPhone.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_change_capital_pwd_phone_hint).setVisibility(View.GONE);
        }
        return true;
    }

    public void onCountryClicked(View view) {
        jumpToActivityForResult(CountryChoiceActivity.class, REQUEST_SELECT_COUNTRY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_SELECT_COUNTRY:
                activityChangeCapitalPwdCountry.setText(data.getStringExtra(CountryChoiceActivity.EXTRA_KEY_COUNTRY));
                activityChangeCapitalPwdCountry.setTag(data.getStringExtra(CountryChoiceActivity.EXTRA_KEY_PHONE_CODE));
                break;
        }
    }
}
