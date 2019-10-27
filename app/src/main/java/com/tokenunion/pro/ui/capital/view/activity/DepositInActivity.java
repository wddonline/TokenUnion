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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tokenunion.pro.app.TUApplication;
import com.tokenunion.pro.ui.capital.model.AssetInfo;
import com.tokenunion.pro.ui.capital.model.AssetOverviewBean;
import com.anypocket.pro.R;
import com.tokenunion.pro.common.SimpleTextWatcher;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.capital.CapitalApi;
import com.tokenunion.pro.ui.mine.view.activity.ChangeCapitalPwdActivity;
import com.tokenunion.pro.utils.EventBusUtils;
import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.Md5Utils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;

import static com.tokenunion.pro.ui.capital.view.fragment.CapitalHomeFragment.BUNDLE_KEY_OVERVIEW;

/**
 * 理财-存入
 */
public class DepositInActivity extends CapitalBaseActivity {
    private static final String TAG = DepositInActivity.class.getSimpleName();
    private EditText mBalanceView;
    private EditText mWealthPwdView;
    private TextView mBalanceHoldView;
    private TextView mBalanceUsableView;
    private TextView mInterestView;
    private TextView mTipsView;
    private TextView mPwdHintView;
    private TextView mDepositInAllView;
    private LoadView mLoadView;
    /**
     * 带颜色的提示文本
     */
    private final ForegroundColorSpan mColorSpan =
            new ForegroundColorSpan(TUApplication.INSTANCE.getResources().getColor(R.color.colorAccent));

    private AssetOverviewBean mAssetOverviewBean;
    private AssetInfo mDepositInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_in);
        initData();
        initViews();

        requestData();
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
        mWealthPwdView = findViewById(R.id.activity_deposit_in_wealth_pwd);
        mBalanceView = findViewById(R.id.activity_deposit_in_balance);
        mBalanceHoldView = findViewById(R.id.activity_deposit_in_balance_hold);
        mBalanceUsableView = findViewById(R.id.activity_deposit_in_balance_usable);
        mTipsView = findViewById(R.id.activity_deposit_in_balance_hint);
        mPwdHintView = findViewById(R.id.activity_deposit_in_pwd_hint);
        mDepositInAllView = findViewById(R.id.activity_deposit_in_all);
        mInterestView = findViewById(R.id.activity_deposit_in_month_interest);
        mLoadView = findViewById(R.id.data_loading);

        if(null == mAssetOverviewBean){
            LogUtil.e(TAG, "mAssetOverviewBean is null");
//            ToastUtils.showToast(this,"数据错误");
            return;
        }
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(getString(R.string.deposit) + " "+ mAssetOverviewBean.getSymbol()); // 币种
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        TextView complexRateView = findViewById(R.id.activity_deposit_in_complex_rate);
        complexRateView.setText(mAssetOverviewBean.getProfitRate()); // 综合收益率

        SmartRefreshLayout refreshLayout = findViewById(R.id.activity_deposit_in_refresh);
        refreshLayout.setRefreshFooter(new FalsifyFooter(this));
        ViewGroup spotsView = findViewById(R.id.activity_deposit_in_spots);
        if (mAssetOverviewBean.getDigest() == null) {
            spotsView.setVisibility(View.GONE);
        } else {
            spotsView.setVisibility(View.VISIBLE);
            for (int i = 0; i < spotsView.getChildCount(); i++) {
                if (i < mAssetOverviewBean.getDigest().size()) {
                    spotsView.getChildAt(i).setVisibility(View.VISIBLE);
                    ((TextView)spotsView.getChildAt(i)).setText(mAssetOverviewBean.getDigest().get(i));
                } else {
                    spotsView.getChildAt(i).setVisibility(View.GONE);
                }
            }
        }

        displayExpectedEarn(0);

        mBalanceView.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                String inputText = mBalanceView.getText().toString();
                if(inputText.isEmpty()){
                    displayExpectedEarn(0);
                    return;
                }

                if(null != mDepositInfo && null != mAssetOverviewBean) {
                    try {
                        double inputValue = Double.parseDouble(mBalanceView.getText().toString()); // 输入的值，"存入"数量
                        double marketPrice = Double.parseDouble(mDepositInfo.getMarketPrice()); // 行情价格，$
                        double profitPrice = Double.parseDouble(mDepositInfo.getProfitPrice()); // 收益行情价格,默认any行情
                        double profitRate = Double.parseDouble(mAssetOverviewBean.getProfitRate()); // （产品）收益率

                        // 计算"预计收益"值
                        double expectedEarn = inputValue * profitRate * marketPrice / profitPrice / 100.0f;
                        displayExpectedEarn(expectedEarn);
                    }catch (Exception ex){
                        ex.printStackTrace();
                        LogUtil.w(TAG, "Calculate expectedEarn failed! "+ ex.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 显示"预计收益"
     * @param val
     */
    private void displayExpectedEarn(double val){
        String text = getString(R.string.deposit_month_interest);
        String formatedVal = String.format("%.4f", val);
        text = String.format(text, formatedVal);
        SpannableString span = new SpannableString(text);
        span.setSpan(mColorSpan, text.indexOf(formatedVal), text.indexOf("ANY")+ "ANY".length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mInterestView.setText(span);
    }

    public void onBackClicked(View view) {
        finish();
    }

    private boolean verifyInput(){
        if(mBalanceView.getText().toString().length()<1){
            mBalanceView.requestFocus();
            mTipsView.setText(R.string.err_empty_depositin);
            mTipsView.setVisibility(View.VISIBLE);
            return false;
        }else{
            mTipsView.setVisibility(View.GONE);
        }

        // 校验最低额
        try {
            double inputVal = Double.parseDouble(mBalanceView.getText().toString()); // 用户输入数量
            double minDepositAmount = Double.parseDouble(mDepositInfo.getMinDepositAmount()); // 最小购入金额
            double max = Double.parseDouble(mDepositInfo.getUsbAmount()); // 钱包可用数量
            // 校验是否小于最低额
            if (inputVal < minDepositAmount) {
                mBalanceView.requestFocus();
                mTipsView.setText(R.string.err_lower_min_deposit_amount);
                mTipsView.setVisibility(View.VISIBLE);
                return false;
            } else {
                mTipsView.setVisibility(View.GONE);
            }
            // 校验是否大于钱包可用数量
            if(inputVal > max){
                mBalanceView.requestFocus();
                mTipsView.setText(R.string.err_beyond_usbamount);
                mTipsView.setVisibility(View.VISIBLE);
                mBalanceView.selectAll();
                return false;
            }else {
                mTipsView.setVisibility(View.GONE);
            }

        }catch (Exception ex){
            ex.printStackTrace();
            LogUtil.w(TAG, ex.getMessage()+ "");
            ToastUtils.showToast(this, ex.getMessage()+ "");
            return false;
        }

        if(mWealthPwdView.getText().toString().length()<8){
            mWealthPwdView.requestFocus();
            mPwdHintView.setVisibility(View.VISIBLE);
            return false;
        }else{
            mPwdHintView.setVisibility(View.GONE);
        }
        return true;
    }

    /**
     * "全部"存入
     * @param view
     */
    public void onDepositAllClicked(View view){
        if(null != mDepositInfo) {
            mBalanceView.setText(mDepositInfo.getUsbAmount());
        }
    }
    public void onDepositClicked(View view) {
        if(!verifyInput()){
            return;
        }

        mLoadView.setVisibility(View.VISIBLE);
        CapitalApi.doFinanceDeposit(mAssetOverviewBean.getSymbol(), mAssetOverviewBean.getProdId()+ "",
                mBalanceView.getText().toString(),
                Md5Utils.MD5Encode(mWealthPwdView.getText().toString()),
                mActive,
                new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(DepositInActivity.this,
                                        DepositInActivity.this.getString(R.string.deposit_save_success));
                                mLoadView.setVisibility(View.GONE);
                                finish();

                                // 存入成功，发送一个event
                                EventBusUtils.post(new EventBusUtils.CommonEvent(EventBusUtils.EVENT_CAPITAL_OPERATOR_UPDATE));
                            }
                        });
                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(DepositInActivity.this, errMessage);
                                mLoadView.setVisibility(View.GONE);
                            }
                        });
                    }
                });

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
    protected void updateData(){
        String hintFormat = getString(R.string.deposit_input_hint);
        mBalanceView.setHint(String.format(hintFormat, mDepositInfo.getMinDepositAmount(), mDepositInfo.getSymbol()));

        String text = getString(R.string.deposit_hold_wealth);
        text = String.format(text, mDepositInfo.getAmount(), mAssetOverviewBean.getSymbol(), "$"+mDepositInfo.getUsbAmount()); // 已持有
        SpannableString span = new SpannableString(text);
        span.setSpan(mColorSpan, text.indexOf(' '), text.lastIndexOf('（'), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mBalanceHoldView.setText(span);

        text = getString(R.string.deposit_usable_wealth);
        text = String.format(text, mDepositInfo.getUsbAmount(), mAssetOverviewBean.getSymbol(), "$"+mDepositInfo.getUsbBal()); // 可用余额
        span = new SpannableString(text);
        span.setSpan(mColorSpan, text.indexOf(mDepositInfo.getUsbAmount()),
                text.lastIndexOf('（'),
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mBalanceUsableView.setText(span);

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
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0080FF"));
        span.setSpan(colorSpan, index, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mPwdHintView.setText(span);
    }

    @Override
    protected void updateOverviewData() {

    }

    private void requestData(){
        CapitalApi.getFinanceAssetInfo(mAssetOverviewBean.getSymbol(), mAssetOverviewBean.getProdId(), mActive,
                new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mDepositInfo = (AssetInfo)object;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateData();
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(DepositInActivity.this,  errMessage);
                    }
                });

            }
        });
    }
}
