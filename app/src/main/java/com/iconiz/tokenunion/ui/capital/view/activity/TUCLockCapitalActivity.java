package com.iconiz.tokenunion.ui.capital.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.capital.model.Product;
import com.iconiz.tokenunion.ui.mine.view.activity.ChangeCapitalPwdActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;

public class TUCLockCapitalActivity extends BaseActivity {

    private View[] mPeriodViews;
    private EditText mWealthPwdView;

    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuc_lock_capital);
        initData();
        initViews();
    }

    @Override
    protected void initData() {
        mProduct = getIntent().getParcelableExtra("product");
    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText("ANY");
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        setTopCardViews();
        initPeriodViews();

        SmartRefreshLayout refreshLayout = findViewById(R.id.activity_tuc_lock_capital_refresh);
        refreshLayout.setRefreshFooter(new FalsifyFooter(this));

        EditText balanceView = findViewById(R.id.activity_tuc_lock_capital_balance);
        balanceView.setHint(String.format(getString(R.string.deposit_input_hint), "200235", mProduct.getName()));

        mWealthPwdView = findViewById(R.id.activity_tuc_lock_capital_wealth_pwd);
        TextView usableBalanceHintView = findViewById(R.id.activity_tuc_lock_capital_usable_balance_hint);
        String text = getString(R.string.any_usable_balance_format);
        text = String.format(text, "4562756.3654", "$10023.3654");
        SpannableString span = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#DE524F"));
        span.setSpan(colorSpan, text.indexOf(' ') + 1, text.indexOf('（'), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        usableBalanceHintView.setText(span);

        TextView pwdHintView = findViewById(R.id.activity_tuc_lock_capital_pwd_hint);
        pwdHintView.setMovementMethod(LinkMovementMethod.getInstance());
        text = pwdHintView.getText().toString();
        span = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                jumpToActivity(ChangeCapitalPwdActivity.class);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        int index = text.indexOf('，') + 1;
        span.setSpan(clickableSpan, index, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        colorSpan = new ForegroundColorSpan(Color.parseColor("#CAAC89"));
        span.setSpan(colorSpan, index, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        pwdHintView.setText(span);
    }

    private void setTopCardViews() {
        TextView quotaTitleView = findViewById(R.id.activity_tuc_lock_capital_quota_title);
        String format = getString(R.string.quota_hold_format);
        quotaTitleView.setText(String.format(format, mProduct.getName()));
        TextView quotaView = findViewById(R.id.activity_tuc_lock_capital_quota_hold);
        quotaView.setText("4589.5231");
        TextView increaseView = findViewById(R.id.activity_tuc_lock_capital_interest_added);
        increaseView.setText("0.0 %");
    }

    private void initPeriodViews() {
        mPeriodViews = new View[3];
        mPeriodViews[0] = findViewById(R.id.activity_tuc_lock_capital_period_0);
        mPeriodViews[1] = findViewById(R.id.activity_tuc_lock_capital_period_1);
        mPeriodViews[2] = findViewById(R.id.activity_tuc_lock_capital_period_2);
        mPeriodViews[1].setSelected(true);

        TextView valueView = findViewById(R.id.activity_tuc_lock_capital_period_0_value);
        valueView.setText("30 天");
        TextView interestView = findViewById(R.id.activity_tuc_lock_capital_period_0_interest);
        interestView.setText("5.0%/月");

        valueView = findViewById(R.id.activity_tuc_lock_capital_period_1_value);
        valueView.setText("90 天");
        interestView = findViewById(R.id.activity_tuc_lock_capital_period_1_interest);
        interestView.setText("7.0%/月");

        valueView = findViewById(R.id.activity_tuc_lock_capital_period_2_value);
        valueView.setText("180 天");
        interestView = findViewById(R.id.activity_tuc_lock_capital_period_2_interest);
        interestView.setText("9.0%/月");
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onLockCapitalClicked(View view) {
        finish();
    }

    public void onHidePwdClicked(View view) {
        view.setSelected(!view.isSelected());
        int inputType;
        if (view.isSelected()) {
            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
        } else {
            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        }
        mWealthPwdView.setInputType(inputType);
        mWealthPwdView.setSelection(mWealthPwdView.getText().length());
    }

    public void onPeriod0Clicked(View view) {
        if (view.isSelected()) return;
        mPeriodViews[0].setSelected(true);
        mPeriodViews[1].setSelected(false);
        mPeriodViews[2].setSelected(false);
    }

    public void onPeriod1Clicked(View view) {
        if (view.isSelected()) return;
        mPeriodViews[0].setSelected(false);
        mPeriodViews[1].setSelected(true);
        mPeriodViews[2].setSelected(false);
    }

    public void onPeriod2Clicked(View view) {
        if (view.isSelected()) return;
        mPeriodViews[0].setSelected(false);
        mPeriodViews[1].setSelected(false);
        mPeriodViews[2].setSelected(true);
    }
}
