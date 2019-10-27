package com.tokenunion.pro.ui.mine.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tokenunion.pro.config.Configs;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.mine.model.MembershipLevel;
import com.tokenunion.pro.ui.mine.view.fragment.MembershipDescFragment;
import com.anypocket.pro.R;
import com.tokenunion.pro.manager.model.User;
import com.tokenunion.pro.ui.base.CommonViewPagerAdapter;
import com.tokenunion.pro.utils.AppUtils;
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
        boolean isCn = ("CN".equals(Configs.getCurrentLanguage()));
        mData = new ArrayList<>();
        MembershipLevel level = new MembershipLevel();
        level.setLevel(User.LEVEL_YOUNG);
        level.setName(getString(R.string.level_star));

        if(isCn) {
            level.setEnName("Young");
        }else{
            level.setEnName(getString(R.string.level_star));//"Preferred");
        }
        level.setColor(Color.parseColor("#BBBBBB"));
        level.setBtnId(R.drawable.bg_level_rect_2);
        mData.add(level);

        level = new MembershipLevel();
        level.setLevel(User.LEVEL_PREFERRED);
        level.setName(getString(R.string.level_jade));
        if(isCn) {
            level.setEnName("Preferred");
        }else{
            level.setEnName(getString(R.string.level_jade));//"Jade");
        }
        level.setColor(Color.parseColor("#BBBBBB"));
        level.setBtnId(R.drawable.bg_level_rect_1);
        mData.add(level);

        level = new MembershipLevel();
        level.setLevel(User.LEVEL_ELITE);
        level.setName(getString(R.string.level_gold));
        if(isCn) {
            level.setEnName("Elite");
        }else{
            level.setEnName(getString(R.string.level_gold));//"Gold");
        }
        level.setColor(Color.parseColor("#DE524F"));
        level.setBtnId(R.drawable.bg_level_rect_1);
        mData.add(level);

        level = new MembershipLevel();
        level.setLevel(User.LEVEL_RESERVE);
        level.setName(getString(R.string.level_titanium));
        if(isCn) {
            level.setEnName("Reserve");
        }else {
            level.setEnName(getString(R.string.level_titanium));
        }
        level.setColor(Color.parseColor("#DE524F"));
        level.setBtnId(R.drawable.bg_level_rect_3);
        mData.add(level);

        level = new MembershipLevel();
        level.setLevel(User.LEVEL_WORLD);
        level.setName(getString(R.string.level_platinum));
        if(isCn) {
            level.setEnName("World");
        }else {
            level.setEnName(getString(R.string.level_platinum));
        }
        level.setColor(Color.parseColor("#B59962"));
        level.setBtnId(R.drawable.bg_level_rect_3);
        mData.add(level);

        level = new MembershipLevel();
        level.setLevel(User.LEVEL_INFINITE);
        level.setName(getString(R.string.level_diamond));
        if(isCn) {
            level.setEnName("Infinite");
        }else {
            level.setEnName(getString(R.string.level_diamond));//"Diamond");
        }
        level.setColor(Color.parseColor("#B59962"));
        level.setBtnId(R.drawable.bg_level_rect_4);
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

        private ImageView mImageViewTopLogo;
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
            ImageView topView = view.findViewById(R.id.item_membership_desc_bg);
            topView.setImageResource(getResources().getIdentifier("bg_membership_desc_" + position, "mipmap", getPackageName()));
            TextView nameView = view.findViewById(R.id.item_membership_desc_level);
            nameView.setText(level.getName());
            nameView.setBackgroundResource(level.getBtnId());

            // 斜体
            AppUtils.setItalicFont(nameView);

            TextView enNameView = view.findViewById(R.id.item_membership_desc_level_en);
            enNameView.setText(level.getEnName());

            // 斜体
            AppUtils.setItalicFont(enNameView);

            ViewGroup rootView = view.findViewById(R.id.item_membership_desc_root);
            setTextColor(rootView, level.getColor());
            container.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            mImageViewTopLogo = findViewById(R.id.item_membership_top_logo);
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
