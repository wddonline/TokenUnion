package com.tokenunion.pro.ui.login.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.Configs;
import com.tokenunion.pro.config.UserAccount;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.login.UserApi;
import com.tokenunion.pro.ui.login.model.LoginBean;
import com.tokenunion.pro.ui.mine.view.activity.CountryChoiceActivity;
import com.tokenunion.pro.ui.web.activity.WebActivity;
import com.tokenunion.pro.utils.FormatUtils;
import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.StringUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.zxing.android.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";
    private final int WAITING_DURATION = 60;
    private final int REQUEST_SELECT_COUNTRY = 1000;
    private final int REQUEST_CODE_SCAN = 0x0000;
    private final int REQUEST_PERMISSION = 0x0001;

    @BindView(R.id.activity_register_country)
    TextView mCountryView;
    @BindView(R.id.activity_register_smscode_btn)
    TextView mSmscodeBtn;
    @BindView(R.id.activity_register_smscode)
    EditText mSmscodeView;
    @BindView(R.id.activity_register_user_licence)
    TextView mLicenceView;
    @BindView(R.id.activity_register_pwd)
    EditText mPwdView;
    @BindView(R.id.activity_register_pwd_again)
    EditText mPwdAgainView;
    @BindView(R.id.activity_register_phone_code)
    TextView mPhoneCodeView;
    @BindView(R.id.activity_register_phone)
    EditText mPhone;
    @BindView(R.id.activity_register_email)
    EditText mEmailView;
    @BindView(R.id.activity_register_username)
    EditText mUserName;
    @BindView(R.id.activity_register_user_next)
    TextView mTextViewNext;
    @BindView(R.id.activity_register_invite_code)
    EditText mInviteCodeView;
    @BindView(R.id.activity_register_loading)
    View mLoadView;

    private Handler mHandler = new Handler();

    private int mRemainDuration;
    private boolean mIsLicenseChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.register_info);

        mLicenceView.setSelected(false);
        setLicenceLabel();

    }

    private void setLicenceLabel() {
        mLicenceView.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString label = new SpannableString(getString(R.string.user_licence));
        int startPos = 0;
        startPos = label.toString().indexOf(':')+1;

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CAAC89"));

        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Bundle bundle = new Bundle();
                bundle.putString("title", getString(R.string.user_licence_title));
                bundle.putString("url", Configs.URL_USER);
                jumpToActivity(WebActivity.class, bundle);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        label.setSpan(clickSpan, startPos, label.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        label.setSpan(colorSpan, startPos, label.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mLicenceView.setText(label);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mSmsAction);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_SELECT_COUNTRY:
                mCountryView.setText(data.getStringExtra(CountryChoiceActivity.EXTRA_KEY_COUNTRY));
                String countryAbb = data.getStringExtra(CountryChoiceActivity.EXTRA_KEY_COUNTRY_ABB);
                String code = data.getStringExtra(CountryChoiceActivity.EXTRA_KEY_PHONE_CODE);
                mCountryView.setTag(countryAbb);
                mPhoneCodeView.setText(code);
                break;

            case REQUEST_CODE_SCAN:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String content = data.getStringExtra(CaptureActivity.KEY_CODEDCONTENT);
                        if(StringUtils.isEmpty(content) || !content.contains("/")) {
                            break;
                        }
                        int pos = content.lastIndexOf("/");
                        String codeStr = content.substring(pos+1);
                        mInviteCodeView.setText(codeStr);
//                        Bitmap bitmap = data.getParcelableExtra(CaptureActivity.KEY_CODEDBITMAP);
//                        ToastUtils.showToast(this, "" + codeStr);
                    }
                }
                break;

            default:
                break;
        }

    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onCountryClicked(View view) {
        jumpToActivityForResult(CountryChoiceActivity.class, REQUEST_SELECT_COUNTRY);
    }

    public void onUserLicenceClicked(View view) {
        view.setSelected(!view.isSelected());
        mIsLicenseChecked = !mIsLicenseChecked;

        mTextViewNext.setEnabled(mIsLicenseChecked);

        LogUtil.d(TAG, "mIsLicenseChecked, "+ mIsLicenseChecked);
    }

    private boolean verifySmsInput(){
        // 国家校验
        if(mCountryView.getText().toString().isEmpty()){
//            ToastUtils.showToast(this, getString(R.string.err_phone_format));
            findViewById(R.id.activity_register_country_hint).setVisibility(View.VISIBLE);
            mCountryView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_register_country_hint).setVisibility(View.GONE);
        }

        // 手机号校验
        if(!FormatUtils.isPhone(mPhone.getText().toString())){
//            ToastUtils.showToast(this, getString(R.string.err_phone_format));
            findViewById(R.id.activity_register_phone_hint).setVisibility(View.VISIBLE);
            mPhone.selectAll();
            mPhone.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_register_phone_hint).setVisibility(View.GONE);
        }
        String inviteCode = mInviteCodeView.getText().toString().trim();
        if (TextUtils.isEmpty(inviteCode)) {
            findViewById(R.id.activity_register_invite_code_hint).setVisibility(View.VISIBLE);
            mInviteCodeView.selectAll();
            mInviteCodeView.requestFocus();
            return false;
        } else {
            findViewById(R.id.activity_register_invite_code_hint).setVisibility(View.GONE);
        }
        return true;
    }

    public void onSmscodeClicked(View view) {
        if(!verifySmsInput()){
            return;
        }
        ToastUtils.showToast(this, R.string.send_smscode_hint);
        String phoneCode = mPhoneCodeView.getText().toString().trim();
        UserApi.getSmsCode(mPhone.getText().toString(), (String)mCountryView.getTag(), phoneCode, mActive,
                new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        LogUtil.d("RegisterActivity", "getSmsCode() send ok");
                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(RegisterActivity.this, errMessage);
                            }
                        });

                    }
                });

        mSmscodeBtn.setEnabled(false);
        mRemainDuration = WAITING_DURATION;
        updateSmsText();
        mHandler.postDelayed(mSmsAction, 1000);
    }

    private Runnable mSmsAction = new Runnable() {

        @Override
        public void run() {
            mRemainDuration--;
            updateSmsText();
            if (mRemainDuration > 0) {
                mHandler.postDelayed(this, 1000);
            }
        }

    };

    private void updateSmsText() {
        if (mRemainDuration == 0) {
            mSmscodeBtn.setText(R.string.get_smscode);
            mSmscodeBtn.setEnabled(true);
        } else {
            String str = getString(R.string.smscode_waitting);
            mSmscodeBtn.setText(String.format(str, mRemainDuration));
        }
    }

    public void onHidePwdAgainClicked(View view) {
        view.setSelected(!view.isSelected());
        showPassword(mPwdAgainView, view.isSelected());
    }

    public void onHidePwdClicked(View view) {
        view.setSelected(!view.isSelected());
        showPassword(mPwdView, view.isSelected());
    }


    private void showPassword(EditText editText, boolean showed) {
        int inputType;
        if (showed) {
            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
        } else {
            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        }
        editText.setInputType(inputType);
        editText.setSelection(editText.getText().length());
    }

    /**
     * 调起相机扫描邀请码
     * @param view
     */
    public void onCameraClicked(View view) {
        checkCarmeraPermission();
    }

    /**
     * 检查输入
     * @return
     */
    private boolean verifyInput(){
        // 国家校验
        if(mCountryView.getText().toString().isEmpty()){
//            ToastUtils.showToast(this, getString(R.string.err_phone_format));
            findViewById(R.id.activity_register_country_hint).setVisibility(View.VISIBLE);
            mCountryView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_register_country_hint).setVisibility(View.GONE);
        }

        // 用户名校验
        String userName = mUserName.getText().toString();
        if(!FormatUtils.isUserName(userName)){
//            ToastUtils.showToast(this, getString(R.string.err_username_format));
            findViewById(R.id.activity_register_username_hint).setVisibility(View.VISIBLE);

            mUserName.selectAll();
            mUserName.requestFocus();
            return false;
        }else{
            findViewById(R.id.activity_register_username_hint).setVisibility(View.GONE);
        }

        // 手机号校验
        if(!FormatUtils.isPhone(mPhone.getText().toString())){
//            ToastUtils.showToast(this, getString(R.string.err_phone_format));
            findViewById(R.id.activity_register_phone_hint).setVisibility(View.VISIBLE);
            mPhone.selectAll();
            mPhone.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_register_phone_hint).setVisibility(View.GONE);
        }

        String smscode = mSmscodeView.getText().toString();
        // 验证码校验
        if(smscode.isEmpty()){
            findViewById(R.id.activity_register_smscode_hint).setVisibility(View.VISIBLE);
            mSmscodeView.selectAll();
            mSmscodeView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_register_smscode_hint).setVisibility(View.GONE);
        }

        // 密码校验
        if(!FormatUtils.isPassword(mPwdView.getText().toString())){
            findViewById(R.id.activity_register_pwd_hint).setVisibility(View.VISIBLE);

            mPwdView.selectAll();
            mPwdView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_register_pwd_hint).setVisibility(View.GONE);
        }

        // 密码一致性校验
        String password = mPwdView.getText().toString();
        if(!password.equals(mPwdAgainView.getText().toString())){
//            ToastUtils.showToast(this, getString(R.string.err_pwd_not_consistency));
            findViewById(R.id.activity_register_pwd_again_hint).setVisibility(View.VISIBLE);

            mPwdAgainView.selectAll();
            mPwdAgainView.requestFocus();
            return false;
        }else{
            findViewById(R.id.activity_register_pwd_again_hint).setVisibility(View.GONE);
        }

        String inviteCode = mInviteCodeView.getText().toString().trim();
        // 邀请码校验
        if(TextUtils.isEmpty(inviteCode)){
            findViewById(R.id.activity_register_invite_code_hint).setVisibility(View.VISIBLE);
            mInviteCodeView.selectAll();
            mInviteCodeView.requestFocus();
            return false;
        }else {
            findViewById(R.id.activity_register_invite_code_hint).setVisibility(View.GONE);
        }

        // 同意协议校验
        if(!mIsLicenseChecked){
            // ToastUtils.showToast(this, "未同意协议");
            findViewById(R.id.activity_register_licence_hint).setVisibility(View.VISIBLE);
            return false;
        }else {
            findViewById(R.id.activity_register_licence_hint).setVisibility(View.INVISIBLE);
        }
        return true;
    }

    /**
     * 执行注册请求
     * @param view
     */
    public void onRegisterClicked(View view) {
        if(!verifyInput()){
            return;
        }
        String pwd = mPwdAgainView.getText().toString();

        mLoadView.setVisibility(View.VISIBLE);

        // 请求signin接口
        String userName = mUserName.getText().toString();
        String phone = mPhone.getText().toString();
        String smscode = mSmscodeView.getText().toString();
        String inviteCode = mInviteCodeView.getText().toString();
        String countryAbb = (mCountryView.getTag() == null) ? "" : mCountryView.getTag().toString();
        String email = mEmailView.getText().toString().trim();

        UserApi.signIn(countryAbb, email, userName, phone, smscode, pwd, inviteCode, mActive,
                new ApiRequestCallback() {
                    @Override
                    public void onSuccess(final Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mLoadView.setVisibility(View.GONE);
                                LoginBean loginBean = (LoginBean)object;
                                UserAccount.getInstance().setUserBean(loginBean);

                                Bundle bundle = new Bundle();
                                bundle.putBoolean(SafetySettingsActivity.KEY_REGIST, true); // 加参数
                                jumpToActivity(SafetySettingsActivity.class, bundle);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mLoadView.setVisibility(View.GONE);
                                ToastUtils.showToast(RegisterActivity.this, errMessage);
                            }
                        });
                    }
                });
    }
}
