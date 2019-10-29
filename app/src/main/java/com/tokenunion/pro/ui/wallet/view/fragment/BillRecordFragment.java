package com.tokenunion.pro.ui.wallet.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.ui.wallet.adapter.BillRecordAdapter;
import com.tokenunion.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.ui.wallet.WalletApi;
import com.tokenunion.pro.ui.wallet.model.AssetBillListBean;
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

public class BillRecordFragment extends BaseFragment {
    private static final String TAG = BillRecordFragment.class.getSimpleName();
    public static final int TYPE_ALL = 0;
    public static final int TYPE_INCOME = 1;
    public static final int TYPE_OUTGO = 2;

    /**
     * 每页记录数
     */
    private static final int PAGE_SIZE = 20;
    /**
     * 当前第几页
     */
    private int mPageIndex = 1;
    /**
     * 总页数
     */
    private int mTotalPage = 0;

    /**
     * 外边传入的展示类型  0：全部；1：收入； 2：支出
     */
    private int mType;

    private AssetBillListBean mAssetBillListBean;

    private LoadView mLoadView;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mListView;
    private EditText mEditTextSearch;

    private List<AssetBillListBean.AssetBillsBean> mRecords;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_bill_record;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mType = getArguments().getInt("type");
    }

    private BillRecordAdapter mAdapter;
    public void setData(List<AssetBillListBean.AssetBillsBean> listData) {
        if (null != listData) {
            boolean isFirstLoad = false;
            if(null == mRecords){
                // 首次加载
                isFirstLoad = true;
                mRecords = new ArrayList<>();
                LogUtil.d(TAG, "is first load ");
            }
            if(isFirstLoad) {
                mRecords = listData;
                mAdapter = new BillRecordAdapter(getContext(), mRecords);
                mListView.setAdapter(mAdapter);
                LogUtil.d(TAG, "is first load, setAdapter. "+ mPageIndex);
            }else {
                for(AssetBillListBean.AssetBillsBean bean : listData) {
                    mRecords.add(bean);
                }
                mAdapter.notifyDataSetChanged();
                LogUtil.d(TAG, "notifyDataSetChanged, "+ mPageIndex);
            }
        }
    }

    @Override
    protected void initViews() {
        mEditTextSearch = mRootView.findViewById(R.id.fragment_bill_record_search);
        mEditTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {//如果是搜索按钮
                    //点击搜索要做的操作
                    mPageIndex = 1;
                    mRecords = null;
                    doReqeust();
                }
                return false;
            }
        });
        mLoadView = mRootView.findViewById(R.id.fragment_bill_record_load);
        mLoadView.setOnReloadClickedListener(new LoadView.OnReloadClickedListener() {
            @Override
            public void onReloadClicked() {

            }
        });
        mRefreshLayout = mRootView.findViewById(R.id.fragment_bill_record_refresh);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.d(TAG, "onRefresh, "+ mPageIndex+ "/"+ mTotalPage );
                mPageIndex = 1;
                mRecords = null;
                doReqeust();
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
                    doReqeust();
                } else {
//                    ToastUtils.showToast(getActivity(), "已经最后一页了");
                }
                LogUtil.d(TAG, "onLoadMore, to request... "+ mPageIndex+ "/"+ mTotalPage );
                refreshLayout.finishLoadMore();
            }
        });
        mListView = mRootView.findViewById(R.id.fragment_bill_record_list);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        LineDividerDecoration decoration = new LineDividerDecoration(getContext());
        int margin = DensityUtils.dip2px(getContext(), 22);
        decoration.setLeftOffset(margin);
        decoration.setRightOffset(margin);
        mListView.addItemDecoration(decoration);
    }

    @Override
    protected void lazyLoad() {
        doReqeust();
    }

    private void doReqeust() {
        String symbol = "";
        if(!TextUtils.isEmpty(mEditTextSearch.getText().toString())){
            symbol = mEditTextSearch.getText().toString();
        }
        mLoadView.setVisibility(View.VISIBLE);
        WalletApi.getAssetBillPage(symbol, mType + "", mPageIndex, PAGE_SIZE, mActive,
                new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        mAssetBillListBean = (AssetBillListBean) object;
                        mTotalPage = mAssetBillListBean.getTotalNum() / PAGE_SIZE;
                        if (mAssetBillListBean.getTotalNum() % PAGE_SIZE != 0) {
                            // 如果不是size的整数倍，总页数加1
                            mTotalPage = mTotalPage + 1;
                        }
                        LogUtil.d(TAG, "mTotalPage = "+ mTotalPage);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setData(mAssetBillListBean.getAssetBills());
                                mLoadView.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(getActivity(), errMessage);
                                mLoadView.setVisibility(View.GONE);
                            }
                        });
                    }
                });
    }
}
