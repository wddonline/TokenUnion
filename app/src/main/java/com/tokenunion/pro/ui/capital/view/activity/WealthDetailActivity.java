package com.tokenunion.pro.ui.capital.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.capital.CapitalApi;
import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.capital.model.ListRatioBean;
import com.tokenunion.pro.ui.capital.adapter.WealthDetailAdapter;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LineDividerDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.utils.DensityUtils;

public class WealthDetailActivity extends CapitalBaseActivity {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mListView;

    private WealthDetailAdapter mAdapter;
    private ListRatioBean mListRatioBean;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wealth_detail);
        initData();
        initViews();

        requestRatioData();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.wealth_detail);
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        mRefreshLayout = findViewById(R.id.activity_wealth_detail_refresh);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                requestRatioData();
                mRefreshLayout.finishRefresh();
            }
        });
        mListView = findViewById(R.id.activity_wealth_detail_list);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        LineDividerDecoration decoration = new LineDividerDecoration(this);
        int margin = DensityUtils.dip2px(this, 22);
        decoration.setLeftOffset(margin);
        decoration.setRightOffset(margin);
        mListView.addItemDecoration(decoration);
    }

    /**
     * 更新顶部数据显示
     */
    private void setTopCardViews() {
        TextView totalView = findViewById(R.id.activity_wealth_detail_capital_total);
        totalView.setText(mListRatioBean.getTotalBal());
        TextView complexRateView = findViewById(R.id.activity_wealth_detail_complex_rate);
        complexRateView.setText(mListRatioBean.getProfitRate());
//        TextView increaseRateView = findViewById(R.id.activity_wealth_detail_complex_increase_rate);
//        increaseRateView.setText("");
    }

    public void onBackClicked(View view) {
        finish();
    }

    @Override
    protected void updateData(){
        if(null == mListRatioBean){
            return;
        }

        // 更新顶部数据
        setTopCardViews();
        if(null != mListRatioBean.getFinanceAssets()) {
            mAdapter = new WealthDetailAdapter(this, mListRatioBean.getFinanceAssets());
            mListView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void updateOverviewData() {

    }

    private void requestRatioData() {
        CapitalApi.getFinanceListRatio(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mListRatioBean = (ListRatioBean) object;
                runOnUiThread(() -> updateData());
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(() -> ToastUtils.showToast(WealthDetailActivity.this, errMessage));
            }
        });
    }

    public static class WealthRecordActivity extends BaseActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_wealth_record);
        }

        @Override
        protected void initData() {

        }

        @Override
        protected void initViews() {

        }
    }
}
