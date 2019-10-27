package com.tokenunion.pro.ui.wallet.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.base.CommonViewPagerAdapter;
import com.tokenunion.pro.ui.mine.view.activity.MembershipCenterActivity;
import com.tokenunion.pro.ui.mine.view.activity.MessageActivity;
import com.tokenunion.pro.ui.wallet.WalletApi;
import com.tokenunion.pro.ui.wallet.model.MarketItemBean;
import com.tokenunion.pro.ui.wallet.model.TopCard;
import com.anypocket.pro.R;
import com.tokenunion.pro.manager.UserManager;
import com.tokenunion.pro.manager.model.User;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.utils.EventBusUtils;
import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.NetRequestUtils;
import com.tokenunion.pro.utils.AppUtils;
import com.yidaichu.android.common.utils.DensityUtils;
import com.yidaichu.android.common.view.tab.viewpager.ScaleInTransformer;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class WalletHomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = WalletHomeFragment.class.getSimpleName();
    // 定时刷新行情的间隔，毫秒
    private static final int TIME_PERIOD_MS = 10* 1000;

    private ViewPager mTabView;
    private ViewPager mViewPager;
    private ViewPager mViewPagerMarket;
    // 总资产，顶部的"资产总览"
    private String mTotalBal;
    private ImageView mHintView;
    private TextView mHintSignView;
    private LinearLayout mMarketView;
    private List<MarketItemBean> mMarketItemBeanList;
    private Handler mHandler;

    private CommonViewPagerAdapter mPagerAdapterMaket;
    private List<Fragment> mMarketFragments;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_wallet_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        LogUtil.d(TAG, "onDestroy");
        EventBusUtils.unregister(this);
        mHandler.removeCallbacks(mRunnable);
        LogUtil.d(TAG, "mRunnable, removeCallbacks");
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        TextView yesterdayIncomeHintView = mRootView.findViewById(R.id.fragment_wallet_home_yesterday_income_hint);
        String yesterdayIncomeHint = getString(R.string.yesterday_income_any);
        yesterdayIncomeHintView.setText(yesterdayIncomeHint);

        mMarketView = mRootView.findViewById(R.id.fragment_wallet_home_market_ll);

        mHintSignView = mRootView.findViewById(R.id.fragment_wallet_home_hint_sign);
        mHintView = mRootView.findViewById(R.id.fragment_wallet_home_hint);
        mHintView.setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_wallet_home_level).setOnClickListener(this);
        mRootView.findViewById(R.id.item_market_iv_left).setOnClickListener(this);
        mRootView.findViewById(R.id.item_market_iv_right).setOnClickListener(this);

        if(hasNewMessage()){
            mHintSignView.setVisibility(View.VISIBLE);
        }else{
            mHintSignView.setVisibility(View.GONE);
        }

        mHandler = new Handler();
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            LogUtil.d(TAG, "requestMarketData()...");
            requestMarketData();

            mHandler.postDelayed(mRunnable, TIME_PERIOD_MS);
        }
    };


    private void initActionBar() {
//        if(UserAccount.getInstance().isLogin()) {
//            RoundNetworkImageView headerView = mRootView.findViewById(R.id.fragment_wallet_home_headimg);
//            BusinessUtils.displayUserHeadImage(headerView);
////            headerView.setImageUri("http://m.360buyimg.com/pop/jfs/t25198/35/225724054/50007/53ddf0bc/5b696c57Nc3885dbe.jpg");
//        }
        TextView levelView = mRootView.findViewById(R.id.fragment_wallet_home_level);
        switch (UserManager.getInstance().getUser().getUserLevel()) {
            case User.LEVEL_YOUNG:
                levelView.setText(R.string.level_star);
                levelView.setTextColor(Color.parseColor("#E1E1E1"));
                levelView.setBackgroundResource(R.drawable.bg_capital_level_0);
                break;
            case User.LEVEL_PREFERRED:
                levelView.setText(R.string.level_jade);
                levelView.setBackgroundResource(R.drawable.bg_capital_level_1);
                break;
            case User.LEVEL_ELITE:
                levelView.setText(R.string.level_gold);
                levelView.setBackgroundResource(R.drawable.bg_capital_level_2);
                break;
            case User.LEVEL_RESERVE:
                levelView.setText(R.string.level_titanium);
                levelView.setTextColor(Color.parseColor("#E1E1E1"));
                levelView.setBackgroundResource(R.drawable.bg_capital_level_3);
                break;
            case User.LEVEL_WORLD:
                levelView.setText(R.string.level_platinum);
                levelView.setBackgroundResource(R.drawable.bg_capital_level_4);
                break;
            case User.LEVEL_INFINITE:
                levelView.setText(R.string.level_diamond);
                levelView.setBackgroundResource(R.drawable.bg_capital_level_5);
                break;
        }

        // 斜体
        AppUtils.setItalicFont(levelView);
    }

    @Override
    protected void lazyLoad() {
        LogUtil.d(TAG, "lazyLoad()");
        if(null == mTotalBal) {
            updateListData(""); // 首次必须要有，不然不加载数据
        }

        if(null != mHandler) {
            mHandler.removeCallbacks(mRunnable);
        }
        mHandler.post(mRunnable);
    }

    @Override
    public void onClick(View v) {
        int nCurrentMarketPage = 0;
        switch (v.getId()) {
            case R.id.fragment_wallet_home_hint:
                jumpToActivity(MessageActivity.class);
                break;

            case R.id.fragment_wallet_home_level:
                jumpToActivity(MembershipCenterActivity.class);
                break;

            case R.id.item_market_iv_left:
                if (mViewPagerMarket.getAdapter() == null) {
                    break;
                }
                nCurrentMarketPage = mViewPagerMarket.getCurrentItem() - 1;
                if (nCurrentMarketPage < 0) {
//                    nCurrentMarketPage = mViewPagerMarket.getAdapter().getCount() - 1;
                    nCurrentMarketPage = 0;
                }
                mViewPagerMarket.setCurrentItem(nCurrentMarketPage, true);
                break;

            case R.id.item_market_iv_right:
                if (mViewPagerMarket.getAdapter() == null) {
                    break;
                }
                nCurrentMarketPage = mViewPagerMarket.getCurrentItem() + 1;
                if (nCurrentMarketPage >= mViewPagerMarket.getAdapter().getCount()) {
//                    nCurrentMarketPage = 0;
                    nCurrentMarketPage = mViewPagerMarket.getAdapter().getCount() -1;
                }
                mViewPagerMarket.setCurrentItem(nCurrentMarketPage, true);
                break;
            default:
                break;
        }
    }

    private class TabAdapter extends PagerAdapter {
        private List<TopCard> cards;
        private int mWidth;

        TabAdapter(List<TopCard> cards) {
            this.cards = cards;
            mWidth = AppUtils.getScreenWidth(getContext()) - DensityUtils.dip2px(getContext(), 46);
        }

        public int getCount() {
            return cards.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.item_wallet_top_card, container, false);
            view.getLayoutParams().width = mWidth;
            ImageView cardView = view.findViewById(R.id.item_wallet_top_card_bg);
            View dataView = view.findViewById(R.id.item_wallet_top_card_data);
            TopCard card = cards.get(position);
            if (card.isPayment()) {
                dataView.setVisibility(View.GONE);
                switch (card.getType()) {
                    case User.LEVEL_YOUNG:
                        cardView.setImageResource(R.mipmap.black_card_small);
                        break;
                    case User.LEVEL_PREFERRED:
                        cardView.setImageResource(R.mipmap.black_card_small);
                        break;
                    case User.LEVEL_ELITE:
                        cardView.setImageResource(R.mipmap.red_card_small);
                        break;
                    case User.LEVEL_RESERVE:
                        cardView.setImageResource(R.mipmap.red_card_small);
                        break;
                    case User.LEVEL_WORLD:
                        cardView.setImageResource(R.mipmap.gold_card_small);
                        break;
                    case User.LEVEL_INFINITE:
                        cardView.setImageResource(R.mipmap.gold_card_small);
                        break;
                    default:
                        cardView.setImageResource(0);
                        break;
                }
            } else {
                dataView.setVisibility(View.VISIBLE);
                View hideBalanceView = view.findViewById(R.id.item_wallet_top_card_hide_btn);
                hideBalanceView.setSelected(false);
                final TextView balanceView = view.findViewById(R.id.item_wallet_top_card_capital_total);
                balanceView.setText(card.getBalance());
                hideBalanceView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int inputType;
                        if (v.isSelected()) {
                            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
                        } else {
                            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                        }
                        balanceView.setInputType(inputType);
                        v.setSelected(!v.isSelected());
                    }
                });
//                switch (UserManager.getInstance().getUser().getUserLevel()) {
//                    case User.LEVEL_YOUNG:
//                        cardView.setImageResource(R.mipmap.bg_wallet_star);
//                        break;
//                    case User.LEVEL_PREFERRED:
//                        cardView.setImageResource(R.mipmap.bg_wallet_jade);
//                        break;
//                    case User.LEVEL_ELITE:
//                        cardView.setImageResource(R.mipmap.bg_wallet_gold);
//                        break;
//                    case User.LEVEL_RESERVE:
//                        cardView.setImageResource(R.mipmap.bg_wallet_titanium);
//                        break;
//                    case User.LEVEL_WORLD:
//                        cardView.setImageResource(R.mipmap.bg_wallet_platinum);
//                        break;
//                    case User.LEVEL_INFINITE:
//                        cardView.setImageResource(R.mipmap.bg_wallet_diamond);
//                        break;
//                }
            }
            container.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    private void updateListData(String totalBal) {
        List<TopCard> cards = new ArrayList<>();
        TopCard card = new TopCard();
        card.setType(UserManager.getInstance().getUser().getUserLevel());
        card.setPayment(false);
        if(null != totalBal) {
            card.setBalance(totalBal);
        }else{
            card.setBalance("");//"999.2356");
        }
        cards.add(card);

//        card = new TopCard();
//        card.setType(User.LEVEL_DIAMOND);
//        card.setPayment(true);
//        cards.add(card);

        initActionBar();
        mTabView = mRootView.findViewById(R.id.fragment_wallet_home_gallery);
        int height = (int) ((AppUtils.getScreenWidth(getContext()) - DensityUtils.dip2px(getContext(), 44)) / 1.8f);
        mTabView.getLayoutParams().height = height;
        TabAdapter tabAdapter = new TabAdapter(cards);
        mTabView.setPageMargin(DensityUtils.dip2px(getContext(), 8));
        mTabView.setOffscreenPageLimit(3);
        mTabView.setAdapter(tabAdapter);
        mTabView.setPageTransformer(true, new ScaleInTransformer());
        mTabView.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mViewPager.setCurrentItem(position);

                // 第一页，才显示行情
                if(position != 0){
                    mMarketView.setVisibility(View.GONE);
                }else {
                    mMarketView.setVisibility(View.VISIBLE);
                }
            }
        });

        List<Fragment> fragments = new ArrayList<>();

        Bundle bundle;
        Fragment fragment = new WalletFragment();
        bundle = new Bundle();
        fragment.setArguments(bundle);
        fragments.add(fragment);

//        fragment = new PaymentCardFragment();
//        bundle = new Bundle();
//        bundle.putInt("level", User.LEVEL_YOUNG);
//        fragment.setArguments(bundle);
//        fragments.add(fragment);

//        fragment = new PaymentCardFragment();
//        bundle = new Bundle();
//        bundle.putInt("level", User.LEVEL_JADE);
//        fragment.setArguments(bundle);
//        fragments.add(fragment);
//
//        fragment = new PaymentCardFragment();
//        bundle = new Bundle();
//        bundle.putInt("level", User.LEVEL_GOLD);
//        fragment.setArguments(bundle);
//        fragments.add(fragment);
//
//        fragment = new PaymentCardFragment();
//        bundle = new Bundle();
//        bundle.putInt("level", User.LEVEL_TITANIUM);
//        fragment.setArguments(bundle);
//        fragments.add(fragment);
//
//        fragment = new PaymentCardFragment();
//        bundle = new Bundle();
//        bundle.putInt("level", User.LEVEL_PLATINUM);
//        fragment.setArguments(bundle);
//        fragments.add(fragment);

//        fragment = new PaymentCardFragment();
//        bundle = new Bundle();
//        bundle.putInt("level", User.LEVEL_DIAMOND);
//        fragment.setArguments(bundle);
//        fragments.add(fragment);

        mViewPagerMarket = mRootView.findViewById(R.id.fragment_wallet_home_market_pager);
        mViewPager = mRootView.findViewById(R.id.fragment_wallet_home_pager);
        mPagerAdapterMaket = new CommonViewPagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(mPagerAdapterMaket);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                mTabView.setCurrentItem(position);
            }
        });
    }

    private void loadMarketData(List<MarketItemBean> marketItemBeanList){
        if(null == marketItemBeanList || marketItemBeanList.isEmpty()){
            LogUtil.d(TAG, "loadMarketData, empty data");
            mPagerAdapterMaket = new CommonViewPagerAdapter(getChildFragmentManager(), new ArrayList<>());
            mViewPagerMarket.setAdapter(mPagerAdapterMaket);
            return;
        }
        int count = marketItemBeanList.size();
        if(null == mMarketItemBeanList || mMarketItemBeanList.size() != count) {
            LogUtil.d(TAG, "loadMarketData, new data");
            // 全新数据
            mMarketFragments = new ArrayList<>();
            for (int i = 0; i < count; i += 3) {
                MarketDataFragment fragment = new MarketDataFragment();
                List<MarketItemBean> fragmentData = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    if (i + j < count) {
                        fragmentData.add(marketItemBeanList.get(i + j));
                    } else {
                        break;
                    }
                }
                fragment.setData(fragmentData);
                mMarketFragments.add(fragment);
            }
            mPagerAdapterMaket = new CommonViewPagerAdapter(getChildFragmentManager(), mMarketFragments);
            mViewPagerMarket.setAdapter(mPagerAdapterMaket);
        }else{
            LogUtil.d(TAG, "loadMarketData, update data");
            // 更新数据
            for (int i = 0; i < count; i += 3) {
                MarketDataFragment fragment = (MarketDataFragment) mMarketFragments.get(i/3);
                List<MarketItemBean> fragmentData = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    if (i + j < count) {
                        fragmentData.add(marketItemBeanList.get(i + j));
                    } else {
                        break;
                    }
                }
                fragment.setData(fragmentData);
            }
            mPagerAdapterMaket.notifyDataSetChanged();
        }
        mMarketItemBeanList = marketItemBeanList;
    }

    private void requestMarketData(){
        if(!NetRequestUtils.isNetworkConnected(getContext())){
            LogUtil.w(TAG, "requestMarketData(), network error");
            return;
        }
        WalletApi.getMarketData(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                List<MarketItemBean> list = (List<MarketItemBean>) object;
                list.get(0).setPrice(list.get(0).getPrice());
                if(null != list && !list.isEmpty()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadMarketData(list);
                        }
                    });
                }
            }

            @Override
            public void onFailed(String errCode, String errMessage) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.w(TAG, "requestMarketData() failed! "+ errMessage);
//                        ToastUtils.showToast(getActivity(), errMessage);
                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBusEvent(EventBusUtils.CommonEvent event) {
        switch (event.getId()){
            case EventBusUtils.EVENT_ASSET_DATA_UPDATED:
                // 资产列表数据获取完毕
                Bundle bundle = event.getData();
                if (null != bundle){
                    mTotalBal = bundle.getString(WalletFragment.KEY_ASSET_DATA_UPDATED, "");
                    updateListData(mTotalBal);
                }
                break;
            case EventBusUtils.EVENT_HAS_NEW_MESSAGE:
                // 有新消息
                mHintSignView.setVisibility(View.VISIBLE);
                break;

            case EventBusUtils.EVENT_NO_NEW_MESSAGE:
                // 无新消息
                mHintSignView.setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }
}
