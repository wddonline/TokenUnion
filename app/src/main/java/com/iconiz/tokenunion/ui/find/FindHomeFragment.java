package com.iconiz.tokenunion.ui.find;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseFragment;
import com.iconiz.tokenunion.ui.mine.view.activity.MessageActivity;
import com.yidaichu.android.common.utils.AppUtils;

public class FindHomeFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager mViewPager;
    private ViewGroup mIndicatorView;

    private int mCurrentPosition = 0;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_find_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {
        mViewPager = mRootView.findViewById(R.id.fragment_find_home_pager);
        mIndicatorView = mRootView.findViewById(R.id.fragment_find_home_indicator);

        int[] resIds = new int[3];
        resIds[0] = R.mipmap.black_card_small;
        resIds[1] = R.mipmap.red_card_small;
        resIds[2] = R.mipmap.gold_card_small;
        mIndicatorView.getChildAt(0).setSelected(true);
        BankCardAdapter adapter = new BankCardAdapter(resIds);
        mViewPager.setAdapter(adapter);
        int height = (int) (180f * AppUtils.getScreenWidth(getContext()) / 375f);
        mViewPager.getLayoutParams().height = height;
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicatorView.getChildAt(position).setSelected(true);
                mIndicatorView.getChildAt(mCurrentPosition).setSelected(false);
                mCurrentPosition = position;
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_capital_home_hint:
                jumpToActivity(MessageActivity.class);
                break;
        }
    }

    private class BankCardAdapter extends PagerAdapter {

        private ImageView[] imageViews;
        private int[] resIds;


        public BankCardAdapter(int[] resIds) {
            this.resIds = resIds;
            imageViews = new ImageView[resIds.length];
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[i] = new ImageView(getContext());
            }
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = imageViews[position];
            imageView.setImageResource(resIds[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return imageViews.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
