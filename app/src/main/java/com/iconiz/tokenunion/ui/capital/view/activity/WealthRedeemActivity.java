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
import com.iconiz.tokenunion.common.SimpleTextWatcher;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.capital.model.Product;
import com.iconiz.tokenunion.ui.mine.view.activity.ChangeCapitalPwdActivity;
import com.iconiz.tokenunion.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;

public class WealthRedeemActivity extends BaseActivity {

    private EditText mBalanceView;
    private EditText mWealthPwdView;
    private TextView mBalanceHoldView;

    private Product mProduct;
    private String mRedeemQuota = "0.1234";
    private String mRedeemQuota$ = "$1223.3654";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wealth_redeem);
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
        titleView.setText(getString(R.string.redeem) + mProduct.getName());
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        SmartRefreshLayout refreshLayout = findViewById(R.id.activity_wealth_redeem_refresh);
        refreshLayout.setRefreshFooter(new FalsifyFooter(this));
        TextView quotaHintView = findViewById(R.id.activity_wealth_redeem_quota_hint);
        quotaHintView.setText(String.format(getString(R.string.quota_hold_format), mProduct.getName()));
        TextView quotaView = findViewById(R.id.activity_wealth_redeem_quota);
        quotaView.setText("0.2356");
        TextView incomeView = findViewById(R.id.activity_wealth_redeem_total_income);
        incomeView.setText("4589.5231");

        mBalanceView = findViewById(R.id.activity_wealth_redeem_balance);
        mBalanceView.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);

            }
        });
        String hintFormat = getString(R.string.deposit_input_hint);
        mBalanceView.setHint(String.format(hintFormat, mProduct.getRate(), mProduct.getName()));
        mWealthPwdView = findViewById(R.id.activity_wealth_redeem_wealth_pwd);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#DE524F"));

        mBalanceHoldView = findViewById(R.id.activity_wealth_redeem_balance_hold);
        String text = getString(R.string.deposit_hold_wealth);
        text = String.format(text, mRedeemQuota, mProduct.getName(), mRedeemQuota$);
        SpannableString span = new SpannableString(text);
        span.setSpan(colorSpan, text.indexOf(' '), text.lastIndexOf('（'), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mBalanceHoldView.setText(span);

        TextView pwdHintView = findViewById(R.id.activity_wealth_redeem_pwd_hint);
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
        colorSpan = new ForegroundColorSpan(Color.parseColor("#0080FF"));
        span.setSpan(colorSpan, index, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        pwdHintView.setText(span);
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onRedeemClicked(View view) {
        ToastUtils.showToast(this, R.string.redeem_back_success);
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
}
