package com.iconiz.tokenunion.ui.mine.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.web.activity.WebActivity;
import com.yidaichu.android.common.utils.AppUtils;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.about_us);

        TextView versionView = findViewById(R.id.activity_about_us_version);
        versionView.setText("V " + AppUtils.getVersionName(this));

    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onPrivacyPolicyClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("title", getString(R.string.privacy_policy));
        bundle.putString("url", "http://www.baidu.com");
        jumpToActivity(WebActivity.class, bundle);
    }
}
