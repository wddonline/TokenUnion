package com.tokenunion.pro.ui.capital.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.base.CommonViewPagerAdapter;
import com.tokenunion.pro.ui.capital.view.fragment.WealthRecordFragment;
import com.tokenunion.pro.widget.PagerSlidingTabStrip;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WealthRecordActivity  extends BaseActivity {

    @BindView(R.id.activity_wealth_record_tabs)
    PagerSlidingTabStrip mTabView;
    @BindView(R.id.activity_wealth_record_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wealth_record);
        ButterKnife.bind(this);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        ((TextView)findViewById(R.id.layout_common_actionbar_title)).setText(R.string.wealth_record);

        mTabView.setTextColor(Color.parseColor("#888888"));
        mTabView.setSelectedTextColor(Color.parseColor("#DE524F"));
        mTabView.setTextSize(DensityUtils.dip2px(this, 14));

        List<WealthRecordFragment> list = new ArrayList<>();
        Bundle bundle;
        WealthRecordFragment recordFragment = new WealthRecordFragment();
        bundle = new Bundle();
        bundle.putString("type", "0");
        recordFragment.setArguments(bundle);
        list.add(recordFragment);

        recordFragment = new WealthRecordFragment();
        bundle = new Bundle();
        bundle.putString("type", "1");
        recordFragment.setArguments(bundle);
        list.add(recordFragment);

        recordFragment = new WealthRecordFragment();
        bundle = new Bundle();
        bundle.putString("type", "2");
        recordFragment.setArguments(bundle);
        list.add(recordFragment);

        String[] titles = getResources().getStringArray(R.array.wealth_record_titles);
        CommonViewPagerAdapter<WealthRecordFragment> adapter = new CommonViewPagerAdapter<>(getSupportFragmentManager(), list, titles);
        mViewPager.setAdapter(adapter);

        mTabView.setViewPager(mViewPager);
        mTabView.setShouldExpand(true);
        mTabView.setIndicatorMode(PagerSlidingTabStrip.IndicatorMode.FIT_TEXT);
    }

    public void onBackClicked(View view) {
        finish();
    }

}
