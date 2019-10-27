package com.tokenunion.pro.ui.mine.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.login.UserApi;
import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.UserAccount;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.SecurityCodeView;

public class UnbindGoogleValidatorActivity extends BaseActivity {

    private SecurityCodeView mSecurityCodeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unbind_google_validator);

        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.unbind_google_identify);
        mSecurityCodeView = findViewById(R.id.activity_google_validator);
    }

    public void onBackClicked(View view) {
        finish();
    }

    private boolean verifyInput(){
        if(null == mSecurityCodeView.getInputCode()
                || mSecurityCodeView.getInputCode().length() < 6){
            return false;
        }
        return true;
    }
    public void onUnBindClicked(View view) {
        if(!verifyInput()){
            return;
        }

        UserApi.unBindGoogleCode(mSecurityCodeView.getInputCode(), mActive,
                new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(UnbindGoogleValidatorActivity.this,
                                        UnbindGoogleValidatorActivity.this.getString(R.string.google_code_unbind_success));
                            }
                        });
                        UserAccount.getInstance().getUserBean().setIsBindGoogle(0);
                        UserAccount.getInstance().saveUserInfo();

                        finish();
                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(UnbindGoogleValidatorActivity.this, errMessage);
                            }
                        });

                    }
                });

    }
}
