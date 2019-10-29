package com.tokenunion.pro.ui.mine.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.login.view.LanguageChoiceActivity;

public class SystemSettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_settings);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.system_setting);
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onLanguageChoiceClicked(View view) {
        jumpToActivity(LanguageChoiceActivity.class);
    }
}
