package com.tokenunion.pro.ui.login.view;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.login.UserApi;
import com.tokenunion.pro.ui.login.model.LoginBean;
import com.tokenunion.pro.R;
//import UserBean;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.UserAccount;
import com.tokenunion.pro.manager.UserManager;
import com.tokenunion.pro.manager.model.User;
import com.tokenunion.pro.ui.main.MainActivity;
import com.tokenunion.pro.utils.ToastUtils;

public class LoginActivity extends BaseActivity {

    private EditText mPwdView;
    private EditText mLoginUser;
    private TextView mTextViewErrorTips;
    private View mLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransparent(true);
        setStatusBarLightMode();
        setContentView(R.layout.activity_login);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        mPwdView = findViewById(R.id.activity_login_pwd);
        mLoginUser = findViewById(R.id.et_login_user);
        mTextViewErrorTips = findViewById(R.id.activity_login_error_tips);
        mLoadView = findViewById(R.id.activity_login_loading);
//        mLoginUser.setText("hansong");
//        mPwdView.setText("123456789");

//        mLoginUser.setText("test1675");
//        mPwdView.setText("12345678");

        RadioGroup radioGroup = findViewById(R.id.user_level_change);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.user_level_change_0:
                        UserManager.getInstance().getUser().setUserLevel(User.LEVEL_YOUNG);
                        break;
                    case R.id.user_level_change_1:
                        UserManager.getInstance().getUser().setUserLevel(User.LEVEL_PREFERRED);
                        break;
                    case R.id.user_level_change_2:
                        UserManager.getInstance().getUser().setUserLevel(User.LEVEL_ELITE);
                        break;
                    case R.id.user_level_change_3:
                        UserManager.getInstance().getUser().setUserLevel(User.LEVEL_RESERVE);
                        break;
                    case R.id.user_level_change_4:
                        UserManager.getInstance().getUser().setUserLevel(User.LEVEL_WORLD);
                        break;
                    case R.id.user_level_change_5:
                        UserManager.getInstance().getUser().setUserLevel(User.LEVEL_INFINITE);
                        break;
                }
            }
        });
    }

    public void onLoginClicked(View view) {
        doLogin();
    }

    public void onRegisterClicked(View view) {
        jumpToActivity(RegisterActivity.class);
    }

    public void onLanguageChoiceClicked(View view) {
        jumpToActivity(LanguageChoiceActivity.class);
    }

    public void onForgetPwdClicked(View view) {
        jumpToActivity(ForgetPwdActivity.class);
    }

    public void onHidePwdClicked(View view) {
        view.setSelected(!view.isSelected());
        int inputType;
        if (view.isSelected()) {
            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
        } else {
            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        }
        mPwdView.setInputType(inputType);
        mPwdView.setSelection(mPwdView.getText().length());
    }

    /**
     * 检查输入
     * @return
     */
    private boolean verifyInput() {
        String userName = mLoginUser.getText().toString();
//        if (!FormatUtils.isLetterDigit(userName) || userName.length() < 6) {
        if (TextUtils.isEmpty(userName)) {
            mTextViewErrorTips.setVisibility(View.VISIBLE);
            mTextViewErrorTips.setText(R.string.field_required);

            mLoginUser.requestFocus();
            return false;
        }else if (userName.length() < 6) {
            mTextViewErrorTips.setVisibility(View.VISIBLE);
            mTextViewErrorTips.setText(R.string.err_username_format);

            mLoginUser.selectAll();
            mLoginUser.requestFocus();
            return false;
        } else {
            mTextViewErrorTips.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(mPwdView.getText().toString())) {
            mTextViewErrorTips.setVisibility(View.VISIBLE);
            mTextViewErrorTips.setText(R.string.field_required);
            mPwdView.requestFocus();
            return false;
        }else if (mPwdView.getText().toString().length() < 8) {
            mTextViewErrorTips.setVisibility(View.VISIBLE);
            mTextViewErrorTips.setText(R.string.err_pwd_format);
            mPwdView.selectAll();
            mPwdView.requestFocus();
            return false;
        } else {
            mTextViewErrorTips.setVisibility(View.GONE);
        }
        return true;
    }

    private void doLogin(){
        if(!verifyInput()){
            return;
        }

        String userName = mLoginUser.getText().toString();
        String userPhone = mLoginUser.getText().toString();

        mLoadView.setVisibility(View.VISIBLE);
        UserApi.login(userName, userPhone,
                mPwdView.getText().toString(),
                mActive, new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        LoginBean loginBean = (LoginBean)object;
                        UserAccount.getInstance().setUserBean(loginBean);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mLoadView.setVisibility(View.GONE);
                            }
                        });
                        jumpToActivity(MainActivity.class);
                        finish();
                    }

                    @Override
                    public void onFailed(final String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mLoadView.setVisibility(View.GONE);
                                        ToastUtils.showToast(getBaseContext(), errMessage);
                                        mPwdView.requestFocus();
                                    }
                                });
                            }
                        });
                    }
                });
    }
}
