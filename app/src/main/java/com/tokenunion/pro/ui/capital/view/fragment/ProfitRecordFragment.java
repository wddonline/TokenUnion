package com.tokenunion.pro.ui.capital.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.ui.capital.CapitalApi;
import com.tokenunion.pro.ui.capital.adapter.CaptialDetailAdapter;
import com.tokenunion.pro.ui.capital.model.ProfitListBean;
import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LineDividerDecoration;
import com.tokenunion.pro.widget.LoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;


public class ProfitRecordFragment extends BaseFragment {
    /**
     * 每页记录数
     */
    private static final int PAGE_SIZE = 15;
    /**
     * 当前第几页
     */
    private int mPageIndex = 1;
    /**
     * 总页数
     */
    private int mTotalPage = 0;

    public String getSymbol() {
        return mSymbol;
    }

    public void setSymbol(String symbol) {
        mSymbol = symbol;
    }

    private String mSymbol;

    private ProfitListBean mProfitListBean;

    private LoadView mLoadView;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mListView;
    private EditText mEditTextSearch;

    private List<ProfitListBean.OrdersBean> mRecords;

    public ProfitRecordFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_profit_record;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private CaptialDetailAdapter mAdapter;
    public void updateListData(ProfitListBean profitListBean) {
        LogUtil.d(TAG, "updateListData");
        if (null != profitListBean && null != profitListBean.getOrders()) {
            boolean isFirstLoad = false;
            if(null == mRecords){
                // 首次加载
                isFirstLoad = true;
                mRecords = new ArrayList<>();
                LogUtil.d(TAG, "is first load ");
            }
            if(isFirstLoad) {
                mRecords = profitListBean.getOrders();
                mAdapter = new CaptialDetailAdapter(getContext(), profitListBean);
                mListView.setAdapter(mAdapter);
                LogUtil.d(TAG, "is first load, setAdapter. "+ mPageIndex);
            }else {
                for(ProfitListBean.OrdersBean bean : profitListBean.getOrders()) {
                    if(!mRecords.contains(bean)) {
                        mRecords.add(bean);
                    }
                }
                mAdapter.notifyDataSetChanged();
                LogUtil.d(TAG, "notifyDataSetChanged, "+ mPageIndex);
            }
        }
    }

    @Override
    protected void initViews() {
        mLoadView = mRootView.findViewById(R.id.fragment_profit_record_load);
        mRefreshLayout = mRootView.findViewById(R.id.fragment_profit_record_refresh);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.d(TAG, "onRefresh, "+ mPageIndex+ "/"+ mTotalPage );
                mPageIndex = 1;
                mRecords = null;
                requestProfitListData(mSymbol);
                refreshLayout.finishRefresh();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                LogUtil.d(TAG, "onLoadMore, "+ mPageIndex+ "/"+ mTotalPage );
                if (mPageIndex < mTotalPage) {
                    mPageIndex++;
//                    ToastUtils.showToast(getActivity(), "mPageIndex," + mPageIndex);
                    requestProfitListData(mSymbol);
                } else {
//                    ToastUtils.showToast(getActivity(), "已经最后一页了");
                }
                LogUtil.d(TAG, "onLoadMore, to request... "+ mPageIndex+ "/"+ mTotalPage );
                refreshLayout.finishLoadMore();
            }
        });
        mListView = mRootView.findViewById(R.id.fragment_profit_record_list);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        LineDividerDecoration decoration = new LineDividerDecoration(getContext());
        int margin = DensityUtils.dip2px(getContext(), 22);
        decoration.setLeftOffset(margin);
        decoration.setRightOffset(margin);
        mListView.addItemDecoration(decoration);
    }

    @Override
    protected void lazyLoad() {
        requestProfitListData(mSymbol);
    }

    /**
     * 财富收益列表--活期
     */
    protected void requestProfitListData(String symbol){
        LogUtil.d(TAG, "requestProfitListData()");
        CapitalApi.getFinanceProfitList(symbol, mActive, PAGE_SIZE, mPageIndex, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mProfitListBean = (ProfitListBean)object;
                if(null != mProfitListBean && null != mProfitListBean.getOrders() && !mProfitListBean.getOrders().isEmpty()) {
                    // 计算总页数
                    if (mProfitListBean.getTotalNum() % PAGE_SIZE == 0) {
                        mTotalPage = mProfitListBean.getTotalNum() / PAGE_SIZE;
                    } else {
                        mTotalPage = mProfitListBean.getTotalNum() / PAGE_SIZE + 1;
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateListData(mProfitListBean);
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(getActivity(), errMessage);
                    }
                });

            }
        });
    }
}
