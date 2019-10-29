package com.tokenunion.pro.ui.invite.view.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.tokenunion.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.ui.invite.view.activity.InviteRecordActivity;
import com.tokenunion.pro.ui.mine.MineApi;
import com.tokenunion.pro.ui.mine.model.InviteInfoBean;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.NetworkImageView;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.tokenunion.pro.utils.AppUtils;
import com.yidaichu.android.common.utils.LogUtil;

public class InviteHomeFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "InviteFriendActivity";
    /**
     * 展示的二维码size宽、高
     */
    private static final int QR_CODE_IMAGE_WIDTH = 200;
    private static final int QR_CODE_IMAGE_HEIGHT = 200;

    private String mInviteCode;// = "011E3TR";
    private String mInviteUrl;// = "http://img3.cache.netease.com/game/2013/11/26/20131126143638f53f4.png";
    private InviteInfoBean mInviteInfoBean;
    private TextView mInviteCodeView;

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

        View codeView = mRootView.findViewById(R.id.fragment_invite_home_copy_code);
        codeView.setOnClickListener(this);
        View linkView = mRootView.findViewById(R.id.fragment_invite_home_copy_link);
        linkView.setOnClickListener(this);

        mInviteCodeView = mRootView.findViewById(R.id.fragment_invite_home_invite_code);
        String label = getString(R.string.my_invite_format);
        label = String.format(label, ""); // 邀请码默认设置空
        mInviteCodeView.setText(label);
    }

    @Override
    protected void lazyLoad() {
        doRequest();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_invite_home_records:
                jumpToActivity(InviteRecordActivity.class);
                break;
            case R.id.fragment_invite_home_copy_code:
                if(null != mInviteCode) {
                    AppUtils.copyText(getContext(), mInviteCode);
                    ToastUtils.showToast(getContext(), R.string.copy_success);
                }
                break;
            case R.id.fragment_invite_home_copy_link:
                if(null != mInviteUrl) {
                    AppUtils.copyText(getContext(), mInviteUrl + "/" + mInviteCode);
                    ToastUtils.showToast(getContext(), R.string.copy_success);
                }
                break;
        }
    }

    private Bitmap createQRCode(String strData){
        //生成二维码
        Bitmap bitmap = CodeUtils.createImage(strData, QR_CODE_IMAGE_WIDTH, QR_CODE_IMAGE_HEIGHT, null);
        return bitmap;
    }

    private void updateData(){
        NetworkImageView qrView = mRootView.findViewById(R.id.fragment_invite_home_qr);
        Bitmap bitmap = createQRCode(mInviteUrl + "/"+ mInviteCode);
        qrView.setImageBitmap(bitmap);

        String label = getString(R.string.my_invite_format);
        label = String.format(label, mInviteCode);
        SpannableString span = new SpannableString(label);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CAAC89"));
        span.setSpan(colorSpan, label.indexOf(':'), label.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mInviteCodeView.setText(span);

    }

    private void doRequest(){
        MineApi.getUserInviteInfo(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                try {
                    mInviteInfoBean = (InviteInfoBean) object;
                    mInviteCode = mInviteInfoBean.getInviteCode();
                    mInviteUrl = mInviteInfoBean.getInviteUrl();
                    getActivity().runOnUiThread(new Runnable() {
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(getActivity(), errMessage);
                    }
                });

            }
        });
    }
}
