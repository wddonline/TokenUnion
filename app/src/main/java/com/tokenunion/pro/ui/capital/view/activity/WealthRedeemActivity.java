package com.tokenunion.pro.ui.capital.view.activity;

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

import com.tokenunion.pro.ui.capital.CapitalApi;
import com.tokenunion.pro.R;
import com.tokenunion.pro.app.TUApplication;
import com.tokenunion.pro.common.SimpleTextWatcher;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.mine.view.activity.ChangeCapitalPwdActivity;
import com.tokenunion.pro.utils.EventBusUtils;
import com.tokenunion.pro.utils.FormatUtils;
import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.Md5Utils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.GoogleCodeVerifyDlg;
import com.tokenunion.pro.widget.LoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;

import static com.tokenunion.pro.ui.capital.view.fragment.CapitalHomeFragment.BUNDLE_KEY_OVERVIEW;

/**
 * 赎回
 */
public class WealthRedeemActivity extends CapitalBaseActivity {

    private static final String TAG = WealthRedeemActivity.class.getSimpleName();
    private EditText mBalanceView;
    private TextView mBalanceHintView;
    private EditText mWealthPwdView;
    private TextView mBalanceHoldView;
    private TextView mPwdHintView;
    private TextView mBalanceUsableView;
    private LoadView mLoadView;

    private String mGoogleCode;
    /**
     * 带颜色的提示文本
     */
    private final ForegroundColorSpan mColorSpan =
            new ForegroundColorSpan(TUApplication.INSTANCE.getResources().getColor(R.color.colorAccent)); // "#CAAC89"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wealth_redeem);
        initData();
        initViews();
        requestAssetInfo(mAssetOverviewBean.getSymbol(), mAssetOverviewBean.getProdId());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        mAssetOverviewBean = getIntent().getParcelableExtra(BUNDLE_KEY_OVERVIEW);
    }

    @Override
    protected void initViews() {
        mWealthPwdView = findViewById(R.id.activity_wealth_redeem_wealth_pwd);
        mPwdHintView = findViewById(R.id.activity_wealth_redeem_pwd_hint);
        mBalanceView = findViewById(R.id.activity_wealth_redeem_balance);
        mBalanceHintView = findViewById(R.id.activity_wealth_redeem_balance_hint);
        mBalanceUsableView = findViewById(R.id.activity_wealth_redeem_balance_usable);
        mBalanceHoldView = findViewById(R.id.activity_wealth_redeem_balance_hold);
        mLoadView = findViewById(R.id.data_loading);

        String text = getString(R.string.deposit_can_redeem); // 可赎回额度
        text = String.format(text, "", "", "$" + "");
        mBalanceHoldView.setText(text);

        if (null == mAssetOverviewBean) {
            return;
        }
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(getString(R.string.redeem) + " "+mAssetOverviewBean.getSymbol());
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        SmartRefreshLayout refreshLayout = findViewById(R.id.activity_wealth_redeem_refresh);
        refreshLayout.setRefreshFooter(new FalsifyFooter(this));
        TextView quotaHintView = findViewById(R.id.activity_wealth_redeem_quota_hint);
        quotaHintView.setText(String.format(getString(R.string.quota_hold_format), mAssetOverviewBean.getSymbol()));
        TextView quotaView = findViewById(R.id.activity_wealth_redeem_quota);
        quotaView.setText(mAssetOverviewBean.getAmount()); // 持有额度（币数量）
        TextView incomeView = findViewById(R.id.activity_wealth_redeem_total_income);
        incomeView.setText(mAssetOverviewBean.getSumProfit()); // 累计收益

        mBalanceView.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);

            }
        });
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onRedeemClicked(View view) {
        if (!verifyInput()) {
            return;
        }
        showGoogleCodeDlg();
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

    @Override
    protected void updateData() {
        if (null == mAssetInfo) {
            return;
        }
        String hintFormat = getString(R.string.deposit_input_hint);
        mBalanceView.setHint(String.format(hintFormat, mAssetInfo.getMinRedeemAmount(), mAssetOverviewBean.getSymbol())); // 最小赎回

        // 手续费显示
        String redeemingTips = getString(R.string.no_interest_when_redeeming);
        String redeemingTipsFull = redeemingTips;
        if(null != mAssetInfo.getDiffFeeRate() && !mAssetInfo.getDiffFeeRate().equals(mAssetInfo.getFeeRate())) {
            // 赎回差异手续费。
            // 如果diffFeeRate == 0 or diffFeeRate == null ,则前端按现有规则显示手续费，否则显示diffFeeRate% - feeRate%，比如2%-5%
            redeemingTipsFull = redeemingTipsFull + " " + mAssetInfo.getDiffFeeRate() + "% -";
        }
        redeemingTipsFull = redeemingTipsFull+ " "+ mAssetInfo.getFeeRate()+ "%";// 手续费率

        mBalanceUsableView.setText(redeemingTipsFull);
        SpannableString span = new SpannableString(redeemingTipsFull);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CAAC89"));
        span.setSpan(colorSpan, redeemingTips.length(), redeemingTipsFull.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mBalanceUsableView.setText(span);

        String text = getString(R.string.deposit_can_redeem); // 可赎回额度
        text = String.format(text, mAssetInfo.getUsbRedeemAmount(), mAssetInfo.getSymbol(), "$" + mAssetInfo.getUsbRedeemBal());
        span = new SpannableString(text);
        span.setSpan(mColorSpan, text.indexOf(mAssetInfo.getUsbRedeemAmount()),
                text.lastIndexOf('（'), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mBalanceHoldView.setText(span);

        mPwdHintView.setMovementMethod(LinkMovementMethod.getInstance());
        text = mPwdHintView.getText().toString();
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
        mPwdHintView.setText(span);
    }

    @Override
    protected void updateOverviewData() {

    }

    private void doRedeem() {
        mLoadView.setVisibility(View.VISIBLE);
        String password = Md5Utils.MD5Encode(mWealthPwdView.getText().toString());
        CapitalApi.doFinanceRedeem(mAssetOverviewBean.getSymbol(), mAssetOverviewBean.getProdId() + "",
                mBalanceView.getText().toString(), password,
                mGoogleCode,
                mActive,
                new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(() -> {
                            ToastUtils.showToast(WealthRedeemActivity.this, R.string.redeem_back_success);
                            mLoadView.setVisibility(View.GONE);
                            finish();

                            // 赎回成功，发送一个event
                            EventBusUtils.post(new EventBusUtils.CommonEvent(EventBusUtils.EVENT_CAPITAL_OPERATOR_UPDATE));
                        });

                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(() -> {
                            ToastUtils.showToast(WealthRedeemActivity.this, errMessage);
                            mLoadView.setVisibility(View.GONE);
                        });
                    }
                });
    }

    private boolean verifyInput() {
        if (null == mAssetOverviewBean) {
            return false;
        }
        if (mBalanceView.getText().length() < 1) {
            mBalanceView.requestFocus();
            mBalanceHintView.setText(R.string.err_empty_quota);
            mBalanceHintView.setVisibility(View.VISIBLE);
            return false;
        } else {
            mBalanceHintView.setVisibility(View.GONE);
        }

        // 校验赎回的最低额,及超过最大额
        try {
            double inputBalance = Double.parseDouble(mBalanceView.getText().toString()); // 用户输入
            double min = Double.parseDouble(mAssetInfo.getMinRedeemAmount()); // 最低赎回
            double max = Double.parseDouble(mAssetInfo.getUsbRedeemAmount()); // 可赎回
            if (inputBalance < min) {
                mBalanceView.requestFocus();
                mBalanceView.selectAll();
                mBalanceHintView.setText(R.string.err_lower_min_redeem_amount);
                mBalanceHintView.setVisibility(View.VISIBLE);
                return false;
            } else {
                mBalanceHintView.setVisibility(View.GONE);
            }
            if (inputBalance > max) {
                mBalanceView.requestFocus();
                mBalanceView.selectAll();
                mBalanceHintView.setText(R.string.err_beyond_quota);
                mBalanceHintView.setVisibility(View.VISIBLE);
                return false;
            } else {
                mBalanceHintView.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtil.w(TAG, ex.getMessage());
            return false;
        }


        if (!FormatUtils.isPassword(mWealthPwdView.getText().toString())) {
            mWealthPwdView.requestFocus();
            mPwdHintView.setVisibility(View.VISIBLE);
            return false;
        } else {
            mPwdHintView.setVisibility(View.GONE);
        }
        return true;
    }

    public void onRedeemAllClicked(View view) {
        if (null != mAssetInfo) {
            mBalanceView.setText(mAssetInfo.getUsbRedeemAmount()); // 全部可赎回数量
        }
    }

    private void showGoogleCodeDlg(){
        final GoogleCodeVerifyDlg dlg = new GoogleCodeVerifyDlg(WealthRedeemActivity.this);
        dlg.show();

        dlg.setOnDialogListener(new GoogleCodeVerifyDlg.OnDialogListener() {
            @Override
            public void onCloseClick() {
                dlg.dismiss();
            }

            @Override
            public void onInputComplete() {
                mGoogleCode = dlg.getGoogleCode();
                dlg.dismiss();

                doRedeem();
            }
        });
    }
}
