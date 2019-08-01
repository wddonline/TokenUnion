package com.iconiz.tokenunion.ui.mine.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.login.view.LoginActivity;

public class GoogleValidatorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_validator);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.google_identify);

        TextView hintView = findViewById(R.id.activity_google_validator_hint);
        hintView.setMovementMethod(LinkMovementMethod.getInstance());
        String str = getString(R.string.google_code_miss);
        SpannableString label = new SpannableString(str);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#DE524F"));
        int start = str.indexOf('？') + 1;
        label.setSpan(clickableSpan, start, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        label.setSpan(colorSpan, start, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        hintView.setText(label);
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onLoginClicked(View view) {
        jumpToActivity(LoginActivity.class);
    }
}
