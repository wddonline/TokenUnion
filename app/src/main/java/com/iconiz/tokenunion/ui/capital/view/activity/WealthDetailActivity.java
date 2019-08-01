package com.iconiz.tokenunion.ui.capital.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.capital.adapter.CaptialDetailAdapter;
import com.iconiz.tokenunion.ui.capital.adapter.WealthDetailAdapter;
import com.iconiz.tokenunion.ui.capital.model.InterestDetail;
import com.iconiz.tokenunion.ui.capital.model.WealthCurrency;
import com.iconiz.tokenunion.widget.LineDividerDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class WealthDetailActivity extends BaseActivity {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mListView;

    private WealthDetailAdapter mAdapter;
    private List<WealthCurrency> mCurrencies;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wealth_detail);
        initData();
        initViews();
    }

    @Override
    protected void initData() {
        mCurrencies = new ArrayList<>();
        WealthCurrency currency = new WealthCurrency();
        currency.setCurrency("ANY");
        currency.setAmount("0.0000");
        currency.setBalance("0.0000 USD");
        currency.setRatio("63.0%");
        mCurrencies.add(currency);

        currency = new WealthCurrency();
        currency.setCurrency("BTC");
        currency.setAmount("0.0000");
        currency.setBalance("0.0000 USD");
        currency.setRatio("63.0%");
        mCurrencies.add(currency);

        currency = new WealthCurrency();
        currency.setCurrency("ETH");
        currency.setAmount("0.0000");
        currency.setBalance("0.0000 USD");
        currency.setRatio("63.0%");
        mCurrencies.add(currency);

        currency = new WealthCurrency();
        currency.setCurrency("EOS");
        currency.setAmount("0.0000");
        currency.setBalance("0.0000 USD");
        currency.setRatio("63.0%");
        mCurrencies.add(currency);

        currency = new WealthCurrency();
        currency.setCurrency("ADA");
        currency.setAmount("0.0000");
        currency.setBalance("0.0000 USD");
        currency.setRatio("63.0%");
        mCurrencies.add(currency);

        currency = new WealthCurrency();
        currency.setCurrency("TRX");
        currency.setAmount("0.0000");
        currency.setBalance("0.0000 USD");
        currency.setRatio("63.0%");
        mCurrencies.add(currency);

        currency = new WealthCurrency();
        currency.setCurrency("BCH");
        currency.setAmount("0.0000");
        currency.setBalance("0.0000 USD");
        currency.setRatio("63.0%");
        mCurrencies.add(currency);
    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.wealth_detail);
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        mRefreshLayout = findViewById(R.id.activity_wealth_detail_refresh);
        mListView = findViewById(R.id.activity_wealth_detail_list);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        LineDividerDecoration decoration = new LineDividerDecoration(this);
        int margin = DensityUtils.dip2px(this, 22);
        decoration.setLeftOffset(margin);
        decoration.setRightOffset(margin);
        mListView.addItemDecoration(decoration);

        mAdapter = new WealthDetailAdapter(this, mCurrencies);
        mListView.setAdapter(mAdapter);
        setTopCardViews();


    }

    private void setTopCardViews() {
        TextView totalView = findViewById(R.id.activity_wealth_detail_capital_total);
        totalView.setText("999.2356");
        TextView complexRateView = findViewById(R.id.activity_wealth_detail_complex_rate);
        complexRateView.setText("18.0");
    }

    public void onBackClicked(View view) {
        finish();
    }

}
