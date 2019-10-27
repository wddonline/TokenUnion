package com.tokenunion.pro.ui.wallet.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.wallet.view.fragment.BillRecordFragment;
import com.anypocket.pro.R;
import com.tokenunion.pro.widget.PagerSlidingTabStrip;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class BillRecordActivity extends BaseActivity {

    private PagerSlidingTabStrip mTabStrip;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_record);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.bill_record);

        mTabStrip = findViewById(R.id.activity_bill_record_tabs);
        mTabStrip.setIndicatorMode(PagerSlidingTabStrip.IndicatorMode.FIXED);
        mTabStrip.setTextColor(Color.parseColor("#888888"));
        mTabStrip.setSelectedTextColor(Color.parseColor("#DE524F"));
        mTabStrip.setTextSize(DensityUtils.dip2px(this, 14));
        mTabStrip.setUnderlineColor(Color.TRANSPARENT);
        mTabStrip.setDividerColor(Color.TRANSPARENT);
        mViewPager = findViewById(R.id.activity_bill_record_viewpager);
        Bundle bundle;
        Fragment fragment;
        int[] types = {
                BillRecordFragment.TYPE_ALL,
                BillRecordFragment.TYPE_INCOME,
                BillRecordFragment.TYPE_OUTGO};
        mFragmentList.clear();
        for (int i = 0; i < types.length; i++) {
            fragment = new BillRecordFragment();
            bundle = new Bundle();
            bundle.putInt("type", types[i]);
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
        }
        String[] titles = getResources().getStringArray(R.array.bill_tabs);
        BillAdapter adapter = new BillAdapter(getSupportFragmentManager(), mFragmentList, titles);
        mViewPager = findViewById(R.id.activity_bill_record_viewpager);
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
    }

    public void onBackClicked(View view) {
        finish();
    }

    private class BillAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;
        private String[] titles;

        public BillAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
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

}
