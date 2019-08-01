package com.iconiz.tokenunion.ui.wallet.view.fragment;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.manager.UserManager;
import com.iconiz.tokenunion.manager.model.User;
import com.iconiz.tokenunion.ui.base.BaseFragment;
import com.iconiz.tokenunion.ui.base.CommonViewPagerAdapter;
import com.iconiz.tokenunion.ui.mine.view.activity.MessageActivity;
import com.iconiz.tokenunion.ui.wallet.model.TopCard;
import com.iconiz.tokenunion.widget.RoundNetworkImageView;
import com.yidaichu.android.common.utils.AppUtils;
import com.yidaichu.android.common.utils.DensityUtils;
import com.yidaichu.android.common.view.tab.viewpager.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

public class WalletHomeFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager mTabView;
    private ViewGroup mIndicatorView;
    private ViewPager mViewPager;

    private int mCurrentPosition = 0;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_wallet_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {
        List<TopCard> cards = new ArrayList<>();
        TopCard card = new TopCard();
        card.setType(UserManager.getInstance().getUser().getUserLevel());
        card.setPayment(false);
        card.setBalance("999.2356");
        cards.add(card);

        card = new TopCard();
        card.setType(User.LEVEL_YOUNG);
        card.setPayment(true);
        cards.add(card);

        card = new TopCard();
        card.setType(User.LEVEL_PREFERRED);
        card.setPayment(true);
        cards.add(card);

        card = new TopCard();
        card.setType(User.LEVEL_ELITE);
        card.setPayment(true);
        cards.add(card);

        card = new TopCard();
        card.setType(User.LEVEL_RESERVE);
        card.setPayment(true);
        cards.add(card);

        card = new TopCard();
        card.setType(User.LEVEL_WORLD);
        card.setPayment(true);
        cards.add(card);

        card = new TopCard();
        card.setType(User.LEVEL_INFINITE);
        card.setPayment(true);
        cards.add(card);

        initActionBar();
        mTabView = mRootView.findViewById(R.id.fragment_wallet_home_gallery);
        int height = (int) (180f * AppUtils.getScreenWidth(getContext()) / 375f);
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
            }
        });
        mIndicatorView = mRootView.findViewById(R.id.fragment_wallet_home_indicator);
        mIndicatorView.getChildAt(0).setSelected(true);

        List<Fragment> fragments = new ArrayList<>();

        Bundle bundle;
        Fragment fragment = new WalletFragment();
        bundle = new Bundle();
        fragment.setArguments(bundle);
        fragments.add(fragment);

        fragment = new PaymentCardFragment();
        bundle = new Bundle();
        bundle.putInt("level", User.LEVEL_YOUNG);
        fragment.setArguments(bundle);
        fragments.add(fragment);

        fragment = new PaymentCardFragment();
        bundle = new Bundle();
        bundle.putInt("level", User.LEVEL_PREFERRED);
        fragment.setArguments(bundle);
        fragments.add(fragment);

        fragment = new PaymentCardFragment();
        bundle = new Bundle();
        bundle.putInt("level", User.LEVEL_ELITE);
        fragment.setArguments(bundle);
        fragments.add(fragment);

        fragment = new PaymentCardFragment();
        bundle = new Bundle();
        bundle.putInt("level", User.LEVEL_RESERVE);
        fragment.setArguments(bundle);
        fragments.add(fragment);

        fragment = new PaymentCardFragment();
        bundle = new Bundle();
        bundle.putInt("level", User.LEVEL_WORLD);
        fragment.setArguments(bundle);
        fragments.add(fragment);

        fragment = new PaymentCardFragment();
        bundle = new Bundle();
        bundle.putInt("level", User.LEVEL_INFINITE);
        fragment.setArguments(bundle);
        fragments.add(fragment);

        mViewPager = mRootView.findViewById(R.id.fragment_wallet_home_pager);
        CommonViewPagerAdapter pagerAdapter = new CommonViewPagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTabView.setCurrentItem(position);

                mIndicatorView.getChildAt(position).setSelected(true);
                mIndicatorView.getChildAt(mCurrentPosition).setSelected(false);
                mCurrentPosition = position;
            }
        });
    }

    private void initActionBar() {
        RoundNetworkImageView headerView = mRootView.findViewById(R.id.fragment_wallet_home_headimg);
        headerView.setImageUri("http://m.360buyimg.com/pop/jfs/t25198/35/225724054/50007/53ddf0bc/5b696c57Nc3885dbe.jpg");
        TextView levelView = mRootView.findViewById(R.id.fragment_wallet_home_level);
        switch (UserManager.getInstance().getUser().getUserLevel()) {
            case User.LEVEL_YOUNG:
                levelView.setText("Young");
                break;
            case User.LEVEL_PREFERRED:
                levelView.setText("Preferred");
                break;
            case User.LEVEL_ELITE:
                levelView.setText("Elite");
                break;
            case User.LEVEL_RESERVE:
                levelView.setText("Reserve");
                break;
            case User.LEVEL_WORLD:
                levelView.setText("World");
                break;
            case User.LEVEL_INFINITE:
                levelView.setText("Infinite");
                break;
        }
        TextView yesterdayIncomeHintView = mRootView.findViewById(R.id.fragment_wallet_home_yesterday_income_hint);
        String yesterdayIncomeHint = getString(R.string.yesterday_income_any);
        yesterdayIncomeHintView.setText(String.format(yesterdayIncomeHint, "3652.325"));
        mRootView.findViewById(R.id.fragment_wallet_home_hint).setOnClickListener(this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_wallet_home_hint:
                jumpToActivity(MessageActivity.class);
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
                final TextView balanceView = view.findViewById(R.id.item_wallet_top_card_capital_total);
                balanceView.setText(card.getBalance());
                hideBalanceView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setSelected(!v.isSelected());
                        int inputType;
                        if (v.isSelected()) {
                            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
                        } else {
                            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                        }
                        balanceView.setInputType(inputType);
                    }
                });
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
}
