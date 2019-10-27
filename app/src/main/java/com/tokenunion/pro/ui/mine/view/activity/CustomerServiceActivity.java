package com.tokenunion.pro.ui.mine.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.anypocket.pro.R;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.utils.AppUtils;

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

    public void onCopyServiceEmailClick(View view) {
        TextView emailView = findViewById(R.id.activity_customer_service_email);
        AppUtils.copyText(this, emailView.getText().toString());
        ToastUtils.showToast(this, R.string.input_google_code_copy_success);
    }
}
