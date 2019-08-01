package com.iconiz.tokenunion.ui.wallet.view.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseFragment;
import com.iconiz.tokenunion.ui.wallet.adapter.WalletHomeAdapter;
import com.iconiz.tokenunion.ui.wallet.model.Wealth;
import com.iconiz.tokenunion.ui.wallet.view.activity.BillRecordActivity;
import com.iconiz.tokenunion.ui.wallet.view.activity.WalletChargeActivity;
import com.iconiz.tokenunion.ui.wallet.view.activity.WalletExchangeActivity;
import com.iconiz.tokenunion.ui.wallet.view.activity.WalletTransferActivity;
import com.iconiz.tokenunion.ui.wallet.view.activity.WalletWithdrawActivity;
import com.iconiz.tokenunion.widget.LineDividerDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class WalletFragment extends BaseFragment implements View.OnClickListener  {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mListView;

    private WalletHomeAdapter mAdapter;
    private List<Wealth> mWealthes;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mWealthes = new ArrayList<>();

        Wealth wealth = new Wealth();
        wealth.setName("ANY");
        wealth.setBalance("7895269.5487");
        wealth.setInterest("0.0000 USD");
        mWealthes.add(wealth);

        wealth = new Wealth();
        wealth.setName("BTC");
        wealth.setBalance("0.0254");
        wealth.setInterest("0.0000 USD");
        mWealthes.add(wealth);

        wealth = new Wealth();
        wealth.setName("123.3256");
        wealth.setBalance("123.3256");
        wealth.setInterest("0.0000 USD");
        mWealthes.add(wealth);

        wealth = new Wealth();
        wealth.setName("EOS");
        wealth.setBalance("2365.1254");
        wealth.setInterest("0.0000 USD");
        mWealthes.add(wealth);

        wealth = new Wealth();
        wealth.setName("ADA");
        wealth.setBalance("2365.1254");
        wealth.setInterest("0.0000 USD");
        mWealthes.add(wealth);

        wealth = new Wealth();
        wealth.setName("TRX");
        wealth.setBalance("2365.1254");
        wealth.setInterest("0.0000 USD");
        mWealthes.add(wealth);

        wealth = new Wealth();
        wealth.setName("BCH");
        wealth.setBalance("2365.1254");
        wealth.setInterest("0.0000 USD");
        mWealthes.add(wealth);
    }

    @Override
    protected void initViews() {
        mRootView.findViewById(R.id.fragment_wallet_charge).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_wallet_withdraw).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_wallet_transfer).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_wallet_exchange).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_wallet_bill).setOnClickListener(this);

        mRefreshLayout = mRootView.findViewById(R.id.fragment_wallet_refresh);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
        mRefreshLayout.setRefreshFooter(new FalsifyFooter(getContext()));
        mListView = mRootView.findViewById(R.id.fragment_wallet_list);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        LineDividerDecoration decoration = new LineDividerDecoration(getContext());
        int margin = DensityUtils.dip2px(getContext(), 22);
        decoration.setLeftOffset(margin);
        decoration.setRightOffset(margin);
        mListView.addItemDecoration(decoration);

        mAdapter = new WalletHomeAdapter(getContext(), mWealthes);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_wallet_charge:
                jumpToActivity(WalletChargeActivity.class);
                break;
            case R.id.fragment_wallet_withdraw:
                jumpToActivity(WalletWithdrawActivity.class);
                break;
            case R.id.fragment_wallet_transfer:
                jumpToActivity(WalletTransferActivity.class);
                break;
            case R.id.fragment_wallet_exchange:
                jumpToActivity(WalletExchangeActivity.class);
                break;
            case R.id.fragment_wallet_bill:
                jumpToActivity(BillRecordActivity.class);
                break;
        }
    }

}
