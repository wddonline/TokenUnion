package com.tokenunion.pro.ui.capital.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anypocket.pro.R;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.ui.capital.adapter.SavingProductAdapter;
import com.tokenunion.pro.ui.capital.contact.SavingProductPresenter;
import com.tokenunion.pro.ui.capital.model.SavingProduct;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LineDividerDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.http.error.HttpError;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SavingProductFragment extends BaseFragment implements SavingProductPresenter.View {

    @BindView(R.id.fragment_saving_product_refresh)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.fragment_saving_product_list)
    RecyclerView mListView;

    private Unbinder mUnbinder;
    private SavingProductPresenter mPresenter;
    private SavingProductAdapter mAdapter;

    private boolean isDemand;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_saving_product;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, mRootView);
        mPresenter = new SavingProductPresenter(this, mActive);
        isDemand = getArguments().getBoolean("isDemand");
    }

    @Override
    protected void initViews() {
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getProducts("", isDemand ? "1" : "2", false);
            }
        });
        mRefreshView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getProducts("", isDemand ? "1" : "2", true);
            }
        });

        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        LineDividerDecoration decoration = new LineDividerDecoration(getContext(), LinearLayoutManager.VERTICAL, Color.TRANSPARENT);
        decoration.setItemSize(DensityUtils.dip2px(getContext(), 5));
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
    public void showProductsFailureView(boolean isAppend, HttpError error) {
        if (isAppend) {
            mRefreshView.finishLoadMore();
        } else {
            mRefreshView.finishRefresh();
        }
        ToastUtils.showToast(getContext(), error.getErrorMsg());
    }

    @Override
    public void showProductsSuccessView(boolean isAppend, List<SavingProduct> products) {
        if (isAppend) {
            mRefreshView.finishLoadMore();
            mAdapter.appendData(products);
        } else {
            mRefreshView.finishRefresh();
            if (mAdapter == null) {
                mAdapter = new SavingProductAdapter(getContext(), products);
                mAdapter.setOnItemClickedListener(new SavingProductAdapter.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(SavingProduct product, int position) {

                    }
                });
                mListView.setAdapter(mAdapter);
            } else {
                mAdapter.refreshData(products);
            }
        }

    }

    @Override
    public void showNoProductsView(boolean isAppend) {
        if (isAppend) {
            mRefreshView.finishLoadMore();
        } else {
            mRefreshView.finishRefresh();
        }
        ToastUtils.showToast(getContext(), R.string.no_data_error);
    }
}
