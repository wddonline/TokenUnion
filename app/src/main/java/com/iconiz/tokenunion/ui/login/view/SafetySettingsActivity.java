package com.iconiz.tokenunion.ui.login.view;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.mine.view.activity.GoogleValidatorActivity;

public class SafetySettingsActivity extends BaseActivity {

    private EditText mPwdView;
    private EditText mPwdAgainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_settings);
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
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onRegisterClicked(View view) {
        jumpToActivity(LoginActivity.class);
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
}
