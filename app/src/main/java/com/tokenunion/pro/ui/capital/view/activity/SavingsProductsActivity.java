package com.tokenunion.pro.ui.capital.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.anypocket.pro.R;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.base.CommonViewPagerAdapter;
import com.tokenunion.pro.ui.capital.view.fragment.SavingProductFragment;
import com.tokenunion.pro.widget.PagerSlidingTabStrip;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavingsProductsActivity extends BaseActivity {

    @BindView(R.id.activity_savings_products_tabs)
    PagerSlidingTabStrip mTabView;
    @BindView(R.id.activity_savings_products_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings_products);
        ButterKnife.bind(this);

        ((TextView)findViewById(R.id.layout_common_actionbar_title)).setText(R.string.wealth_product);

        mTabView.setTextColor(Color.parseColor("#888888"));
        mTabView.setSelectedTextColor(Color.parseColor("#DE524F"));
        mTabView.setTextSize(DensityUtils.dip2px(this, 14));

        boolean isDemand = getIntent().getBooleanExtra("isDemand", true);
        List<SavingProductFragment> list = new ArrayList<>();
        Bundle bundle;
        SavingProductFragment demandFragment = new SavingProductFragment();
        bundle = new Bundle();
        bundle.putBoolean("isDemand", true);
        demandFragment.setArguments(bundle);
        list.add(demandFragment);

        demandFragment = new SavingProductFragment();
        bundle = new Bundle();
        bundle.putBoolean("isDemand", false);
        demandFragment.setArguments(bundle);
        list.add(demandFragment);

        String[] titles = {getString(R.string.is_demand), getString(R.string.is_regular)};
        CommonViewPagerAdapter<SavingProductFragment> adapter = new CommonViewPagerAdapter<>(getSupportFragmentManager(), list, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(isDemand ? 0 : 1);

        mTabView.setViewPager(mViewPager);
        mTabView.setShouldExpand(true);
        mTabView.setIndicatorMode(PagerSlidingTabStrip.IndicatorMode.FIT_TEXT);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {

    }

    public void onBackClicked(View view) {
        finish();
    }
}
