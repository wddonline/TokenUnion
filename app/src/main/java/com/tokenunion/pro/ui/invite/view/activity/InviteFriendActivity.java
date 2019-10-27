package com.tokenunion.pro.ui.invite.view.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.mine.MineApi;
import com.tokenunion.pro.ui.mine.model.InviteInfoBean;
import com.tokenunion.pro.utils.BusinessUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.NetworkImageView;
import com.tokenunion.pro.utils.AppUtils;
import com.yidaichu.android.common.utils.LogUtil;

public class InviteFriendActivity extends BaseActivity {

    private static final String TAG = "InviteFriendActivity";
    private String mInviteCode;// = "011E3TR";
    private String mInviteUrl;// = "http://img3.cache.netease.com/game/2013/11/26/20131126143638f53f4.png";
    private InviteInfoBean mInviteInfoBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        initData();
        initViews();

        doRequest();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.invite_friend);

        TextView version = findViewById(R.id.activity_invite_friend_version);
        version.setText("V" + AppUtils.getVersionName(this));
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onCopyCodeClicked(View view) {
        if(null != mInviteCode) {
            AppUtils.copyText(this, mInviteCode);
            ToastUtils.showToast(this, R.string.copy_success);
        }
    }

    public void onCopyLinkClicked(View view) {
        if(null != mInviteUrl) {
            AppUtils.copyText(this, mInviteUrl+ "/"+ mInviteCode);
            ToastUtils.showToast(this, R.string.copy_success);
        }
    }

    private void updateData(){
        NetworkImageView qrView = findViewById(R.id.activity_invite_friend_qr);
        Bitmap bitmap = BusinessUtils.createQRCode(mInviteUrl + "/"+ mInviteCode);
        qrView.setImageBitmap(bitmap);

        TextView inviteCodeView = findViewById(R.id.activity_invite_friend_invite_code);
        String label = getString(R.string.my_invite_format);
        label = String.format(label, mInviteCode);
        SpannableString span = new SpannableString(label);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CAAC89"));
        span.setSpan(colorSpan, label.indexOf(':'), label.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        inviteCodeView.setText(span);

    }

    private void doRequest(){
        MineApi.getUserInviteInfo(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                try {
                    mInviteInfoBean = (InviteInfoBean) object;
                    mInviteCode = mInviteInfoBean.getInviteCode();
                    mInviteUrl = mInviteInfoBean.getInviteUrl();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateData();
                        }
                    });
                }
                catch (Exception ex){
                    ex.printStackTrace();
                    LogUtil.e(TAG, ex.getMessage());
                }
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(InviteFriendActivity.this, errMessage);
                    }
                });

            }
        });
    }

}
