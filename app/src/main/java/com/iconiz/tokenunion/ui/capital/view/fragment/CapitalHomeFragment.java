package com.iconiz.tokenunion.ui.capital.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.manager.UserManager;
import com.iconiz.tokenunion.manager.model.User;
import com.iconiz.tokenunion.ui.base.BaseFragment;
import com.iconiz.tokenunion.ui.capital.adapter.CapitalHomeAdapter;
import com.iconiz.tokenunion.ui.capital.model.Product;
import com.iconiz.tokenunion.ui.capital.view.activity.TUCRecordActivity;
import com.iconiz.tokenunion.ui.capital.view.activity.InterestRuleActivity;
import com.iconiz.tokenunion.ui.capital.view.activity.ProductDetailActivity;
import com.iconiz.tokenunion.ui.capital.view.activity.WealthDetailActivity;
import com.iconiz.tokenunion.ui.mine.view.activity.MessageActivity;
import com.iconiz.tokenunion.widget.LineDividerDecoration;
import com.iconiz.tokenunion.widget.RoundNetworkImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class CapitalHomeFragment extends BaseFragment implements View.OnClickListener {

    private SmartRefreshLayout mRefreshLayout;
    private TextView mCapitalTotalView;
    private RecyclerView mListView;
    private View mHintView;

    private List<Product> mCapitals;
    private CapitalHomeAdapter mAdapter;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_capital_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mCapitals = new ArrayList<>();

        Product capital = new Product();
        capital.setName("ANY");
        capital.setHot(true);
        capital.setRate("5269.5487");
        capital.setSpots(new String[] {"超级节点", "锁定加息"});
        capital.setBalance("89.5487 USD");
        mCapitals.add(capital);

        capital = new Product();
        capital.setName("BTC");
        capital.setHot(false);
        capital.setRate("0.0254");
        capital.setSpots(new String[] {"灵活存取", "0手续费"});
        capital.setBalance("89.5487 USD");
        mCapitals.add(capital);

        capital = new Product();
        capital.setName("ETH");
        capital.setHot(false);
        capital.setRate("123.3256");
        capital.setSpots(new String[] {"灵活存取"});
        capital.setBalance("89.5487 USD");
        mCapitals.add(capital);

        capital = new Product();
        capital.setName("EOS");
        capital.setHot(false);
        capital.setRate("2365.1254");
        capital.setSpots(new String[] {"0手续费"});
        capital.setBalance("89.5487 USD");
        mCapitals.add(capital);
    }

    @Override
    protected void initViews() {
        mHintView = mRootView.findViewById(R.id.fragment_capital_home_hint);
        mHintView.setOnClickListener(this);
        mRefreshLayout = mRootView.findViewById(R.id.fragment_capital_home_refresh);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
        mListView = mRootView.findViewById(R.id.fragment_capital_home_list);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        LineDividerDecoration decoration = new LineDividerDecoration(getContext(), LinearLayoutManager.VERTICAL, Color.TRANSPARENT);
        decoration.setItemSize(DensityUtils.dip2px(getContext(), 8));
        mListView.addItemDecoration(decoration);
        mRootView.findViewById(R.id.fragment_capital_home_hide_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_capital_home_complex_btn).setOnClickListener(this);
    }

    @Override
    protected void lazyLoad() {
        initActionBar();
        setTopCardViews();
        mAdapter = new CapitalHomeAdapter(getContext(), mCapitals);
        mAdapter.setOnItemClickedListener(new CapitalHomeAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(Product product, boolean isSuperNode) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("product", product);
                if (isSuperNode) {
                    jumpToActivity(TUCRecordActivity.class, bundle);
                } else {
                    jumpToActivity(ProductDetailActivity.class, bundle);
                }
            }
        });
        mListView.setAdapter(mAdapter);
    }

    private void initActionBar() {
        RoundNetworkImageView headerView = mRootView.findViewById(R.id.fragment_capital_home_headimg);
        headerView.setImageUri("http://m.360buyimg.com/pop/jfs/t25198/35/225724054/50007/53ddf0bc/5b696c57Nc3885dbe.jpg");
        TextView levelView = mRootView.findViewById(R.id.fragment_capital_home_level);
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
        TextView yesterdayIncomeHintView = mRootView.findViewById(R.id.fragment_capital_home_yesterday_income_hint);
        String yesterdayIncomeHint = getString(R.string.yesterday_income_any);
        yesterdayIncomeHintView.setText(String.format(yesterdayIncomeHint, "3652.325"));
    }

    private void setTopCardViews() {
        mCapitalTotalView = mRootView.findViewById(R.id.fragment_capital_home_capital_total);
        mCapitalTotalView.setText("999.2356");
        mCapitalTotalView.setOnClickListener(this);
        TextView complexRateView = mRootView.findViewById(R.id.fragment_capital_home_complex_rate);
        complexRateView.setText("18.0");
        TextView totalIncomeView = mRootView.findViewById(R.id.fragment_capital_home_total_income);
        totalIncomeView.setText("658.2356");
        TextView yesterdayIncomeView = mRootView.findViewById(R.id.fragment_capital_home_yesterday_income);
        yesterdayIncomeView.setText("58.2698");

    }

    @Override
    public void onClick(View v) {
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
        }
    }
}
