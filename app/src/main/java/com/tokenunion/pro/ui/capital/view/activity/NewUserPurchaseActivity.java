package com.tokenunion.pro.ui.capital.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.capital.CapitalApi;
import com.tokenunion.pro.ui.capital.model.FinanceInfoBean;
import com.tokenunion.pro.ui.capital.model.NoviceCommonBean;
import com.tokenunion.pro.ui.capital.model.ProfitParticularsListBean;
import com.tokenunion.pro.ui.wallet.WalletApi;
import com.tokenunion.pro.ui.wallet.model.TradeBalanceBean;
import com.tokenunion.pro.ui.wallet.model.TradeBalanceBean.SymbolBalsBean;
import com.tokenunion.pro.utils.FormatUtils;
import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.NetRequestUtils;
import com.tokenunion.pro.utils.StringUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewUserPurchaseActivity extends BaseActivity {
    private static final String TAG = NewUserPurchaseActivity.class.getSimpleName();
    private static final double PROFIT_ANY_PRICE = 0.1d; // TODO 获取any对美元的行情价格。目前固定0.1

    @BindView(R.id.layout_common_actionbar_left)
    TextView layoutCommonActionbarLeft;
    @BindView(R.id.layout_common_actionbar_title)
    TextView layoutCommonActionbarTitle;
    @BindView(R.id.layout_common_actionbar_root)
    LinearLayout layoutCommonActionbarRoot;
    @BindView(R.id.data_loading)
    LoadView dataLoading;
    @BindView(R.id.activity_new_user_purchase_product_list)
    RecyclerView activityNewUserPurchaseProductList;
    @BindView(R.id.activity_new_user_purchase_balance)
    EditText activityNewUserPurchaseBalance;
    @BindView(R.id.activity_new_user_purchase_balance_hint)
    TextView activityNewUserPurchaseBalanceHint;
    @BindView(R.id.activity_new_user_purchase_availiable_hint)
    TextView activityNewUserPurchaseAvailiableHint;
    @BindView(R.id.activity_new_user_purchase_monthly_hint)
    TextView activityNewUserPurchaseMonthlyHint;
    @BindView(R.id.activity_new_user_purchase_expected_hint)
    TextView activityNewUserPurchaseExpectedHint;
    @BindView(R.id.activity_new_user_purchase_wealth_pwd)
    EditText activityNewUserPurchaseWealthPwd;
    @BindView(R.id.activity_new_user_purchase_pwd_hint)
    TextView activityNewUserPurchasePwdHint;
    @BindView(R.id.activity_new_user_purchase_refresh)
    SmartRefreshLayout activityNewUserPurchaseRefresh;

    @BindView(R.id.activity_new_user_purchase_bounds)
    TextView activityNewUserPurchaseBounds;
    @BindView(R.id.activity_new_user_purchase_tag_welcome)
    TextView activityNewUserPurchaseTagWelcome;
    @BindView(R.id.activity_new_user_purchase_tag_fee)
    TextView activityNewUserPurchaseTagFee;


    private EasyRVAdapter<NoviceCommonBean> mEasyRVAdapter;
    // 产品列表（接口数据）
    private List<NoviceCommonBean> mProductList;
    // 当前选中的product
    private NoviceCommonBean mCurrentNoviceCommonBean;

    // 顶部的tags TextView
    private List<TextView> mListTextviewTags = new ArrayList<>();

    // 钱包资产及市场行情价格
//    private SymbolBalsBean mSymbolBalsBean;

    // 产品列表的View（是item最外层的LinearLayout）
    private List<View> mProductViewList = new ArrayList<>();

    // 收益利率规则
    private ProfitParticularsListBean mProfitParticularsListBean;

    // 当前持有的理财资产
    private FinanceInfoBean mFinanceInfoBean;
    private Map<String, SymbolBalsBean> mMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_purchase);
        ButterKnife.bind(this);

        activityNewUserPurchaseProductList.setLayoutManager(new LinearLayoutManager(this));//, LinearLayoutManager.VERTICAL, false));
        activityNewUserPurchaseProductList.setHasFixedSize(true);
        activityNewUserPurchaseProductList.setNestedScrollingEnabled(false);

        // 收益规则
        getProfitRule();
        // 产品列表
        requestProductData();
        // 获取持有的理财数量
        requestTotalFinanceData();

        initData();
        initViews();
    }

    @Override
    protected void initData() {
        // 初始值
        String strAny = String.format(getString(R.string.new_user_expected_format),
//                            "4562.3654", "ANY", "10023.3654"));
                FormatUtils.effectiveNum(String.valueOf(0), 4),
                "ANY",
                FormatUtils.effectiveNum(String.valueOf(0), 4));
        activityNewUserPurchaseExpectedHint.setText(strAny);
        changeColor(activityNewUserPurchaseExpectedHint, strAny,
                strAny.indexOf(FormatUtils.effectiveNum("0", 4)),
                strAny.indexOf("ANY") + 3);

        // Monthly Yield 8% + 5% Bonus
        String strMonthlyYield = String.format(getString(R.string.new_user_monthly_format),
                0 + "%", "0" + "%");
        activityNewUserPurchaseMonthlyHint.setText(strMonthlyYield);

        changeColor(activityNewUserPurchaseMonthlyHint, strMonthlyYield,
                strMonthlyYield.indexOf(0 + "%"),
                strMonthlyYield.indexOf("+ " + "0" + "%") +
                        "".length() + 3);


        // Available Balance 0.3654 BTC（$3002.3654）
        String strAvailiable = String.format(getString(R.string.new_user_availiable_balance_format),
                "0", "BTC", "0");
        activityNewUserPurchaseAvailiableHint.setText(strAvailiable);
        changeColor(activityNewUserPurchaseAvailiableHint, strAvailiable,
                strAvailiable.indexOf("0"),
                strAvailiable.indexOf("BTC") + "BTC".length());

        // 输入框
        // More than %1$s %2$s
        String hintFormat = getString(R.string.deposit_input_hint);
        activityNewUserPurchaseBalance.setHint(String.format(hintFormat,
                "0",
                "BTC"));
    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(getString(R.string.profit_new_user_holdings_title));
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        // tags的TextView
        mListTextviewTags.add(activityNewUserPurchaseTagWelcome);
        mListTextviewTags.add(activityNewUserPurchaseTagFee);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtil.i(TAG, "afterTextChanged, " + s.toString());
                updateProductData();
            }
        };
        activityNewUserPurchaseBalance.addTextChangedListener(watcher);
    }

    public void onBackClicked(View view) {
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
        activityNewUserPurchaseWealthPwd.setInputType(inputType);
        activityNewUserPurchaseWealthPwd.setSelection(activityNewUserPurchaseWealthPwd.getText().length());
    }

    private void displayProductList() {
        mProductViewList.clear();
        mEasyRVAdapter = new EasyRVAdapter<NoviceCommonBean>(this, mProductList, R.layout.item_act_new_user_product) {
            @Override
            protected void onBindData(EasyRVHolder viewHolder, int position, NoviceCommonBean item) {
                viewHolder.setText(R.id.item_act_new_user_product_symbol, item.getSymbol());
                viewHolder.setText(R.id.item_act_new_user_product_period, item.getPeriodString());
                viewHolder.setText(R.id.item_act_new_user_product_start, item.getStart());
                viewHolder.setText(R.id.item_act_new_user_product_available,
                        item.getOrgCurr() + "/" + item.getOrgMax());

                mProductViewList.add(viewHolder.itemView);

//                if(position == 0) {
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            // 默认选中第一个
//                            mProductViewList.get(0).performClick();
//                        }
//                    }, 1000);
//                }

                requestTradeBalance(item.getSymbol());
            }
        };
        activityNewUserPurchaseProductList.setAdapter(mEasyRVAdapter);
        mEasyRVAdapter.setOnItemClickListener(new EasyRVAdapter.OnItemClickListener<NoviceCommonBean>() {
            @Override
            public void onItemClick(View view, int position, NoviceCommonBean item) {
//                ToastUtils.showToast(NewUserPurchaseActivity.this, item.getSymbol());
                for (int i = 0; i < mProductViewList.size(); i++) {
                    mProductViewList.get(i).setSelected(false);
                }
                view.setSelected(true);

                mCurrentNoviceCommonBean = item;

                updateProductData();

                activityNewUserPurchaseBounds.setText(item.getBonusString());
            }
        });
    }

    /**
     * 显示可用资产。 需要已经获取到
     */
    private void displayAvailiableData() {
        if (null == mCurrentNoviceCommonBean) {
            LogUtil.w(TAG, "mCurrentNoviceCommonBean is null ");
            return;
        }
        String symbol = mCurrentNoviceCommonBean.getSymbol();
        if (!mMap.containsKey(symbol)) {
            LogUtil.w(TAG, "mMap not contains symbol: " + symbol);
            return;
        }
        SymbolBalsBean symbolBalsBean = mMap.get(symbol);
        // 显示可用资产
        // Available Balance 0.3654 BTC（$3002.3654）
        String strAvailiable = String.format(getString(R.string.new_user_availiable_balance_format),
                symbolBalsBean.getAmount(), symbol, symbolBalsBean.getBal());
        activityNewUserPurchaseAvailiableHint.setText(strAvailiable);
        changeColor(activityNewUserPurchaseAvailiableHint, strAvailiable,
                strAvailiable.indexOf(symbolBalsBean.getAmount()),
                strAvailiable.indexOf(symbol) + symbol.length());
    }

    /**
     * 显示预期收益
     *
     * @param expectedDollar 美元收益
     * @param expectedAny    any收益
     */
    private void displayExpectedProfit(double expectedDollar, double expectedAny) {
        if (null == mFinanceInfoBean || null == mCurrentNoviceCommonBean) {
            return;
        }
        if (null == mProfitParticularsListBean) {
            // 获取收益规则
            getProfitRule();
        }

        String strAny = String.format(getString(R.string.new_user_expected_format),
                FormatUtils.effectiveNum(String.valueOf(expectedAny), 4),
                "ANY",
                FormatUtils.effectiveNum(String.valueOf(expectedDollar), 4));
        activityNewUserPurchaseExpectedHint.setText(strAny);
        changeColor(activityNewUserPurchaseExpectedHint, strAny,
                strAny.indexOf(FormatUtils.effectiveNum(String.valueOf(expectedAny), 4)),
                strAny.indexOf("ANY") + 3);
    }

    private void displayMonthlyProfitRate(double baseRate, String boundStr) {
        // Monthly Yield 8% + 5% Bonus
        String strMonthlyYield = String.format(getString(R.string.new_user_monthly_format),
                baseRate + "%", mCurrentNoviceCommonBean.getBonusString() + "%");
        activityNewUserPurchaseMonthlyHint.setText(strMonthlyYield);

        changeColor(activityNewUserPurchaseMonthlyHint, strMonthlyYield,
                strMonthlyYield.indexOf(baseRate + "%"),
                strMonthlyYield.indexOf("+ " + boundStr + "%") +
                        boundStr.length() + 3);
    }

    /**
     * 显示产品的详细信息：可用余额、月利率、预期收益
     */
    private void updateProductData() {
        displayAvailiableData();

        if (null == mFinanceInfoBean) {
            return;
        }

        ///// 预期收益
        double expectProfitRate = getCurrentProfitRate(mFinanceInfoBean.getTotalBal());
        if (null == mProfitParticularsListBean) {
            // 获取收益规则
            getProfitRule();
        } else {
            // Expected Profit 4562.3654 ANY（$10023.3654）
            // 计算预期收益
            double expectedProfit = calculateExpectProfit(expectProfitRate); // 预期美元收益
            double anyCount = expectedProfit / PROFIT_ANY_PRICE; // 转化成any的数量

            displayExpectedProfit(expectedProfit, anyCount);
        }

        if (null == mCurrentNoviceCommonBean) {
            return;
        }
        ///// 月利率
        displayMonthlyProfitRate(expectProfitRate, mCurrentNoviceCommonBean.getBonusString());

        // 显示顶部的Bounds
        activityNewUserPurchaseBounds.setText(mCurrentNoviceCommonBean.getBonusString());

        // 输入框hint
        String hintFormat = getString(R.string.deposit_input_hint);
        activityNewUserPurchaseBalance.setHint(String.format(hintFormat,
                mCurrentNoviceCommonBean.getStart(), mCurrentNoviceCommonBean.getSymbol()));

    }

    private void changeColor(TextView textView, String strData, int start, int end) {
        SpannableString span = new SpannableString(strData);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CAAC89"));
        span.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(span);
    }

    /**
     * 根据当前持有的理财资产+即将购买的理财，获得预期收益率
     *
     * @param currentHold
     * @return 如果未获取到，会返回0
     */
    private double getCurrentProfitRate(String currentHold) {
        Double hold = 0d;
        try {
            hold = Double.parseDouble(currentHold);
        } catch (Exception ex) {
            LogUtil.e(TAG, "getCurrentProfitRate() failed! " + ex.getMessage());
            ex.printStackTrace();
        }
        Double inputVal = 0d;
        try {
            inputVal = Double.parseDouble(activityNewUserPurchaseBalance.getText().toString());
        } catch (Exception ex) {
            LogUtil.w(TAG, "getCurrentProfitRate(), input error");
        }

        Double marketPrice = 0d;
        try {
            marketPrice = Double.parseDouble(mMap.get(mCurrentNoviceCommonBean.getSymbol()).getMarketPrice());
        } catch (Exception ex) {
            LogUtil.w(TAG, "marketPrice parse failed.");
            ex.printStackTrace();
            return 0;
        }

        // 已持有的+(将购买的 * 行情价格)
        Double totalVal = hold + (inputVal * marketPrice);
        if (null == mProfitParticularsListBean) {
            return 0;
        }
        Double minVal, maxVal, resultRate;
        int levelCount = mProfitParticularsListBean.getProfitDetails().size();
        // 根据总理财资产，检测落在的收益率区间，得到收益率
        for (int i = 0; i < levelCount; i++) {
            ProfitParticularsListBean.ProfitDetailsBean detail = mProfitParticularsListBean.getProfitDetails().get(i);
            if (i != (levelCount - 1)) {
                minVal = Double.parseDouble(detail.getFloorBal());
                maxVal = Double.parseDouble(detail.getCapBal());
                if (totalVal > minVal && totalVal <= maxVal) {
                    resultRate = Double.parseDouble(detail.getProfitRate());
                    return resultRate;
                }
            } else {
                minVal = Double.parseDouble(detail.getFloorBal());
                if (totalVal > minVal) {
                    resultRate = Double.parseDouble(detail.getProfitRate());
                    return resultRate;
                }
            }
        }
        return 0;
    }

    /**
     * 根据收益率，计算预期收益（$）
     *
     * @param expectProfitRate
     * @return
     */
    private double calculateExpectProfit(double expectProfitRate) {
        Double inputVal = 0d;
        try {
            inputVal = Double.parseDouble(activityNewUserPurchaseBalance.getText().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtil.w(TAG, "input error. " + ex.getMessage());
        }

        Double boundRate = 0d;
        try {
            boundRate = Double.parseDouble(mCurrentNoviceCommonBean.getBonus());
        } catch (Exception ex) {
            LogUtil.w(TAG, "bound error. " + ex.getMessage());
            ex.printStackTrace();
        }

        Double marketPrice = 0d;
        try {
            marketPrice = Double.parseDouble(mMap.get(mCurrentNoviceCommonBean.getSymbol()).getMarketPrice());
        } catch (Exception ex) {
            LogUtil.w(TAG, "marketPrice parse failed.");
            ex.printStackTrace();
            return 0;
        }
        // 预期收益（$） = 输入的数量 * （基本收益率+ 新手收益率）* 行情价格 * 产品天数 / 30
        double expectProfit = inputVal * (expectProfitRate / 100.0d + boundRate) * marketPrice
                * mCurrentNoviceCommonBean.getPeriod() / 30.0d;

        return expectProfit;
    }

    /**
     * 显示顶部的tags，比如"Welcome、0 Fee"
     *
     * @param tagDes
     */
    private void showTags(String tagDes) {
        if (StringUtils.isEmpty(tagDes)) {
            return;
        }
        String[] tags = tagDes.split(",");

        for (int i = 0; i < tags.length; i++) {
            if (i >= mListTextviewTags.size()) {
                break;
            }
            mListTextviewTags.get(i).setVisibility(View.VISIBLE);
            mListTextviewTags.get(i).setText(tags[i]);
        }
    }

    /**
     * 获取币种的可用余额
     */
    private void requestTradeBalance(String symbol) {
        if (!NetRequestUtils.isNetworkConnected(NewUserPurchaseActivity.this)) {
            return;
        }
        WalletApi.getTradeBalance(symbol, mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                final TradeBalanceBean tradeBalanceBean = (TradeBalanceBean) object;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!tradeBalanceBean.getSymbolBals().isEmpty()) {
                            SymbolBalsBean symbolBalsBean = tradeBalanceBean.getSymbolBals().get(0);

                            synchronized (mMap) {
                                mMap.put(symbol, symbolBalsBean);

                                if (!mProductViewList.isEmpty()) {
                                    // 没选中产品则默认选中第一个
                                    if (null == mCurrentNoviceCommonBean) {
                                        mProductViewList.get(0).performClick();
                                    }
                                }
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(NewUserPurchaseActivity.this, errMessage);
                    }
                });
            }
        });
    }

    /**
     * 获取已持有理财数量
     */
    private void requestTotalFinanceData() {
        if (!NetRequestUtils.isNetworkConnected(NewUserPurchaseActivity.this)) {
            return;
        }
        CapitalApi.getFinanceInfo("", mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                final FinanceInfoBean bean = (FinanceInfoBean) object;
                mFinanceInfoBean = bean;
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(NewUserPurchaseActivity.this, errMessage);
                    }
                });
            }
        });
    }

    /**
     * 获取 新手产品列表
     */
    private void requestProductData() {
        CapitalApi.getNoviceCommon(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mProductList = (List<NoviceCommonBean>) object;
                if (null != mProductList && !mProductList.isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            displayProductList();

                            // 顶部tags
                            showTags(mProductList.get(0).getDes());
                        }
                    });
                }
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(NewUserPurchaseActivity.this, errMessage);
                    }
                });
            }
        });
    }

    /**
     * 获取收益利率规则
     */
    private void getProfitRule() {
        CapitalApi.getFinanceProfitParticularsList(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mProfitParticularsListBean = (ProfitParticularsListBean) object;
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(NewUserPurchaseActivity.this, errMessage);
                    }
                });
            }
        });
    }

    private void commitPurchase() {
        if (null == mCurrentNoviceCommonBean) {
            return;
        }

        CapitalApi.doNoviceDeposit(activityNewUserPurchaseBalance.getText().toString(),
                mCurrentNoviceCommonBean.getNoviceId() + "",
                mCurrentNoviceCommonBean.getSymbol(),
                activityNewUserPurchaseWealthPwd.getText().toString(),
                mActive, new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(NewUserPurchaseActivity.this, getString(R.string.submit_success));
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(NewUserPurchaseActivity.this, errMessage);
                            }
                        });
                    }
                });
    }

    private boolean verifyInput() {
        // TODO 输入检查
        if (StringUtils.isEmpty(activityNewUserPurchaseBalance.getText().toString())) {
            ToastUtils.showToast(this, getString(R.string.err_empty_depositin));
            activityNewUserPurchaseBalance.requestFocus();
            return false;
        }
        if (StringUtils.isEmpty(activityNewUserPurchaseWealthPwd.getText().toString())) {
            ToastUtils.showToast(this, "Password is empty");
            activityNewUserPurchaseWealthPwd.requestFocus();
            return false;
        }
        return true;
    }

    public void onPurchaseClicked(View view) {
        if (verifyInput()) {
            if (verifyTradePasswordAndSet()) {
                commitPurchase();
            }
        }
    }
}
