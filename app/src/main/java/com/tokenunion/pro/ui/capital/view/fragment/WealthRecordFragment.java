package com.tokenunion.pro.ui.capital.view.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.ui.capital.adapter.WealthRecordAdapter;
import com.tokenunion.pro.ui.capital.contact.WealthRecordPresenter;
import com.tokenunion.pro.ui.capital.model.WealthRecord;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LineDividerDecoration;
import com.yidaichu.android.common.http.error.HttpError;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WealthRecordFragment extends BaseFragment implements WealthRecordPresenter.View {

    @BindView(R.id.fragment_wealth_record_refresh)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.fragment_wealth_record_list)
    RecyclerView mListView;

    private Unbinder mUnbinder;
    private WealthRecordPresenter mPresenter;
    private WealthRecordAdapter mAdapter;

    private String mOrderType;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_wealth_record;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mOrderType = getArguments().getString("orderType");
        mPresenter = new WealthRecordPresenter(this, mActive);
    }

    @Override
    protected void initViews() {
        mUnbinder = ButterKnife.bind(this, mRootView);

        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getRecords("", mOrderType, false);
            }
        });
        mRefreshView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getRecords("", mOrderType, true);
            }
        });

        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        LineDividerDecoration decoration = new LineDividerDecoration(getContext(), LinearLayoutManager.VERTICAL, Color.parseColor("#EDEDED"));
//        decoration.setItemSize(DensityUtils.dip2px(getContext(), 5));
        int offset = DensityUtils.dip2px(getContext(), 23);
        decoration.setLeftOffset(offset);
        decoration.setRightOffset(offset);
        mListView.addItemDecoration(decoration);
    }

    @Override
    protected void lazyLoad() {
        mRefreshView.autoRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void showNetworkErrorView(boolean isAppend) {
        if (isAppend) {
            mRefreshView.finishLoadMore();
        } else {
            mRefreshView.finishRefresh();
        }
        ToastUtils.showToast(getContext(), R.string.no_connection_error);
    }

    @Override
    public void showRecordsFailureView(boolean isAppend, HttpError error) {
        if (isAppend) {
            mRefreshView.finishLoadMore();
        } else {
            mRefreshView.finishRefresh();
        }
        ToastUtils.showToast(getContext(), error.getErrorMsg());
    }

    @Override
    public void showRecordsSuccessView(boolean isAppend, List<WealthRecord> records) {
        if (isAppend) {
            mRefreshView.finishLoadMore();
            mAdapter.appendData(records);
        } else {
            mRefreshView.finishRefresh();
            if (mAdapter == null) {
                mAdapter = new WealthRecordAdapter(getContext(), records);
                mAdapter.setOnItemClickedListener(new WealthRecordAdapter.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(WealthRecord record, int position) {

                    }
                });
                mListView.setAdapter(mAdapter);
            } else {
                mAdapter.refreshData(records);
            }
        }
    }

    @Override
    public void showNoRecordsView(boolean isAppend) {
        if (isAppend) {
            mRefreshView.finishLoadMore();
        } else {
            mRefreshView.finishRefresh();
        }
        ToastUtils.showToast(getContext(), R.string.no_data_error);
    }
}
