package com.tokenunion.pro.ui.capital.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.ui.capital.CapitalApi;
import com.tokenunion.pro.ui.capital.adapter.CaptialOperaterAdapter;
import com.tokenunion.pro.ui.capital.model.OrdersBean;
import com.tokenunion.pro.utils.EventBusUtils;
import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LineDividerDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.utils.DensityUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class CapitalOperaterFragment extends BaseFragment {
    private static final String TAG = CapitalOperaterFragment.class.getSimpleName();
    private static final String ARG_SYMBOL_KEY = "symbol";
    // 每页数据行数
    private static int PAGE_SIZE = 15;

    private String mSymbol;
    // 操作记录的当前页，从1开始
    private int mPageIndex = 1;
    private int mTotalPage;

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mListView;

    private CaptialOperaterAdapter mAdapter;
    private List<OrdersBean.OperaterBean> mList;

    public CapitalOperaterFragment() {
        // Required empty public constructor
    }

    public static CapitalOperaterFragment newInstance(String symbol) {
        CapitalOperaterFragment fragment = new CapitalOperaterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SYMBOL_KEY, symbol);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSymbol = getArguments().getString(ARG_SYMBOL_KEY);
        }

        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_capital_operater;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {
        mRefreshLayout = mRootView.findViewById(R.id.fragment_capital_operater_record_refresh);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.d(TAG, "onRefresh, "+ mPageIndex+ "/"+ mTotalPage );
                mPageIndex = 1;
                mList = null;
                requestOperaterList();
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
                    requestOperaterList();
                }
                LogUtil.d(TAG, "onLoadMore, to request... "+ mPageIndex+ "/"+ mTotalPage );
                refreshLayout.finishLoadMore();
            }
        });
        mListView = mRootView.findViewById(R.id.fragment_capital_operater_record_list);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        LineDividerDecoration decoration = new LineDividerDecoration(getContext());
        int margin = DensityUtils.dip2px(getContext(), 22);
        decoration.setLeftOffset(margin);
        decoration.setRightOffset(margin);
        mListView.addItemDecoration(decoration);
    }

    private void updateListData(OrdersBean ordersBean) {
        LogUtil.d(TAG, "updateListData()");
        if(null != ordersBean && null != ordersBean.getOrders() && !ordersBean.getOrders().isEmpty()) {
            if(null == mList){
                mList = ordersBean.getOrders();
                mAdapter = new CaptialOperaterAdapter(getContext(), mList);
                mListView.setAdapter(mAdapter);
                LogUtil.d(TAG, "updateListData -> new data");
            }else {
                // mList.addAll(ordersBean.getOrders());
                for(OrdersBean.OperaterBean bean: ordersBean.getOrders()){
                    if(!mList.contains(bean)){
                        mList.add(bean);
                    }
                }
                mAdapter.notifyDataSetChanged();
                LogUtil.d(TAG, "updateListData -> notifyDataSetChanged");
            }
        }
    }

    @Override
    protected void lazyLoad() {
        LogUtil.d(TAG, "lazyLoad()");
        requestOperaterList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void requestOperaterList(){
        LogUtil.d(TAG, "requestOperaterList()");
        if(null == mSymbol){
            return;
        }
        String symbol = mSymbol;
        CapitalApi.getFinanceOperaterList(symbol, mActive, PAGE_SIZE, mPageIndex, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                OrdersBean ordersBean = (OrdersBean) object;
                if(null != ordersBean && null != ordersBean.getOrders() && !ordersBean.getOrders().isEmpty()) {
                    // 计算总页数
                    if (ordersBean.getTotalNum() % PAGE_SIZE == 0) {
                        mTotalPage = ordersBean.getTotalNum() / PAGE_SIZE;
                    } else {
                        mTotalPage = ordersBean.getTotalNum() / PAGE_SIZE + 1;
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateListData(ordersBean);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBusEvent(EventBusUtils.CommonEvent event) {
        switch (event.getId()){
            case EventBusUtils.EVENT_CAPITAL_OPERATOR_UPDATE:
                // 有新操作记录
                mPageIndex = 1;
                mList = null;
                requestOperaterList();
                break;

            default:
                break;
        }
    }
}
