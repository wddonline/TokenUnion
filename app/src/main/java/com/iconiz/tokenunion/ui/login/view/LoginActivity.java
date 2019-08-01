package com.iconiz.tokenunion.ui.login.view;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.manager.UserManager;
import com.iconiz.tokenunion.manager.model.User;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.main.MainActivity;

public class LoginActivity extends BaseActivity {

    private EditText mPwdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        jumpToActivity(MainActivity.class);
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
}
