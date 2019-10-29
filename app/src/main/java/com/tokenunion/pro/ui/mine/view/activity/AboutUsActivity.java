package com.tokenunion.pro.ui.mine.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tokenunion.pro.config.Configs;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.web.activity.WebActivity;
import com.tokenunion.pro.utils.EventBusUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.utils.AppUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initData();
        initViews();
        EventBusUtils.register(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
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
        bundle.putString("url", Configs.URL_PRIVACY);
        jumpToActivity(WebActivity.class, bundle);
    }

    public void onCheckNewVersionClicked(View view) {
        checkVersion();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void noNewVersion(EventBusUtils.CommonEvent event){
        if(event.getId() == EventBusUtils.EVENT_NO_NEW_APP_VERSION) {
            ToastUtils.showToast(AboutUsActivity.this, getString(R.string.mine_app_no_version));
        }
    }
}
