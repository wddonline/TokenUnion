package com.tokenunion.pro.ui.find.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.base.BaseActivity;

public class SignGameActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_game);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        ((TextView)findViewById(R.id.layout_common_actionbar_title)).setText(R.string.sign_challenge);
    }

    public void onBackClicked() {
        finish();
    }
}
