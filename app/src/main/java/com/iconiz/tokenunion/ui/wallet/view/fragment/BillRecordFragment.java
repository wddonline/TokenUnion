package com.iconiz.tokenunion.ui.wallet.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseFragment;
import com.iconiz.tokenunion.ui.wallet.adapter.BillRecordAdapter;
import com.iconiz.tokenunion.ui.wallet.model.BillRecord;
import com.iconiz.tokenunion.widget.LineDividerDecoration;
import com.iconiz.tokenunion.widget.LoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class BillRecordFragment extends BaseFragment {

    public static final int TYPE_ALL = 0;
    public static final int TYPE_INCOME = 1;
    public static final int TYPE_OUTGO = 2;

    private int mType;

    private LoadView mLoadView;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mListView;

    private List<BillRecord> mRecords;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_bill_record;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mType = getArguments().getInt("type");

        mRecords = new ArrayList<>();

        BillRecord record = new BillRecord();
        record.setName("ANY");
        record.setBalance("1234.2541");
        record.setDatetime(System.currentTimeMillis());
        record.setType(BillRecord.TYPE_CHARGE);
        mRecords.add(record);

        record = new BillRecord();
        record.setName("ANY");
        record.setBalance("1234.2541");
        record.setDatetime(System.currentTimeMillis());
        record.setType(BillRecord.TYPE_WITHDRAW);
        mRecords.add(record);

        record = new BillRecord();
        record.setName("ANY");
        record.setBalance("1234.2541");
        record.setDatetime(System.currentTimeMillis());
        record.setType(BillRecord.TYPE_INTEREST);
        mRecords.add(record);

        record = new BillRecord();
        record.setName("ANY");
        record.setBalance("1234.2541");
        record.setDatetime(System.currentTimeMillis());
        record.setType(BillRecord.TYPE_TRANSFER);
        mRecords.add(record);

        record = new BillRecord();
        record.setName("ANY");
        record.setBalance("1234.2541");
        record.setDatetime(System.currentTimeMillis());
        record.setType(BillRecord.TYPE_TRANSFER);
        mRecords.add(record);

        record = new BillRecord();
        record.setName("ANY");
        record.setBalance("1234.2541");
        record.setDatetime(System.currentTimeMillis());
        record.setType(BillRecord.TYPE_SUBSIDY);
        mRecords.add(record);
    }

    @Override
    protected void initViews() {
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

            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
        mListView = mRootView.findViewById(R.id.fragment_bill_record_list);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        LineDividerDecoration decoration = new LineDividerDecoration(getContext());
        int margin = DensityUtils.dip2px(getContext(), 22);
        decoration.setLeftOffset(margin);
        decoration.setRightOffset(margin);
        mListView.addItemDecoration(decoration);

        BillRecordAdapter adapter = new BillRecordAdapter(getContext(), mRecords);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void lazyLoad() {

    }
}
