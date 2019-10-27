package com.tokenunion.pro.ui.capital.view.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.ui.capital.CapitalApi;
import com.tokenunion.pro.ui.capital.model.BannerCommonBean;
import com.tokenunion.pro.ui.capital.model.FinanceListBean;
import com.tokenunion.pro.ui.capital.view.activity.InterestRuleActivity;
import com.tokenunion.pro.ui.capital.view.activity.ProductDetailActivity;
import com.tokenunion.pro.ui.capital.view.activity.SavingsProductsActivity;
import com.tokenunion.pro.ui.capital.view.activity.TUCRecordActivity;
import com.tokenunion.pro.ui.capital.view.activity.WealthDetailActivity;
import com.tokenunion.pro.ui.mine.view.activity.MembershipCenterActivity;
import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.manager.UserManager;
import com.tokenunion.pro.manager.model.User;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.ui.capital.adapter.CapitalHomeAdapter;
import com.tokenunion.pro.ui.capital.model.FinanceInfoBean;
import com.tokenunion.pro.ui.mine.view.activity.MessageActivity;
import com.tokenunion.pro.utils.BusinessUtils;
import com.tokenunion.pro.utils.EventBusUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.BannerDelegate;
import com.tokenunion.pro.widget.LineDividerDecoration;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tokenunion.pro.utils.AppUtils;
import com.yidaichu.android.common.utils.DensityUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

public class CapitalHomeFragment extends BaseFragment implements View.OnClickListener {
    public final String TAG = CapitalHomeFragment.class.getSimpleName();
    // 传递bundle的参数的key
    public static final String BUNDLE_KEY_PRODUCT = "product";
    public static final String BUNDLE_KEY_OVERVIEW = "overview";
    public static final String BUNDLE_KEY_FINANCE_INFO = "financeinfo";

    private SmartRefreshLayout mRefreshLayout;

    private RecyclerView mListView;
    private View mHintView;
    private View mHintSignView;
    private BGABanner mBannerView;
    private List<BannerCommonBean> mBannerCommonBeanList;

    // 星级
    private TextView mLevelView; // fragment_capital_home_level

    // 财富总览
    private TextView mCapitalTotalView; // fragment_capital_home_capital_total

    // 综合收益率
    private TextView mComplexRateView; // fragment_capital_home_complex_rate

    // 累计收益
    private TextView mTotalIncomeView; // fragment_capital_home_total_income

    // 昨日收益（USD）
    private TextView mYesterdayIncomeView; // fragment_capital_home_yesterday_income

    private List<FinanceListBean> mCapitals;
    private CapitalHomeAdapter mAdapter;
    private FinanceInfoBean mFinanceInfoBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_capital_home;
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
        mBannerView = mRootView.findViewById(R.id.fragment_capital_home_banner);

        mHintView = mRootView.findViewById(R.id.fragment_capital_home_hint);
        mHintSignView = mRootView.findViewById(R.id.fragment_capital_home_hint_sign);
        mHintView.setOnClickListener(this);
        if(hasNewMessage()){
            mHintSignView.setVisibility(View.VISIBLE);
        }else{
            mHintSignView.setVisibility(View.GONE);
        }

        mRootView.findViewById(R.id.fragment_capital_home_level).setOnClickListener(this);

        mRefreshLayout = mRootView.findViewById(R.id.fragment_capital_home_refresh);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                requestData();
                refreshLayout.finishRefresh();//结束刷新
            }
        });
//        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
////                requestData();
//                refreshLayout.finishLoadMore();//结束加载
//            }
//        });
        mRootView.findViewById(R.id.fragment_capital_home_hide_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_capital_home_complex_btn).setOnClickListener(this);

        // listview
        mListView = mRootView.findViewById(R.id.fragment_capital_home_list);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        LineDividerDecoration decoration = new LineDividerDecoration(getContext());
        int margin = DensityUtils.dip2px(getContext(), 24);
        decoration.setLeftOffset(margin);
        decoration.setRightOffset(margin);
        mListView.addItemDecoration(decoration);

        // top view
        mCapitalTotalView = mRootView.findViewById(R.id.fragment_capital_home_capital_total);
        mCapitalTotalView.setOnClickListener(this);
        mComplexRateView = mRootView.findViewById(R.id.fragment_capital_home_complex_rate);
        mTotalIncomeView = mRootView.findViewById(R.id.fragment_capital_home_total_income);
        mYesterdayIncomeView = mRootView.findViewById(R.id.fragment_capital_home_yesterday_income);
        mRootView.findViewById(R.id.fragment_capital_home_demand).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_capital_home_regular).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_capital_home_my_wealth).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_capital_home_wealth_record).setOnClickListener(this);
    }

    @Override
    protected void lazyLoad() {

        requestData();
    }

    @SuppressLint("ResourceType")
    private void initActionBar() {
//        RoundNetworkImageView headerView = mRootView.findViewById(R.id.fragment_capital_home_headimg);
//        BusinessUtils.displayUserHeadImage(headerView);

        String colorGold = "";
        mLevelView = mRootView.findViewById(R.id.fragment_capital_home_level);
        switch (UserManager.getInstance().getUser().getUserLevel()) {
            case User.LEVEL_YOUNG:
                mLevelView.setText(R.string.level_star);
                mLevelView.setTextColor(Color.parseColor("#E1E1E1"));
                mLevelView.setBackgroundResource(R.drawable.bg_capital_level_0);
                break;
            case User.LEVEL_PREFERRED:
                mLevelView.setText(R.string.level_jade);
                mLevelView.setBackgroundResource(R.drawable.bg_capital_level_1);
                break;
            case User.LEVEL_ELITE:
                mLevelView.setText(R.string.level_gold);
                mLevelView.setBackgroundResource(R.drawable.bg_capital_level_2);
                break;
            case User.LEVEL_RESERVE:
                mLevelView.setText(R.string.level_titanium);
                mLevelView.setTextColor(Color.parseColor("#E1E1E1"));
                mLevelView.setBackgroundResource(R.drawable.bg_capital_level_3);
                break;
            case User.LEVEL_WORLD:
                mLevelView.setText(R.string.level_platinum);
                mLevelView.setBackgroundResource(R.drawable.bg_capital_level_4);
                break;
            case User.LEVEL_INFINITE:
                mLevelView.setText(R.string.level_diamond);
                mLevelView.setBackgroundResource(R.drawable.bg_capital_level_5);
                break;
        }

        // 斜体
        AppUtils.setItalicFont(mLevelView);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle;
        switch (v.getId()) {
            case R.id.fragment_capital_home_hint:
                jumpToActivity(MessageActivity.class);
                break;
            case R.id.fragment_capital_home_hide_btn:
                v.setSelected(!v.isSelected());
                int inputType;
                if (v.isSelected()) {
                    inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                } else {
                    inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
                }
                mCapitalTotalView.setInputType(inputType);
                break;
            case R.id.fragment_capital_home_capital_total:
                jumpToActivity(WealthDetailActivity.class);
                break;
            case R.id.fragment_capital_home_complex_btn:
                jumpToActivity(InterestRuleActivity.class);
                break;
            case R.id.fragment_capital_home_level:
                jumpToActivity(MembershipCenterActivity.class);
                break;
            case R.id.fragment_capital_home_demand:
                bundle = new Bundle();
                bundle.putBoolean("isDemand", true);
                jumpToActivity(SavingsProductsActivity.class, bundle);
                break;
            case R.id.fragment_capital_home_regular:
                bundle = new Bundle();
                bundle.putBoolean("isDemand", false);
                jumpToActivity(SavingsProductsActivity.class, bundle);
                break;
            case R.id.fragment_capital_home_my_wealth:
                jumpToActivity(MyWealthActivity.class);
                break;
            case R.id.fragment_capital_home_wealth_record:
                jumpToActivity(WealthDetailActivity.WealthRecordActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 更新财富信息显示
     * @param bean
     */
    private void refreshFinanceInfo(FinanceInfoBean bean){
        mFinanceInfoBean = bean;
        // 用户星级
        int level = BusinessUtils.getLevelId(bean.getLevel());
        UserManager.getInstance().getUser().setUserLevel(level);
        initActionBar();

        // 总资产------------
        // 财富总览
        mCapitalTotalView.setText(bean.getTotalBal());
        // 综合收益率
        mComplexRateView.setText(bean.getProfitRate());
        // 累计收益
        mTotalIncomeView.setText(bean.getSumProfit());
        // 昨日收益
        mYesterdayIncomeView.setText(bean.getLastProfit());
    }

    private void requestData(){
        CapitalApi.getFinanceInfo("", mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                final FinanceInfoBean bean = (FinanceInfoBean)object;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshFinanceInfo(bean);
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(getContext(), errMessage);
                    }
                });
            }
        });

        // 请求财富列表数据
        CapitalApi.getFinanceList("", mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                List<FinanceListBean> list = (List<FinanceListBean>) object;
                mCapitals = list;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(null != mCapitals && !mCapitals.isEmpty()) {
                            mAdapter = new CapitalHomeAdapter(getContext(), mCapitals);
                            mAdapter.setOnItemClickedListener(new CapitalHomeAdapter.OnItemClickedListener() {
                                @Override
                                public void onItemClicked(FinanceListBean product, boolean isSuperNode) {
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable(BUNDLE_KEY_PRODUCT, product);
                                    bundle.putParcelable(BUNDLE_KEY_FINANCE_INFO, mFinanceInfoBean);
                                    if (isSuperNode) {
                                        jumpToActivity(TUCRecordActivity.class, bundle);
                                    } else {
                                        jumpToActivity(ProductDetailActivity.class, bundle);
                                    }
                                }
                            });
                            mListView.setAdapter(mAdapter);
                        }
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(getContext(), errMessage);
                    }
                });
            }
        });

        // 请求banner数据
        CapitalApi.getHomeBanners(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mBannerCommonBeanList = (List<BannerCommonBean>) object;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initHomeBannerData(mBannerCommonBeanList);
                    }
                });

            }

            @Override
            public void onFailed(String errCode, String errMessage) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(getActivity(), errMessage);
                    }
                });
            }
        });
    }

    /**
     * 加载轮播图
     * @param banners
     */
    private void initHomeBannerData(List<BannerCommonBean> banners) {
        mBannerView.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                itemView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getActivity())
                        .load(model)
                        .placeholder(R.mipmap.banner_bg_blank)
//                        .error(R.mipmap.place_holder_bg)
                        .into(itemView);
            }
        });


        List<String> iconList = new ArrayList<>();
        for (int i = 0; i < banners.size(); i++) {
            iconList.add(banners.get(i).getPicUrl());
        }
        //加载数据
        mBannerView.setData(iconList, null);

        //点击事件
        mBannerView.setDelegate(new BannerDelegate(getActivity(), mBannerCommonBeanList));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBusEvent(EventBusUtils.CommonEvent event) {
        switch (event.getId()){
            case EventBusUtils.EVENT_HAS_NEW_MESSAGE:
                // 有新消息
                mRootView.findViewById(R.id.fragment_capital_home_hint_sign).setVisibility(View.VISIBLE);
                break;

            case EventBusUtils.EVENT_NO_NEW_MESSAGE:
                // 无新消息
                mRootView.findViewById(R.id.fragment_capital_home_hint_sign).setVisibility(View.GONE);
                break;

                default:
                    break;
        }
    }
}
