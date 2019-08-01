package com.iconiz.tokenunion.ui.invite.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseFragment;
import com.iconiz.tokenunion.ui.invite.view.activity.InviteRecordActivity;
import com.iconiz.tokenunion.utils.ToastUtils;
import com.iconiz.tokenunion.widget.NetworkImageView;
import com.yidaichu.android.common.utils.AppUtils;

public class InviteHomeFragment extends BaseFragment implements View.OnClickListener {

    private String mInviteCode = "011E3TR";
    private String mInviteUrl = "http://img3.cache.netease.com/game/2013/11/26/20131126143638f53f4.png";

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_invite_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {
        TextView recordView = mRootView.findViewById(R.id.fragment_invite_home_records);
        recordView.setOnClickListener(this);
        TextView version = mRootView.findViewById(R.id.fragment_invite_home_version);
        version.setText("V" + AppUtils.getVersionName(getContext()));

        NetworkImageView qrView = mRootView.findViewById(R.id.fragment_invite_home_qr);
        qrView.setImageUri(mInviteUrl);

        TextView inviteCodeView = mRootView.findViewById(R.id.fragment_invite_home_invite_code);
        String label = getString(R.string.my_invite_format);
        label = String.format(label, mInviteCode);
        SpannableString span = new SpannableString(label);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CAAC89"));
        span.setSpan(colorSpan, label.indexOf(' '), label.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        inviteCodeView.setText(span);

        View codeView = mRootView.findViewById(R.id.fragment_invite_home_copy_code);
        codeView.setOnClickListener(this);
        View linkView = mRootView.findViewById(R.id.fragment_invite_home_copy_link);
        linkView.setOnClickListener(this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_invite_home_records:
                jumpToActivity(InviteRecordActivity.class);
                break;
            case R.id.fragment_invite_home_copy_code:
                AppUtils.copyText(getContext(), mInviteCode);
                ToastUtils.showToast(getContext(), R.string.copy_success);
                break;
            case R.id.fragment_invite_home_copy_link:
                AppUtils.copyText(getContext(), mInviteUrl);
                ToastUtils.showToast(getContext(), R.string.copy_success);
                break;
        }
    }
}
