package com.iconiz.tokenunion.ui.mine.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;

public class CustomerServiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.customer_service);
    }

    public void onBackClicked(View view) {
        finish();
    }

}
