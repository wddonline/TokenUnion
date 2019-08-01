package com.iconiz.tokenunion.ui.mine.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.login.view.LanguageChoiceActivity;
import com.yidaichu.android.common.utils.AppUtils;

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
