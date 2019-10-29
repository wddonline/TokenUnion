package com.tokenunion.pro.ui.wallet.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.ui.wallet.WalletApi;
import com.tokenunion.pro.ui.wallet.adapter.WalletHomeAdapter;
import com.tokenunion.pro.ui.wallet.model.TradeBalanceBean;
import com.tokenunion.pro.ui.wallet.view.activity.BillRecordActivity;
import com.tokenunion.pro.ui.wallet.view.activity.WalletChargeActivity;
import com.tokenunion.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.ui.wallet.view.activity.WalletExchangeActivity;
import com.tokenunion.pro.ui.wallet.view.activity.WalletTransferActivity;
import com.tokenunion.pro.ui.wallet.view.activity.WalletWithdrawActivity;
import com.tokenunion.pro.utils.EventBusUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LineDividerDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class WalletFragment extends BaseFragment implements View.OnClickListener  {

    public static final String KEY_ASSET_DATA_UPDATED = "Bal_value";
    private ImageView mNoDataView;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mListView;

    private WalletHomeAdapter mAdapter;
    private List<TradeBalanceBean.SymbolBalsBean> mWealthes = new ArrayList<>();
    private TradeBalanceBean mTradeBalanceBean;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initViews() {
        mRootView.findViewById(R.id.fragment_wallet_charge).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_wallet_withdraw).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_wallet_transfer).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_wallet_exchange).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_wallet_bill).setOnClickListener(this);

        mNoDataView = mRootView.findViewById(R.id.fragment_wallet_nodata);
        mRefreshLayout = mRootView.findViewById(R.id.fragment_wallet_refresh);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                doRequest();
                refreshLayout.finishRefresh();
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
    }

    @Override
    protected void lazyLoad() {
        doRequest();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_wallet_charge:
                jumpToActivity(WalletChargeActivity.class);
                break;
            case R.id.fragment_wallet_withdraw:
                if(verifyTradePasswordAndSet()) {
                    jumpToActivity(WalletWithdrawActivity.class);
                }
                break;
            case R.id.fragment_wallet_transfer:
                if(verifyTradePasswordAndSet()) {
                    jumpToActivity(WalletTransferActivity.class);
                }
                break;
            case R.id.fragment_wallet_exchange:
                if(verifyTradePasswordAndSet()) {
                    jumpToActivity(WalletExchangeActivity.class);
                }
                break;
            case R.id.fragment_wallet_bill:
                jumpToActivity(BillRecordActivity.class);
                break;
        }
    }

    /**
     * 更新列表数据
     */
    private void updateListData(){
        if(null == mTradeBalanceBean || null == mTradeBalanceBean.getSymbolBals()
                || mTradeBalanceBean.getSymbolBals().isEmpty()){
            mNoDataView.setVisibility(View.VISIBLE);
            return;
        }

        mNoDataView.setVisibility(View.GONE);
        mWealthes = mTradeBalanceBean.getSymbolBals();
        mAdapter = new WalletHomeAdapter(getContext(), mWealthes);
        mListView.setAdapter(mAdapter);
        postUpdatedEvent();
    }

    /**
     * 数据获取之后，通过event bus通知首页更新总资产
     */
    private void postUpdatedEvent(){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ASSET_DATA_UPDATED, mTradeBalanceBean.getBal());
        EventBusUtils.CommonEvent event =
                new EventBusUtils.CommonEvent(EventBusUtils.EVENT_ASSET_DATA_UPDATED, bundle);

        EventBusUtils.post(event);
    }

    private void doRequest(){
        WalletApi.getTradeBalance("", mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mTradeBalanceBean = (TradeBalanceBean) object;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateListData();
                    }
                });
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
