package com.tokenunion.pro.ui.find;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tokenunion.pro.config.Configs;
import com.anypocket.pro.R;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.ui.mine.view.activity.MessageActivity;
import com.tokenunion.pro.ui.web.activity.WebActivity;
import com.tokenunion.pro.utils.AppUtils;
import com.tokenunion.pro.utils.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FindHomeFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager mViewPager;
    private ViewGroup mIndicatorView;
    private ImageView mHintView;
    private TextView mHintSignView;

    private int mCurrentPosition = 0;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_find_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        mViewPager = mRootView.findViewById(R.id.fragment_find_home_pager);
        mIndicatorView = mRootView.findViewById(R.id.fragment_find_home_indicator);
        mHintView = mRootView.findViewById(R.id.fragment_find_home_hint);
        mHintView.setOnClickListener(this);
        mHintSignView = mRootView.findViewById(R.id.fragment_find_home_hint_sign);
        if(hasNewMessage()){
            mHintSignView.setVisibility(View.VISIBLE);
        }else{
            mHintSignView.setVisibility(View.GONE);
        }

        int[] resIds = new int[2];
        resIds[0] = R.mipmap.black_card_small;
        resIds[1] = R.mipmap.red_card_small;
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
        Bundle bundle = new Bundle();
        bundle.putString("title", "");//getString(R.string.privacy_policy));
        switch (v.getId()) {
            case R.id.fragment_find_home_hint:
                jumpToActivity(MessageActivity.class);
                break;

//                // dapp游戏暂不开放
//            case R.id.fragment_find_home_ll_dice:
//                bundle.putString("url", Configs.URL_GAME_DICE);
//                jumpToActivity(WebActivity.class, bundle);
//                break;
//
//            case R.id.fragment_find_home_ll_jacks:
//                bundle.putString("url", Configs.URL_GAME_JACKSORBETTER);
//                jumpToActivity(WebActivity.class, bundle);
//                break;
//
//            case R.id.fragment_find_home_ll_magic_circle:
//                bundle.putString("url", Configs.URL_GAME_MAGIC_CIRCLE);
//                jumpToActivity(WebActivity.class, bundle);
//                break;

            default:
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
            container.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "");//getString(R.string.privacy_policy));
                    bundle.putString("url", Configs.URL_OFFICIAL);
                    jumpToActivity(WebActivity.class, bundle);
                }
            });
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBusEvent(EventBusUtils.CommonEvent event) {
        switch (event.getId()){
            case EventBusUtils.EVENT_HAS_NEW_MESSAGE:
                // 有新消息
                mRootView.findViewById(R.id.fragment_find_home_hint_sign).setVisibility(View.VISIBLE);
                break;

            case EventBusUtils.EVENT_NO_NEW_MESSAGE:
                // 无新消息
                mRootView.findViewById(R.id.fragment_find_home_hint_sign).setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }
}
