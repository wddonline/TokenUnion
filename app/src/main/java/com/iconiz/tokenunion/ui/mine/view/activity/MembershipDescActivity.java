package com.iconiz.tokenunion.ui.mine.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.manager.model.User;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.base.CommonViewPagerAdapter;
import com.iconiz.tokenunion.ui.mine.model.MembershipLevel;
import com.iconiz.tokenunion.ui.mine.view.fragment.MembershipDescFragment;
import com.yidaichu.android.common.utils.AppUtils;
import com.yidaichu.android.common.utils.DensityUtils;
import com.yidaichu.android.common.view.tab.viewpager.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

public class MembershipDescActivity extends BaseActivity {

    private ViewPager mTabView;
    private ViewPager mViewPager;

    private List<MembershipLevel> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_desc);
        initData();
        initViews();
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        MembershipLevel level = new MembershipLevel();
        level.setLevel(User.LEVEL_YOUNG);
        level.setName(getString(R.string.level_star));
        level.setEnName("Young");
        level.setColor(Color.parseColor("#BBBBBB"));
        mData.add(level);

        level = new MembershipLevel();
        level.setLevel(User.LEVEL_PREFERRED);
        level.setName(getString(R.string.level_jade));
        level.setEnName("Preferred");
        level.setColor(Color.parseColor("#BBBBBB"));
        mData.add(level);

        level = new MembershipLevel();
        level.setLevel(User.LEVEL_ELITE);
        level.setName(getString(R.string.level_gold));
        level.setEnName("Elite");
        level.setColor(Color.parseColor("#DE524F"));
        mData.add(level);

        level = new MembershipLevel();
        level.setLevel(User.LEVEL_RESERVE);
        level.setName(getString(R.string.level_titanium));
        level.setEnName("Reserve");
        level.setColor(Color.parseColor("#DE524F"));
        mData.add(level);

        level = new MembershipLevel();
        level.setLevel(User.LEVEL_WORLD);
        level.setName(getString(R.string.level_platinum));
        level.setEnName("World");
        level.setColor(Color.parseColor("#B59962"));
        mData.add(level);

        level = new MembershipLevel();
        level.setLevel(User.LEVEL_INFINITE);
        level.setName(getString(R.string.level_diamond));
        level.setEnName("Infinite");
        level.setColor(Color.parseColor("#B59962"));
        mData.add(level);
    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.membership_desc);
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        mTabView = findViewById(R.id.activity_membership_desc_gallery);
        int height = (int) ((AppUtils.getScreenWidth(this) - DensityUtils.dip2px(this, 44)) / 2.75f);
        mTabView.getLayoutParams().height = height;
        TabAdapter tabAdapter = new TabAdapter();
        mTabView.setPageMargin(DensityUtils.dip2px(this, 8));
        mTabView.setOffscreenPageLimit(3);
        mTabView.setAdapter(tabAdapter);
        mTabView.setPageTransformer(true, new ScaleInTransformer());
        mTabView.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mViewPager.setCurrentItem(position);
            }
        });

        List<Fragment> fragments = new ArrayList<>();
        Bundle bundle;
        Fragment fragment;
        for (int i = 0; i < mData.size(); i++) {
            fragment = new MembershipDescFragment();
            bundle = new Bundle();
            bundle.putInt("level", mData.get(i).getLevel());
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        CommonViewPagerAdapter adapter = new CommonViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager = findViewById(R.id.activity_membership_desc_viewpager);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTabView.setCurrentItem(position);
            }
        });
    }

    public void onBackClicked(View view) {
        finish();
    }

    private class TabAdapter extends PagerAdapter {

        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            MembershipLevel level = mData.get(position);
            View view = getLayoutInflater().inflate(R.layout.item_membership_desc, container, false);
            TextView enNameView = view.findViewById(R.id.item_membership_desc_level_en);
            enNameView.setText(level.getEnName());
            enNameView.setTextColor(level.getColor());
            TextView prevView = view.findViewById(R.id.item_membership_desc_prev);
            TextView currentView = view.findViewById(R.id.item_membership_desc_current);
            TextView nextView = view.findViewById(R.id.item_membership_desc_next);
            if (position == 0) {
                prevView.setVisibility(View.GONE);
                nextView.setVisibility(View.VISIBLE);
                currentView.setText(level.getName());
                nextView.setText(mData.get(position + 1).getName() + " 》");
            } else if (position == mData.size() - 1) {
                prevView.setVisibility(View.VISIBLE);
                nextView.setVisibility(View.GONE);
                prevView.setText("《 " + mData.get(position - 1).getName());
                currentView.setText(level.getName());
            } else {
                prevView.setVisibility(View.VISIBLE);
                nextView.setVisibility(View.VISIBLE);
                prevView.setText("《 " + mData.get(position - 1).getName());
                currentView.setText(level.getName());
                nextView.setText(mData.get(position + 1).getName() + " 》");
            }
            container.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return view;
        }
    }

    private void setTextColor(ViewGroup viewGroup, int color) {
        View childView;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            childView = viewGroup.getChildAt(i);
            if (childView instanceof ViewGroup) {
                setTextColor((ViewGroup) childView, color);
            } else {
                if (childView instanceof TextView) {
                    ((TextView)childView).setTextColor(color);
                }
            }
        }
    }

}
