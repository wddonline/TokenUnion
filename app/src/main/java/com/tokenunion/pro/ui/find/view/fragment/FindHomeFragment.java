package com.tokenunion.pro.ui.find.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.ui.find.contact.FindHomePresenter;
import com.tokenunion.pro.ui.find.model.FindBanner;
import com.tokenunion.pro.ui.find.view.activity.SignGameActivity;
import com.tokenunion.pro.ui.mine.view.activity.MessageActivity;
import com.tokenunion.pro.ui.web.activity.WebActivity;
import com.tokenunion.pro.utils.EventBusUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.NetworkImageView;
import com.yidaichu.android.common.http.error.HttpError;
import com.yidaichu.android.common.utils.DensityUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FindHomeFragment extends BaseFragment implements FindHomePresenter.View {

    @BindView(R.id.fragment_find_home_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.fragment_find_home_pager)
    ViewPager mViewPager;
    @BindView(R.id.fragment_find_home_indicator)
    LinearLayout mIndicatorView;
    @BindView(R.id.fragment_find_home_hint)
    ImageView mHintView;
    @BindView(R.id.fragment_find_home_hint_sign)
    TextView mHintSignView;

    private FindHomePresenter mPresenter;
    private Unbinder mUnbinder;

    private int mCurrentPosition = 0;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_find_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, mRootView);
        EventBusUtils.register(this);
        mPresenter = new FindHomePresenter(this, mActive);
    }

    @Override
    protected void initViews() {
        if(hasNewMessage()){
            mHintSignView.setVisibility(View.VISIBLE);
        }else{
            mHintSignView.setVisibility(View.GONE);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicatorView.getChildAt(position).setSelected(true);
                mIndicatorView.getChildAt(mCurrentPosition).setSelected(false);
                mCurrentPosition = position;
            }
        });

        mRefreshLayout.setRefreshFooter(new FalsifyFooter(getContext()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getBanners();
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onDestroy() {
        EventBusUtils.unregister(this);
        mUnbinder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.fragment_find_home_hint, R.id.fragment_find_home_sign_game})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_find_home_hint:
                jumpToActivity(MessageActivity.class);
                break;
            case R.id.fragment_find_home_sign_game:
                jumpToActivity(SignGameActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void showNetworkErrorView() {
        mRefreshLayout.finishRefresh();
        ToastUtils.showToast(getContext(), R.string.no_connection_error);
    }

    @Override
    public void showBannerFailureView(HttpError error) {
        mRefreshLayout.finishRefresh();
        ToastUtils.showToast(getContext(), error.getErrorMsg());
    }

    @Override
    public void showBannerSuccessView(List<FindBanner> banners) {
        mRefreshLayout.finishRefresh();
        BankCardAdapter adapter = new BankCardAdapter(banners);
        mViewPager.setAdapter(adapter);

        mIndicatorView.removeAllViews();
        View dotView;
        final int size =DensityUtils.dip2px(getContext(), 7);
        final int margin = DensityUtils.dip2px(getContext(), 8);
        LinearLayout.LayoutParams lp;
        for (int i = 0; i < banners.size(); i++) {
            dotView = new View(getContext());
            lp = new LinearLayout.LayoutParams(size, size);
            lp.rightMargin = (i == banners.size() - 1) ? 0 : margin;
            dotView.setBackgroundResource(R.drawable.find_home_tab_indicator);
            mIndicatorView.addView(dotView, lp);
        }
        mIndicatorView.getChildAt(0).setSelected(true);
    }

    @Override
    public void showNoBannerView() {
        mRefreshLayout.finishRefresh();
        ToastUtils.showToast(getContext(), R.string.no_data_error);
    }

    private class BankCardAdapter extends PagerAdapter {

        private NetworkImageView[] imageViews;
        private List<FindBanner> banners;

        public BankCardAdapter(List<FindBanner> banners) {
            this.banners = banners;
            imageViews = new NetworkImageView[banners.size()];
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[i] = new NetworkImageView(getContext());
            }
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            final FindBanner banner = banners.get(position);
            NetworkImageView imageView = imageViews[position];
            imageView.setImageUri(banner.getPicUrl());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "");//getString(R.string.privacy_policy));
                    bundle.putString("url", banner.getBurl());
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
