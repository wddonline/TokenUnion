package com.iconiz.tokenunion.ui.capital.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.capital.adapter.TUCRecordAdapter;
import com.iconiz.tokenunion.ui.capital.model.ANYRecord;
import com.iconiz.tokenunion.ui.capital.model.Product;
import com.iconiz.tokenunion.ui.wallet.model.BillRecord;
import com.iconiz.tokenunion.ui.wallet.view.activity.BillRecordActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;

import java.util.ArrayList;
import java.util.List;

public class TUCRecordActivity extends BaseActivity {

    private Product mProduct;
    private RecyclerView mListView;
    private TUCRecordAdapter mAdapter;

    private List<ANYRecord> mHoldRecords;
    private List<BillRecord> mBillRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuc_record);
        initData();
        initViews();
    }

    @Override
    protected void initData() {
        mProduct = getIntent().getParcelableExtra("product");

        mHoldRecords = new ArrayList<>();
        ANYRecord hold = new ANYRecord();
        hold.setDatatime(System.currentTimeMillis());
        hold.setAmount("3000.0000");
        hold.setPastDays(10);
        hold.setTotalDays(30);
        hold.setRate("5.0%");
        mHoldRecords.add(hold);

        hold = new ANYRecord();
        hold.setDatatime(System.currentTimeMillis());
        hold.setAmount("5000.0000");
        hold.setPastDays(10);
        hold.setTotalDays(90);
        hold.setRate("7.0%");
        mHoldRecords.add(hold);

        hold = new ANYRecord();
        hold.setDatatime(System.currentTimeMillis());
        hold.setAmount("2000.0000");
        hold.setPastDays(110);
        hold.setTotalDays(180);
        hold.setRate("9.0%");
        mHoldRecords.add(hold);

        hold = new ANYRecord();
        hold.setDatatime(System.currentTimeMillis());
        hold.setAmount("100000.0000");
        hold.setPastDays(70);
        hold.setTotalDays(90);
        hold.setRate("7.0%");
        mHoldRecords.add(hold);


        mBillRecords = new ArrayList<>();
        BillRecord bill = new BillRecord();
        bill.setType(BillRecord.TYPE_SUBSIDY);
        bill.setDatetime(System.currentTimeMillis());
        bill.setBalance("1234.2541");
        mBillRecords.add(bill);

        bill = new BillRecord();
        bill.setType(BillRecord.TYPE_WITHDRAW);
        bill.setDatetime(System.currentTimeMillis());
        bill.setBalance("234.2541");
        mBillRecords.add(bill);

        bill = new BillRecord();
        bill.setType(BillRecord.TYPE_SUBSIDY);
        bill.setDatetime(System.currentTimeMillis());
        bill.setBalance("1234.2541");
        mBillRecords.add(bill);

        bill = new BillRecord();
        bill.setType(BillRecord.TYPE_SUBSIDY);
        bill.setDatetime(System.currentTimeMillis());
        bill.setBalance("1234.2541");
        mBillRecords.add(bill);
    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText("ANY");
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        setTopCardViews();

        SmartRefreshLayout refreshLayout = findViewById(R.id.activity_tuc_record_refresh);
        refreshLayout.setRefreshFooter(new FalsifyFooter(this));
        mListView = findViewById(R.id.activity_tuc_record_list);
        mListView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TUCRecordAdapter(this, mHoldRecords, mBillRecords);
        mAdapter.setOnItemClickedListener(new TUCRecordAdapter.OnItemClickedListener() {
            @Override
            public void onBillRecordClicked() {
                jumpToActivity(BillRecordActivity.class);
            }
        });
        mListView.setAdapter(mAdapter);
    }

    public void onBackClicked(View view) {
        finish();
    }

    private void setTopCardViews() {
        TextView quotaTitleView = findViewById(R.id.activity_tuc_record_quota_title);
        String format = getString(R.string.quota_hold_format);
        quotaTitleView.setText(String.format(format, mProduct.getName()));
        TextView quotaView = findViewById(R.id.activity_tuc_record_quota_hold);
        quotaView.setText("4589.5231");
        TextView increaseView = findViewById(R.id.activity_tuc_record_interest_added);
        increaseView.setText("0.0 %");
    }

    public void onLockCapitalClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", mProduct);
        jumpToActivity(TUCLockCapitalActivity.class, bundle);
    }
}
