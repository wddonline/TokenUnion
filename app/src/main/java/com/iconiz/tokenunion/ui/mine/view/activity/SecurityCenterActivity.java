package com.iconiz.tokenunion.ui.mine.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;

public class SecurityCenterActivity extends BaseActivity {

    private final int REQUEST_CHANGE_PWD = 1000;
    private final int REQUEST_CHANGE_CAPITAL_PWD = 1001;
    private final int REQUEST_BIND_GOOGLE = 1002;

    private TextView mPwdView;
    private TextView mCapitalPwdView;
    private TextView mBindStatusView;
    private TextView mBindView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_center);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.security_center);

        TextView userNameView = findViewById(R.id.activity_security_center_username);
        userNameView.setText("EWR12334UG");
        TextView phoneView = findViewById(R.id.activity_security_center_phone);
        phoneView.setText("+86-10-85865390");
        mPwdView = findViewById(R.id.activity_security_center_pwd);
        mPwdView.setText("wwwwwwwwwwwwwww");
        mCapitalPwdView = findViewById(R.id.activity_security_center_capital_pwd);
        mCapitalPwdView.setText("wwwwwwwwwwwwwww");
        mBindStatusView = findViewById(R.id.activity_security_center_bind_status);
        mBindStatusView.setText(R.string.unbinded);
        mBindView = findViewById(R.id.activity_security_center_bind);
        mBindView.setText(R.string.bind);
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
                mBindStatusView.setText(R.string.binded);
                mBindView.setText(R.string.unbind);
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
        jumpToActivityForResult(ChangeCapitalPwdActivity.class, REQUEST_CHANGE_CAPITAL_PWD);
    }

    public void onBindGoogleClicked(View view) {
        jumpToActivityForResult(GoogleValidatorActivity.class, REQUEST_BIND_GOOGLE);
    }
}
