package com.tokenunion.pro.ui.wallet.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.anypocket.pro.R;
import com.tokenunion.pro.app.TUApplication;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.ui.wallet.model.MarketItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MarketDataFragment extends BaseFragment {
    private int mColorUp =
            TUApplication.INSTANCE.getResources().getColor(R.color.market_up_text_color);
    private int mColorDown =
            TUApplication.INSTANCE.getResources().getColor(R.color.market_down_text_color);
    private int mColorNoChange =
            TUApplication.INSTANCE.getResources().getColor(R.color.market_nochange_text_color);
    private final static String PLUS_CHAR = "+"; // 用于在上涨行情涨幅前加个+号
    private Unbinder mUnbinder;

    @BindView(R.id.item_market_ll_l)
    LinearLayout itemMarketLlL;
    @BindView(R.id.item_market_ll_m)
    LinearLayout itemMarketLlM;
    @BindView(R.id.item_market_ll_r)
    LinearLayout itemMarketLlR;

    @BindView(R.id.item_market_symbols_l)
    TextView itemMarketSymbolsL;
    @BindView(R.id.item_market_price_l)
    TextView itemMarketPriceL;
    @BindView(R.id.item_market_percent_change_l)
    TextView itemMarketPercentChangeL;
    @BindView(R.id.item_market_symbols_m)
    TextView itemMarketSymbolsM;
    @BindView(R.id.item_market_price_m)
    TextView itemMarketPriceM;
    @BindView(R.id.item_market_percent_change_m)
    TextView itemMarketPercentChangeM;
    @BindView(R.id.item_market_symbols_r)
    TextView itemMarketSymbolsR;
    @BindView(R.id.item_market_price_r)
    TextView itemMarketPriceR;
    @BindView(R.id.item_market_percent_change_r)
    TextView itemMarketPercentChangeR;

    private MarketDataViewModel mViewModel;
    private List<MarketItemBean> mList;

    public static MarketDataFragment newInstance() {
        return new MarketDataFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        showMarketData();
        return view;
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    public void setData(List<MarketItemBean> list) {
        this.mList = list;
        showMarketData();
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.market_data_fragment;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MarketDataViewModel.class);
        // TODO: Use the ViewModel
    }

    private void showMarketData() {
        if (null == mUnbinder || null == mList) {
            return;
        }
        if (mList.size() >= 1) {
            MarketItemBean bean = mList.get(0);
            itemMarketSymbolsL.setText(bean.getSymbol() + "/" + bean.getToSymbol());
            itemMarketPriceL.setText(bean.getPrice());
            itemMarketPercentChangeL.setText(bean.getPercentChange() + "%");

            Double doublePercentChange = 0d;
            try {
                doublePercentChange = Double.parseDouble(bean.getPercentChange());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (doublePercentChange > 0) {
                itemMarketPriceL.setTextColor(mColorUp);
                itemMarketPercentChangeL.setTextColor(mColorUp);
                itemMarketPercentChangeL.setText(PLUS_CHAR + bean.getPercentChange() + "%");
            } else if (doublePercentChange < 0) {
                itemMarketPriceL.setTextColor(mColorDown);
                itemMarketPercentChangeL.setTextColor(mColorDown);
            } else {
                itemMarketPriceL.setTextColor(mColorNoChange);
                itemMarketPercentChangeL.setTextColor(mColorNoChange);
            }
        }

        if (mList.size() >= 2) {
            itemMarketLlM.setVisibility(View.VISIBLE);
            MarketItemBean bean = mList.get(1);
            itemMarketSymbolsM.setText(bean.getSymbol() + "/" + bean.getToSymbol());
            itemMarketPriceM.setText(bean.getPrice());
            itemMarketPercentChangeM.setText(bean.getPercentChange() + "%");

            Double doublePercentChange = 0d;
            try {
                doublePercentChange = Double.parseDouble(bean.getPercentChange());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (doublePercentChange > 0) {
                itemMarketPriceM.setTextColor(mColorUp);
                itemMarketPercentChangeM.setTextColor(mColorUp);
                itemMarketPercentChangeM.setText(PLUS_CHAR + bean.getPercentChange() + "%");
            } else if (doublePercentChange < 0) {
                itemMarketPriceM.setTextColor(mColorDown);
                itemMarketPercentChangeM.setTextColor(mColorDown);
            } else {
                itemMarketPriceM.setTextColor(mColorNoChange);
                itemMarketPercentChangeM.setTextColor(mColorNoChange);
            }
        } else {
            itemMarketLlM.setVisibility(View.INVISIBLE);
        }

        if (mList.size() >= 3) {
            itemMarketLlR.setVisibility(View.VISIBLE);
            MarketItemBean bean = mList.get(2);
            itemMarketSymbolsR.setText(bean.getSymbol() + "/" + bean.getToSymbol());
            itemMarketPriceR.setText(bean.getPrice());
            itemMarketPercentChangeR.setText(bean.getPercentChange() + "%");

            Double doublePercentChange = 0d;
            try {
                doublePercentChange = Double.parseDouble(bean.getPercentChange());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (doublePercentChange > 0) {
                itemMarketPriceR.setTextColor(mColorUp);
                itemMarketPercentChangeR.setTextColor(mColorUp);
                itemMarketPercentChangeR.setText(PLUS_CHAR + bean.getPercentChange() + "%");
            } else if (doublePercentChange < 0) {
                itemMarketPriceR.setTextColor(mColorDown);
                itemMarketPercentChangeR.setTextColor(mColorDown);
            } else {
                itemMarketPriceR.setTextColor(mColorNoChange);
                itemMarketPercentChangeR.setTextColor(mColorNoChange);
            }
        } else {
            itemMarketLlR.setVisibility(View.INVISIBLE);
        }

    }
}
