package com.tokenunion.pro.ui.login.view;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.login.UserApi;
import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.UserAccount;
import com.tokenunion.pro.ui.main.MainActivity;
import com.tokenunion.pro.ui.mine.view.activity.GoogleValidatorActivity;
import com.tokenunion.pro.utils.FormatUtils;
import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.Md5Utils;
import com.tokenunion.pro.utils.ToastUtils;

public class SafetySettingsActivity extends BaseActivity {

    public static final String KEY_REGIST = "isRegist";
    private static final String TAG = "SafetySettingsActivity";
    private EditText mPwdView;
    private EditText mPwdAgainView;
    private TextView mSkipView;
    private boolean mIsFromRegist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_settings);

        if(null != getIntent().getExtras()){
            mIsFromRegist = getIntent().getExtras().getBoolean(KEY_REGIST, false);
        }

        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {

        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.safety_settings);

        TextView googleView = findViewById(R.id.activity_safety_settings_google);
        googleView.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        googleView.getPaint().setAntiAlias(true);//抗锯齿

        mPwdView = findViewById(R.id.activity_safety_settings_pwd);
        mPwdAgainView = findViewById(R.id.activity_safety_settings_pwd_again);

        mSkipView = findViewById(R.id.layout_common_actionbar_right_skip); // 跳过

        if(mIsFromRegist) {
            TextView backView = findViewById(R.id.layout_common_actionbar_left);
            backView.setVisibility(View.GONE); // 隐藏"返回"
            mSkipView.setVisibility(View.VISIBLE); // 显示跳过
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goNext();
    }

    public void onBackClicked(View view) {
        goNext();
    }

    public void onSkipClicked(View view) {
        goNext();
    }

    public void onRegisterClicked(View view) {
        setTradePassword();
    }

    public void onGoogleClicked(View view) {
        jumpToActivity(GoogleValidatorActivity.class);
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

    /**
     * 检查输入
     * @return
     */
    private boolean verifyInput(){
        String password = mPwdView.getText().toString();

        if(!FormatUtils.isPassword(password)){
            mPwdView.selectAll();
            mPwdView.requestFocus();
            findViewById(R.id.activity_safety_settings_pwd_hint).setVisibility(View.VISIBLE);

            return false;
        }else{
            findViewById(R.id.activity_safety_settings_pwd_hint).setVisibility(View.GONE);
        }

        if(!password.equals(mPwdAgainView.getText().toString())){
            findViewById(R.id.activity_safety_settings_pwd_again_hint).setVisibility(View.VISIBLE);
            mPwdAgainView.selectAll();
            mPwdAgainView.requestFocus();
            return false;
        }else{
            findViewById(R.id.activity_safety_settings_pwd_again_hint).setVisibility(View.GONE);
        }
        return true;
    }

    /**
     * "设置成功"或者"跳过"，之后执行的操作，跳转到首页
     */
    private void goNext(){
        if(mIsFromRegist) {
            jumpToActivity(MainActivity.class);
        }
        finish();
    }

    /**
     * 设置交易资金密码
     */
    private void setTradePassword(){
        if(!verifyInput()){
            return;
        }
        UserApi.setTradePassword(
                Md5Utils.MD5Encode(mPwdView.getText().toString()),
                mActive,
                new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        LogUtil.d(TAG, "setTradePassword success."+ object);
                        UserAccount.getInstance().getUserBean().setIsBindTradePasswd(1);
                        UserAccount.getInstance().saveUserInfo();
                        if(mIsFromRegist) {
                            jumpToActivity(MainActivity.class);
                        }
                        finish();
                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(SafetySettingsActivity.this, errMessage);
                            }
                        });

                    }
                });
    }
}
