package com.iconiz.tokenunion.ui.invite.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.utils.ToastUtils;
import com.iconiz.tokenunion.widget.NetworkImageView;
import com.yidaichu.android.common.utils.AppUtils;

public class InviteFriendActivity extends BaseActivity {

    private String mInviteCode = "011E3TR";
    private String mInviteUrl = "http://img3.cache.netease.com/game/2013/11/26/20131126143638f53f4.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.invite_friend);
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        TextView version = findViewById(R.id.activity_invite_friend_version);
        version.setText("V" + AppUtils.getVersionName(this));

        NetworkImageView qrView = findViewById(R.id.activity_invite_friend_qr);
        qrView.setImageUri(mInviteUrl);

        TextView inviteCodeView = findViewById(R.id.activity_invite_friend_invite_code);
        String label = getString(R.string.my_invite_format);
        label = String.format(label, mInviteCode);
        SpannableString span = new SpannableString(label);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CAAC89"));
        span.setSpan(colorSpan, label.indexOf(' '), label.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        inviteCodeView.setText(span);
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onCopyCodeClicked(View view) {
        AppUtils.copyText(this, mInviteCode);
        ToastUtils.showToast(this, R.string.copy_success);
    }

    public void onCopyLinkClicked(View view) {
        AppUtils.copyText(this, mInviteUrl);
        ToastUtils.showToast(this, R.string.copy_success);
    }

}
