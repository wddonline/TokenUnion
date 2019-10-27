package com.tokenunion.pro.ui.mine.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.login.view.SafetySettingsActivity;
import com.anypocket.pro.R;
import com.tokenunion.pro.config.UserAccount;

public class SecurityCenterActivity extends BaseActivity {

    private final int REQUEST_CHANGE_PWD = 1000;
    private final int REQUEST_CHANGE_CAPITAL_PWD = 1001;
    private final int REQUEST_BIND_GOOGLE = 1002;

    private TextView mPwdView;
    private TextView mCapitalPwdView;
    private TextView mBindStatusView;
    private TextView mBindView;
    private TextView mChangeTradePasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_center);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.security_center);

        if(!UserAccount.getInstance().isLogin()){
            return;
        }

        TextView userNameView = findViewById(R.id.activity_security_center_username);

        userNameView.setText(UserAccount.getInstance().getUserBean().getUserName());//"EWR12334UG");
        TextView phoneView = findViewById(R.id.activity_security_center_phone);
        phoneView.setText(UserAccount.getInstance().getUserBean().getPhone());//"+86-10-85865390");

        // 登录密码
        mPwdView = findViewById(R.id.activity_security_center_pwd);
        mPwdView.setText("wwwwwwwwwwwwwww"); // 登录密码

        // 资金密码
        mCapitalPwdView = findViewById(R.id.activity_security_center_capital_pwd);
        mChangeTradePasswordView = findViewById(R.id.activity_security_center_capital_change);
        if(UserAccount.getInstance().getUserBean().isSetTradePasswd()){
            mChangeTradePasswordView.setText(R.string.modify);
            mCapitalPwdView.setText("wwwwwwwwwwwwwww"); // 资金密码
        }else{
            mChangeTradePasswordView.setText(R.string.set);
            mCapitalPwdView.setText("");
        }

        // google绑定状态
        mBindStatusView = findViewById(R.id.activity_security_center_bind_status);
        mBindView = findViewById(R.id.activity_security_center_bind);
        if(UserAccount.getInstance().getUserBean().isBindedGoogle()) {
            // 已绑定
            mBindStatusView.setText(R.string.binded);
            mBindView.setText(R.string.unbind);
        }else{
            // 未绑定
            mBindStatusView.setText(R.string.unbinded);
            mBindView.setText(R.string.bind);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_CHANGE_PWD:
                
                break;
            case REQUEST_CHANGE_CAPITAL_PWD:
                break;
            case REQUEST_BIND_GOOGLE:
                if(UserAccount.getInstance().getUserBean().isBindedGoogle()) {
                    mBindStatusView.setText(R.string.binded);
                    mBindView.setText(R.string.unbind);
                }else{
                    mBindStatusView.setText(R.string.unbinded);
                    mBindView.setText(R.string.bind);
                }
                break;
        }
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onChangePwdClicked(View view) {
        jumpToActivityForResult(ChangePwdActivity.class, REQUEST_CHANGE_PWD);
    }

    public void onChangeCapitalPwdClicked(View view) {
        if(UserAccount.getInstance().getUserBean().isSetTradePasswd()) {
            jumpToActivityForResult(ChangeCapitalPwdActivity.class, REQUEST_CHANGE_CAPITAL_PWD);
        }else {
            jumpToActivityForResult(SafetySettingsActivity.class, REQUEST_CHANGE_PWD);
        }
    }

    public void onBindGoogleClicked(View view) {
        if(UserAccount.getInstance().getUserBean().isBindedGoogle()) {
            jumpToActivityForResult(UnbindGoogleValidatorActivity.class, REQUEST_BIND_GOOGLE);
        }else{
            jumpToActivityForResult(GoogleValidatorActivity.class, REQUEST_BIND_GOOGLE);
        }
    }
}
