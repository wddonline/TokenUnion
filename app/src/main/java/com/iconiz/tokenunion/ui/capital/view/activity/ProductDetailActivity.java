package com.iconiz.tokenunion.ui.capital.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.capital.adapter.CaptialDetailAdapter;
import com.iconiz.tokenunion.ui.capital.model.Product;
import com.iconiz.tokenunion.ui.capital.model.TradeRecord;
import com.iconiz.tokenunion.ui.wallet.view.activity.BillRecordActivity;
import com.iconiz.tokenunion.widget.LineDividerDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends BaseActivity {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mListView;

    private Product mProduct;
    private CaptialDetailAdapter mAdapter;
    private List<TradeRecord> mRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initData();
        initViews();
    }

    @Override
    protected void initData() {
        mProduct = getIntent().getParcelableExtra("product");

        mRecords = new ArrayList<>();

        TradeRecord record = new TradeRecord();
        record.setDatetime(System.currentTimeMillis());
        record.setValue(1234.2541f);
        mRecords.add(record);

        record = new TradeRecord();
        record.setDatetime(System.currentTimeMillis());
        record.setValue(-1234.2541f);
        mRecords.add(record);

        record = new TradeRecord();
        record.setDatetime(System.currentTimeMillis());
        record.setValue(-1234.2541f);
        mRecords.add(record);

        record = new TradeRecord();
        record.setDatetime(System.currentTimeMillis());
        record.setValue(1234.2541f);
        mRecords.add(record);

        record = new TradeRecord();
        record.setDatetime(System.currentTimeMillis());
        record.setValue(1234.2541f);
        mRecords.add(record);

        record = new TradeRecord();
        record.setDatetime(System.currentTimeMillis());
        record.setValue(1234.2541f);
        mRecords.add(record);

        record = new TradeRecord();
        record.setDatetime(System.currentTimeMillis());
        record.setValue(1234.2541f);
        mRecords.add(record);
    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.product_detail);
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        setTopCardViews();

        mRefreshLayout = findViewById(R.id.activity_product_detail_refresh);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
        mRefreshLayout.setRefreshFooter(new FalsifyFooter(this));
        mListView = findViewById(R.id.activity_product_detail_list);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.addItemDecoration(new LineDividerDecoration(this));

        mAdapter = new CaptialDetailAdapter(this, mRecords);
        mListView.setAdapter(mAdapter);
    }

    private void setTopCardViews() {
        ViewGroup spotsView = findViewById(R.id.activity_product_detail_spots);
        if (mProduct.getSpots() == null) {
            spotsView.setVisibility(View.GONE);
        } else {
            spotsView.setVisibility(View.VISIBLE);
            for (int i = 0; i < spotsView.getChildCount(); i++) {
                if (i < mProduct.getSpots().length) {
                    spotsView.getChildAt(i).setVisibility(View.VISIBLE);
                    ((TextView)spotsView.getChildAt(i)).setText(mProduct.getSpots()[i]);
                } else {
                    spotsView.getChildAt(i).setVisibility(View.GONE);
                }
            }
        }

        TextView currencyView = findViewById(R.id.activity_product_detail_currency);
        String format = getString(R.string.quota_hold_format);
        currencyView.setText(String.format(format, mProduct.getName()));
        TextView balanceView = findViewById(R.id.activity_product_detail_balance);
        balanceView.setText("0.2356");
        TextView complexRateView = findViewById(R.id.activity_product_detail_complex_rate);
        complexRateView.setText("18.0");
        TextView totalView = findViewById(R.id.activity_product_detail_capital_total);
        totalView.setText("4589.5231");
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onViewBillsClicked(View view) {
        jumpToActivity(BillRecordActivity.class);
    }

    public void onDepositClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", mProduct);
        jumpToActivity(DepositInActivity.class, bundle);
    }

    public void onRedeemClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", mProduct);
        jumpToActivity(WealthRedeemActivity.class, bundle);
    }
}
