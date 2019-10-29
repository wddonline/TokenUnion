package com.tokenunion.pro.ui.wallet.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.widget.LoadView;

public class PaymentCardFragment extends BaseFragment {

    private LoadView mLoadView;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_payment_card;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {
        mLoadView = mRootView.findViewById(R.id.fragment_payment_card_load);
        mLoadView.setStatus(LoadView.LoadStatus.No_Data, getString(R.string.not_support_in_zone));

        View chargeView = mRootView.findViewById(R.id.fragment_payment_card_charge);
        chargeView.setClickable(false);
        View withdrawView = mRootView.findViewById(R.id.fragment_payment_card_withdraw);
        withdrawView.setClickable(false);
        View transferView = mRootView.findViewById(R.id.fragment_payment_card_transfer);
        transferView.setClickable(false);
        View exchangeView = mRootView.findViewById(R.id.fragment_payment_card_exchange);
        exchangeView.setClickable(false);
        View billView = mRootView.findViewById(R.id.fragment_payment_card_bill);
        billView.setClickable(false);
    }

    @Override
    protected void lazyLoad() {

    }
}
