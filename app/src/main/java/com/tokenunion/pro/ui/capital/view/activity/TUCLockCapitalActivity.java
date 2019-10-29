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

import com.tokenunion.pro.ui.capital.model.FinanceListBean;
import com.tokenunion.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.capital.CapitalApi;
import com.tokenunion.pro.ui.capital.model.FixedProductListBean;
import com.tokenunion.pro.ui.mine.view.activity.ChangeCapitalPwdActivity;
import com.tokenunion.pro.utils.Md5Utils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;

import java.util.List;

import static com.tokenunion.pro.ui.capital.view.fragment.CapitalHomeFragment.BUNDLE_KEY_PRODUCT;

public class TUCLockCapitalActivity extends CapitalBaseActivity {

    private static final String TAG = TUCLockCapitalActivity.class.getSimpleName();
    private View[] mPeriodViews;
    private EditText mWealthPwdView;
    private EditText mBalanceView;
    private TextView mPwdHintView;
    private TextView mBalanceHintView;
    private LoadView mLoadView;

    private FinanceListBean mProduct;
    private List<FixedProductListBean> mFixedProductList;

    /**
     * 当前选中的定期产品prodId
     */
    private int mCurrentProductId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuc_lock_capital);
        initData();
        initViews();

        requestOverviewData(mProduct.getSymbol());
//        requestAssetInfo(mProduct.getSymbol(), mProduct.get);
        requestFixedProductData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        mProduct = getIntent().getParcelableExtra(BUNDLE_KEY_PRODUCT);
    }


    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(mProduct.getSymbol());
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);
        mWealthPwdView = findViewById(R.id.activity_tuc_lock_capital_wealth_pwd);
        mBalanceView = findViewById(R.id.activity_tuc_lock_capital_balance);

        initPeriodViews();
        initPwdHitView();
    }

    /**
     * 初始化密码输入提示view
     */
    private void initPwdHitView(){
        mLoadView = findViewById(R.id.data_loading);
        mBalanceHintView = findViewById(R.id.activity_tuc_lock_capital_balance_hint);
        mPwdHintView = findViewById(R.id.activity_tuc_lock_capital_pwd_hint);
        mPwdHintView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = mPwdHintView.getText().toString();
        SpannableString span = new SpannableString(text);
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

    private boolean verifyInput() {
        if(mBalanceView.getText().toString().length()<1){
            mBalanceView.requestFocus();
            mBalanceHintView.setText(R.string.err_empty_depositin);
            mBalanceHintView.setVisibility(View.VISIBLE);
            return false;
        }else{
            mBalanceHintView.setVisibility(View.GONE);
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

    @Override
    protected void updateData() {
        // 此时mAssetInfo已经有值
        SmartRefreshLayout refreshLayout = findViewById(R.id.activity_tuc_lock_capital_refresh);
        refreshLayout.setRefreshFooter(new FalsifyFooter(this));

        mBalanceView.setHint(String.format(getString(R.string.deposit_input_hint),
                mAssetInfo.getMinDepositAmount(), mProduct.getSymbol())); // 最小存入

        TextView usableBalanceHintView = findViewById(R.id.activity_tuc_lock_capital_usable_balance_hint); // 可用余额
        String text = getString(R.string.any_usable_balance_format);
        text = String.format(text, mAssetInfo.getUsbAmount(), "$"+ mAssetInfo.getUsbBal());

        SpannableString span = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CAAC89"));
        span.setSpan(colorSpan, text.indexOf(mAssetInfo.getUsbAmount()), text.indexOf('（'), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        usableBalanceHintView.setText(span);
    }

    @Override
    protected void updateOverviewData() {
        // 获取到产品信息了
        requestAssetInfo(mProduct.getSymbol(), mAssetOverviewBean.getProdId());
        setTopCardViews();
    }

    private void setTopCardViews() {
        TextView quotaTitleView = findViewById(R.id.activity_tuc_lock_capital_quota_title);
        String format = getString(R.string.quota_hold_format);
        quotaTitleView.setText(String.format(format, mProduct.getSymbol()));
        TextView quotaView = findViewById(R.id.activity_tuc_lock_capital_quota_hold);
        quotaView.setText(mAssetOverviewBean.getAmount()); // 持有额度
        TextView increaseView = findViewById(R.id.activity_tuc_lock_capital_interest_added);
        increaseView.setText(mAssetOverviewBean.getSumProfit()); // 已获收益
    }

    private void initPeriodViews() {
        mPeriodViews = new View[3];
        mPeriodViews[0] = findViewById(R.id.activity_tuc_lock_capital_period_0);
        mPeriodViews[0].findViewById(R.id.activity_tuc_lock_capital_period_0_value).setTag("text_deadline");
        mPeriodViews[0].findViewById(R.id.activity_tuc_lock_capital_period_0_interest).setTag("text_profit");


        mPeriodViews[1] = findViewById(R.id.activity_tuc_lock_capital_period_1);
        mPeriodViews[1].findViewById(R.id.activity_tuc_lock_capital_period_1_value).setTag("text_deadline");
        mPeriodViews[1].findViewById(R.id.activity_tuc_lock_capital_period_1_interest).setTag("text_profit");

        mPeriodViews[2] = findViewById(R.id.activity_tuc_lock_capital_period_2);
        mPeriodViews[2].findViewById(R.id.activity_tuc_lock_capital_period_2_value).setTag("text_deadline");
        mPeriodViews[2].findViewById(R.id.activity_tuc_lock_capital_period_2_interest).setTag("text_profit");

        mPeriodViews[1].setSelected(true);
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onLockCapitalClicked(View view) {

        if(!verifyInput()){
            return;
        }
        if(mCurrentProductId<0){
            return;
        }

        mLoadView.setVisibility(View.VISIBLE);

        CapitalApi.doFinanceDeposit(mAssetOverviewBean.getSymbol(), mCurrentProductId+ "",
                mBalanceView.getText().toString(),
                Md5Utils.MD5Encode(mWealthPwdView.getText().toString()),
                mActive,
                new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(TUCLockCapitalActivity.this,
                                        TUCLockCapitalActivity.this.getString(R.string.deposit_save_success));
                                mLoadView.setVisibility(View.GONE);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(TUCLockCapitalActivity.this, errMessage);
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

    public void onPeriodClicked(View view) {
        if (view.isSelected()) return;
        if( null == mProduct){
            return;
        }

        for(int i=0; i<mPeriodViews.length; i++){
            if(mPeriodViews[i].getId() == view.getId()){
                mPeriodViews[i].setSelected(true);

                mCurrentProductId = (int)mPeriodViews[i].getTag();
            }else{
                mPeriodViews[i].setSelected(false);
            }
        }

        // 获取选中的定期产品详情
        requestAssetInfo(mProduct.getSymbol(), mCurrentProductId);
    }

    static final int MAX_COUNT = 3;
    /**
     * 跟新定期产品列表展示
     * @param list
     */
    private void updateFixProduct(List<FixedProductListBean> list){
        for(int i=0; i<list.size(); i++){
            if(i>=MAX_COUNT) {
                break;
            }
            FixedProductListBean bean = list.get(i);
            mPeriodViews[i].setVisibility(View.VISIBLE);
            TextView valueView = mPeriodViews[i].findViewWithTag("text_deadline");
            if(null != valueView) {
                valueView.setText(bean.getDeadline() + " "+ getString(R.string.period_day));
            }
            TextView interestView = mPeriodViews[i].findViewWithTag("text_profit");
            if(null !=interestView ) {
//                interestView.setText(bean.getProfitRate() + "%/"+ getString(R.string.profit_per_month));
                interestView.setText(bean.getProfitRate()+ " %");// + "%/"+ getString(R.string.profit_per_month));
            }

            mPeriodViews[i].setTag(bean.getProdId()); // 在tag中存放ProdId
            mPeriodViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPeriodClicked(view);
                }
            });
        }
        if(list.size()<MAX_COUNT) {
            // 隐藏多余的item
            for (int j = list.size(); j < MAX_COUNT; j++) {
                mPeriodViews[j].setVisibility(View.GONE);
            }
        }

        // 默认选中第一个
        if(mPeriodViews.length>0) {
            mPeriodViews[0].performClick();
        }
    }

    private void requestFixedProductData(){
        CapitalApi.getFinanceFixedProductList(mProduct.getSymbol(), mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mFixedProductList = (List<FixedProductListBean>)object;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateFixProduct(mFixedProductList);
                    }
                });
            }

            @Override
            public void onFailed(String errCode, String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(TUCLockCapitalActivity.this, errMessage);
                    }
                });

            }
        });

    }

    public void onDepositAllClicked(View view) {
        if(null != mAssetInfo){
            mBalanceView.setText(mAssetInfo.getUsbAmount()); // 钱包可用数量
        }
    }
}
