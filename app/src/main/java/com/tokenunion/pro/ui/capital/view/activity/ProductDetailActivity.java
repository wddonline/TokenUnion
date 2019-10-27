package com.tokenunion.pro.ui.capital.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.capital.CapitalApi;
import com.tokenunion.pro.ui.capital.adapter.CaptialDetailAdapter;
import com.tokenunion.pro.ui.capital.model.AssetOverviewBean;
import com.tokenunion.pro.ui.capital.model.FinanceListBean;
import com.tokenunion.pro.ui.capital.model.ProfitListBean;
import com.tokenunion.pro.ui.capital.view.fragment.CapitalOperaterFragment;
import com.tokenunion.pro.ui.capital.view.fragment.ProfitRecordFragment;
import com.tokenunion.pro.ui.wallet.view.activity.BillRecordActivity;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LineDividerDecoration;
import com.tokenunion.pro.widget.PagerSlidingTabStrip;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import static com.tokenunion.pro.ui.capital.view.fragment.CapitalHomeFragment.BUNDLE_KEY_OVERVIEW;
import static com.tokenunion.pro.ui.capital.view.fragment.CapitalHomeFragment.BUNDLE_KEY_PRODUCT;

public class ProductDetailActivity extends BaseActivity {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mListView;
    private TextView mCurrencyView;
    private TextView mTitleView;
    private ViewGroup mSpotsView;

    private FinanceListBean mProduct;
    private CaptialDetailAdapter mAdapter;
    private ProfitListBean mRecords;
    private AssetOverviewBean mAssetOverviewBean;

    private PagerSlidingTabStrip mTabStrip;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        mProduct = getIntent().getParcelableExtra(BUNDLE_KEY_PRODUCT);
        initData();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestOverviewData();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initViews() {
        mTitleView = findViewById(R.id.layout_common_actionbar_title);
//        mTitleView.setText(R.string.product_detail);
        mCurrencyView = findViewById(R.id.activity_product_detail_currency);
        mSpotsView = findViewById(R.id.activity_product_detail_spots);

        mListView = findViewById(R.id.activity_product_detail_list);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        LineDividerDecoration decoration = new LineDividerDecoration(this);
        int margin = DensityUtils.dip2px(this, 22);
        decoration.setLeftOffset(margin);
        decoration.setRightOffset(margin);
        mListView.addItemDecoration(decoration);

        mTabStrip = findViewById(R.id.activity_product_detail_tabs);
        mTabStrip.setIndicatorMode(PagerSlidingTabStrip.IndicatorMode.FIXED);
        mTabStrip.setTextColor(Color.parseColor("#888888"));
        mTabStrip.setSelectedTextColor(Color.parseColor("#CAAC89"));
        mTabStrip.setTextSize(DensityUtils.dip2px(this, 14));
        mViewPager = findViewById(R.id.activity_product_detail_viewpager);

        Fragment fragment;
        mFragmentList.clear();
        ProfitRecordFragment profitRecordFragment = new ProfitRecordFragment();
        if(null != mProduct) {
            profitRecordFragment.setSymbol(mProduct.getSymbol());
        }
        mFragmentList.add(profitRecordFragment);
        CapitalOperaterFragment capitalOperaterFragment = CapitalOperaterFragment.newInstance(mProduct.getSymbol());
        mFragmentList.add(capitalOperaterFragment);

        String[] titles = getResources().getStringArray(R.array.product_list_tables);
        ProductPageAdapter adapter = new ProductPageAdapter(getSupportFragmentManager(), mFragmentList, titles);
        mViewPager = findViewById(R.id.activity_product_detail_viewpager);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
//                ToastUtils.showToast(BillRecordActivity.this, "onPageSelected, "+ position);
                super.onPageSelected(position);
            }
        });
        mTabStrip.setViewPager(mViewPager);
        mTabStrip.setShouldExpand(true);


        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);
    }

    private void updateTopData(){
        if (mAssetOverviewBean.getDigest() == null) {
            mSpotsView.setVisibility(View.GONE);
        } else {
            mSpotsView.setVisibility(View.VISIBLE);
            for (int i = 0; i < mSpotsView.getChildCount(); i++) {
                if (i < mAssetOverviewBean.getDigest().size()) {
                    mSpotsView.getChildAt(i).setVisibility(View.VISIBLE);
                    ((TextView)mSpotsView.getChildAt(i)).setText(mAssetOverviewBean.getDigest().get(i));
                } else {
                    mSpotsView.getChildAt(i).setVisibility(View.GONE);
                }
            }
        }

        mTitleView.setText(mAssetOverviewBean.getSymbol());

        String format = getString(R.string.quota_hold_format);
        mCurrencyView.setText(String.format(format, mAssetOverviewBean.getSymbol())); // 币种
        TextView balanceView = findViewById(R.id.activity_product_detail_balance);
        balanceView.setText(mAssetOverviewBean.getAmount()); // 持有额度
        TextView complexRateView = findViewById(R.id.activity_product_detail_complex_rate);
        complexRateView.setText(mAssetOverviewBean.getProfitRate()); // 综合收益率
//        TextView increaseRateView = findViewById(R.id.activity_product_detail_complex_increase_rate);
//        increaseRateView.setText("+1.5%");
        TextView totalView = findViewById(R.id.activity_product_detail_capital_total);
        totalView.setText(mAssetOverviewBean.getSumProfit()); // 累计收益
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onViewBillsClicked(View view) {
        jumpToActivity(BillRecordActivity.class);
    }

    public void onDepositClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_KEY_OVERVIEW, mAssetOverviewBean);
        if(verifyTradePasswordAndSet()) {
            jumpToActivity(DepositInActivity.class, bundle);
        }
    }

    public void onRedeemClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_KEY_OVERVIEW, mAssetOverviewBean);
        if(verifyTradePasswordAndSet()) {
            jumpToActivity(WealthRedeemActivity.class, bundle);
        }
    }
    private class ProductPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;
        private String[] titles;

        public ProductPageAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    /**
     * 顶部的总收益
     */
    private void requestOverviewData(){
        CapitalApi.getFinanceAssetOverview(mProduct.getSymbol(), mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mAssetOverviewBean = (AssetOverviewBean)object;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateTopData();
                    }
                });

            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(ProductDetailActivity.this, errMessage);
                    }
                });
            }
        });
    }

}
