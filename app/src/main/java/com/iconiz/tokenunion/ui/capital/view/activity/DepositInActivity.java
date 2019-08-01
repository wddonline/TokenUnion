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
import android.view.ViewGroup;
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

public class DepositInActivity extends BaseActivity {

    private EditText mBalanceView;
    private EditText mWealthPwdView;
    private TextView mBalanceHoldView;
    private TextView mBalanceUsableView;
    private TextView mInterestView;

    private Product mProduct;
    private String balanceHold = "0.2365";
    private String balanceHold$ = "$2223.3654";
    private String balanceUsing = "1.2365";
    private String balanceUsing$ = "$10023.3654";
    private String interest = "0.0000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_in);
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
        titleView.setText(getString(R.string.deposit) + mProduct.getName());
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        TextView complexRateView = findViewById(R.id.activity_deposit_in_complex_rate);
        complexRateView.setText("18.0 %");
        TextView increaseRateView = findViewById(R.id.activity_deposit_in_complex_increase_rate);
        increaseRateView.setText("+1.5%");

        SmartRefreshLayout refreshLayout = findViewById(R.id.activity_deposit_in_refresh);
        refreshLayout.setRefreshFooter(new FalsifyFooter(this));
        ViewGroup spotsView = findViewById(R.id.activity_deposit_in_spots);
        if (mProduct.getSpots() == null) {
            spotsView.setVisibility(View.GONE);
        } else {
            spotsView.setVisibility(View.VISIBLE);
            for (int i = 0; i < spotsView.getChildCount(); i++) {
                if (i < mProduct.getSpots().length) {
                    spotsView.getChildAt(i).setVisibility(View.VISIBLE);
                    ((TextView)spotsView.getChildAt(i)).setText(mProduct.getSpots()[i]);
                } else {
                    spotsView.getChildAt(i).setVisibility(View.GONE);
                }
            }
        }

        mBalanceView = findViewById(R.id.activity_deposit_in_balance);
        mBalanceView.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);

            }
        });
        String hintFormat = getString(R.string.deposit_input_hint);
        mBalanceView.setHint(String.format(hintFormat, mProduct.getRate(), mProduct.getName()));
        mWealthPwdView = findViewById(R.id.activity_deposit_in_wealth_pwd);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#DE524F"));

        mBalanceHoldView = findViewById(R.id.activity_deposit_in_balance_hold);
        String text = getString(R.string.deposit_hold_wealth);
        text = String.format(text, balanceHold, mProduct.getName(), balanceHold$);
        SpannableString span = new SpannableString(text);
        span.setSpan(colorSpan, text.indexOf(' '), text.lastIndexOf('（'), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mBalanceHoldView.setText(span);

        mBalanceUsableView = findViewById(R.id.activity_deposit_in_balance_usable);
        text = getString(R.string.deposit_usable_wealth);
        text = String.format(text, balanceUsing, mProduct.getName(), balanceUsing$);
        span = new SpannableString(text);
        span.setSpan(colorSpan, text.indexOf(' '), text.lastIndexOf('（'), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mBalanceUsableView.setText(span);

        mInterestView = findViewById(R.id.activity_deposit_in_month_interest);
        text = getString(R.string.deposit_month_interest);
        text = String.format(text, interest);
        span = new SpannableString(text);
        span.setSpan(colorSpan, text.indexOf(' '), text.lastIndexOf(' '), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mInterestView.setText(span);

        TextView pwdHintView = findViewById(R.id.activity_deposit_in_pwd_hint);
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

    public void onDepositClicked(View view) {
        ToastUtils.showToast(this, R.string.deposit_save_success);
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
