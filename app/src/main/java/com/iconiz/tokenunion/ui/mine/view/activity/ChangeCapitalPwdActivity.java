package com.iconiz.tokenunion.ui.mine.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.utils.ToastUtils;

public class ChangeCapitalPwdActivity extends BaseActivity {

    private final int WAITING_DURATION = 60;

    private EditText mPwdView;
    private EditText mPwdAgainView;
    private TextView mSmscodeBtn;

    private Handler mHandler = new Handler();

    private int mRemainDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_capital_pwd);
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

        mPwdView = findViewById(R.id.activity_change_capital_pwd_pwd);
        mPwdAgainView = findViewById(R.id.activity_change_capital_pwd_pwd_again);
        mSmscodeBtn = findViewById(R.id.activity_change_capital_pwd_smscode_btn);
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
        ToastUtils.showToast(this, R.string.change_pwd_success);
        finish();
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
}
